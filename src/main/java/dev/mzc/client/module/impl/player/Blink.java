package dev.mzc.client.module.impl.player;

import com.mojang.authlib.GameProfile;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.client.GameJoinEvent;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
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
    private int timer;

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue autoDisable = new BoolValue("AutoDisable", "定时关闭", true);
    private final NumberValue<Integer> duration = new NumberValue<>("Duration", "持续时间", 40, 1, 100, 1, autoDisable::get);

    public Blink() {
        super("Blink", "瞬移", Category.Player);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        packetsList.clear();
        timer = 0;
        if (nullCheck()) {
            setState(false);
            return;
        }
        spawnFakePlayer();
    }

    @Override
    protected void onDisable() {
        if (nullCheck()) {
            packetsList.clear();
            return;
        }
        removeFakePlayer();
        sendPackets();
    }

    private void spawnFakePlayer() {
        if (!render.get()) return;
        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("11451466-6666-6666-6666-666666666601"), mc.player.getName().getString()));
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        fakePlayer.getInventory().clone(mc.player.getInventory());
        mc.world.addEntity(fakePlayer);
    }

    private void removeFakePlayer() {
        if (fakePlayer != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }

    private void sendPackets() {
        blinking = true;
        for (Packet<?> packet : packetsList) {
            mc.getNetworkHandler().sendPacket(packet);
        }
        packetsList.clear();
        blinking = false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (mc.player.isDead()) {
            packetsList.clear();
            setState(false);
            return;
        }
        
        setSuffix(String.valueOf(packetsList.size()));

        if (autoDisable.get()) {
            timer++;
            if (timer >= duration.get()) {
                setState(false);
            }
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
        if (event.getType() != EventType.SEND || blinking) return;

        Packet<?> packet = event.getPacket();
        
        if (packet instanceof ChatMessageC2SPacket
                || packet instanceof RequestCommandCompletionsC2SPacket
                || packet instanceof CommandExecutionC2SPacket
                || packet instanceof TeleportConfirmC2SPacket
                || packet instanceof KeepAliveC2SPacket
                || packet instanceof AdvancementTabC2SPacket
                || packet instanceof ClientStatusC2SPacket
                || packet instanceof ClickSlotC2SPacket
                || packet instanceof HandSwingC2SPacket) {
            return;
        }

        // Cancel all movement and interaction packets (equivalent to onlyMove=false behavior)
        boolean shouldCancel = packet instanceof PlayerMoveC2SPacket
                || packet instanceof PlayerActionC2SPacket
                || packet instanceof ClientCommandC2SPacket
                || packet instanceof PlayerInteractEntityC2SPacket
                || packet instanceof PlayerInteractBlockC2SPacket
                || packet instanceof PlayerInteractItemC2SPacket;

        if (shouldCancel) {
            event.cancel();
            packetsList.add(packet);
        }
    }
}
