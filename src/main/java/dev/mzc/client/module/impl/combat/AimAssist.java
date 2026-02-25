package dev.mzc.client.module.impl.combat;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.Friend;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.rotation.RotationUtil;
import dev.mzc.client.utils.vector.Rotation;
import dev.mzc.client.utils.vector.Vector3d;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AimAssist extends Module {

    public enum TargetMode {
        Distance("距离近"),
        Health("血量低"),
        HealthPercentage("百分比血量低");

        private final String cnName;
        TargetMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<TargetMode> targetMode = new EnumValue<>("TargetMode", "瞄准模式", TargetMode.Distance);
    private final NumberValue<Double> range = new NumberValue<>("Range", "距离", 4.0, 1.0, 8.0, 0.1);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1);
    private final NumberValue<Double> fov = new NumberValue<>("FOV", "视角范围", 60.0, 10.0, 360.0, 1.0);
    private final BoolValue clickOnly = new BoolValue("ClickOnly", "仅点击时", true);
    private final BoolValue weaponOnly = new BoolValue("WeaponOnly", "仅持武器", true);
    private final BoolValue ignoreTeam = new BoolValue("IgnoreTeam", "忽略队友", true);
    private final BoolValue vertical = new BoolValue("Vertical", "垂直瞄准", false);
    private final BoolValue bodyAim = new BoolValue("BodyAim", "瞄准身体", true);
    private final NumberValue<Double> jitter = new NumberValue<>("Jitter", "抖动强度", 0.5, 0.0, 5.0, 0.1);
    private final BoolValue dynamicSpeed = new BoolValue("DynamicSpeed", "速度变换", true);
    private final NumberValue<Double> farBoost = new NumberValue<>("FarBoost", "远距离加速%", 20.0, 0.0, 100.0, 1.0, dynamicSpeed::get);
    private final NumberValue<Double> farBoostThreshold = new NumberValue<>("FarBoostThreshold", "加速距离阈值", 5.0, 1.0, 20.0, 0.5, dynamicSpeed::get);
    private final NumberValue<Double> nearReduction = new NumberValue<>("NearReduction", "近距离减速%", 15.0, 0.0, 100.0, 1.0, dynamicSpeed::get);
    private final NumberValue<Double> nearReductionThreshold = new NumberValue<>("NearReductionThreshold", "减速距离阈值", 2.0, 0.1, 10.0, 0.5, dynamicSpeed::get);

    private Entity target;

    private double currentSpeedFactor = 0.0;
    private boolean lockedOnTarget = false;

    public AimAssist() {
        super("AimAssist", "自动瞄准", Category.Combat);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        target = findTarget();
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (clickOnly.get() && !mc.options.attackKey.isPressed()) return;
        if (weaponOnly.get() && !isHoldingWeapon()) return;

        if (target == null) {
            currentSpeedFactor = Math.max(0.0, currentSpeedFactor - 0.15);
            lockedOnTarget = false;
            return;
        }
        if (!isValid(target) || mc.player.distanceTo(target) > range.get()) {
            target = null;
            currentSpeedFactor = Math.max(0.0, currentSpeedFactor - 0.15);
            lockedOnTarget = false;
            return;
        }

        // Calculate rotation to target center (body center)
        Rotation targetRotation = getTargetRotation(target);
        if (targetRotation == null) return;

        boolean insideInner = isWithinCenterThreshold(targetRotation, 15.0);
        boolean insideOuter = isWithinCenterThreshold(targetRotation, 30.0);

        if (!lockedOnTarget && insideInner) {
            lockedOnTarget = true;
        } else if (lockedOnTarget && !insideOuter) {
            lockedOnTarget = false;
        }

        if (lockedOnTarget) {
            currentSpeedFactor = Math.max(0.0, currentSpeedFactor - 0.12);
            if (jitter.get() > 0) {
                applyJitter(event.getTickDelta());
            }
            return;
        }

        double targetSpeedFactor = insideInner ? 0.0 : 1.0;

        double accel = 0.12;
        if (targetSpeedFactor > currentSpeedFactor) {
            currentSpeedFactor = Math.min(targetSpeedFactor, currentSpeedFactor + accel);
        } else {
            currentSpeedFactor = Math.max(targetSpeedFactor, currentSpeedFactor - accel);
        }

        // Smoothly rotate towards target
        smoothAim(targetRotation, event.getTickDelta());
    }

    private boolean isWithinCenterThreshold(Rotation centerRotation, double pixelThreshold) {
        float currentYaw = mc.player.getYaw();
        float currentPitch = mc.player.getPitch();

        float yawDiff = Math.abs(MathHelper.wrapDegrees(centerRotation.yaw - currentYaw));
        float pitchDiff = Math.abs(centerRotation.pitch - currentPitch);

        // Approximate degrees per pixel based on vertical FOV
        double fov = mc.options.getFov().getValue();
        double height = mc.getWindow().getScaledHeight();
        double degreesPerPixel = fov / height;

        double threshold = pixelThreshold * degreesPerPixel;

        if (!vertical.get()) {
            // If vertical aim is off, we only care about the horizontal 3px strip.
            // But we must also ensure we are actually looking at the entity's hitbox vertically,
            // otherwise we would snap to people far above/below us horizontally.
            if (mc.crosshairTarget instanceof EntityHitResult entityHit && entityHit.getEntity() == target) {
                return yawDiff <= threshold;
            }
            // If not even looking at the entity hitbox, don't stop.
            return false;
        }

        // If vertical aim is on, use the 3x3 pixel square.
        return yawDiff <= threshold && pitchDiff <= threshold;
    }

    private Entity findTarget() {
        List<Entity> candidates = StreamSupport.stream(mc.world.getEntities().spliterator(), false)
                .filter(e -> e instanceof LivingEntity)
                .filter(e -> e != mc.player)
                .filter(Entity::isAlive)
                .filter(e -> mc.player.distanceTo(e) <= range.get())
                .filter(this::isValid)
                .filter(e -> getAngleToEntity(e) <= fov.get() / 2.0)
                .collect(Collectors.toList());

        if (candidates.isEmpty()) return null;

        // 根据模式排序
        switch (targetMode.get()) {
            case Distance:
                candidates.sort(Comparator.comparingDouble(e -> mc.player.distanceTo(e)));
                break;
            case Health:
                candidates.sort(Comparator.comparingDouble(e -> ((LivingEntity) e).getHealth()));
                break;
            case HealthPercentage:
                candidates.sort(Comparator.comparingDouble(e -> {
                    LivingEntity le = (LivingEntity) e;
                    return le.getHealth() / le.getMaxHealth();
                }));
                break;
        }

        return candidates.get(0);
    }

    private boolean isValid(Entity entity) {
        if (ignoreTeam.get() && isTeammate(entity)) return false;
        if (Sakura.MODULES.getModule(Friend.class).isFriend(entity.getName().getString())) return false;
        if (entity instanceof PlayerEntity) return true;
        return false;
    }

    private boolean isTeammate(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return false;
        if (mc.player == null) return false;

        // Leather armor color check (simple team check used in other modules)
        int myColor = getLeatherArmorColor(mc.player);
        int theirColor = getLeatherArmorColor(player);

        if (myColor != -1 && theirColor != -1 && myColor == theirColor) return true;

        // Scoreboard team check
        if (mc.player.getScoreboardTeam() != null && player.getScoreboardTeam() != null) {
            return mc.player.getScoreboardTeam().isEqual(player.getScoreboardTeam());
        }

        return false;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }

    private boolean isHoldingWeapon() {
        ItemStack stack = mc.player.getMainHandStack();
        return stack.getItem() instanceof SwordItem || 
               stack.getItem() instanceof AxeItem || 
               stack.getItem() instanceof MaceItem ||
               stack.getItem() instanceof TridentItem;
    }

    private double getAngleToEntity(Entity entity) {
        Rotation rot = RotationUtil.calculate(entity);
        float yawDiff = Math.abs(MathHelper.wrapDegrees(rot.yaw - mc.player.getYaw()));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(rot.pitch - mc.player.getPitch()));
        return yawDiff + pitchDiff;
    }

    private Rotation getTargetRotation(Entity target) {
        if (bodyAim.get()) {
            // Aim at the center of the bounding box
            double centerX = (target.getBoundingBox().minX + target.getBoundingBox().maxX) / 2.0;
            double centerY = (target.getBoundingBox().minY + target.getBoundingBox().maxY) / 2.0;
            double centerZ = (target.getBoundingBox().minZ + target.getBoundingBox().maxZ) / 2.0;
            return RotationUtil.calculate(new Vector3d(centerX, centerY, centerZ));
        } else {
            // Default to head/eye level (provided by RotationUtil.calculate)
            return RotationUtil.calculate(target);
        }
    }

    private void smoothAim(Rotation targetRotation, float tickDelta) {
        float currentYaw = mc.player.getYaw();
        float currentPitch = mc.player.getPitch();

        float targetYaw = targetRotation.yaw;
        float targetPitch = targetRotation.pitch;

        // Vertical check
        if (!vertical.get()) {
            targetPitch = currentPitch;
        }

        // Randomization for Grim bypass (noise)
        if (jitter.get() > 0) {
            targetYaw += (float) (ThreadLocalRandom.current().nextGaussian() * jitter.get());
            targetPitch += (float) (ThreadLocalRandom.current().nextGaussian() * jitter.get());
        }

        // Smooth logic
        // Calculate difference
        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = targetPitch - currentPitch;

        // Apply speed limit
        // Base speed logic: speed.get() is roughly "degrees per tick" in old logic.
        // We want to apply it per frame.
        // If we have 60 FPS, tickDelta is small? No, tickDelta is partial tick.
        // We should treat speed as "speed per tick", so per frame we apply speed * (time passed since last frame in ticks).
        // But simpler: just divide by expected FPS or use a multiplier.
        // Actually, just applying a fraction of the speed per frame works well.
        // Let's use a lower multiplier for Render loop since it runs faster than Tick.
        
        double aimSpeed = speed.get() * 0.15 * currentSpeedFactor;
        
        if (dynamicSpeed.get()) {
            // Distance in degrees
            double totalDiff = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);
            
            // If far away, boost speed
            if (totalDiff > farBoostThreshold.get()) {
                aimSpeed *= (1.0 + farBoost.get() / 100.0);
            } 
            // If close, reduce speed
            else if (totalDiff < nearReductionThreshold.get()) {
                aimSpeed *= (1.0 - nearReduction.get() / 100.0);
            }
        }

        aimSpeed += ThreadLocalRandom.current().nextDouble(-0.1, 0.1);
        aimSpeed = Math.max(0.01, aimSpeed);

        float yawChange = (float) MathHelper.clamp(yawDiff, -aimSpeed, aimSpeed);
        float pitchChange = (float) MathHelper.clamp(pitchDiff, -aimSpeed, aimSpeed);

        // Apply sensitivity patch (GCD fix) to make it look like valid mouse input
        float sens = (float) (mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2);
        float gcd = sens * sens * sens * 8.0f * 0.15f;
        
        // Snap changes to GCD
        yawChange = Math.round(yawChange / gcd) * gcd;
        pitchChange = Math.round(pitchChange / gcd) * gcd;

        float newYaw = currentYaw + yawChange;
        float newPitch = currentPitch + pitchChange;
        
        mc.player.setYaw(newYaw);
        mc.player.setPitch(newPitch);
    }

    private void applyJitter(float tickDelta) {
        float jitterVal = jitter.get().floatValue();
        if (jitterVal <= 0) return;

        float yawChange = (float) (ThreadLocalRandom.current().nextGaussian() * jitterVal * 0.05);
        float pitchChange = (float) (ThreadLocalRandom.current().nextGaussian() * jitterVal * 0.05);

        mc.player.setYaw(mc.player.getYaw() + yawChange);
        mc.player.setPitch(mc.player.getPitch() + pitchChange);
    }
}
