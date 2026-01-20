package dev.mahiro.client.auth;

import dev.mahiro.client.gui.auth.AuthScreen;
import dev.mahiro.client.gui.clickgui.ClickGuiScreen;
import dev.mahiro.client.gui.hud.HudEditorScreen;
import dev.mahiro.client.gui.mainmenu.MainMenuScreen;
import dev.mahiro.client.gui.mainmenu.WelcomeScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;

import java.util.Objects;

public final class AuthGate {
    private static volatile boolean initialized;
    private static volatile boolean pendingMainMenuIntro;

    private static String deviceId;
    private static String licenseKey;
    private static String serverBaseUrl;
    private static String serverSigningKeyX509Base64;
    private static volatile String sessionToken;
    private static volatile boolean sessionOnlineVerified;

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
        pendingMainMenuIntro = false;
        initialized = true;
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

    public static boolean interceptSetScreen(MinecraftClient c, Screen nextScreen) {
        if (AuthGate.isVerified()) return false;
        if (c == null) return false;
        if (!isBlockedScreen(nextScreen)) return false;

        if (c.currentScreen instanceof AuthScreen) return true;

        c.execute(() -> {
            if (AuthGate.isVerified()) return;
            if (c.currentScreen instanceof AuthScreen) return;
            c.setScreen(new AuthScreen(nextScreen));
        });
        return true;
    }

    public static void doTickCheck(MinecraftClient c) {
        if (AuthGate.isVerified()) return;
        if (c == null) return;
        if (c.player != null || c.world != null) {
            System.exit(0);
            return;
        }
        Screen s = c.currentScreen;
        if (isBlockedScreen(s) && !(s instanceof AuthScreen)) {
            c.execute(() -> {
                if (AuthGate.isVerified()) return;
                if (c.currentScreen instanceof AuthScreen) return;
                c.setScreen(new AuthScreen(s));
            });
        }
    }

    public static boolean isVerified() {
        String token = sessionToken;
        return sessionOnlineVerified && token != null && !token.isBlank();
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
        return Objects.requireNonNullElse(serverSigningKeyX509Base64, "");
    }

    /*public static void saveServerSigningKeyX509Base64(String base64) {
        serverSigningKeyX509Base64 = base64 == null ? "" : base64.trim();
    }*/

    public static boolean canToggleModules() {
        return isVerified();
    }

    public static void acceptVerifiedToken(String token) {
        sessionToken = token == null ? "" : token.trim();
        sessionOnlineVerified = true;
    }

    public static void clearSession() {
        sessionToken = "";
        sessionOnlineVerified = false;
    }

    public static void requestMainMenuIntro() {
        pendingMainMenuIntro = true;
    }

    public static boolean consumeMainMenuIntro() {
        if (!pendingMainMenuIntro) return false;
        pendingMainMenuIntro = false;
        return true;
    }
}
