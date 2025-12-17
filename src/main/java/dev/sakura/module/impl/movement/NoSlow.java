package dev.sakura.module.impl.movement;

import dev.sakura.Sakura;
import dev.sakura.events.client.TickEvent;
import dev.sakura.events.packet.PacketEvent;
import dev.sakura.events.player.SlowdownEvent;
import dev.sakura.events.type.EventType;
import dev.sakura.gui.clickgui.ClickGuiScreen;
import dev.sakura.manager.impl.RotationManager;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.utils.player.MovementUtils;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NoSlow extends Module {

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
    public final BoolValue invFix = new BoolValue("Rotate Fix", false, () -> mode.get() == Mode.Meow);
    public final BoolValue items = new BoolValue("Items", true);
    public final BoolValue guiMove = new BoolValue("Inv Move", true);
    public final BoolValue webs = new BoolValue("Webs", true);
    public final BoolValue crawling = new BoolValue("Crawling", false);
    public final BoolValue sneak = new BoolValue("Sneaking", false);

    public NoSlow() {
        super("NoSlow", Category.Movement);
    }

    public boolean cancelDisabler = false;

    private final Queue<Packet<?>> packets = new LinkedBlockingQueue<>();

    public void release() {
        if (mc.getNetworkHandler() == null) return;
        for (Packet<?> p : packets) {
            mc.getNetworkHandler().sendPacket(p);
        }
        packets.clear();
    }

    @Override
    protected void onEnable() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    @Override
    protected void onDisable() {
        Sakura.EVENT_BUS.unsubscribe(this);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (canNoSlow()) {
            event.setSlowdown(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        if (!guiMove.get()) return;

        if (mc.currentScreen != null) {
            if (canInvMove()) {
                for (KeyBinding k : new KeyBinding[]{mc.options.forwardKey, mc.options.backKey, mc.options.leftKey, mc.options.rightKey, mc.options.jumpKey, mc.options.sprintKey}) {
                    k.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(k.getBoundKeyTranslationKey()).getCode()));
                }
                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), 264)) { // Down
                    mc.player.setPitch(mc.player.getPitch() + 5);
                }
                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), 265)) { // Up
                    mc.player.setPitch(mc.player.getPitch() - 5);
                }
                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), 262)) { // Right
                    mc.player.setYaw(mc.player.getYaw() + 5);
                }
                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), 263)) { // Left
                    mc.player.setYaw(mc.player.getYaw() - 5);
                }
                if (mc.player.getPitch() > 90) {
                    mc.player.setYaw(90);
                }
                if (mc.player.getPitch() < -90) {
                    mc.player.setYaw(-90);
                }
            }
        }

        // Grim logic from onPlayerTick
        if (mode.get() == Mode.Grim) {
            if (mc.player.isUsingItem() && !mc.player.hasVehicle() && !mc.player.isGliding() && !mc.player.isSneaking()) {
                if (mc.player.getActiveHand() == Hand.MAIN_HAND) {
                    mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0, RotationManager.getYaw(), RotationManager.getPitch()));
                }
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (cancelDisabler) return;

        Packet<?> packet = event.getPacket();

        if (event.getType() == EventType.SEND) {
            switch (mode.get()) {
                case Strict:
                    if (items.get() && packet instanceof PlayerMoveC2SPacket movePacket) {
                        if (mc.player.isUsingItem() && movePacket.changesPosition()) {
                            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
                        }
                    } else if (guiMove.get() && packet instanceof ClickSlotC2SPacket && MovementUtils.isMoving()) {
                        doStrictPre();
                    }
                    break;
                case Meow:
                    break;
            }
        } else if (event.getType() == EventType.SENT) { // Post
            switch (mode.get()) {
                case Strict:
                    if (guiMove.get() && packet instanceof ClickSlotC2SPacket && MovementUtils.isMoving()) {
                        doStrictPost();
                    }
                    break;
                case Meow:
                    if (guiMove.get() && invFix.get()) {
                        if (packet instanceof ClickSlotC2SPacket clickPacket) {
                            if (clickPacket.getSyncId() != 0) return;
                            if (clickPacket.getActionType() != SlotActionType.PICKUP && clickPacket.getActionType() != SlotActionType.PICKUP_ALL && clickPacket.getActionType() != SlotActionType.QUICK_CRAFT) {
                                mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
                            }
                        }
                    }
                    break;
            }
        }
    }

    public boolean doStrictPre() {
        if (mc.player.isSneaking())
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));

        if (mc.player.isSprinting())
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));


        if (mc.player.isOnGround() && !mc.options.jumpKey.isPressed() && !mc.world.getBlockCollisions(mc.player, mc.player.getBoundingBox().offset(0.0, 0.0656, 0.0)).iterator().hasNext()) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.0656, mc.player.getZ(), false, mc.player.horizontalCollision));
            return true;
        }
        return false;
    }

    public void doStrictPost() {
        if (mc.player.isSneaking())
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));

        if (mc.player.isSprinting())
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
    }

    public boolean canNoSlow() {
        if (!isEnabled()) return false;

        if (!items.get())
            return false;

        if (mode.get() == Mode.Grim)
            if (mc.player.getActiveHand() == Hand.OFF_HAND)
                return false;

        if (mode.get() == Mode.Meow)
            return mc.player.getItemUseTimeLeft() < 5 || ((mc.player.getItemUseTime() > 1) && mc.player.getItemUseTime() % 2 != 0);

        return true;
    }

    public boolean canInvMove() {
        if (mc.currentScreen instanceof HandledScreen<?>)
            return true;

        if (mc.currentScreen instanceof ClickGuiScreen)
            return true;

        return false;
    }

    public enum Mode {
        Vanilla,
        Grim,
        Meow,
        Strict
    }
}
