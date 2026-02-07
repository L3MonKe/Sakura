package dev.sakura.verification;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.implemention.clientbound.ClientBoundLoginResultPacket;
import dev.sakura.verification.packet.implemention.serverbound.ServerBoundHandshakePacket;
import dev.sakura.verification.processor.IRCProtocol;
import dev.sakura.verification.server.handler.HandlerManager;
import dev.sakura.verification.server.auth.AuthService;
import dev.sakura.verification.server.storage.CardRepository;
import dev.sakura.verification.server.storage.SqliteDatabase;
import dev.sakura.verification.server.storage.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

public class VerificationTests {
    @Test
    public void protocolRoundtrip() {
        IRCProtocol protocol = new IRCProtocol();
        ClientBoundLoginResultPacket in = new ClientBoundLoginResultPacket(true, 123L, 456L, "OK");
        byte[] encoded = protocol.encode(in);
        IRCPacket decoded = protocol.decodePayload(encoded);
        Assertions.assertInstanceOf(ClientBoundLoginResultPacket.class, decoded);
        ClientBoundLoginResultPacket out = (ClientBoundLoginResultPacket) decoded;
        Assertions.assertTrue(out.isSuccess());
        Assertions.assertEquals(123L, out.getExpireAt());
        Assertions.assertEquals(456L, out.getTimeWindow());
        Assertions.assertEquals("OK", out.getMessage());
    }

    @Test
    public void handshakeIsAllowedWhenUserNull() {
        HandlerManager handlerManager = new HandlerManager();
        boolean allowed = handlerManager.allowNull(new ServerBoundHandshakePacket("u", "t"));
        Assertions.assertTrue(allowed);
    }

    @Test
    public void authFlowRegisterLoginRecharge() throws Exception {
        Path dbFile = Files.createTempFile("verify", ".sqlite");
        SqliteDatabase database = new SqliteDatabase(dbFile);
        database.initSchema();

        UserRepository userRepository = new UserRepository();
        CardRepository cardRepository = new CardRepository();
        AuthService authService = new AuthService(database, userRepository, cardRepository);

        String cardKey;
        try (Connection c = database.openConnection()) {
            cardKey = cardRepository.createCard(c, "test", 24L * 60L * 60L * 1000L);
        }

        AuthService.AuthResult reg = authService.register("user_test", "pw", "hwid", java.util.Set.of("123"), "phone", cardKey);
        Assertions.assertTrue(reg.success(), reg.message());

        try (Connection c = database.openConnection()) {
            userRepository.setOnline(c, "user_test", false);
        }

        AuthService.AuthResult loginBad = authService.login("user_test", "bad", "hwid", java.util.Collections.emptySet(), "");
        Assertions.assertFalse(loginBad.success());

        AuthService.AuthResult loginOk = authService.login("user_test", "pw", "hwid", java.util.Collections.emptySet(), "");
        Assertions.assertTrue(loginOk.success(), loginOk.message());

        try (Connection c = database.openConnection()) {
            userRepository.setOnline(c, "user_test", false);
        }

        String cardKey2;
        try (Connection c = database.openConnection()) {
            cardKey2 = cardRepository.createCard(c, "test", 24L * 60L * 60L * 1000L);
        }
        AuthService.AuthResult recharge = authService.recharge("user_test", cardKey2);
        Assertions.assertTrue(recharge.success(), recharge.message());
    }

    @Test
    public void cloudConfigCrudAndLimit() throws Exception {
        Path dbFile = Files.createTempFile("verify", ".sqlite");
        SqliteDatabase database = new SqliteDatabase(dbFile);
        database.initSchema();

        UserRepository userRepository = new UserRepository();
        CardRepository cardRepository = new CardRepository();
        AuthService authService = new AuthService(database, userRepository, cardRepository);

        String cardKey;
        try (Connection c = database.openConnection()) {
            cardKey = cardRepository.createCard(c, "test", 24L * 60L * 60L * 1000L);
        }
        AuthService.AuthResult reg = authService.register("user_test", "pw", "hwid", java.util.Set.of("123"), "phone", cardKey);
        Assertions.assertTrue(reg.success(), reg.message());

        try (Connection c = database.openConnection()) {
            userRepository.setOnline(c, "user_test", false);
        }

        try (Connection c = database.openConnection()) {
            int max = userRepository.getMaxCloudConfigs(c, "user_test");
            Assertions.assertEquals(3, max);

            Assertions.assertTrue(userRepository.insertCloudConfig(c, "user_test", "Fin", "{\"a\":1}"));
            Assertions.assertTrue(userRepository.insertCloudConfig(c, "user_test", "Fin2", "{\"a\":2}"));
            Assertions.assertTrue(userRepository.insertCloudConfig(c, "user_test", "Fin3", "{\"a\":3}"));
            Assertions.assertEquals(3, userRepository.countCloudConfigs(c, "user_test"));

            Assertions.assertTrue(userRepository.cloudConfigExists(c, "user_test", "Fin"));
            Assertions.assertEquals("{\"a\":1}", userRepository.getCloudConfigContent(c, "user_test", "Fin"));

            Assertions.assertTrue(userRepository.updateCloudConfig(c, "user_test", "Fin", "{\"a\":9}"));
            Assertions.assertEquals("{\"a\":9}", userRepository.getCloudConfigContent(c, "user_test", "Fin"));

            Assertions.assertTrue(userRepository.deleteCloudConfig(c, "user_test", "Fin2"));
            Assertions.assertFalse(userRepository.cloudConfigExists(c, "user_test", "Fin2"));
        }
    }
}
