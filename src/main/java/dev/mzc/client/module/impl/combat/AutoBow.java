package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.utils.rotation.RotationUtil;
import dev.mzc.client.utils.vector.Rotation;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AutoBow extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public enum TargetMode {
        Distance("距离近"),
        Health("血量低"),
        HealthPercentage("百分比血量低");

        private final String cnName;
        TargetMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final BoolValue autoAim = new BoolValue("AutoAim", "自动瞄准", true);
    private final EnumValue<TargetMode> targetMode = new EnumValue<>("TargetMode", "瞄准模式", TargetMode.Distance, autoAim::get);
    private final NumberValue<Double> aimSpeed = new NumberValue<>("AimSpeed", "瞄准速度", 5.0, 0.1, 20.0, 0.1, autoAim::get);
    private final BoolValue dynamicSpeed = new BoolValue("DynamicSpeed", "速度变换", true, autoAim::get);
    private final NumberValue<Double> farBoost = new NumberValue<>("FarBoost", "远距离加速%", 20.0, 0.0, 100.0, 1.0, () -> autoAim.get() && dynamicSpeed.get());
    private final NumberValue<Double> farBoostThreshold = new NumberValue<>("FarBoostThreshold", "加速距离阈值", 5.0, 1.0, 20.0, 0.5, () -> autoAim.get() && dynamicSpeed.get());
    private final NumberValue<Double> nearReduction = new NumberValue<>("NearReduction", "近距离减速%", 15.0, 0.0, 100.0, 1.0, () -> autoAim.get() && dynamicSpeed.get());
    private final NumberValue<Double> nearReductionThreshold = new NumberValue<>("NearReductionThreshold", "减速距离阈值", 2.0, 0.1, 10.0, 0.5, () -> autoAim.get() && dynamicSpeed.get());
    private final NumberValue<Double> fov = new NumberValue<>("FOV", "瞄准范围", 60.0, 10.0, 360.0, 1.0, autoAim::get);
    private final NumberValue<Double> jitter = new NumberValue<>("Jitter", "抖动强度", 0.2, 0.0, 2.0, 0.1, autoAim::get);
    private final NumberValue<Double> range = new NumberValue<>("Range", "检测距离", 30.0, 5.0, 100.0, 1.0);
    private final NumberValue<Double> predictDistance = new NumberValue<>("PredictDistance", "轨迹预测距离", 20.0, 5.0, 100.0, 1.0);
    private final NumberValue<Double> releaseThreshold = new NumberValue<>("ReleaseThreshold", "放箭角度误差", 3.0, 0.5, 10.0, 0.1);

    private boolean released = false; // 防止一帧多次松弓
    private LivingEntity currentTarget = null;
    private Vec3d targetVelocity = Vec3d.ZERO;
    private Vec3d predictedAimPos = null;
    private float currentYawDiff = 180f;
    private float currentPitchDiff = 180f;

    public AutoBow() {
        super("AutoBow", "自动弓箭", Category.Combat);
        this.setType(ModuleType.Safe);
    }

    @Override
    public void onEnable() {
        released = false;
        currentTarget = null;
        predictedAimPos = null;
    }

    @Override
    public void onDisable() {
        released = false;
        currentTarget = null;
        predictedAimPos = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        
        PlayerEntity player = mc.player;
        ItemStack stack = player.getMainHandStack();

        boolean isBow = stack.getItem() instanceof BowItem;
        boolean isCrossbow = stack.getItem() instanceof CrossbowItem;

        /* ===== 只处理弓 / 弩 ===== */
        if (!isBow && !isCrossbow) {
            released = false;
            currentTarget = null;
            return;
        }

        /* ===== 必须正在拉弓 / 上弦 ===== */
        if (!player.isUsingItem()) {
            released = false;
            currentTarget = null;
            return;
        }

        if (isBow) {
            /* ===== 弓：拉满检测（BowItem 官方算法） ===== */
            int useTicks = stack.getMaxUseTime(player) - player.getItemUseTimeLeft();
            float pull = BowItem.getPullProgress(useTicks);
            if (pull < 1.0f) {
                released = false;
                currentTarget = null;
                return;
            }
        } else {
            /* ===== 弩：拉满检测（CrossbowItem 官方动画进度算法） ===== */
            int useTicks = stack.getMaxUseTime(player) - player.getItemUseTimeLeft();
            float progress = (float) useTicks / (float) CrossbowItem.getPullTime(stack, player);
            if (progress < 1.0f) {
                released = false;
                currentTarget = null;
                return;
            }
        }

        /* ===== 已经松过，不再重复 ===== */
        if (released) return;

        /* ===== 目标寻找与预测 (Tick 级别处理逻辑) ===== */
        updateTargetAndPrediction();

        if (currentTarget == null || predictedAimPos == null) {
            released = false;
            return;
        }

        // 提前计算角度差，用于双重判定
        Rotation targetRot = RotationUtil.calculate(predictedAimPos);
        float yawDiff = Math.abs(MathHelper.wrapDegrees(targetRot.yaw - player.getYaw()));
        float pitchDiff = Math.abs(targetRot.pitch - player.getPitch());
        double totalDiff = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);

        // 如果已经高度锁定（误差极小），则无视轨迹直接放箭，增强手感
        if (autoAim.get() && totalDiff < 0.5) {
            releaseBow();
            released = true;
            return;
        }

        /* ===== 模拟箭的轨迹并检测命中 ===== */
        Vec3d eyePos = player.getEyePos();
        Vec3d velocity = player.getRotationVector().multiply(3.0); // 满拉速度
        Vec3d currentPos = eyePos;
        Vec3d currentVel = velocity;
        
        for (int i = 0; i < predictDistance.get().intValue(); i++) {
            Vec3d prevPos = currentPos;
            currentVel = currentVel.add(0, -0.05, 0); // 重力
            currentPos = currentPos.add(currentVel);

            // 构造目标在预测位置的碰撞箱 (稍微扩大一点容错范围 0.1)
            Box targetBox = currentTarget.getBoundingBox()
                    .offset(predictedAimPos.subtract(currentTarget.getPos().add(0, currentTarget.getStandingEyeHeight() * 0.5, 0)))
                    .expand(0.1);

            // 使用精确的线段与碰撞箱相交检测
            if (targetBox.raycast(prevPos, currentPos).isPresent() || currentTarget.getBoundingBox().raycast(prevPos, currentPos).isPresent()) {
                // 如果开启了自动瞄准，必须确保准星基本对准了预测点才放箭
                if (autoAim.get()) {
                    // 使用可配置的误差范围
                    if (totalDiff <= releaseThreshold.get()) {
                        releaseBow();
                        released = true;
                        return;
                    }
                } else {
                    // 没开自动瞄准则直接放箭
                    releaseBow();
                    released = true;
                    return;
                }
            }
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (!autoAim.get() || mc.player == null || mc.world == null) return;
        if (currentTarget == null || targetVelocity == null) return;
        
        // 只有正在拉弓/上弦时才瞄准
        ItemStack stack = mc.player.getMainHandStack();
        if (!(stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) || !mc.player.isUsingItem()) return;

        // 获取目标当前的插值位置 (Eye Level)
        Vec3d targetPos = currentTarget.getLerpedPos(event.getTickDelta()).add(0, currentTarget.getStandingEyeHeight() * 0.5, 0);
        
        // 重新计算距离和飞行时间
        double distance = mc.player.getEyePos().distanceTo(targetPos);
        double flightTime = distance / 3.0;
        
        // 使用向量预测：当前插值位置 + 速度向量 * 飞行时间
        Vec3d aimPos = targetPos.add(targetVelocity.multiply(flightTime));
        
        // 计算带重力补偿的瞄准点
        double g = 0.05;
        double yOffset = 0.5 * g * flightTime * flightTime;
        aimPos = aimPos.add(0, yOffset, 0);
        
        Rotation targetRotation = RotationUtil.calculate(aimPos);
        smoothAim(targetRotation);
    }

    private void updateTargetAndPrediction() {
        PlayerEntity player = mc.player;
        Vec3d eyePos = player.getEyePos();

        List<LivingEntity> targets = mc.world.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(range.get()),
                e -> e != player && e.isAlive() && !(e instanceof ArmorStandEntity) && player.canSee(e)
        );

        // FOV 过滤
        targets = targets.stream()
                .filter(e -> getAngleToEntity(e) <= fov.get() / 2.0)
                .collect(Collectors.toList());

        if (targets.isEmpty()) {
            currentTarget = null;
            predictedAimPos = null;
            targetVelocity = Vec3d.ZERO;
            return;
        }

        // 根据模式排序
        if (autoAim.get()) {
            // 自动瞄准开启时，使用指定的选择模式
            switch (targetMode.get()) {
                case Distance:
                    targets.sort(Comparator.comparingDouble(e -> player.distanceTo(e)));
                    break;
                case Health:
                    targets.sort(Comparator.comparingDouble(e -> e.getHealth()));
                    break;
                case HealthPercentage:
                    targets.sort(Comparator.comparingDouble(e -> e.getHealth() / e.getMaxHealth()));
                    break;
            }
        } else {
            // 自动瞄准关闭时（正常模式），优先选择准星最接近的目标
            targets.sort(Comparator.comparingDouble(this::getAngleToEntity));
        }

        currentTarget = targets.get(0);
        Vec3d targetPos = currentTarget.getPos().add(0, currentTarget.getStandingEyeHeight() * 0.5, 0);
        
        // 计算目标移动向量 (使用当前位置与上一 tick 位置的差值)
        targetVelocity = new Vec3d(
                currentTarget.getX() - currentTarget.prevX,
                currentTarget.getY() - currentTarget.prevY,
                currentTarget.getZ() - currentTarget.prevZ
        );
        
        // 如果目标在地面且没有明显的 Y 轴移动，强制 Y 轴向量为 0，防止预测点入地
        if (currentTarget.isOnGround() && Math.abs(targetVelocity.y) < 0.01) {
            targetVelocity = new Vec3d(targetVelocity.x, 0, targetVelocity.z);
        }

        double distance = eyePos.distanceTo(targetPos);
        double flightTime = distance / 3.0;
        
        // 迭代预测：考虑箭矢飞行过程中目标的位移
        // 第一次迭代
        Vec3d predPos = targetPos.add(targetVelocity.multiply(flightTime));
        // 第二次迭代：根据新的预测位置重新计算飞行时间
        flightTime = eyePos.distanceTo(predPos) / 3.0;
        predictedAimPos = targetPos.add(targetVelocity.multiply(flightTime));
    }

    /* ===== 正确松弓方式（关键） ===== */
    private void releaseBow() {
        if (mc.interactionManager != null && mc.player != null) {
            mc.interactionManager.stopUsingItem(mc.player);
        }
        // 同时重置按键状态，防止逻辑冲突
        mc.options.useKey.setPressed(false);
    }

    private double getAngleToEntity(LivingEntity entity) {
        Rotation rot = RotationUtil.calculate(entity);
        float yawDiff = Math.abs(MathHelper.wrapDegrees(rot.yaw - mc.player.getYaw()));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(rot.pitch - mc.player.getPitch()));
        return yawDiff + pitchDiff;
    }

    private void smoothAim(Rotation targetRotation) {
        float currentYaw = mc.player.getYaw();
        float currentPitch = mc.player.getPitch();

        float targetYaw = targetRotation.yaw;
        float targetPitch = targetRotation.pitch;

        // 加入高斯随机抖动，模拟人手震颤 (针对 Grim 启发式检测)
        if (jitter.get() > 0) {
            targetYaw += (float) (ThreadLocalRandom.current().nextGaussian() * jitter.get());
            targetPitch += (float) (ThreadLocalRandom.current().nextGaussian() * jitter.get());
        }

        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = targetPitch - currentPitch;

        // 记录当前与目标的角度差，用于放箭判定
        this.currentYawDiff = Math.abs(yawDiff);
        this.currentPitchDiff = Math.abs(pitchDiff);

        // 调整速度系数以适配 Render 循环，并加入随机波动
        double speed = aimSpeed.get() * 0.2;

        if (dynamicSpeed.get()) {
            // 计算总的角度差（欧几里得距离）
            double totalDiff = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);

            // 如果偏离较远，则加速
            if (totalDiff > farBoostThreshold.get()) {
                speed *= (1.0 + farBoost.get() / 100.0);
            }
            // 如果已经很接近了，则减速以防震荡并增加真实感
            else if (totalDiff < nearReductionThreshold.get()) {
                speed *= (1.0 - nearReduction.get() / 100.0);
            }
        }

        speed += ThreadLocalRandom.current().nextDouble(-0.05, 0.05); // 速度微调，打破线性感
        speed = Math.max(0.01, speed);
        
        float yawChange = (float) MathHelper.clamp(yawDiff, -speed, speed);
        float pitchChange = (float) MathHelper.clamp(pitchDiff, -speed, speed);

        // GCD 修复 (关键：绕过 Grim Post-Simulation 检测)
        float sens = (float) (mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2);
        float gcd = sens * sens * sens * 8.0f * 0.15f;
        
        yawChange = Math.round(yawChange / gcd) * gcd;
        pitchChange = Math.round(pitchChange / gcd) * gcd;

        mc.player.setYaw(currentYaw + yawChange);
        mc.player.setPitch(currentPitch + pitchChange);
    }
}
