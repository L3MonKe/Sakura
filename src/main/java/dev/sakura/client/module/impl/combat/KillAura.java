package dev.sakura.client.module.impl.combat;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.MotionEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.utils.vector.Rotation;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.awt.*;
import java.util.stream.StreamSupport;

public class KillAura extends Module {

    private final EnumValue<Page> page = new EnumValue<>("Page", "页面", Page.General);

    private final EnumValue<TargetMode> mode = new EnumValue<>("Mode", "模式", TargetMode.Switch, () -> page.is(Page.General));
    private final EnumValue<Priority> priority = new EnumValue<>("Priority", "优先", Priority.Health, () -> page.is(Page.General));

    private final NumberValue<Double> searchRange = new NumberValue<>("EnemyRange", "搜敌范围", 5.0, 1.0, 10.0, 0.1, () -> page.is(Page.Targeting));
    private final NumberValue<Double> range = new NumberValue<>("Range", "攻击范围", 4.5, 1.0, 6.0, 0.1, () -> page.is(Page.Targeting));
    private final NumberValue<Double> wallRange = new NumberValue<>("WallRange", "穿墙范围", 4.5, 0.0, 6.0, 0.1, () -> page.is(Page.Targeting));
    private final BoolValue vanillaRange = new BoolValue("VanillaRange", "原版范围", false, () -> page.is(Page.Targeting));
    private final NumberValue<Double> fov = new NumberValue<>("FOV", "视野", 180.0, 1.0, 180.0, 1.0, () -> page.is(Page.Targeting));
    private final NumberValue<Integer> ticksExisted = new NumberValue<>("TicksExisted", "存在时间", 0, 0, 200, 1, () -> page.is(Page.Targeting));
    private final BoolValue armorCheck = new BoolValue("ArmorCheck", "护甲检查", false, () -> page.is(Page.Targeting));

    private final BoolValue players = new BoolValue("Players", "玩家", true, () -> page.is(Page.Targeting));
    private final BoolValue monsters = new BoolValue("Monsters", "怪物", false, () -> page.is(Page.Targeting));
    private final BoolValue neutrals = new BoolValue("Neutrals", "中立生物", false, () -> page.is(Page.Targeting));
    private final BoolValue animals = new BoolValue("Animals", "动物", false, () -> page.is(Page.Targeting));
    private final BoolValue invisibles = new BoolValue("Invisibles", "隐身", true, () -> page.is(Page.Targeting));

    private final BoolValue attackDelay = new BoolValue("AttackDelay", "攻击延迟", true, () -> page.is(Page.Attack));
    private final NumberValue<Double> attackSpeed = new NumberValue<>("AttackSpeed", "攻击速度", 20.0, 1.0, 20.0, 1.0, () -> page.is(Page.Attack) && !attackDelay.get());
    private final NumberValue<Double> randomSpeed = new NumberValue<>("RandomSpeed", "随机速度", 0.0, 0.0, 10.0, 0.1, () -> page.is(Page.Attack) && !attackDelay.get());
    private final NumberValue<Double> delay = new NumberValue<>("Delay", "延迟系数", 0.89, 0.0, 1.0, 0.01, () -> page.is(Page.Attack) && attackDelay.get());

    private final BoolValue rotateEnabled = new BoolValue("Rotate", "旋转", false, () -> page.is(Page.Rotation));
    private final EnumValue<Rotate> rotate = new EnumValue<>("RotateMode", "旋转模式", Rotate.Normal, () -> page.is(Page.Rotation) && rotateEnabled.get());
    private final BoolValue silentRotate = new BoolValue("RotateSilent", "静默旋转", false, () -> page.is(Page.Rotation) && rotateEnabled.get());
    private final BoolValue yawStep = new BoolValue("YawStep", "偏航步进", false, () -> page.is(Page.Rotation) && rotateEnabled.get());
    private final NumberValue<Integer> yawStepLimit = new NumberValue<>("YawStep-Limit", "步进限制", 180, 1, 180, 1, () -> page.is(Page.Rotation) && rotateEnabled.get() && yawStep.get());
    private final EnumValue<HitVector> hitVector = new EnumValue<>("HitVector", "打击向量", HitVector.Feet, () -> page.is(Page.Rotation));

    private final NumberValue<Double> swapPenalty = new NumberValue<>("SwapPenalty", "切换惩罚", 0.0, 0.0, 10.0, 0.1, () -> page.is(Page.Swap));
    private final EnumValue<Swap> swap = new EnumValue<>("Swap", "切换", Swap.Require, () -> page.is(Page.Swap));

    private final EnumValue<Sprint> stopSprinting = new EnumValue<>("Sprinting", "疾跑控制", Sprint.None, () -> page.is(Page.Misc));
    private final BoolValue stopShield = new BoolValue("StopShield", "停盾", false, () -> page.is(Page.Misc));
    private final BoolValue strictHit = new BoolValue("StrictHit", "严格打击", true, () -> page.is(Page.Misc));
    private final BoolValue multitask = new BoolValue("Multitask", "多任务", true, () -> page.is(Page.Misc));
    private final BoolValue swing = new BoolValue("Swing", "挥手", true, () -> page.is(Page.Misc));

    private final BoolValue render = new BoolValue("Render", "渲染", true, () -> page.is(Page.Render));
    private final EnumValue<ESPMode> espMode = new EnumValue<>("ESPMode", "ESP模式", ESPMode.JELLO, () -> page.is(Page.Render) && render.get());
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "半径", 0.8, 0.1, 2.0, 0.05, () -> page.is(Page.Render) && render.get());
    private final ColorValue lineColor = new ColorValue("LineColor", "线条颜色", new Color(255, 255, 255, 233), () -> page.is(Page.Render) && render.get());
    private final ColorValue sideColor = new ColorValue("SideColor", "侧面颜色", new Color(255, 255, 255, 23), () -> page.is(Page.Render) && render.get());
    private final ColorValue renderColor = new ColorValue("Color", "颜色", new Color(255, 0, 0, 100), () -> page.is(Page.Render) && render.get() && espMode.is(ESPMode.BOX));

    private Entity currentTarget;
    private float attackCooldownTicks = 0f;

    private final TimerUtil attackTimer = new TimerUtil();
    private final TimerUtil swapTimer = new TimerUtil();
    private long randomDelayMs = -1;

    private Entity pendingTarget;
    private boolean pendingAttack;
    private boolean pendingSkipCooldown;
    private float pendingTps;

    private static float prevCircleStep;
    private static float circleStep;

    public KillAura() {
        super("KillAura", "杀戮光环", Category.Combat);
    }

    @Override
    public void onEnable() {
        attackTimer.reset();
        swapTimer.reset();
        attackCooldownTicks = 0f;
        randomDelayMs = -1;
        pendingTarget = null;
        pendingAttack = false;
        pendingSkipCooldown = false;
        pendingTps = 20f;
    }

    private enum Page {
        General,
        Targeting,
        Attack,
        Rotation,
        Swap,
        Render,
        Misc
    }

    public enum TargetMode {
        Switch, Single
    }

    public enum Priority {
        Health, Distance, Armor
    }

    public enum Swap {
        None, Require, Normal, Silent
    }

    public enum Sprint {
        None, Motion, Packet
    }

    public enum Rotate {
        Normal, Hold, None
    }

    public enum HitVector {
        Eyes, Torso, Feet, Auto
    }

    public enum ESPMode {
        BOX, JELLO
    }

    @Override
    public void onDisable() {
        currentTarget = null;
        randomDelayMs = -1;
        pendingTarget = null;
        pendingAttack = false;
    }

    public Entity getCurrentTarget() {
        return currentTarget;
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null) return;
        if (event.getType() != EventType.SEND) return;
        if (!(event.getPacket() instanceof UpdateSelectedSlotC2SPacket)) return;

        swapTimer.reset();
        if (attackDelay.get()) {
            attackCooldownTicks = getBaseCooldownTicks(mc.player.getMainHandStack(), 20f);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        prevCircleStep = circleStep;
        circleStep += 0.15f;

        float tps = 20f;

        if (attackDelay.get()) {
            attackCooldownTicks -= 1f * (tps / 20f);
            if (attackCooldownTicks < 0f) attackCooldownTicks = 0f;
        }

        if (!multitask.get() && mc.player.isUsingItem()) {
            currentTarget = null;
            return;
        }

        Vec3d eyePos = mc.player.getEyePos();

        Entity target;
        if (mode.get() == TargetMode.Switch) {
            target = getTarget(eyePos);
        } else {
            if (currentTarget == null || !isValidTarget(eyePos, currentTarget, true)) {
                target = getTarget(eyePos);
            } else {
                target = currentTarget;
            }
        }
        if (target != null && swap.get() == Swap.Require) {
            ItemStack stack = mc.player.getMainHandStack();
            if (!isWeapon(stack) && getWeaponSlot() == -1) {
                currentTarget = null;
                setSuffix("");
                return;
            }
        }

        if (target == null) {
            currentTarget = null;
            setSuffix("");
            return;
        }

        currentTarget = target;
        setSuffix(target.getName().getString());

        if (!swapTimer.delay(swapPenalty.get().floatValue())) {
            return;
        }

        boolean skipCooldown = false;

        if (target instanceof ShulkerBulletEntity) {
            skipCooldown = true;
        } else if (target instanceof LivingEntity living) {
            float attackDamage = getAttackDamage(mc.player.getMainHandStack());
            if (living.getMaxHealth() <= attackDamage) {
                skipCooldown = true;
            }
        }

        if (rotateEnabled.get() && rotate.get() != Rotate.None) {
            Vec3d attackVec = getAttackVec(target);
            Rotation desired = RotationUtil.calculate(attackVec);

            if (yawStep.get()) {
                float currentYaw = Managers.ROTATION.getYaw();
                float diff = MathHelper.wrapDegrees(desired.yaw - currentYaw);
                float limit = yawStepLimit.get();
                if (Math.abs(diff) > limit) {
                    desired = new Rotation(currentYaw + MathHelper.clamp(diff, -limit, limit), desired.pitch);
                }
            }

            Managers.ROTATION.setRotations(desired, 100, MovementFix.OFF, RotationManager.Priority.Medium);
            if (!silentRotate.get()) {
                mc.player.setYaw(desired.yaw);
                mc.player.setPitch(desired.pitch);
            }

            double walls = wallRange.get();
            boolean isFacing = RaytraceUtil.facingEnemy(mc.player, target, Managers.ROTATION.getRotation(), range.get(), walls);
            if (!isFacing && RotationManager.lastServerRotations != null) {
                isFacing = RaytraceUtil.facingEnemy(mc.player, target, RotationManager.lastServerRotations, range.get(), walls);
            }
            if (!isFacing) return;
        }

        if (!isInAttackRange(eyePos, target)) {
            return;
        }

        if (!skipCooldown) {
            if (attackDelay.get()) {
                if (mc.player.getAttackCooldownProgress(0.0f) < delay.get().floatValue()) return;
            } else {
                if (randomDelayMs < 0) {
                    double max = randomSpeed.get() * 10.0;
                    randomDelayMs = (long) (Math.random() * (max + 1.0));
                }
                double configuredDelay = (attackSpeed.get() * 50.0) + randomDelayMs;
                double threshold = 1000.0 - configuredDelay;
                if (threshold < 0.0) threshold = 0.0;
                if (!attackTimer.delay((float) (threshold / 50.0))) return;
            }
        }

        pendingTarget = target;
        pendingAttack = true;
        pendingSkipCooldown = skipCooldown;
        pendingTps = tps;
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() != EventType.POST) return;
        if (!pendingAttack || pendingTarget == null || nullCheck()) return;

        Entity target = pendingTarget;
        boolean skipCooldown = pendingSkipCooldown;
        float tps = pendingTps;

        pendingAttack = false;
        pendingTarget = null;
        pendingSkipCooldown = false;

        if (!target.isAlive() || target.isSpectator()) return;

        Vec3d eyePos = mc.player.getEyePos();
        if (!isValidTarget(eyePos, target, true)) return;

        if (!skipCooldown && attackDelay.get()) {
            if (mc.player.getAttackCooldownProgress(0.0f) < delay.get().floatValue()) return;
        }


        double walls = wallRange.get();
        Rotation rayRot = RotationManager.lastServerRotations != null ? RotationManager.lastServerRotations : Managers.ROTATION.getRotation();

        if (strictHit.get() && !target.getBoundingBox().contains(eyePos)) {
            EntityHitResult hit = RaytraceUtil.rayTraceEntity(range.get(), rayRot, e -> isValidTarget(eyePos, e, true));
            if (hit == null || hit.getEntity() == null) return;
            target = hit.getEntity();
        }

        Vec3d attackVec = getAttackVec(target);
        boolean isFacing = RaytraceUtil.facingEnemy(mc.player, target, rayRot, range.get(), walls);
        if (!isFacing) {
            isFacing = RaytraceUtil.facingEnemy(mc.player, target, Managers.ROTATION.getRotation(), range.get(), walls);
        }
        if (!isFacing && !target.getBoundingBox().contains(eyePos)) return;

        boolean sprintingCanceled = false;
        if (stopSprinting.get() == Sprint.Packet && !mc.player.isSneaking() && mc.player.isSprinting()) {
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            sprintingCanceled = true;
        } else if (stopSprinting.get() == Sprint.Motion) {
            mc.player.setSprinting(false);
        }

        int prevSlot = -1;
        if (swap.get() == Swap.Normal || swap.get() == Swap.Silent) {
            int slot = getWeaponSlot();
            if (slot != -1 && slot != mc.player.getInventory().selectedSlot) {
                prevSlot = mc.player.getInventory().selectedSlot;
                InvUtil.swap(slot, false);
                swapTimer.reset();
            }
        }

        if (swap.get() == Swap.Require && prevSlot == -1 && !isWeapon(mc.player.getMainHandStack())) {
            if (sprintingCanceled) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            }
            return;
        }

        boolean wasBlocking = false;
        if (stopShield.get()) {
            ItemStack offhand = mc.player.getOffHandStack();
            if (offhand.getItem() == Items.SHIELD && mc.player.isBlocking()) {
                wasBlocking = true;
                BlockPos pos = mc.player.getBlockPos();
                Direction facing = mc.player.getHorizontalFacing();
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, pos, facing));
            }
        }

        mc.interactionManager.attackEntity(mc.player, target);
        if (swing.get()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        if (wasBlocking) {
            mc.interactionManager.interactItem(mc.player, Hand.OFF_HAND);
        }

        if (swap.get() == Swap.Silent && prevSlot != -1) {
            InvUtil.swap(prevSlot, false);
            swapTimer.reset();
        }

        if (sprintingCanceled) {
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
        }

        if (!skipCooldown) {
            if (attackDelay.get()) {
                attackCooldownTicks = getBaseCooldownTicks(mc.player.getMainHandStack(), tps);
            } else {
                randomDelayMs = -1;
                attackTimer.reset();
            }
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!render.get() || currentTarget == null) return;

        if (espMode.is(ESPMode.BOX)) {
            Render3DUtil.drawFilledBox(event.getMatrices(), currentTarget.getBoundingBox(), renderColor.get());
            Render3DUtil.drawBoxOutline(event.getMatrices(), currentTarget.getBoundingBox(), renderColor.get().getRGB(), 2f);
        } else {
            drawJelloTargetEsp(event.getMatrices(), currentTarget, lineColor.get(), sideColor.get(), event.getTickDelta());
        }
    }

    private void drawJelloTargetEsp(net.minecraft.client.util.math.MatrixStack stack, Entity target, Color line, Color side, float tickDelta) {
        double cs = prevCircleStep + (circleStep - prevCircleStep) * tickDelta;
        double prevSinAnim = Math.abs(1 + Math.sin(cs - 0.45f)) / 2;
        double sinAnim = Math.abs(1 + Math.sin(cs)) / 2;

        Vec3d cam = mc.getEntityRenderDispatcher().camera.getPos();
        double x = target.prevX + (target.getX() - target.prevX) * tickDelta - cam.getX();
        double y = target.prevY + (target.getY() - target.prevY) * tickDelta - cam.getY() + prevSinAnim * target.getHeight();
        double z = target.prevZ + (target.getZ() - target.prevZ) * tickDelta - cam.getZ();
        double nextY = target.prevY + (target.getY() - target.prevY) * tickDelta - cam.getY() + sinAnim * target.getHeight();

        stack.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

        for (int i = 0; i <= 30; i++) {
            float cx = (float) (x + Math.cos(i * 6.28 / 30) * target.getWidth() * radius.get());
            float cz = (float) (z + Math.sin(i * 6.28 / 30) * target.getWidth() * radius.get());
            bufferBuilder.vertex(stack.peek().getPositionMatrix(), cx, (float) nextY, cz).color(line.getRGB());
            bufferBuilder.vertex(stack.peek().getPositionMatrix(), cx, (float) y, cz).color(side.getRGB());
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        stack.pop();
    }

    private Entity getTarget(Vec3d eyePos) {
        return StreamSupport.stream(mc.world.getEntities().spliterator(), false)
                .filter(e -> isValidTarget(eyePos, e, false))
                .min((a, b) -> Double.compare(getPriorityValue(a, eyePos), getPriorityValue(b, eyePos)))
                .orElse(null);
    }

    private boolean isValidTarget(Vec3d eyePos, Entity e, boolean checkAttackRange) {
        if (e == null || e == mc.player) return false;
        if (!e.isAlive() || e.isSpectator()) return false;
        if (e instanceof ItemEntity || e instanceof ArrowEntity || e instanceof ExperienceBottleEntity) return false;
        if (e.age < ticksExisted.get()) return false;

        if (!invisibles.get() && e.isInvisible()) return false;
        if (!(e instanceof PlayerEntity) && !(e instanceof LivingEntity) && !(e instanceof ShulkerBulletEntity))
            return false;

        if (e instanceof PlayerEntity) {
            if (!players.get()) return false;
        } else if (e instanceof HostileEntity) {
            if (!monsters.get()) return false;
        } else if (e instanceof PassiveEntity) {
            if (!animals.get()) return false;
        } else if (e instanceof Angerable) {
            if (!neutrals.get()) return false;
        } else if (e instanceof ShulkerBulletEntity) {
            return true;
        } else {
            return false;
        }

        double dist = eyePos.distanceTo(getAttackVec(e));
        if (dist > searchRange.get()) return false;

        if (armorCheck.get() && e instanceof LivingEntity living) {
            if (!living.getArmorItems().iterator().hasNext()) return false;
        }

        if (checkAttackRange) {
            return isInAttackRange(eyePos, e);
        }
        return true;
    }

    private double getPriorityValue(Entity e, Vec3d eyePos) {
        return switch (priority.get()) {
            case Distance -> eyePos.distanceTo(getAttackVec(e));
            case Health ->
                    e instanceof LivingEntity living ? (living.getHealth() + living.getAbsorptionAmount()) : Double.MAX_VALUE;
            case Armor -> e instanceof LivingEntity living ? getArmorDurability(living) : Double.MAX_VALUE;
        };
    }

    private double getArmorDurability(LivingEntity e) {
        float dmg = 0.0f;
        float max = 0.0f;
        for (ItemStack armor : e.getArmorItems()) {
            if (armor != null && !armor.isEmpty()) {
                dmg += armor.getDamage();
                max += armor.getMaxDamage();
            }
        }
        if (max <= 0.0f) return 100.0;
        return 100.0 - (dmg / max);
    }

    private boolean isInAttackRange(Vec3d eyePos, Entity entity) {
        Vec3d vec = getAttackVec(entity);
        double dist = eyePos.distanceTo(vec);

        if (vanillaRange.get() && dist > 3.0) return false;
        if (dist > range.get()) return false;

        BlockHitResult result = mc.world.raycast(new RaycastContext(
                eyePos,
                vec,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                mc.player
        ));

        if (result != null && result.getType() == HitResult.Type.BLOCK && dist > wallRange.get()) {
            BlockPos expected = BlockPos.ofFloored(vec);
            if (!result.getBlockPos().equals(expected)) {
                return false;
            }
        }

        if (fov.get() != 180.0) {
            float idealYaw = (float) (MathHelper.atan2(vec.z - eyePos.z, vec.x - eyePos.x) * (180.0 / Math.PI)) - 90.0f;
            float diff = MathHelper.wrapDegrees(mc.player.getYaw()) - MathHelper.wrapDegrees(idealYaw);
            float magnitude = Math.abs(diff);
            return magnitude <= fov.get();
        }
        return true;
    }

    private Vec3d getAttackVec(Entity entity) {
        Vec3d feet = entity.getPos();
        Vec3d torso = feet.add(0.0, entity.getHeight() / 2.0, 0.0);
        Vec3d eyes = entity.getEyePos();

        return switch (hitVector.get()) {
            case Feet -> feet;
            case Torso -> torso;
            case Eyes -> eyes;
            case Auto -> {
                Vec3d a = feet;
                Vec3d b = torso;
                Vec3d c = eyes;
                Vec3d eye = mc.player.getEyePos();
                Vec3d best = a;
                double bestDist = eye.squaredDistanceTo(a);
                double bd = eye.squaredDistanceTo(b);
                if (bd < bestDist) {
                    bestDist = bd;
                    best = b;
                }
                double cd = eye.squaredDistanceTo(c);
                if (cd < bestDist) {
                    best = c;
                }
                yield best;
            }
        };
    }

    private boolean isWeapon(ItemStack stack) {
        return stack.isIn(ItemTags.SWORDS)
                || stack.isIn(ItemTags.AXES)
                || stack.getItem() instanceof TridentItem
                || stack.getItem() instanceof MaceItem;
    }

    private int getWeaponSlot() {
        int bestSlot = -1;
        float bestDamage = -1f;
        boolean prioritizeMace = !mc.player.getAbilities().flying && !mc.player.isOnGround();

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            boolean isSword = stack.isIn(ItemTags.SWORDS);
            boolean isAxe = stack.isIn(ItemTags.AXES);
            boolean isTrident = stack.getItem() instanceof TridentItem;
            boolean isMace = stack.getItem() instanceof MaceItem;

            if (!isSword && !isAxe && !isTrident && !isMace) continue;

            if (isMace && prioritizeMace) return i;

            float damage = getAttackDamage(stack);
            if (damage > bestDamage) {
                bestDamage = damage;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    private float getAttackDamage(ItemStack stack) {
        float attackDamage = 0f;

        if (stack.isIn(ItemTags.SWORDS)) attackDamage = 6f;
        if (stack.getItem() == Items.STONE_SWORD) attackDamage = 5f;
        if (stack.getItem() == Items.IRON_SWORD) attackDamage = 6f;
        if (stack.getItem() == Items.DIAMOND_SWORD) attackDamage = 7f;
        if (stack.getItem() == Items.NETHERITE_SWORD) attackDamage = 8f;

        if (stack.getItem() == Items.WOODEN_AXE) attackDamage = 7f;
        if (stack.getItem() == Items.STONE_AXE) attackDamage = 9f;
        if (stack.getItem() == Items.IRON_AXE) attackDamage = 9f;
        if (stack.getItem() == Items.DIAMOND_AXE) attackDamage = 9f;
        if (stack.getItem() == Items.NETHERITE_AXE) attackDamage = 10f;

        int sharpness = InvUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
        int smite = InvUtil.getEnchantmentLevel(stack, Enchantments.SMITE);
        int bane = InvUtil.getEnchantmentLevel(stack, Enchantments.BANE_OF_ARTHROPODS);

        attackDamage += sharpness * 1.25f + smite * 2.5f + bane * 2.5f;

        return attackDamage;
    }

    private float getBaseCooldownTicks(ItemStack stack, float tps) {
        float baseTicks;
        ItemStack currentStack = stack;

        if ((swap.get() == Swap.Silent || swap.get() == Swap.Normal)) {
            int weaponSlot = getWeaponSlot();
            if (weaponSlot != -1) currentStack = mc.player.getInventory().getStack(weaponSlot);
        }

        if (currentStack.isIn(ItemTags.SWORDS)) baseTicks = 13f;
        else if (currentStack.isIn(ItemTags.AXES)) baseTicks = 21f;
        else if (currentStack.getItem() instanceof TridentItem) baseTicks = 19f;
        else if (currentStack.getItem() instanceof MaceItem) baseTicks = 34f;
        else {
            float attackSpeed = 6.0f;
            baseTicks = 20f / attackSpeed;
        }

        return (baseTicks * (20f / tps)) * delay.get().floatValue();
    }
}
