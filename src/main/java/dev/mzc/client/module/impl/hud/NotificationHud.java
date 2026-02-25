package dev.mzc.client.module.impl.hud;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.entity.AttackEvent;
import dev.mzc.client.events.misc.WorldLoadEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.manager.impl.NotificationManager;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.impl.client.HudEditor;
import dev.mzc.client.values.Value;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationHud extends HudModule {
    public enum AlignedEnum {
        LEFT("左对齐"),
        RIGHT("右对齐");

        private final String cnName;

        AlignedEnum(String cnName) {
            this.cnName = cnName;
        }
    }

    private final Value<Double> maxWidthConfig = new NumberValue<>("MaxWidth", "最大宽度", 300.0, 100.0, 500.0, 10.0);
    private final Value<Double> roundRadius = new NumberValue<>("RoundRadius", "圆角半径", 6.0, 0.0, 20.0, 1.0);
    private final Value<Color> primaryColorConfig = new ColorValue("PrimaryColor", "主颜色", new Color(255, 183, 197, 255));
    private final Value<Color> backgroundColorConfig = new ColorValue("BackgroundColor", "背景颜色", new Color(20, 20, 20, 200));
    // private final EnumValue<AlignedEnum> aligned = new EnumValue<>("Aligned", "对齐方式", AlignedEnum.RIGHT);
    private final Value<Boolean> backgroundBlur = new BoolValue("BackgroundBlur", "背景模糊", true);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 15.0, 1.0, 30.0, 0.5, backgroundBlur::get);
    private final Value<Boolean> shadow = new BoolValue("Shadow", "阴影", true);
    private final Value<Boolean> progressBar = new BoolValue("ProgressBar", "进度条", true);

    private final Value<Boolean> totemPop = new BoolValue("TotemPop", "图腾弹出", true);
    private final Value<Boolean> selfPop = new BoolValue("SelfPop", "自身弹出", true, totemPop::get);
    private final Value<Boolean> enemyPop = new BoolValue("EnemyPop", "敌人弹出", true, totemPop::get);
    private final Value<Boolean> killNotify = new BoolValue("KillNotify", "击杀提示", true);
    private final Value<Boolean> lowHp = new BoolValue("LowHp", "低血量提示", true);
    private final Value<Color> nameColor = new ColorValue("NameColor", "名字颜色", new Color(255, 100, 100, 255));
    private final Value<Integer> lowHpThreshold = new NumberValue<>("LowHpThreshold", "低血量阈值", 8, 1, 20, 1, lowHp::get);
    private final Value<Boolean> durabilityWarning = new BoolValue("DurabilityWarning", "耐久警告", true);
    private final Value<Integer> durabilityThreshold = new NumberValue<>("DurabilityThreshold", "耐久阈值(%)", 20, 1, 50, 1, durabilityWarning::get);

    private final Map<UUID, Integer> popCounts = new HashMap<>();
    private Entity lastAttackedEntity = null;
    private long lastAttackTime = 0;
    private boolean lowHpTriggered = false;
    private boolean durabilityTriggered = false;

    public NotificationHud() {
        super("Notification", "通知", 10, 10);
    }

    @Override
    public void onEnable() {
        popCounts.clear();
        lastAttackedEntity = null;
        lastAttackTime = 0;
        lowHpTriggered = false;
        durabilityTriggered = false;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        popCounts.clear();
        lastAttackedEntity = null;
        lastAttackTime = 0;
        lowHpTriggered = false;
        durabilityTriggered = false;
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (event.getType() == EventType.RECEIVE) {
            Packet<?> packet = event.getPacket();
            if (packet instanceof EntityStatusS2CPacket statusPacket) {
                if (statusPacket.getStatus() == 35) { // Totem Pop
                    if (totemPop.get()) {
                        Entity entity = statusPacket.getEntity(mc.world);
                        if (entity instanceof PlayerEntity player) {
                            handleTotemPop(player);
                        }
                    }
                } else if (statusPacket.getStatus() == 3) { // Death
                        Entity entity = statusPacket.getEntity(mc.world);
                        if (entity instanceof PlayerEntity) {
                            popCounts.remove(entity.getUuid());
                        }

                        if (killNotify.get()) {
                            if (entity != null && lastAttackedEntity != null && entity.getId() == lastAttackedEntity.getId()) {
                                if (System.currentTimeMillis() - lastAttackTime < 5000) {
                                    NotificationManager.send(getNameColorHex() + entity.getName().getString() + "§r was killed by you.", 3000);
                                    lastAttackedEntity = null;
                                }
                            }
                        }
                    }
            }
        }
    }

    private String getNameColorHex() {
        Color c = nameColor.get();
        return String.format("§#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private void handleTotemPop(PlayerEntity player) {
        boolean isSelf = player == mc.player;

        if (isSelf && !selfPop.get()) return;
        if (!isSelf && !enemyPop.get()) return;

        UUID uuid = player.getUuid();
        int count = popCounts.getOrDefault(uuid, 0) + 1;
        popCounts.put(uuid, count);

        if (isSelf) {
            NotificationManager.send("You popped §c" + count + "§r totem" + (count > 1 ? "s" : "") + "!", 3000);
        } else {
            NotificationManager.send(getNameColorHex() + player.getName().getString() + "§r popped §c" + count + "§r totem" + (count > 1 ? "s" : "") + "!", 3000);
        }
    }

    @EventHandler
    public void onAttack(AttackEvent event) {
        if (killNotify.get()) {
            lastAttackedEntity = event.getTargetEntity();
            lastAttackTime = System.currentTimeMillis();
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        if (lowHp.get()) {
            float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            if (health <= lowHpThreshold.get() && !mc.player.isDead() && !mc.player.isCreative() && !mc.player.isSpectator()) {
                if (!lowHpTriggered) {
                    NotificationManager.send("Low HP Warning! " + String.format("%.1f", health), 3000);
                    lowHpTriggered = true;
                }
            } else if (health > lowHpThreshold.get() + 2) {
                lowHpTriggered = false;
            }
        } else {
            lowHpTriggered = false;
        }

        if (durabilityWarning.get()) {
            boolean anyLow = false;
            for (ItemStack stack : mc.player.getInventory().armor) {
                if (!stack.isEmpty() && stack.isDamageable()) {
                    float damage = stack.getDamage();
                    float maxDamage = stack.getMaxDamage();
                    float durability = (maxDamage - damage) / maxDamage * 100f;
                    if (durability < durabilityThreshold.get()) {
                        anyLow = true;
                        break;
                    }
                }
            }
            if (anyLow) {
                if (!durabilityTriggered) {
                    NotificationManager.send("Your armor is breaking!", 3000);
                    durabilityTriggered = true;
                }
            } else {
                durabilityTriggered = false;
            }
        } else {
            durabilityTriggered = false;
        }
    }

    @Override
    public void onRender(DrawContext context) {
        boolean isLeft = (x + width / 2.0) < (context.getScaledWindowWidth() / 2.0);

        if (Sakura.MODULES.getModule(HudEditor.class).isEnabled()) {
            float[] size = NotificationManager.renderPreview(
                    context.getMatrices(),
                    x, y,
                    isLeft,
                    primaryColorConfig.get(),
                    backgroundColorConfig.get(),
                    maxWidthConfig.get().floatValue(),
                    backgroundBlur.get(),
                    blurStrength.get().floatValue(),
                    roundRadius.get().floatValue(),
                    shadow.get(),
                    progressBar.get()
            );
            width = size[0];
            height = size[1];
        } else {
            NotificationManager.render(
                    context.getMatrices(),
                    x, y,
                    isLeft,
                    primaryColorConfig.get(),
                    backgroundColorConfig.get(),
                    maxWidthConfig.get().floatValue(),
                    backgroundBlur.get(),
                    blurStrength.get().floatValue(),
                    roundRadius.get().floatValue(),
                    shadow.get(),
                    progressBar.get()
            );
        }
    }
}
