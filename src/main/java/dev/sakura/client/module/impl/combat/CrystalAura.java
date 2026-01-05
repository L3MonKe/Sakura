package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.combat.DamageUtil;
import dev.sakura.client.utils.entity.EntityUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.utils.vector.Rotation;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "水晶光环", Category.Combat);
    }

    public enum Page {
        General,
        Timing,
        Place,
        Render
    }

    public enum SwitchMode {
        None,
        Normal,
        Silent,
        InvSilent
    }

    public enum FadeMode {
        Up,
        Down,
        Normal
    }

    private final EnumValue<Page> page = new EnumValue<>("Page", "页面", Page.General);

    private final NumberValue<Double> targetRange = new NumberValue<>("Target Range", "目标范围", 10.0, 1.0, 20.0, 0.1, () -> page.is(Page.General));
    private final NumberValue<Double> minDamage = new NumberValue<>("Min Damage", "最小伤害", 4.0, 0.0, 36.0, 0.1, () -> page.is(Page.General));
    private final NumberValue<Double> maxSelfDamage = new NumberValue<>("Max Self Damage", "最大自伤", 8.0, 0.0, 36.0, 0.1, () -> page.is(Page.General));
    private final NumberValue<Double> forcePlaceHealth = new NumberValue<>("Force Place Health", "强放血量", 8.0, 0.0, 36.0, 0.1, () -> page.is(Page.General));
    private final BoolValue extrapolation = new BoolValue("Extrapolation", "外推", true, () -> page.is(Page.Render));
    private final NumberValue<Integer> extrapolationTicks = new NumberValue<>("Extra Ticks", "外推刻数", 0, 0, 20, 1, () -> page.is(Page.Render) && extrapolation.get());
    private final NumberValue<Integer> smooth = new NumberValue<>("Smooth", "平滑度", 1, 1, 10, 1, () -> page.is(Page.Render) && extrapolation.get());

    private final BoolValue rotate = new BoolValue("Rotate", "旋转", true, () -> page.is(Page.Timing));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, () -> page.is(Page.Timing) && rotate.get());
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Back Speed", "回转速度", 10, 0, 10, 1, () -> page.is(Page.Timing) && rotate.get());
    private final NumberValue<Integer> placeDelay = new NumberValue<>("Place Delay", "放置延迟", 0, 0, 15, 1, () -> page.is(Page.Timing));
    private final NumberValue<Integer> attackDelay = new NumberValue<>("Attack Delay", "攻击延迟", 0, 0, 15, 1, () -> page.is(Page.Timing));
    private final NumberValue<Integer> forcePlaceDelay = new NumberValue<>("Force Place Delay", "强放延迟", 0, 0, 15, 1, () -> page.is(Page.Timing));

    private final BoolValue place = new BoolValue("Place", "放置", true, () -> page.is(Page.Place));
    private final NumberValue<Double> placeRange = new NumberValue<>("Place Range", "放置范围", 5.0, 1.0, 6.0, 0.1, () -> page.is(Page.Place));
    private final BoolValue houyuepingMode = new BoolValue("1.12 Place", "1.12放置", false, () -> page.is(Page.Place));
    private final BoolValue attack = new BoolValue("Attack", "攻击", true, () -> page.is(Page.Place));
    private final NumberValue<Double> breakRange = new NumberValue<>("Break Range", "破坏范围", 5.0, 1.0, 6.0, 0.1, () -> page.is(Page.Place));
    private final EnumValue<SwitchMode> autoSwitch = new EnumValue<>("Switch", "切换", SwitchMode.Normal, () -> page.is(Page.Place));
    private final BoolValue swingHand = new BoolValue("Place Swing", "放置挥手", true, () -> page.is(Page.Place));
    private final BoolValue attackSwing = new BoolValue("Attack Swing", "攻击挥手", true, () -> page.is(Page.Place));

    private final BoolValue render = new BoolValue("Render", "渲染", true, () -> page.is(Page.Render));
    private final BoolValue renderDamageText = new BoolValue("Render Damage", "显示伤害", false, () -> page.is(Page.Render) && render.get());
    private final EnumValue<FadeMode> fadeMode = new EnumValue<>("Fade Mode", "淡出模式", FadeMode.Normal, () -> page.is(Page.Render) && render.get());
    private final NumberValue<Double> animationSpeed = new NumberValue<>("Animation Speed", "动画速度", 5.0, 0.1, 20.0, 0.1, () -> page.is(Page.Render) && render.get());
    private final NumberValue<Double> animationExponent = new NumberValue<>("Animation Exp", "动画指数", 3.0, 0.1, 10.0, 0.1, () -> page.is(Page.Render) && render.get());
    private final BoolValue smoothBox = new BoolValue("Smooth Box", "平滑方块", false, () -> page.is(Page.Render) && render.get());
    private final BoolValue breathing = new BoolValue("Breathing", "呼吸效果", false, () -> page.is(Page.Render) && render.get());
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 192, 203, 50), () -> page.is(Page.Render) && render.get());
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 192, 203, 255), () -> page.is(Page.Render) && render.get());
    private final BoolValue renderExtrapolation = new BoolValue("Render Extrapolation", "渲染预测", false, () -> page.is(Page.Render) && render.get());
    private final ColorValue extraSideColor = new ColorValue("Extra Side Color", "外推侧面颜色", new Color(135, 206, 235, 50), () -> page.is(Page.Render) && renderExtrapolation.get());
    private final ColorValue extraLineColor = new ColorValue("Extra Line Color", "外推线条颜色", new Color(135, 206, 235, 255), () -> page.is(Page.Render) && renderExtrapolation.get());

    private final TimerUtil placeTimer = new TimerUtil();
    private final TimerUtil breakTimer = new TimerUtil();

    private BlockPos renderPos = null;
    private Vec3d currentRenderPos = null;
    private double renderProgress = 0;
    private long lastTime = 0;
    private double renderDamage = 0;
    private boolean isRotating = false;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        renderPos = null;
        currentRenderPos = null;
        renderProgress = 0;
        lastTime = 0;
        renderDamage = 0;
        isRotating = false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.world == null || mc.player == null) return;

        isRotating = false;

        PlayerEntity target = getTarget();

        if (target == null) {
            renderPos = null;
            if (rotate.get()) {
                Managers.ROTATION.setRotations(new Rotation(mc.player.getYaw(), mc.player.getPitch()), rotationBackSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
            }
            return;
        }

        FindItemResult result = autoSwitch.is(SwitchMode.InvSilent) ? InvUtil.find(Items.END_CRYSTAL) : InvUtil.findInHotbar(Items.END_CRYSTAL);

        if (attack.get() && breakTimer.delay(getAttackDelay(target))) {
            doBreak(target, result);
        }

        if (place.get() && placeTimer.delay(placeDelay.get().floatValue())) {
            doPlace(target, result);
        }

        if (!isRotating && rotate.get()) {
            Managers.ROTATION.setRotations(new Rotation(mc.player.getYaw(), mc.player.getPitch()), rotationBackSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!render.get()) return;

        long currentTime = System.currentTimeMillis();
        double delta = (currentTime - lastTime) / 1000.0;
        lastTime = currentTime;

        if (renderPos != null) {
            renderProgress = Math.min(1.0, renderProgress + delta * animationSpeed.get());
            if (currentRenderPos == null || !smoothBox.get()) {
                currentRenderPos = new Vec3d(renderPos.getX(), renderPos.getY(), renderPos.getZ());
            } else {
                currentRenderPos = currentRenderPos.lerp(new Vec3d(renderPos.getX(), renderPos.getY(), renderPos.getZ()), delta * animationSpeed.get());
            }
        } else {
            renderProgress = Math.max(0.0, renderProgress - delta * animationSpeed.get());
        }

        if (renderProgress > 0.001 && currentRenderPos != null) {
            double r = 0.5 - Math.pow(1.0 - renderProgress, animationExponent.get()) / 2.0;

            double down = 0;
            double up = 0;
            double width = 0.5;

            switch (fadeMode.get()) {
                case Up -> {
                    up = 1.0;
                    down = 1.0 - (r * 2);
                }
                case Down -> {
                    up = r * 2;
                    down = 0;
                }
                case Normal -> {
                    up = 0.5 + r;
                    down = 0.5 - r;
                    width = r;
                }
            }

            Color sColor = sideColor.get();
            Color lColor = lineColor.get();

            if (breathing.get()) {
                float breatheFactor = (float) (Math.sin(System.currentTimeMillis() / 1000.0 * 2.0) + 1.0) / 2.0f;
                float alphaFactor = 0.4f + (breatheFactor * 0.6f);
                float scale = 1.0f - (breatheFactor * 0.15f);

                sColor = new Color(sColor.getRed(), sColor.getGreen(), sColor.getBlue(), Math.max(0, Math.min(255, (int) (sColor.getAlpha() * alphaFactor))));
                lColor = new Color(lColor.getRed(), lColor.getGreen(), lColor.getBlue(), Math.max(0, Math.min(255, (int) (lColor.getAlpha() * alphaFactor))));

                width *= scale;
                double height = up - down;
                double newHeight = height * scale;
                double heightDiff = height - newHeight;

                up -= heightDiff / 2.0;
                down += heightDiff / 2.0;
            }

            Box box = new Box(
                    currentRenderPos.getX() + 0.5 - width, currentRenderPos.getY() + down, currentRenderPos.getZ() + 0.5 - width,
                    currentRenderPos.getX() + 0.5 + width, currentRenderPos.getY() + up, currentRenderPos.getZ() + 0.5 + width
            );

            Render3DUtil.drawFullBox(event.getMatrices(), box, sColor, lColor);

            if (renderDamageText.get()) {
                Vec3d center = new Vec3d(
                        box.minX + (box.maxX - box.minX) * 0.5,
                        box.minY + (box.maxY - box.minY) * 0.5,
                        box.minZ + (box.maxZ - box.minZ) * 0.5
                );
                Render3DUtil.drawText(String.format("%.1f", renderDamage), center, 0, 0, 0, Color.WHITE);
            }
        }

        if (renderExtrapolation.get()) {
            PlayerEntity target = getTarget();
            if (target != null) {
                Box predicted = Managers.EXTRAPOLATION.extrapolate((AbstractClientPlayerEntity) target, extrapolationTicks.get(), smooth.get());
                if (predicted != null) {
                    Render3DUtil.drawFullBox(event.getMatrices(), predicted, extraSideColor.get(), extraLineColor.get());
                }
            }
        }
    }

    private void doBreak(PlayerEntity target, FindItemResult result) {
        EndCrystalEntity bestCrystal = null;
        float bestDamage = 0;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndCrystalEntity crystal)) continue;
            if (mc.player.distanceTo(crystal) > breakRange.get()) continue;
            if (!crystal.isAlive()) continue;

            float targetDamage = calculateDamage(target, crystal.getPos());
            float selfDamage = calculateDamage(mc.player, crystal.getPos());

            if (selfDamage > maxSelfDamage.get()) continue;
            if (targetDamage < getMinDamage(target)) continue;

            if (targetDamage > bestDamage) {
                bestDamage = targetDamage;
                bestCrystal = crystal;
            }
        }

        if (bestCrystal != null) {
            if (rotate.get()) {
                Rotation rotation = RotationUtil.calculate(bestCrystal.getPos());
                Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
                isRotating = true;
            }

            mc.interactionManager.attackEntity(mc.player, bestCrystal);

            Hand hand = result == null || result.getHand() == null ? Hand.MAIN_HAND : result.getHand();
            if (attackSwing.get()) mc.player.swingHand(hand);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));

            breakTimer.reset();
        }
    }

    private void doPlace(PlayerEntity target, FindItemResult result) {
        BlockPos bestPos = null;
        float bestDamage = 0;
        EndCrystalEntity blockingCrystal = null;

        for (BlockPos pos : getSphere(placeRange.get())) {
            if (!isValidBaseBlock(pos)) continue;

            EndCrystalEntity crystal = getCrystalAt(pos);
            if (crystal == null) {
                if (EntityUtil.intersectsWithEntity(new Box(pos.up()).stretch(0, 1, 0), entity -> true)) continue;
            } else {
                if (EntityUtil.intersectsWithEntity(new Box(pos.up()).stretch(0, 1, 0), entity -> !entity.equals(crystal)))
                    continue;
            }

            Vec3d crystalPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            float damage = calculateDamage(target, crystalPos);
            float selfDamage = calculateDamage(mc.player, crystalPos);

            if (selfDamage > maxSelfDamage.get()) continue;
            if (damage < getMinDamage(target)) continue;

            if (damage > bestDamage) {
                bestDamage = damage;
                bestPos = pos;
                blockingCrystal = crystal;
            }
        }

        if (bestPos != null) {
            if (blockingCrystal != null) {
                if (breakTimer.delay(getAttackDelay(target))) {
                    if (rotate.get()) {
                        Rotation rotation = RotationUtil.calculate(blockingCrystal.getPos());
                        Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
                        isRotating = true;
                    }

                    mc.interactionManager.attackEntity(mc.player, blockingCrystal);

                    Hand hand = result == null || result.getHand() == null ? Hand.MAIN_HAND : result.getHand();
                    if (attackSwing.get()) mc.player.swingHand(hand);
                    else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));

                    breakTimer.reset();
                }
                return;
            }

            if (!result.found()) return;

            int slot = result.slot();
            boolean switched = false;

            if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && !autoSwitch.is(SwitchMode.None)) {
                switch (autoSwitch.get()) {
                    case Silent -> switched = InvUtil.swap(slot, true);
                    case InvSilent -> switched = InvUtil.invSwap(slot);
                    case Normal -> InvUtil.swap(slot, false);
                }
            }

            if (rotate.get()) {
                Rotation rotation = RotationUtil.calculate(bestPos.toCenterPos().add(0, 0.5, 0));
                Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
                isRotating = true;
            }

            BlockHitResult hitResult = new BlockHitResult(bestPos.toCenterPos().add(0, 1, 0), Direction.UP, bestPos, false);
            Hand hand = result.getHand();
            ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, hand, hitResult);

            if (actionResult.isAccepted()) {
                if (swingHand.get()) mc.player.swingHand(hand, true);
                else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
            }

            renderPos = bestPos;
            renderDamage = bestDamage;
            placeTimer.reset();

            if (switched) {
                switch (autoSwitch.get()) {
                    case Silent -> InvUtil.swapBack();
                    case InvSilent -> InvUtil.invSwapBack();
                }
            }
        }
    }

    private boolean isValidBaseBlock(BlockPos pos) {
        if (mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
            return false;
        }

        BlockPos up = pos.up();
        if (!mc.world.isAir(up)) return false;

        if (houyuepingMode.get()) {
            return mc.world.isAir(up.up());
        }
        return true;
    }

    private EndCrystalEntity getCrystalAt(BlockPos pos) {
        BlockPos up = pos.up();
        Box box = new Box(up);
        List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(EndCrystalEntity.class, box, Entity::isAlive);
        return crystals.isEmpty() ? null : crystals.getFirst();
    }

    public PlayerEntity getCurrentTarget() {
        return getTarget();
    }

    private PlayerEntity getTarget() {
        PlayerEntity target = null;
        double distance = targetRange.get();

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (mc.player.distanceTo(player) > distance) continue;

            target = player;
            distance = mc.player.distanceTo(player);
        }
        return target;
    }

    private float calculateDamage(PlayerEntity entity, Vec3d crystalPos) {
        if (extrapolation.get() && entity != mc.player) {
            Box predicted = Managers.EXTRAPOLATION.extrapolate((AbstractClientPlayerEntity) entity, extrapolationTicks.get(), smooth.get());
            if (predicted != null) {
                Box oldBox = entity.getBoundingBox();
                Vec3d oldPos = entity.getPos();

                entity.setBoundingBox(predicted);
                entity.setPos(predicted.getCenter().x, predicted.minY, predicted.getCenter().z);

                float damage = DamageUtil.calculateCrystalDamage(entity, crystalPos);

                entity.setBoundingBox(oldBox);
                entity.setPos(oldPos.x, oldPos.y, oldPos.z);

                return damage;
            }
        }
        return DamageUtil.calculateCrystalDamage(entity, crystalPos);
    }

    private float getAttackDelay(PlayerEntity target) {
        if (target != null && (target.getHealth() + target.getAbsorptionAmount() <= forcePlaceHealth.get())) {
            return forcePlaceDelay.get().floatValue();
        }
        return attackDelay.get().floatValue();
    }

    private double getMinDamage(PlayerEntity target) {
        if (target.getHealth() + target.getAbsorptionAmount() <= forcePlaceHealth.get()) {
            return 1.0;
        }
        return minDamage.get();
    }

    private List<BlockPos> getSphere(double range) {
        List<BlockPos> list = new ArrayList<>();
        BlockPos center = mc.player.getBlockPos();
        int r = (int) Math.ceil(range);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = center.add(x, y, z);
                    if (mc.player.squaredDistanceTo(pos.toCenterPos()) <= range * range) {
                        list.add(pos);
                    }
                }
            }
        }
        return list;
    }
}
