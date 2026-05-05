package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.player.SlowdownEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.concurrent.LinkedBlockingQueue;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "无减速", Category.Movement);
    }

    private enum Mode {
        Vanilla,
        Jump,
        GrimFull,
        Grim1_2,
        Grim1_3
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.GrimFull);
    private final BoolValue food = new BoolValue("Food", "食物", true);
    private final BoolValue bow = new BoolValue("Bow", "弓", true);
    private final BoolValue crossbow = new BoolValue("Crossbow", "弩", true);

    private int ticks;
    private int useDuration = 32;
    private int onGroundTick = 0;

    private boolean eating;

    private final LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue<>();

    @Override
    protected void onDisable() {
        flush();
        eating = false;
        ticks = 0;
        useDuration = 32;
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @EventHandler
    private void onSendPosition(MotionEvent event) {
        if (event.getType() != EventType.PRE) return;

        if (mc.player.isOnGround()) {
            onGroundTick++;
        } else {
            onGroundTick = 0;
        }

        if (!mode.is(Mode.GrimFull)) {
            return;
        }

        if (mc.player.age < 30) {
            return;
        }

        if (eating) {
            ticks++;
        }

        ItemStack useItem = mc.player.getActiveItem();
        UseAction anim = useItem.getUseAction();
        if (
                mc.player.isUsingItem()
                        && (anim == UseAction.EAT
                        || anim == UseAction.DRINK
                        || anim == UseAction.CROSSBOW)
                        && !eating
        ) {
            eating = true;
            ticks = 0;
            ItemStack activeItem = mc.player.getActiveItem();
            useDuration = activeItem.getMaxUseTime(mc.player);
        }

        if (eating) {
            if (ticks == 1) {
                PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            }
            if (ticks == 2) {
                Hand hand = mc.player.getActiveHand() == Hand.OFF_HAND
                        ? Hand.MAIN_HAND
                        : Hand.OFF_HAND;

                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(hand, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), mc.player.getYaw(), mc.player.getPitch()));
            }
            if (ticks > useDuration + 3) {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                mc.options.useKey.setPressed(false);
                ticks = 0;
                eating = false;
            }
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent event) {
        if (nullCheck() || mc.player.age < 30 || !eating || !mode.is(Mode.GrimFull)) return;

        if (event.getType() == EventType.RECEIVE) {
            Packet<?> packet = event.getPacket();

            if (packet instanceof PlayerPositionLookS2CPacket) {
                flush();
                return;
            }

            if (packet instanceof HealthUpdateS2CPacket
                    || packet instanceof GameMessageS2CPacket
                    || packet instanceof EntityPositionS2CPacket
                    || packet instanceof EntityS2CPacket
                    || packet instanceof EntityStatusS2CPacket
                    || packet instanceof EntitySpawnS2CPacket
                    || packet instanceof BlockUpdateS2CPacket
                    || packet instanceof BlockEventS2CPacket
            ) {
                return;
            }

            if (packet.getPacketType().side() == NetworkSide.CLIENTBOUND) {
                event.setCancelled(true);
                packets.add(packet);
            }
        }

        if (event.getType() == EventType.SEND && mode.is(Mode.GrimFull) && event.getPacket() instanceof PlayerActionC2SPacket packet && packet.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM) {
            eating = false;
            ticks = 0;
            flush();
            PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
        }
    }

    @EventHandler
    private void onSlowdown(SlowdownEvent event) {
        if (nullCheck() || mc.player.age < 30) return;

        if (!food.get() && mc.player.getActiveItem().contains(DataComponentTypes.FOOD)) return;
        if (!bow.get() && mc.player.getActiveItem().isOf(Items.BOW)) return;
        if (!crossbow.get() && mc.player.getActiveItem().isOf(Items.CROSSBOW)) return;

        switch (mode.get()) {
            case Vanilla -> cancel(event);
            case GrimFull -> grimFull(event);
            case Jump -> jump(event);
            case Grim1_2 -> grim50(event);
            case Grim1_3 -> grim33(event);
        }
    }

    @EventHandler
    private void onKeyboardInput(MoveInputEvent event) {
        if (mode.is(Mode.Jump) && mc.player.isOnGround() && mc.player.isUsingItem() && (event.getForward() != 0 || event.getStrafe() != 0)) {
            event.setJump(true);
        }
    }

    private void cancel(SlowdownEvent event) {
        event.setSlowdown(false);
    }

    private void grimFull(SlowdownEvent event) {
        UseAction anim = mc.player.getActiveItem().getUseAction();
        if (
                mc.player.isUsingItem()
                        && mc.player.getItemUseTimeLeft() < 30
                        && (anim == UseAction.EAT || anim == UseAction.DRINK)
        ) {
            event.setSlowdown(false);
            mc.player.setSprinting(true);
        }
    }

    private void jump(SlowdownEvent event) {
        if (onGroundTick == 1 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim50(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 2 == 0 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim33(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 3 == 0 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void flush() {
        while (!packets.isEmpty()) packets.poll().apply(mc.getNetworkHandler());
    }

}
