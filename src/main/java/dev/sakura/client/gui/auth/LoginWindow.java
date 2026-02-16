package dev.sakura.client.gui.auth;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import dev.sakura.client.verify.client.IRCTransport;
import dev.sakura.client.verify.util.AuthUtil;
import dev.sakura.client.verify.util.HwidUtil;
import dev.sakura.client.verify.util.QQUtils;
import dev.sakura.client.verify.util.TodeskUtils;
import dev.sakura.niurendeobf.ZKMIndy;
import jnic.JNICInclude;
import org.lwjgl.glfw.*;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;

@Native
@JNICInclude
@ZKMIndy
public final class LoginWindow {
    private static final long TIME_WINDOW_MS = 30_000L;
    private static final long MAX_TIME_WINDOW_SKEW = 1L;
    private static final byte[] CRED_MAGIC = new byte[]{'S', 'K', 'R', '1'};
    private static final int GCM_TAG_BITS = 128;
    private static final float TRANSITION_SECONDS = 0.65f;

    private static final int WIN_W = 900;
    private static final int WIN_H = 520;

    private static final int MOUSE_LEFT = 0;

    private static final float DESIGN_W = 820f;
    private static final float DESIGN_H = 460f;

    private static final class Rect {
        float x;
        float y;
        float w;
        float h;

        Rect(float x, float y, float w, float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        boolean contains(double mx, double my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }

    private static final class Anim {
        float v;

        Anim(float v) {
            this.v = v;
        }

        void update(float target, float speed, float dt) {
            v = approach(v, target, speed, dt);
        }
    }

    private static final class TextField {
        String placeholder;
        final StringBuilder value = new StringBuilder();
        int cursor = 0;
        boolean focused = false;
        float scrollPx = 0f;
        final Anim hover = new Anim(0f);
        final Anim focus = new Anim(0f);
        final Anim error = new Anim(0f);
        long errorUntilMs = 0L;
        long errorStartMs = 0L;

        TextField(String placeholder) {
            this.placeholder = placeholder == null ? "" : placeholder;
        }

        String get() {
            return value.toString();
        }

        void set(String s) {
            value.setLength(0);
            if (s != null && !s.isEmpty()) value.append(s);
            cursor = clamp(cursor, 0, value.length());
        }

        void setFocused(boolean focused) {
            this.focused = focused;
        }

        void insert(String s) {
            if (s == null || s.isEmpty()) return;
            cursor = clamp(cursor, 0, value.length());
            value.insert(cursor, s);
            cursor += s.length();
        }

        void backspace() {
            if (cursor <= 0 || value.isEmpty()) return;
            cursor = clamp(cursor, 0, value.length());
            value.deleteCharAt(cursor - 1);
            cursor--;
        }

        void delete() {
            if (cursor >= value.length() || value.isEmpty()) return;
            cursor = clamp(cursor, 0, value.length());
            value.deleteCharAt(cursor);
        }

        void left() {
            cursor = clamp(cursor - 1, 0, value.length());
        }

        void right() {
            cursor = clamp(cursor + 1, 0, value.length());
        }

        void pulseError() {
            errorStartMs = System.currentTimeMillis();
            errorUntilMs = errorStartMs + 900L;
        }
    }

    private enum Mode {
        Login,
        Register
    }

    private volatile boolean verifying = false;
    private volatile String statusLine = "";
    private volatile boolean success = false;

    private long window = 0L;
    private long vg = 0L;
    private int fontRegular = -1;
    private int fontMedium = -1;
    private int fontBold = -1;
    private int fontCjk = -1;

    private Mode mode = Mode.Login;
    private final Anim modeAnim = new Anim(0f);

    private final TextField username = new TextField("用户名");
    private final TextField password = new TextField("密码");
    private final TextField license = new TextField("卡密");

    private boolean rememberPassword = false;
    private final Anim rememberHover = new Anim(0f);
    private final Anim rememberAnim = new Anim(0f);

    private float transitionP = 0f;
    private volatile float transitionTarget = 0f;

    private final Anim loginTabHover = new Anim(0f);
    private final Anim registerTabHover = new Anim(0f);

    private final Anim primaryHover = new Anim(0f);
    private final Anim secondaryHover = new Anim(0f);
    private final Anim exitHover = new Anim(0f);
    private final Anim primaryPress = new Anim(0f);

    private boolean mouseDown = false;
    private boolean mouseClicked = false;
    private boolean mouseReleased = false;
    private int mouseButton = -1;
    private double mouseX = 0;
    private double mouseY = 0;

    private long lastFrameNs = 0L;
    private boolean requestClose = false;

    private static final class IconImage {
        final int w;
        final int h;
        final ByteBuffer pixels;

        IconImage(int w, int h, ByteBuffer pixels) {
            this.w = w;
            this.h = h;
            this.pixels = pixels;
        }
    }

    public static void verifyOrExitBlocking() {
        LoginWindow w = new LoginWindow();
        boolean ok = w.runBlocking();
        if (!ok) {
            System.exit(0);
        }
    }

    private boolean runBlocking() {
        try {
            initWindow();
            loop();
            return success;
        } finally {
            cleanup();
        }
    }

    private void initWindow() {
        glfwSetErrorCallback((error, description) -> {
        });
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW init failed");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(WIN_W, WIN_H, "欢迎来到 Sakura | Welcome to Sakura", MemoryUtil.NULL, MemoryUtil.NULL);

        glfwSetWindowPos(window, (glfwGetVideoMode(glfwGetPrimaryMonitor()).width() - WIN_W) / 2, (glfwGetVideoMode(glfwGetPrimaryMonitor()).height() - WIN_H) / 2);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);

        loadFonts();
        setWindowIcons();
        loadSavedCredentials();

        GLFWFramebufferSizeCallbackI fbCb = (w, width, height) -> GL33C.glViewport(0, 0, width, height);
        glfwSetFramebufferSizeCallback(window, fbCb);

        GLFWCursorPosCallbackI cursorCb = (w, x, y) -> {
            mouseX = x;
            mouseY = y;
        };
        glfwSetCursorPosCallback(window, cursorCb);

        GLFWMouseButtonCallbackI mouseCb = (w, button, action, mods) -> {
            mouseButton = button;
            if (action == GLFW_PRESS) {
                mouseDown = true;
                mouseClicked = true;
            } else if (action == GLFW_RELEASE) {
                mouseDown = false;
                mouseReleased = true;
            }
        };
        glfwSetMouseButtonCallback(window, mouseCb);

        GLFWKeyCallbackI keyCb = (w, key, scancode, action, mods) -> {
            if (action != GLFW_PRESS && action != GLFW_REPEAT) return;
            if (key == GLFW_KEY_ESCAPE) {
                requestClose = true;
                return;
            }

            boolean ctrl = (mods & GLFW_MOD_CONTROL) != 0;

            if (ctrl && key == GLFW_KEY_V) {
                String clip = glfwGetClipboardString(window);
                if (clip != null && !clip.isEmpty()) {
                    getFocusedField().insert(clip);
                }
                return;
            }

            TextField f = getFocusedFieldOrNull();
            if (f == null) return;
            if (key == GLFW_KEY_BACKSPACE) {
                f.backspace();
                return;
            }
            if (key == GLFW_KEY_DELETE) {
                f.delete();
                return;
            }
            if (key == GLFW_KEY_LEFT) {
                f.left();
                return;
            }
            if (key == GLFW_KEY_RIGHT) {
                f.right();
                return;
            }
            if (key == GLFW_KEY_TAB) {
                focusNext();
                return;
            }
            if (key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
                if (!verifying) trySubmit();
            }
        };
        glfwSetKeyCallback(window, keyCb);

        GLFWCharCallbackI charCb = (w, codepoint) -> {
            TextField f = getFocusedFieldOrNull();
            if (f == null || verifying) return;
            if (codepoint == 0) return;
            char[] chars = Character.toChars(codepoint);
            String s = new String(chars);
            if (s.isBlank()) return;
            f.insert(s);
        };
        glfwSetCharCallback(window, charCb);

        glfwShowWindow(window);
        glfwFocusWindow(window);
    }

    private void loadSavedCredentials() {
        try {
            Path p = credentialPath();
            if (!Files.isRegularFile(p)) return;
            byte[] bytes = Files.readAllBytes(p);
            String plain = decryptCredentials(bytes);
            if (plain == null || plain.isBlank()) {
                clearSavedCredentials();
                return;
            }
            int sep = plain.indexOf('\0');
            if (sep < 0) {
                clearSavedCredentials();
                return;
            }
            String u = plain.substring(0, sep);
            String pw = plain.substring(sep + 1);
            if (u != null && !u.isBlank()) username.set(u);
            if (pw != null && !pw.isBlank()) password.set(pw);
            rememberPassword = true;
            rememberAnim.v = 1f;
        } catch (Exception ignored) {
            clearSavedCredentials();
        }
    }

    private static void clearSavedCredentials() {
        try {
            Files.deleteIfExists(credentialPath());
        } catch (Exception ignored) {
        }
    }

    private static void saveCredentials(String u, String pw) {
        if (u == null || u.isBlank() || pw == null || pw.isBlank()) {
            clearSavedCredentials();
            return;
        }
        try {
            Path p = credentialPath();
            Files.createDirectories(p.getParent());
            byte[] data = encryptCredentials(u + "\0" + pw);
            Files.write(p, data);
        } catch (Exception ignored) {
        }
    }

    private static Path credentialPath() {
        return Paths.get(System.getProperty("user.home"), "Sakura", "credentials.bin");
    }

    private static byte[] encryptCredentials(String plain) throws Exception {
        byte[] key = credentialKey();
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(GCM_TAG_BITS, iv));
        cipher.updateAAD("SakuraCredential-v1".getBytes(StandardCharsets.UTF_8));
        byte[] ct = cipher.doFinal((plain == null ? "" : plain).getBytes(StandardCharsets.UTF_8));

        ByteBuffer out = ByteBuffer.allocate(4 + 1 + iv.length + 4 + ct.length);
        out.put(CRED_MAGIC);
        out.put((byte) iv.length);
        out.put(iv);
        out.putInt(ct.length);
        out.put(ct);
        return out.array();
    }

    private static String decryptCredentials(byte[] data) {
        try {
            if (data == null || data.length < 4 + 1 + 12 + 4) return null;
            ByteBuffer in = ByteBuffer.wrap(data);
            for (int i = 0; i < CRED_MAGIC.length; i++) {
                if (in.get() != CRED_MAGIC[i]) return null;
            }
            int ivLen = Byte.toUnsignedInt(in.get());
            if (ivLen < 12 || ivLen > 32) return null;
            if (in.remaining() < ivLen + 4) return null;
            byte[] iv = new byte[ivLen];
            in.get(iv);
            int ctLen = in.getInt();
            if (ctLen <= 0 || ctLen > in.remaining()) return null;
            byte[] ct = new byte[ctLen];
            in.get(ct);

            byte[] key = credentialKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(GCM_TAG_BITS, iv));
            cipher.updateAAD("SakuraCredential-v1".getBytes(StandardCharsets.UTF_8));
            byte[] pt = cipher.doFinal(ct);
            return new String(pt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] credentialKey() throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        String seed = "Sakura|Cred|v1|" + HwidUtil.getHWID() + "|" + System.getProperty("user.name");
        byte[] digest = sha256.digest(seed.getBytes(StandardCharsets.UTF_8));
        byte[] key = new byte[16];
        System.arraycopy(digest, 0, key, 0, 16);
        return key;
    }

    private void setWindowIcons() {
        IconImage icon16 = loadIcon("/assets/sakura/icons/icon_16x16.png");
        IconImage icon32 = loadIcon("/assets/sakura/icons/icon_32x32.png");
        if (icon16 == null || icon32 == null) {
            freeIcon(icon16);
            freeIcon(icon32);
            return;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            GLFWImage.Buffer icons = GLFWImage.malloc(2, stack);
            icons.position(0);
            icons.get(0).set(icon16.w, icon16.h, icon16.pixels);
            icons.get(1).set(icon32.w, icon32.h, icon32.pixels);
            icons.position(0);
            glfwSetWindowIcon(window, icons);
        } finally {
            freeIcon(icon16);
            freeIcon(icon32);
        }
    }

    private static void freeIcon(IconImage icon) {
        if (icon == null) return;
        if (icon.pixels != null) {
            STBImage.stbi_image_free(icon.pixels);
        }
    }

    private static IconImage loadIcon(String path) {
        ByteBuffer encoded = null;
        try (InputStream is = LoginWindow.class.getResourceAsStream(path)) {
            if (is == null) return null;
            byte[] bytes = is.readAllBytes();
            encoded = MemoryUtil.memAlloc(bytes.length);
            encoded.put(bytes);
            encoded.flip();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                IntBuffer comp = stack.mallocInt(1);
                ByteBuffer pixels = STBImage.stbi_load_from_memory(encoded, w, h, comp, 4);
                if (pixels == null) return null;
                return new IconImage(w.get(0), h.get(0), pixels);
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (encoded != null) {
                MemoryUtil.memFree(encoded);
            }
        }
    }

    private void loadFonts() {
        fontRegular = createFont("sakura-regular", "/assets/sakura/fonts/regular.otf");
        fontMedium = createFont("sakura-medium", "/assets/sakura/fonts/regular_medium.otf");
        fontBold = createFont("sakura-bold", "/assets/sakura/fonts/regular_bold.otf");
        fontCjk = createFont("sakura-cjk", "/assets/sakura/fonts/kuriyama.ttf");
        if (fontRegular >= 0 && fontCjk >= 0) {
            NanoVG.nvgAddFallbackFontId(vg, fontRegular, fontCjk);
            NanoVG.nvgAddFallbackFontId(vg, fontMedium, fontCjk);
            NanoVG.nvgAddFallbackFontId(vg, fontBold, fontCjk);
        }
    }

    private int createFont(String name, String path) {
        ByteBuffer buf = readResourceToDirectBuffer(path);
        if (buf == null) return -1;
        return nvgCreateFontMem(vg, name, buf, false);
    }

    private static ByteBuffer readResourceToDirectBuffer(String path) {
        try (InputStream is = LoginWindow.class.getResourceAsStream(path)) {
            if (is == null) return null;
            byte[] bytes = is.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        } catch (Exception e) {
            return null;
        }
    }

    private void loop() {
        lastFrameNs = System.nanoTime();
        while (!glfwWindowShouldClose(window)) {
            long nowNs = System.nanoTime();
            float dt = clamp((nowNs - lastFrameNs) / 1_000_000_000f, 0f, 0.05f);
            lastFrameNs = nowNs;

            glfwPollEvents();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                java.nio.DoubleBuffer px = stack.mallocDouble(1);
                java.nio.DoubleBuffer py = stack.mallocDouble(1);
                glfwGetCursorPos(window, px, py);
                mouseX = px.get(0);
                mouseY = py.get(0);
            }
            if (requestClose) {
                break;
            }

            renderFrame(dt);

            mouseClicked = false;
            mouseReleased = false;
            mouseButton = -1;
        }
    }

    private void renderFrame(float dt) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int[] fbWArr = new int[1];
            int[] fbHArr = new int[1];
            int[] winWArr = new int[1];
            int[] winHArr = new int[1];
            glfwGetFramebufferSize(window, fbWArr, fbHArr);
            glfwGetWindowSize(window, winWArr, winHArr);
            int fbW = Math.max(1, fbWArr[0]);
            int fbH = Math.max(1, fbHArr[0]);
            int winW = Math.max(1, winWArr[0]);
            int winH = Math.max(1, winHArr[0]);

            float pxRatio = (float) fbW / (float) winW;
            GL33C.glViewport(0, 0, fbW, fbH);
            GL33C.glClearColor(0f, 0f, 0f, 1f);
            GL33C.glClear(GL33C.GL_COLOR_BUFFER_BIT | GL33C.GL_STENCIL_BUFFER_BIT);

            nvgBeginFrame(vg, winW, winH, pxRatio);
            drawUi(winW, winH, dt, stack);
            nvgEndFrame(vg);

            glfwSwapBuffers(window);
        }
    }

    private void drawUi(int w, int h, float dt, MemoryStack stack) {
        float target = transitionTarget;
        if (transitionP != target) {
            float step = dt / Math.max(0.001f, TRANSITION_SECONDS);
            if (transitionP < target) {
                transitionP = Math.min(target, transitionP + step);
            } else {
                transitionP = Math.max(target, transitionP - step);
            }
        }
        float eased = easeOutCubic(clamp(transitionP, 0f, 1f));

        float margin = 18f;
        float availW = Math.max(1f, w - margin * 2);
        float availH = Math.max(1f, h - margin * 2);
        float scale = Math.min(1f, Math.min(availW / DESIGN_W, availH / DESIGN_H));
        scale = clamp(scale, 0.15f, 1.0f);

        float outerW = Math.min(w, Math.round(DESIGN_W * scale));
        float outerH = Math.min(h, Math.round(DESIGN_H * scale));
        float ox = (w - outerW) * 0.5f;
        float oy = (h - outerH) * 0.5f;

        float pad = Math.max(8, Math.round(26f * scale));
        float leftW = clamp(Math.round(outerW * 0.44f), Math.max(160, Math.round(220f * scale)), Math.max(Math.max(160, Math.round(220f * scale)), outerW - Math.max(180, Math.round(240f * scale))));
        float rightX = ox + leftW;
        float rightW = outerW - leftW;

        float t = (System.currentTimeMillis() % 12000L) / 12000.0f;
        float bgT = (float) Math.sin(t * Math.PI * 2);

        drawGradientRect(0, 0, w, h, 10, 10, 14, 255, 18, 18, 24, 255, stack);
        drawGradientRect(0, 0, w, h, 92, 124, 255, 18, 236, 72, 153, 0, stack);

        if (eased > 0.0001f) {
            drawRRect(0, 0, w, h, 0, 0, 0, 0, (int) (70f * eased), stack);
        }

        float panelScale = 1.0f - 0.1f * eased;
        float panelAlpha = 1.0f - eased;
        float cx = w * 0.5f;
        float cy = h * 0.5f;
        nvgSave(vg);
        nvgTranslate(vg, cx, cy);
        nvgScale(vg, panelScale, panelScale);
        nvgTranslate(vg, -cx, -cy);
        nvgGlobalAlpha(vg, panelAlpha);

        float shadowR = 26f * scale;
        int shadowA = (int) (60 + 70f * eased);
        drawRRect(ox - 10, oy - 10, outerW + 20, outerH + 20, shadowR, 0, 0, 0, clampInt(shadowA, 0, 160), stack);

        float r = 22f * scale;
        drawRRect(ox, oy, outerW, outerH, r, 18, 18, 24, 242, stack);
        drawRRectStroke(ox, oy, outerW, outerH, r, 1.1f, 255, 255, 255, 26, stack);

        float lx = ox + pad * 0.45f;
        float ly = oy + pad * 0.45f;
        float lw = leftW - pad * 0.9f;
        float lh = outerH - pad * 0.9f;
        float leftR = 18f * scale;

        int a0r = 92, a0g = 124, a0b = 255;
        int a1r = 140, a1g = 92, a1b = 255;
        drawGradientRRect(lx, ly, lw, lh, leftR, a0r, a0g, a0b, 228, a1r, a1g, a1b, 212, stack);
        drawCircle(lx + lw * (0.18f + 0.02f * bgT), ly + lh * (0.20f + 0.04f * (float) Math.cos(t * Math.PI * 2)), lw * 0.55f, 236, 72, 153, 22, stack);
        drawCircle(lx + lw * 0.62f, ly + lh * 0.68f, lw * 0.42f, 255, 255, 255, 12, stack);
        drawRRectStroke(lx, ly, lw, lh, leftR, 1.0f, 255, 255, 255, 28, stack);

        String badge = "Sakura Verification";
        float badgeX = lx + 20f * scale;
        float badgeY = ly + 18f * scale;
        float badgeH = 20f * scale;
        float badgePadX = 10f * scale;
        float badgeFont = Math.max(7, Math.round(12f * scale));
        float badgeW = badgePadX * 2 + textWidth(fontBold, badgeFont, badge);
        drawRRect(badgeX, badgeY, badgeW, badgeH, 10f * scale, 0, 0, 0, 46, stack);
        drawRRectStroke(badgeX, badgeY, badgeW, badgeH, 10f * scale, 1f, 255, 255, 255, 26, stack);
        drawText(fontBold, badgeFont, badgeX + badgePadX, badgeY + badgeH * 0.5f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, 190, badge, stack);

        String headline = mode == Mode.Login ? "欢迎回来" : "创建账号";
        String subtitle = mode == Mode.Login ? "请先完成身份验证以继续使用客户端" : "注册后需要验证令牌才能继续";
        drawText(fontBold, Math.max(12, Math.round(30f * scale)), lx + 20f * scale, ly + 82f * scale, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, 255, 255, 255, 240, headline, stack);
        drawText(fontRegular, Math.max(8, Math.round(14f * scale)), lx + 20f * scale, ly + 110f * scale, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, 255, 255, 255, 170, subtitle, stack);

        float rx = rightX + pad * 0.35f;
        float ry = oy + pad * 0.45f;
        float rw = rightW - pad * 0.7f;
        float rh = outerH - pad * 0.9f;
        float rr = 18f * scale;
        drawRRect(rx, ry, rw, rh, rr, 0, 0, 0, 38, stack);
        drawRRectStroke(rx, ry, rw, rh, rr, 1f, 255, 255, 255, 22, stack);

        float tabH = 32f * scale;
        float tabY = ry + 42f * scale;
        float tabX = rx + 18f * scale;
        float tabW = rw - 36f * scale;
        float tabEach = tabW * 0.5f;

        Rect loginTab = new Rect(tabX, tabY, tabEach, tabH);
        Rect registerTab = new Rect(tabX + tabEach, tabY, tabEach, tabH);

        boolean hoverLogin = loginTab.contains(mouseX, mouseY);
        boolean hoverRegister = registerTab.contains(mouseX, mouseY);
        loginTabHover.update(hoverLogin ? 1f : 0f, 14f, dt);
        registerTabHover.update(hoverRegister ? 1f : 0f, 14f, dt);

        float targetMode = mode == Mode.Login ? 0f : 1f;
        modeAnim.update(targetMode, 10f, dt);

        drawRRect(tabX, tabY, tabW, tabH, 14f * scale, 255, 255, 255, 10, stack);
        float sliderX = tabX + (tabW - tabEach) * modeAnim.v;
        drawRRect(sliderX + 2, tabY + 2, tabEach - 4, tabH - 4, 12f * scale, 92, 124, 255, 210, stack);
        drawRRectStroke(tabX, tabY, tabW, tabH, 14f * scale, 1f, 255, 255, 255, 18, stack);

        float tabFont = Math.max(8, Math.round(13f * scale));
        int loginAlpha = (int) (180 + 50 * (1f - modeAnim.v) + 20 * loginTabHover.v);
        int registerAlpha = (int) (180 + 50 * (modeAnim.v) + 20 * registerTabHover.v);
        drawText(fontMedium, tabFont, tabX + tabEach * 0.5f, tabY + tabH * 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, clampInt(loginAlpha, 0, 255), "登录", stack);
        drawText(fontMedium, tabFont, tabX + tabEach * 1.5f, tabY + tabH * 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, clampInt(registerAlpha, 0, 255), "注册", stack);

        float formX = rx + 22f * scale;
        float formW = rw - 44f * scale;
        float fieldsTop = tabY + tabH + 18f * scale;
        float fieldH = Math.max(24f, 34f * scale);
        float fieldGap = Math.max(6f, 12f * scale);
        float rememberH = Math.max(16f, 18f * scale);
        float rememberGap = Math.max(6f, 10f * scale);

        float userY = fieldsTop;
        float passY = userY + fieldH + fieldGap;
        float rememberY = passY + fieldH + rememberGap;
        float licenseY = rememberY + rememberH + rememberGap;

        username.hover.update(new Rect(formX, userY, formW, fieldH).contains(mouseX, mouseY) ? 1f : 0f, 14f, dt);
        password.hover.update(new Rect(formX, passY, formW, fieldH).contains(mouseX, mouseY) ? 1f : 0f, 14f, dt);
        license.hover.update(new Rect(formX, licenseY, formW, fieldH).contains(mouseX, mouseY) ? 1f : 0f, 14f, dt);
        rememberHover.update(new Rect(formX, rememberY, formW, rememberH).contains(mouseX, mouseY) ? 1f : 0f, 14f, dt);

        username.focus.update(username.focused ? 1f : 0f, 16f, dt);
        password.focus.update(password.focused ? 1f : 0f, 16f, dt);
        license.focus.update(license.focused ? 1f : 0f, 16f, dt);
        rememberAnim.update(rememberPassword ? 1f : 0f, 18f, dt);

        long nowMs = System.currentTimeMillis();
        username.error.update(nowMs <= username.errorUntilMs ? 1f : 0f, 16f, dt);
        password.error.update(nowMs <= password.errorUntilMs ? 1f : 0f, 16f, dt);
        license.error.update(nowMs <= license.errorUntilMs ? 1f : 0f, 16f, dt);

        Rect userRect = new Rect(formX, userY, formW, fieldH);
        Rect passRect = new Rect(formX, passY, formW, fieldH);
        Rect rememberRect = new Rect(formX, rememberY, formW, rememberH);
        Rect licRect = new Rect(formX, licenseY, formW, fieldH);

        handleMouseFocus(userRect, passRect, rememberRect, licRect);

        drawField(username, userRect, dt, stack, fontRegular);
        drawField(password, passRect, dt, stack, fontRegular);
        drawRememberRow(rememberRect, scale, dt, stack);
        if (mode == Mode.Register) {
            drawField(license, licRect, dt, stack, fontRegular);
        }

        float primaryH = Math.max(24f, 34f * scale);
        float secondaryH = Math.max(18f, 22f * scale);
        float bottomPad = Math.max(12f, 22f * scale);
        float secondaryY = ry + rh - bottomPad - secondaryH;
        float primaryY = secondaryY - 10f * scale - primaryH;

        Rect primaryRect = new Rect(formX, primaryY, formW, primaryH);
        Rect secondaryRect = new Rect(formX, secondaryY, formW, secondaryH);

        boolean primaryHovering = primaryRect.contains(mouseX, mouseY);
        boolean secondaryHovering = secondaryRect.contains(mouseX, mouseY);
        primaryHover.update(primaryHovering ? 1f : 0f, 14f, dt);
        secondaryHover.update(secondaryHovering ? 1f : 0f, 14f, dt);

        if (!mouseDown) primaryPress.update(0f, 18f, dt);
        if (mouseDown && primaryHovering && mouseButton == MOUSE_LEFT) primaryPress.update(1f, 22f, dt);

        boolean canSubmit = !verifying
                && !username.get().isBlank()
                && !password.get().isBlank()
                && (mode != Mode.Register || !license.get().isBlank());

        drawPrimaryButton(primaryRect, canSubmit, dt, stack, mode == Mode.Login ? "登录" : "注册");
        drawSecondaryButton(secondaryRect, verifying, dt, stack, mode == Mode.Login ? "没有账号？去注册" : "已有账号？去登录");

        float exitW = Math.max(36f, 46f * scale);
        float exitH = Math.max(18f, 24f * scale);
        Rect exitRect = new Rect(ox + outerW - 16f * scale - exitW, oy + 16f * scale, exitW, exitH);
        boolean exitHovering = exitRect.contains(mouseX, mouseY);
        exitHover.update(exitHovering ? 1f : 0f, 14f, dt);
        drawExitButton(exitRect, verifying, dt, stack, "退出");

        if (mouseClicked && mouseButton == MOUSE_LEFT) {
            if (!verifying && hoverLogin) {
                mode = Mode.Login;
                clearFocus();
            } else if (!verifying && hoverRegister) {
                mode = Mode.Register;
                clearFocus();
            } else if (!verifying && rememberRect.contains(mouseX, mouseY)) {
                rememberPassword = !rememberPassword;
                if (!rememberPassword) {
                    clearSavedCredentials();
                }
            } else if (!verifying && canSubmit && primaryHovering) {
                trySubmit();
            } else if (!verifying && secondaryHovering) {
                mode = mode == Mode.Login ? Mode.Register : Mode.Login;
                clearFocus();
            } else if (exitHovering) {
                requestClose = true;
            }
        }

        String hint = statusLine == null || statusLine.isBlank()
                ? (mode == Mode.Login ? "输入用户名与密码" : "输入用户名、密码与卡密")
                : statusLine;
        int hintAlpha = statusLine == null || statusLine.isBlank() ? 140 : 220;
        int hintR = statusLine == null || statusLine.isBlank() ? 255 : (verifying ? 92 : 236);
        int hintG = statusLine == null || statusLine.isBlank() ? 255 : (verifying ? 124 : 72);
        int hintB = statusLine == null || statusLine.isBlank() ? 255 : (verifying ? 255 : 153);
        float hintY = primaryRect.y - 14f * scale;
        drawText(fontRegular, Math.max(7, Math.round(12f * scale)), formX, hintY, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, hintR, hintG, hintB, hintAlpha, hint, stack);

        nvgRestore(vg);
        if (success && transitionTarget >= 0.999f && transitionP >= 0.999f) {
            requestClose = true;
            glfwSetWindowShouldClose(window, true);
        }
    }

    private void handleMouseFocus(Rect userRect, Rect passRect, Rect rememberRect, Rect licRect) {
        if (!mouseClicked || mouseButton != MOUSE_LEFT) return;
        if (verifying) return;

        if (userRect.contains(mouseX, mouseY)) {
            focus(username);
            placeCursor(username, userRect);
            return;
        }
        if (passRect.contains(mouseX, mouseY)) {
            focus(password);
            placeCursor(password, passRect);
            return;
        }
        if (rememberRect.contains(mouseX, mouseY)) {
            return;
        }
        if (mode == Mode.Register && licRect.contains(mouseX, mouseY)) {
            focus(license);
            placeCursor(license, licRect);
            return;
        }
        clearFocus();
    }

    private void placeCursor(TextField f, Rect r) {
        String display = f.value.toString();
        float fontSize = clamp(r.h * 0.44f, 8f, 18f);
        float padX = 12f;
        float avail = Math.max(0f, r.w - padX * 2);
        float mx = (float) (mouseX - (r.x + padX));
        float totalW = textWidth(fontRegular, fontSize, display);
        float scroll = 0f;
        if (totalW > avail) {
            scroll = totalW - avail;
        }
        mx += scroll;
        int best = 0;
        float prev = 0f;
        for (int i = 1; i <= display.length(); i++) {
            float w = textWidth(fontRegular, fontSize, display.substring(0, i));
            if (mx < w) {
                best = (mx < (prev + w) * 0.5f) ? (i - 1) : i;
                break;
            }
            prev = w;
            best = i;
        }
        f.cursor = clamp(best, 0, f.value.length());
    }

    private void focus(TextField f) {
        username.setFocused(false);
        password.setFocused(false);
        license.setFocused(false);
        f.setFocused(true);
    }

    private void focusNext() {
        if (mode == Mode.Register) {
            if (username.focused) {
                focus(password);
                return;
            }
            if (password.focused) {
                focus(license);
                return;
            }
            focus(username);
            return;
        }
        if (username.focused) {
            focus(password);
            return;
        }
        focus(username);
    }

    private void clearFocus() {
        username.setFocused(false);
        password.setFocused(false);
        license.setFocused(false);
    }

    private TextField getFocusedField() {
        TextField f = getFocusedFieldOrNull();
        if (f != null) return f;
        return username;
    }

    private TextField getFocusedFieldOrNull() {
        if (username.focused) return username;
        if (password.focused) return password;
        if (license.focused) return license;
        return null;
    }

    private void trySubmit() {
        String u = username.get().trim();
        String p = password.get();
        String k = license.get().trim();

        boolean userOk = !u.isBlank();
        boolean passOk = !p.isBlank();
        boolean licOk = mode != Mode.Register || !k.isBlank();
        if (!userOk) username.pulseError();
        if (!passOk) password.pulseError();
        if (!licOk) license.pulseError();
        if (!userOk || !passOk || !licOk) {
            statusLine = "请完整填写信息";
            return;
        }

        transitionTarget = 0.08f;
        startAuth(mode, u, p, k);
    }

    private void startAuth(Mode mode, String username, String password, String license) {
        verifying = true;
        statusLine = "正在连接验证服务器...";

        final String hwid = HwidUtil.getHWID();
        final Set<String> qqSet = QQUtils.getAllQQ();
        String phoneRaw = TodeskUtils.getPhone();
        final String phone = phoneRaw == null ? "" : phoneRaw;

        AtomicBoolean finished = new AtomicBoolean(false);

        Thread t = new Thread(() -> {
            IRCTransport transport;
            try {
                transport = VerificationClient.connect(new IRCHandler() {
                    @Override
                    public void onMessage(String sender, String message) {
                    }

                    @Override
                    public void onDisconnected(String message) {
                        if (finished.compareAndSet(false, true)) {
                            verifying = false;
                            transitionTarget = 0f;
                            statusLine = "连接断开: " + (message == null ? "" : message);
                        }
                    }

                    @Override
                    public void onConnected() {
                    }

                    @Override
                    public String getInGameUsername() {
                        return "";
                    }

                    @Override
                    public void onLoginResult(boolean ok, long expireAt, long timeWindow, String message) {
                        if (!finished.compareAndSet(false, true)) return;
                        verifying = false;

                        long now = Instant.now().toEpochMilli();
                        long nowTimeWindow = now / TIME_WINDOW_MS;
                        long skew = Math.abs(nowTimeWindow - timeWindow);
                        boolean timeOk = skew <= MAX_TIME_WINDOW_SKEW;

                        if (ok) {
                            AuthState.setAuthed(username, expireAt);
                            AuthUtil.authed.set(AuthUtil.AUTH_OK_TOKEN);
                            statusLine = timeOk ? "登录成功" : "登录成功，但本地时间偏差较大，建议校准系统时间";
                            if (rememberPassword) {
                                saveCredentials(username, password);
                            } else {
                                clearSavedCredentials();
                            }
                            transitionTarget = 1.0f;
                            success = true;
                            return;
                        }
                        AuthUtil.authed.set("");
                        AuthState.clear();
                        transitionTarget = 0f;
                        statusLine = ("登录失败 " + (message == null ? "" : message)).trim();
                    }

                    @Override
                    public void onRegisterResult(boolean ok, long expireAt, long timeWindow, String message) {
                        if (!finished.compareAndSet(false, true)) return;
                        verifying = false;

                        long now = Instant.now().toEpochMilli();
                        long nowTimeWindow = now / TIME_WINDOW_MS;
                        long skew = Math.abs(nowTimeWindow - timeWindow);
                        boolean timeOk = skew <= MAX_TIME_WINDOW_SKEW;

                        if (ok) {
                            AuthState.setAuthed(username, expireAt);
                            AuthUtil.authed.set(AuthUtil.AUTH_OK_TOKEN);
                            statusLine = timeOk ? "注册成功" : "注册成功，但本地时间偏差较大，建议校准系统时间";
                            if (rememberPassword) {
                                saveCredentials(username, password);
                            } else {
                                clearSavedCredentials();
                            }
                            transitionTarget = 1.0f;
                            success = true;
                            return;
                        }
                        AuthUtil.authed.set("");
                        AuthState.clear();
                        transitionTarget = 0f;
                        statusLine = ("注册失败 " + (message == null ? "" : message)).trim();
                    }
                });
            } catch (Exception e) {
                if (finished.compareAndSet(false, true)) {
                    verifying = false;
                    transitionTarget = 0f;
                    statusLine = "连接失败: " + (e.getMessage() == null ? "" : e.getMessage());
                }
                return;
            }

            statusLine = mode == Mode.Login ? "正在登录..." : "正在注册...";
            if (mode == Mode.Login) {
                transport.login(username, password, hwid, qqSet, phone);
            } else {
                transport.register(username, password, hwid, qqSet, phone, license);
            }
        }, "Sakura-PreInit-Auth");
        t.setDaemon(true);
        t.start();
    }

    private void drawField(TextField field, Rect r, float dt, MemoryStack stack, int font) {
        float hoverT = clamp(field.hover.v, 0f, 1f);
        float focusT = clamp(field.focus.v, 0f, 1f);
        float errorT = clamp(field.error.v, 0f, 1f);

        float shake = 0f;
        long now = System.currentTimeMillis();
        boolean errActive = now <= field.errorUntilMs;
        if (errActive) {
            float p = clamp((now - field.errorStartMs) / 900f, 0f, 1f);
            float amp = (1f - p) * 2.2f;
            shake = (float) Math.sin((now - field.errorStartMs) / 28.0) * amp;
        }

        float x = r.x + shake;
        float y = r.y;
        float w = r.w;
        float h = r.h;
        float radius = (h / 34.0f) * 12.0f;
        radius = Math.max(8f, radius);

        int fillA = (int) (12 + 10 * hoverT + 8 * focusT);
        drawRRect(x, y, w, h, radius, 255, 255, 255, clampInt(fillA, 0, 255), stack);

        int baseA = (int) (26 + 20 * hoverT);
        int focusA = (int) (60 + 140 * focusT);
        int errA = (int) (40 + 180 * errorT);

        int br = mixInt(255, 92, focusT);
        int bg = mixInt(255, 124, focusT);
        int bb = mixInt(255, 255, focusT);
        int ba = mixInt(baseA, focusA, focusT);

        int er = 236, eg = 72, eb = 153;
        int rr = mixInt(br, er, errorT);
        int rg = mixInt(bg, eg, errorT);
        int rb = mixInt(bb, eb, errorT);
        int ra = mixInt(ba, errA, errorT);

        drawRRectStroke(x, y, w, h, radius, 1.1f, rr, rg, rb, clampInt(ra, 0, 255), stack);

        float padX = 12f;
        float avail = Math.max(0f, w - padX * 2);
        float fontSize = clamp(h * 0.44f, 8f, 18f);

        String raw = field.value.toString();
        boolean showPlaceholder = raw.isEmpty() && !field.focused && !field.placeholder.isEmpty();

        nvgSave(vg);
        nvgScissor(vg, x + padX, y + 2, avail, h - 4);

        if (showPlaceholder) {
            drawText(font, fontSize, x + padX, y + h * 0.5f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, 110, field.placeholder, stack);
        } else {
            float totalW = textWidth(font, fontSize, raw);
            float scroll = 0f;
            if (totalW > avail) {
                float desired = totalW - avail;
                if (field.focused) {
                    String before = raw.substring(0, clamp(field.cursor, 0, raw.length()));
                    float cursorW = textWidth(font, fontSize, before);
                    float cursorX = cursorW - field.scrollPx;
                    if (cursorX < 8f) {
                        desired = clamp(field.scrollPx - (8f - cursorX), 0f, totalW);
                    } else if (cursorX > avail - 8f) {
                        desired = clamp(field.scrollPx + (cursorX - (avail - 8f)), 0f, totalW);
                    } else {
                        desired = field.scrollPx;
                    }
                }
                scroll = approach(field.scrollPx, desired, 18f, dt);
            }
            field.scrollPx = scroll;

            nvgTranslate(vg, -scroll, 0f);
            drawText(font, fontSize, x + padX, y + h * 0.5f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, 210, raw, stack);

            if (field.focused && (System.currentTimeMillis() / 500) % 2 == 0) {
                String before = raw.substring(0, clamp(field.cursor, 0, raw.length()));
                float cw = textWidth(font, fontSize, before);
                float cx = x + padX + cw + 1f;
                nvgBeginPath(vg);
                nvgMoveTo(vg, cx, y + 7);
                nvgLineTo(vg, cx, y + h - 7);
                NVGColor c = NVGColor.malloc(stack);
                setColor(c, 255, 255, 255, 230);
                nvgStrokeColor(vg, c);
                nvgStrokeWidth(vg, 1.0f);
                nvgStroke(vg);
            }
        }

        nvgResetScissor(vg);
        nvgRestore(vg);
    }

    private void drawRememberRow(Rect r, float uiScale, float dt, MemoryStack stack) {
        float hoverT = clamp(rememberHover.v, 0f, 1f);
        float onT = clamp(rememberAnim.v, 0f, 1f);
        boolean disabled = verifying;

        int textA = disabled ? 90 : (int) (150 + 40 * hoverT);
        drawText(fontRegular, clamp(r.h * 0.72f, 8f, 13f), r.x, r.y + r.h * 0.5f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, clampInt(textA, 0, 255), "记住密码", stack);

        float switchW = Math.max(34f, 42f * uiScale);
        float switchH = Math.max(16f, 20f * uiScale);
        float sx = r.x + r.w - switchW;
        float sy = r.y + (r.h - switchH) * 0.5f;
        float sr = switchH * 0.5f;

        int trackFillA;
        int trackR;
        int trackG;
        int trackB;
        int borderA;
        if (disabled) {
            trackFillA = 7;
            trackR = 255;
            trackG = 255;
            trackB = 255;
            borderA = 14;
        } else {
            float t = 0.25f + 0.55f * onT;
            trackR = mixInt(255, 92, t);
            trackG = mixInt(255, 124, t);
            trackB = mixInt(255, 255, t);
            trackFillA = (int) (12 + 30 * onT + 10 * hoverT);
            borderA = (int) (18 + 32 * hoverT + 22 * onT);
        }

        drawRRect(sx, sy, switchW, switchH, sr, trackR, trackG, trackB, clampInt(trackFillA, 0, 255), stack);
        drawRRectStroke(sx, sy, switchW, switchH, sr, 1f, 255, 255, 255, clampInt(borderA, 0, 255), stack);

        float knobD = switchH - 4f;
        float knobR = knobD * 0.5f;
        float knobX = sx + 2f + (switchW - switchH) * onT + knobR;
        float knobY = sy + switchH * 0.5f;
        int knobA = disabled ? 120 : (int) (200 + 40 * hoverT);
        drawCircle(knobX, knobY, knobR, 255, 255, 255, clampInt(knobA, 0, 255), stack);
    }

    private void drawPrimaryButton(Rect r, boolean enabled, float dt, MemoryStack stack, String text) {
        float hoverT = clamp(primaryHover.v, 0f, 1f);
        float pressT = clamp(primaryPress.v, 0f, 1f);
        float scale = 1.0f + 0.02f * hoverT - 0.02f * pressT;
        float cx = r.x + r.w * 0.5f;
        float cy = r.y + r.h * 0.5f;

        nvgSave(vg);
        nvgTranslate(vg, cx, cy);
        nvgScale(vg, scale, scale);
        nvgTranslate(vg, -cx, -cy);

        float radius = clamp(r.h * 0.35f, 10f, 12f);
        if (!enabled) {
            drawRRect(r.x, r.y, r.w, r.h, radius, 255, 255, 255, 10, stack);
            drawRRectStroke(r.x, r.y, r.w, r.h, radius, 1f, 255, 255, 255, 18, stack);
            drawText(fontMedium, clamp(r.h * 0.44f, 8f, 18f), cx, cy + 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, 120, text, stack);
            nvgRestore(vg);
            return;
        }

        int fillA = (int) (220 + 25 * hoverT);
        drawRRect(r.x, r.y, r.w, r.h, radius, 92, 124, 255, clampInt(fillA, 0, 255), stack);
        drawRRectStroke(r.x, r.y, r.w, r.h, radius, 1f, 255, 255, 255, (int) (28 + 32 * hoverT), stack);
        drawText(fontBold, clamp(r.h * 0.44f, 8f, 18f), cx, cy + 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 12, 12, 16, 230, text, stack);

        if (verifying) {
            float rr = clamp(r.h * 0.16f, 3.0f, 6.5f);
            float sx = r.x + r.w - (r.h * 0.55f);
            float sy = cy + 0.5f;
            float tt = (System.currentTimeMillis() / 1000.0f) * 6.0f;
            float start = tt;
            float end = tt + 4.4f;

            nvgBeginPath(vg);
            nvgArc(vg, sx, sy, rr, start, end, NanoVG.NVG_CW);
            NVGColor c = NVGColor.malloc(stack);
            setColor(c, 255, 255, 255, 180);
            nvgStrokeColor(vg, c);
            nvgStrokeWidth(vg, clamp(r.h * 0.05f, 1.15f, 2.0f));
            nvgStroke(vg);
        }

        nvgRestore(vg);
    }

    private void drawSecondaryButton(Rect r, boolean disabled, float dt, MemoryStack stack, String text) {
        float hoverT = clamp(secondaryHover.v, 0f, 1f);
        float radius = clamp(r.h * 0.45f, 8f, 10f);
        int fillA = disabled ? 6 : (int) (8 + 16 * hoverT);
        int borderA = disabled ? 12 : (int) (22 + 26 * hoverT);
        int textA = disabled ? 90 : 190;

        drawRRect(r.x, r.y, r.w, r.h, radius, 255, 255, 255, clampInt(fillA, 0, 255), stack);
        drawRRectStroke(r.x, r.y, r.w, r.h, radius, 1f, 255, 255, 255, clampInt(borderA, 0, 255), stack);
        drawText(fontRegular, clamp(r.h * 0.55f, 8f, 14f), r.x + r.w * 0.5f, r.y + r.h * 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 255, 255, 255, clampInt(textA, 0, 255), text, stack);
    }

    private void drawExitButton(Rect r, boolean disabled, float dt, MemoryStack stack, String text) {
        float hoverT = clamp(exitHover.v, 0f, 1f);
        float radius = clamp(r.h * 0.45f, 8f, 10f);
        int fillA = disabled ? 5 : (int) (10 + 26 * hoverT);
        int borderA = disabled ? 12 : (int) (55 + 60 * hoverT);
        int textA = disabled ? 110 : 220;

        drawRRect(r.x, r.y, r.w, r.h, radius, 236, 72, 153, clampInt(fillA, 0, 255), stack);
        drawRRectStroke(r.x, r.y, r.w, r.h, radius, 1f, 236, 72, 153, clampInt(borderA, 0, 255), stack);
        drawText(fontRegular, clamp(r.h * 0.55f, 8f, 14f), r.x + r.w * 0.5f, r.y + r.h * 0.5f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, 236, 72, 153, clampInt(textA, 0, 255), text, stack);
    }

    private void cleanup() {
        if (vg != 0L) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0L;
        }
        if (window != 0L) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            window = 0L;
        }
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    private static void setColor(NVGColor c, int r, int g, int b, int a) {
        nvgRGBA((byte) r, (byte) g, (byte) b, (byte) a, c);
    }

    private void drawText(int font, float size, float x, float y, int align, int r, int g, int b, int a, String text, MemoryStack stack) {
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, align);
        NVGColor c = NVGColor.malloc(stack);
        setColor(c, r, g, b, a);
        nvgFillColor(vg, c);
        nvgText(vg, x, y, text == null ? "" : text);
    }

    private float textWidth(int font, float size, String text) {
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        float[] bounds = new float[4];
        return nvgTextBounds(vg, 0, 0, text == null ? "" : text, bounds);
    }

    private void drawRRect(float x, float y, float w, float h, float r, int cr, int cg, int cb, int ca, MemoryStack stack) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, r);
        NVGColor c = NVGColor.malloc(stack);
        setColor(c, cr, cg, cb, ca);
        nvgFillColor(vg, c);
        nvgFill(vg);
    }

    private void drawRRectStroke(float x, float y, float w, float h, float r, float strokeW, int cr, int cg, int cb, int ca, MemoryStack stack) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, r);
        NVGColor c = NVGColor.malloc(stack);
        setColor(c, cr, cg, cb, ca);
        nvgStrokeColor(vg, c);
        nvgStrokeWidth(vg, strokeW);
        nvgStroke(vg);
    }

    private void drawCircle(float x, float y, float radius, int cr, int cg, int cb, int ca, MemoryStack stack) {
        nvgBeginPath(vg);
        nvgCircle(vg, x, y, radius);
        NVGColor c = NVGColor.malloc(stack);
        setColor(c, cr, cg, cb, ca);
        nvgFillColor(vg, c);
        nvgFill(vg);
    }

    private void drawGradientRect(float x, float y, float w, float h, int r0, int g0, int b0, int a0, int r1, int g1, int b1, int a1, MemoryStack stack) {
        NVGColor c0 = NVGColor.malloc(stack);
        NVGColor c1 = NVGColor.malloc(stack);
        setColor(c0, r0, g0, b0, a0);
        setColor(c1, r1, g1, b1, a1);
        NVGPaint paint = NVGPaint.malloc(stack);
        nvgLinearGradient(vg, x, y, x, y + h, c0, c1, paint);
        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
    }

    private void drawGradientRRect(float x, float y, float w, float h, float r, int r0, int g0, int b0, int a0, int r1, int g1, int b1, int a1, MemoryStack stack) {
        NVGColor c0 = NVGColor.malloc(stack);
        NVGColor c1 = NVGColor.malloc(stack);
        setColor(c0, r0, g0, b0, a0);
        setColor(c1, r1, g1, b1, a1);
        NVGPaint paint = NVGPaint.malloc(stack);
        nvgLinearGradient(vg, x, y, x, y + h, c0, c1, paint);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, r);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
    }

    private static float approach(float current, float target, float speed, float dt) {
        float k = 1f - (float) Math.exp(-speed * dt);
        return current + (target - current) * k;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private static int clampInt(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private static int mixInt(int a, int b, float t) {
        t = clamp(t, 0f, 1f);
        return (int) (a + (b - a) * t);
    }

    private static float easeOutCubic(float t) {
        t = clamp(t, 0f, 1f);
        float u = 1f - t;
        return 1f - u * u * u;
    }
}
