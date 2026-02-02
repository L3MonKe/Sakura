package dev.mahiro.client.auth;

import by.radioegor146.nativeobfuscator.Native;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.mahiro.client.auth.crypto.AesGcm;
import dev.mahiro.client.auth.crypto.B64;
import dev.mahiro.client.config.ConfigManager;
import dev.mahiro.niurendeobf.ZKMIndy;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

@Native
@ZKMIndy
public final class AuthPassStore {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    private static final Path FILE = Paths.get(System.getProperty("user.home", ""), "Mahiro").resolve("auth_pass.dat");
    private static final Path LEGACY_FILE = ConfigManager.CONFIG_DIR.resolve("auth_pass.json");
    private static final byte[] AAD = "mahiro-auth-pass-v1".getBytes(StandardCharsets.UTF_8);
    private static final int MAGIC = 0x4D485031;

    private AuthPassStore() {
    }

    public static AuthPass load(String deviceId) {
        if (deviceId == null || deviceId.isBlank()) return null;
        try {
            AuthPass pass = loadBinary(deviceId);
            if (pass != null) return pass;

            AuthPass legacy = loadLegacyJson(deviceId);
            if (legacy != null) {
                save(deviceId, legacy.token(), legacy.expiresAtMillis());
                try {
                    Files.deleteIfExists(LEGACY_FILE);
                } catch (Exception ignored) {
                }
                return legacy;
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void save(String deviceId, String token, long expiresAtMillis) {
        if (deviceId == null || deviceId.isBlank()) return;
        if (token == null || token.isBlank()) return;
        if (expiresAtMillis <= System.currentTimeMillis()) return;

        try {
            Files.createDirectories(FILE.getParent());

            JsonObject payload = new JsonObject();
            payload.addProperty("token", token);
            payload.addProperty("expiresAt", expiresAtMillis);
            payload.addProperty("device", sha256Hex(deviceId));

            byte[] key32 = sha256Bytes(("mahiro-pass-v1|" + deviceId).getBytes(StandardCharsets.UTF_8));
            AesGcm.Encrypted enc = AesGcm.encrypt(key32, GSON.toJson(payload).getBytes(StandardCharsets.UTF_8), AAD);
            byte[] blob = encodeBinary(enc.iv(), enc.ciphertext());
            Files.write(FILE, blob);
        } catch (Exception ignored) {
        }
    }

    public static void save(String deviceId, String token, long expiresAtMillis, String passPayloadB64, String passSigB64) {
        save(deviceId, token, expiresAtMillis);
    }

    public static void clear() {
        try {
            Files.deleteIfExists(FILE);
        } catch (Exception ignored) {
        }
        try {
            Files.deleteIfExists(LEGACY_FILE);
        } catch (Exception ignored) {
        }
    }

    private static byte[] encodeBinary(byte[] iv, byte[] ct) {
        byte[] i = iv == null ? new byte[0] : iv;
        byte[] c = ct == null ? new byte[0] : ct;
        ByteBuffer buf = ByteBuffer.allocate(4 + 4 + i.length + 4 + c.length);
        buf.putInt(MAGIC);
        buf.putInt(i.length);
        buf.put(i);
        buf.putInt(c.length);
        buf.put(c);
        return buf.array();
    }

    private static byte[][] decodeBinary(byte[] blob) {
        if (blob == null) return null;
        if (blob.length < 4 + 4 + 4) return null;
        ByteBuffer buf = ByteBuffer.wrap(blob);
        if (buf.getInt() != MAGIC) return null;
        int ivLen = buf.getInt();
        if (ivLen <= 0 || ivLen > 64 || buf.remaining() < ivLen + 4) return null;
        byte[] iv = new byte[ivLen];
        buf.get(iv);
        int ctLen = buf.getInt();
        if (ctLen <= 0 || ctLen > 65536 || buf.remaining() != ctLen) return null;
        byte[] ct = new byte[ctLen];
        buf.get(ct);
        return new byte[][]{iv, ct};
    }

    private static AuthPass loadBinary(String deviceId) {
        try {
            if (!Files.exists(FILE)) return null;
            byte[] blob = Files.readAllBytes(FILE);
            byte[][] parts = decodeBinary(blob);
            if (parts == null || parts.length != 2) return null;
            byte[] iv = parts[0];
            byte[] ct = parts[1];
            byte[] key32 = sha256Bytes(("mahiro-pass-v1|" + deviceId).getBytes(StandardCharsets.UTF_8));
            byte[] pt = AesGcm.decrypt(key32, iv, ct, AAD);
            JsonObject payload = JsonParser.parseString(new String(pt, StandardCharsets.UTF_8)).getAsJsonObject();
            return parsePayload(deviceId, payload);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static AuthPass loadLegacyJson(String deviceId) {
        try {
            if (!Files.exists(LEGACY_FILE)) return null;
            String raw = Files.readString(LEGACY_FILE, StandardCharsets.UTF_8);
            if (raw == null || raw.isBlank()) return null;
            JsonObject wrapper = JsonParser.parseString(raw).getAsJsonObject();
            if (wrapper == null || !wrapper.has("iv") || !wrapper.has("ct") || !wrapper.has("v")) return null;
            int v = wrapper.get("v").getAsInt();
            if (v != 1) return null;
            byte[] iv = B64.dec(wrapper.get("iv").getAsString());
            byte[] ct = B64.dec(wrapper.get("ct").getAsString());
            byte[] key32 = sha256Bytes(("mahiro-pass-v1|" + deviceId).getBytes(StandardCharsets.UTF_8));
            byte[] pt = AesGcm.decrypt(key32, iv, ct, AAD);
            JsonObject payload = JsonParser.parseString(new String(pt, StandardCharsets.UTF_8)).getAsJsonObject();
            return parsePayload(deviceId, payload);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static AuthPass parsePayload(String deviceId, JsonObject payload) throws Exception {
        if (payload == null || !payload.has("token") || !payload.has("expiresAt") || !payload.has("device"))
            return null;
        String token = payload.get("token").getAsString();
        long expiresAt = payload.get("expiresAt").getAsLong();
        String deviceHash = payload.get("device").getAsString();
        if (token == null || token.isBlank()) return null;
        if (expiresAt <= System.currentTimeMillis()) return null;
        if (!sha256Hex(deviceId).equalsIgnoreCase(deviceHash)) return null;
        return new AuthPass(token, expiresAt);
    }

    private static byte[] sha256Bytes(byte[] input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input);
    }

    private static String sha256Hex(String s) throws Exception {
        byte[] hash = sha256Bytes(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
