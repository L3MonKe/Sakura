package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.PacketUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stuck extends Module {
    public Stuck() {
        super("Stuck", "卡空", Category.Movement);
    }

    private int stage = 0;
    private Packet<?> packet;
    private float lastYaw;
    private float lastPitch;
    private boolean tryDisable = false;
    private final Queue<CommonPongC2SPacket> packets = new ConcurrentLinkedQueue<>();

    @Override
    public void onEnable() {
        stage = 0;
        packet = null;
        lastYaw = Managers.ROTATION.rotations.yaw;
        lastPitch = Managers.ROTATION.rotations.pitch;
        tryDisable = false;
    }

    @Override
    public void setState(boolean state) {
        if (mc.player != null) {
            if (state) {
                super.setState(true);
            } else if (this.stage == 3) {
                super.setState(false);
            } else {
                this.tryDisable = true;
            }
        }
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (nullCheck()) return;

        Module scaffold = Sakura.MODULES.getModule(Scaffold.class);
        if (scaffold.isEnabled()) {
            scaffold.toggle();
        } else {
            if (e.getType() == EventType.PRE) {
                mc.player.setVelocity(0.0, 0.0, 0.0);
                if (stage == 1) {
                    stage = 2;
                    float rotationYaw = mc.player.getYaw();
                    float rotationPitch = mc.player.getPitch();
                    if (shouldRotate() && (lastYaw != rotationYaw || lastPitch != rotationPitch)) {
                        PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(rotationYaw, rotationPitch, mc.player.isOnGround(), mc.player.horizontalCollision));

                        while (!packets.isEmpty()) {
                            PacketUtil.sendPacketNoEvent(packets.poll());
                        }

                        lastYaw = rotationYaw;
                        lastPitch = rotationPitch;
                    }

                    PacketUtil.sendPacketNoEvent(packet);
                }

                if (tryDisable) {
                    PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 1337.0, mc.player.getY(), mc.player.getZ() + 1337.0, mc.player.isOnGround(), mc.player.horizontalCollision));

                    while (!packets.isEmpty()) {
                        PacketUtil.sendPacketNoEvent(packets.poll());
                    }

                    this.tryDisable = false;
                }
            }
        }
    }

    private boolean shouldRotate() {
        if (packet instanceof PlayerInteractItemC2SPacket blockPlacement) {
            ItemStack item = mc.player.getStackInHand(blockPlacement.getHand());
            boolean isBowlFood = item.contains(DataComponentTypes.FOOD) && item.get(DataComponentTypes.USE_REMAINDER) != null && item.get(DataComponentTypes.USE_REMAINDER).convertInto().isOf(Items.BOWL);
            return !isBowlFood && !(item.getItem() instanceof BowItem);
        } else {
            return packet instanceof PlayerActionC2SPacket playerDigging && playerDigging.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM && mc.player.getActiveItem().getItem() instanceof BowItem;
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        event.setForward(0.0F);
        event.setStrafe(0.0F);
        event.setJump(false);
        event.setSneak(false);
    }

    @EventHandler
    public void onRespawnMotion(MotionEvent event) {
        if (event.getType() == EventType.PRE && mc.player.age <= 1) {
            stage = 3;
            packet = null;
            toggle();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            event.setCancelled(true);
        } else if (event.getPacket() instanceof CommonPongC2SPacket) {
            packets.offer((CommonPongC2SPacket) event.getPacket());
            event.setCancelled(true);
        } else if (event.getPacket() instanceof PlayerInteractItemC2SPacket || event.getPacket() instanceof PlayerActionC2SPacket) {
            packet = event.getPacket();
            stage = 1;
            event.setCancelled(true);
        } else if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            while (!packets.isEmpty()) {
                PacketUtil.sendPacketNoEvent(packets.poll());
            }

            stage = 3;
            toggle();
        }
    }
}
