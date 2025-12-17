package dev.sakura.module.impl.movement;

import dev.sakura.events.client.TickEvent;
import dev.sakura.events.packet.PacketEvent;
import dev.sakura.events.type.EventType;
import dev.sakura.manager.impl.RotationManager;
import dev.sakura.mixin.accessor.IClientPlayerEntity;
import dev.sakura.mixininterface.IPlayerInteractEntityC2SPacket;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.utils.rotation.MovementFix;
import dev.sakura.utils.vector.Vector2f;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class AutoSprint extends Module {

    public enum Mode {
        Rage, Strict
    }

    public AutoSprint() {
        super("AutoSprint", Category.Movement);
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Strict);
    public final BoolValue keepSprint = new BoolValue("Keep Sprint", false);
    public final BoolValue unsprintOnHit = new BoolValue("Unsprint On Hit", false);
    public final BoolValue unsprintInWater = new BoolValue("Unsprint In Water", true, () -> mode.is(Mode.Rage));
    public final BoolValue permaSprint = new BoolValue("Sprint While Stationary", true, () -> mode.is(Mode.Rage));

    @EventHandler(priority = EventPriority.HIGH)
    private void onTickMovement(TickEvent.Post event) {
        if (mc.player == null || unsprintInWater.get() && mc.player.isTouchingWater()) return;
        mc.player.setSprinting(shouldSprint());
        RotationManager.setRotations(new Vector2f(0, mc.player.getPitch()), 10f, MovementFix.NORMAL);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPacketSend(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;
        if (!unsprintOnHit.get()) return;
        if (!(event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet)
                || packet.getType() != PlayerInteractEntityC2SPacket.InteractType.ATTACK) return;

        mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
        mc.player.setSprinting(false);
    }

    @EventHandler
    private void onPacketSent(PacketEvent event) {
        if (event.getType() != EventType.SENT) return;

        if (!unsprintOnHit.get() || !keepSprint.get()) return;
        if (!(event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet)
                || packet.getType() != PlayerInteractEntityC2SPacket.InteractType.ATTACK) return;

        if (!shouldSprint() || mc.player.isSprinting()) return;

        mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
        mc.player.setSprinting(true);
    }

    public boolean shouldSprint() {
        //if (mc.currentScreen != null && !Modules.get().get(GUIMove.class).sprint.get()) return false;

        float movement = mode.is(Mode.Strict)
                ? (Math.abs(mc.player.forwardSpeed) + Math.abs(mc.player.sidewaysSpeed))
                : mc.player.forwardSpeed;

        if (movement <= (mc.player.isSubmergedInWater() ? 1.0E-5F : 0.8)) {
            if (mode.is(Mode.Strict) || !permaSprint.get()) return false;
        }

        boolean strictSprint = !(mc.player.isTouchingWater() && !mc.player.isSubmergedInWater())
                && ((IClientPlayerEntity) mc.player).invokeCanSprint()
                && (!mc.player.horizontalCollision || mc.player.collidedSoftly);

        return isEnabled() && (mode.is(Mode.Rage) || strictSprint);
    }

    public boolean rageSprint() {
        return isEnabled() && mode.is(Mode.Rage);
    }

    public boolean unsprintInWater() {
        return isEnabled() && unsprintInWater.get();
    }

    public boolean stopSprinting() {
        return !isEnabled() || !keepSprint.get();
    }
}
