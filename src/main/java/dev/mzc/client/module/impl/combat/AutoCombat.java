package dev.mzc.client.module.impl.combat;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.Friend;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Random;

public class AutoCombat extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    /* ================= 模式 ================= */

    public enum Mode {
        HighVersion("高版本"),   // 1.9+
        LowVersion("低版本")   // 1.8
        ;

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    public final EnumValue<Mode> mode =
            new EnumValue<>("Mode", "攻击模式", Mode.HighVersion);

    // High Version Settings
    private final NumberValue<Integer> attackDelay = new NumberValue<>("Delay", "攻击延迟", 0, 0, 5, 1, () -> mode.get() == Mode.HighVersion);

    // Low Version Settings
    private final NumberValue<Double> minCPS = new NumberValue<>("MinCPS", "最小CPS", 8.0, 1.0, 20.0, 0.5, () -> mode.get() == Mode.LowVersion);
    private final NumberValue<Double> maxCPS = new NumberValue<>("MaxCPS", "最大CPS", 12.0, 1.0, 20.0, 0.5, () -> mode.get() == Mode.LowVersion);
    private final BoolValue smartAttackRange = new BoolValue("SmartRange", "智能距离", true, () -> mode.get() == Mode.LowVersion);
    private final NumberValue<Double> attackRange = new NumberValue<>("Range", "攻击距离", 3.0, 1.0, 6.0, 0.1, () -> mode.get() == Mode.LowVersion && !smartAttackRange.get());

    /* ================= 通用设置 ================= */

    private final BoolValue teamCheck = new BoolValue("TeamCheck", "队伍检测", true);
    private final BoolValue antiBot = new BoolValue("AntiBot", "防假人", true);
    private final BoolValue usingPause = new BoolValue("UsingPause", "使用物品暂停", true);
    private final BoolValue requireLeftButton = new BoolValue("RequireLeftButton", "按住左键", true);
    private final BoolValue CriticalFix = new BoolValue("CriticalFix", "暴击检测修复", true);
    private final BoolValue autoShieldBreak = new BoolValue("ShieldBreaker1.9+", "自动破盾", true,
            () -> mode.get()== Mode.HighVersion);

    private int attackCooldown = 0;
    private int originalSlot = -1;
    private boolean needSwapBack = false;

    private double clickDelay = 0;

    public AutoCombat() {
        super("AutoCombat", "自动战斗", Category.Combat);
        this.setType(ModuleType.Safe);
    }

    @Override
    public void onEnable() {
        attackCooldown = 0;
        clickDelay = 0;
        needSwapBack = false;
    }

    @Override
    public void onDisable() {
        if (needSwapBack && mc.player != null && originalSlot != -1) {
            mc.player.getInventory().setSelectedSlot(originalSlot);
        }
        needSwapBack = false;
    }

    /* ================= Tick ================= */

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.interactionManager == null) return;
        switch (mode.get()) {
            case HighVersion -> handleModern();
            case LowVersion -> handleLegacy();
        }
    }

    /* ================= 1.9+ 攻击 ================= */
    
    private int currentDelay = 0;

    private void handleModern() {
        PlayerEntity p = mc.player;

        if (attackCooldown > 0) {
            attackCooldown--;
            if (needSwapBack && attackCooldown == 0) {
                p.getInventory().setSelectedSlot(originalSlot);
                needSwapBack = false;
            }
            return;
        }

        if (requireLeftButton.get() && !mc.options.attackKey.isPressed()) {
            currentDelay = 0;
            return;
        }
        if (p.getAttackCooldownProgress(0) < 1.0f) return;
        if (usingPause.get() && p.isUsingItem()) return;

        // 仅在暴击开关启用且玩家正在下落时进行攻击
        if (CriticalFix.get() && p.getVelocity().y > 0) {
            return; // 玩家处于上升状态或地面上，不能攻击
        }

        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult ehr)) {
            currentDelay = 0;
            return;
        }

        Entity target = ehr.getEntity();
        if (!isValidTarget(target, p)) {
            currentDelay = 0;
            return;
        }

        // Delay logic
        if (currentDelay < attackDelay.get()) {
            currentDelay++;
            return;
        }
        currentDelay = 0;

        // 自动破盾
        if (autoShieldBreak.get()
                && target instanceof PlayerEntity tp
                && tp.isBlocking()) {

            int before = p.getInventory().selectedSlot;
            if (switchToAxe()) {
                originalSlot = before;
                needSwapBack = true;
            }
        }

        mc.interactionManager.attackEntity(p, target);
        p.swingHand(Hand.MAIN_HAND);

        attackCooldown = 10;
    }

    /* ================= 1.8 连点 ================= */

    private void handleLegacy() {
        PlayerEntity p = mc.player;

        if (requireLeftButton.get() && !mc.options.attackKey.isPressed()) {
            clickDelay = 0;
            return;
        }

        clickDelay -= 1.0;

        if (clickDelay > 0) {
            return;
        }

        if (usingPause.get() && p.isUsingItem()) {
            if (clickDelay < 0) clickDelay = 0;
            return;
        }

        // 仅在暴击开关启用且玩家正在下落时进行攻击
        if (CriticalFix.get() && p.getVelocity().y > 0) {
            if (clickDelay < 0) clickDelay = 0;
            return; // 玩家处于地面上，不允许暴击攻击
        }

        // Target acquisition
        Entity target = null;
        
        if (smartAttackRange.get()) {
            // Smart Mode: Use default crosshair target
            if (mc.crosshairTarget instanceof EntityHitResult ehr) {
                target = ehr.getEntity();
            }
        } else {
            // Manual Mode: Check entities within custom range
            // We use a simple raycast or just iterate entities to find one in look direction
            // Since AutoCombat is usually a "TriggerBot" style, we should probably raycast manually
            // if we want to support hitting things that are technically out of vanilla reach but within our custom range
            // (assuming Reach module is handling the packet side, or we just want to restrict vanilla reach).
            
            // However, usually "attackRange" in TriggerBot means "don't attack if > range".
            // If range > vanilla, we can't hit it without Reach module anyway.
            // But if range < vanilla, we should restrict it.
            
            // Let's support "TriggerBot within range".
            // We check the crosshair target first.
            if (mc.crosshairTarget instanceof EntityHitResult ehr) {
                Entity e = ehr.getEntity();
                if (p.squaredDistanceTo(e) <= attackRange.get() * attackRange.get()) {
                    target = e;
                }
            } else {
                // If we want to support "Reach" behavior integrated here (hitting things further away):
                // We would need to raycast. But typically Reach is a separate module.
                // If the user wants to hit things further, they enable Reach.
                // If they want to RESTRICT distance (e.g. only hit if < 2 blocks), this works.
                
                // If the user wants to hit things that ARE in range but maybe not strictly under crosshair (aim assist?),
                // that's AimAssist.
                
                // So, assuming this is just a distance check on the current target:
            }
        }

        if (target == null) {
             // No valid target in range
            if (clickDelay < 0) clickDelay = 0;
            return;
        }

        if (!isValidTarget(target, p)) {
            if (clickDelay < 0) clickDelay = 0;
            return;
        }

        // Perform attack
        mc.interactionManager.attackEntity(p, target);
        p.swingHand(Hand.MAIN_HAND);

        // Calculate next delay
        double min = minCPS.get();
        double max = maxCPS.get();
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }

        double cps = min + (max - min) * random.nextDouble();
        clickDelay = 20.0 / cps;
    }

    /* ================= 工具方法 ================= */

    private boolean switchToAxe() {
        PlayerEntity p = mc.player;
        PlayerInventory inv = p.getInventory();

        if (inv.getStack(inv.selectedSlot).getItem() instanceof AxeItem) return false;

        for (int i = 0; i < 9; i++) {
            if (inv.getStack(i).getItem() instanceof AxeItem) {
                inv.setSelectedSlot(i);
                return true;
            }
        }
        return false;
    }

    private boolean isValidTarget(Entity target, PlayerEntity self) {
        if (target == self) return false;
        if (!target.isAlive()) return false;
        if (Sakura.MODULES.getModule(Friend.class).isFriend(target.getName().getString())) return false;
        if (antiBot.get() && AntiBot.isBot(target)) return false;
        if (teamCheck.get() && !isEnemy(target)) return false;
        return target instanceof PlayerEntity || target instanceof Monster;
    }

    private boolean isEnemy(Entity entity) {
        if (!teamCheck.get()) return true;
        if (!(entity instanceof PlayerEntity player)) return true;
        if (mc.player == null) return false;

        int myColor = getLeatherArmorColor(mc.player);
        int theirColor = getLeatherArmorColor(player);

        if (myColor == -1 || theirColor == -1) return true;
        return myColor != theirColor;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
        for (ItemStack stack : player.getArmorItems()) {
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) return dyed.rgb();
        }
        return -1;
    }
}
