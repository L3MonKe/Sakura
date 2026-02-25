package dev.mzc.client.module.impl.client;

import dev.mzc.client.manager.Managers;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Friend extends Module {
    public Friend() {
        super("Friend", "好友", Category.Client);
        this.setType(ModuleType.All);
        refreshFriends();
    }

    // Called when GUI is opened or friends are updated
    public void refreshFriends() {
        // Save current state of friends
        Map<String, Boolean> friendStates = new HashMap<>();
        Value<?> hiddenValue = null;

        // Iterate a copy to avoid concurrent modification (though unlikely here)
        List<Value<?>> currentValues = new ArrayList<>(this.values);
        
        for (Value<?> value : currentValues) {
            if (value.getName().equals("Hidden")) {
                hiddenValue = value;
            } else if (value instanceof BoolValue) {
                friendStates.put(value.getName(), ((BoolValue) value).get());
            }
        }

        // Clear the actual list
        this.values.clear();
        
        // Restore hidden value FIRST
        if (hiddenValue != null) {
            this.values.add(hiddenValue);
        } else {
            // Fallback - this is actually critical if super constructor hasn't run yet or failed
            // But since we use the same BoolValue class, it should be fine.
            // However, to be 100% safe against "duplicate" logic in some GUIs:
            // We'll rely on the one we found.
        }

        // Add each friend as a boolean setting
        List<String> friends = Managers.FRIEND.getFriends();
        for (String friend : friends) {
            // Restore state if exists, otherwise default to true
            boolean enabled = friendStates.getOrDefault(friend, true);
            BoolValue friendVal = new BoolValue(friend, friend, enabled);
            this.values.add(friendVal);
        }
    }

    public boolean isFriend(String name) {
        if (!isEnabled()) return false;
        if (!Managers.FRIEND.isFriend(name)) return false;
        
        for (Value<?> v : values) {
            if (v.getName().equalsIgnoreCase(name) && v instanceof BoolValue) {
                return ((BoolValue) v).get();
            }
        }
        return true; // Default to true if somehow missing from settings but in list
    }
}
