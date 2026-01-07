package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.SlowdownEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NoSlow extends Module {
    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.GrimBlink);
    public final BoolValue inventoryMove = new BoolValue("InventoryMove", "背包移动", true);
    public final BoolValue arrowMove = new BoolValue("ArrowMove", "箭头移动", false);

    private boolean blink;
    private final Queue<Packet<?>> packets = new LinkedBlockingQueue<>();

    public NoSlow() {
        super("NoSlow", "无减速", Category.Movement);
    }

    @Override
    protected void onEnable() {
        packets.clear();
        Sakura.EVENT_BUS.subscribe(this);
    }

    @Override
    protected void onDisable() {
        blink = false;
        blink();
        Sakura.EVENT_BUS.unsubscribe(this);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
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
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) return;

        boolean isBow = mc.player.getMainHandStack().getItem() instanceof BowItem || mc.player.getMainHandStack().getItem() instanceof CrossbowItem;
        boolean slowTick = mc.player.getItemUseTimeLeft() % 3 == 0;

        switch (mode.get()) {
            case Normal -> event.setSlowdown(false);

            case GrimTick -> {
                if (!isBow) {
                    if (slowTick) event.setSlowdown(false);
                }
            }

            case GrimBlink -> {
                if (!isBow) {
                    if (mc.player.getItemUseTime() > 0 && mc.player.getItemUseTime() < 12) {
                        if (slowTick) event.setSlowdown(false);
                    } else if (mc.player.getItemUseTime() >= 12) {
                        blink = true;
                        event.setSlowdown(false);
                    }
                }
            }
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkScreen() {
        return mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen
                || mc.currentScreen instanceof SignEditScreen || mc.currentScreen instanceof DeathScreen);
    }

    public enum Mode {
        GrimBlink, GrimTick, Normal
    }
}