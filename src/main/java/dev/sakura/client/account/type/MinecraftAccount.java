package dev.sakura.client.account.type;

import com.google.gson.JsonObject;
import net.minecraft.client.session.Session;

public interface MinecraftAccount {
    Session login();

    String username();

    default JsonObject toJSON() {
        final JsonObject object = new JsonObject();
        object.addProperty("username", username());
        return object;
    }
}
