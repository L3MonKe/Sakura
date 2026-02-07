package dev.sakura.verification.server.service;

import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.storage.CardRepository;
import dev.sakura.verification.server.storage.SqliteDatabase;
import dev.sakura.verification.server.storage.UserRepository;
import dev.sakura.verification.server.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AdminService {
    private final IRCServer server;
    private final SqliteDatabase database;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public record SessionRow(String sessionId, String ip, String username, String ign) {
    }

    public AdminService(IRCServer server, SqliteDatabase database, UserRepository userRepository, CardRepository cardRepository) {
        this.server = server;
        this.database = database;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public List<UserRepository.UserRow> listUsers() throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.listAllUsers(c);
        }
    }

    public List<UserRepository.UserRow> listOnlineUsers() throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.listOnlineUsers(c);
        }
    }

    public UserRepository.UserRow findUser(String username) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.findByUsername(c, username);
        }
    }

    public List<UserRepository.UserRow> findUsersByQq(String qq) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.findByQq(c, qq);
        }
    }

    public boolean setPassword(String username, String newPassword) {
        return server.getAuthService().changePassword(username, newPassword);
    }

    public boolean setPrefix(String username, String prefix) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            boolean ok = userRepository.updatePrefix(c, username, prefix);
            if (ok) {
                server.getUserManager().updatePrefix(username, prefix);
            }
            return ok;
        }
    }

    public boolean resetHwid(String username) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.resetHwid(c, username);
        }
    }

    public boolean setCloudConfigMax(String username, int max) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.updateMaxCloudConfigs(c, username, max);
        }
    }

    public boolean setBanned(String username, boolean banned) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            boolean ok = userRepository.setBanned(c, username, banned);
            if (ok && banned) {
                server.disconnectUser(username, "你已被封禁");
            }
            return ok;
        }
    }

    public boolean setOnline(String username, boolean online) {
        return server.getAuthService().setUserOnline(username, online);
    }

    public void disconnectUser(String username, String reason) {
        server.disconnectUser(username, reason);
    }

    public String createCard(String group, long durationMs) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return cardRepository.createCard(c, group, durationMs);
        }
    }

    public List<String> createCardsBatch(String group, long durationMs, int count) throws Exception {
        List<String> keys = new ArrayList<>(count);
        try (java.sql.Connection c = database.openConnection()) {
            c.setAutoCommit(false);
            try {
                for (int i = 0; i < count; i++) {
                    keys.add(cardRepository.createCard(c, group, durationMs));
                }
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
        return keys;
    }

    public boolean deleteCard(String cardKey) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return cardRepository.deleteCard(c, cardKey);
        }
    }

    public int deleteCardGroup(String group) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return cardRepository.deleteGroup(c, group);
        }
    }

    public List<CardRepository.CardRow> listCards(String group, Boolean used) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT card_key,grp,duration_ms,created_at,used_by,used_at FROM cards WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (group != null && !group.isBlank()) {
            sql.append(" AND grp = ?");
            params.add(group.trim());
        }
        if (used != null) {
            sql.append(used ? " AND used_by IS NOT NULL" : " AND used_by IS NULL");
        }
        sql.append(" ORDER BY created_at DESC");

        try (java.sql.Connection c = database.openConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<CardRepository.CardRow> out = new ArrayList<>();
                while (rs.next()) {
                    Long usedAt = rs.getObject("used_at") == null ? null : rs.getLong("used_at");
                    out.add(new CardRepository.CardRow(
                            rs.getString("card_key"),
                            rs.getString("grp"),
                            rs.getLong("duration_ms"),
                            rs.getLong("created_at"),
                            rs.getString("used_by"),
                            usedAt
                    ));
                }
                return out;
            }
        }
    }

    public int getCloudConfigMax(String username) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.getMaxCloudConfigs(c, username);
        }
    }

    public List<String> listCloudConfigNames(String ownerUsername) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.listCloudConfigNames(c, ownerUsername);
        }
    }

    public String getCloudConfigContent(String ownerUsername, String name) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.getCloudConfigContent(c, ownerUsername, name);
        }
    }

    public boolean saveCloudConfig(String ownerUsername, String name, String content) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            c.setAutoCommit(false);
            try {
                if (userRepository.cloudConfigExists(c, ownerUsername, name)) {
                    boolean ok = userRepository.updateCloudConfig(c, ownerUsername, name, content);
                    c.commit();
                    return ok;
                }

                int max = userRepository.getMaxCloudConfigs(c, ownerUsername);
                int count = userRepository.countCloudConfigs(c, ownerUsername);
                if (count >= max) {
                    c.rollback();
                    return false;
                }

                boolean ok = userRepository.insertCloudConfig(c, ownerUsername, name, content);
                if (!ok) {
                    c.rollback();
                    return false;
                }
                c.commit();
                return true;
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
    }

    public boolean deleteCloudConfig(String ownerUsername, String name) throws Exception {
        try (java.sql.Connection c = database.openConnection()) {
            return userRepository.deleteCloudConfig(c, ownerUsername, name);
        }
    }

    public List<SessionRow> listSessions() {
        Map<String, Connection> snap = server.getConnectionSnapshot();
        List<SessionRow> out = new ArrayList<>(snap.size());
        for (Map.Entry<String, Connection> e : snap.entrySet()) {
            String sessionId = e.getKey();
            Connection conn = e.getValue();
            String ip = conn == null ? "" : conn.getIPAddress();
            User user = server.getUserManager().getUser(sessionId);
            String username = user == null ? "" : user.getUsername();
            String ign = user == null ? "" : user.getIgn();
            out.add(new SessionRow(sessionId, ip, username, ign));
        }
        return out;
    }

    public static String formatEpochMillis(long epochMillis) {
        if (epochMillis <= 0) {
            return "";
        }
        return Instant.ofEpochMilli(epochMillis).toString();
    }
}
