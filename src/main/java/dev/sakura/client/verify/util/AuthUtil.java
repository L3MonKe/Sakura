package dev.sakura.client.verify.util;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.gui.mainmenu.MainMenuScreen;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import dev.sakura.client.verify.client.IRCTransport;
import dev.sakura.niurendeobf.ZKMIndy;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@Native
@ZKMIndy
public final class AuthUtil {
    public enum Mode {
        Login,
        Register
    }

    public static final AtomicReference<String> authed = new AtomicReference<>("");
    public static final String AUTH_OK_TOKEN = "SakuraVerifyToken0123456789ABCDE";
    private static final long TIME_WINDOW_MS = 30_000L;
    private static final long MAX_TIME_WINDOW_SKEW = 1L;

    private AuthUtil() {
    }

    public static void startAuth(Mode mode, String username, String password, String license, BiConsumer<Boolean, String> ui) {
        if (ui != null) {
            ui.accept(true, "正在连接验证服务器...");
        }

        final String hwid = HwidUtil.getHWID();
        final Set<String> qqSet = QQUtils.getAllQQ();
        String p = TodeskUtils.getPhone();
        final String phone = p == null ? "" : p;

        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicBoolean authedFlag = new AtomicBoolean(false);

        Thread t = new Thread(() -> {
            IRCTransport transport;
            try {
                transport = VerificationClient.connect(new IRCHandler() {
                    @Override
                    public void onMessage(String sender, String message) {
                    }

                    @Override
                    public void onDisconnected(String message) {
                        boolean duringAuth = !authedFlag.get();
                        if (duringAuth) {
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> {
                                    if (ui != null) {
                                        ui.accept(false, "连接断开: " + (message == null ? "" : message));
                                    }
                                });
                            }
                            authed.set("");
                            AuthState.clear();
                            VerificationClient.shutdown();
                            ExitUtil.exit0();
                            return;
                        }

                        VerificationClient.shutdown();
                        ExitUtil.exit0();
                    }

                    @Override
                    public void onConnected() {
                    }

                    @Override
                    public String getInGameUsername() {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        if (mc.player == null) return mc.getSession().getUsername();
                        return mc.player.getName().getString();
                    }

                    @Override
                    public void onLoginResult(boolean success, long expireAt, long timeWindow, String message) {
                        long now = Instant.now().toEpochMilli();
                        long nowTimeWindow = now / TIME_WINDOW_MS;
                        long skew = Math.abs(nowTimeWindow - timeWindow);
                        boolean timeOk = skew <= MAX_TIME_WINDOW_SKEW;
                        if (success) {
                            authedFlag.set(true);
                            authed.set(AUTH_OK_TOKEN);
                            AuthState.setAuthed(username, expireAt);
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> {
                                    if (!timeOk && ui != null) {
                                        ui.accept(true, "登录成功，但本地时间偏差较大，建议校准系统时间");
                                    }
                                    MainMenuScreen screen = new MainMenuScreen();
                                    screen.startIntro();
                                    MinecraftClient.getInstance().setScreen(screen);
                                });
                            }
                        } else {
                            authed.set("");
                            AuthState.clear();
                            String m = message == null ? "" : message;
                            String display = "登录失败 " + m;
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> {
                                    if (ui != null) {
                                        ui.accept(false, display.trim());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onRegisterResult(boolean success, long expireAt, long timeWindow, String message) {
                        long now = Instant.now().toEpochMilli();
                        long nowTimeWindow = now / TIME_WINDOW_MS;
                        long skew = Math.abs(nowTimeWindow - timeWindow);
                        boolean timeOk = skew <= MAX_TIME_WINDOW_SKEW;
                        if (success) {
                            authedFlag.set(true);
                            authed.set(AUTH_OK_TOKEN);
                            AuthState.setAuthed(username, expireAt);
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> {
                                    if (!timeOk && ui != null) {
                                        ui.accept(true, "注册成功，但本地时间偏差较大，建议校准系统时间");
                                    }
                                    MainMenuScreen screen = new MainMenuScreen();
                                    screen.startIntro();
                                    MinecraftClient.getInstance().setScreen(screen);
                                });
                            }
                            return;
                        }

                        authed.set("");
                        AuthState.clear();
                        String m = message == null ? "" : message;
                        if (finished.compareAndSet(false, true)) {
                            MinecraftClient.getInstance().execute(() -> {
                                if (ui != null) {
                                    ui.accept(false, ("注册失败 " + m).trim());
                                }
                            });
                        }
                    }
                });
            } catch (IOException e) {
                if (finished.compareAndSet(false, true)) {
                    MinecraftClient.getInstance().execute(() -> {
                        if (ui != null) {
                            ui.accept(false, "连接失败: " + e.getMessage());
                        }
                    });
                }
                return;
            }

            MinecraftClient.getInstance().execute(() -> {
                if (ui != null) {
                    ui.accept(true, mode == Mode.Login ? "正在登录..." : "正在注册...");
                }
            });

            if (mode == Mode.Login) {
                transport.login(username, password, hwid, qqSet, phone);
            } else {
                transport.register(username, password, hwid, qqSet, phone, license);
            }
        }, "Sakura-Auth");
        t.setDaemon(true);
        t.start();
    }
}
