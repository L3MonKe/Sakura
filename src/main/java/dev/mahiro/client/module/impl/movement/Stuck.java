package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.input.MoveInputEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.player.MotionEvent;
import dev.mahiro.client.events.player.PlayerTickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import meteordevelopment.orbit.EventHandler;
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
        this.stage = 0;
        this.packet = null;
        this.lastYaw = 0.0f;
        this.lastPitch = 0.0f;
        this.tryDisable = false;
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
        } else {
            super.setState(state);
        }
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (nullCheck()) return;

        Module scaffold = Mahiro.MODULES.getModule(Scaffold.class);
        if (scaffold.isEnabled()) {
            scaffold.toggle();
        } else {
            if (e.getType() == EventType.PRE) {
                mc.player.setVelocity(0.0, 0.0, 0.0);
                if (this.stage == 1) {
                    this.stage = 2;
                    float rotationYaw = mc.player.getYaw();
                    float rotationPitch = mc.player.getPitch();
                    if (this.shouldRotate() && (this.lastYaw != rotationYaw || this.lastPitch != rotationPitch)) {
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotationYaw, rotationPitch, mc.player.isOnGround(), mc.player.horizontalCollision));

                        while (!this.packets.isEmpty()) {
                            mc.getNetworkHandler().sendPacket(this.packets.poll());
                        }

                        lastYaw = rotationYaw;
                        lastPitch = rotationPitch;
                    }

                    mc.getNetworkHandler().sendPacket(packet);
                }

                if (tryDisable) {
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 1337.0, mc.player.getY(), mc.player.getZ() + 1337.0, mc.player.isOnGround(), mc.player.horizontalCollision));

                    while (!this.packets.isEmpty()) {
                        mc.getNetworkHandler().sendPacket(this.packets.poll());
                    }

                    this.tryDisable = false;
                    super.setState(false);
                }
            }
        }
    }

    private boolean shouldRotate() {
        if (this.packet instanceof PlayerInteractItemC2SPacket blockPlacement) {
            ItemStack item = mc.player.getStackInHand(blockPlacement.getHand());
            boolean isBowlFood = item.contains(DataComponentTypes.FOOD) && item.get(DataComponentTypes.USE_REMAINDER) != null && item.get(DataComponentTypes.USE_REMAINDER).convertInto().isOf(Items.BOWL);
            return !isBowlFood && !(item.getItem() instanceof BowItem);
        } else {
            return this.packet instanceof PlayerActionC2SPacket playerDigging && playerDigging.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM && mc.player.getActiveItem().getItem() instanceof BowItem;
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent e) {
        e.setForward(0.0F);
        e.setStrafe(0.0F);
        e.setJump(false);
        e.setSneak(false);
    }

    @EventHandler
    public void onRespawn(PlayerTickEvent event) {
        if (mc.player.age <= 1) {
            stage = 3;
            packet = null;
            toggle();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof PlayerMoveC2SPacket) {
            e.setCancelled(true);
        } else if (e.getPacket() instanceof CommonPongC2SPacket) {
            packets.offer((CommonPongC2SPacket) e.getPacket());
            e.setCancelled(true);
        } else if (e.getPacket() instanceof PlayerInteractItemC2SPacket || e.getPacket() instanceof PlayerActionC2SPacket) {
            packet = e.getPacket();
            stage = 1;
            e.setCancelled(true);
        } else if (e.getPacket() instanceof PlayerPositionLookS2CPacket) {
            while (!packets.isEmpty()) {
                mc.getNetworkHandler().sendPacket(packets.poll());
            }
            stage = 3;
            toggle();
        }
    }
}
