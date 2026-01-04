package dev.sakura.client.module.impl.hud;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.misc.WorldLoadEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Easing;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotifyHud extends HudModule {
    public static NotifyHud INSTANCE;

    private final BoolValue totemPop = new BoolValue("TotemPop", "图腾弹出", true);
    private final BoolValue selfPop = new BoolValue("SelfPop", "自身弹出", true, totemPop::get);
    private final BoolValue enemyPop = new BoolValue("EnemyPop", "敌人弹出", true, totemPop::get);
    private final BoolValue deathNotify = new BoolValue("DeathNotify", "死亡通知", true);
    private final BoolValue packetWarning = new BoolValue("PacketWarning", "发包警告", true);
    private final NumberValue<Integer> packetThreshold = new NumberValue<>("PacketLimit", "发包限制", 20, 10, 50, 1, packetWarning::get);
    private final BoolValue blockWarning = new BoolValue("BlockWarning", "方块警告", true);
    private final NumberValue<Integer> blockThreshold = new NumberValue<>("BlockThreshold", "方块阈值", 20, 5, 64, 1, blockWarning::get);
    private final BoolValue blur = new BoolValue("Blur", "模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 4.0, 1.0, 20.0, 0.5, blur::get);

    private final List<NotifyEntry> notifications = new CopyOnWriteArrayList<>();
    private final Map<UUID, Integer> popCounts = new HashMap<>();
    private final LinkedList<Long> packetTimestamps = new LinkedList<>();
    private boolean packetWarningTriggered = false;
    private boolean blockWarningTriggered = false;
    private boolean wasAlive = true;
    private boolean hasDied = false;
    private int lastDeathMessageIndex = -1;

    // 这么强？！
    private static final String[] DEATH_MESSAGES = {
            "别灰心，打回去！",
            "一定是操作失误了吧？",
            "呜呜呜一定是参数不够强！",
            "对面肯定是Hachimi用户！"
    };
    private static final Random RANDOM = new Random();

    private static final float NOTIFICATION_WIDTH = 200f;
    private static final float NOTIFICATION_HEIGHT = 48f;
    private static final float PADDING = 10f;
    private static final float RADIUS = 8f;
    private static final float PROGRESS_HEIGHT = 2.5f;
    private static final long DURATION = 2000L;
    private static final long SLIDE_DURATION = 300L;

    public NotifyHud() {
        super("Notify", "提示", -210, -58);
        this.width = NOTIFICATION_WIDTH;
        this.height = NOTIFICATION_HEIGHT;
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        notifications.clear();
        popCounts.clear();
        packetTimestamps.clear();
        packetWarningTriggered = false;
        blockWarningTriggered = false;
        wasAlive = true;
        hasDied = false;
        lastDeathMessageIndex = -1;
    }

    @Override
    protected void onDisable() {
        notifications.clear();
        popCounts.clear();
        packetTimestamps.clear();
        packetWarningTriggered = false;
        blockWarningTriggered = false;
        wasAlive = true;
        hasDied = false;
        lastDeathMessageIndex = -1;
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (event.getType() == EventType.SEND && packetWarning.get()) {
            handlePacketSend();
        }

        if (event.getType() == EventType.RECEIVE && totemPop.get()) {
            Packet<?> packet = event.getPacket();
            if (packet instanceof EntityStatusS2CPacket statusPacket) {
                if (statusPacket.getStatus() == 35) {
                    Entity entity = statusPacket.getEntity(mc.world);
                    if (entity instanceof PlayerEntity player) {
                        handleTotemPop(player);
                    }
                }
            }
        }
    }

    private void handlePacketSend() {
        long now = System.currentTimeMillis();
        packetTimestamps.addLast(now);

        while (!packetTimestamps.isEmpty()) {
            Long firstTimestamp = packetTimestamps.peekFirst();
            if (firstTimestamp == null || now - firstTimestamp > 1000L) {
                packetTimestamps.pollFirst();
            } else {
                break;
            }
        }

        int currentPackets = packetTimestamps.size();
        int threshold = packetThreshold.get();

        if (currentPackets >= threshold) {
            if (!packetWarningTriggered) {
                packetWarningTriggered = true;
                addNotification(NotifyType.PACKET_WARNING, "Packet Overflow",
                        currentPackets + "/" + threshold + " packets/s");
            }
        } else {
            packetWarningTriggered = false;
        }
    }

    private void handleTotemPop(PlayerEntity player) {
        boolean isSelf = player == mc.player;

        if (isSelf && !selfPop.get()) return;
        if (!isSelf && !enemyPop.get()) return;

        UUID uuid = player.getUuid();
        int count = popCounts.getOrDefault(uuid, 0) + 1;
        popCounts.put(uuid, count);

        if (isSelf) {
            addNotification(NotifyType.SELF_POP, "You are losing!", count + " pop" + (count > 1 ? "s" : ""));
        } else {
            String name = player.getName().getString();
            addNotification(NotifyType.ENEMY_POP, name, count + " pop" + (count > 1 ? "s" : ""));
        }
    }

    public void checkBlockWarning(int blockCount) {
        if (!blockWarning.get()) return;
        if (blockCount <= blockThreshold.get()) {
            if (!blockWarningTriggered) {
                blockWarningTriggered = true;
                addNotification(NotifyType.BLOCK_WARNING, "Warning!", "Blocks remaining: " + blockCount);
            }
        } else {
            blockWarningTriggered = false;
        }
    }

    public void updateBlockWarning(int blockCount) {
        if (!blockWarning.get()) return;
        if (blockCount <= blockThreshold.get()) {
            if (!blockWarningTriggered) {
                blockWarningTriggered = true;
                addNotification(NotifyType.BLOCK_WARNING, "Warning!", "Blocks remaining: " + blockCount);
            }
        } else {
            blockWarningTriggered = false;
        }
    }

    public int getBlockThreshold() {
        return blockThreshold.get();
    }

    public boolean isBlockWarningEnabled() {
        return blockWarning.get();
    }

    private void addNotification(NotifyType type, String title, String subtitle) {
        notifications.add(new NotifyEntry(type, title, subtitle));
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        popCounts.clear();
        notifications.clear();
        packetWarningTriggered = false;
        blockWarningTriggered = false;
        wasAlive = true;
        hasDied = false;
    }

    @Override
    public void onRenderContent() {
        if (mc.player == null) return;

        if (deathNotify.get()) {
            boolean isAlive = mc.player.isAlive();
            if (wasAlive && !isAlive) {
                hasDied = true;
            }
            if (!wasAlive && isAlive && hasDied) {
                int newIndex;
                if (DEATH_MESSAGES.length > 1) {
                    do {
                        newIndex = RANDOM.nextInt(DEATH_MESSAGES.length);
                    } while (newIndex == lastDeathMessageIndex);
                } else {
                    newIndex = 0;
                }
                lastDeathMessageIndex = newIndex;
                addNotification(NotifyType.DEATH, "You Die!", DEATH_MESSAGES[newIndex]);
                hasDied = false;
            }
            wasAlive = isAlive;
        }

        notifications.removeIf(NotifyEntry::shouldRemove);

        if (notifications.isEmpty()) return;

        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();
        float baseX = screenWidth - PADDING - NOTIFICATION_WIDTH;
        float baseY = screenHeight - PADDING - NOTIFICATION_HEIGHT;

        this.x = baseX;
        this.y = baseY;

        if (blur.get()) {
            NanoVGRenderer.INSTANCE.withRawCoords(() -> {
                float currentY = baseY;
                for (int i = notifications.size() - 1; i >= 0; i--) {
                    NotifyEntry entry = notifications.get(i);
                    float slideOffset = entry.getSlideOffset();
                    float alpha = entry.getAlpha();

                    if (alpha > 0.01f) {
                        float notifyX = screenWidth - PADDING - slideOffset;
                        if (alpha > 0.1f) {
                            Shader2DUtil.drawRoundedBlur(
                                    getMatrix(),
                                    notifyX, currentY,
                                    NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT,
                                    RADIUS,
                                    new Color(0, 0, 0, 0),
                                    blurStrength.get().floatValue() * alpha,
                                    alpha
                            );
                        }
                        currentY -= (NOTIFICATION_HEIGHT + PADDING) * alpha;
                    }
                }
            });
        }

        float currentY = baseY;
        for (int i = notifications.size() - 1; i >= 0; i--) {
            NotifyEntry entry = notifications.get(i);
            float slideOffset = entry.getSlideOffset();
            float alpha = entry.getAlpha();

            if (alpha > 0.01f) {
                float notifyX = screenWidth - PADDING - slideOffset;
                renderContent(entry, notifyX, currentY, alpha);
                currentY -= (NOTIFICATION_HEIGHT + PADDING) * alpha;
            }
        }
    }

    private void renderContent(NotifyEntry entry, float x, float y, float alpha) {
        int textAlpha = (int) (255 * alpha);
        int bgAlpha = (int) (200 * alpha);

        float iconAreaWidth = NOTIFICATION_WIDTH / 4f;

        Color bgColor = new Color(20, 20, 25, bgAlpha);
        Color iconBgColor = getIconBgColor(entry.type, alpha);
        Color iconColor = new Color(255, 255, 255, textAlpha);
        Color titleColor = new Color(255, 255, 255, textAlpha);
        Color subtitleColor = new Color(180, 180, 180, textAlpha);
        Color progressBgColor = new Color(60, 60, 70, (int) (150 * alpha));
        Color progressColor = getProgressColor(entry.type, textAlpha);

        NanoVGHelper.drawRoundRect(x, y, NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT, RADIUS, bgColor);

        NanoVGHelper.drawRect(x + RADIUS, y, iconAreaWidth - RADIUS, NOTIFICATION_HEIGHT, iconBgColor);
        NanoVGHelper.drawRoundRect(x, y, RADIUS * 2, NOTIFICATION_HEIGHT, RADIUS, iconBgColor);

        int iconFont = FontLoader.solid(18);
        String icon = getIcon(entry.type);
        float iconWidth = NanoVGHelper.getTextWidth(icon, iconFont, 18);
        float iconX = x + (iconAreaWidth - iconWidth) / 2f;
        float iconY = y + NOTIFICATION_HEIGHT / 2f + 6f;
        NanoVGHelper.drawString(icon, iconX, iconY, iconFont, 18, iconColor);

        int titleFont = FontLoader.medium(12);
        int subtitleFont = FontLoader.regular(10);

        float textX = x + iconAreaWidth + PADDING;
        float titleY = y + 18f;
        float subtitleY = y + 32f;

        NanoVGHelper.drawString(entry.title, textX, titleY, titleFont, 12, titleColor);
        NanoVGHelper.drawString(entry.subtitle, textX, subtitleY, subtitleFont, 10, subtitleColor);

        float progressWidth = NOTIFICATION_WIDTH - iconAreaWidth - PADDING * 2;
        float progressX = x + iconAreaWidth + PADDING;
        float progressY = y + NOTIFICATION_HEIGHT - PROGRESS_HEIGHT - 5f;
        float progress = entry.getProgress();

        NanoVGHelper.drawRoundRect(progressX, progressY, progressWidth, PROGRESS_HEIGHT,
                PROGRESS_HEIGHT / 2f, progressBgColor);

        if (progress > 0.01f) {
            NanoVGHelper.drawRoundRect(progressX, progressY, progressWidth * progress, PROGRESS_HEIGHT,
                    PROGRESS_HEIGHT / 2f, progressColor);
        }
    }

    private Color getIconBgColor(NotifyType type, float alpha) {
        int r, g, b;
        switch (type) {
            case SELF_POP -> {
                r = 130;
                g = 70;
                b = 180;
            }
            case ENEMY_POP -> {
                r = 90;
                g = 70;
                b = 200;
            }
            case PACKET_WARNING -> {
                r = 150;
                g = 90;
                b = 190;
            }
            case DEATH -> {
                r = 180;
                g = 50;
                b = 80;
            }
            case BLOCK_WARNING -> {
                r = 200;
                g = 170;
                b = 50;
            }
            default -> {
                r = 90;
                g = 70;
                b = 200;
            }
        }
        return new Color((int) (r * alpha), (int) (g * alpha), (int) (b * alpha));
    }

    private Color getProgressColor(NotifyType type, int alpha) {
        return switch (type) {
            case SELF_POP -> new Color(170, 110, 255, alpha);
            case ENEMY_POP -> new Color(130, 110, 255, alpha);
            case PACKET_WARNING -> new Color(190, 130, 255, alpha);
            case DEATH -> new Color(255, 80, 120, alpha);
            case BLOCK_WARNING -> new Color(255, 220, 80, alpha);
        };
    }

    private String getIcon(NotifyType type) {
        return switch (type) {
            case SELF_POP -> "\uf071";
            case ENEMY_POP -> "\uf007";
            case PACKET_WARNING -> "\uf70c";
            case DEATH -> "\uf714";
            case BLOCK_WARNING -> "\uf1b2";
        };
    }

    public void clearPopCounts() {
        popCounts.clear();
    }

    private enum NotifyType {
        SELF_POP,
        ENEMY_POP,
        PACKET_WARNING,
        DEATH,
        BLOCK_WARNING
    }

    private static class NotifyEntry {
        final NotifyType type;
        final String title;
        String subtitle;
        long startTime;
        boolean closing = false;
        long closeStartTime = -1L;

        NotifyEntry(NotifyType type, String title, String subtitle) {
            this.type = type;
            this.title = title;
            this.subtitle = subtitle;
            this.startTime = System.currentTimeMillis();
        }

        void resetTime() {
            this.startTime = System.currentTimeMillis();
            this.closing = false;
            this.closeStartTime = -1L;
        }

        float getSlideOffset() {
            long elapsed = System.currentTimeMillis() - startTime;

            if (elapsed < SLIDE_DURATION) {
                float progress = elapsed / (float) SLIDE_DURATION;
                float eased = (float) Easing.CUBIC_OUT.ease(progress);
                return NOTIFICATION_WIDTH * eased;
            }

            if (closing) {
                long closeElapsed = System.currentTimeMillis() - closeStartTime;
                if (closeElapsed < SLIDE_DURATION) {
                    float progress = closeElapsed / (float) SLIDE_DURATION;
                    float eased = (float) Easing.CUBIC_IN.ease(progress);
                    return NOTIFICATION_WIDTH * (1f - eased);
                }
                return 0f;
            }

            return NOTIFICATION_WIDTH;
        }

        float getAlpha() {
            long elapsed = System.currentTimeMillis() - startTime;

            if (elapsed < SLIDE_DURATION) {
                float progress = elapsed / (float) SLIDE_DURATION;
                return (float) Easing.CUBIC_OUT.ease(progress);
            }

            if (closing) {
                long closeElapsed = System.currentTimeMillis() - closeStartTime;
                if (closeElapsed < SLIDE_DURATION) {
                    float progress = closeElapsed / (float) SLIDE_DURATION;
                    return 1f - (float) Easing.CUBIC_IN.ease(progress);
                }
                return 0f;
            }

            return 1f;
        }

        float getProgress() {
            if (closing) return 0f;

            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed < SLIDE_DURATION) return 1f;

            long displayTime = elapsed - SLIDE_DURATION;
            float progress = 1f - (displayTime / (float) DURATION);
            return Math.max(0f, Math.min(1f, progress));
        }

        boolean isExpired() {
            if (closing) return true;

            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed > SLIDE_DURATION + DURATION) {
                closing = true;
                closeStartTime = System.currentTimeMillis();
                return true;
            }
            return false;
        }

        boolean shouldRemove() {
            isExpired();
            if (closing && closeStartTime != -1L) {
                return System.currentTimeMillis() - closeStartTime > SLIDE_DURATION;
            }
            return false;
        }
    }
}