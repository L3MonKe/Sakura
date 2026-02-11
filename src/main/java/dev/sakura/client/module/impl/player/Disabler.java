package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec3d;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue logging = new BoolValue("Logging", "日志", false);
    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    //by Gemini
    private final BoolValue aca = new BoolValue("ACA", "打死ACA", true);
    private final BoolValue grim = new BoolValue("Grim", "Grim Disabler", true);
    private final BoolValue grimBadPackets = new BoolValue("Grim BadPackets", "Grim烂包", true, grim::get);
    private final BoolValue grimOffset = new BoolValue("Grim Offset", "Grim偏移", true, grim::get);

    private float lastYaw;
    private float lastPitch;

    @Override
    public void onEnable() {
        if (mc.player != null) {
            lastYaw = mc.player.getYaw();
            lastPitch = mc.player.getPitch();
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.SEND) return;

        if (aca.get()) {
            if (event.getPacket() instanceof CustomPayloadC2SPacket packet) {
                CustomPayload payload = packet.payload();
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

            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float yaw = accessor.getYaw();
                float pitch = accessor.getPitch();

                // PacketAnalysisEqualRotation Bypass
                if (yaw == lastYaw) {
                    yaw += (float) ((Math.random() - 0.5) * 0.001);
                }
                if (pitch == lastPitch) {
                    pitch += (float) ((Math.random() - 0.5) * 0.001);
                }

                // PacketAnalysisAimStep Bypass
                float deltaYaw = Math.abs(yaw - lastYaw);
                float deltaPitch = Math.abs(pitch - lastPitch);

                if (deltaPitch > 1.0f && deltaYaw < 0.0001f) {
                    yaw += 0.0002f;
                }
                if (deltaYaw > 1.0f && deltaPitch < 0.0001f) {
                    pitch += 0.0002f;
                }

                // PacketAnalysisPerfectRotation Bypass (Random Noise)
                yaw += (float) ((Math.random() - 0.5) * 0.0001);
                pitch += (float) ((Math.random() - 0.5) * 0.0001);

                // Clamp pitch for IllegalPitch Check
                if (pitch > 90.0f) pitch = 90.0f;
                else if (pitch < -90.0f) pitch = -90.0f;

                accessor.setYaw(yaw);
                accessor.setPitch(pitch);

                lastYaw = yaw;
                lastPitch = pitch;
            }
        }

        if (disAim360.get() || grim.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    log("Disabled aim 360");
                }
            }
        }

        if (grim.get()) {
            if (grimOffset.get()) {
                if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesPosition()) {
                    IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                    accessor.setY(accessor.getY() + 1E-10); // Tiny offset to confuse 0.03 checks
                } else if (event.getPacket() instanceof VehicleMoveC2SPacket packet) {
                    Vec3d pos = packet.position();
                    Vec3d newPos = pos.add(0, 1E-10, 0);
                    event.setPacket(new VehicleMoveC2SPacket(newPos, packet.yaw(), packet.pitch(), packet.onGround()));
                }
            }
        }
    }

    @EventHandler
    public void onUpdate(PlayerTickEvent e) {
        if (grim.get() && grimBadPackets.get()) {
            if (mc.player.getAbilities().allowFlying) {
                PlayerAbilities ab = new PlayerAbilities();
                ab.flying = Math.random() > 0.5; // Randomly toggle flying status
                ab.allowFlying = true;
                PacketUtil.sendPacketNoEvent(new UpdatePlayerAbilitiesC2SPacket(ab));
            }
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.clientMessage(message);
            //NotificationManager.send(message);
        }
    }
}
