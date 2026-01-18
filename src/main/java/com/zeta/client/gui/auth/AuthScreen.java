package com.zeta.client.gui.auth;

import com.zeta.client.auth.AuthGate;
import com.zeta.client.auth.net.AuthClient;
import com.zeta.client.auth.net.AuthVerifyResult;
import com.zeta.client.gui.component.SakuraButton;
import com.zeta.client.gui.component.SakuraTextField;
import com.zeta.client.gui.theme.SakuraTheme;
import com.zeta.client.nanovg.NanoVGRenderer;
import com.zeta.client.nanovg.font.FontLoader;
import com.zeta.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class AuthScreen extends Screen {
    private final Screen parent;

    private enum Mode {
        Login,
        Register
    }

    private Mode mode = Mode.Login;
    private Mode lastInitMode;
    private String loginUsername = "";
    private String loginPassword = "";
    private String registerUsername = "";
    private String registerPassword = "";
    private String registerLicense = "";

    private SakuraTextField licenseField;
    private SakuraTextField usernameField;
    private SakuraTextField passwordField;

    private SakuraButton loginTabButton;
    private SakuraButton registerTabButton;
    private SakuraButton verifyButton;
    private SakuraButton exitButton;

    private String statusLine = "";
    private volatile boolean verifying;
    private volatile long verifyAttemptSeq;

    public AuthScreen(Screen parent) {
        super(Text.of("Auth"));
        this.parent = parent;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        Mode prevMode = lastInitMode != null ? lastInitMode : mode;
        saveInputs(prevMode);

        boolean prevVerifying = verifying;
        String prevStatusLine = statusLine;
        boolean modeChanged = lastInitMode != null && lastInitMode != mode;
        lastInitMode = mode;

        if (modeChanged) {
            prevVerifying = false;
            prevStatusLine = "";
        }

        verifying = prevVerifying;
        statusLine = prevStatusLine == null ? "" : prevStatusLine;
        clearChildren();

        int panelWidth = Math.min(540, width - 40);
        int panelHeight = 376;
        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        int tabY = panelY + 58;
        int tabW = (panelWidth - 20 * 2 - 8) / 2;

        loginTabButton = new SakuraButton(panelX + 20, tabY, tabW, 22, "登录", button -> {
            saveInputs(mode);
            mode = Mode.Login;
            init();
        });
        registerTabButton = new SakuraButton(panelX + 20 + tabW + 8, tabY, tabW, 22, "注册", button -> {
            saveInputs(mode);
            mode = Mode.Register;
            init();
        });
        loginTabButton.setSelected(mode == Mode.Login);
        registerTabButton.setSelected(mode == Mode.Register);

        addDrawableChild(loginTabButton);
        addDrawableChild(registerTabButton);

        int fieldY = panelY + 92;
        int fieldH = 24;
        int fieldGap = 10;

        usernameField = new SakuraTextField(textRenderer, panelX + 20, fieldY, panelWidth - 40, fieldH, Text.of(""));
        usernameField.setPlaceholder("用户名");
        String usernameToSet = mode == Mode.Login ? loginUsername : registerUsername;
        if (usernameToSet != null && !usernameToSet.isEmpty()) usernameField.setText(usernameToSet);
        addDrawableChild(usernameField);

        passwordField = new SakuraTextField(textRenderer, panelX + 20, fieldY + (fieldH + fieldGap), panelWidth - 40, fieldH, Text.of(""));
        passwordField.setPlaceholder("密码");
        String passwordToSet = mode == Mode.Login ? loginPassword : registerPassword;
        if (passwordToSet != null && !passwordToSet.isEmpty()) passwordField.setText(passwordToSet);
        addDrawableChild(passwordField);

        if (mode == Mode.Register) {
            licenseField = new SakuraTextField(textRenderer, panelX + 20, fieldY + (fieldH + fieldGap) * 2, panelWidth - 40, fieldH, Text.of(""));
            licenseField.setPlaceholder("卡密");
            licenseField.setMaxLength(128);
            String licenseToSet = registerLicense == null || registerLicense.isBlank() ? AuthGate.getLicenseKey() : registerLicense;
            if (licenseToSet != null && !licenseToSet.isBlank()) licenseField.setText(licenseToSet);
            addDrawableChild(licenseField);
        } else {
            licenseField = null;
        }

        int buttonY = panelY + panelHeight - 44;

        verifyButton = new SakuraButton(panelX + 20, buttonY - 60, panelWidth - 40, 24, mode == Mode.Login ? "登录" : "注册", button -> {
            if (verifying) return;
            long attempt = ++verifyAttemptSeq;
            verifying = true;
            statusLine = mode == Mode.Login ? "登录中..." : "注册中...";
            verifyButton.setLoading(true);
            loginTabButton.active = false;
            registerTabButton.active = false;
            usernameField.setEditable(false);
            passwordField.setEditable(false);
            if (licenseField != null) licenseField.setEditable(false);
            exitButton.active = false;

            String deviceId = AuthGate.getDeviceId();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                verifying = false;
                statusLine = "请输入用户名和密码";
                verifyButton.setLoading(false);
                loginTabButton.active = true;
                registerTabButton.active = true;
                usernameField.setEditable(true);
                passwordField.setEditable(true);
                if (licenseField != null) licenseField.setEditable(true);
                exitButton.active = true;
                usernameField.pulseError();
                passwordField.pulseError();
                return;
            }

            CompletableFuture<AuthVerifyResult> fut;
            if (mode == Mode.Register) {
                String license = licenseField == null ? "" : licenseField.getText();
                if (license == null || license.isBlank()) {
                    verifying = false;
                    statusLine = "请输入卡密";
                    verifyButton.setLoading(false);
                    loginTabButton.active = true;
                    registerTabButton.active = true;
                    usernameField.setEditable(true);
                    passwordField.setEditable(true);
                    if (licenseField != null) licenseField.setEditable(true);
                    exitButton.active = true;
                    if (licenseField != null) licenseField.pulseError();
                    return;
                }
                String licenseNorm = license.trim().toUpperCase(java.util.Locale.ROOT);
                AuthGate.saveLicenseKey(licenseNorm);
                fut = new AuthClient().register(username, password, licenseNorm, deviceId);
            } else {
                fut = new AuthClient().login(username, password, deviceId);
            }

            fut.whenComplete((res, err) -> {
                MinecraftClient c = this.client != null ? this.client : MinecraftClient.getInstance();
                if (c == null) return;
                c.execute(() -> {
                    if (verifyAttemptSeq != attempt) return;
                    verifying = false;
                    verifyButton.setLoading(false);
                    loginTabButton.active = true;
                    registerTabButton.active = true;
                    usernameField.setEditable(true);
                    passwordField.setEditable(true);
                    if (licenseField != null) licenseField.setEditable(true);
                    exitButton.active = true;
                    if (err != null) {
                        String msg = err.getMessage();
                        if (msg == null || msg.isBlank()) msg = err.getClass().getSimpleName();
                        statusLine = translateStatus(msg);
                        usernameField.pulseError();
                        passwordField.pulseError();
                        if (licenseField != null && mode == Mode.Register) {
                            licenseField.pulseError();
                        }
                        return;
                    }
                    if (res == null) {
                        statusLine = "验证失败，请稍后重试";
                        usernameField.pulseError();
                        passwordField.pulseError();
                        if (licenseField != null && mode == Mode.Register) {
                            licenseField.pulseError();
                        }
                        return;
                    }
                    if (!res.ok()) {
                        String code = res.error();
                        statusLine = translateStatus(code);
                        if ("BAD_CREDENTIALS".equalsIgnoreCase(code) || "AUTH_FAILED".equalsIgnoreCase(code) || "USER_NOT_FOUND".equalsIgnoreCase(code)) {
                            usernameField.pulseError();
                            passwordField.pulseError();
                        }
                        if (mode == Mode.Register && ("LICENSE_NOT_FOUND".equalsIgnoreCase(code) || "LICENSE_KEY_EMPTY".equalsIgnoreCase(code) || "LICENSE_EXPIRED".equalsIgnoreCase(code) || "LICENSE_REVOKED".equalsIgnoreCase(code))) {
                            if (licenseField != null) {
                                licenseField.pulseError();
                            }
                        }
                        return;
                    }
                    String token = res.token();
                    if (token == null || token.isBlank()) {
                        statusLine = translateStatus("NO_TOKEN");
                        return;
                    }

                    verifying = true;
                    statusLine = "验证中...";
                    verifyButton.setLoading(true);
                    verifyButton.active = false;
                    loginTabButton.active = false;
                    registerTabButton.active = false;
                    usernameField.setEditable(false);
                    passwordField.setEditable(false);
                    if (licenseField != null) licenseField.setEditable(false);
                    exitButton.active = false;

                    new AuthClient().verifyToken(token, deviceId).whenComplete((vRes, vErr) -> {
                        MinecraftClient c2 = this.client != null ? this.client : MinecraftClient.getInstance();
                        if (c2 == null) return;
                        c2.execute(() -> {
                            if (verifyAttemptSeq != attempt) return;
                            verifying = false;
                            verifyButton.setLoading(false);
                            verifyButton.active = true;
                            loginTabButton.active = true;
                            registerTabButton.active = true;
                            usernameField.setEditable(true);
                            passwordField.setEditable(true);
                            if (licenseField != null) licenseField.setEditable(true);
                            exitButton.active = true;

                            if (vErr != null) {
                                String msg = vErr.getMessage();
                                if (msg == null || msg.isBlank()) msg = vErr.getClass().getSimpleName();
                                statusLine = translateStatus(msg);
                                return;
                            }
                            if (vRes == null || !vRes.ok()) {
                                String code = vRes != null ? vRes.error() : "DENIED";
                                statusLine = translateStatus(code);
                                return;
                            }

                            AuthGate.requestMainMenuIntro();
                            AuthGate.acceptVerifiedToken(token);
                            c2.setScreen(parent);
                        });
                    });
                });
            });
        });
        verifyButton.setPrimary(true);

        exitButton = new SakuraButton(panelX + 20, buttonY - 30, panelWidth - 40, 24, "退出游戏", button -> {
            MinecraftClient c = this.client != null ? this.client : MinecraftClient.getInstance();
            if (c != null) c.scheduleStop();
        });
        exitButton.setDanger(true);

        addDrawableChild(exitButton);
        addDrawableChild(verifyButton);

        if (verifying) {
            verifyButton.setLoading(true);
            verifyButton.active = false;
            loginTabButton.active = false;
            registerTabButton.active = false;
            usernameField.setEditable(false);
            passwordField.setEditable(false);
            if (licenseField != null) licenseField.setEditable(false);
            exitButton.active = false;
            if (statusLine == null || statusLine.isBlank()) {
                statusLine = mode == Mode.Login ? "登录中..." : "注册中...";
            }
        }

        addDrawable((context, mouseX, mouseY, delta) -> {
            int w = context.getScaledWindowWidth();
            int h = context.getScaledWindowHeight();
            int pw = Math.min(540, w - 40);
            int ph = 376;
            int px = (w - pw) / 2;
            int py = (h - ph) / 2;
            int verifyY = verifyButton != null ? verifyButton.getY() : (py + ph - 44 - 60);
            int statusY = verifyY - 18;

            String statusText;
            if (statusLine == null || statusLine.isBlank()) {
                statusText = mode == Mode.Login ? "请输入用户名和密码" : "请输入用户名、密码和卡密";
            } else {
                statusText = statusLine;
            }

            Color statusColor;
            if (verifying) {
                statusColor = SakuraTheme.ACCENT;
            } else if (statusLine == null || statusLine.isBlank()) {
                statusColor = new Color(SakuraTheme.TEXT_SECONDARY.getRed(), SakuraTheme.TEXT_SECONDARY.getGreen(), SakuraTheme.TEXT_SECONDARY.getBlue(), 150);
            } else {
                statusColor = SakuraTheme.DANGER;
            }

            NanoVGRenderer.INSTANCE.draw(vg -> NanoVGHelper.drawString(
                    statusText,
                    px + 20,
                    statusY,
                    FontLoader.regular(13.0f),
                    13.0f,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                    statusColor
            ));
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (verifyButton == null) return;

        boolean userOk = usernameField != null && usernameField.getText() != null && !usernameField.getText().isBlank();
        boolean passOk = passwordField != null && passwordField.getText() != null && !passwordField.getText().isBlank();
        boolean licenseOk = mode != Mode.Register || (licenseField != null && licenseField.getText() != null && !licenseField.getText().isBlank());

        verifyButton.active = !verifying && userOk && passOk && licenseOk;
    }

    private static String translateStatus(String raw) {
        String s = raw == null ? "" : raw.trim();
        if (s.isEmpty()) return "";

        String upper = s.toUpperCase(Locale.ROOT);
        if (upper.startsWith("HTTP_")) {
            return "服务器请求失败（" + upper + "）";
        }

        String lower = s.toLowerCase(Locale.ROOT);
        if (lower.contains("connection refused") || lower.contains("connectexception")) return "无法连接服务器";
        if (lower.contains("timed out") || lower.contains("timeout")) return "连接超时";
        if (lower.contains("unknownhost")) return "服务器地址无效";

        return switch (upper) {
            case "BAD_CREDENTIALS" -> "账号或密码错误";
            case "USER_NOT_FOUND" -> "账号不存在";
            case "USER_EXISTS" -> "账号已存在";
            case "USER_CREATE_FAILED" -> "创建账号失败";
            case "NO_TOKEN" -> "服务器未下发令牌";
            case "TOKEN_EMPTY" -> "令牌为空";
            case "TOKEN_EXPIRED" -> "令牌已过期";
            case "BAD_SIGNATURE" -> "签名校验失败";
            case "LICENSE_REVOKED" -> "操你妈滚";
            case "LICENSE_EXPIRED" -> "卡密已过期";
            case "LICENSE_ALREADY_CLAIMED" -> "卡密已被绑定";
            case "NO_LICENSE_BOUND" -> "账号未绑定卡密";
            case "LICENSE_NOT_OWNED" -> "该卡密不属于此账号";
            case "DEVICE_NOT_BOUND" -> "未绑定机器码";
            case "DEVICE_MISMATCH" -> "机器码不匹配";
            case "DENIED" -> "验证失败";
            case "EMPTY_RESPONSE" -> "服务器无响应";
            case "INVALID_RESPONSE" -> "服务器响应异常";
            default -> "我们跑路了。";
        };
    }

    private void saveInputs(Mode m) {
        if (m == null) return;
        String u = usernameField != null ? usernameField.getText() : "";
        String p = passwordField != null ? passwordField.getText() : "";
        String lic = licenseField != null ? licenseField.getText() : "";

        if (m == Mode.Login) {
            loginUsername = u == null ? "" : u;
            loginPassword = p == null ? "" : p;
        } else {
            registerUsername = u == null ? "" : u;
            registerPassword = p == null ? "" : p;
            registerLicense = lic == null ? "" : lic;
        }
    }

}
