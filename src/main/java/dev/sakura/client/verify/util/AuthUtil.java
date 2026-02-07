package dev.sakura.client.verify.util;

import dev.sakura.client.gui.mainmenu.MainMenuScreen;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import dev.sakura.client.verify.client.IRCTransport;
import dev.sakura.niurendeobf.ZKMIndy;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@ZKMIndy
public final class AuthUtil {
    public enum Mode {
        Login,
        Register
    }

    public static final AtomicReference<String> authed = new AtomicReference<>("");
    public static final String AUTH_OK_TOKEN = "SakuraVerifyToken0123456789ABCDE";

    private AuthUtil() {
    }

    public static void startAuth(Mode mode, String username, String password, String license, BiConsumer<Boolean, String> ui) {
        if (ui != null) {
            ui.accept(true, "正在连接验证服务器...");
        }

        final String hwid = HwidUtil.getHWID();
        final Set<String> qqSet = QQUtils.getAllQQ();
        final String phone = "";

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
                            return;
                        }

                        VerificationClient.shutdown();
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
                        long nowTimeWindow = now / 30000L;
                        boolean ok = success && nowTimeWindow == timeWindow;
                        if (ok) {
                            authedFlag.set(true);
                            authed.set(AUTH_OK_TOKEN);
                            AuthState.setAuthed(username, expireAt);
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new MainMenuScreen()));
                            }
                        } else {
                            authed.set("");
                            AuthState.clear();
                            String m = message == null ? "" : message;
                            if (success && nowTimeWindow != timeWindow) {
                                m = "请尝试重新登陆或校准系统时间";
                            }
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
                        long nowTimeWindow = now / 30000L;
                        if (success && nowTimeWindow == timeWindow) {
                            authedFlag.set(true);
                            authed.set(AUTH_OK_TOKEN);
                            AuthState.setAuthed(username, expireAt);
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new MainMenuScreen()));
                            }
                            return;
                        }

                        if (success && nowTimeWindow != timeWindow) {
                            authed.set("");
                            AuthState.clear();
                            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(expireAt), ZoneId.systemDefault());
                            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
                            if (finished.compareAndSet(false, true)) {
                                MinecraftClient.getInstance().execute(() -> {
                                    if (ui != null) {
                                        ui.accept(false, ("注册成功, 请重启客户端 到期时间: " + formattedDate).trim());
                                    }
                                });
                            }
                            MinecraftClient.getInstance().scheduleStop();
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
