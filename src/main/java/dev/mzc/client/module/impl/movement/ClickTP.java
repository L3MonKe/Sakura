package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.input.MouseButtonEvent;
import dev.mzc.client.events.misc.KeyAction;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.render.RenderUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ClickTP extends Module {

    private final NumberValue<Double> maxDistance = new NumberValue<>("MaxDistance", "最大距离", 100.0, 1.0, 500.0, 1.0);
    private final BoolValue hold = new BoolValue("Hold", "长按连续触发", true);
    private final BoolValue animate = new BoolValue("Animate", "动画效果", true);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "动画速度", 5.0, 0.5, 20.0, 0.5, () -> animate.get());

    private Vec3d cameraPos;

    public ClickTP() {
        super("ClickTP", "点击传送", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @Override
    public void onDisable() {
        cameraPos = null;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        if (hold.get() && mc.options.useKey.isPressed() && mc.currentScreen == null) {
            teleport();
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (animate.get() && cameraPos != null) {
            Vec3d target = mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0);
            Vec3d currentPos = cameraPos;
            double distance = currentPos.distanceTo(target);
            
            // Speed calculation: convert blocks/tick to blocks/frame approximately
            double moveSpeed = speed.get() * 20 * RenderUtil.deltaTime();

            if (distance <= moveSpeed || distance < 0.1) {
                cameraPos = null;
            } else {
                Vec3d direction = target.subtract(currentPos).normalize();
                cameraPos = currentPos.add(direction.multiply(moveSpeed));
            }
        }
    }

    @EventHandler
    public void onMouseButton(MouseButtonEvent event) {
        if (hold.get()) return; // Let onTick handle it if hold is enabled
        if (event.getAction() != KeyAction.Press || event.getButton() != GLFW.GLFW_MOUSE_BUTTON_2) return;
        if (mc.player == null || mc.currentScreen != null) return;

        teleport();
    }

    private void teleport() {
        HitResult hitResult = mc.player.raycast(maxDistance.get(), mc.getRenderTickCounter().getTickDelta(true), false);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5;

            // Anti-stuck: finding valid position upwards
            double maxY = mc.world.getTopYInclusive();
            while (y <= maxY) {
                Vec3d candidatePos = new Vec3d(x, y, z);
                if (!invalid(candidatePos)) {
                    if (animate.get()) {
                        // Start animation from current camera position if not already animating
                        if (cameraPos == null) {
                            cameraPos = mc.gameRenderer.getCamera().getPos();
                        }
                    }
                    // Teleport instantly
                    mc.player.setPosition(x, y, z);
                    mc.player.setVelocity(Vec3d.ZERO);
                    return;
                }
                y++;
            }
        }
    }

    private boolean invalid(Vec3d pos) {
        Box box = mc.player.getDimensions(mc.player.getPose()).getBoxAt(pos);
        return !mc.world.isSpaceEmpty(mc.player, box);
    }
    
    public boolean shouldModifyCamera() {
        return isEnabled() && animate.get() && cameraPos != null;
    }
    
    public Vec3d getCameraPos() {
        return cameraPos;
    }
}
