package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.combat.CrystalUtil;
import dev.mahiro.client.utils.entity.EntityUtil;
import dev.mahiro.client.utils.player.EatingUtil;
import dev.mahiro.client.utils.player.FindItemResult;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RaytraceUtil;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.utils.world.BlockUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class AutoCrystal extends Module {
    private final NumberValue<Double> targetRange = new NumberValue<>("Target Range", "目标距离", 10.0, 1.0, 20.0, 0.5);
    private final NumberValue<Double> placeRange = new NumberValue<>("Place Range", "放置距离", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> breakRange = new NumberValue<>("Break Range", "破坏距离", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> wallRange = new NumberValue<>("Wall Range", "墙距离", 3.0, 0.0, 6.0, 0.1);
    private final NumberValue<Double> minDamage = new NumberValue<>("Min Damage", "最小伤害", 4.0, 1.0, 20.0, 0.5);
    private final NumberValue<Double> maxSelfDamage = new NumberValue<>("Max Self Damage", "最大自我伤害", 8.0, 1.0, 20.0, 0.5);
    private final NumberValue<Double> facePlaceHealth = new NumberValue<>("Face Place Health", "脸放置伤害", 8.0, 0.0, 36.0, 0.5);
    private final NumberValue<Double> minCPS = new NumberValue<>("Min CPS", "最小CPS", 8.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> maxCPS = new NumberValue<>("Max CPS", "最大CPS", 12.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotate Speed", "转头速度", 1.5, 0.1, 5.0, 0.1);
    private final NumberValue<Double> angleTolerance = new NumberValue<>("Angle Tolerance", "Angle Tolerance", 20.0, 1.0, 90.0, 1.0);
    private final BoolValue autoSwitch = new BoolValue("Auto Switch", "自动切换", true);

    private Entity target;
    private long lastBreakTime;
    private long lastPlaceTime;
    private long breakDelay;
    private long placeDelay;

    public AutoCrystal() {
        super("AutoCrystal", "自动水晶", Category.Combat);
    }

    @Override
    public void onEnable() {
        target = null;
        resetBreakTimer();
        resetPlaceTimer();
    }

    private void resetBreakTimer() {
        lastBreakTime = System.currentTimeMillis();
        breakDelay = (long) (1000.0 / (minCPS.get().doubleValue() + Math.random() * (maxCPS.get().doubleValue() - minCPS.get().doubleValue())));
    }

    private void resetPlaceTimer() {
        lastPlaceTime = System.currentTimeMillis();
        placeDelay = (long) (1000.0 / (minCPS.get().doubleValue() + Math.random() * (maxCPS.get().doubleValue() - minCPS.get().doubleValue())));
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 如果正在吃东西/喝药/使用物品，暂停一切动作
        if (EatingUtil.isEating()) return;

        target = EntityUtil.getClosestPlayer(targetRange.get().doubleValue());
        if (target == null) return;

        // 2. 攻击水晶 (Break Crystal)
        if (handleCrystalBreak()) return;

        // 3. 放置水晶 (Place Crystal)
        handleCrystalPlace();
    }

    private boolean handleCrystalBreak() {
        // 查找范围内最近的水晶
        EndCrystalEntity bestCrystal = null;
        double bestDistSq = Double.MAX_VALUE;
        double rangeSq = breakRange.get().doubleValue() * breakRange.get().doubleValue();

        Box box = new Box(mc.player.getPos(), mc.player.getPos()).expand(breakRange.get().doubleValue());
        List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(EndCrystalEntity.class, box, e -> true);

        for (EndCrystalEntity crystal : crystals) {
            if (!crystal.isAlive()) continue;
            double distSq = mc.player.squaredDistanceTo(crystal);
            if (distSq > rangeSq) continue;

            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                bestCrystal = crystal;
            }
        }

        if (bestCrystal != null) {
            // 隔墙检测
            boolean canSee = RaytraceUtil.canSeePointFrom(mc.player.getEyePos(), bestCrystal.getPos());
            if (!canSee && mc.player.squaredDistanceTo(bestCrystal) > wallRange.get().doubleValue() * wallRange.get().doubleValue()) {
                return false;
            }

            // 攻击水晶
            Rotation rot = RotationUtil.calculate(bestCrystal);
            Managers.ROTATION.setRotations(rot, rotateSpeed.get().doubleValue(), MovementFix.OFF, RotationManager.Priority.Highest);

            if (System.currentTimeMillis() - lastBreakTime >= breakDelay) {
                // Attacking doesn't require specific item, but we should use executeAction for consistency if we wanted to switch
                // However, crystals can be broken with anything. Usually weak attacks are fine.
                // But some servers require a weapon. Let's assume we attack with whatever we have.
                // Or we can switch to a sword if we want. For now, just attack.
                // To be safe against ghost hand if we were switching, we would use executeAction.
                // Since we aren't switching, direct attack is fine.
                // Wait, if we are mid-switch from other modules?
                // AutoCrystal usually runs tick by tick.

                // Let's just attack directly.
                mc.interactionManager.attackEntity(mc.player, bestCrystal);
                mc.player.swingHand(Hand.MAIN_HAND);
                resetBreakTimer();
            }
            return true; // 锁定目标
        }
        return false;
    }

    private void handleCrystalPlace() {
        FindItemResult crystal = InvUtil.findInHotbar(Items.END_CRYSTAL);
        if (!crystal.found()) return;

        // 寻找最佳放置点 (寻找目标周围最近的可放置点)
        BlockPos bestPos = null;
        float bestDamage = 0;
        double rangeSq = placeRange.get().doubleValue() * placeRange.get().doubleValue();

        BlockPos pPos = mc.player.getBlockPos();
        int r = (int) Math.ceil(placeRange.get().doubleValue());

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = pPos.add(x, y, z);
                    Vec3d posVec = Vec3d.ofCenter(pos);
                    double distSq = mc.player.squaredDistanceTo(posVec);
                    if (distSq > rangeSq) continue;

                    // 墙体检测
                    boolean canSee = RaytraceUtil.canSeePointFrom(mc.player.getEyePos(), posVec);
                    if (!canSee && distSq > wallRange.get().doubleValue() * wallRange.get().doubleValue()) {
                        continue;
                    }

                    if (canPlaceCrystal(pos)) {
                        float damage = CrystalUtil.calculateDamage(pos, target);
                        float minDmg = minDamage.get().floatValue();

                        if (target instanceof PlayerEntity player) {
                            if (player.getHealth() + player.getAbsorptionAmount() <= facePlaceHealth.get().floatValue()) {
                                minDmg = 2.0f;
                            }
                        }

                        if (damage < minDmg) continue;

                        float selfDamage = CrystalUtil.calculateDamage(pos, mc.player);
                        if (selfDamage > maxSelfDamage.get().doubleValue()) continue;

                        if (damage > bestDamage) {
                            bestDamage = damage;
                            bestPos = pos;
                        }
                    }
                }
            }
        }

        if (bestPos != null) {
            // FindItemResult crystal = InvUtil.findInHotbar(Items.END_CRYSTAL); // Already found above

            BlockPos finalBestPos = bestPos;
            Rotation rot = RotationUtil.calculate(finalBestPos.up(), Direction.UP);
            Managers.ROTATION.setRotations(rot, rotateSpeed.get().doubleValue(), MovementFix.OFF, RotationManager.Priority.High);

            if (isFacing(rot) && System.currentTimeMillis() - lastPlaceTime >= placeDelay) {
                executeAction(() -> {
                    BlockUtil.clickBlock(finalBestPos, Direction.UP, false, false);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    resetPlaceTimer();
                }, crystal.slot(), autoSwitch.get());
            }
        }
    }

    // Helper to execute action with safe switching
    private boolean executeAction(Runnable action, int slot, boolean autoSwitch) {
        int oldSlot = mc.player.getInventory().selectedSlot;
        if (autoSwitch) InvUtil.swap(slot, false);
        else if (mc.player.getInventory().selectedSlot != slot) return false;

        action.run();

        if (autoSwitch) InvUtil.swap(oldSlot, false);
        return true;
    }

    private boolean isFacing(Rotation targetRot) {
        Rotation current = Managers.ROTATION.rotations;
        float yawDiff = Math.abs(net.minecraft.util.math.MathHelper.wrapDegrees(current.yaw - targetRot.yaw));
        float pitchDiff = Math.abs(net.minecraft.util.math.MathHelper.wrapDegrees(current.pitch - targetRot.pitch));
        return yawDiff <= angleTolerance.get().doubleValue() && pitchDiff <= angleTolerance.get().doubleValue();
    }

    private boolean canPlaceCrystal(BlockPos pos) {
        // 检查基座是黑曜石或基岩
        if (mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN &&
                mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) return false;

        // 检查上方空间
        BlockPos up1 = pos.up();
        BlockPos up2 = up1.up();

        if (!mc.world.isAir(up1)) return false;
        // 1.12.2+ 不需要检查第二格空气，但在某些服务器可能需要，这里暂时只检查一格，或者检查实体

        Box box = new Box(up1);
        return !EntityUtil.intersectsWithEntity(box, entity -> true);
    }
}
