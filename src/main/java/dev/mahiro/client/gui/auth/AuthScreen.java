package dev.mahiro.client.gui.auth;

import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.auth.net.AuthClient;
import dev.mahiro.client.auth.net.AuthVerifyResult;
import dev.mahiro.client.gui.theme.SakuraTheme;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.DecelerateAnimation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
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

    private AuthTextField licenseField;
    private AuthTextField usernameField;
    private AuthTextField passwordField;

    private AuthButton primaryButton;
    private AuthButton switchModeButton;
    private AuthButton exitButton;

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
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
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

        Layout l = computeLayout(width, height);
        addDrawable(this::renderNanoBackground);

        float s = l.scale;
        float rx = l.rightX() + ss(10, s);
        float ry = l.y + ss(10, s);
        float rw = l.rightW() - ss(20, s);
        float rh = l.h - ss(20, s);

        int formX = (int) (rx + ss(22, s));
        int formW = (int) (rw - ss(44, s));

        int fieldsTop = (int) (ry + ss(92, s));
        int fieldH = Math.max(24, ss(34, s));
        int fieldGap = Math.max(6, ss(12, s));

        if (mode == Mode.Register && !client.getWindow().isFullscreen()) {
            fieldH = Math.max(22, Math.round(fieldH * 0.92f));
            fieldGap = Math.max(5, Math.round(fieldGap * 0.9f));
            fieldsTop -= ss(6, s);

            int hintY = (int) ((ry + rh - Math.max(12, ss(22, s)) - Math.max(18, ss(22, s))) - ss(10, s) - Math.max(24, ss(34, s)) - ss(14, s));
            int maxBottom = hintY - ss(10, s);
            int licenseBottom = fieldsTop + (fieldH + fieldGap) * 2 + fieldH;
            if (licenseBottom > maxBottom) {
                fieldsTop -= (licenseBottom - maxBottom);
            }
        }

        usernameField = new AuthTextField(textRenderer, formX, fieldsTop, formW, fieldH, Text.of(""));
        usernameField.setPlaceholder("用户名");
        String usernameToSet = mode == Mode.Login ? loginUsername : registerUsername;
        if (usernameToSet != null && !usernameToSet.isEmpty()) usernameField.setText(usernameToSet);
        addDrawableChild(usernameField);

        passwordField = new AuthTextField(textRenderer, formX, fieldsTop + (fieldH + fieldGap), formW, fieldH, Text.of(""));
        passwordField.setPlaceholder("密码");
        String passwordToSet = mode == Mode.Login ? loginPassword : registerPassword;
        if (passwordToSet != null && !passwordToSet.isEmpty()) passwordField.setText(passwordToSet);
        addDrawableChild(passwordField);

        if (mode == Mode.Register) {
            licenseField = new AuthTextField(textRenderer, formX, fieldsTop + (fieldH + fieldGap) * 2, formW, fieldH, Text.of(""));
            licenseField.setPlaceholder("卡密");
            licenseField.setMaxLength(128);
            String licenseToSet = registerLicense == null || registerLicense.isBlank() ? AuthGate.getLicenseKey() : registerLicense;
            if (licenseToSet != null && !licenseToSet.isBlank()) licenseField.setText(licenseToSet);
            addDrawableChild(licenseField);
        } else {
            licenseField = null;
        }

        int primaryH = Math.max(24, ss(34, s));
        int switchH = Math.max(18, ss(22, s));
        int bottomPad = Math.max(12, ss(22, s));
        int switchY = (int) (ry + rh - bottomPad - switchH);
        int primaryY = switchY - ss(10, s) - primaryH;
        primaryButton = new AuthButton(formX, primaryY, formW, primaryH, mode == Mode.Login ? "登录" : "注册", b -> startAuth());
        primaryButton.setVariant(AuthButton.Variant.Primary);
        primaryButton.setDesignMetrics(34f, 12f);

        switchModeButton = new AuthButton(formX, switchY, formW, switchH,
                mode == Mode.Login ? "没有账号？去注册" : "已有账号？去登录",
                b -> {
                    if (verifying) return;
                    saveInputs(mode);
                    mode = mode == Mode.Login ? Mode.Register : Mode.Login;
                    init();
                });
        switchModeButton.setVariant(AuthButton.Variant.Ghost);
        switchModeButton.setDesignMetrics(22f, 10f);

        int exitW = Math.max(36, ss(46, s));
        int exitH = Math.max(18, ss(24, s));
        exitButton = new AuthButton(l.x + l.w - ss(16, s) - exitW, l.y + ss(16, s), exitW, exitH, "退出", b -> {
            MinecraftClient c = this.client != null ? this.client : MinecraftClient.getInstance();
            if (c != null) c.scheduleStop();
        });
        exitButton.setVariant(AuthButton.Variant.DangerGhost);
        exitButton.setDesignMetrics(24f, 10f);

        addDrawableChild(primaryButton);
        addDrawableChild(switchModeButton);
        addDrawableChild(exitButton);

        applyVerifyingState(verifying, statusLine == null ? "" : statusLine);
    }

    @Override
    public void tick() {
        super.tick();
        if (primaryButton == null) return;

        boolean userOk = usernameField != null && usernameField.getText() != null && !usernameField.getText().isBlank();
        boolean passOk = passwordField != null && passwordField.getText() != null && !passwordField.getText().isBlank();
        boolean licenseOk = mode != Mode.Register || (licenseField != null && licenseField.getText() != null && !licenseField.getText().isBlank());

        primaryButton.active = !verifying && userOk && passOk && licenseOk;
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
            case "BAD_REQUEST" -> "请求参数错误";
            case "BAD_TIMESTAMP" -> "请求已过期，请重试";
            case "BAD_CREDENTIALS" -> "账号或密码错误";
            case "USER_NOT_FOUND" -> "账号不存在";
            case "USER_EXISTS" -> "账号已存在";
            case "USER_CREATE_FAILED" -> "创建账号失败";
            case "LICENSE_KEY_EMPTY" -> "卡密为空";
            case "LICENSE_NOT_FOUND" -> "卡密不存在";
            case "NO_TOKEN" -> "服务器未下发令牌";
            case "TOKEN_EMPTY" -> "令牌为空";
            case "TOKEN_EXPIRED" -> "令牌已过期";
            case "DECODE_FAILED" -> "响应解析失败";
            case "INTERNAL_ERROR" -> "服务器内部错误";
            case "UNSUPPORTED_MEDIA_TYPE" -> "请求格式不支持";
            case "DEVICE_ID_EMPTY" -> "设备码为空";
            case "LICENSE_REVOKED" -> "操你妈滚";
            case "LICENSE_EXPIRED" -> "卡密已过期";
            case "LICENSE_ALREADY_CLAIMED" -> "卡密已被绑定";
            case "NO_LICENSE_BOUND" -> "账号未绑定卡密";
            case "LICENSE_NOT_OWNED" -> "该卡密不属于此账号";
            case "DEVICE_NOT_BOUND" -> "未绑定机器码";
            case "DEVICE_MISMATCH" -> "机器码不匹配";
            case "INVALID_TOKEN" -> "令牌无效";
            case "DENIED" -> "验证失败";
            case "EMPTY_RESPONSE" -> "服务器无响应";
            case "INVALID_RESPONSE" -> "服务器响应异常";
            default -> "未知错误";
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

    private void startAuth() {
        if (verifying) return;

        long attempt = ++verifyAttemptSeq;
        String username = usernameField != null ? usernameField.getText() : "";
        String password = passwordField != null ? passwordField.getText() : "";

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            applyVerifyingState(false, "请输入用户名和密码");
            if (usernameField != null) usernameField.pulseError();
            if (passwordField != null) passwordField.pulseError();
            return;
        }

        String deviceId = AuthGate.getDeviceId();

        CompletableFuture<AuthVerifyResult> fut;
        if (mode == Mode.Register) {
            String license = licenseField == null ? "" : licenseField.getText();
            if (license == null || license.isBlank()) {
                applyVerifyingState(false, "请输入卡密");
                if (licenseField != null) licenseField.pulseError();
                return;
            }
            String licenseNorm = license.trim().toUpperCase(Locale.ROOT);
            AuthGate.saveLicenseKey(licenseNorm);
            applyVerifyingState(true, "注册中...");
            fut = new AuthClient().register(username, password, licenseNorm, deviceId);
        } else {
            applyVerifyingState(true, "登录中...");
            fut = new AuthClient().login(username, password, deviceId);
        }

        fut.whenComplete((res, err) -> {
            MinecraftClient c = this.client != null ? this.client : MinecraftClient.getInstance();
            if (c == null) return;
            c.execute(() -> {
                if (verifyAttemptSeq != attempt) return;

                if (err != null) {
                    String msg = err.getMessage();
                    if (msg == null || msg.isBlank()) msg = err.getClass().getSimpleName();
                    applyVerifyingState(false, translateStatus(msg));
                    if (usernameField != null) usernameField.pulseError();
                    if (passwordField != null) passwordField.pulseError();
                    if (licenseField != null && mode == Mode.Register) licenseField.pulseError();
                    return;
                }

                if (res == null) {
                    applyVerifyingState(false, "验证失败，请稍后重试");
                    if (usernameField != null) usernameField.pulseError();
                    if (passwordField != null) passwordField.pulseError();
                    if (licenseField != null && mode == Mode.Register) licenseField.pulseError();
                    return;
                }

                if (!res.ok()) {
                    String code = res.error();
                    applyVerifyingState(false, translateStatus(code));
                    if ("BAD_CREDENTIALS".equalsIgnoreCase(code) || "AUTH_FAILED".equalsIgnoreCase(code) || "USER_NOT_FOUND".equalsIgnoreCase(code)) {
                        if (usernameField != null) usernameField.pulseError();
                        if (passwordField != null) passwordField.pulseError();
                    }
                    if (mode == Mode.Register && ("LICENSE_NOT_FOUND".equalsIgnoreCase(code) || "LICENSE_KEY_EMPTY".equalsIgnoreCase(code) || "LICENSE_EXPIRED".equalsIgnoreCase(code) || "LICENSE_REVOKED".equalsIgnoreCase(code) || "LICENSE_ALREADY_CLAIMED".equalsIgnoreCase(code))) {
                        if (licenseField != null) licenseField.pulseError();
                    }
                    return;
                }

                String token = res.token();
                if (token == null || token.isBlank()) {
                    applyVerifyingState(false, translateStatus("NO_TOKEN"));
                    return;
                }

                //applyVerifyingState(true, "验证中...");
                if (primaryButton != null) primaryButton.active = false;

                new AuthClient().verifyToken(token, deviceId).whenComplete((vRes, vErr) -> {
                    MinecraftClient c2 = this.client != null ? this.client : MinecraftClient.getInstance();
                    if (c2 == null) return;
                    c2.execute(() -> {
                        if (verifyAttemptSeq != attempt) return;

                        if (vErr != null) {
                            String msg = vErr.getMessage();
                            if (msg == null || msg.isBlank()) msg = vErr.getClass().getSimpleName();
                            applyVerifyingState(false, translateStatus(msg));
                            return;
                        }

                        if (vRes == null || !vRes.ok()) {
                            String code = vRes != null ? vRes.error() : "DENIED";
                            applyVerifyingState(false, translateStatus(code));
                            return;
                        }

                        applyVerifyingState(false, "");
                        AuthGate.requestMainMenuIntro();
                        AuthGate.acceptVerifiedToken(token);
                        c2.setScreen(parent);
                    });
                });
            });
        });
    }

    private void applyVerifyingState(boolean v, String status) {
        verifying = v;
        statusLine = status == null ? "" : status;

        if (primaryButton != null) primaryButton.setLoading(v);
        if (primaryButton != null) primaryButton.active = !v;
        if (switchModeButton != null) switchModeButton.active = !v;
        if (exitButton != null) exitButton.active = !v;

        if (usernameField != null) usernameField.setEditable(!v);
        if (passwordField != null) passwordField.setEditable(!v);
        if (licenseField != null) licenseField.setEditable(!v);
    }

    private void renderNanoBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        int w = context.getScaledWindowWidth();
        int h = context.getScaledWindowHeight();
        Layout l = computeLayout(w, h);
        float s = l.scale;

        String headline = mode == Mode.Login ? "欢迎回来" : "创建账号";
        String subtitle = mode == Mode.Login ? "请先完成身份验证以继续使用客户端" : "注册后需要验证令牌才能继续";

        String statusText;
        if (statusLine == null || statusLine.isBlank()) {
            statusText = mode == Mode.Login ? "输入用户名与密码" : "输入用户名、密码与卡密";
        } else {
            statusText = statusLine;
        }

        Color statusColor;
        if (verifying) {
            statusColor = withAlpha(SakuraTheme.ACCENT, 220);
        } else if (statusLine == null || statusLine.isBlank()) {
            statusColor = withAlpha(new Color(255, 255, 255), 140);
        } else {
            statusColor = withAlpha(SakuraTheme.DANGER, 220);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color bg0 = new Color(10, 10, 14, 240);
            Color bg1 = new Color(18, 18, 24, 240);
            NanoVGHelper.drawGradientRRect(0, 0, w, h, 0, bg0, bg1);
            NanoVGHelper.drawGradientRRect2(0, 0, w, h, 0, new Color(92, 124, 255, 18), new Color(236, 72, 153, 0));

            float t = (System.currentTimeMillis() % 12000L) / 12000.0f;
            float cx = l.x + l.w * (0.18f + 0.06f * (float) Math.sin(t * Math.PI * 2));
            float cy = l.y + l.h * (0.25f + 0.08f * (float) Math.cos(t * Math.PI * 2));
            NanoVGHelper.drawCircle(cx, cy, l.w * 0.38f, withAlpha(SakuraTheme.ACCENT, 24));
            NanoVGHelper.drawCircle(cx + l.w * 0.14f, cy + l.h * 0.22f, l.w * 0.28f, withAlpha(new Color(236, 72, 153), 16));

            float r = 22f * s;
            NanoVGHelper.drawShadow(l.x, l.y, l.w, l.h, r, new Color(0, 0, 0, 120), 34, 0, 16);
            NanoVGHelper.drawRoundRect(l.x, l.y, l.w, l.h, r, new Color(18, 18, 24, 238));
            NanoVGHelper.drawRoundRectOutline(l.x, l.y, l.w, l.h, r, 1.0f, new Color(255, 255, 255, 26));

            float leftR = 18f * s;
            float lx = l.x + ss(10, s);
            float ly = l.y + ss(10, s);
            float lw = l.leftW() - ss(20, s);
            float lh = l.h - ss(20, s);

            Color a0 = withAlpha(SakuraTheme.ACCENT, 255);
            Color a1 = withAlpha(new Color(140, 92, 255), 255);
            Color a2 = withAlpha(new Color(236, 72, 153), 255);
            NanoVGHelper.drawGradientRRect(lx, ly, lw, lh, leftR, withAlpha(a0, 228), withAlpha(a1, 212));
            NanoVGHelper.drawCircle(lx + lw * 0.18f, ly + lh * 0.18f, lw * 0.55f, withAlpha(a2, 22));
            NanoVGHelper.drawCircle(lx + lw * 0.62f, ly + lh * 0.68f, lw * 0.42f, withAlpha(new Color(255, 255, 255), 12));
            NanoVGHelper.drawRoundRectOutline(lx, ly, lw, lh, leftR, 1.0f, new Color(255, 255, 255, 28));

            float badgeX = lx + ss(20, s);
            float badgeY = ly + ss(18, s);
            String badgeText = "Mahiro Verification";
            float badgePadX = ss(10, s);
            float badgeH = ss(20, s);
            int badgeFont = Math.max(7, ss(12, s));
            float badgeTextW = NanoVGHelper.getTextWidth(badgeText, FontLoader.bold(badgeFont), badgeFont);
            float badgeW = badgePadX * 2 + badgeTextW;
            float badgeR = ss(10, s);
            NanoVGHelper.drawRoundRect(badgeX, badgeY, badgeW, badgeH, badgeR, new Color(0, 0, 0, 46));
            NanoVGHelper.drawRoundRectOutline(badgeX, badgeY, badgeW, badgeH, badgeR, 1f, new Color(255, 255, 255, 26));
            NanoVGHelper.drawString(badgeText, badgeX + badgePadX, badgeY + badgeH / 2f,
                    FontLoader.bold(badgeFont), badgeFont, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, withAlpha(new Color(255, 255, 255), 190));

            int headlineFont = Math.max(12, ss(30, s));
            int subtitleFont = Math.max(8, ss(14, s));
            NanoVGHelper.drawString(headline, lx + ss(20, s), ly + ss(76, s),
                    FontLoader.bold(headlineFont), headlineFont, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, withAlpha(new Color(255, 255, 255), 240));
            NanoVGHelper.drawString(subtitle, lx + ss(20, s), ly + ss(104, s),
                    FontLoader.regular(subtitleFont), subtitleFont, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, withAlpha(new Color(255, 255, 255), 170));

            float rx = l.rightX() + ss(10, s);
            float ry = l.y + ss(10, s);
            float rw = l.rightW() - ss(20, s);
            float rh = l.h - ss(20, s);
            float rightR = 18f * s;
            NanoVGHelper.drawRoundRect(rx, ry, rw, rh, rightR, new Color(0, 0, 0, 38));
            NanoVGHelper.drawRoundRectOutline(rx, ry, rw, rh, rightR, 1f, new Color(255, 255, 255, 22));

            int rightTitleFont = Math.max(10, ss(20, s));
            int rightSubFont = Math.max(8, ss(13, s));
            NanoVGHelper.drawString(mode == Mode.Login ? "登录" : "注册",
                    rx + ss(22, s), ry + ss(40, s),
                    FontLoader.bold(rightTitleFont), rightTitleFont, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, withAlpha(new Color(255, 255, 255), 230));
            NanoVGHelper.drawString(mode == Mode.Login ? "输入凭据以继续" : "填写信息以创建账号",
                    rx + ss(22, s), ry + ss(62, s),
                    FontLoader.regular(rightSubFont), rightSubFont, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, withAlpha(new Color(255, 255, 255), 150));

            float hintX = primaryButton != null ? primaryButton.getX() : (rx + 22);
            float hintY = primaryButton != null ? (primaryButton.getY() - ss(14, s)) : (ry + rh - ss(88, s));
            NanoVGHelper.drawString(statusText,
                    hintX, hintY,
                    FontLoader.regular(Math.max(7, ss(12, s))), Math.max(7, ss(12, s)), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, statusColor);
        });
    }

    private static Layout computeLayout(int w, int h) {
        float designW = 820f;
        float designH = 460f;

        float margin = 18f;
        float availW = Math.max(1f, w - margin * 2);
        float availH = Math.max(1f, h - margin * 2);

        float scale = Math.min(1f, Math.min(availW / designW, availH / designH));
        scale = MathHelper.clamp(scale, 0.15f, 1.0f);

        int outerW = Math.max(1, Math.round(designW * scale));
        int outerH = Math.max(1, Math.round(designH * scale));
        outerW = Math.min(outerW, w);
        outerH = Math.min(outerH, h);

        int x = (w - outerW) / 2;
        int y = (h - outerH) / 2;

        int pad = Math.max(8, Math.round(26f * scale));
        int leftW = Math.round(outerW * 0.44f);

        int minLeft = Math.max(160, Math.round(220f * scale));
        int minRight = Math.max(180, Math.round(240f * scale));
        leftW = MathHelper.clamp(leftW, minLeft, Math.max(minLeft, outerW - minRight));

        return new Layout(x, y, outerW, outerH, pad, leftW, scale);
    }

    private record Layout(int x, int y, int w, int h, int pad, int leftW, float scale) {
        float rightX() {
            return x + leftW;
        }

        float rightW() {
            return w - leftW;
        }
    }

    private static int ss(int px, float s) {
        return Math.max(1, Math.round(px * s));
    }

    private static Color withAlpha(Color c, int alpha) {
        int a = MathHelper.clamp(alpha, 0, 255);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
    }

    private static final class AuthTextField extends TextFieldWidget {
        private String placeholderText = "";
        private final Animation hoverAnim = new DecelerateAnimation(180, 1.0, Direction.BACKWARDS);
        private final Animation focusAnim = new DecelerateAnimation(220, 1.0, Direction.BACKWARDS);
        private final Animation errorAnim = new DecelerateAnimation(260, 1.0, Direction.BACKWARDS);
        private long errorUntilMs;
        private long errorStartMs;
        private int scrollStart;

        public AuthTextField(net.minecraft.client.font.TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
            super(textRenderer, x, y, width, height, message);
            this.setDrawsBackground(false);
        }

        public void setPlaceholder(String text) {
            this.placeholderText = text == null ? "" : text;
            this.setPlaceholder(Text.of(this.placeholderText));
        }

        public void pulseError() {
            errorStartMs = System.currentTimeMillis();
            errorUntilMs = errorStartMs + 900L;
            errorAnim.setDirection(Direction.FORWARDS);
            errorAnim.reset();
        }

        @Override
        public boolean mouseClicked(Click click, boolean doubled) {
            if (click.button() != 0) return super.mouseClicked(click, doubled);
            if (!this.active || !this.visible) return super.mouseClicked(click, doubled);

            boolean hovered = click.x() >= getX() && click.x() <= getX() + getWidth() && click.y() >= getY() && click.y() <= getY() + getHeight();
            if (!hovered) return super.mouseClicked(click, doubled);

            setFocused(true);
            setSelectionStart(getCursor());

            int fontSize = getFontSize();
            VisibleText visible = computeVisibleText(getInnerAvailableWidth(), fontSize);
            double localX = click.x() - (getX() + 12);
            if (localX <= 0) {
                int idx = MathHelper.clamp(visible.start, 0, visible.display.length());
                setCursor(idx, false);
                setSelectionStart(idx);
                return true;
            }

            int best = visible.start;
            int max = visible.text.length();
            float prevW = 0f;
            boolean decided = false;
            for (int i = 1; i <= max; i++) {
                String prefix = visible.text.substring(0, i);
                float w = NanoVGHelper.getTextWidth(prefix, FontLoader.regular(fontSize), fontSize);
                if (localX < w) {
                    float mid = (prevW + w) * 0.5f;
                    int localPos = localX < mid ? (i - 1) : i;
                    best = visible.start + localPos;
                    decided = true;
                    break;
                }
                prevW = w;
            }
            if (!decided) {
                best = visible.start + max;
            }

            best = MathHelper.clamp(best, 0, visible.display.length());
            setCursor(best, false);
            setSelectionStart(best);
            return true;
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            boolean hovered = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
            hoverAnim.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
            focusAnim.setDirection(isFocused() ? Direction.FORWARDS : Direction.BACKWARDS);

            long now = System.currentTimeMillis();
            boolean errorActive = now <= errorUntilMs;
            errorAnim.setDirection(errorActive ? Direction.FORWARDS : Direction.BACKWARDS);

            NanoVGRenderer.INSTANCE.draw(vg -> {
                float hoverT = MathHelper.clamp(hoverAnim.getOutput().floatValue(), 0f, 1f);
                float focusT = MathHelper.clamp(focusAnim.getOutput().floatValue(), 0f, 1f);
                float errorT = MathHelper.clamp(errorAnim.getOutput().floatValue(), 0f, 1f);

                float shake = 0.0f;
                if (errorActive) {
                    float p = (now - errorStartMs) / 900.0f;
                    p = MathHelper.clamp(p, 0f, 1f);
                    float amp = (1.0f - p) * 2.2f;
                    shake = (float) Math.sin((now - errorStartMs) / 28.0) * amp;
                }

                float x = getX() + shake;
                float y = getY();
                float w = getWidth();
                float h = getHeight();
                float r = (h / 34.0f) * 12.0f;

                Color fill = new Color(255, 255, 255, (int) (12 + 10 * hoverT + 8 * focusT));
                NanoVGHelper.drawRoundRect(x, y, w, h, r, fill);

                Color baseBorder = new Color(255, 255, 255, (int) (26 + 20 * hoverT));
                Color focusBorder = withAlpha(SakuraTheme.ACCENT, (int) (60 + 140 * focusT));
                Color errorBorder = withAlpha(SakuraTheme.DANGER, (int) (40 + 180 * errorT));

                Color border = mixColors(baseBorder, focusBorder, focusT);
                border = mixColors(border, errorBorder, errorT);
                NanoVGHelper.drawRoundRectOutline(x, y, w, h, r, 1.1f, border);

                NanoVG.nvgScissor(vg, x + 10, y, w - 20, h);

                int fontSize = getFontSize();
                VisibleText visible = computeVisibleText(getInnerAvailableWidth(), fontSize);

                if (visible.text.isEmpty() && !isFocused() && !placeholderText.isEmpty()) {
                    NanoVGHelper.drawString(placeholderText, x + 12, y + h / 2f, FontLoader.regular(fontSize), fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(255, 255, 255, 110));
                } else {
                    NanoVGHelper.drawString(visible.text, x + 12, y + h / 2f, FontLoader.regular(fontSize), fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(255, 255, 255, 210));

                    if (isFocused() && (System.currentTimeMillis() / 500) % 2 == 0) {
                        int cursor = getCursor();
                        cursor = MathHelper.clamp(cursor, 0, visible.display.length());
                        int localCursor = MathHelper.clamp(cursor - visible.start, 0, visible.text.length());
                        String beforeCursor = visible.text.substring(0, localCursor);
                        float textWidth = NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(fontSize), fontSize);
                        NanoVG.nvgBeginPath(vg);
                        NanoVG.nvgMoveTo(vg, x + 12 + textWidth + 1, y + 7);
                        NanoVG.nvgLineTo(vg, x + 12 + textWidth + 1, y + h - 7);
                        NanoVG.nvgStrokeColor(vg, SakuraTheme.color(withAlpha(new Color(255, 255, 255), 230)));
                        NanoVG.nvgStrokeWidth(vg, 1.0f);
                        NanoVG.nvgStroke(vg);
                    }
                }

                NanoVG.nvgResetScissor(vg);
            });
        }

        private int getInnerAvailableWidth() {
            return Math.max(0, getWidth() - 24);
        }

        private int getFontSize() {
            return MathHelper.clamp(Math.round(getHeight() * 0.44f), 8, 18);
        }

        private VisibleText computeVisibleText(int availableWidth, int fontSize) {
            String display = getText() == null ? "" : getText();

            int len = display.length();
            int cursor = MathHelper.clamp(getCursor(), 0, len);

            scrollStart = MathHelper.clamp(scrollStart, 0, len);
            if (cursor < scrollStart) scrollStart = cursor;

            if (availableWidth <= 0 || len == 0) {
                return new VisibleText(scrollStart, "", display);
            }

            float totalW = NanoVGHelper.getTextWidth(display, FontLoader.regular(fontSize), fontSize);
            if (totalW <= availableWidth) {
                scrollStart = 0;
                return new VisibleText(0, display, display);
            }

            while (scrollStart < cursor) {
                String beforeCursor = display.substring(scrollStart, cursor);
                float w = NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(fontSize), fontSize);
                if (w <= availableWidth) break;
                scrollStart++;
            }

            while (scrollStart > 0) {
                String beforeCursor = display.substring(scrollStart - 1, cursor);
                float w = NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(fontSize), fontSize);
                if (w > availableWidth) break;
                scrollStart--;
            }

            int end = len;
            while (end > scrollStart) {
                String s = display.substring(scrollStart, end);
                float w = NanoVGHelper.getTextWidth(s, FontLoader.regular(fontSize), fontSize);
                if (w <= availableWidth) {
                    return new VisibleText(scrollStart, s, display);
                }
                end--;
            }

            return new VisibleText(scrollStart, "", display);
        }

        private record VisibleText(int start, String text, String display) {
        }

        private static Color mixColors(Color a, Color b, float t) {
            t = MathHelper.clamp(t, 0f, 1f);
            int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
            int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
            int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
            int al = (int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t);
            return new Color(
                    MathHelper.clamp(r, 0, 255),
                    MathHelper.clamp(g, 0, 255),
                    MathHelper.clamp(bl, 0, 255),
                    MathHelper.clamp(al, 0, 255)
            );
        }
    }

    private static class AuthButton extends ButtonWidget {
        enum Variant {
            Primary,
            Ghost,
            DangerGhost
        }

        private Variant variant = Variant.Primary;
        private final Animation hoverAnim = new DecelerateAnimation(200, 1.0);
        private final Animation pressAnim = new DecelerateAnimation(120, 1.0, Direction.BACKWARDS);
        private final Animation loadingAnim = new DecelerateAnimation(160, 1.0, Direction.BACKWARDS);
        private boolean pressed;
        private boolean loading;
        private long loadingStartMs;
        private float designHeight;
        private float designRadius;

        public AuthButton(int x, int y, int width, int height, String message, PressAction onPress) {
            super(x, y, width, height, net.minecraft.text.Text.of(message), onPress, DEFAULT_NARRATION_SUPPLIER);
            this.designHeight = Math.max(1f, height);
            this.designRadius = height >= 30 ? 12f : 10f;
        }

        public void setVariant(Variant variant) {
            this.variant = variant == null ? Variant.Primary : variant;
        }

        public void setDesignMetrics(float designHeight, float designRadius) {
            this.designHeight = Math.max(1f, designHeight);
            this.designRadius = Math.max(0f, designRadius);
        }

        public void setLoading(boolean loading) {
            if (this.loading == loading) return;
            this.loading = loading;
            this.loadingStartMs = System.currentTimeMillis();
            this.loadingAnim.setDirection(loading ? Direction.FORWARDS : Direction.BACKWARDS);
            this.loadingAnim.reset();
        }

        @Override
        public void onClick(Click click, boolean doubled) {
            boolean hovered = click.x() >= getX() && click.x() <= getX() + width && click.y() >= getY() && click.y() <= getY() + height;
            if (click.button() == 0 && hovered && this.active && this.visible) {
                pressed = true;
                pressAnim.setDirection(Direction.FORWARDS);
            }
        }

        @Override
        public void onRelease(Click click) {
            if (click.button() == 0) {
                pressed = false;
                pressAnim.setDirection(Direction.BACKWARDS);
            }
        }

        @Override
        protected void drawIcon(DrawContext context, int mouseX, int mouseY, float delta) {
            boolean hovered = mouseX >= getX() && mouseX <= getX() + width && mouseY >= getY() && mouseY <= getY() + height;
            hoverAnim.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
            if (!hovered && pressed) {
                pressed = false;
                pressAnim.setDirection(Direction.BACKWARDS);
            }
            if (!loadingAnim.finished(loading ? Direction.FORWARDS : Direction.BACKWARDS)) {
                loadingAnim.setDirection(loading ? Direction.FORWARDS : Direction.BACKWARDS);
            }

            NanoVGRenderer.INSTANCE.draw(vg -> {
                float hoverT = MathHelper.clamp(hoverAnim.getOutput().floatValue(), 0f, 1f);
                float pressT = MathHelper.clamp(pressAnim.getOutput().floatValue(), 0f, 1f);
                float loadingT = MathHelper.clamp(loadingAnim.getOutput().floatValue(), 0f, 1f);

                float scale = 1.0f + 0.02f * hoverT - 0.02f * pressT;
                float cx = getX() + width / 2.0f;
                float cy = getY() + height / 2.0f;

                NanoVG.nvgSave(vg);
                NanoVG.nvgTranslate(vg, cx, cy);
                NanoVG.nvgScale(vg, scale, scale);
                NanoVG.nvgTranslate(vg, -cx, -cy);

                float r = designRadius * (height / designHeight);
                r = Math.max(1f, r);

                Color fill;
                Color border;
                Color text;

                if (!this.active) {
                    fill = new Color(255, 255, 255, 10);
                    border = new Color(255, 255, 255, 18);
                    text = new Color(255, 255, 255, 120);
                } else if (variant == Variant.Primary) {
                    fill = withAlpha(SakuraTheme.ACCENT, (int) (220 + 25 * hoverT));
                    border = withAlpha(new Color(255, 255, 255), (int) (28 + 32 * hoverT));
                    text = new Color(12, 12, 16, 230);
                } else if (variant == Variant.DangerGhost) {
                    fill = withAlpha(SakuraTheme.DANGER, (int) (10 + 26 * hoverT));
                    border = withAlpha(SakuraTheme.DANGER, (int) (55 + 60 * hoverT));
                    text = withAlpha(SakuraTheme.DANGER, 220);
                } else {
                    fill = new Color(255, 255, 255, (int) (8 + 16 * hoverT));
                    border = new Color(255, 255, 255, (int) (22 + 26 * hoverT));
                    text = new Color(255, 255, 255, 190);
                }

                NanoVGHelper.drawRoundRect(getX(), getY(), width, height, r, fill);
                NanoVGHelper.drawRoundRectOutline(getX(), getY(), width, height, r, 1.0f, border);

                NanoVG.nvgScissor(vg, getX() + 6, getY() + 2, Math.max(0, width - 12), Math.max(0, height - 4));

                float textX = cx;
                if (loadingT > 0.001f) textX -= (height * 0.22f) * loadingT;

                int fontSize = MathHelper.clamp(Math.round(height * 0.44f), 8, 18);
                NanoVG.nvgFontSize(vg, fontSize);
                NanoVG.nvgFontFaceId(vg, FontLoader.medium(fontSize));
                NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
                NanoVG.nvgFillColor(vg, SakuraTheme.color(text));
                NanoVG.nvgText(vg, textX, cy + 0.5f, getMessage().getString());

                if (loadingT > 0.001f) {
                    float rr = MathHelper.clamp(height * 0.16f, 3.0f, 6.5f);
                    float sx = getX() + width - (height * 0.55f);
                    float sy = cy + 0.5f;
                    float tt = ((System.currentTimeMillis() - loadingStartMs) / 1000.0f) * 6.0f;
                    float start = tt;
                    float end = tt + 4.4f;

                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgArc(vg, sx, sy, rr, start, end, NanoVG.NVG_CW);
                    NanoVG.nvgStrokeWidth(vg, MathHelper.clamp(height * 0.05f, 1.15f, 2.0f));
                    NanoVG.nvgStrokeColor(vg, SakuraTheme.color(withAlpha(new Color(255, 255, 255), (int) (180 * loadingT))));
                    NanoVG.nvgStroke(vg);
                }

                NanoVG.nvgResetScissor(vg);
                NanoVG.nvgRestore(vg);
            });
        }
    }
}
