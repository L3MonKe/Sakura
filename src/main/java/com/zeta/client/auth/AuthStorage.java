package com.zeta.client.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zeta.client.Zeta;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class AuthStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private AuthStorage() {
    }

    static JsonObject read(Path file) {
        try {
            if (!Files.exists(file)) return null;
            String content = Files.readString(file, StandardCharsets.UTF_8);
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (Exception e) {
            Zeta.LOGGER.error("AuthStorage read failed: {}", e.getMessage());
            return null;
        }
    }

    static void write(Path file, JsonObject json) {
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(file, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (Exception e) {
            Zeta.LOGGER.error("AuthStorage write failed: {}", e.getMessage());
        }
    }

    static String getString(JsonObject json, String key) {
        try {
            if (json == null || key == null) return "";
            if (!json.has(key)) return "";
            return json.get(key).getAsString();
        } catch (Exception ignored) {
            return "";
        }
    }
}

