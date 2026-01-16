package com.zeta.client.module.impl.movement;

import com.zeta.client.events.EventType;
import com.zeta.client.events.client.TickEvent;
import com.zeta.client.events.input.MoveInputEvent;
import com.zeta.client.events.packet.PacketEvent;
import com.zeta.client.events.player.SlowdownEvent;
import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.values.impl.BoolValue;
import com.zeta.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.*;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "无减速", Category.Movement);
    }

    public enum Mode {
        Cancel("Cancel"),
        GrimBlink("GrimBlink"),
        GrimTick("GrimTick"),
        Heypixel2_3("Heypixel 2/3"),
        Grim50("Grim50%"),
        Grim1_3("Grim 1/3"),
        Jump("Jump");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Grim50);
    public final BoolValue inventoryMove = new BoolValue("InventoryMove", "背包移动", false);
    public final BoolValue arrowMove = new BoolValue("ArrowMove", "箭头移动", false);
    public final BoolValue food = new BoolValue("Food", "食物", true, this::isBjdMode);
    public final BoolValue bow = new BoolValue("Bow", "弓", true, this::isBjdMode);
    public final BoolValue crossbow = new BoolValue("Crossbow", "弩", true, this::isBjdMode);

    private boolean blink;
    private final Queue<Packet<?>> packets = new LinkedBlockingQueue<>();
    private int onGroundTick;

    private boolean isBjdMode() {
        return mode.is(Mode.Heypixel2_3) || mode.is(Mode.Grim50) || mode.is(Mode.Grim1_3) || mode.is(Mode.Jump);
    }

    @Override
    protected void onEnable() {
        packets.clear();
        onGroundTick = 0;
    }

    @Override
    protected void onDisable() {
        blink = false;
        onGroundTick = 0;
        blink();
    }

    @Override
    public String getSuffix() {
        return mode.get().displayName;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (inventoryMove.get() && checkScreen()) {
            final long handle = mc.getWindow().getHandle();
            KeyBinding[] keys = new KeyBinding[]{mc.options.jumpKey, mc.options.forwardKey, mc.options.backKey, mc.options.rightKey, mc.options.leftKey};
            for (KeyBinding binding : keys) {
                binding.setPressed(InputUtil.isKeyPressed(handle, InputUtil.fromTranslationKey(binding.getBoundKeyTranslationKey()).getCode()));
            }
            if (arrowMove.get()) {
                float yaw = mc.player.getYaw();
                float pitch = mc.player.getPitch();
                if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_UP)) {
                    pitch -= 3.0f;
                } else if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_DOWN)) {
                    pitch += 3.0f;
                } else if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_LEFT)) {
                    yaw -= 3.0f;
                } else if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_RIGHT)) {
                    yaw += 3.0f;
                }
                mc.player.setYaw(yaw);
                mc.player.setPitch(MathHelper.clamp(pitch, -90.0f, 90.0f));
            }
        }

        if (mc.player.isOnGround()) {
            onGroundTick++;
        } else {
            onGroundTick = 0;
        }
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) return;

        boolean isBowItem = mc.player.getMainHandStack().getItem() instanceof BowItem || mc.player.getMainHandStack().getItem() instanceof CrossbowItem;
        boolean slowTick = mc.player.getItemUseTimeLeft() % 3 == 0;

        if (isBjdMode()) {
            if (checkFood() && mc.player.getItemUseTimeLeft() > 30) return;
            if (!food.get() && checkFood()) return;
            if (!bow.get() && checkItem(Items.BOW)) return;
            if (!crossbow.get() && checkItem(Items.CROSSBOW)) return;
        }

        switch (mode.get()) {
            case Cancel -> event.setSlowdown(false);

            case GrimTick -> {
                if (!isBowItem) {
                    if (slowTick) event.setSlowdown(false);
                }
            }

            case GrimBlink -> {
                if (!isBowItem) {
                    if (mc.player.getItemUseTime() > 0 && mc.player.getItemUseTime() < 12) {
                        if (slowTick) event.setSlowdown(false);
                    } else if (mc.player.getItemUseTime() >= 12) {
                        blink = true;
                        event.setSlowdown(false);
                    }
                }
            }

            case Grim50 -> {
                if (mc.player.getItemUseTimeLeft() % 2 == 0 && mc.player.getItemUseTimeLeft() <= 30) {
                    event.setSlowdown(false);
                    if (!mc.player.isSprinting()) mc.player.setSprinting(true);
                }
            }

            case Grim1_3 -> {
                if (mc.player.getItemUseTimeLeft() % 3 == 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
                    event.setSlowdown(false);
                    if (!mc.player.isSprinting()) mc.player.setSprinting(true);
                }
            }

            case Heypixel2_3 -> {
                if (mc.player.getItemUseTimeLeft() % 3 != 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
                    event.setSlowdown(false);
                    if (!mc.player.isSprinting()) mc.player.setSprinting(true);
                }
            }

            case Jump -> {
                if (onGroundTick == 1 && mc.player.getItemUseTimeLeft() <= 30) {
                    event.setSlowdown(false);
                    if (!mc.player.isSprinting()) mc.player.setSprinting(true);
                }
            }
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;
        if (mode.get() != Mode.Jump) return;
        if (!mc.player.isOnGround()) return;
        if (!mc.player.isUsingItem()) return;
        if (event.getForward() == 0 && event.getStrafe() == 0) return;
        event.setJump(true);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.getNetworkHandler() == null) return;
        if (event.getType() != EventType.SEND) return;

        Packet<?> packet = event.getPacket();

        if (mode.get() == Mode.GrimBlink) {
            if (mc.player.getItemUseTime() > 0) {
                if (blink) {
                    if (packet instanceof PlayerMoveC2SPacket
                            || packet instanceof UpdateSelectedSlotC2SPacket
                            || packet instanceof HandSwingC2SPacket
                            || packet instanceof PlayerInteractItemC2SPacket
                            || packet instanceof CommonPongC2SPacket
                            || packet instanceof ClientCommandC2SPacket
                            || packet instanceof PlayerInteractBlockC2SPacket) {
                        event.cancel();
                        packets.add(packet);
                    }
                }
            } else {
                if (blink) {
                    blink = false;
                    blink();
                }
            }
        }
    }

    private void blink() {
        if (mc.getNetworkHandler() == null) return;

        try {
            while (!packets.isEmpty()) {
                Packet<?> p = packets.poll();
                mc.getNetworkHandler().sendPacket(p);
            }
        } catch (Exception ignored) {
        }
    }

    public boolean checkScreen() {
        return mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen
                || mc.currentScreen instanceof SignEditScreen || mc.currentScreen instanceof DeathScreen);
    }

    private boolean checkFood() {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(Items.GOLDEN_APPLE)
                || offhandItem.isOf(Items.GOLDEN_APPLE)
                || mainHandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || offhandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || mainHandItem.isOf(Items.POTION)
                || offhandItem.isOf(Items.POTION);
    }

    private boolean checkItem(Item item) {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(item) || offhandItem.isOf(item);
    }
}
