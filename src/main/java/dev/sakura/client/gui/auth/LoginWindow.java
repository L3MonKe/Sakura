package dev.sakura.client.gui.auth;

import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import dev.sakura.client.verify.client.IRCTransport;
import dev.sakura.client.verify.util.*;
import org.lwjgl.glfw.*;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;

public final class LoginWindow {
    private static final long TIME_WINDOW_MS = 30_000L;
    private static final long MAX_TIME_WINDOW_SKEW = 1L;
    private static final AtomicBoolean gateRan = new AtomicBoolean(false);

    public static void verifyOrExit() {
        if (!gateRan.compareAndSet(false, true)) {
            return;
        }

        if (AuthState.isAuthed() && AuthState.getExpireAt() > System.currentTimeMillis()) {
            return;
        }

        boolean ok = false;
        try {
            ok = new LoginWindow().runBlocking();
        } catch (Throwable ignored) {
        }

        if (!ok) {
            ExitUtil.exit0();
            throw new IllegalStateException("Verification failed");
        }
    }

    private enum Mode {
        Login,
        Register
    }

    private final AtomicBoolean shouldClose = new AtomicBoolean(false);
    private final AtomicBoolean authed = new AtomicBoolean(false);
    private final AtomicBoolean verifying = new AtomicBoolean(false);
    private final AtomicReference<String> status = new AtomicReference<>("");
    private final AtomicReference<StatusKind> statusKind = new AtomicReference<>(StatusKind.Neutral);
    private volatile long verifyStartedAtMs = 0L;
    private volatile long expireAt = 0L;
    private volatile long timeWindow = 0L;
    private volatile String activeUsername = "";

    private long window;
    private long vg;
    private int fontRegular;
    private int fontMedium;
    private int fontBold;
    private int fontCjk;
    private final Map<String, ByteBuffer> fontBuffers = new HashMap<>();

    private int winW = 920;
    private int winH = 540;
    private float pxRatio = 1f;

    private double mouseX;
    private double mouseY;
    private double scrollY;

    private Mode mode = Mode.Login;
    private InputField userField;
    private InputField passField;
    private InputField licenseField;

    private Button primaryButton;
    private Button switchButton;
    private Button closeButton;

    private boolean dragging;
    private double dragStartX;
    private double dragStartY;
    private int dragWinStartX;
    private int dragWinStartY;

    private GLFWCursorPosCallback cursorCb;
    private GLFWMouseButtonCallback mouseBtnCb;
    private GLFWScrollCallback scrollCb;
    private GLFWKeyCallback keyCb;
    private GLFWCharCallback charCb;

    private boolean runBlocking() {
        System.setProperty("java.awt.headless", "false");

        if (!glfwInit()) {
            return false;
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(winW, winH, "Sakura Verification", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            return false;
        }

        centerWindow();

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();
        vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        if (vg == 0L) {
            glfwDestroyWindow(window);
            window = MemoryUtil.NULL;
            return false;
        }

        loadFonts();
        installCallbacks();
        rebuildLayout();

        glfwShowWindow(window);

        double lastTime = glfwGetTime();
        long authedAtMs = 0L;

        while (!glfwWindowShouldClose(window) && !shouldClose.get()) {
            if (updateSizing()) {
                rebuildLayout();
            }

            double now = glfwGetTime();
            float dt = (float) Math.min(0.05, Math.max(0.0, now - lastTime));
            lastTime = now;

            updateHover(dt);
            render(dt);

            glfwSwapBuffers(window);
            glfwPollEvents();

            if (verifying.get() && !authed.get()) {
                long since = verifyStartedAtMs;
                if (since > 0L && System.currentTimeMillis() - since > 12_000L) {
                    verifying.set(false);
                    status.set("连接超时，请重试");
                    statusKind.set(StatusKind.Error);
                    VerificationClient.shutdown();
                }
            }

            if (authed.get()) {
                if (authedAtMs == 0L) authedAtMs = System.currentTimeMillis();
                if (System.currentTimeMillis() - authedAtMs > 550L) {
                    break;
                }
            } else {
                authedAtMs = 0L;
            }
        }

        cleanup();
        return authed.get();
    }

    private void centerWindow() {
        GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vid == null) {
            return;
        }
        int x = (vid.width() - winW) / 2;
        int y = (vid.height() - winH) / 2;
        glfwSetWindowPos(window, Math.max(0, x), Math.max(0, y));
    }

    private boolean updateSizing() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            var pW = stack.mallocInt(1);
            var pH = stack.mallocInt(1);
            glfwGetWindowSize(window, pW, pH);
            int newW = Math.max(320, pW.get(0));
            int newH = Math.max(240, pH.get(0));

            var fbW = stack.mallocInt(1);
            var fbH = stack.mallocInt(1);
            glfwGetFramebufferSize(window, fbW, fbH);
            int fW = Math.max(1, fbW.get(0));
            float newPxRatio = (float) fW / (float) Math.max(1, newW);

            boolean changed = newW != winW || newH != winH;
            winW = newW;
            winH = newH;
            pxRatio = newPxRatio;
            return changed;
        }
    }

    private void installCallbacks() {
        cursorCb = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
                if (dragging) {
                    int nx = (int) Math.round(dragWinStartX + (xpos - dragStartX));
                    int ny = (int) Math.round(dragWinStartY + (ypos - dragStartY));
                    glfwSetWindowPos(window, nx, ny);
                }
            }
        };
        glfwSetCursorPosCallback(window, cursorCb);

        mouseBtnCb = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    onMouseDown();
                    return;
                }
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                    dragging = false;
                }
            }
        };
        glfwSetMouseButtonCallback(window, mouseBtnCb);

        scrollCb = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollY += yoffset;
            }
        };
        glfwSetScrollCallback(window, scrollCb);

        keyCb = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action != GLFW_PRESS && action != GLFW_REPEAT) {
                    return;
                }

                if (key == GLFW_KEY_ESCAPE) {
                    shouldClose.set(true);
                    return;
                }

                if (key == GLFW_KEY_TAB) {
                    focusNext((mods & GLFW_MOD_SHIFT) != 0);
                    return;
                }

                InputField f = getFocusedField();
                if (f != null) {
                    boolean ctrl = (mods & GLFW_MOD_CONTROL) != 0;
                    if (ctrl && key == GLFW_KEY_V) {
                        String clip = glfwGetClipboardString(window);
                        if (clip != null) f.insert(clip);
                        return;
                    }
                    if (ctrl && key == GLFW_KEY_A) {
                        f.selectAll();
                        return;
                    }
                    if (ctrl && key == GLFW_KEY_C) {
                        String sel = f.getSelectedText();
                        if (sel != null) glfwSetClipboardString(window, sel);
                        return;
                    }
                    if (ctrl && key == GLFW_KEY_X) {
                        String sel = f.getSelectedText();
                        if (sel != null) glfwSetClipboardString(window, sel);
                        f.deleteSelection();
                        return;
                    }
                    if (key == GLFW_KEY_BACKSPACE) {
                        f.backspace();
                        return;
                    }
                    if (key == GLFW_KEY_DELETE) {
                        f.deleteForward();
                        return;
                    }
                    if (key == GLFW_KEY_LEFT) {
                        f.moveCursor(-1, (mods & GLFW_MOD_SHIFT) != 0);
                        return;
                    }
                    if (key == GLFW_KEY_RIGHT) {
                        f.moveCursor(1, (mods & GLFW_MOD_SHIFT) != 0);
                        return;
                    }
                    if (key == GLFW_KEY_HOME) {
                        f.home((mods & GLFW_MOD_SHIFT) != 0);
                        return;
                    }
                    if (key == GLFW_KEY_END) {
                        f.end((mods & GLFW_MOD_SHIFT) != 0);
                        return;
                    }
                    if (key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
                        submit();
                        return;
                    }
                } else {
                    if (key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
                        submit();
                    }
                }
            }
        };
        glfwSetKeyCallback(window, keyCb);

        charCb = new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                InputField f = getFocusedField();
                if (f == null) return;
                if (codepoint < 32) return;
                f.insert(new String(Character.toChars(codepoint)));
            }
        };
        glfwSetCharCallback(window, charCb);
    }

    private void rebuildLayout() {
        float m = 22f;
        float cardX = m;
        float cardY = m;
        float cardW = winW - m * 2f;
        float cardH = winH - m * 2f;

        float leftW = cardW * 0.44f;
        float rightW = cardW - leftW;

        float rx = cardX + leftW;
        float ry = cardY;

        float formPad = 26f;
        float formX = rx + formPad;
        float formW = rightW - formPad * 2f;

        float headerH = 86f;
        float fieldH = 36f;
        float gap = 14f;

        float y = ry + headerH;
        userField = new InputField("用户名", formX, y, formW, fieldH, false);
        y += fieldH + gap;
        passField = new InputField("密码", formX, y, formW, fieldH, true);
        y += fieldH + gap;
        licenseField = new InputField("卡密", formX, y, formW, fieldH, false);

        float btnH = 38f;
        float btnY = cardY + cardH - 26f - btnH - 26f;
        primaryButton = new Button(mode == Mode.Login ? "登录" : "注册", formX, btnY, formW, btnH, ButtonKind.Primary);
        switchButton = new Button(mode == Mode.Login ? "没有账号？去注册" : "已有账号？去登录", formX, btnY + btnH + 10f, formW, 26f, ButtonKind.Ghost);

        closeButton = new Button("×", cardX + cardW - 44f, cardY + 12f, 32f, 24f, ButtonKind.Close);

        if (getFocusedField() == null) {
            userField.focused = true;
        }
    }

    private void updateHover(float dt) {
        float mx = (float) mouseX;
        float my = (float) mouseY;

        userField.updateHover(mx, my, dt);
        passField.updateHover(mx, my, dt);
        licenseField.updateHover(mx, my, dt);
        primaryButton.updateHover(mx, my, dt);
        switchButton.updateHover(mx, my, dt);
        closeButton.updateHover(mx, my, dt);
    }

    private void onMouseDown() {
        float mx = (float) mouseX;
        float my = (float) mouseY;

        if (closeButton.hit(mx, my)) {
            shouldClose.set(true);
            return;
        }

        if (primaryButton.hit(mx, my)) {
            submit();
            return;
        }

        if (switchButton.hit(mx, my) && !verifying.get()) {
            mode = mode == Mode.Login ? Mode.Register : Mode.Login;
            status.set("");
            statusKind.set(StatusKind.Neutral);
            verifying.set(false);
            rebuildLayout();
            primaryButton.label = mode == Mode.Login ? "登录" : "注册";
            switchButton.label = mode == Mode.Login ? "没有账号？去注册" : "已有账号？去登录";
            return;
        }

        boolean any = false;
        if (userField.hit(mx, my)) {
            focus(userField, mx);
            any = true;
        } else if (passField.hit(mx, my)) {
            focus(passField, mx);
            any = true;
        } else if (mode == Mode.Register && licenseField.hit(mx, my)) {
            focus(licenseField, mx);
            any = true;
        }
        if (any) return;

        float dragTop = 40f;
        float m = 22f;
        float cardX = m;
        float cardY = m;
        float cardW = winW - m * 2f;
        if (mx >= cardX && mx <= cardX + cardW && my >= cardY && my <= cardY + dragTop) {
            dragging = true;
            dragStartX = mouseX;
            dragStartY = mouseY;
            try (MemoryStack stack = MemoryStack.stackPush()) {
                var px = stack.mallocInt(1);
                var py = stack.mallocInt(1);
                glfwGetWindowPos(window, px, py);
                dragWinStartX = px.get(0);
                dragWinStartY = py.get(0);
            }
        } else {
            blurAll();
        }
    }

    private void focus(InputField f, float mx) {
        blurAll();
        f.focused = true;
        f.placeCursorByMouse(mx, vg, fontRegular, 14f);
    }

    private void blurAll() {
        userField.focused = false;
        passField.focused = false;
        licenseField.focused = false;
    }

    private void focusNext(boolean reverse) {
        InputField a = userField;
        InputField b = passField;
        InputField c = licenseField;

        if (mode == Mode.Login) {
            if (!reverse) {
                if (a.focused) {
                    a.focused = false;
                    b.focused = true;
                } else {
                    b.focused = false;
                    a.focused = true;
                }
            } else {
                if (b.focused) {
                    b.focused = false;
                    a.focused = true;
                } else {
                    a.focused = false;
                    b.focused = true;
                }
            }
            return;
        }

        if (!reverse) {
            if (a.focused) {
                a.focused = false;
                b.focused = true;
                return;
            }
            if (b.focused) {
                b.focused = false;
                c.focused = true;
                return;
            }
            c.focused = false;
            a.focused = true;
        } else {
            if (a.focused) {
                a.focused = false;
                c.focused = true;
                return;
            }
            if (c.focused) {
                c.focused = false;
                b.focused = true;
                return;
            }
            b.focused = false;
            a.focused = true;
        }
    }

    private InputField getFocusedField() {
        if (userField != null && userField.focused) return userField;
        if (passField != null && passField.focused) return passField;
        if (mode == Mode.Register && licenseField != null && licenseField.focused) return licenseField;
        return null;
    }

    private void submit() {
        if (verifying.get() || authed.get()) {
            return;
        }

        String u = userField.value.trim();
        String p = passField.value;
        String lic = licenseField.value.trim();
        boolean ok = !u.isBlank() && !p.isBlank() && (mode != Mode.Register || !lic.isBlank());
        if (!ok) {
            status.set(mode == Mode.Login ? "请输入用户名与密码" : "请输入用户名、密码与卡密");
            statusKind.set(StatusKind.Error);
            if (u.isBlank()) userField.pulseError();
            if (p.isBlank()) passField.pulseError();
            if (mode == Mode.Register && lic.isBlank()) licenseField.pulseError();
            return;
        }

        activeUsername = u;
        verifying.set(true);
        verifyStartedAtMs = System.currentTimeMillis();
        status.set("正在连接验证服务器...");
        statusKind.set(StatusKind.Neutral);

        Thread t = new Thread(() -> startAuth(mode, u, p, lic), "Sakura-PreInit-Auth");
        t.setDaemon(true);
        t.start();
    }

    private void startAuth(Mode mode, String username, String password, String license) {
        final String hwid = HwidUtil.getHWID();
        final Set<String> qqSet = QQUtils.getAllQQ();
        String p = TodeskUtils.getPhone();
        final String phone = p == null ? "" : p;

        IRCTransport transport;
        try {
            transport = VerificationClient.connect(new IRCHandler() {
                @Override
                public void onMessage(String sender, String message) {
                }

                @Override
                public void onDisconnected(String message) {
                    verifying.set(false);
                    status.set("连接断开: " + (message == null ? "" : message));
                    statusKind.set(StatusKind.Error);
                    AuthUtil.authed.set("");
                    AuthState.clear();
                    VerificationClient.shutdown();
                }

                @Override
                public void onConnected() {
                }

                @Override
                public String getInGameUsername() {
                    return activeUsername == null ? "" : activeUsername;
                }

                @Override
                public void onLoginResult(boolean success, long expireAtMillis, long timeWindowValue, String message) {
                    handleAuthResult("登录", success, expireAtMillis, timeWindowValue, message);
                }

                @Override
                public void onRegisterResult(boolean success, long expireAtMillis, long timeWindowValue, String message) {
                    handleAuthResult("注册", success, expireAtMillis, timeWindowValue, message);
                }
            });
        } catch (Exception e) {
            verifying.set(false);
            status.set("连接失败: " + (e.getMessage() == null ? "" : e.getMessage()));
            statusKind.set(StatusKind.Error);
            return;
        }

        status.set(mode == Mode.Login ? "正在登录..." : "正在注册...");
        statusKind.set(StatusKind.Neutral);

        if (mode == Mode.Login) {
            transport.login(username, password, hwid, qqSet, phone);
        } else {
            transport.register(username, password, hwid, qqSet, phone, license);
        }
    }

    private void handleAuthResult(String action, boolean success, long expireAtMillis, long timeWindowValue, String message) {
        verifying.set(false);
        verifyStartedAtMs = 0L;
        expireAt = expireAtMillis;
        timeWindow = timeWindowValue;

        if (!success) {
            AuthUtil.authed.set("");
            AuthState.clear();
            String m = message == null ? "" : message;
            status.set((action + "失败 " + m).trim());
            statusKind.set(StatusKind.Error);
            userField.pulseError();
            passField.pulseError();
            if (mode == Mode.Register) licenseField.pulseError();
            return;
        }

        long now = Instant.now().toEpochMilli();
        long nowTimeWindow = now / TIME_WINDOW_MS;
        long skew = Math.abs(nowTimeWindow - timeWindowValue);
        boolean timeOk = skew <= MAX_TIME_WINDOW_SKEW;

        AuthUtil.authed.set(AuthUtil.AUTH_OK_TOKEN);
        AuthState.setAuthed(activeUsername, expireAtMillis);
        status.set(timeOk ? "验证成功，正在进入游戏..." : "验证成功，但本地时间偏差较大，建议校准系统时间");
        statusKind.set(StatusKind.Success);
        authed.set(true);
    }

    private void render(float dt) {
        GL11C.glClearColor(0f, 0f, 0f, 0f);
        GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_STENCIL_BUFFER_BIT);

        nvgBeginFrame(vg, winW, winH, pxRatio);
        drawBackground(dt);
        drawCard(dt);
        nvgEndFrame(vg);

        GL33C.glViewport(0, 0, Math.round(winW * pxRatio), Math.round(winH * pxRatio));
    }

    private void drawBackground(float dt) {
        Color bg0 = new Color(10, 10, 14, 255);
        Color bg1 = new Color(18, 18, 24, 255);
        drawGradientRect(0, 0, winW, winH, bg0, bg1);

        float t = (System.currentTimeMillis() % 12000L) / 12000.0f;
        float cx = winW * (0.22f + 0.05f * (float) Math.sin(t * Math.PI * 2));
        float cy = winH * (0.26f + 0.08f * (float) Math.cos(t * Math.PI * 2));
        drawCircle(cx, cy, Math.min(winW, winH) * 0.36f, withAlpha(SakuraTheme.ACCENT, 22));
        drawCircle(cx + winW * 0.18f, cy + winH * 0.20f, Math.min(winW, winH) * 0.26f, new Color(236, 72, 153, 14));
    }

    private void drawCard(float dt) {
        float m = 22f;
        float cardX = m;
        float cardY = m;
        float cardW = winW - m * 2f;
        float cardH = winH - m * 2f;
        float r = 22f;

        drawShadow(cardX, cardY, cardW, cardH, r, 28f, new Color(0, 0, 0, 92));
        drawRoundRect(cardX, cardY, cardW, cardH, r, new Color(18, 18, 24, 238));
        drawRoundRectOutline(cardX, cardY, cardW, cardH, r, 1.0f, new Color(255, 255, 255, 26));

        float leftW = cardW * 0.44f;
        float rx = cardX + leftW;
        float rightW = cardW - leftW;

        drawCustomRoundRect(cardX, cardY, leftW, cardH, r - 4f, 0, 0, r - 4f, new Color(0, 0, 0, 38));

        Color a0 = withAlpha(SakuraTheme.ACCENT, 228);
        Color a1 = new Color(140, 92, 255, 212);
        drawGradientRoundedRect(cardX + 10f, cardY + 10f, leftW - 20f, cardH - 20f, 18f, a0, a1);
        drawCircle(cardX + 10f + (leftW - 20f) * 0.18f, cardY + 10f + (cardH - 20f) * 0.18f, (leftW - 20f) * 0.55f, new Color(236, 72, 153, 18));
        drawCircle(cardX + 10f + (leftW - 20f) * 0.62f, cardY + 10f + (cardH - 20f) * 0.68f, (leftW - 20f) * 0.42f, new Color(255, 255, 255, 10));
        drawRoundRectOutline(cardX + 10f, cardY + 10f, leftW - 20f, cardH - 20f, 18f, 1.0f, new Color(255, 255, 255, 28));

        float badgeX = cardX + 30f;
        float badgeY = cardY + 28f;
        String badge = "Sakura Verification";
        float badgeH = 22f;
        float badgePadX = 10f;
        float badgeFont = 12f;
        float badgeW = badgePadX * 2f + textWidth(badge, fontBold, badgeFont);
        drawRoundRect(badgeX, badgeY, badgeW, badgeH, 10f, new Color(0, 0, 0, 46));
        drawRoundRectOutline(badgeX, badgeY, badgeW, badgeH, 10f, 1.0f, new Color(255, 255, 255, 26));
        drawText(badge, badgeX + badgePadX, badgeY + badgeH / 2f, fontBold, badgeFont, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, new Color(255, 255, 255, 190));

        String headline = mode == Mode.Login ? "欢迎回来" : "创建账号";
        String subtitle = mode == Mode.Login ? "请先完成身份验证以继续使用客户端" : "注册后需要验证令牌才能继续";
        drawText(headline, cardX + 30f, cardY + 120f, fontBold, 30f, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, new Color(255, 255, 255, 240));
        drawText(subtitle, cardX + 30f, cardY + 152f, fontRegular, 14f, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, new Color(255, 255, 255, 170));

        float rightInnerX = rx + 10f;
        float rightInnerY = cardY + 10f;
        float rightInnerW = rightW - 20f;
        float rightInnerH = cardH - 20f;
        drawRoundRect(rightInnerX, rightInnerY, rightInnerW, rightInnerH, 18f, new Color(0, 0, 0, 38));
        drawRoundRectOutline(rightInnerX, rightInnerY, rightInnerW, rightInnerH, 18f, 1.0f, new Color(255, 255, 255, 22));

        drawText(mode == Mode.Login ? "登录" : "注册", rightInnerX + 22f, rightInnerY + 44f, fontBold, 20f, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, new Color(255, 255, 255, 230));
        drawText(mode == Mode.Login ? "输入凭据以继续" : "填写信息以创建账号", rightInnerX + 22f, rightInnerY + 66f, fontRegular, 13f, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, new Color(255, 255, 255, 150));

        userField.render(vg, fontRegular, fontMedium, dt);
        passField.render(vg, fontRegular, fontMedium, dt);
        if (mode == Mode.Register) {
            licenseField.render(vg, fontRegular, fontMedium, dt);
        }

        boolean canSubmit = canSubmit();
        primaryButton.disabled = verifying.get() || authed.get() || !canSubmit;
        primaryButton.loading = verifying.get();
        switchButton.disabled = verifying.get() || authed.get();
        closeButton.disabled = verifying.get();

        primaryButton.render(vg, fontBold, fontRegular, dt);
        switchButton.render(vg, fontMedium, fontRegular, dt);
        closeButton.render(vg, fontBold, fontRegular, dt);

        String statusText = status.get();
        if (statusText == null || statusText.isBlank()) {
            statusText = mode == Mode.Login ? "输入用户名与密码" : "输入用户名、密码与卡密";
        }

        Color sc;
        StatusKind kind = statusKind.get();
        if (verifying.get()) {
            sc = withAlpha(SakuraTheme.ACCENT, 220);
        } else if (kind == StatusKind.Error) {
            sc = withAlpha(SakuraTheme.DANGER, 230);
        } else if (kind == StatusKind.Success) {
            sc = new Color(92, 255, 176, 220);
        } else {
            sc = new Color(255, 255, 255, 140);
        }

        float hintX = primaryButton.x;
        float hintY = primaryButton.y - 14f;
        drawText(statusText, hintX, hintY, fontRegular, 12f, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, sc);
    }

    private boolean canSubmit() {
        if (userField == null || passField == null || licenseField == null) return false;
        if (userField.value.trim().isBlank()) return false;
        if (passField.value.isBlank()) return false;
        return mode != Mode.Register || !licenseField.value.trim().isBlank();
    }

    private void loadFonts() {
        fontRegular = createFont("regular", "/assets/sakura/fonts/regular.otf");
        fontMedium = createFont("medium", "/assets/sakura/fonts/regular_medium.otf");
        fontBold = createFont("bold", "/assets/sakura/fonts/regular_bold.otf");
        fontCjk = createFont("cjk", "/assets/sakura/fonts/kuriyama.ttf");

        if (fontRegular != -1 && fontCjk != -1) nvgAddFallbackFontId(vg, fontRegular, fontCjk);
        if (fontMedium != -1 && fontCjk != -1) nvgAddFallbackFontId(vg, fontMedium, fontCjk);
        if (fontBold != -1 && fontCjk != -1) nvgAddFallbackFontId(vg, fontBold, fontCjk);
    }

    private int createFont(String name, String resourcePath) {
        try (InputStream is = LoginWindow.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                return -1;
            }
            byte[] bytes = is.readAllBytes();
            ByteBuffer buf = ByteBuffer.allocateDirect(bytes.length);
            buf.put(bytes);
            buf.flip();
            fontBuffers.put(name, buf);
            return nvgCreateFontMem(vg, name, buf, false);
        } catch (Exception ignored) {
            return -1;
        }
    }

    private void cleanup() {
        if (cursorCb != null) cursorCb.free();
        if (mouseBtnCb != null) mouseBtnCb.free();
        if (scrollCb != null) scrollCb.free();
        if (keyCb != null) keyCb.free();
        if (charCb != null) charCb.free();

        if (vg != 0L) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0L;
        }

        fontBuffers.clear();

        if (window != MemoryUtil.NULL) {
            glfwDestroyWindow(window);
            window = MemoryUtil.NULL;
        }
        glfwMakeContextCurrent(MemoryUtil.NULL);
    }

    private enum StatusKind {
        Neutral,
        Error,
        Success
    }

    private enum ButtonKind {
        Primary,
        Ghost,
        Close
    }

    private final class Button {
        private String label;
        private final float x;
        private final float y;
        private final float w;
        private final float h;
        private final ButtonKind kind;
        private boolean disabled;
        private boolean loading;
        private float hoverT;

        private Button(String label, float x, float y, float w, float h, ButtonKind kind) {
            this.label = label == null ? "" : label;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.kind = kind;
        }

        private boolean hit(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }

        private void updateHover(float mx, float my, float dt) {
            float target = hit(mx, my) && !disabled ? 1f : 0f;
            hoverT = approach(hoverT, target, dt * 12f);
        }

        private void render(long vg, int fontPrimary, int fontSecondary, float dt) {
            float r = kind == ButtonKind.Ghost ? 12f : 12f;
            if (kind == ButtonKind.Close) r = 10f;

            if (kind == ButtonKind.Primary) {
                Color base = withAlpha(SakuraTheme.ACCENT, disabled ? 80 : 255);
                Color glow = withAlpha(SakuraTheme.ACCENT, disabled ? 0 : (int) (34 + 48 * hoverT));
                drawShadow(x, y, w, h, r, 18f, glow);
                drawGradientRoundedRect(x, y, w, h, r, base, new Color(236, 72, 153, disabled ? 70 : 210));
                drawRoundRectOutline(x, y, w, h, r, 1.0f, new Color(255, 255, 255, disabled ? 16 : 32));
                drawButtonLabel(label, fontPrimary, new Color(255, 255, 255, disabled ? 120 : 235), loading);
                return;
            }

            if (kind == ButtonKind.Ghost) {
                Color fill = new Color(255, 255, 255, (int) (8 + 16 * hoverT));
                Color border = new Color(255, 255, 255, (int) (18 + 16 * hoverT));
                drawRoundRect(x, y, w, h, r, disabled ? new Color(255, 255, 255, 6) : fill);
                drawRoundRectOutline(x, y, w, h, r, 1.0f, disabled ? new Color(255, 255, 255, 12) : border);
                drawText(label, x + w / 2f, y + h / 2f, fontSecondary, 12f, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, disabled ? new Color(255, 255, 255, 80) : new Color(255, 255, 255, 180));
                return;
            }

            Color fill = new Color(255, 255, 255, (int) (10 + 20 * hoverT));
            Color border = new Color(255, 255, 255, (int) (18 + 22 * hoverT));
            drawRoundRect(x, y, w, h, r, disabled ? new Color(255, 255, 255, 6) : fill);
            drawRoundRectOutline(x, y, w, h, r, 1.0f, disabled ? new Color(255, 255, 255, 12) : border);
            drawText(label, x + w / 2f, y + h / 2f - 1f, fontPrimary, 16f, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, disabled ? new Color(255, 255, 255, 90) : new Color(255, 255, 255, 190));
        }

        private void drawButtonLabel(String text, int font, Color color, boolean loading) {
            if (!loading) {
                drawText(text, x + w / 2f, y + h / 2f, font, 14f, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, color);
                return;
            }

            float spinnerR = 7f;
            float cx = x + w / 2f;
            float cy = y + h / 2f;
            float a0 = (float) ((System.currentTimeMillis() % 900L) / 900.0 * Math.PI * 2);
            drawArcSpinner(cx - 34f, cy, spinnerR, a0, withAlpha(new Color(255, 255, 255), 210));
            drawText(text, cx + 8f, cy, font, 14f, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, color);
        }
    }

    private final class InputField {
        private final String placeholder;
        private final float x;
        private final float y;
        private final float w;
        private final float h;
        private final boolean password;

        private String value = "";
        private boolean focused;
        private float hoverT;
        private float focusT;
        private long errorUntil;
        private long errorStart;
        private float errorT;

        private int cursor;
        private int selStart = -1;
        private int selEnd = -1;
        private float scroll;

        private InputField(String placeholder, float x, float y, float w, float h, boolean password) {
            this.placeholder = placeholder == null ? "" : placeholder;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.password = password;
        }

        private boolean hit(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }

        private void updateHover(float mx, float my, float dt) {
            float target = hit(mx, my) ? 1f : 0f;
            hoverT = approach(hoverT, target, dt * 12f);
            focusT = approach(focusT, focused ? 1f : 0f, dt * 14f);

            long now = System.currentTimeMillis();
            boolean err = now <= errorUntil;
            float et = err ? 1f : 0f;
            errorT = approach(errorT, et, dt * 10f);
        }

        private void pulseError() {
            errorStart = System.currentTimeMillis();
            errorUntil = errorStart + 900L;
        }

        private void insert(String s) {
            if (s == null || s.isEmpty()) return;
            if (s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0) s = s.replace("\r", "").replace("\n", "");
            s = s.replace("\t", " ");
            deleteSelection();
            int c = clamp(cursor, 0, value.length());
            value = value.substring(0, c) + s + value.substring(c);
            cursor = c + s.length();
            clearSelection();
            ensureCursorVisible();
        }

        private void backspace() {
            if (deleteSelection()) return;
            if (cursor <= 0) return;
            int c = clamp(cursor, 0, value.length());
            value = value.substring(0, c - 1) + value.substring(c);
            cursor = c - 1;
            ensureCursorVisible();
        }

        private void deleteForward() {
            if (deleteSelection()) return;
            int c = clamp(cursor, 0, value.length());
            if (c >= value.length()) return;
            value = value.substring(0, c) + value.substring(c + 1);
            ensureCursorVisible();
        }

        private void moveCursor(int delta, boolean keepSelection) {
            int prev = cursor;
            cursor = clamp(cursor + delta, 0, value.length());
            if (keepSelection) {
                extendSelection(prev, cursor);
            } else {
                clearSelection();
            }
            ensureCursorVisible();
        }

        private void home(boolean keepSelection) {
            int prev = cursor;
            cursor = 0;
            if (keepSelection) extendSelection(prev, cursor);
            else clearSelection();
            ensureCursorVisible();
        }

        private void end(boolean keepSelection) {
            int prev = cursor;
            cursor = value.length();
            if (keepSelection) extendSelection(prev, cursor);
            else clearSelection();
            ensureCursorVisible();
        }

        private void selectAll() {
            selStart = 0;
            selEnd = value.length();
            cursor = selEnd;
            ensureCursorVisible();
        }

        private String getSelectedText() {
            if (!hasSelection()) return null;
            int a = Math.min(selStart, selEnd);
            int b = Math.max(selStart, selEnd);
            if (a < 0 || b > value.length()) return null;
            return value.substring(a, b);
        }

        private boolean deleteSelection() {
            if (!hasSelection()) return false;
            int a = Math.min(selStart, selEnd);
            int b = Math.max(selStart, selEnd);
            a = clamp(a, 0, value.length());
            b = clamp(b, 0, value.length());
            if (b <= a) {
                clearSelection();
                return false;
            }
            value = value.substring(0, a) + value.substring(b);
            cursor = a;
            clearSelection();
            ensureCursorVisible();
            return true;
        }

        private void clearSelection() {
            selStart = -1;
            selEnd = -1;
        }

        private boolean hasSelection() {
            return selStart >= 0 && selEnd >= 0 && selStart != selEnd;
        }

        private void extendSelection(int from, int to) {
            if (!hasSelection()) {
                selStart = from;
                selEnd = to;
            } else {
                selEnd = to;
            }
        }

        private void ensureCursorVisible() {
            float pad = 12f;
            float inner = Math.max(1f, w - pad * 2f);
            String display = password ? "•".repeat(value.length()) : value;
            float before = textWidth(display.substring(0, clamp(cursor, 0, display.length())), fontRegular, 14f);
            float left = scroll;
            float right = scroll + inner;
            if (before < left) {
                scroll = before;
            } else if (before > right) {
                scroll = before - inner;
            }
            scroll = Math.max(0f, scroll);
        }

        private void placeCursorByMouse(float mx, long vg, int font, float size) {
            String display = password ? "•".repeat(value.length()) : value;
            float pad = 12f;
            float localX = mx - (x + pad);
            localX += scroll;
            localX = Math.max(0f, localX);

            int best = 0;
            float prevW = 0f;
            for (int i = 1; i <= display.length(); i++) {
                float w = textWidth(display.substring(0, i), font, size);
                if (localX < w) {
                    float mid = (prevW + w) * 0.5f;
                    best = localX < mid ? i - 1 : i;
                    break;
                }
                prevW = w;
                best = i;
            }
            cursor = clamp(best, 0, value.length());
            clearSelection();
            ensureCursorVisible();
        }

        private void render(long vg, int fontReg, int fontMed, float dt) {
            float r = (h / 34.0f) * 12.0f;
            float shake = 0.0f;
            long now = System.currentTimeMillis();
            if (now <= errorUntil) {
                float p = (now - errorStart) / 900.0f;
                p = clamp01(p);
                float amp = (1.0f - p) * 2.2f;
                shake = (float) Math.sin((now - errorStart) / 28.0) * amp;
            }

            float xx = x + shake;

            Color fill = new Color(255, 255, 255, (int) (12 + 10 * hoverT + 8 * focusT));
            drawRoundRect(xx, y, w, h, r, fill);

            Color baseBorder = new Color(255, 255, 255, (int) (26 + 18 * hoverT));
            Color focusBorder = withAlpha(SakuraTheme.ACCENT, (int) (60 + 150 * focusT));
            Color errorBorder = withAlpha(SakuraTheme.DANGER, (int) (40 + 190 * errorT));
            Color border = mix(mix(baseBorder, focusBorder, focusT), errorBorder, errorT);
            drawRoundRectOutline(xx, y, w, h, r, 1.1f, border);

            float pad = 12f;
            float innerW = w - pad * 2f;
            float textY = y + h / 2f;

            String display = password ? "•".repeat(value.length()) : value;
            boolean empty = display.isEmpty();

            nvgScissor(vg, xx + pad, y, innerW, h);

            if (empty && !focused) {
                drawText(placeholder, xx + pad, textY, fontReg, 14f, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, new Color(255, 255, 255, 110));
            } else {
                float tx = xx + pad - scroll;
                drawText(display, tx, textY, fontReg, 14f, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, new Color(255, 255, 255, 210));

                if (hasSelection()) {
                    int a = Math.min(selStart, selEnd);
                    int b = Math.max(selStart, selEnd);
                    a = clamp(a, 0, display.length());
                    b = clamp(b, 0, display.length());
                    float ax = textWidth(display.substring(0, a), fontReg, 14f);
                    float bx = textWidth(display.substring(0, b), fontReg, 14f);
                    float sx = tx + ax;
                    float sw = Math.max(0f, bx - ax);
                    drawRoundRect(sx, y + 6f, sw, h - 12f, 6f, new Color(255, 255, 255, 26));
                }

                if (focused && (System.currentTimeMillis() / 520) % 2 == 0) {
                    int c = clamp(cursor, 0, display.length());
                    float before = textWidth(display.substring(0, c), fontReg, 14f);
                    float cx = tx + before + 1f;
                    nvgBeginPath(vg);
                    nvgMoveTo(vg, cx, y + 7f);
                    nvgLineTo(vg, cx, y + h - 7f);
                    nvgStrokeColor(vg, nvgColor(withAlpha(new Color(255, 255, 255), 230)));
                    nvgStrokeWidth(vg, 1.0f);
                    nvgStroke(vg);
                }
            }

            nvgResetScissor(vg);
        }
    }

    private static float approach(float current, float target, float speed) {
        if (current < target) return Math.min(target, current + speed);
        return Math.max(target, current - speed);
    }

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private static NVGColor nvgColor(Color c) {
        NVGColor nc = NVGColor.create();
        nc.r(c.getRed() / 255.0f);
        nc.g(c.getGreen() / 255.0f);
        nc.b(c.getBlue() / 255.0f);
        nc.a(c.getAlpha() / 255.0f);
        return nc;
    }

    private void drawText(String text, float x, float y, int font, float size, int align, Color color) {
        if (text == null) text = "";
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, align);
        nvgFillColor(vg, nvgColor(color));
        nvgText(vg, x, y, text);
    }

    private float textWidth(String text, int font, float size) {
        if (text == null || text.isEmpty()) return 0f;
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        float[] bounds = new float[4];
        return nvgTextBounds(vg, 0, 0, text, bounds);
    }

    private static Color withAlpha(Color c, int alpha) {
        int a = Math.max(0, Math.min(255, alpha));
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
    }

    private static Color mix(Color a, Color b, float t) {
        t = clamp01(t);
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        int al = (int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t);
        return new Color(r, g, bl, al);
    }

    private void drawGradientRect(float x, float y, float w, float h, Color c0, Color c1) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgLinearGradient(vg, x, y, x, y + h, nvgColor(c0), nvgColor(c1), paint);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    private void drawGradientRoundedRect(float x, float y, float w, float h, float r, Color c0, Color c1) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            nvgBeginPath(vg);
            nvgRoundedRect(vg, x, y, w, h, r);
            nvgLinearGradient(vg, x, y, x + w, y + h, nvgColor(c0), nvgColor(c1), paint);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    private void drawRoundRect(float x, float y, float w, float h, float r, Color color) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, r);
        nvgFillColor(vg, nvgColor(color));
        nvgFill(vg);
    }

    private void drawCustomRoundRect(float x, float y, float w, float h, float rtl, float rtr, float rbr, float rbl, Color color) {
        nvgBeginPath(vg);
        nvgRoundedRectVarying(vg, x, y, w, h, rtl, rtr, rbr, rbl);
        nvgFillColor(vg, nvgColor(color));
        nvgFill(vg);
    }

    private void drawRoundRectOutline(float x, float y, float w, float h, float r, float stroke, Color color) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, r);
        nvgStrokeWidth(vg, stroke);
        nvgStrokeColor(vg, nvgColor(color));
        nvgStroke(vg);
    }

    private void drawCircle(float x, float y, float radius, Color color) {
        nvgBeginPath(vg);
        nvgCircle(vg, x, y, radius);
        nvgFillColor(vg, nvgColor(color));
        nvgFill(vg);
    }

    private void drawShadow(float x, float y, float w, float h, float r, float spread, Color color) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint shadowPaint = NVGPaint.malloc(stack);
            NVGColor transparent = nvgColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));
            nvgBoxGradient(vg, x, y, w, h, r, spread, nvgColor(color), transparent, shadowPaint);
            nvgBeginPath(vg);
            nvgRect(vg, x - spread, y - spread, w + spread * 2f, h + spread * 2f);
            nvgRoundedRect(vg, x, y, w, h, r);
            nvgPathWinding(vg, NVG_HOLE);
            nvgFillPaint(vg, shadowPaint);
            nvgFill(vg);
        }
    }

    private void drawArcSpinner(float cx, float cy, float r, float angle, Color color) {
        float a0 = angle;
        float a1 = angle + (float) (Math.PI * 1.2);
        nvgBeginPath(vg);
        nvgArc(vg, cx, cy, r, a0, a1, NVG_CW);
        nvgStrokeWidth(vg, 2.2f);
        nvgStrokeColor(vg, nvgColor(color));
        nvgStroke(vg);
    }
}
