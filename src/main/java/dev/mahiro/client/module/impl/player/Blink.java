package dev.mahiro.client.module.impl.player;

import com.mojang.authlib.GameProfile;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.GameJoinEvent;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.*;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Blink extends Module {
    private final CopyOnWriteArrayList<Packet<?>> packetsList = new CopyOnWriteArrayList<>();
    private OtherClientPlayerEntity fakePlayer;
    private boolean blinking;

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue onlyMove = new BoolValue("OnlyMove", "仅移动", true);
    private final BoolValue pulse = new BoolValue("Pulse", "脉冲", false);
    private final NumberValue<Double> factor = new NumberValue<>("Factor", "因子", 1.0, 0.0, 10.0, 0.1, pulse::get);

    public Blink() {
        super("Blink", "瞬移", Category.Player);
    }

    @Override
    protected void onEnable() {
        packetsList.clear();
        if (nullCheck()) {
            setState(false);
            return;
        }
        if (!render.get()) return;
        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("11451466-6666-6666-6666-666666666601"), mc.player.getName().getString()));
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        fakePlayer.getInventory().clone(mc.player.getInventory());
        mc.world.addEntity(fakePlayer);
    }

    @Override
    protected void onDisable() {
        if (nullCheck()) {
            packetsList.clear();
            return;
        }
        removeFakePlayer();
        for (Packet<?> packet : packetsList) {
            mc.getNetworkHandler().sendPacket(packet);
        }
        packetsList.clear();
    }

    private void removeFakePlayer() {
        if (fakePlayer != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }

    @EventHandler
    private void onTick(TickEvent event) {
        if (nullCheck()) return;
        if (mc.player.isDead()) {
            packetsList.clear();
            setState(false);
            return;
        }
        setSuffix(String.valueOf(packetsList.size()));
        if (pulse.get() && packetsList.size() > factor.get() * 10.0) {
            blinking = true;
            for (Packet<?> packet : packetsList) {
                mc.getNetworkHandler().sendPacket(packet);
            }
            packetsList.clear();
            if (fakePlayer != null) {
                fakePlayer.copyPositionAndRotation(mc.player);
                fakePlayer.setHeadYaw(mc.player.headYaw);
                fakePlayer.setBodyYaw(mc.player.bodyYaw);
            }
            blinking = false;
        }
    }

    @EventHandler
    private void onGameJoin(GameJoinEvent event) {
        if (isEnabled()) {
            packetsList.clear();
            setState(false);
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (event.getType() != EventType.PRE || blinking) return;
        Packet<?> packet = event.getPacket();
        if (packet instanceof ChatMessageC2SPacket
                || packet instanceof RequestCommandCompletionsC2SPacket
                || packet instanceof CommandExecutionC2SPacket
                || packet instanceof TeleportConfirmC2SPacket
                || packet instanceof KeepAliveC2SPacket
                || packet instanceof AdvancementTabC2SPacket
                || packet instanceof ClientStatusC2SPacket
                || packet instanceof ClickSlotC2SPacket) {
            return;
        }
        boolean shouldCancel = packet instanceof PlayerMoveC2SPacket;
        if (!onlyMove.get()) {
            shouldCancel |= packet instanceof PlayerActionC2SPacket
                    || packet instanceof ClientCommandC2SPacket
                    || packet instanceof HandSwingC2SPacket
                    || packet instanceof PlayerInteractEntityC2SPacket
                    || packet instanceof PlayerInteractBlockC2SPacket
                    || packet instanceof PlayerInteractItemC2SPacket;
        }
        if (shouldCancel) {
            event.cancel();
            packetsList.add(packet);
        }
    }
}