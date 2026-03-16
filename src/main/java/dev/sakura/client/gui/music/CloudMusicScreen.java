package dev.sakura.client.gui.music;

import dev.sakura.client.module.impl.client.CloudMusicGui;
import dev.sakura.client.music.CloudMusicPlayer;
import dev.sakura.client.music.CloudMusicService;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.utils.TranslationManager;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.CharInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class CloudMusicScreen extends Screen {
    private enum Tab {
        Recommend, Search
    }

    private static final String PLAY_ICON = "b";
    private static final String PAUSE_ICON = "c";
    private static final String NEXT_ICON = "e";
    private static final String PREV_ICON = "f";

    private final CloudMusicGui owner;
    private final CloudMusicService service;
    private final CloudMusicPlayer player;
    private final Map<String, Integer> imageTextures = new HashMap<>();
    private final Map<String, byte[]> pendingImageBytes = new ConcurrentHashMap<>();
    private final Set<String> loadingImages = new HashSet<>();
    private final List<CloudMusicService.PlaylistCard> recommendCards = new ArrayList<>();
    private final List<CloudMusicService.PlaylistCard> userPlaylists = new ArrayList<>();
    private final List<CloudMusicService.SongItem> currentSongs = new ArrayList<>();
    private final Map<Long, Rect> recommendCardRects = new HashMap<>();
    private final Map<String, Rect> playlistBarRects = new HashMap<>();
    private final Map<Integer, Rect> songRowRects = new HashMap<>();
    private final Map<String, Rect> staticRects = new HashMap<>();
    private final Map<String, Float> hoverProgresses = new HashMap<>();

    private CloudMusicService.PlaylistDetail currentPlaylistDetail;
    private CloudMusicService.QrLoginState qrState;
    private Tab currentTab = Tab.Recommend;
    private String statusText = TranslationManager.get("cloudmusic.status.connecting");
    private String errorText = "";
    private boolean loginReady = false;
    private boolean loadingMainData = false;
    private boolean checkingQr = false;
    private long nextQrCheckAt = 0L;
    private String qrTextureKey = "";
    private float windowX = -1, windowY = -1;
    private float windowW = 600f, windowH = 400f;
    private float sidebarW = 120f;
    private boolean dragging = false;
    private float dragX, dragY;
    private float openAnimation = 0f;
    private long startTime = -1;
    private boolean closing = false;
    private long closeTime = -1;

    private boolean showLoginOverlay = false;
    private boolean searchEditing = false;
    private String searchText = "";
    private int searchCursorPos = 0;
    private long searchBlinkAt = 0L;
    private boolean searchCursorVisible = true;
    private boolean searchLoading = false;
    private Rect searchInputRect;
    private Rect searchButtonRect;

    private float playlistScrollCurrent = 0f;
    private float playlistScrollTarget = 0f;
    private float songScrollCurrent = 0f;
    private float songScrollTarget = 0f;
    private float sidebarScrollCurrent = 0f;
    private float sidebarScrollTarget = 0f;
    private float scrollSmoothFactor = 0.15f;
    private Rect playlistScrollArea;
    private Rect songScrollArea;
    private Rect sidebarScrollArea;

    public CloudMusicScreen(CloudMusicGui owner, CloudMusicService service, CloudMusicPlayer player) {
        super(Text.literal("CloudMusicScreen"));
        this.owner = owner;
        this.service = service;
        this.player = player;
    }

    @Override
    protected void init() {
        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }
        if (windowX == -1 || windowY == -1) {
            windowX = (width - windowW) * 0.5f;
            windowY = (height - windowH) * 0.5f;
        }
        bootstrap();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        // No background render
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        pollQrStatus();
        updateSmoothScroll();
        
        float ease;
        if (closing) {
            float progress = clamp((System.currentTimeMillis() - closeTime) / 300f, 0f, 1f);
            ease = (float) (1.0f - Math.pow(progress, 3)); // Cubic ease in (reverse)
            if (progress >= 1.0f) {
                if (owner != null) {
                    owner.setState(false);
                } else {
                    super.close();
                }
                return;
            }
        } else {
            long elapsed = System.currentTimeMillis() - startTime;
            openAnimation = clamp(elapsed / 400f, 0f, 1f);
            ease = (float) (1.0 - Math.pow(1.0 - openAnimation, 3)); // Cubic ease out
        }

        float x = windowX;
        float y = windowY + (1.0f - ease) * 20f; // Slide up/down
        float w = windowW;
        float h = windowH;
        float r = 16f;

        // --- Render Shaders first (Before NanoVG) ---
        float shadowRange = CloudMusicGui.shadowRange.get().floatValue();
        if (shadowRange > 0) {
            int tick = (int) ((System.currentTimeMillis() / 20.0) * CloudMusicGui.gradientSpeed.get().floatValue());
            Color c1 = dev.sakura.client.module.impl.client.ClickGui.color(tick);
            Color c2 = dev.sakura.client.module.impl.client.ClickGui.color2(tick);
            float[] rects = new float[]{x, y, w, h};
            float[] radii = new float[]{r};
            ShadowShader.drawStairShadowGradient(
                    x, y, w, h,
                    shadowRange, 0.6f * ease,
                    new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                    new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255),
                    rects, radii, 1
            );
        }

        float blurStrength = CloudMusicGui.blurStrength.get().floatValue();
        if (blurStrength > 0) {
            // Main window blur
            BlurShader.drawRoundedBlur(x, y, w, h, r, new Color(0, 0, 0, 0), blurStrength, ease);
        }

        if (showLoginOverlay) {
            // Login overlay blur - extremely strong to hide background UI
            BlurShader.drawRoundedBlur(x, y, w, h, r, new Color(0, 0, 0, 150), 40f, ease);
        }

        // Extra blur/shadow for Search controls (outside NVG batch to avoid pipeline mixing)
        if (currentTab == Tab.Search) {
            float contentX = x + sidebarW;
            float contentY = y + 50f;
            float contentW = w - sidebarW;
            float contentH = h - 50f - 80f;
            float inputH = 28f;
            float btnW = 86f;
            float inputX = contentX + 12f;
            float inputY = contentY + 12f;
            float inputW = contentW - 24f - btnW - 10f;
            float pillRadius = inputH * 0.5f;
            float btnX = inputX + inputW + 10f;
            float btnY = inputY;
            float searchShadowRange = Math.max(6f, CloudMusicGui.shadowRange.get().floatValue());
            float bgBlur = CloudMusicGui.blurStrength.get().floatValue();
            float searchBlur = Math.min(32f, Math.max(12f, bgBlur + 6f));
            int tick = (int) ((System.currentTimeMillis() / 20.0) * CloudMusicGui.gradientSpeed.get().floatValue());
            Color c1 = dev.sakura.client.module.impl.client.ClickGui.color(tick);
            Color c2 = dev.sakura.client.module.impl.client.ClickGui.color2(tick);
            float[] rects = new float[]{inputX, inputY, inputW, inputH, btnX, btnY, btnW, inputH};
            float[] radii = new float[]{pillRadius, pillRadius};
            float unionX = inputX;
            float unionY = inputY;
            float unionW = (btnX + btnW) - inputX;
            float unionH = inputH;
            BlurShader.drawSegmentedBlur(unionX, unionY, unionW, unionH, 0f, new Color(0, 0, 0, 0), searchBlur, 1.0f, rects, radii, 2);
            ShadowShader.drawStairShadowGradient(
                    inputX, inputY, inputW, inputH,
                    searchShadowRange * 0.8f,
                    0.65f * ease,
                    new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                    new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255),
                    new float[]{inputX, inputY, inputW, inputH},
                    new float[]{pillRadius},
                    1
            );
            ShadowShader.drawStairShadowGradient(
                    btnX, btnY, btnW, inputH,
                    searchShadowRange * 0.8f,
                    0.65f * ease,
                    new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                    new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255),
                    new float[]{btnX, btnY, btnW, inputH},
                    new float[]{pillRadius},
                    1
            );
        }
        // --- Render UI (NanoVG) ---
        NanoVGRenderer.INSTANCE.draw(vg -> {
            flushPendingImages();
            
            NanoVG.nvgGlobalAlpha(vg, ease);

            staticRects.clear();
            recommendCardRects.clear();
            playlistBarRects.clear();
            songRowRects.clear();

            // Header
            drawModernHeader(x, y, w, 50f);
            
            float contentX = x + sidebarW;
            float contentY = y + 50f;
            float contentW = w - sidebarW;
            float contentH = h - 50f - 80f; // 80 for bottom bar
            
            // Sidebar
            drawSidebar(x, contentY, sidebarW, contentH, mouseX, mouseY);
            
            if (loadingMainData) {
                NanoVGHelper.drawCenteredString(TranslationManager.get("cloudmusic.loading"), contentX + contentW * 0.5f, contentY + contentH * 0.5f, FontLoader.bold(), 18f, new Color(200, 200, 220, 200));
            } else {
                drawModernContent(contentX, contentY, contentW, contentH, mouseX, mouseY);
            }

            // Login Overlay
            if (showLoginOverlay) {
                drawLoginOverlay(x, y, w, h);
            }

            // Bottom Player
            drawModernBottomPlayer(x + 10, y + h - 70, w - 20, 60, mouseX, mouseY);
            
            drawErrorToast(x + 10, y + 60);
            
            NanoVG.nvgGlobalAlpha(vg, 1.0f);
        });
    }

    @Override
    public void close() {
        if (!closing) {
            closing = true;
            closeTime = System.currentTimeMillis();
        }
    }

    private void drawModernHeader(float x, float y, float w, float h) {
        float titleX = x + 18f;
        float titleCenterY = y + h * 0.5f;
        if (CloudMusicGui.titleTextShadow.get()) {
            float dist = CloudMusicGui.titleTextShadowDist.get().floatValue();
            NanoVGHelper.drawString("Sakura", titleX + dist, titleCenterY - 6f + dist, FontLoader.bold(), 20f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(0, 0, 0, 160));
        }
        drawGradientGlowText("Sakura", titleX, titleCenterY - 6f, FontLoader.bold(), 20f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGHelper.drawString("Music", titleX, titleCenterY + 12f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(180, 180, 200));
        
        float closeSize = 24f;
        float closeX = x + w - 18f - closeSize;
        float closeY = y + (h - closeSize) * 0.5f;
        
        // Profile / Login Section
        float profileRightX = closeX - 15f;
        CloudMusicService.UserProfile profile = service.getProfile();
        
        if (loginReady && profile != null) {
            // Logged In: [Avatar] [Nickname (Glow)]
            String nickname = profile.nickname();
            String avatarUrl = profile.avatarUrl();
            
            float avatarSize = 24f;
            float nameSize = 13f;
            float nameW = NanoVGHelper.getTextWidth(nickname, FontLoader.bold(), nameSize);
            
            float sectionW = avatarSize + 8f + nameW;
            float sectionX = profileRightX - sectionW;
            float avatarX = sectionX;
            float avatarY = y + (h - avatarSize) * 0.5f;
            
            int avatarTex = requestUrlTexture(avatarUrl);
            if (avatarTex > 0) {
                NanoVGHelper.drawImage(avatarTex, avatarX, avatarY, avatarSize, avatarSize, avatarSize * 0.5f, 1f);
            } else {
                NanoVGHelper.drawCircle(avatarX + avatarSize * 0.5f, avatarY + avatarSize * 0.5f, avatarSize * 0.5f, new Color(255, 255, 255, 20));
            }
            
            drawGlowText(nickname, avatarX + avatarSize + 8f, y + h * 0.5f, FontLoader.bold(), nameSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE, -1f, 1.0f);
            
            staticRects.put("login_btn", new Rect(sectionX, y + (h - avatarSize) * 0.5f, sectionW, avatarSize));
        } else {
            // Not Logged In: "登录" Button
            float loginW = 50f;
            float loginH = 24f;
            float loginX = profileRightX - loginW;
            float loginY = y + (h - loginH) * 0.5f;
            
            float loginProgress = hoverProgresses.getOrDefault("login_btn", 0f);
            if (loginProgress > 0.01f) {
                float lineW = loginW * 0.8f * loginProgress;
                float lineX = loginX + loginW * 0.5f - lineW * 0.5f;
                float lineY = loginY + loginH - 2;
                
                // Shadow effect for the line
                long vg = NanoVGRenderer.INSTANCE.getContext();
                try (org.lwjgl.system.MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
                    org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                    org.lwjgl.nanovg.NanoVG.nvgBoxGradient(vg, lineX, lineY, lineW, 1.5f, 1f, 4f, 
                        NanoVGHelper.nvgColor(new Color(255, 255, 255, (int) (loginProgress * 120))), 
                        NanoVGHelper.nvgColor(new Color(255, 255, 255, 0)), paint);
                    org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
                    org.lwjgl.nanovg.NanoVG.nvgRect(vg, lineX - 6, lineY - 6, lineW + 12, 1.5f + 12);
                    org.lwjgl.nanovg.NanoVG.nvgFillPaint(vg, paint);
                    org.lwjgl.nanovg.NanoVG.nvgFill(vg);
                }
                
                NanoVGHelper.drawRect(lineX, lineY, lineW, 1.5f, new Color(255, 255, 255, (int) (loginProgress * 255)));
            }
            
            drawGlowText(TranslationManager.get("cloudmusic.login"), loginX + loginW * 0.5f, loginY + loginH * 0.5f, FontLoader.bold(), 13f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE, -1f, loginProgress);
            staticRects.put("login_btn", new Rect(loginX, loginY, loginW, loginH));
        }

        boolean hovered = lastMouseX >= closeX && lastMouseX <= closeX + closeSize && lastMouseY >= closeY && lastMouseY <= closeY + closeSize;
        // Close button: only icon color change or slight outline
        NanoVGHelper.drawCenteredString("×", closeX + closeSize * 0.5f, closeY + closeSize * 0.5f, FontLoader.regular(), 18f, hovered ? new Color(255, 80, 80) : Color.WHITE);
        staticRects.put("close", new Rect(closeX, closeY, closeSize, closeSize));
    }

    private double lastMouseX, lastMouseY;
    private boolean volumeDragging = false;

    private void drawLoginOverlay(float x, float y, float w, float h) {
        // Overlay background (Removed dark block)
        // NanoVGHelper.drawRect(x, y, w, h, new Color(0, 0, 0, 200));
        
        float qrSize = 160f;
        float qrX = x + (w - qrSize) * 0.5f;
        float qrY = y + (h - qrSize) * 0.5f - 20f;
        
        // NanoVGHelper.drawRoundRect(qrX - 6, qrY - 6, qrSize + 12, qrSize + 12, 12f, new Color(255, 255, 255, 10));
        // NanoVGHelper.drawRoundRectOutline(qrX - 6, qrY - 6, qrSize + 12, qrSize + 12, 12f, 1f, new Color(255, 255, 255, 50));
        
        int qrTex = requestQrTexture();
        if (qrTex > 0) {
            NanoVGHelper.drawImage(qrTex, qrX, qrY, qrSize, qrSize, 8f, 1f);
        } else {
            // NanoVGHelper.drawRoundRect(qrX, qrY, qrSize, qrSize, 8f, new Color(40, 42, 54, 200));
            // NanoVGHelper.drawRoundRectOutline(qrX, qrY, qrSize, qrSize, 8f, 1f, new Color(255, 255, 255, 30));
            NanoVGHelper.drawCenteredString(qrTex == 0 ? TranslationManager.get("cloudmusic.qr.loading") : TranslationManager.get("cloudmusic.qr.parse_error"), qrX + qrSize * 0.5f, qrY + qrSize * 0.5f, FontLoader.regular(), 14f, new Color(180, 180, 200));
        }
        
        NanoVGHelper.drawCenteredString(statusText, x + w * 0.5f, qrY + qrSize + 30f, FontLoader.regular(), 14f, new Color(180, 180, 200));
        
        float btnW = 120f, btnH = 32f;
        float btnX = x + (w - btnW) * 0.5f;
        float btnY = qrY + qrSize + 60f;
        
        boolean hovered = lastMouseX >= btnX && lastMouseX <= btnX + btnW && lastMouseY >= btnY && lastMouseY <= btnY + btnH;
        // NanoVGHelper.drawRoundRect(btnX, btnY, btnW, btnH, 8f, hovered ? new Color(255, 60, 80, 240) : new Color(255, 60, 80, 200));
        // NanoVGHelper.drawRoundRectOutline(btnX, btnY, btnW, btnH, 8f, 1f, hovered ? new Color(255, 60, 80, 255) : new Color(255, 60, 80, 150));
        NanoVGHelper.drawCenteredString(TranslationManager.get("cloudmusic.qr.refresh"), btnX + btnW * 0.5f, btnY + btnH * 0.5f, FontLoader.bold(), 14f, Color.WHITE);
        staticRects.put("refresh_qr", new Rect(btnX, btnY, btnW, btnH));
        
        // Close Overlay Button
        float closeOverlaySize = 30f;
        float closeOverlayX = x + w - closeOverlaySize - 10f;
        float closeOverlayY = y + 10f;
        boolean closeHover = lastMouseX >= closeOverlayX && lastMouseX <= closeOverlayX + closeOverlaySize && lastMouseY >= closeOverlayY && lastMouseY <= closeOverlayY + closeOverlaySize;
        // NanoVGHelper.drawRoundRect(closeOverlayX, closeOverlayY, closeOverlaySize, closeOverlaySize, 6f, closeHover ? new Color(255, 255, 255, 30) : new Color(255, 255, 255, 10));
        NanoVGHelper.drawCenteredString("×", closeOverlayX + closeOverlaySize * 0.5f, closeOverlayY + closeOverlaySize * 0.5f, FontLoader.bold(), 18f, closeHover ? new Color(255, 80, 80) : Color.WHITE);
        staticRects.put("close_overlay", new Rect(closeOverlayX, closeOverlayY, closeOverlaySize, closeOverlaySize));
    }

    private void drawSidebar(float x, float y, float w, float h, int mouseX, int mouseY) {
        float itemH = 32f;
        float itemPadding = 6f;
        sidebarScrollArea = new Rect(x, y, w, h);
        
        NanoVGHelper.save();
        NanoVGHelper.scissor(x, y, w, h);
        
        float currentY = y + 10f - sidebarScrollCurrent * itemH;
        
        // Recommend Item
        String recId = "tab_recommend";
        boolean recActive = currentTab == Tab.Recommend && currentPlaylistDetail == null;
        drawSidebarItem(x, currentY, w, itemH, TranslationManager.get("cloudmusic.sidebar.recommend"), recId, recActive);
        currentY += itemH + itemPadding;
        
        // Search Item
        String searchId = "tab_search";
        boolean searchActive = currentTab == Tab.Search;
        drawSidebarItem(x, currentY, w, itemH, TranslationManager.get("cloudmusic.sidebar.search"), searchId, searchActive);
        currentY += itemH + itemPadding;
        
        // Horizontal Line
        if (currentY > y && currentY < y + h) {
            NanoVGHelper.drawRect(x + 15, currentY + 5, w - 30, 1, new Color(255, 255, 255, 30));
        }
        currentY += 15f;
        
        // My Playlists Section Label
        if (currentY + 20 > y && currentY < y + h) {
            NanoVGHelper.drawString(TranslationManager.get("cloudmusic.sidebar.playlists"), x + 20, currentY, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, new Color(120, 120, 140));
        }
        currentY += 20f;
        
        // Playlists
        playlistBarRects.clear();
        for (CloudMusicService.PlaylistCard card : userPlaylists) {
            String id = "playlist_" + card.id();
            boolean active = currentPlaylistDetail != null && currentPlaylistDetail.id() == card.id();
            
            if (currentY + itemH > y && currentY < y + h) {
                drawSidebarItem(x, currentY, w, itemH, card.name(), id, active, card.coverUrl());
                playlistBarRects.put(id, new Rect(x + 10, currentY, w - 20, itemH));
            }
            currentY += itemH + itemPadding;
        }
        
        // Update sidebar scroll target max
        float totalH = (currentY - (y + 10f - sidebarScrollCurrent * itemH));
        sidebarScrollTarget = clamp(sidebarScrollTarget, 0, Math.max(0, (totalH - h) / itemH));
        
        NanoVGHelper.restore();
    }

    private void drawSidebarItem(float x, float y, float w, float h, String text, String id, boolean active) {
        drawSidebarItem(x, y, w, h, text, id, active, null);
    }

    private void drawSidebarItem(float x, float y, float w, float h, String text, String id, boolean active, String iconUrl) {
        float progress = hoverProgresses.getOrDefault(id, 0f);
        float itemX = x + 10;
        float itemW = w - 20;
        
        staticRects.put(id, new Rect(itemX, y, itemW, h));
        
        // Hover Line Animation with Glow
        if (progress > 0.01f && id.startsWith("tab_")) {
            float lineW = itemW * 0.8f * progress;
            float lineX = itemX + itemW * 0.5f - lineW * 0.5f;
            float lineY = y + h - 2;
            
            // Draw a high-quality glow using nvgBoxGradient to simulate ShadowShader inside scissor
            long vg = NanoVGRenderer.INSTANCE.getContext();
            try (org.lwjgl.system.MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                org.lwjgl.nanovg.NanoVG.nvgBoxGradient(vg, lineX, lineY, lineW, 1.5f, 1f, 4f, 
                    NanoVGHelper.nvgColor(new Color(255, 255, 255, (int) (progress * 120))), 
                    NanoVGHelper.nvgColor(new Color(255, 255, 255, 0)), paint);
                org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
                org.lwjgl.nanovg.NanoVG.nvgRect(vg, lineX - 6, lineY - 6, lineW + 12, 1.5f + 12);
                org.lwjgl.nanovg.NanoVG.nvgFillPaint(vg, paint);
                org.lwjgl.nanovg.NanoVG.nvgFill(vg);
            }
            
            NanoVGHelper.drawRect(lineX, lineY, lineW, 1.5f, new Color(255, 255, 255, (int) (progress * 255)));
        }
        
        float textX;
        int align;
        float imgSize = h - 8f;
        
        if (iconUrl != null) {
            textX = itemX + 10;
            int tex = requestUrlTexture(iconUrl);
            if (tex > 0) {
                NanoVGHelper.drawImage(tex, itemX + 4, y + 4, imgSize, imgSize, 4f, 1f);
            } else {
                NanoVGHelper.drawRoundRect(itemX + 4, y + 4, imgSize, imgSize, 4f, new Color(255, 255, 255, 10));
            }
            textX += imgSize + 6;
            align = NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE;
        } else {
            textX = itemX + itemW * 0.5f;
            align = NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE;
        }

        if (active) {
            drawGradientGlowText(text, textX, y + h * 0.5f, FontLoader.bold(), iconUrl != null ? 12f : 13f, align);
        } else {
            drawGlowText(trim(text, iconUrl != null ? 10 : 12), textX, y + h * 0.5f, FontLoader.bold(), iconUrl != null ? 12f : 13f, align, Color.WHITE, -1f, progress);
        }
    }

    private void drawGradientGlowText(String text, float x, float y, int font, float size, int align) {
        drawGradientGlowText(text, x, y, font, size, align, -1f, 1.0f);
    }

    private void drawGradientGlowText(String text, float x, float y, int font, float size, int align, float maxWidth) {
        drawGradientGlowText(text, x, y, font, size, align, maxWidth, 1.0f);
    }

    private void drawGradientGlowText(String text, float x, float y, int font, float size, int align, float maxWidth, float progress) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);
        
        boolean scrolling = maxWidth > 0 && textW > maxWidth;
        float scrollX = 0;
        if (scrolling) {
            float maxScroll = textW - maxWidth;
            double scrollDuration = (maxScroll / 20f) * 1000.0;
            double pauseDuration = 1000.0;
            double cycleDuration = scrollDuration * 2 + pauseDuration * 2;
            double timeInCycle = System.currentTimeMillis() % cycleDuration;

            if (timeInCycle < pauseDuration) {
                scrollX = 0;
            } else if (timeInCycle < pauseDuration + scrollDuration) {
                scrollX = (float) ((timeInCycle - pauseDuration) / scrollDuration * maxScroll);
            } else if (timeInCycle < pauseDuration * 2 + scrollDuration) {
                scrollX = maxScroll;
            } else {
                scrollX = (float) (maxScroll - ((timeInCycle - (pauseDuration * 2 + scrollDuration)) / scrollDuration * maxScroll));
            }

            NanoVG.nvgSave(vg);
            NanoVG.nvgIntersectScissor(vg, x, y - size, maxWidth, size * 2f);
            NanoVG.nvgTranslate(vg, -scrollX, 0);
        }

        float drawX = x;
        if ((align & NanoVG.NVG_ALIGN_CENTER) != 0) {
            drawX = x - textW * 0.5f;
        } else if ((align & NanoVG.NVG_ALIGN_RIGHT) != 0) {
            drawX = x - textW;
        }
        
        float speed = CloudMusicGui.gradientSpeed.get().floatValue();
        int timeTick = (int) ((System.currentTimeMillis() / 20.0) * speed);
        int startTick = timeTick + (int) (drawX * 3f);
        int endTick = timeTick + (int) ((drawX + Math.max(1f, textW)) * 3f);
        Color c1 = dev.sakura.client.module.impl.client.ClickGui.color(startTick);
        Color c2 = dev.sakura.client.module.impl.client.ClickGui.color(endTick);
        
        try (org.lwjgl.system.MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
            org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
            org.lwjgl.nanovg.NanoVG.nvgLinearGradient(vg, drawX, y, drawX + textW, y, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);
            
            // Glow
            if (progress > 0.01f) {
                org.lwjgl.nanovg.NanoVG.nvgFontFaceId(vg, font);
                org.lwjgl.nanovg.NanoVG.nvgFontSize(vg, size);
                org.lwjgl.nanovg.NanoVG.nvgTextAlign(vg, align);
                
                org.lwjgl.nanovg.NanoVG.nvgFontBlur(vg, 4f * progress);
                org.lwjgl.nanovg.NanoVG.nvgFillPaint(vg, paint);
                for (int i = 0; i < 2; i++) {
                    org.lwjgl.nanovg.NanoVG.nvgText(vg, x, y, text);
                }
            }
            
            // Main text
            org.lwjgl.nanovg.NanoVG.nvgFontBlur(vg, 0);
            org.lwjgl.nanovg.NanoVG.nvgFillPaint(vg, paint);
            org.lwjgl.nanovg.NanoVG.nvgText(vg, x, y, text);
        }

        if (scrolling) {
            NanoVG.nvgRestore(vg);
        }
    }

    private void drawGlowText(String text, float x, float y, int font, float size, int align, Color color) {
        drawGlowText(text, x, y, font, size, align, color, -1f, 1.0f);
    }

    private void drawGlowText(String text, float x, float y, int font, float size, int align, Color color, float maxWidth) {
        drawGlowText(text, x, y, font, size, align, color, maxWidth, 1.0f);
    }

    private void drawGlowText(String text, float x, float y, int font, float size, int align, Color color, float maxWidth, float progress) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);
        
        boolean scrolling = maxWidth > 0 && textW > maxWidth;
        float scrollX = 0;
        if (scrolling) {
            float maxScroll = textW - maxWidth;
            double scrollDuration = (maxScroll / 20f) * 1000.0;
            double pauseDuration = 1000.0;
            double cycleDuration = scrollDuration * 2 + pauseDuration * 2;
            double timeInCycle = System.currentTimeMillis() % cycleDuration;

            if (timeInCycle < pauseDuration) {
                scrollX = 0;
            } else if (timeInCycle < pauseDuration + scrollDuration) {
                scrollX = (float) ((timeInCycle - pauseDuration) / scrollDuration * maxScroll);
            } else if (timeInCycle < pauseDuration * 2 + scrollDuration) {
                scrollX = maxScroll;
            } else {
                scrollX = (float) (maxScroll - ((timeInCycle - (pauseDuration * 2 + scrollDuration)) / scrollDuration * maxScroll));
            }

            NanoVG.nvgSave(vg);
            NanoVG.nvgIntersectScissor(vg, x, y - size, maxWidth, size * 2f);
            NanoVG.nvgTranslate(vg, -scrollX, 0);
        }

        org.lwjgl.nanovg.NanoVG.nvgFontFaceId(vg, font);
        org.lwjgl.nanovg.NanoVG.nvgFontSize(vg, size);
        org.lwjgl.nanovg.NanoVG.nvgTextAlign(vg, align);
        
        // Glow
        if (progress > 0.01f) {
            org.lwjgl.nanovg.NanoVG.nvgFontBlur(vg, 4f * progress);
            org.lwjgl.nanovg.NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * progress))));
            for (int i = 0; i < 2; i++) {
                org.lwjgl.nanovg.NanoVG.nvgText(vg, x, y, text);
            }
        }
        
        // Main text
        org.lwjgl.nanovg.NanoVG.nvgFontBlur(vg, 0);
        org.lwjgl.nanovg.NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(color));
        org.lwjgl.nanovg.NanoVG.nvgText(vg, x, y, text);

        if (scrolling) {
            NanoVG.nvgRestore(vg);
        }
    }

    private void drawScrollingString(String text, float x, float y, int font, float size, int align, Color color, float maxWidth) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);
        
        if (maxWidth > 0 && textW > maxWidth) {
            float maxScroll = textW - maxWidth;
            double scrollDuration = (maxScroll / 20f) * 1000.0;
            double pauseDuration = 1000.0;
            double cycleDuration = scrollDuration * 2 + pauseDuration * 2;
            double timeInCycle = System.currentTimeMillis() % cycleDuration;

            float scrollX;
            if (timeInCycle < pauseDuration) {
                scrollX = 0;
            } else if (timeInCycle < pauseDuration + scrollDuration) {
                scrollX = (float) ((timeInCycle - pauseDuration) / scrollDuration * maxScroll);
            } else if (timeInCycle < pauseDuration * 2 + scrollDuration) {
                scrollX = maxScroll;
            } else {
                scrollX = (float) (maxScroll - ((timeInCycle - (pauseDuration * 2 + scrollDuration)) / scrollDuration * maxScroll));
            }

            NanoVG.nvgSave(vg);
            NanoVG.nvgIntersectScissor(vg, x, y - size, maxWidth, size * 2f);
            NanoVG.nvgTranslate(vg, -scrollX, 0);
            NanoVGHelper.drawString(text, x, y, font, size, align, color);
            NanoVG.nvgRestore(vg);
        } else {
            NanoVGHelper.drawString(text, x, y, font, size, align, color);
        }
    }

    private void drawModernContent(float x, float y, float w, float h, int mouseX, int mouseY) {
        if (!loginReady && recommendCards.isEmpty() && userPlaylists.isEmpty()) {
             NanoVGHelper.drawCenteredString(TranslationManager.get("cloudmusic.empty.not_logged_in"), x + w * 0.5f, y + h * 0.5f, FontLoader.regular(), 14f, new Color(180, 180, 200));
             return;
        }

            if (currentTab == Tab.Search) {
                drawSearch(x + 12, y, w - 24, h, mouseX, mouseY);
                return;
            }

        if (currentPlaylistDetail != null) {
            drawModernPlaylist(x + 12, y, w - 24, h, mouseX, mouseY);
        } else {
            // Tab.Recommend shows cards
            drawModernPlaylistList(x + 12, y, w - 24, h, mouseX, mouseY, recommendCards);
        }
    }

    private void drawModernPlaylistList(float x, float y, float w, float h, int mouseX, int mouseY, List<CloudMusicService.PlaylistCard> list) {
        float rowH = 48f;
        int visibleCount = (int) (h / rowH) + 1;
        int total = list.size();
        
        playlistScrollArea = new Rect(x, y, w, h);
        playlistScrollTarget = clamp(playlistScrollTarget, 0, Math.max(0, total - (h / rowH)));
        
        NanoVGHelper.save();
        NanoVGHelper.scissor(x, y, w, h);
        
        for (int i = 0; i < total; i++) {
            float rowY = y + i * rowH - playlistScrollCurrent * rowH;
            if (rowY + rowH < y || rowY > y + h) continue;
            
            CloudMusicService.PlaylistCard card = list.get(i);
            boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= rowY && mouseY <= rowY + rowH - 4;
            
            // Removed block background
            // NanoVGHelper.drawRoundRect(x, rowY, w, rowH - 6, 8f, hovered ? new Color(255, 255, 255, 12) : new Color(255, 255, 255, 5));
            // if (hovered) {
            //    NanoVGHelper.drawRoundRectOutline(x, rowY, w, rowH - 6, 8f, 1f, new Color(255, 255, 255, 50));
            // }
            
            int tex = requestUrlTexture(card.coverUrl());
            if (tex > 0) {
                NanoVGHelper.drawImage(tex, x + 6, rowY + 6, rowH - 18, rowH - 18, 6f, 1f);
            }

            String id = "card_" + card.id();
            Rect rect = new Rect(x, rowY, w, rowH - 6);
            staticRects.put(id, rect);
            float progress = hoverProgresses.getOrDefault(id, 0f);

            float textX = x + rowH - 6;
            float maxTextW = w - (textX - x) - 10;
            drawGlowText(card.name(), textX, rowY + rowH * 0.5f, FontLoader.regular(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE, maxTextW, progress);
            recommendCardRects.put(card.id(), rect);
        }
        
        NanoVGHelper.restore();
    }

    private void drawModernPlaylist(float x, float y, float w, float h, int mouseX, int mouseY) {
        // Back button
        float backH = 26f;
        boolean hoveredBack = hit("back_recommend", mouseX, mouseY);
        NanoVGHelper.drawCenteredString(TranslationManager.get("cloudmusic.back"), x + 30, y + backH * 0.5f, FontLoader.regular(), 12f, Color.WHITE);
        staticRects.put("back_recommend", new Rect(x, y, 60, backH));
        
        float listY = y + backH + 8f;
        float listH = h - backH - 8f;
        
        float rowH = 52f;
        int total = currentSongs.size();
        
        songScrollArea = new Rect(x, listY, w, listH);
        songScrollTarget = clamp(songScrollTarget, 0, Math.max(0, total - (listH / rowH)));
        
        NanoVGHelper.save();
        NanoVGHelper.scissor(x, listY, w, listH);
        
        for (int i = 0; i < total; i++) {
            float rowY = listY + i * rowH - songScrollCurrent * rowH;
            if (rowY + rowH < listY || rowY > listY + listH) continue;
            
            CloudMusicService.SongItem song = currentSongs.get(i);
            boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= rowY && mouseY <= rowY + rowH - 4;
            boolean playing = player.getCurrentSong() != null && player.getCurrentSong().id() == song.id();
            
            int tex = requestUrlTexture(song.coverUrl());
            float imgSize = rowH - 16f;
            if (tex > 0) {
                NanoVGHelper.drawImage(tex, x + 10, rowY + 8, imgSize, imgSize, 6f, 1f);
            } else {
                NanoVGHelper.drawRoundRect(x + 10, rowY + 8, imgSize, imgSize, 6f, new Color(255, 255, 255, 10));
            }
            
            float textX = x + 10 + imgSize + 12;
            float maxTextW = w - (textX - x) - 20;
            if (playing) {
                drawGradientGlowText(song.name(), textX, rowY + rowH * 0.35f, FontLoader.bold(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, maxTextW);
                drawScrollingString(song.artist(), textX, rowY + rowH * 0.7f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 180), maxTextW);
            } else {
                drawScrollingString(song.name(), textX, rowY + rowH * 0.35f, FontLoader.bold(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE, maxTextW);
                drawScrollingString(song.artist(), textX, rowY + rowH * 0.7f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 180), maxTextW);
            }
            
            songRowRects.put(i, new Rect(x, rowY, w, rowH - 4));
        }
        
        NanoVGHelper.restore();
    }

    private void drawSearch(float x, float y, float w, float h, int mouseX, int mouseY) {
        long now = System.currentTimeMillis();
        if (now - searchBlinkAt > 530) {
            searchCursorVisible = !searchCursorVisible;
            searchBlinkAt = now;
        }
        float inputH = 28f;
        float btnW = 86f;
        float inputX = x;
        float inputY = y + 12f;
        float inputW = w - btnW - 10f;
        float pillRadius = inputH * 0.5f;
        String displayText = searchEditing ? searchText : (searchText.isEmpty() ? TranslationManager.get("Enter the song name") : searchText);
        float fontSize = 13f;
        NanoVGHelper.drawString(displayText, inputX + 8f, inputY + inputH * 0.5f + 1f, FontLoader.regular(), fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(220, 220, 230));
        if (searchEditing && searchCursorVisible) {
            String before = searchText.substring(0, Math.min(searchCursorPos, searchText.length()));
            float cursorX = inputX + 8f + NanoVGHelper.getTextWidth(before, FontLoader.regular(), fontSize);
            if (cursorX < inputX + inputW - 6f) {
                NanoVGHelper.drawRect(cursorX, inputY + 6f, 1f, inputH - 12f, new Color(255, 255, 255));
            }
        }
        searchInputRect = new Rect(inputX, inputY, inputW, inputH);
        float btnX = inputX + inputW + 10f;
        float btnY = inputY;
        NanoVGHelper.drawCenteredString(TranslationManager.get("Search"), btnX + btnW * 0.5f, btnY + inputH * 0.5f, FontLoader.bold(), 13f, Color.WHITE);
        searchButtonRect = new Rect(btnX, btnY, btnW, inputH);
        float listY = inputY + inputH + 10f;
        float listH = h - (listY - y);
        drawSongList(x, listY, w, listH, mouseX, mouseY, currentSongs);
    }

    private void drawSongList(float x, float y, float w, float h, int mouseX, int mouseY, List<CloudMusicService.SongItem> list) {
        float rowH = 52f;
        int total = list.size();
        songScrollArea = new Rect(x, y, w, h);
        songScrollTarget = clamp(songScrollTarget, 0, Math.max(0, total - (h / rowH)));
        NanoVGHelper.save();
        NanoVGHelper.scissor(x, y, w, h);
        for (int i = 0; i < total; i++) {
            float rowY = y + i * rowH - songScrollCurrent * rowH;
            if (rowY + rowH < y || rowY > y + h) continue;
            CloudMusicService.SongItem song = list.get(i);
            int tex = requestUrlTexture(song.coverUrl());
            float imgSize = rowH - 16f;
            if (tex > 0) {
                NanoVGHelper.drawImage(tex, x + 10, rowY + 8, imgSize, imgSize, 6f, 1f);
            }
            float textX = x + 10 + imgSize + 12;
            float maxTextW = w - (textX - x) - 20;
            drawScrollingString(song.name(), textX, rowY + rowH * 0.35f, FontLoader.bold(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE, maxTextW);
            drawScrollingString(song.artist(), textX, rowY + rowH * 0.7f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 180), maxTextW);
            songRowRects.put(i, new Rect(x, rowY, w, rowH - 4));
        }
        NanoVGHelper.restore();
    }

    private void triggerSearch() {
        if (searchLoading) return;
        searchLoading = true;
        currentSongs.clear();
        service.searchSongs(searchText, 30, 0).thenCompose(service::fillMissingCovers).whenComplete((songs, ex) -> {
            searchLoading = false;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            currentSongs.clear();
            currentSongs.addAll(songs);
            songScrollTarget = 0f;
            songScrollCurrent = 0f;
            errorText = "";
        });
    }

    private void resetSearchCursor() {
        searchBlinkAt = System.currentTimeMillis();
        searchCursorVisible = true;
    }

    private void drawModernBottomPlayer(float x, float y, float w, float h, int mouseX, int mouseY) {
        CloudMusicService.SongItem song = player.getCurrentSong();
        if (song != null) {
            int tex = requestUrlTexture(song.coverUrl());
            if (tex > 0) {
                NanoVGHelper.drawImage(tex, x + 8, y + 8, h - 16, h - 16, 8f, 1f);
            }
            
            float textX = x + h + 8;
            NanoVGHelper.drawString(trim(song.name(), 25), textX, y + h * 0.4f, FontLoader.bold(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);
            NanoVGHelper.drawString(trim(song.artist(), 30), textX, y + h * 0.65f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 180));
        } else {
            NanoVGHelper.drawString(TranslationManager.get("cloudmusic.player.no_music"), x + 12, y + h * 0.5f, FontLoader.regular(), 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 180));
        }
        
        // Controls in center
        float centerX = x + w * 0.5f;
        
        // Play/Pause Button
        float playX = centerX - 12;
        boolean playHover = hit("play", mouseX, mouseY);
        String icon = player.isPlaying() ? PAUSE_ICON : PLAY_ICON;
        NanoVGHelper.drawCenteredString(icon, playX + 12, y + h * 0.5f, FontLoader.icont(), 24f, playHover ? Color.WHITE : new Color(200, 200, 220));
        staticRects.put("play", new Rect(playX, y + h * 0.5f - 12, 24, 24));

        // Next Button
        float nextX = playX + 40;
        boolean nextHover = hit("next", mouseX, mouseY);
        NanoVGHelper.drawCenteredString(NEXT_ICON, nextX + 12, y + h * 0.5f, FontLoader.icont(), 20f, nextHover ? Color.WHITE : new Color(200, 200, 220));
        staticRects.put("next", new Rect(nextX, y + h * 0.5f - 12, 24, 24));
        
        // Previous Button
        float prevX = playX - 40;
        boolean prevHover = hit("prev", mouseX, mouseY);
        NanoVGHelper.drawCenteredString(PREV_ICON, prevX + 12, y + h * 0.5f, FontLoader.icont(), 20f, prevHover ? Color.WHITE : new Color(200, 200, 220));
        staticRects.put("prev", new Rect(prevX, y + h * 0.5f - 12, 24, 24));

        // Volume Slider on right
        float sliderW = 120f;
        float sliderH = 16f;
        float sliderX = x + w - sliderW - 12f;
        float sliderY = y + (h - sliderH) * 0.5f;
        int vol = player.getVolume();
        float ratio = Math.max(0f, Math.min(1f, vol / 100f));
        NanoVGHelper.drawRoundRect(sliderX, sliderY, sliderW, sliderH, sliderH * 0.5f, new Color(255, 255, 255, 20));
        float fillW = Math.max(6f, sliderW * ratio);
        int tick = (int) ((System.currentTimeMillis() / 20.0) * CloudMusicGui.gradientSpeed.get().floatValue());
        Color c1 = dev.sakura.client.module.impl.client.ClickGui.color(tick);
        Color c2 = dev.sakura.client.module.impl.client.ClickGui.color2(tick);
        long vg = NanoVGRenderer.INSTANCE.getContext();
        try (org.lwjgl.system.MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
            org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
            org.lwjgl.nanovg.NanoVG.nvgLinearGradient(vg, sliderX, sliderY, sliderX + sliderW, sliderY, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);
            org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
            org.lwjgl.nanovg.NanoVG.nvgRoundedRect(vg, sliderX, sliderY, fillW, sliderH, sliderH * 0.5f);
            org.lwjgl.nanovg.NanoVG.nvgFillPaint(vg, paint);
            org.lwjgl.nanovg.NanoVG.nvgFill(vg);
        }
        float knobX = sliderX + fillW - 8f;
        knobX = Math.max(sliderX + 2f, Math.min(sliderX + sliderW - 10f, knobX));
        NanoVGHelper.drawRoundRect(knobX, sliderY + 2f, 10f, sliderH - 4f, (sliderH - 4f) * 0.5f, Color.WHITE);
        staticRects.put("volume_slider", new Rect(sliderX, sliderY, sliderW, sliderH));
    }

    private void drawErrorToast(float x, float y) {
        String show = errorText;
        if ((show == null || show.isBlank()) && player != null && player.getLastError() != null && !player.getLastError().isBlank()) {
            show = player.getLastError();
        }
        if (show == null || show.isBlank()) {
            return;
        }
        float w = 230f;
        float h = 32f;
        // Transparent error toast, only text and outline
        // NanoVGHelper.drawRoundRectOutline(x, y, w, h, 7f, 1f, new Color(255, 67, 94, 255));
        NanoVGHelper.drawString(trim(show, 20), x + 10f, y + 16f, FontLoader.bold(), 13f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(255, 67, 94));
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        lastMouseX = click.x();
        lastMouseY = click.y();
        
        if (click.button() != 0) {
            return super.mouseClicked(click, doubled);
        }
        float mx = (float) click.x();
        float my = (float) click.y();

        if (hit("login_btn", mx, my)) {
            if (!loginReady) {
                if (client != null) {
                    client.setScreen(new CloudMusicLoginScreen(this, service, this::refreshAfterLogin));
                }
            }
            return true;
        }

        if (hit("close", mx, my)) {
            close();
            return true;
        }
        
        if (hit("play", mx, my)) {
            if (player.getCurrentSong() == null && !currentSongs.isEmpty()) {
                player.playQueue(currentSongs, 0);
            } else {
                player.togglePause();
            }
            return true;
        }
        
        if (hit("next", mx, my)) {
            player.next();
            return true;
        }

        if (hit("prev", mx, my)) {
            player.previous();
            return true;
        }
        if (hit("volume_slider", mx, my)) {
            Rect r = staticRects.get("volume_slider");
            float ratio = Math.max(0f, Math.min(1f, (mx - r.x) / r.w));
            player.setVolume((int) Math.round(ratio * 100));
            volumeDragging = true;
            return true;
        }
        
        if (hit("tab_recommend", mx, my)) {
            currentTab = Tab.Recommend;
            currentPlaylistDetail = null;
            if (recommendCards.isEmpty()) {
                loadRecommend();
            }
            return true;
        }
        
        if (hit("tab_search", mx, my)) {
            currentTab = Tab.Search;
            currentPlaylistDetail = null;
            return true;
        }
        if (currentTab == Tab.Search) {
            if (searchInputRect != null && searchInputRect.contains(mx, my)) {
                searchEditing = true;
                searchCursorPos = searchText.length();
                searchBlinkAt = System.currentTimeMillis();
                searchCursorVisible = true;
                return true;
            }
            if (searchButtonRect != null && searchButtonRect.contains(mx, my)) {
                triggerSearch();
                return true;
            }
        }
        
        if (hit("refresh_qr", mx, my)) {
            refreshQr();
            return true;
        }
        
        if (hit("back_recommend", mx, my)) {
            currentPlaylistDetail = null;
            return true;
        }

        // Sidebar Playlist clicks
        for (Map.Entry<String, Rect> entry : playlistBarRects.entrySet()) {
            if (entry.getValue().contains(mx, my)) {
                String idStr = entry.getKey().replace("playlist_", "");
                try {
                    long id = Long.parseLong(idStr);
                    openPlaylist(id);
                } catch (NumberFormatException ignored) {}
                return true;
            }
        }

        // Playlist & Song clicks
        for (Map.Entry<Long, Rect> entry : recommendCardRects.entrySet()) {
            if (entry.getValue().contains(mx, my)) {
                openPlaylist(entry.getKey());
                return true;
            }
        }
        
        for (Map.Entry<Integer, Rect> entry : songRowRects.entrySet()) {
            if (entry.getValue().contains(mx, my)) {
                player.playQueue(currentSongs, entry.getKey());
                return true;
            }
        }

        // Dragging logic: if clicked on window but not on buttons
        if (mx >= windowX && mx <= windowX + windowW && my >= windowY && my <= windowY + windowH) {
            dragging = true;
            dragX = mx - windowX;
            dragY = my - windowY;
            return true;
        }

        return super.mouseClicked(click, doubled);
    }

    void refreshAfterLogin() {
        bootstrap();
    }

    @Override
    public boolean mouseReleased(Click click) {
        dragging = false;
        volumeDragging = false;
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseDragged(Click click, double deltaX, double deltaY) {
        lastMouseX = click.x();
        lastMouseY = click.y();
        if (dragging && click.button() == 0) {
            windowX = (float) click.x() - dragX;
            windowY = (float) click.y() - dragY;
            return true;
        }
        if (volumeDragging && click.button() == 0) {
            Rect r = staticRects.get("volume_slider");
            if (r != null) {
                float mx = (float) click.x();
                float ratio = Math.max(0f, Math.min(1f, (mx - r.x) / r.w));
                player.setVolume((int) Math.round(ratio * 100));
                return true;
            }
        }
        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.getKeycode() == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        if (currentTab == Tab.Search && searchEditing) {
            switch (input.getKeycode()) {
                case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                    triggerSearch();
                    return true;
                }
                case GLFW.GLFW_KEY_BACKSPACE -> {
                    if (searchCursorPos > 0 && !searchText.isEmpty()) {
                        searchText = searchText.substring(0, searchCursorPos - 1) + searchText.substring(searchCursorPos);
                        searchCursorPos--;
                        resetSearchCursor();
                    }
                    return true;
                }
                case GLFW.GLFW_KEY_DELETE -> {
                    if (searchCursorPos < searchText.length()) {
                        searchText = searchText.substring(0, searchCursorPos) + searchText.substring(searchCursorPos + 1);
                        resetSearchCursor();
                    }
                    return true;
                }
                case GLFW.GLFW_KEY_LEFT -> {
                    if (searchCursorPos > 0) {
                        searchCursorPos--;
                        resetSearchCursor();
                    }
                    return true;
                }
                case GLFW.GLFW_KEY_RIGHT -> {
                    if (searchCursorPos < searchText.length()) {
                        searchCursorPos++;
                        resetSearchCursor();
                    }
                    return true;
                }
                case GLFW.GLFW_KEY_HOME -> {
                    searchCursorPos = 0;
                    resetSearchCursor();
                    return true;
                }
                case GLFW.GLFW_KEY_END -> {
                    searchCursorPos = searchText.length();
                    resetSearchCursor();
                    return true;
                }
            }
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (currentTab == Tab.Search && searchEditing) {
            char chr = (char) input.codepoint();
            if (chr >= 32 && chr != 127) {
                searchText = searchText.substring(0, searchCursorPos) + chr + searchText.substring(searchCursorPos);
                searchCursorPos++;
                resetSearchCursor();
                return true;
            }
        }
        return super.charTyped(input);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == 0) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }
        float mx = (float) mouseX;
        float my = (float) mouseY;
        int delta = scrollY > 0 ? -1 : 1;
        if (staticRects.containsKey("volume_slider") && staticRects.get("volume_slider").contains(mx, my)) {
            int step = 5;
            int newVol = Math.max(0, Math.min(100, player.getVolume() + (delta > 0 ? -step : step)));
            player.setVolume(newVol);
            return true;
        }
        if (songScrollArea != null && songScrollArea.contains(mx, my)) {
            int visibleCount = Math.max(1, (int) (songScrollArea.h / 42f));
            int maxStart = Math.max(0, currentSongs.size() - visibleCount);
            songScrollTarget = clamp(songScrollTarget + delta, 0f, maxStart);
            return true;
        }
        if (playlistScrollArea != null && playlistScrollArea.contains(mx, my)) {
            int visibleCount = Math.max(1, (int) (playlistScrollArea.h / 44f));
            int maxStart = Math.max(0, recommendCards.size() - visibleCount);
            playlistScrollTarget = clamp(playlistScrollTarget + delta, 0f, maxStart);
            return true;
        }
        if (sidebarScrollArea != null && sidebarScrollArea.contains(mx, my)) {
            sidebarScrollTarget = clamp(sidebarScrollTarget + delta, 0f, 100f); // sidebarScrollTarget is updated in drawSidebar
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void removed() {
        imageTextures.values().forEach(NanoVGHelper::deleteTexture);
        imageTextures.clear();
        pendingImageBytes.clear();
        loadingImages.clear();
        super.removed();
    }

    private void bootstrap() {
        statusText = TranslationManager.get("cloudmusic.status.checking_login");
        errorText = "";
        service.refreshProfile().thenAccept(profile -> {
            loginReady = true;
            statusText = String.format(TranslationManager.get("cloudmusic.status.welcome"), profile.nickname());
            loadMainData();
        }).exceptionally(ex -> {
            loginReady = false;
            statusText = TranslationManager.get("cloudmusic.status.not_logged_in");
            // Do not force login UI immediately, let user choose
            return null;
        });
    }

    private void loadMainData() {
        loadingMainData = true;
        CompletableFuture<List<CloudMusicService.PlaylistCard>> recFuture = service.loadRecommendPlaylists();
        CompletableFuture<List<CloudMusicService.PlaylistCard>> userFuture = service.loadUserPlaylists();
        recFuture.thenCombine(userFuture, (rec, user) -> {
            recommendCards.clear();
            recommendCards.addAll(rec);
            userPlaylists.clear();
            userPlaylists.addAll(user);
            playlistScrollTarget = 0f;
            playlistScrollCurrent = 0f;
            songScrollTarget = 0f;
            songScrollCurrent = 0f;
            return true;
        }).whenComplete((v, ex) -> {
            loadingMainData = false;
            if (ex != null) {
                errorText = ex.getMessage();
            } else {
                errorText = "";
            }
        });
    }

    private void loadRecommend() {
        loadingMainData = true;
        service.loadRecommendPlaylists().whenComplete((list, ex) -> {
            loadingMainData = false;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            recommendCards.clear();
            recommendCards.addAll(list);
            songScrollTarget = 0f;
            songScrollCurrent = 0f;
            errorText = "";
        });
    }

    private void loadLikedSongs() {
        loadingMainData = true;
        currentPlaylistDetail = null;
        service.loadMyLikedSongs().whenComplete((songs, ex) -> {
            loadingMainData = false;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            currentSongs.clear();
            currentSongs.addAll(songs);
            songScrollTarget = 0f;
            songScrollCurrent = 0f;
            errorText = "";
        });
    }

    private void openPlaylist(long playlistId) {
        loadingMainData = true;
        service.loadPlaylistDetail(playlistId).whenComplete((detail, ex) -> {
            loadingMainData = false;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            currentPlaylistDetail = detail;
            currentSongs.clear();
            currentSongs.addAll(detail.songs());
            currentTab = Tab.Recommend;
            songScrollTarget = 0f;
            songScrollCurrent = 0f;
            errorText = "";
        });
    }

    private void updateSmoothScroll() {
        playlistScrollCurrent += (playlistScrollTarget - playlistScrollCurrent) * scrollSmoothFactor;
        songScrollCurrent += (songScrollTarget - songScrollCurrent) * scrollSmoothFactor;
        sidebarScrollCurrent += (sidebarScrollTarget - sidebarScrollCurrent) * scrollSmoothFactor;
        
        // Update hover animations
        for (String id : staticRects.keySet()) {
            if (id.startsWith("tab_") || id.startsWith("playlist_") || id.startsWith("card_") || id.equals("login_btn")) {
                Rect rect = staticRects.get(id);
                boolean hovered = rect.contains((float) lastMouseX, (float) lastMouseY);
                float target = hovered ? 1f : 0f;
                float current = hoverProgresses.getOrDefault(id, 0f);
                if (Math.abs(target - current) > 0.001f) {
                    hoverProgresses.put(id, current + (target - current) * 0.15f);
                } else {
                    hoverProgresses.put(id, target);
                }
            }
        }
        
        if (Math.abs(playlistScrollTarget - playlistScrollCurrent) < 0.001f) {
            playlistScrollCurrent = playlistScrollTarget;
        }
        if (Math.abs(songScrollTarget - songScrollCurrent) < 0.001f) {
            songScrollCurrent = songScrollTarget;
        }
        if (Math.abs(sidebarScrollTarget - sidebarScrollCurrent) < 0.001f) {
            sidebarScrollCurrent = sidebarScrollTarget;
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    private void refreshQr() {
        statusText = TranslationManager.get("cloudmusic.status.generating_qr");
        service.refreshQrCode().whenComplete((state, ex) -> {
            if (ex != null) {
                statusText = TranslationManager.get("cloudmusic.status.qr_fetch_failed");
                errorText = ex.getMessage();
                return;
            }
            qrState = state;
            qrTextureKey = "";
            statusText = state.message();
            nextQrCheckAt = System.currentTimeMillis() + 1500L;
            errorText = "";
        });
    }

    private void pollQrStatus() {
        if (loginReady || qrState == null || !showLoginOverlay) {
            return;
        }
        if (checkingQr) {
            return;
        }
        if (System.currentTimeMillis() < nextQrCheckAt) {
            return;
        }
        if (qrState.code() == 800) {
            return;
        }
        checkingQr = true;
        service.checkQrLogin().whenComplete((state, ex) -> {
            checkingQr = false;
            nextQrCheckAt = System.currentTimeMillis() + 1200L;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            qrState = state;
            statusText = state.message();
            if (state.code() == 803) {
                loginReady = true;
                statusText = TranslationManager.get("cloudmusic.status.login_success");
                showLoginOverlay = false;
                loadMainData();
            }
            if (state.code() == 800) {
                statusText = TranslationManager.get("cloudmusic.status.qr_expired");
            }
        });
    }

    private boolean hit(String id, float mx, float my) {
        Rect rect = staticRects.get(id);
        return rect != null && rect.contains(mx, my);
    }

    private int requestQrTexture() {
        if (qrState == null || qrState.qrBase64() == null || qrState.qrBase64().isBlank()) {
            return 0;
        }
        String key = "qr:" + qrState.key();
        if (!key.equals(qrTextureKey)) {
            try {
                String b64 = qrState.qrBase64();
                if (b64.contains(",")) {
                    b64 = b64.substring(b64.indexOf(",") + 1);
                }
                byte[] bytes = Base64.getDecoder().decode(b64);
                pendingImageBytes.put(key, bytes);
                qrTextureKey = key;
            } catch (Exception e) {
                return -1; // Special value for parse error
            }
        }
        return imageTextures.getOrDefault(key, 0);
    }

    private int requestUrlTexture(String url) {
        if (url == null || url.isBlank()) {
            return 0;
        }
        if (imageTextures.containsKey(url)) {
            return imageTextures.get(url);
        }
        if (!loadingImages.contains(url)) {
            loadingImages.add(url);
            CompletableFuture.runAsync(() -> {
                try {
                    String u = url.startsWith("//") ? ("https:" + url) : url;
                    java.net.URL uu = new java.net.URL(u);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) uu.openConnection();
                    conn.setConnectTimeout(6000);
                    conn.setReadTimeout(8000);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
                    conn.setRequestProperty("Accept", "*/*");
                    conn.setRequestProperty("Referer", "https://music.163.com");
                    conn.connect();
                    try (InputStream stream = conn.getInputStream()) {
                        byte[] bytes = stream.readAllBytes();
                        if (bytes != null && bytes.length > 0) {
                            pendingImageBytes.put(url, bytes);
                        }
                    } finally {
                        conn.disconnect();
                    }
                } catch (Exception ignored) {
                } finally {
                    loadingImages.remove(url);
                }
            });
        }
        return 0;
    }

    private void flushPendingImages() {
        if (pendingImageBytes.isEmpty()) {
            return;
        }
        List<String> keys = new ArrayList<>(pendingImageBytes.keySet());
        for (String key : keys) {
            byte[] bytes = pendingImageBytes.remove(key);
            if (bytes == null || bytes.length == 0) {
                continue;
            }
            ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            int image = NanoVGHelper.loadTexture(buffer);
            if (image > 0) {
                Integer old = imageTextures.put(key, image);
                if (old != null && old != image) {
                    NanoVGHelper.deleteTexture(old);
                }
            }
        }
    }

    private String trim(String text, int max) {
        if (text == null) {
            return "";
        }
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, max) + "...";
    }

    private String formatTime(int durationMs) {
        if (durationMs <= 0) {
            return "--:--";
        }
        int sec = durationMs / 1000;
        int min = sec / 60;
        int rem = sec % 60;
        return String.format("%02d:%02d", min, rem);
    }

    private record Rect(float x, float y, float w, float h) {
        boolean contains(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }
}
