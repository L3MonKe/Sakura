package dev.mzc.client.manager.impl;

import dev.mzc.client.Sakura;
import java.util.ArrayList;
import java.util.List;

import dev.mzc.client.module.impl.client.Friend;

public class FriendManager {
    private final List<String> friends = new ArrayList<>();

    public void addFriend(String name) {
        if (!isFriend(name)) {
            friends.add(name);
            // Don't save here if called during loading
            if (Sakura.CONFIG != null) {
                Sakura.CONFIG.saveFriends();
                updateModule();
            }
        }
    }

    public void removeFriend(String name) {
        if (isFriend(name)) {
            friends.removeIf(f -> f.equalsIgnoreCase(name));
            if (Sakura.CONFIG != null) {
                Sakura.CONFIG.saveFriends();
                updateModule();
            }
        }
    }

    private void updateModule() {
        if (Sakura.MODULES == null) return;
        Friend friendModule = Sakura.MODULES.getModule(Friend.class);
        if (friendModule != null) {
            friendModule.refreshFriends();
        }
    }

    public boolean isFriend(String name) {
        for (String friend : friends) {
            if (friend.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void clearFriends() {
        friends.clear();
        if (Sakura.CONFIG != null) {
            Sakura.CONFIG.saveFriends();
            updateModule();
        }
    }

    public List<String> getFriends() {
        return friends;
    }
}
