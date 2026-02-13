/*
package dev.sakura.client.module.impl.combat;

import com.google.common.eventbus.Subscribe;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.world.DamageUtils;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ZealotCrystal extends Module {

    private final NumberValue<Double> minPlaceDmg = new NumberValue<>("MinPlaceDmg", "最小放置伤害", 6.0, 0.0, 24.0, 0.1);
    private final NumberValue<Double> minBreakDmg = new NumberValue<>("MinBreakDmg", "最小敲击伤害", 4.0, 0.0, 24.0, 0.1);
    private final NumberValue<Double> maxSelfDmg = new NumberValue<>("MaxSelfDmg", "对自己最大伤害", 8.0, 0.0, 36.0, 0.1);
    private final BoolValue lethalDamage = new BoolValue("LethalDamage", "低血量斩杀", true);
    private final BoolValue antiTotem = new BoolValue("AntiTotem", "连续致命放置", true);
    private final NumberValue<Double> lethalMultiplier = new NumberValue<>("LethalMultiplier", "低血量斩杀值", 2.0, -5.0, 6.0, 0.1);
    private final BoolValue facePlace = new BoolValue("FacePlace", "炸脸", true);
    private final NumberValue<Double> facePlaceHealth = new NumberValue<>("FacePlaceHealth", "多少血量启动炸脸", 8.0, 1.0, 36.0, 0.1);

    private final BoolValue strict = new BoolValue("Strict", "严格模式", true);
    private final BoolValue explode = new BoolValue("Explode", "敲击", true);
    private final BoolValue place = new BoolValue("Place", "放置", true);
    private final BoolValue eatingPause = new BoolValue("Eating Pause", "吃苹果暂停", true);
    private final BoolValue instantBreak = new BoolValue("Instant Break", "快速敲击", false);
    private final BoolValue packetBreak = new BoolValue("Packet Break", "发包敲击", false);
    private final BoolValue antiSuicide = new BoolValue("Anti Suicide", "防自杀", true);
    private final BoolValue antiWeakness = new BoolValue("Anti Weakness", "防止虚弱", true);

    private final NumberValue<Integer> updateDelay = new NumberValue<>("Update Delay", "更新延迟", 50, 0, 1000, 1);

    private final NumberValue<Double> placeRange = new NumberValue<>("Place Range", "放置范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Integer> placeMinDelay = new NumberValue<>("PlaceMin Delay", "放置最小延迟(ms)", 100, 0, 1000, 10);
    private final NumberValue<Integer> placeMaxDelay = new NumberValue<>("PlaceMax Delay", "放置最大延迟(ms)", 300, 0, 1000, 10);

    private final NumberValue<Double> breakRange = new NumberValue<>("BreakRange", "敲击范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Integer> breakMinDelay = new NumberValue<>("BreakMin Delay", "敲击最小延迟(ms)", 100, 0, 1000, 10);
    private final NumberValue<Integer> breakMaxDelay = new NumberValue<>("BreakMax Delay", "敲击最大延迟(ms)", 300, 0, 1000, 10);


    private final NumberValue<Double> targetRange = new NumberValue<>("Target Range", "目标范围", 10.0, 1.0, 20.0, 0.5);
    private final NumberValue<Double> wallRange = new NumberValue<>("Wall Range", "穿墙范围", 3.0, 0.0, 6.0, 0.1);

    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotate Speed", "旋转速度", 1.5, 0.1, 5.0, 0.1);
    private final NumberValue<Double> angleTolerance = new NumberValue<>("Angle Tolerance", "角度容差", 20.0, 1.0, 90.0, 1.0);

    private final BoolValue predict = new BoolValue("Predict", "预判", true);
    private final BoolValue highJump = new BoolValue("High Jump", "更夸张的跳跃预判", true);
    private final BoolValue jumpPredict = new BoolValue("Jump Predict", "跳跃预判", true);
    private final NumberValue<Double> predictTicks = new NumberValue<>("Predict Tick", "预判游戏刻", 5.0, 1.0, 6.0, 0.1);

    private final BoolValue antiSurround = new BoolValue("Anti Surround", "卡脚放置水晶", true);


    private long lastPlaceTime = 0;
    private long lastBreakTime = 0;
    private int originalSlot = -1;
    private boolean isSwitched = false;

    private PlayerEntity currentTarget = null;
    private EndCrystalEntity crystalToBreak = null;
    private BlockPos bestPlacePos = null;
    private Vec3d predictedPos = null;

    private boolean shouldRotate = false;
    public ZealotCrystal() {
        super("ZealotCrystal", "Zc", Category.Combat);
    }


    @Override
    public void onEnable() {
        lastPlaceTime = 0;
        lastBreakTime = 0;
        currentTarget = null;
        crystalToBreak = null;
        bestPlacePos = null;
        shouldRotate = false;
        predictedPos = null;
        isSwitched = false;
        originalSlot = -1;
    }

    @Override
    public void onDisable() {
        shouldRotate = false;
        if (isSwitched && originalSlot != -1) {
            InventoryUtils.switchToSlot(originalSlot);
            isSwitched = false;
            originalSlot = -1;
        }
    }


    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (eatingPause.get() && isEating()) {
            return;
        }

        if (mc.currentScreen instanceof GenericContainerScreen) {
            mc.player.closeHandledScreen();
        }


        boolean hasCrystalInHand = mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL ||
                mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL;

        if (!hasCrystalInHand && !slient.getValue()) {
            currentTarget = null;
            bestPlacePos = null;
            return;
        }

        updateTarget();
        if (currentTarget == null) {
            crystalToBreak = null;
            return;
        }

        if (predict.get()) {
            predictedPos = predictPlayerPosition(currentTarget, predictTicks.get(), highJump.get());
        } else {
            predictedPos = currentTarget.getEntityPos();
        }

        if (explode.get() && System.currentTimeMillis() - lastBreakTime >= breakDelay.getValue().longValue()) {
            findCrystalToBreak();
            if (crystalToBreak != null) {
                performBreak();
                lastBreakTime = System.currentTimeMillis();
            }
        }

        if (instantBreak.get()) {
            breakNearestCrystal();
        }

        if (place.get() && System.currentTimeMillis() - lastPlaceTime >= placeDelay.getValue().longValue()) {
            findBestPlacePosition();
            if (bestPlacePos != null) {
                performPlace();
                lastPlaceTime = System.currentTimeMillis();
            }
        }

        if (isSwitched && originalSlot != -1 && mc.player.getInventory().getSelectedSlot() != originalSlot) {
            InventoryUtils.switchToSlot(originalSlot);
            isSwitched = false;
            originalSlot = -1;
        }
    }

    private boolean isEating() {
        if (mc.player.isUsingItem()) {
            ItemStack mainHandStack = mc.player.getMainHandStack();
            ItemStack offHandStack = mc.player.getOffHandStack();

            return hasConsumableComponent(mainHandStack) || hasConsumableComponent(offHandStack);
        }
        return false;
    }


    private boolean hasConsumableComponent(ItemStack stack) {
        if (stack.isEmpty()) return false;

        return stack.contains(DataComponentTypes.CONSUMABLE);
    }

    private void breakNearestCrystal() {
        EndCrystalEntity nearestCrystal = null;
        double minDistance = Double.MAX_VALUE;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndCrystalEntity crystal) || !crystal.isAlive()) {
                continue;
            }

            double distance = mc.player.distanceTo(crystal);
            if (distance < minDistance && distance <= breakRange.get()) {
                minDistance = distance;
                nearestCrystal = crystal;
            }
        }

        if (nearestCrystal != null) {
            if (rotate.getValue()) {
                Vec3d crystalVec = nearestCrystal.getPos();
                OyVey.rotationManager.rotate(RotationUtils.getRotations(crystalVec), OyVey.rotationManager.getModulePriority(this));
            }

            if (packetBreak.get()) {
                mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(nearestCrystal, mc.player.isSneaking()));
            } else {
                mc.interactionManager.attackEntity(mc.player, nearestCrystal);
            }

            if (breakSwing.getValue() != SwingMode.NONE) {
                mc.player.swingHand(breakSwing.getValue() == SwingMode.MAINHAND ? Hand.MAIN_HAND : Hand.OFF_HAND);
            }
        }
    }

    private void updateTarget() {
        List<PlayerEntity> enemies = mc.world.getPlayers().stream()
                .filter(player -> player != mc.player &&
                        player.isAlive() &&
                        mc.player.distanceTo(player) <= targetRange.get().floatValue() &&
                        !OyVey.friendManager.isFriend(player.getName().getString())
                )
                .sorted(Comparator.comparingDouble(player -> mc.player.distanceTo(player)))
                .collect(Collectors.toList());

        currentTarget = enemies.isEmpty() ? null : enemies.get(0);
    }

    private void findCrystalToBreak() {
        crystalToBreak = null;
        if (currentTarget == null) return;

        double maxDamage = 0.0;
        boolean foundLethal = false;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndCrystalEntity crystal) || !crystal.isAlive()) {
                continue;
            }

            if (mc.player.distanceTo(crystal) > breakRange.get().floatValue()) {
                continue;
            }

            boolean canSee = mc.player.canSee(crystal) || mc.player.getEyePos().distanceTo(crystal.getEntityPos()) <= wallRange.get();
            if (!canSee && mc.player.distanceTo(crystal) > wallRange.get()) {
                continue;
            }

            double targetDamage = DamageUtils.getCrystalDamage(currentTarget, predictedPos, crystal.getEntityPos(), false);
            double selfDamage = DamageUtils.getCrystalDamage(mc.player, mc.player.getBoundingBox(), crystal, false);


            if (selfDamage + (antiSuicide.get() ? 2.0 : 0.5) >= mc.player.getHealth() + mc.player.getAbsorptionAmount()) {
                continue;
            }


            boolean hasTotem = hasTotem(currentTarget);
            boolean isLethalDamage = targetDamage >= currentTarget.getHealth() + currentTarget.getAbsorptionAmount();
            boolean isAntiTotem = antiTotem.get() && hasTotem && targetDamage > 8.0;

            if ((isLethalDamage || isAntiTotem) && lethalDamage.get()) {

                if (selfDamage < mc.player.getHealth() + mc.player.getAbsorptionAmount() - 1.0) {
                    crystalToBreak = crystal;
                    foundLethal = true;
                    break;
                }
            }


            if (targetDamage < minBreakDmg.get() && !foundLethal &&
                    targetDamage < currentTarget.getHealth() + currentTarget.getAbsorptionAmount() &&
                    !isFacePlaceTarget(currentTarget)) {
                continue;
            }


            if (selfDamage > maxSelfDmg.get() && !foundLethal) {
                continue;
            }

            double currentCrystalScore = targetDamage - selfDamage * 0.5;
            if ((crystalToBreak == null || currentCrystalScore > maxDamage) && !foundLethal) {
                maxDamage = currentCrystalScore;
                crystalToBreak = crystal;
            }
        }
    }

    private boolean hasTotem(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                return true;
            }
        }
        return player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;
    }

    private boolean isFacePlaceTarget(PlayerEntity player) {
        return facePlace.get() && player.getHealth() + player.getAbsorptionAmount() <= facePlaceHealth.get();
    }

    private void performBreak() {
        if (crystalToBreak == null) return;

        if (rotate.getValue()) {
            Vec3d crystalVec = crystalToBreak.getPos();
            OyVey.rotationManager.rotate(RotationUtils.getRotations(crystalVec), 1);
            shouldRotate = true;
        }


        if (antiWeakness.get() && mc.player.hasStatusEffect(StatusEffects.WEAKNESS)) {
            int oldSlot = mc.player.getInventory().getSelectedSlot();
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);

                if (stack.contains(DataComponentTypes.WEAPON)) {
                    mc.player.getInventory().setSelectedSlot(i);
                    break;
                }
            }

            if (packetBreak.get()) {
                mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystalToBreak, mc.player.isSneaking()));
            } else {
                mc.interactionManager.attackEntity(mc.player, crystalToBreak);
            }

            if (breakSwing.getValue() != SwingMode.NONE) {
                mc.player.swingHand(breakSwing.getValue() == SwingMode.MAINHAND ? Hand.MAIN_HAND : Hand.OFF_HAND);
            }

            mc.player.getInventory().setSelectedSlot(oldSlot);
        } else {
            if (packetBreak.get()) {
                mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystalToBreak, mc.player.isSneaking()));
            } else {
                mc.interactionManager.attackEntity(mc.player, crystalToBreak);
            }

            if (breakSwing.getValue() != SwingMode.NONE) {
                mc.player.swingHand(breakSwing.getValue() == SwingMode.MAINHAND ? Hand.MAIN_HAND : Hand.OFF_HAND);
            }
        }

        mc.player.resetLastAttackedTicks();
    }

    private void findBestPlacePosition() {
        bestPlacePos = null;
        if (currentTarget == null || mc.player == null || mc.world == null) return;

        double bestDamage = 0.0;
        boolean foundLethal = false;

        List<BlockPos> possiblePositions = getSphere(mc.player.getBlockPos(), placeRange.get().floatValue(), placeRange.get().intValue(), false, true, 0);


        if (jumpPredict.get() && currentTarget.getVelocity().y > 0) {
            BlockPos headPos = new BlockPos((int) currentTarget.getX(), (int) currentTarget.getY() + 2, (int) currentTarget.getZ());
            if (canPlaceCrystal(headPos, true, true)) {
                possiblePositions.add(headPos);
            }
        }

        for (BlockPos pos : possiblePositions) {
            if (!canPlaceCrystal(pos, true, true)) {
                continue;
            }

            Vec3d interactionPoint = Vec3d.ofCenter(pos).add(0, 0.5, 0);
            BlockHitResult hitResult = mc.world.raycast(new RaycastContext(mc.player.getEyePos(), interactionPoint, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player));
            if (hitResult.getType() == HitResult.Type.BLOCK && !hitResult.getBlockPos().equals(pos) && !hitResult.getBlockPos().equals(pos.down())) {
                if (mc.player.getEyePos().distanceTo(Vec3d.ofCenter(pos)) > wallRange.get()) continue;
            }

            Vec3d crystalExplosionCenter = Vec3d.ofCenter(pos.up());


            double targetDamage = DamageUtils.getCrystalDamage(currentTarget, predictedPos, crystalExplosionCenter, false);
            double selfDamage = DamageUtils.getCrystalDamage(mc.player, mc.player.getBoundingBox(), Vec3d.ofCenter(pos.up()), false);


            if (selfDamage + (antiSuicide.get() ? 2.0 : 0.5) >= mc.player.getHealth() + mc.player.getAbsorptionAmount()) {
                continue;
            }


            boolean hasTotem = hasTotem(currentTarget);
            boolean isLethalDamage = targetDamage >= currentTarget.getHealth() + currentTarget.getAbsorptionAmount();
            boolean isAntiTotem = antiTotem.get() && hasTotem && targetDamage > 8.0;


            if ((isLethalDamage || isAntiTotem) && lethalDamage.get()) {

                double maxAllowedSelfDmg = lethalMultiplier.get() * maxSelfDmg.get();
                if (selfDamage < maxAllowedSelfDmg && selfDamage < mc.player.getHealth() + mc.player.getAbsorptionAmount() - 1.0) {
                    bestPlacePos = pos;
                    foundLethal = true;
                    break;
                }
            }


            if (selfDamage > maxSelfDmg.get() && !foundLethal) {
                continue;
            }


            if (targetDamage < minPlaceDmg.get() && !foundLethal &&
                    !isFacePlaceTarget(currentTarget)) {
                continue;
            }

            if (targetDamage > bestDamage || foundLethal) {
                bestDamage = targetDamage;
                bestPlacePos = pos;
            }
        }
    }

    private void performPlace() {
        if (bestPlacePos == null) return;

        Hand handToUse = Hand.MAIN_HAND;
        boolean isCrystalInMain = mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL;
        boolean isCrystalInOff = mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL;
        int oldSlot = mc.player.getInventory().getSelectedSlot();
        boolean switched = false;
        int crystalSlot = InventoryUtils.find(Items.END_CRYSTAL, 0, autoSwitch.getValue() == InventoryUtils.SWITCH_MODES.AltSwap || autoSwitch.getValue() == InventoryUtils.SWITCH_MODES.AltPickup ? 35 : 8);
        if (!isCrystalInMain && !isCrystalInOff && slient.getValue()) {
            if (crystalSlot != -1) {

                originalSlot = oldSlot;
                isSwitched = true;

                InventoryUtils.switchSlot(autoSwitch.getValue(), crystalSlot, oldSlot);
                switched = true;
                isCrystalInMain = true;
            } else {
                return;
            }
        } else if (isCrystalInOff) {
            handToUse = Hand.OFF_HAND;
        } else if (!isCrystalInMain) {
            return;
        }

        if (rotate.getValue()) {
            OyVey.rotationManager.rotate(RotationUtils.getRotations(Vec3d.ofCenter(bestPlacePos, 1)), 1);
            shouldRotate = true;
        }

        Direction direction = getClosestDirection(bestPlacePos, true);
        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerInteractBlockC2SPacket(handToUse, new BlockHitResult(getHitVector(bestPlacePos, direction), direction, bestPlacePos, false), 0));

        if (switched && !isSwitched) {
            InventoryUtils.switchBack(autoSwitch.getValue(), crystalSlot, oldSlot);
        }
    }

    private BlockState getCachedState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    private Vec3d predictPlayerPosition(PlayerEntity player, double ticks, boolean enableHighJump) {
        Vec3d currentPos = player.getEntityPos();
        Vec3d velocity = player.getVelocity();
        if (velocity.horizontalLength() < 0.001) {
            return currentPos;
        }

        Vec3d predictedPos = currentPos;
        Vec3d currentVelocity = velocity;
        boolean onGround = player.isOnGround();
        boolean isSprinting = player.isSprinting();
        boolean isSneaking = player.isSneaking();
        boolean isJumping = onGround && velocity.y >= 0.41 && velocity.y <= 0.43
                && !player.hasStatusEffect(StatusEffects.JUMP_BOOST)
                && !player.isClimbing() && !player.isInLava() && !player.isTouchingWater();

        final double FRICTION = 0.6;
        final double AIR_DRAG = 0.91;
        final double GRAVITY = 0.08;
        final double TERMINAL_VELOCITY = -3.92;
        final double HIGH_JUMP_VELOCITY = 1;

        for (int i = 0; i < ticks; i++) {
            if (i == 0 && isJumping) {
                if (enableHighJump) {
                    currentVelocity = new Vec3d(currentVelocity.x, HIGH_JUMP_VELOCITY, currentVelocity.z);
                }
                onGround = false;
            }

            double speedMultiplier = 1.0;
            if (isSprinting) speedMultiplier = 1.3;
            else if (isSneaking) speedMultiplier = 0.3;
            if (player.isTouchingWater()) speedMultiplier *= 0.8;
            if (player.hasStatusEffect(StatusEffects.SPEED)) {
                speedMultiplier *= (1.0 + 0.2 * (player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1));
            }
            if (player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                speedMultiplier *= (1.0 - 0.2 * (player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1));
            }
            Vec3d adjustedVelocity = currentVelocity.multiply(speedMultiplier, 1.0, speedMultiplier);

            predictedPos = predictedPos.add(adjustedVelocity);

            boolean hitCeiling = false;
            if (!onGround) {
                for (int yOffset = 0; yOffset <= 2; yOffset++) {
                    BlockPos abovePos = new BlockPos((int) predictedPos.x, (int) (predictedPos.y + yOffset), (int) predictedPos.z);
                    BlockState state = getCachedState(abovePos);
                    if (!state.isAir() && !state.getCollisionShape(mc.world, abovePos).isEmpty()) {
                        predictedPos = new Vec3d(predictedPos.x, abovePos.getY() - 0.01, predictedPos.z);
                        currentVelocity = new Vec3d(currentVelocity.x, 0, currentVelocity.z);
                        hitCeiling = true;
                        break;
                    }
                }
            }

            Box playerBox = new Box(predictedPos.x - 0.3, predictedPos.y, predictedPos.z - 0.3,
                    predictedPos.x + 0.3, predictedPos.y + 1.8, predictedPos.z + 0.3);
            boolean hasCollision = false;
            for (VoxelShape shape : mc.world.getBlockCollisions(null, playerBox)) {
                if (!shape.isEmpty()) {
                    hasCollision = true;
                    break;
                }
            }
            if (hasCollision) {
                currentVelocity = new Vec3d(0, currentVelocity.y, 0);
            }

            if (!onGround && !hitCeiling) {
                currentVelocity = currentVelocity.add(0, -GRAVITY, 0);
                if (currentVelocity.y < TERMINAL_VELOCITY) {
                    currentVelocity = new Vec3d(currentVelocity.x, TERMINAL_VELOCITY, currentVelocity.z);
                }
            } else if (onGround) {
                currentVelocity = new Vec3d(currentVelocity.x, 0, currentVelocity.z);
            }

            double envFriction = FRICTION;
            BlockPos feetPos = new BlockPos((int) predictedPos.x, (int) predictedPos.y, (int) predictedPos.z);
            BlockState feetState = getCachedState(feetPos);
            if (feetState.isOf(Blocks.SOUL_SAND)) envFriction *= 0.4;
            if (feetState.isOf(Blocks.HONEY_BLOCK)) envFriction *= 0.6;
            if (onGround) {
                currentVelocity = currentVelocity.multiply(envFriction, 1.0, envFriction);
            }
            currentVelocity = currentVelocity.multiply(AIR_DRAG, 0.98, AIR_DRAG);

            BlockPos belowPos = new BlockPos((int) predictedPos.x, (int) (predictedPos.y - 0.1), (int) predictedPos.z);
            BlockState belowState = getCachedState(belowPos);
            onGround = !belowState.isAir() && !belowState.getCollisionShape(mc.world, belowPos).isEmpty();

            if (currentVelocity.horizontalLength() < 0.001 && onGround) {
                break;
            }
        }

        Box finalBox = new Box(predictedPos.x - 0.3, predictedPos.y, predictedPos.z - 0.3,
                predictedPos.x + 0.3, predictedPos.y + 1.8, predictedPos.z + 0.3);
        if (!mc.world.getWorldBorder().contains(finalBox)) {
            return currentPos;
        }

        return predictedPos;
    }

  */
/*  @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (bestPlacePos != null) {
            BlockPos renderPos = bestPlacePos.up();

            float minX = renderPos.getX();
            float minY = renderPos.getY();
            float minZ = renderPos.getZ();
            float maxX = renderPos.getX() + 1;
            float maxY = renderPos.getY() + 0.2f;
            float maxZ = renderPos.getZ() + 1;

            Box sliceBox = new Box(minX, minY, minZ, maxX, maxY, maxZ);

            Color placeColor = new Color(placeRColorSetting.getValue(),placeBlphaSetting.getValue(),placeGlphaSetting.getValue(), placeAlphaSetting.getValue());
            RenderUtil.drawBox(event.getMatrix(), sliceBox, placeColor, O.getValue());
            RenderUtil.drawBoxFilled(event.getMatrix(), sliceBox, placeColor);

        }

        if (predictedPos != null && currentTarget != null && predictColor.getValue()) {
            BlockPos predictedBlockPos = new BlockPos((int)predictedPos.x, (int)predictedPos.y, (int)predictedPos.z);
            BlockPos renderPos = predictedBlockPos;
            float minX = renderPos.getX();
            float minY = renderPos.getY();
            float minZ = renderPos.getZ();
            float maxX = renderPos.getX() + 1;
            float maxY = renderPos.getY() + 0.2f;
            float maxZ = renderPos.getZ() + 1;

            Box sliceBox = new Box(minX, minY, minZ, maxX, maxY, maxZ);

            Color predictColor = new Color(predictRColorSetting.getValue(), predictBlphaSetting.getValue(), predictGlphaSetting.getValue(), predictAlphaSetting.getValue());
            RenderUtil.drawBox(event.getMatrix(), sliceBox, predictColor, O.getValue());
            RenderUtil.drawBoxFilled(event.getMatrix(), sliceBox, predictColor);
        }
    }*//*



    //misc



    public static List<BlockPos> getSphere(final BlockPos pos, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = pos.getX();
        final int cy = pos.getY();
        final int cz = pos.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = (float)y;
                    final float f2 = sphere ? (cy + r) : ((float)(cy + h));
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }


    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck, final boolean checkEntities) {
        if (mc.world == null || mc.player == null) {
            return false;
        }
        BlockState baseBlockState = mc.world.getBlockState(blockPos);
        if (baseBlockState.getBlock() != Blocks.BEDROCK && baseBlockState.getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        BlockPos crystalPos = blockPos.up();
        BlockState crystalBlockState = mc.world.getBlockState(crystalPos);
        if (!crystalBlockState.isReplaceable()) {
            return false;
        }
        if (checkEntities) {
            Box crystalCheckBox = new Box(crystalPos);
            List<Entity> entitiesInCrystalSpace = mc.world.getOtherEntities(
                    null,
                    crystalCheckBox
            );
            for (final Entity entity : entitiesInCrystalSpace) {
                if (entity.isAlive()) {
                    if (specialEntityCheck && entity instanceof EndCrystalEntity) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
*/
