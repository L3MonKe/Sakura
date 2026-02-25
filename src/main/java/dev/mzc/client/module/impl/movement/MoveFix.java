package dev.mzc.client.module.impl.movement;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.input.MoveInputEvent;
import dev.mzc.client.events.player.JumpEvent;
import dev.mzc.client.events.player.TravelEvent;
import dev.mzc.client.events.player.UpdateVelocityEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MoveFix extends Module {
    public MoveFix() {
        super("MoveFix", "移动修复", Category.Movement);
    }

    public final EnumValue<UpdateMode> updateMode = new EnumValue<>("Update Mode", "模式", UpdateMode.UpdateMouse);
    public final BoolValue grim = new BoolValue("Grim", "Grim模式", true);
    private final BoolValue travel = new BoolValue("Travel", "何意味", false, grim::get);

    public enum UpdateMode {
        MovementPacket("数据包"),
        UpdateMouse("鼠标"),
        All("全部");

        private final String cnName;

        UpdateMode(String cnName) {
            this.cnName = cnName;
        }


    }

    public static float fixRotation;
    public static float fixPitch;
    private float prevYaw;
    private float prevPitch;

    @EventHandler
    public void onTravel(TravelEvent e) {
        if (!grim.get() || !travel.get()) return;

        if (mc.player.isRiding())
            return;

        if (e.isPre()) {
            prevYaw = mc.player.getYaw();
            prevPitch = mc.player.getPitch();
            mc.player.setYaw(fixRotation);
            mc.player.setPitch(fixPitch);
        } else {
            mc.player.setYaw(prevYaw);
            mc.player.setPitch(prevPitch);
        }
    }

    @EventHandler
    public void onJump(JumpEvent event) {
        if (!grim.get()) return;
        if (mc.player.isRiding())
            return;

        if (event.getType().equals(EventType.PRE)) {
            prevYaw = mc.player.getYaw();
            prevPitch = mc.player.getPitch();
            mc.player.setYaw(fixRotation);
            mc.player.setPitch(fixPitch);
        } else {
            mc.player.setYaw(prevYaw);
            mc.player.setPitch(prevPitch);
        }
    }

    @EventHandler
    public void onPlayerMove(UpdateVelocityEvent event) {
        if (!grim.get() || travel.get() || mc.player.isRiding()) return;

        event.cancel();
        event.setVelocity(movementInputToVelocity(event.getMovementInput(), event.getSpeed(), fixRotation));
    }

    @EventHandler(priority = -999)
    public void onMoveInput(MoveInputEvent event) {
        if (!grim.get()) return;
        if (mc.player.isRiding())
            return;

        float mF = event.getForward();
        float mS = event.getStrafe();
        float delta = (mc.player.getYaw() - fixRotation) * MathHelper.RADIANS_PER_DEGREE;
        float cos = MathHelper.cos(delta);
        float sin = MathHelper.sin(delta);
        event.setStrafe(mS * cos - mF * sin);
        event.setForward(mF * cos + mS * sin);

        // Force sprint update if moving and sprint key is held
        // This fixes the issue where rotated inputs result in low forward speed, disabling vanilla sprint
        if (mc.options.sprintKey.isPressed() && (mF != 0 || mS != 0)) {
            mc.player.setSprinting(true);
        }
    }

    private static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        double d = movementInput.lengthSquared();
        if (d < 1.0E-7) {
            return Vec3d.ZERO;
        } else {
            Vec3d vec3d = (d > 1.0 ? movementInput.normalize() : movementInput).multiply(speed);
            float f = MathHelper.sin(yaw * 0.017453292F);
            float g = MathHelper.cos(yaw * 0.017453292F);
            return new Vec3d(vec3d.x * (double) g - vec3d.z * (double) f, vec3d.y, vec3d.z * (double) g + vec3d.x * (double) f);
        }
    }

    public static boolean isActive() {
        return Sakura.MODULES.getModule(MoveFix.class).isEnabled();
    }
}
