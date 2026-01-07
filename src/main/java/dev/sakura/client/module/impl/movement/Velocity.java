package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.entity.BlockPushEvent;
import dev.sakura.client.events.entity.EntityPushEvent;
import dev.sakura.client.events.entity.EntityVelocityUpdateEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.mixin.accessor.IEntityVelocityUpdateS2CPacket;
import dev.sakura.client.mixin.accessor.IExplosionS2CPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.entity.EntityUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "反击退", Category.Movement);
    }

    public enum Mode {
        Custom, Grim, Wall
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Custom);
    private final NumberValue<Double> horizontal = new NumberValue<>("Horizontal", "水平", 0.0, 0.0, 100.0, 1.0, () -> mode.is(Mode.Custom));
    private final NumberValue<Double> vertical = new NumberValue<>("Vertical", "垂直", 0.0, 0.0, 100.0, 1.0, () -> mode.is(Mode.Custom));
    public final BoolValue flagInWall = new BoolValue("Flag In Wall", "墙内标记", false, () -> mode.is(Mode.Grim) || mode.is(Mode.Wall));
    public final BoolValue noExplosions = new BoolValue("No Explosions", "无爆炸", false);
    public final BoolValue pauseInLiquid = new BoolValue("Pause In Liquid", "液体中暂停", false);
    public final BoolValue waterPush = new BoolValue("No Water Push", "无水推", false);
    public final BoolValue entityPush = new BoolValue("No Entity Push", "无实体推", true);
    public final BoolValue blockPush = new BoolValue("No Block Push", "无方块推", true);
    public final BoolValue fishBob = new BoolValue("No Fish Bob", "无鱼漂", true);

    private final TimerUtil timer = new TimerUtil();
    private boolean flag;

    @Override
    public String getSuffix() {
        if (mode.is(Mode.Custom)) {
            return horizontal.get().intValue() + "%, " + vertical.get().intValue() + "%";
        }
        return mode.get().name();
    }

    @EventHandler
    private void onEntityPush(EntityPushEvent event) {
        if (entityPush.get()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPush(BlockPushEvent event) {
        if (blockPush.get()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPacketEvent(PacketEvent event) {
        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            timer.reset();
        }
    }

    @EventHandler
    public void onVelocity(EntityVelocityUpdateEvent event) {
        if (nullCheck()) return;

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (mode.is(Mode.Grim) || mode.is(Mode.Wall)) {
            if (!timer.passedMS(100)) {
                return;
            }
            if (mode.is(Mode.Wall) && !EntityUtil.isInsideBlock()) return;
            event.cancel();
            flag = true;
        }
    }

    @EventHandler
    public void onReceivePacket(PacketEvent event) {
        if (nullCheck() || event.getType() != EventType.RECEIVE) return;

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (fishBob.get()) {
            if (event.getPacket() instanceof EntityStatusS2CPacket packet && packet.getStatus() == 31 && packet.getEntity(mc.world) instanceof FishingBobberEntity fishHook) {
                if (fishHook.getHookedEntity() == mc.player) {
                    event.setCancelled(true);
                }
            }
        }

        if (mode.is(Mode.Grim) || mode.is(Mode.Wall)) {
            if (!timer.passedMS(100)) {
                return;
            }

            if (mode.is(Mode.Wall) && !EntityUtil.isInsideBlock()) return;

            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket explosion) {
                ((IExplosionS2CPacket) explosion).setPlayerKnockback(Optional.empty());
                flag = true;
            }
        } else {
            double h = horizontal.get() / 100;
            double v = vertical.get() / 100;
            if (event.getPacket() instanceof ExplosionS2CPacket) {
                IExplosionS2CPacket packet = (IExplosionS2CPacket) event.getPacket();

                if (packet.getPlayerKnockback().isPresent()) {
                    double x = packet.getPlayerKnockback().get().getX() * h;
                    double y = packet.getPlayerKnockback().get().getY() * v;
                    double z = packet.getPlayerKnockback().get().getZ() * h;

                    packet.setPlayerKnockback(Optional.of(new Vec3d(x, y, z)));
                }

                if (noExplosions.get()) event.cancel();
                return;
            }

            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
                if (packet.getEntityId() == mc.player.getId()) {
                    if (horizontal.get() == 0 && vertical.get() == 0) {
                        event.cancel();
                    } else {
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityX((int) (packet.getVelocityX() * h));
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityY((int) (packet.getVelocityY() * v));
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityZ((int) (packet.getVelocityZ() * h));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (flag) {
            if (timer.passedMS(100) && (flagInWall.get() || !EntityUtil.isInsideBlock())) {
                //Zenith.ROTATION.snapBack();
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK,
                        mc.player.isCrawling() ? mc.player.getBlockPos() : mc.player.getBlockPos().up(), Direction.DOWN));
                //mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, BlockPos.ofFloored(mc.player.getPos()), mc.player.getHorizontalFacing().getOpposite()));
            }
            flag = false;
        }
    }
}
