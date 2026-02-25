package dev.mzc.client.module.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.entity.AttackEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.Module;
import dev.mzc.client.mixin.accessor.IPlayerInteractEntityC2SPacket;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.module.impl.player.AutoTotem;
import dev.mzc.client.module.impl.player.InvManager;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Easing;
import dev.mzc.client.utils.render.Shader2DUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DynamicIslandHud extends HudModule {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static volatile Text capturedTabHeader;
    private static volatile Text capturedTabFooter;
    private static volatile List<PlayerListEntry> capturedTabEntries = List.of();

    private static final class Size {
        static final float BASE_W = 65, BASE_H = 19;
        static final float EXPANDED_W = 90, EXPANDED_H = 25;
        static final float ELEMENT_SPACING = 20;
        static final float ELEMENT_WIDTH = 50;
        static final float LOGO_FONT_SIZE = 12;
        static final float INFO_FONT_SIZE = 10;
        static final float GLOW_RADIUS = 3.0f;
        static final Color INVENTORY_BG_COLOR = new Color(18, 18, 18, 70);

        static final float TAB_PLAYER_HEIGHT = 14;
        static final float TAB_PADDING = 8;
        static final float TAB_HEADER_Y = 12;
        static final float TAB_LIST_Y = 30;
        static final int TAB_COLUMNS = 1;
    }

    private static final class Timing {
        static final long EXPAND = 210L;
        static final long DISPLAY = 1500L;
        static final long COLLAPSE_1 = 210L;
        static final long COLLAPSE_2 = 280L;
        static final long TOTAL = EXPAND + DISPLAY + COLLAPSE_1 + COLLAPSE_2;
        static final long TAB_TRANSITION = 245L;
    }

    private enum Phase {
        IDLE,
        EXPANDING,
        DISPLAY,
        COLLAPSE_1,
        COLLAPSE_2,
        TAB_EXPAND,
        TAB_DISPLAY,
        TAB_COLLAPSE
    }

    private enum Layout {
        Classic("经典"),
        Unified("整体");

        private final String cnName;

        Layout(String cnName) {
            this.cnName = cnName;
        }
    }

    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final BoolValue killNotification = new BoolValue("KillNotification", "击杀提示", true);
    private final EnumValue<Layout> layout = new EnumValue<>("Layout", "布局", Layout.Classic);

    private static ToggleInfo currentToggle;
    private static ToggleInfo pendingToggle;
    private static KillInfo currentKill;
    private static KillInfo pendingKill;

    private long toggleStartTime = -1L;
    private long tabStartTime = -1L;
    private long noTotemTriggerTime = 0L;
    private long toggleTriggerTime = 0L;
    private long sortingTriggerTime = 0L;
    private long killTriggerTime = 0L;
    private float targetExpandedWidth = Size.EXPANDED_W;

    private Entity lastAttackedEntity;
    private long lastAttackTime;
    private int lastProcessedKillId = -1;

    private InvManager invManager;
    private boolean isSorting;
    private boolean closingSorting;
    private boolean displayingSorting;
    private boolean isNoTotem;
    private int maxPendingActions = 1;

    private Phase phase = Phase.IDLE;
    private float progress;
    private float blurOpacity = 1f;
    private float animX, animY, animW, animH;
    private float tabMergeProgress;
    private long lastNotificationTime = 0;

    private int unifiedPromptLines = 0;
    private long unifiedHeightAnimStart = 0L;
    private float unifiedHeightFrom = 0f;
    private float unifiedHeightTo = 0f;
    private long unifiedWidthAnimStart = 0L;
    private float unifiedWidthFrom = 0f;
    private float unifiedWidthTo = 0f;
    private long idleReturnStart = 0L;
    private float idleFromW = 0f;
    private float idleFromH = 0f;
    private long sortingAnimStart = 0L;
    private float sortingAnimFrom = 0f;
    private float sortingAnimTo = 0f;

    private List<PlayerListEntry> playerList;
    private float tabTargetW, tabTargetH;
    private int tabColumns = 1;
    private int tabRows = 0;
    private int tabColumnWidth = 0;

    public static void hookVanillaTab(Text header, Text footer, List<PlayerListEntry> entries) {
        capturedTabHeader = header;
        capturedTabFooter = footer;
        capturedTabEntries = entries == null ? List.of() : List.copyOf(entries);
    }

    public DynamicIslandHud() {
        super("DynamicIsland", "灵动岛", 0, 0);
        this.width = Size.BASE_W;
        this.height = Size.BASE_H;
    }

    public static void onModuleToggle(Module module, boolean enabled) {
        pendingToggle = new ToggleInfo(module.getEnglishName(), enabled);
    }

    @Override
    public void onRender(DrawContext context) {
        update();

        renderBlur(context);
        renderSideBlurs(context, getSideBlurOpacity());

        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());

        if (isTabPhase()) {
            renderCapturedTab(context);
        }
    }

    private void update() {
        if (invManager == null) invManager = (InvManager) Sakura.MODULES.getModule(InvManager.class);

        boolean wasNoTotem = isNoTotem;
        isNoTotem = shouldShowNoTotem();

        if (isNoTotem && !wasNoTotem) {
            displayingSorting = false;
            noTotemTriggerTime = System.currentTimeMillis();
            if (!isTabPhase()) {
                phase = Phase.EXPANDING;
                toggleStartTime = System.currentTimeMillis();
                mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), 1.0f, 2.0f));
            }
        } else if (wasNoTotem && !isNoTotem) {
            noTotemTriggerTime = 0L;
            if (!isTabPhase()) {
                toggleStartTime = System.currentTimeMillis() - (Timing.EXPAND + Timing.DISPLAY);
                pendingToggle = null;
                pendingKill = null;
            }
        }

        if (isUnifiedLayout()) {
            boolean wasSorting = isSorting;
            isSorting = invManager != null && invManager.isEnabled() && invManager.pendingActions > 0 && invManager.shouldSort();

            if (isSorting && !wasSorting) {
                maxPendingActions = Math.max(1, invManager.pendingActions);
                closingSorting = false;
                sortingTriggerTime = System.currentTimeMillis();
            } else if (wasSorting && !isSorting) {
                closingSorting = true;
                displayingSorting = false;
                sortingTriggerTime = 0L;
                if (!isTabPhase()) {
                    toggleStartTime = System.currentTimeMillis() - (Timing.EXPAND + Timing.DISPLAY);
                }
            } else if (isSorting) {
                if (invManager.pendingActions > maxPendingActions) {
                    maxPendingActions = invManager.pendingActions;
                }
            }
        } else {
            if (!isNoTotem) {
                boolean wasSorting = isSorting;
                isSorting = invManager != null && invManager.isEnabled() && invManager.pendingActions > 0 && invManager.shouldSort();

                if (isSorting && !wasSorting) {
                    maxPendingActions = Math.max(1, invManager.pendingActions);
                    closingSorting = false;
                    sortingTriggerTime = System.currentTimeMillis();
                } else if (wasSorting && !isSorting) {
                    closingSorting = true;
                    displayingSorting = false;
                    sortingTriggerTime = 0L;
                    if (!isTabPhase()) {
                        toggleStartTime = System.currentTimeMillis() - (Timing.EXPAND + Timing.DISPLAY);
                    }
                } else if (isSorting) {
                    if (invManager.pendingActions > maxPendingActions) {
                        maxPendingActions = invManager.pendingActions;
                    }
                }
            } else {
                isSorting = false;
            }
        }

        if (killNotification.get() && lastAttackedEntity instanceof LivingEntity living) {
            if (living.getHealth() <= 0.0f && living.getId() != lastProcessedKillId && System.currentTimeMillis() - lastAttackTime < 5000) {
                pendingKill = new KillInfo(living.getName().getString());
                lastProcessedKillId = living.getId();
            }
        }

        handleTabInput();
        processNotifications();
        calculateState();
    }

    private void handleTabInput() {
        boolean tabPressed = mc.options.playerListKey.isPressed();

        if (tabPressed && !isTabPhase()) {
            tabStartTime = System.currentTimeMillis();
            phase = Phase.TAB_EXPAND;
            updatePlayerList();
        } else if (tabPressed && isTabPhase()) {
            updatePlayerList();
            if (phase == Phase.TAB_COLLAPSE) {
                // If pressing tab while collapsing, re-expand
                phase = Phase.TAB_EXPAND;
                tabStartTime = System.currentTimeMillis() - (long)((1f - tabMergeProgress) * Timing.TAB_TRANSITION * 0.45f);
            }
        } else if (phase == Phase.TAB_DISPLAY || phase == Phase.TAB_EXPAND) {
            tabStartTime = System.currentTimeMillis();
            phase = Phase.TAB_COLLAPSE;
        }
    }

    private void updatePlayerList() {
        if (mc.getNetworkHandler() != null) {
            List<PlayerListEntry> source = capturedTabEntries.isEmpty() ? List.copyOf(mc.getNetworkHandler().getPlayerList()) : capturedTabEntries;
            playerList = source.stream()
                    .sorted(Comparator.comparingInt((PlayerListEntry e) -> e.getGameMode() == GameMode.SPECTATOR ? 1 : 0).thenComparing(e -> e.getProfile().getName()))
                    .limit(80) // 可有可无吧。。。
                    .collect(Collectors.toList());

            int count = playerList.size();

            int maxNameWidth = 0;
            for (PlayerListEntry entry : playerList) {
                int w = mc.textRenderer.getWidth(mc.inGameHud.getPlayerListHud().getPlayerName(entry));
                if (w > maxNameWidth) maxNameWidth = w;
            }
            // Limit max name width to avoid super wide columns
            maxNameWidth = Math.min(maxNameWidth, 150);

            int pingWidth = mc.textRenderer.getWidth("999ms");
            int headSize = 10;
            int innerPadding = 4;
            int singleColWidth = headSize + innerPadding + maxNameWidth + innerPadding + pingWidth + innerPadding;
            singleColWidth = Math.max(singleColWidth, 80);

            int maxRows = 20;
            if (count == 0) {
                tabColumns = 1;
                tabRows = 0;
            } else {
                tabColumns = (int) Math.ceil((double) count / maxRows);
                if (tabColumns > 4) tabColumns = 4;
                // Recalculate rows based on actual columns to balance the list
                tabRows = (int) Math.ceil((double) count / tabColumns);
            }
            tabColumnWidth = singleColWidth;

            float spacing = 5;
            tabTargetW = Size.TAB_PADDING * 2 + tabColumns * tabColumnWidth + (tabColumns - 1) * spacing;
            tabTargetW = Math.max(tabTargetW, Size.BASE_W);

            int innerW = (int) Math.max(0, tabTargetW - Size.TAB_PADDING * 2f);
            int fontH = mc.textRenderer.fontHeight;

            int headerLines;
            if (capturedTabHeader != null && !capturedTabHeader.getString().isEmpty()) {
                headerLines = mc.textRenderer.wrapLines(capturedTabHeader, innerW).size();
            } else {
                headerLines = 1;
            }

            int ftLine = 0;
            if (capturedTabFooter != null && !capturedTabFooter.getString().isEmpty()) {
                ftLine = mc.textRenderer.wrapLines(capturedTabFooter, innerW).size();
            }

            float headerH = headerLines * fontH;
            float footerH = ftLine * fontH;
            float listY = Math.max(Size.TAB_LIST_Y, Size.TAB_HEADER_Y + headerH + 8f);

            tabTargetH = listY + tabRows * Size.TAB_PLAYER_HEIGHT + Size.TAB_PADDING + (ftLine > 0 ? (footerH + 8f) : 0f);
            tabTargetH = Math.max(tabTargetH, Size.BASE_H * 2f);
        }
    }

    private void processNotifications() {
        if (isTabPhase()) return;

        if ((currentToggle != null || currentKill != null) && ela() >= Timing.TOTAL) {
            currentToggle = null;
            currentKill = null;
            toggleTriggerTime = 0L;
            killTriggerTime = 0L;
            if (isSorting && !isTabPhase()) {
                displayingSorting = true;
                closingSorting = false;
                toggleStartTime = System.currentTimeMillis();
                lastNotificationTime = 0L;
            } else {
                toggleStartTime = -1L;
                lastNotificationTime = System.currentTimeMillis();
            }
        }

        if (pendingKill != null) {
            currentKill = pendingKill;
            pendingKill = null;
            currentToggle = null;
            displayingSorting = false;
            closingSorting = false;
            toggleStartTime = System.currentTimeMillis();
            killTriggerTime = System.currentTimeMillis();
            targetExpandedWidth = calculateKillWidth();
        } else if (pendingToggle != null) {
            if (currentKill == null) {
                currentToggle = pendingToggle;
                pendingToggle = null;
                displayingSorting = false;
                closingSorting = false;
                toggleStartTime = System.currentTimeMillis();
                toggleTriggerTime = System.currentTimeMillis();
                targetExpandedWidth = calculateExpandedWidth();
            }
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent event) {
        if (!killNotification.get()) return;
        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            int id = ((IPlayerInteractEntityC2SPacket) packet).getEntityId();
            Entity entity = mc.world.getEntityById(id);
            if (entity != null) {
                lastAttackedEntity = entity;
                lastAttackTime = System.currentTimeMillis();
            }
        }
    }

    @EventHandler
    private void onAttack(AttackEvent event) {
        if (!killNotification.get()) return;
        lastAttackedEntity = event.getTargetEntity();
        lastAttackTime = System.currentTimeMillis();
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (!killNotification.get()) return;
        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == 3) {
                Entity entity = packet.getEntity(mc.world);
                if (entity == null) return;

                boolean isTarget = (entity == lastAttackedEntity || (lastAttackedEntity != null && entity.getId() == lastAttackedEntity.getId()));

                if (!isTarget && lastAttackedEntity instanceof EndCrystalEntity && entity instanceof PlayerEntity) {
                    if (entity.distanceTo(lastAttackedEntity) <= 12.0) {
                        isTarget = true;
                    }
                }

                if (isTarget && System.currentTimeMillis() - lastAttackTime < 5000) {
                    if (entity instanceof PlayerEntity || entity instanceof Monster) {
                        pendingKill = new KillInfo(entity.getName().getString());
                        lastProcessedKillId = entity.getId();
                    }
                }
            }
        }
    }

    private void calculateState() {
        long dt = ela();
        long tabDt = elaTab();

        if (isUnifiedLayout()) {
            calculateStateUnified(dt, tabDt);
        } else {
            calculateStateClassic(dt, tabDt);
        }
    }

    private void calculateStateClassic(long dt, long tabDt) {
        if (isNoTotem && !isTabPhase()) {
            targetExpandedWidth = calculateNoTotemWidth();
            if (phase == Phase.EXPANDING) {
                if (dt < Timing.EXPAND) {
                    float p = easeOut(dt / (float) Timing.EXPAND);
                    setPhase(Phase.EXPANDING, p,
                            lerp(Size.BASE_W, targetExpandedWidth, p),
                            lerp(Size.BASE_H, Size.EXPANDED_H, p),
                            lerp(1f, 1f, p));
                } else {
                    setPhase(Phase.DISPLAY, 1f, targetExpandedWidth, Size.EXPANDED_H, 1f);
                }
            } else if (phase == Phase.DISPLAY) {
                setPhase(Phase.DISPLAY, 1f, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else {
                phase = Phase.EXPANDING;
                toggleStartTime = System.currentTimeMillis();
            }
            animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
            animY = y;
            this.width = animW;
            this.height = animH;
            this.x = animX;
            return;
        }

        if (isSorting && !isTabPhase() && currentKill == null && currentToggle == null && (displayingSorting || phase == Phase.IDLE) && System.currentTimeMillis() - lastNotificationTime > 500) {
            displayingSorting = true;
            targetExpandedWidth = calculateSortingWidth();
            if (phase == Phase.EXPANDING) {
                if (dt < Timing.EXPAND) {
                    float p = easeOut(dt / (float) Timing.EXPAND);
                    setPhase(Phase.EXPANDING, p,
                            lerp(Size.BASE_W, targetExpandedWidth, p),
                            lerp(Size.BASE_H, Size.EXPANDED_H, p),
                            lerp(1f, 1f, p));
                } else {
                    setPhase(Phase.DISPLAY, 1f, targetExpandedWidth, Size.EXPANDED_H, 1f);
                }
            } else if (phase == Phase.DISPLAY) {
                setPhase(Phase.DISPLAY, 1f, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else {
                phase = Phase.EXPANDING;
                toggleStartTime = System.currentTimeMillis();
            }
            animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
            animY = y;
            this.width = animW;
            this.height = animH;
            this.x = animX;
            return;
        }

        if (phase == Phase.TAB_EXPAND) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = clamp(tabDt / (Timing.TAB_TRANSITION * 0.45f), 0f, 1f);
                float expandT = clamp((tabDt - Timing.TAB_TRANSITION * 0.25f) / (Timing.TAB_TRANSITION * 0.75f), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_EXPAND, expandP,
                        lerp(Size.BASE_W, tabTargetW, mergeP),
                        lerp(Size.BASE_H, tabTargetH, expandP),
                        1f);
            } else {
                tabMergeProgress = 1f;
                setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
            }
        } else if (phase == Phase.TAB_COLLAPSE) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = clamp(1f - (tabDt / (Timing.TAB_TRANSITION * 0.45f)), 0f, 1f);
                float expandT = clamp(1f - ((tabDt - Timing.TAB_TRANSITION * 0.10f) / (Timing.TAB_TRANSITION * 0.90f)), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_COLLAPSE, expandP,
                        lerp(Size.BASE_W, tabTargetW, mergeP),
                        lerp(Size.BASE_H, tabTargetH, expandP),
                        1f);
            } else {
                tabMergeProgress = 0f;
                float idleW = getUnifiedIdleWidth();
                float idleH = getUnifiedIdleHeight();
                setPhase(Phase.IDLE, 0f, idleW, idleH, 1f);
                tabStartTime = -1L;
            }
        } else if (phase == Phase.TAB_DISPLAY) {
            tabMergeProgress = 1f;
            setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
        } else {
            if (currentToggle == null && currentKill == null && toggleStartTime == -1L) {
                closingSorting = false;
                setPhase(Phase.IDLE, 0f, Size.BASE_W, Size.BASE_H, 1f);
            } else if (dt < Timing.EXPAND) {
                float p = easeOut(dt / (float) Timing.EXPAND);
                setPhase(Phase.EXPANDING, p,
                        lerp(Size.BASE_W, targetExpandedWidth, p),
                        lerp(Size.BASE_H, Size.EXPANDED_H, p),
                        lerp(1f, 1f, p));
            } else if (dt < Timing.EXPAND + Timing.DISPLAY) {
                float p = (dt - Timing.EXPAND) / (float) Timing.DISPLAY;
                setPhase(Phase.DISPLAY, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else if (dt < Timing.EXPAND + Timing.DISPLAY + Timing.COLLAPSE_1) {
                float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY) / (float) Timing.COLLAPSE_1);
                setPhase(Phase.COLLAPSE_1, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else {
                float timeInCollapse2 = dt - Timing.EXPAND - Timing.DISPLAY - Timing.COLLAPSE_1;
                if (timeInCollapse2 >= Timing.COLLAPSE_2) {
                    toggleStartTime = -1L;
                    closingSorting = false;
                    setPhase(Phase.IDLE, 0f, Size.BASE_W, Size.BASE_H, 1f);
                } else {
                    float p = easeOut(timeInCollapse2 / (float) Timing.COLLAPSE_2);
                    setPhase(Phase.COLLAPSE_2, p,
                            lerp(targetExpandedWidth, Size.BASE_W, p),
                            lerp(Size.EXPANDED_H, Size.BASE_H, p),
                            1f);
                }
            }
        }

        animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
        animY = y;
        this.width = animW;
        this.height = animH;
        this.x = animX;
    }

    private void calculateStateUnified(long dt, long tabDt) {
        if (!hasUnifiedPrompt() && !isTabPhase()) {
            closingSorting = false;
            float idleW = getUnifiedIdleWidth();
            float idleH = getUnifiedIdleHeight();
            if (idleReturnStart == 0L) {
                idleReturnStart = System.currentTimeMillis();
                idleFromW = animW <= 0 ? idleW : animW;
                idleFromH = animH <= 0 ? idleH : animH;
            }
            float duration = Timing.COLLAPSE_1 + Timing.COLLAPSE_2;
            long elapsed = System.currentTimeMillis() - idleReturnStart;
            float t = clamp(elapsed / duration, 0f, 1f);
            float p = easeOut(t);
            float w = lerp(idleFromW, idleW, p);
            float h = lerp(idleFromH, idleH, p);
            setPhase(Phase.IDLE, p, w, h, 1f);
            animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
            animY = y;
            this.width = animW;
            this.height = animH;
            this.x = animX;
            if (t >= 1f) {
                idleReturnStart = 0L;
            }
            return;
        } else {
            idleReturnStart = 0L;
        }
        if (!isTabPhase()) {
            float headerWidth = calculateUnifiedHeaderWidth();
            float noTotemWidth = isNoTotem ? calculateNoTotemWidth() : 0f;
            float sortingWidth = isSorting ? calculateSortingWidth() : 0f;
            float toggleWidth = currentToggle != null ? calculateExpandedWidth() : 0f;
            float killWidth = currentKill != null ? calculateKillWidth() : 0f;
            targetExpandedWidth = Math.max(headerWidth, Math.max(Math.max(noTotemWidth, sortingWidth), Math.max(toggleWidth, killWidth)));
        }

        if (isNoTotem && !isTabPhase()) {
            float targetHeight = getUnifiedTargetHeight(isNoTotem, isSorting, currentKill != null || currentToggle != null);
            targetHeight = getUnifiedAnimatedHeight(targetHeight);
            float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
            if (phase == Phase.EXPANDING) {
                if (dt < Timing.EXPAND) {
                    float p = easeOut(dt / (float) Timing.EXPAND);
                    setPhase(Phase.EXPANDING, p,
                            lerp(Size.BASE_W, animatedWidth, p),
                            lerp(Size.BASE_H, targetHeight, p),
                            lerp(1f, 1f, p));
                } else {
                    setPhase(Phase.DISPLAY, 1f, animatedWidth, targetHeight, 1f);
                }
            } else if (phase == Phase.DISPLAY) {
                setPhase(Phase.DISPLAY, 1f, animatedWidth, targetHeight, 1f);
            } else {
                phase = Phase.EXPANDING;
                toggleStartTime = System.currentTimeMillis();
            }
            animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
            animY = y;
            this.width = animW;
            this.height = animH;
            this.x = animX;
            return;
        }

        if (isSorting && !isTabPhase() && currentKill == null && currentToggle == null && (displayingSorting || phase == Phase.IDLE) && System.currentTimeMillis() - lastNotificationTime > 500) {
            displayingSorting = true;
            float targetHeight = getUnifiedTargetHeight(false, isSorting, false);
            targetHeight = getUnifiedAnimatedHeight(targetHeight);
            float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
            if (phase == Phase.EXPANDING) {
                if (dt < Timing.EXPAND) {
                    float p = easeOut(dt / (float) Timing.EXPAND);
                    setPhase(Phase.EXPANDING, p,
                            lerp(Size.BASE_W, animatedWidth, p),
                            lerp(Size.BASE_H, targetHeight, p),
                            lerp(1f, 1f, p));
                } else {
                    setPhase(Phase.DISPLAY, 1f, animatedWidth, targetHeight, 1f);
                }
            } else if (phase == Phase.DISPLAY) {
                setPhase(Phase.DISPLAY, 1f, animatedWidth, targetHeight, 1f);
            } else {
                phase = Phase.EXPANDING;
                toggleStartTime = System.currentTimeMillis();
            }
            animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
            animY = y;
            this.width = animW;
            this.height = animH;
            this.x = animX;
            return;
        }

        if (phase == Phase.TAB_EXPAND) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = clamp(tabDt / (Timing.TAB_TRANSITION * 0.45f), 0f, 1f);
                float expandT = clamp((tabDt - Timing.TAB_TRANSITION * 0.25f) / (Timing.TAB_TRANSITION * 0.75f), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_EXPAND, expandP,
                        lerp(Size.BASE_W, tabTargetW, mergeP),
                        lerp(Size.BASE_H, tabTargetH, expandP),
                        1f);
            } else {
                tabMergeProgress = 1f;
                setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
            }
        } else if (phase == Phase.TAB_COLLAPSE) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = clamp(1f - (tabDt / (Timing.TAB_TRANSITION * 0.45f)), 0f, 1f);
                float expandT = clamp(1f - ((tabDt - Timing.TAB_TRANSITION * 0.10f) / (Timing.TAB_TRANSITION * 0.90f)), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_COLLAPSE, expandP,
                        lerp(Size.BASE_W, tabTargetW, mergeP),
                        lerp(Size.BASE_H, tabTargetH, expandP),
                        1f);
            } else {
                tabMergeProgress = 0f;
                float idleW = getUnifiedIdleWidth();
                float idleH = getUnifiedIdleHeight();
                setPhase(Phase.IDLE, 0f, idleW, idleH, 1f);
                tabStartTime = -1L;
            }
        } else if (phase == Phase.TAB_DISPLAY) {
            tabMergeProgress = 1f;
            setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
        } else {
            if (currentToggle == null && currentKill == null && toggleStartTime == -1L && !isNoTotem && !isSorting) {
                closingSorting = false;
                float idleW = getUnifiedIdleWidth();
                float idleH = getUnifiedIdleHeight();
                setPhase(Phase.IDLE, 0f, idleW, idleH, 1f);
            } else if (dt < Timing.EXPAND) {
                float p = easeOut(dt / (float) Timing.EXPAND);
                float targetHeight = getUnifiedTargetHeight(isNoTotem, isSorting, currentKill != null || currentToggle != null);
                targetHeight = getUnifiedAnimatedHeight(targetHeight);
                float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
                setPhase(Phase.EXPANDING, p,
                        lerp(Size.BASE_W, animatedWidth, p),
                        lerp(Size.BASE_H, targetHeight, p),
                        lerp(1f, 1f, p));
            } else if (dt < Timing.EXPAND + Timing.DISPLAY) {
                float targetHeight = getUnifiedTargetHeight(isNoTotem, isSorting, currentKill != null || currentToggle != null);
                targetHeight = getUnifiedAnimatedHeight(targetHeight);
                float p = (dt - Timing.EXPAND) / (float) Timing.DISPLAY;
                float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
                setPhase(Phase.DISPLAY, p, animatedWidth, targetHeight, 1f);
            } else if (dt < Timing.EXPAND + Timing.DISPLAY + Timing.COLLAPSE_1) {
                float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY) / (float) Timing.COLLAPSE_1);
                float targetHeight = getUnifiedTargetHeight(isNoTotem, isSorting, currentKill != null || currentToggle != null);
                targetHeight = getUnifiedAnimatedHeight(targetHeight);
                float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
                setPhase(Phase.COLLAPSE_1, p, animatedWidth, targetHeight, 1f);
            } else {
                float timeInCollapse2 = dt - Timing.EXPAND - Timing.DISPLAY - Timing.COLLAPSE_1;
                float idleW = getUnifiedIdleWidth();
                float idleH = getUnifiedIdleHeight();
                if (timeInCollapse2 >= Timing.COLLAPSE_2) {
                    toggleStartTime = -1L;
                    closingSorting = false;
                    setPhase(Phase.IDLE, 0f, idleW, idleH, 1f);
                } else {
                    float p = easeOut(timeInCollapse2 / (float) Timing.COLLAPSE_2);
                    float targetHeight = getUnifiedTargetHeight(isNoTotem, isSorting, currentKill != null || currentToggle != null);
                    targetHeight = getUnifiedAnimatedHeight(targetHeight);
                    float animatedWidth = getUnifiedAnimatedWidth(targetExpandedWidth);
                    setPhase(Phase.COLLAPSE_2, p,
                            lerp(animatedWidth, idleW, p),
                            lerp(targetHeight, idleH, p),
                            1f);
                }
            }
        }

        animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
        animY = y;
        this.width = animW;
        this.height = animH;
        this.x = animX;
    }

    public float getRadius() {
        return radius.get().floatValue();
    }

    private void setPhase(Phase p, float prog, float w, float h, float blur) {
        this.phase = p;
        this.progress = prog;
        this.animW = w;
        this.animH = h;
        this.blurOpacity = interpolateBlurOpacity(blur);
    }

    private boolean isTabPhase() {
        return phase == Phase.TAB_EXPAND || phase == Phase.TAB_DISPLAY || phase == Phase.TAB_COLLAPSE;
    }

    private float getMergeProgress() {
        if (phase == Phase.TAB_EXPAND || phase == Phase.TAB_DISPLAY || phase == Phase.TAB_COLLAPSE)
            return tabMergeProgress;
        return progress;
    }

    private float interpolateBlurOpacity(float targetBlur) {
        float delta = targetBlur - this.blurOpacity;
        float interpolationFactor = 0.15f;
        return this.blurOpacity + delta * interpolationFactor;
    }

    private float getSideBlurOpacity() {
        if (isTabPhase()) {
            return (phase == Phase.TAB_EXPAND) ? (1f - tabMergeProgress) : (phase == Phase.TAB_COLLAPSE ? tabMergeProgress : 0f);
        }
        return 1f;
    }

    private void renderBlur(DrawContext context) {
        if (!blur.get()) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity));
        if (clampedBlurOpacity <= 0.005f) return;

        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), animX, animY, animW, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderSideBlurs(DrawContext context, float opacity) {
        if (isUnifiedLayout()) return;
        if (!blur.get() || opacity <= 0.05f) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity * opacity));
        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;

        if (phase == Phase.TAB_EXPAND) {
            timeBgX = lerp(timeBgX, animX, tabMergeProgress);
        } else if (phase == Phase.TAB_COLLAPSE) {
            timeBgX = lerp(timeBgX, animX, tabMergeProgress);
        }

        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;
        if (phase == Phase.TAB_EXPAND) {
            nameBgX = lerp(nameBgX, animX + animW - Size.ELEMENT_WIDTH, tabMergeProgress);
        } else if (phase == Phase.TAB_COLLAPSE) {
            nameBgX = lerp(nameBgX, animX + animW - Size.ELEMENT_WIDTH, tabMergeProgress);
        }

        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderContent() {
        switch (phase) {
            case IDLE -> renderIdle();
            case EXPANDING -> renderExpanding();
            case DISPLAY -> renderDisplay();
            case COLLAPSE_1 -> renderCollapse1();
            case COLLAPSE_2 -> renderCollapse2();
            case TAB_EXPAND -> renderTabExpand();
            case TAB_DISPLAY -> renderTabDisplay();
            case TAB_COLLAPSE -> renderTabCollapse();
        }
    }

    private float calculateSortingWidth() {
        int font = FontLoader.bold(12);
        String text = "Sorting";
        float textW = NanoVGHelper.getTextWidth(text, font, 12);
        return Math.max(Size.EXPANDED_W, textW + 10 + 60 + 20);
    }

    private void drawSortingInfo(int alpha) {
        if (alpha <= 5 || invManager == null) return;
        float centerY = animY + animH / 2f + 3;
        drawSortingInfoAt(alpha, centerY);
    }

    private void drawSortingInfoAt(int alpha, float centerY) {
        if (alpha <= 5 || invManager == null) return;
        int font = FontLoader.bold(12);
        String text = "Sorting";
        float textW = NanoVGHelper.getTextWidth(text, font, 12);

        float barW = 60f;
        float spacing = 10f;
        float totalContentW = textW + spacing + barW;

        float startX = animX + (animW - totalContentW) / 2f;

        float textY = centerY + 1f;
        NanoVGHelper.drawString(text, startX, textY, font, 12, withAlpha(Color.WHITE, alpha));

        float barH = 4;
        float barX = startX + textW + spacing;
        float barY = centerY - barH / 2f - 2f;

        float targetProg = (float) invManager.pendingActions / maxPendingActions;
        targetProg = Math.max(0f, Math.min(1f, targetProg));
        float prog = getSortingAnimatedProgress(targetProg);

        NanoVGHelper.drawRoundRect(barX, barY, barW, barH, barH / 2, withAlpha(new Color(255, 255, 255, 50), alpha));
        if (prog > 0) {
            NanoVGHelper.drawRoundRect(barX, barY, barW * prog, barH, barH / 2, withAlpha(ClickGui.color(0), alpha));
        }
    }

    private void renderIdle() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        if (isUnifiedLayout()) {
            drawCenteredTitle(1f);
        } else {
            drawSideInfo(0f, 1f);
            drawCenteredTitle(1f);
        }
    }

    private void renderExpanding() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        if (!isUnifiedLayout()) {
            drawSideInfo(progress, 1f);
        }
        int lines = getUnifiedPromptLineCount();
        if (isUnifiedLayout() && lines >= 2) {
            drawUnifiedMultiPrompts(alphaFromProgress(progress), progress);
            return;
        }
        if (currentKill != null) {
            drawKillInfo(alphaFromProgress(progress), 0f);
        } else if (isNoTotem) {
            drawNoTotemInfo(alphaFromProgress(progress));
        } else if (isSorting) {
            drawSortingInfo(alphaFromProgress(progress));
        } else if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(progress), 0f);
        }
        if (isUnifiedLayout() && !hasUnifiedPrompt()) {
            drawCenteredTitle(1f);
        }
    }

    private void renderDisplay() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        if (!isUnifiedLayout()) {
            drawSideInfo(1f, 1f);
        }
        int lines = getUnifiedPromptLineCount();
        if (isUnifiedLayout() && lines >= 2) {
            drawUnifiedMultiPrompts(255, progress);
            return;
        }
        if (currentKill != null) {
            drawKillInfo(255, progress);
        } else if (isNoTotem) {
            drawNoTotemInfo(255);
        } else if (isSorting) {
            drawSortingInfo(255);
        } else if (currentToggle != null) {
            drawToggleInfo(255, progress);
        }
        if (isUnifiedLayout() && !hasUnifiedPrompt()) {
            drawCenteredTitle(1f);
        }
    }

    private void renderCollapse1() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        if (!isUnifiedLayout()) {
            drawSideInfo(1f, 1f);
        }
        int lines = getUnifiedPromptLineCount();
        if (isUnifiedLayout() && lines >= 2) {
            drawUnifiedMultiPrompts(alphaFromProgress(1f - progress), 1f);
            return;
        }
        if (currentKill != null) {
            drawKillInfo(alphaFromProgress(1f - progress), 1f);
        } else if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(1f - progress), 1f);
        } else if (closingSorting) {
            if (isUnifiedLayout()) {
                drawCenteredTitle(1f);
            } else {
                drawSortingInfo(alphaFromProgress(1f - progress));
            }
        }
        if (isUnifiedLayout() && !hasUnifiedPrompt()) {
            drawCenteredTitle(1f);
        }
    }

    private void renderCollapse2() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        if (isUnifiedLayout()) {
            if (closingSorting) {
                drawCenteredTitle(1f);
            } else {
                float p = progress;
                if (p > 0.5f) {
                    float alpha = (p - 0.5f) / 0.5f;
                    drawCenteredTitle(alpha);
                }
            }
        } else {
            drawSideInfo(0f, 1f);
            drawCenteredTitle(progress);
        }
        if (isUnifiedLayout() && !hasUnifiedPrompt()) {
            drawCenteredTitle(1f);
        }
    }

    private void renderTabExpand() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        float progress = getMergeProgress();
        float alpha = 1f - progress;
        
        // During expansion (TAB_EXPAND), progress goes from 0 to 1
        // When progress is small (just started), alpha is close to 1 (show title/side info)
        // When progress is large (expanded), alpha is close to 0 (hide title/side info)
        
        if (!isUnifiedLayout()) {
            drawSideInfo(0f, alpha);
            drawCenteredTitle(alpha);
        }
    }

    private void renderTabDisplay() {
        drawBackground(Size.INVENTORY_BG_COLOR);
    }

    private void renderTabCollapse() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        float progress = getMergeProgress();
        float alpha = 1f - progress;
        
        // During collapse (TAB_COLLAPSE), merge progress goes from 1 to 0 (calculated in calculateState)
        // So alpha goes from 0 to 1
        // We want to fade IN the title/side info as it collapses back to idle
        
        if (!isUnifiedLayout()) {
            // Only draw if we have enough alpha to avoid "slide-out" ghosting
            // The slide-out happens because animW is shrinking while we draw side info relative to it
            // We need to fade out/in faster than the width change or clamp it
            
            if (alpha > 0.1f) {
                drawSideInfo(0f, alpha);
                drawCenteredTitle(alpha);
            }
        }
    }

    private boolean isUnifiedLayout() {
        return layout.is(Layout.Unified);
    }

    private void drawBackground(Color color) {
        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(animX, animY, animW, animH, getRadius(), color);
        } else {
            NanoVGHelper.drawRoundRect(animX, animY, animW, animH, getRadius(), color);
        }
    }

    private void drawCenteredTitle(float alpha) {
        if (alpha <= 0.05f) return;
        int font = FontLoader.bold((int) Size.LOGO_FONT_SIZE);
        String name = Sakura.MOD_NAME;
        float textW = NanoVGHelper.getTextWidth(name, font, Size.LOGO_FONT_SIZE);
        NanoVGHelper.drawGlowingString(name, animX + (animW - textW) / 2f, animY + animH / 2f + 4, font, Size.LOGO_FONT_SIZE, withAlpha(ClickGui.color(0), (int) (255 * alpha)), Size.GLOW_RADIUS);
    }

    private void drawUnifiedHeader(float alpha) {
        if (alpha <= 0.05f) return;

        int infoFont = FontLoader.medium(9);
        int titleFont = FontLoader.bold((int) Size.LOGO_FONT_SIZE);

        String time = LocalTime.now().format(TIME_FORMAT);
        String title = Sakura.MOD_NAME;
        String pingText = getPingText();

        float timeW = NanoVGHelper.getTextWidth(time, infoFont, Size.INFO_FONT_SIZE);
        float titleW = NanoVGHelper.getTextWidth(title, titleFont, Size.LOGO_FONT_SIZE);
        float pingW = NanoVGHelper.getTextWidth(pingText, infoFont, Size.INFO_FONT_SIZE);

        float spacing = 10f;
        float totalTextW = timeW + titleW + pingW + spacing * 2f;
        float padding = 6f;
        float totalW = totalTextW + padding * 2f;

        float startX = animX + (animW - totalW) / 2f + padding;
        float baseY = animY + 8f;

        Color infoColor = withAlpha(Color.WHITE, (int) (255 * alpha));
        Color titleColor = withAlpha(ClickGui.color(0), (int) (255 * alpha));

        float timeX = startX;
        float titleX = timeX + timeW + spacing;
        float pingX = titleX + titleW + spacing;

        float line1X = timeX + timeW + spacing * 0.5f;
        float line2X = titleX + titleW + spacing * 0.5f;
        float lineTop = animY + 4f;
        float lineBottom = animY + animH - 4f;
        int lineAlpha = (int) (160 * alpha);
        Color lineColor = new Color(255, 255, 255, Math.max(0, Math.min(255, lineAlpha)));

        NanoVGHelper.drawLine(line1X, lineTop, line1X, lineBottom, 0.6f, lineColor);
        NanoVGHelper.drawLine(line2X, lineTop, line2X, lineBottom, 0.6f, lineColor);

        NanoVGHelper.drawString(time, timeX, baseY + Size.INFO_FONT_SIZE * 0.5f, infoFont, Size.INFO_FONT_SIZE, infoColor);
        NanoVGHelper.drawString(title, titleX, baseY + Size.LOGO_FONT_SIZE * 0.5f, titleFont, Size.LOGO_FONT_SIZE - 1f, titleColor);
        NanoVGHelper.drawString(pingText, pingX, baseY + Size.INFO_FONT_SIZE * 0.5f, infoFont, Size.INFO_FONT_SIZE, infoColor);
    }

    private float calculateUnifiedHeaderWidth() {
        int infoFont = FontLoader.medium(9);
        int titleFont = FontLoader.bold((int) Size.LOGO_FONT_SIZE);

        String time = LocalTime.now().format(TIME_FORMAT);
        String title = Sakura.MOD_NAME;
        String pingText = getPingText();

        float timeW = NanoVGHelper.getTextWidth(time, infoFont, Size.INFO_FONT_SIZE);
        float titleW = NanoVGHelper.getTextWidth(title, titleFont, Size.LOGO_FONT_SIZE);
        float pingW = NanoVGHelper.getTextWidth(pingText, infoFont, Size.INFO_FONT_SIZE);

        float spacing = 10f;
        float padding = 6f;
        float totalTextW = timeW + titleW + pingW + spacing * 2f;
        return Math.max(Size.BASE_W, totalTextW + padding * 2f);
    }

    private float getUnifiedIdleWidth() {
        return Math.max(Size.EXPANDED_W, calculateUnifiedHeaderWidth());
    }

    private float getUnifiedIdleHeight() {
        return Size.EXPANDED_H;
    }

    private void drawUnifiedNoTotemAndSorting(int alpha) {
        if (alpha <= 5 || invManager == null) return;

        int noTotemFont = FontLoader.bold(12);
        String noTotemText = isChinese() ? "无不死图腾" : "No Totem";
        float noTotemW = NanoVGHelper.getTextWidth(noTotemText, noTotemFont, 12);

        float centerX = animX + animW / 2f;
        float headerZone = 18f;
        float contentTop = animY + headerZone;
        float contentBottom = animY + animH - 8f;
        float centerSpacing = 18f;

        float contentCenter = (contentTop + contentBottom) / 2f;
        float firstCenterY = contentCenter - centerSpacing / 2f;
        float secondCenterY = contentCenter + centerSpacing / 2f;

        drawSortingInfoAt(alpha, secondCenterY);
    }

    private void drawUnifiedNoTotemAndToggle(int alpha, float timeProgress) {
        if (alpha <= 5) return;

        boolean noTotemFirst;
        if (noTotemTriggerTime == 0L && toggleTriggerTime == 0L) {
            noTotemFirst = true;
        } else if (noTotemTriggerTime == 0L) {
            noTotemFirst = false;
        } else if (toggleTriggerTime == 0L) {
            noTotemFirst = true;
        } else {
            noTotemFirst = noTotemTriggerTime <= toggleTriggerTime;
        }

        float headerZone = 18f;
        float contentTop = animY + headerZone;
        float contentBottom = animY + animH - 8f;
        float centerSpacing = 18f;

        float contentCenter = (contentTop + contentBottom) / 2f;
        float firstCenterY = contentCenter - centerSpacing / 2f;
        float secondCenterY = contentCenter + centerSpacing / 2f;

        if (noTotemFirst) {
            drawNoTotemInfoAt(alpha, firstCenterY);
            drawToggleInfoAt(alpha, secondCenterY, timeProgress, false);
        } else {
            drawToggleInfoAt(alpha, firstCenterY, timeProgress, false);
            drawNoTotemInfoAt(alpha, secondCenterY);
        }
    }

    private enum PromptKind {
        NOTOTEM,
        SORTING,
        TOGGLE,
        KILL
    }

    private record PromptLine(PromptKind kind, long time) {
    }

    private void drawUnifiedMultiPrompts(int alpha, float timeProgress) {
        if (alpha <= 5) return;
        int linesCount = getUnifiedPromptLineCount();
        if (linesCount < 2) return;

        List<PromptLine> lines = new ArrayList<>();
        if (isNoTotem) {
            lines.add(new PromptLine(PromptKind.NOTOTEM, noTotemTriggerTime));
        }
        if (isSorting) {
            lines.add(new PromptLine(PromptKind.SORTING, sortingTriggerTime));
        }
        if (currentToggle != null) {
            lines.add(new PromptLine(PromptKind.TOGGLE, toggleTriggerTime));
        }
        if (currentKill != null) {
            lines.add(new PromptLine(PromptKind.KILL, killTriggerTime));
        }

        lines.sort((a, b) -> {
            long ta = a.time();
            long tb = b.time();
            if (ta == tb) {
                if (a.kind() == PromptKind.NOTOTEM && b.kind() != PromptKind.NOTOTEM) return -1;
                if (b.kind() == PromptKind.NOTOTEM && a.kind() != PromptKind.NOTOTEM) return 1;
                return 0;
            }
            return Long.compare(ta, tb);
        });

        float headerZone = 18f;
        float contentTop = animY + headerZone;
        float contentBottom = animY + animH - 8f;
        float centerSpacing = 18f;
        float contentCenter = (contentTop + contentBottom) / 2f;

        int n = lines.size();
        boolean hasSortingLine = lines.stream().anyMatch(l -> l.kind() == PromptKind.SORTING);
        boolean hasToggleLine = lines.stream().anyMatch(l -> l.kind() == PromptKind.TOGGLE);
        boolean splitSortingToggle = n == 2 && hasSortingLine && hasToggleLine;
        float upperHalfCenter = contentTop + (contentCenter - contentTop) * 0.28f;
        float lowerHalfCenter = contentCenter + (contentBottom - contentCenter) * 0.55f;
        for (int i = 0; i < n; i++) {
            PromptLine line = lines.get(i);
            float centerY;
            if (splitSortingToggle) {
                centerY = line.kind() == PromptKind.SORTING ? upperHalfCenter : lowerHalfCenter;
            } else {
                float offset = (i - (n - 1) / 2f) * centerSpacing;
                centerY = contentCenter + offset;
            }
            if (line.kind() == PromptKind.NOTOTEM) {
                drawNoTotemInfoAt(alpha, centerY);
            } else if (line.kind() == PromptKind.SORTING) {
                drawSortingInfoAt(alpha, centerY);
            } else if (line.kind() == PromptKind.TOGGLE) {
                drawToggleInfoAt(alpha, centerY, timeProgress, false);
            } else if (line.kind() == PromptKind.KILL) {
                drawKillInfoAt(alpha, centerY, timeProgress, false);
            }
        }
    }

    private boolean hasUnifiedPrompt() {
        return isNoTotem || isSorting || currentKill != null || currentToggle != null;
    }

    private int getUnifiedPromptLineCount() {
        if (!isUnifiedLayout()) return 0;
        int lines = 0;
        if (isNoTotem) lines++;
        if (isSorting) lines++;
        if (currentToggle != null) lines++;
        if (currentKill != null) lines++;
        return lines;
    }

    private float getUnifiedAnimatedHeight(float baseTargetHeight) {
        int lines = getUnifiedPromptLineCount();
        long now = System.currentTimeMillis();

        if (unifiedPromptLines != lines) {
            unifiedPromptLines = lines;
            unifiedHeightAnimStart = now;
            unifiedHeightFrom = animH > 0f ? animH : baseTargetHeight;
            unifiedHeightTo = baseTargetHeight;
        }

        if (unifiedHeightAnimStart <= 0L) {
            return baseTargetHeight;
        }

        long dt = now - unifiedHeightAnimStart;
        long duration = 350L;
        if (dt >= duration) {
            unifiedHeightAnimStart = 0L;
            return baseTargetHeight;
        }

        float t = dt / (float) duration;
        float p = easeOut(t);
        return lerp(unifiedHeightFrom, unifiedHeightTo, p);
    }

    private float getUnifiedAnimatedWidth(float baseTargetWidth) {
        long now = System.currentTimeMillis();
        if (unifiedWidthTo != baseTargetWidth) {
            unifiedWidthAnimStart = now;
            unifiedWidthFrom = animW > 0f ? animW : baseTargetWidth;
            unifiedWidthTo = baseTargetWidth;
        }
        if (unifiedWidthAnimStart <= 0L) {
            return baseTargetWidth;
        }
        long dt = now - unifiedWidthAnimStart;
        long duration = 350L;
        if (dt >= duration) {
            unifiedWidthAnimStart = 0L;
            return baseTargetWidth;
        }
        float t = dt / (float) duration;
        float p = easeOut(t);
        return lerp(unifiedWidthFrom, unifiedWidthTo, p);
    }

    private float getSortingAnimatedProgress(float targetProg) {
        long now = System.currentTimeMillis();
        if (sortingAnimTo != targetProg) {
            sortingAnimStart = now;
            sortingAnimFrom = sortingAnimTo;
            sortingAnimTo = targetProg;
        }
        if (sortingAnimStart <= 0L) {
            return targetProg;
        }
        long dt = now - sortingAnimStart;
        long duration = 200L;
        if (dt >= duration) {
            sortingAnimStart = 0L;
            return sortingAnimTo;
        }
        float t = dt / (float) duration;
        float p = easeOut(t);
        return lerp(sortingAnimFrom, sortingAnimTo, p);
    }

    private void drawSideInfo(float expandProgress, float alpha) {
        if (alpha <= 0.05f) return;

        int font = FontLoader.medium(9);
        Color color = withAlpha(Color.WHITE, (int) (255 * alpha));
        float centerY = animY + animH / 2f + 3;
        Color bgColor = withAlpha(Size.INVENTORY_BG_COLOR, (int) (70 * alpha));

        // Time
        String time = LocalTime.now().format(TIME_FORMAT);
        float timeW = NanoVGHelper.getTextWidth(time, font, Size.INFO_FONT_SIZE);
        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;

        if (phase == Phase.TAB_EXPAND) {
            timeBgX = lerp(timeBgX, animX, tabMergeProgress);
        } else if (phase == Phase.TAB_COLLAPSE) {
            timeBgX = lerp(timeBgX, animX, tabMergeProgress);
        }

        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(time, timeBgX + (Size.ELEMENT_WIDTH - timeW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);

        // FPS
        String username = "FPS:" + mc.getCurrentFps();
        float nameW = NanoVGHelper.getTextWidth(username, font, Size.INFO_FONT_SIZE);
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;

        if (phase == Phase.TAB_EXPAND) {
            nameBgX = lerp(nameBgX, animX + animW - Size.ELEMENT_WIDTH, tabMergeProgress);
        } else if (phase == Phase.TAB_COLLAPSE) {
            nameBgX = lerp(nameBgX, animX + animW - Size.ELEMENT_WIDTH, tabMergeProgress);
        }

        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(username, nameBgX + (Size.ELEMENT_WIDTH - nameW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);
    }

    private String getPingText() {
        if (mc.getNetworkHandler() == null || mc.player == null) {
            return "Ping";
        }
        PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (entry == null) {
            return "Ping";
        }
        return entry.getLatency() + "ms";
    }

    private float getUnifiedTargetHeight(boolean hasNoTotem, boolean hasSorting, boolean hasNotification) {
        if (!isUnifiedLayout()) {
            return Size.EXPANDED_H;
        }
        float headerH = 20f;
        float blockH = 14f;
        float spacing = 8f;
        int blocks = getUnifiedPromptLineCount();
        if (blocks == 0) {
            return Size.BASE_H;
        }
        float total = headerH + blocks * blockH + Math.max(0, blocks - 1) * spacing;
        if (hasNotification && blocks >= 2) {
            total += 12f;
        }
        return Math.max(Size.EXPANDED_H, total);
    }

    private void renderCapturedTab(DrawContext context) {
        if (playerList == null) return;

        // Calculate opacity based on phase
        float opacity = 1f;
        if (phase == Phase.TAB_EXPAND) {
            opacity = getMergeProgress();
        } else if (phase == Phase.TAB_COLLAPSE) {
            // Accelerate fade out: when merge progress is 0.5, opacity is 0.0
            float p = getMergeProgress();
            opacity = Math.max(0f, (p - 0.5f) * 2f);
        }
        
        // Don't render if too transparent
        if (opacity < 0.05f) return;

        int innerX1 = (int) (animX + Size.TAB_PADDING);
        int innerY1 = (int) (animY + Size.TAB_PADDING);
        int innerX2 = (int) (animX + animW - Size.TAB_PADDING);
        int innerY2 = (int) (animY + animH - Size.TAB_PADDING);
        
        // Ensure bounds are valid
        if (innerX2 <= innerX1 || innerY2 <= innerY1) return;

        context.enableScissor(innerX1, innerY1, innerX2, innerY2);

        int innerW = innerX2 - innerX1;
        int fontH = mc.textRenderer.fontHeight;

        // Apply opacity to text colors
        int textAlpha = (int) (255 * opacity);
        int textColor = (textAlpha << 24) | 0xFFFFFF;
        int pingColor = (textAlpha << 24) | 0xA0A0A0;

        int y = (int) (animY + Size.TAB_HEADER_Y);

        Text headerText = capturedTabHeader;
        if (headerText == null || headerText.getString().isEmpty()) {
            headerText = Text.literal("Players: " + playerList.size());
        }
        List<OrderedText> headerLines = mc.textRenderer.wrapLines(headerText, innerW);
        for (OrderedText line : headerLines) {
            int lineW = mc.textRenderer.getWidth(line);
            int x = (int) (animX + (animW - lineW) / 2f);
            context.drawTextWithShadow(mc.textRenderer, line, x, y, textColor);
            y += fontH;
        }
        y += 8;

        int listY = Math.max((int) (animY + Size.TAB_LIST_Y), y);
        int rowH = (int) Size.TAB_PLAYER_HEIGHT;
        int headSize = 10;
        int headYOffset = Math.max(0, (rowH - headSize) / 2);

        int i = 0;
        float spacing = 5;
        for (PlayerListEntry entry : playerList) {
            int col = i / tabRows;
            int row = i % tabRows;
            if (col >= tabColumns) break;

            int colX = (int) (innerX1 + col * (tabColumnWidth + spacing));
            int rowY = listY + row * rowH;
            if (rowY + rowH > innerY2) break;

            int headX = colX;
            int headY = rowY + headYOffset;

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1f, 1f, 1f, opacity); // Apply opacity to textures
            context.drawTexture(RenderLayer::getGuiTextured,
                    entry.getSkinTextures().texture(),
                    headX, headY,
                    8, 8,
                    headSize, headSize,
                    8, 8,
                    64, 64);
            context.drawTexture(RenderLayer::getGuiTextured,
                    entry.getSkinTextures().texture(),
                    headX, headY,
                    40, 8,
                    headSize, headSize,
                    8, 8,
                    64, 64);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // Reset
            RenderSystem.disableBlend();

            String ping = entry.getLatency() + "ms";
            int pingW = mc.textRenderer.getWidth(ping);
            int pingX = colX + tabColumnWidth - pingW;
            int textY = rowY + Math.max(0, (rowH - fontH) / 2);
            context.drawTextWithShadow(mc.textRenderer, ping, pingX, textY, pingColor);

            Text nameText = mc.inGameHud.getPlayerListHud().getPlayerName(entry);
            int nameX = headX + headSize + 4;
            int nameClipX2 = pingX - 6;
            int maxNameW = Math.max(0, nameClipX2 - nameX);
            
            if (maxNameW > 0) {
                net.minecraft.text.StringVisitable trimmed = mc.textRenderer.trimToWidth(nameText, maxNameW);
                OrderedText ordered = net.minecraft.util.Language.getInstance().reorder(trimmed);
                context.drawTextWithShadow(mc.textRenderer, ordered, nameX, textY, textColor);
            }

            i++;
        }

        Text footerText = capturedTabFooter;
        if (footerText != null && !footerText.getString().isEmpty()) {
            List<OrderedText> footerLines = mc.textRenderer.wrapLines(footerText, innerW);
            int footerY = innerY2 - footerLines.size() * fontH;
            
            // Only render footer if it doesn't overlap with header/top area
            // This prevents "flashing" at the top during initial expansion
            if (footerY >= innerY1) {
                for (OrderedText line : footerLines) {
                    int lineW = mc.textRenderer.getWidth(line);
                    int x = (int) (animX + (animW - lineW) / 2f);
                    context.drawTextWithShadow(mc.textRenderer, line, x, footerY, textColor);
                    footerY += fontH;
                }
            }
        }

        context.disableScissor();
    }

    private void drawToggleInfo(int alpha, float timeProgress) {
        float centerY = animY + (animH - 3) / 2f;
        drawToggleInfoAt(alpha, centerY, timeProgress, true);
    }

    private void drawToggleInfoAt(int alpha, float centerY, float timeProgress, boolean showProgress) {
        if (currentToggle == null) return;
        float padding = 6, iconSize = 16;
        int iconFont = FontLoader.icons(iconSize);
        String icon = currentToggle.enabled ? "U" : "T";
        Color iconColor = ClickGui.color(0);
        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        NanoVGHelper.drawString(icon, animX + padding + 6, centerY + iconSize * 0.35f, iconFont, iconSize, withAlpha(iconColor, alpha));
        int textFont = FontLoader.medium((int) Size.LOGO_FONT_SIZE);
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");
        NanoVGHelper.drawString(status, animX + padding + iconW + 14, centerY + Size.LOGO_FONT_SIZE * 0.35f, textFont, Size.LOGO_FONT_SIZE - 2f, withAlpha(Color.WHITE, alpha));
        if (showProgress) {
            drawProgressBar(alpha, timeProgress);
        }
    }

    private void drawProgressBar(int alpha, float timeProgress) {
        float padding = 8, barH = 1.5f;
        float barY = animY + animH - barH - 3;
        float maxW = Math.max(0, animW - padding * 2);
        float progress = Math.max(0f, Math.min(1f, 1f - timeProgress));
        float currentW = maxW * progress;

        NanoVGHelper.drawRoundRect(animX + padding, barY, maxW, barH, barH / 2, withAlpha(ClickGui.color(0), (int) (50 * (alpha / 255f))));
        if (currentW > 0) {
            NanoVGHelper.drawRoundRect(animX + padding, barY, currentW, barH, barH / 2, withAlpha(ClickGui.color(0), (int) (220 * (alpha / 255f))));
        }
    }

    private void drawKillInfo(int alpha, float timeProgress) {
        float centerY = animY + (animH - 3) / 2f;
        drawKillInfoAt(alpha, centerY, timeProgress, true);
    }

    private void drawKillInfoAt(int alpha, float centerY, float timeProgress, boolean showProgress) {
        if (currentKill == null) return;
        float padding = 6, iconSize = 16;
        int iconFont = FontLoader.icons(iconSize);
        String icon = "D";
        Color iconColor = new Color(255, 50, 50);
        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        NanoVGHelper.drawString(icon, animX + padding + 6, centerY + iconSize * 0.35f, iconFont, iconSize, withAlpha(iconColor, alpha));
        int textFont = FontLoader.medium((int) Size.LOGO_FONT_SIZE);
        String status = (isChinese() ? "击杀 " : "Killed ") + currentKill.name;
        NanoVGHelper.drawString(status, animX + padding + iconW + 14, centerY + Size.LOGO_FONT_SIZE * 0.35f, textFont, Size.LOGO_FONT_SIZE - 2f, withAlpha(Color.WHITE, alpha));

        if (showProgress) {
            drawProgressBar(alpha, timeProgress);
        }
    }

    private float calculateExpandedWidth() {
        if (currentToggle == null) return Size.EXPANDED_W;

        float padding = 6, iconSize = 16, textSize = Size.LOGO_FONT_SIZE;
        int iconFont = FontLoader.icons(iconSize);
        int textFont = FontLoader.medium(textSize);

        String icon = currentToggle.enabled ? "U" : "T";
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");

        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        float textW = NanoVGHelper.getTextWidth(status, textFont, textSize);
        float needed = padding * 2 + iconW + 4 + textW;

        return Math.max(Size.EXPANDED_W, Math.max(needed, 41));
    }

    private float calculateKillWidth() {
        if (currentKill == null) return Size.EXPANDED_W;

        float padding = 6, iconSize = 16, textSize = Size.LOGO_FONT_SIZE;
        int iconFont = FontLoader.icons(iconSize);
        int textFont = FontLoader.medium(textSize);

        String icon = "D";
        String status = (isChinese() ? "击杀 " : "Killed ") + currentKill.name;

        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        float textW = NanoVGHelper.getTextWidth(status, textFont, textSize);
        float needed = padding * 2 + iconW + 4 + textW;

        return Math.max(Size.EXPANDED_W, Math.max(needed, 41));
    }

    private long ela() {
        return toggleStartTime == -1L ? 0 : System.currentTimeMillis() - toggleStartTime;
    }

    private long elaTab() {
        return tabStartTime == -1L ? 0 : System.currentTimeMillis() - tabStartTime;
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private static float easeOut(float t) {
        return (float) Easing.CUBIC_OUT.ease(t);
    }

    private static int alphaFromProgress(float p) {
        return (int) (255 * p);
    }

    private static Color withAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, Math.min(255, alpha)));
    }

    private boolean shouldShowNoTotem() {
        AutoTotem autoTotem = (AutoTotem) Sakura.MODULES.getModule(AutoTotem.class);
        if (autoTotem == null || !autoTotem.isEnabled()) return false;
        if (mc.player == null || mc.player.getInventory() == null) return false;

        float currentHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        if (currentHealth > autoTotem.healthThreshold.get()) {
            return false;
        }

        boolean hasInOffhand = mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING);
        
        boolean hasInInv = false;
        for (int i = 9; i < 36; i++) {
            if (mc.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                hasInInv = true;
                break;
            }
        }

        return !hasInOffhand && hasInInv;
    }

    private float calculateNoTotemWidth() {
        int font = FontLoader.bold(12);
        String text = isChinese() ? "无不死图腾" : "No Totem";
        float textW = NanoVGHelper.getTextWidth(text, font, 12);
        return Math.max(Size.EXPANDED_W, textW + 30);
    }

    private void drawNoTotemInfo(int alpha) {
        if (alpha <= 5) return;
        float centerY = animY + animH / 2f + 4;
        drawNoTotemInfoAt(alpha, centerY);
    }

    private void drawNoTotemInfoAt(int alpha, float centerY) {
        int font = FontLoader.bold(12);
        String text = isChinese() ? "无不死图腾" : "No Totem";
        float textW = NanoVGHelper.getTextWidth(text, font, 12);

        float centerX = animX + animW / 2f;
        NanoVGHelper.drawString(text, centerX - textW / 2f, centerY, font, 12, withAlpha(new Color(255, 80, 80), alpha));
    }

    private boolean isChinese() {
        return mc.getLanguageManager().getLanguage().toLowerCase().startsWith("zh");
    }

    private record ToggleInfo(String name, boolean enabled) {
    }

    private record KillInfo(String name) {
    }
}
