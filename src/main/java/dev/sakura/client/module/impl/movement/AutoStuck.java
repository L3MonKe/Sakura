package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.dimension.DimensionType;

public class AutoStuck extends Module {
    public AutoStuck() {
        super("AutoStuck", "自动卡空", Category.Movement);
    }

    private final NumberValue<Double> fallDistance = new NumberValue<>("Fall Distance", "下坠距离", 10.0, 3.0, 15.0, 0.1);

    private final TimerUtil timer = new TimerUtil();

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.PRE) return;

        Stuck stuck = Sakura.MODULES.getModule(Stuck.class);

        if (stuck.isEnabled()) {
            timer.reset();
        }

        int pearlSlot = -1;
        for (int i = 0; i < 9; i++) {
            if (!mc.player.getInventory().getStack(i).isEmpty() && mc.player.getInventory().getStack(i).getItem() == Items.ENDER_PEARL) {
                pearlSlot = i;
                break;
            }
        }

        boolean shouldTrigger = ((pearlSlot != -1 && mc.player.fallDistance > fallDistance.get()) || (mc.player.getY() + mc.player.getVelocity().y < -50.0)) && isOverVoid() && !mc.player.isOnGround() && timer.delay(1000.0);
        if (shouldTrigger && !stuck.isEnabled()) {
            stuck.toggle();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            timer.reset();
        }
    }

    private boolean isOverVoid() {
        Vec3d start = mc.player.getEntityPos();
        Vec3d end = new Vec3d(start.x, DimensionType.MIN_HEIGHT - 2.0, start.z);
        HitResult hit = mc.world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player));
        return hit.getType() == HitResult.Type.MISS;
    }
}
