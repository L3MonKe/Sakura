package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.GameJoinEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.ICloseHandledScreenC2SPacket;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.network.blockage.block.BlockHolder;
import dev.sakura.client.utils.network.blockage.impl.InboundNetworkBlockage;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue logging = new BoolValue("Logging", "日志", false);
    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue aca = new BoolValue("ACA", "打死ACA", true);
    private final BoolValue grim = new BoolValue("Grim", "Grim Disabler", true);
    private final BoolValue grimBadPackets = new BoolValue("Grim BadPackets", "Grim烂包", true, grim::get);
    private final BoolValue grimOffset = new BoolValue("Grim Offset", "Grim偏移", true, grim::get);

    private final BoolValue watchdog = new BoolValue("Watchdog", "Watchdog", false);
//    private final BoolValue watchdogInvMove = new BoolValue("Inventory Move", "背包移动", true, watchdog::get);
    private final BoolValue cubecraft = new BoolValue("Cubecraft", "Cubecraft", false);

    // Watchdog Queue (Outbound)
    private final Queue<Packet<?>> packetQueue = new ConcurrentLinkedQueue<>();
    private boolean shouldBlink;

    // Cubecraft BlockHolder (Inbound)
    private final BlockHolder blockHolder = new BlockHolder(InboundNetworkBlockage.get());
    private final TimerUtil timer = new TimerUtil();

    private float lastYaw;
    private float lastPitch;

    @Override
    public void onEnable() {
        if (mc.player != null) {
            lastYaw = mc.player.getYaw();
            lastPitch = mc.player.getPitch();
        }
        timer.reset();
        shouldBlink = false;
        packetQueue.clear();
        blockHolder.release();
    }

    @Override
    public void onDisable() {
        releasePackets();
        blockHolder.release();
    }

    @EventHandler
    public void onJoin(GameJoinEvent event) {
        timer.reset();
        shouldBlink = false;
        packetQueue.clear();
        blockHolder.release();
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        Packet<?> packet = event.getPacket();

        // RECEIVE Logic
        if (event.getType() == EventType.RECEIVE) {
            if (cubecraft.get()) {
                if (packet instanceof KeepAliveS2CPacket) {
                    event.cancel();
                } else if (packet instanceof PlayerPositionLookS2CPacket) {
                    blockHolder.release();
                    timer.reset();
                }
            }
            return;
        }

        // SEND Logic
        if (event.getType() != EventType.SEND) return;

        if (cubecraft.get()) {
            if (timer.passedMillise(3000)) {
                if (packet instanceof PlayerMoveC2SPacket movePacket && movePacket.changesPosition()) {
                    IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                    accessor.setY(accessor.getY() + 11);
                }
            }
        }

        // Watchdog Logic
//        if (watchdog.get()) {
//            if (packet instanceof ClickSlotC2SPacket clickSlot) {
//                if (watchdogInvMove.get()) {
//                    boolean allowedAction = clickSlot.actionType() == SlotActionType.QUICK_MOVE
//                            || clickSlot.actionType() == SlotActionType.SWAP
//                            || clickSlot.actionType() == SlotActionType.THROW;
//
//                    if (clickSlot.syncId() == mc.player.playerScreenHandler.syncId && allowedAction) {
//                        mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(clickSlot.syncId()));
//                    } else {
//                        shouldBlink = true;
//                    }
//                }
//            } else if (packet instanceof CloseHandledScreenC2SPacket closeScreen) {
//                if (watchdogInvMove.get() && ((ICloseHandledScreenC2SPacket) closeScreen).getSyncId() == mc.player.playerScreenHandler.syncId) {
//                    shouldBlink = false;
//                }
//            }
//
//            if (shouldBlink) {
//                if (!(packet instanceof ClickSlotC2SPacket ||
//                        packet instanceof CloseHandledScreenC2SPacket ||
//                        packet instanceof CommonPongC2SPacket ||
//                        packet instanceof KeepAliveC2SPacket)) {
//                    event.cancel();
//                    packetQueue.add(packet);
//                }
//            }
//        }

        if (aca.get()) {
            if (event.getPacket() instanceof CustomPayloadC2SPacket customPacket) {
                CustomPayload payload = customPacket.payload();
                Identifier id = payload.getId().id();
                String channelStr = id.toString();

                if (channelStr.contains("schematica") || channelStr.contains("vape") ||
                        channelStr.contains("wdl") || channelStr.contains("labymod") ||
                        channelStr.contains("lolimahcker") || channelStr.contains("5zig")) {
                    event.cancel();
                    log("Blocked mod channel: " + channelStr);
                    return;
                }

                if (payload instanceof BrandCustomPayload) {
                    event.cancel();
                    mc.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new BrandCustomPayload("vanilla")));
                    log("Spoofed client brand to vanilla");
                    return;
                }
            }

            if (event.getPacket() instanceof ChatMessageC2SPacket) {
                boolean sprinting = mc.player.isSprinting();
                boolean sneaking = mc.player.isSneaking();

                if (sprinting) {
                    mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                }
                if (sneaking) {
                    PlayerInput currentInput = mc.player.input.playerInput;
                    PlayerInput newInput = new PlayerInput(
                            currentInput.forward(),
                            currentInput.backward(),
                            currentInput.left(),
                            currentInput.right(),
                            currentInput.jump(),
                            false,
                            currentInput.sprint()
                    );
                    mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(newInput));
                }
            }

            if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket && movePacket.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                float yaw = accessor.getYaw();
                float pitch = accessor.getPitch();

                if (yaw == lastYaw) {
                    yaw += (float) ((Math.random() - 0.5) * 0.001);
                }
                if (pitch == lastPitch) {
                    pitch += (float) ((Math.random() - 0.5) * 0.001);
                }

                float deltaYaw = Math.abs(yaw - lastYaw);
                float deltaPitch = Math.abs(pitch - lastPitch);

                if (deltaPitch > 1.0f && deltaYaw < 0.0001f) {
                    yaw += 0.0002f;
                }
                if (deltaYaw > 1.0f && deltaPitch < 0.0001f) {
                    pitch += 0.0002f;
                }

                yaw += (float) ((Math.random() - 0.5) * 0.0001);
                pitch += (float) ((Math.random() - 0.5) * 0.0001);

                if (pitch > 90.0f) pitch = 90.0f;
                else if (pitch < -90.0f) pitch = -90.0f;

                accessor.setYaw(yaw);
                accessor.setPitch(pitch);

                lastYaw = yaw;
                lastPitch = pitch;
            }
        }

        if (disAim360.get() || grim.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket && movePacket.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    log("Disabled aim 360");
                }
            }
        }

        if (grim.get()) {
            if (grimOffset.get()) {
                if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket && movePacket.changesPosition()) {
                    IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                    accessor.setY(accessor.getY() + 1E-10); // Tiny offset to confuse 0.03 checks
                } else if (event.getPacket() instanceof VehicleMoveC2SPacket movePacket) {
                    Vec3d pos = movePacket.position();
                    Vec3d newPos = pos.add(0, 1E-10, 0);
                    event.setPacket(new VehicleMoveC2SPacket(newPos, movePacket.yaw(), movePacket.pitch(), movePacket.onGround()));
                }
            }
        }
    }

    @EventHandler
    public void onUpdate(PlayerTickEvent e) {
        if (cubecraft.get()) {
            if (timer.passedMillise(200)) {
                blockHolder.block(p -> p, p -> p instanceof CommonPingS2CPacket);
            } else {
                blockHolder.release();
            }
        }

//        if (watchdog.get()) {
//            if (watchdogInvMove.get()) {
//                if (mc.currentScreen == null) {
//                    shouldBlink = false;
//                }
//                if (!shouldBlink) {
//                    releasePackets();
//                }
//            }
//        }

        if (grim.get() && grimBadPackets.get()) {
            if (mc.player.getAbilities().allowFlying) {
                PlayerAbilities ab = new PlayerAbilities();
                ab.flying = Math.random() > 0.5; // Randomly toggle flying status
                ab.allowFlying = true;
                PacketUtil.sendPacketNoEvent(new UpdatePlayerAbilitiesC2SPacket(ab));
            }
        }
    }

    private void releasePackets() {
        while (!packetQueue.isEmpty()) {
            PacketUtil.sendPacketNoEvent(packetQueue.poll());
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.clientMessage(message);
            //NotificationManager.send(message);
        }
    }
}
