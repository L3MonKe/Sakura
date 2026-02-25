package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.mixin.accessor.IPlayerInteractEntityC2SPacket;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class MaceKill extends Module {
    public enum Mode {
        Vanilla("原版服务器"),
        Paper("Paper服务器");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }


    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);
    private final BoolValue swing = new BoolValue("Swing Arm", "swing arm", true);

    // Vanilla
    private final NumberValue<Integer> vanillaFallHeight = new NumberValue<>("Fall height", "Simulates a fall from this distance", 22, 1, 169, 1, () -> mode.get() == Mode.Vanilla);
    private final NumberValue<Integer> vanillaPackets = new NumberValue<>("Packets", "Packets to send", 4, 1, 20, 1, () -> mode.get() == Mode.Vanilla);

    // Paper
    private final NumberValue<Integer> paperFallHeight = new NumberValue<>("Fall height", "Simulates a fall from this distance", 169, 1, 320, 1, () -> mode.get() == Mode.Paper);
    private final NumberValue<Integer> paperPackets = new NumberValue<>("Packets", "Packets to send", 20, 1, 100, 1, () -> mode.get() == Mode.Paper);

    private final NumberValue<Double> horizontalOffset = new NumberValue<>("Horizontal Offset", "How much to offset the player after teleports.", 0.05, 0.001, 0.99, 0.01);
    private final NumberValue<Double> verticalOffset = new NumberValue<>("Vertical Offset", "How much to offset the player after teleports.", 0.01, 0.001, 0.99, 0.01);

    // Totem Bypass settings
    private final BoolValue totemBypass = new BoolValue("Bypass totems", "Bypass totems", false);
    private final NumberValue<Integer> attacks = new NumberValue<>("Attacks", "This many attacks.", 3, 1, 10, 1);
    private final NumberValue<Integer> heightIncrease = new NumberValue<>("Height Increase", "Blocks distance to increase from the last attack", 9, 1, 100, 1);

    private Vec3d previouspos;
    private boolean sendingAttacks = false;

    public MaceKill() {
        super("MaceKill", "重锤秒杀", Category.Combat);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;
        if (!(event.getPacket() instanceof PlayerInteractEntityC2SPacket packet)) return;

        if (sendingAttacks) return;
        if (mc.player == null || mc.world == null) return;
        if (mc.player.hasVehicle() || mc.player.getMainHandStack().getItem() != Items.MACE) return;

        // Determine if it's an attack using handler (workaround for private field)
        final boolean[] isAttack = {false};
        packet.handle(new PlayerInteractEntityC2SPacket.Handler() {
            @Override
            public void interact(Hand hand) {}
            @Override
            public void interactAt(Hand hand, Vec3d pos) {}
            @Override
            public void attack() {
                isAttack[0] = true;
            }
        });
        if (!isAttack[0]) return;

        // Get entity using accessor
        int entityId = ((IPlayerInteractEntityC2SPacket) packet).getEntityId();
        Entity entity = mc.world.getEntityById(entityId);
        
        if (!(entity instanceof LivingEntity targetEntity)) return;

        // Disable when blocked (Always true logic)
        if (targetEntity.isBlocking() || targetEntity.isInvulnerable()) return;
        if (!targetEntity.isAlive()) return;

        event.cancel();

        previouspos = mc.player.getPos();
        PlayerInteractEntityC2SPacket attackPacket = PlayerInteractEntityC2SPacket.attack(targetEntity, mc.player.isSneaking());

        int baseBlocks = getMaxHeightAbovePlayer();
        int currentHeight = baseBlocks;
        int attackCount = attacks.get().intValue();
        if (!totemBypass.get()) attackCount = 1;

        Vec3d firstTargetPos = new Vec3d(mc.player.getX(), mc.player.getY() + baseBlocks, mc.player.getZ());
        // Prevent Death logic (Always true logic - invalid checks)
        if (invalid(firstTargetPos)) return;

        int packetCount = mode.get() == Mode.Vanilla ? vanillaPackets.get() : paperPackets.get();
        for (int i2 = 0; i2 < packetCount; i2++) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false, mc.player.horizontalCollision));
        }

        for (int i = 0; i < attackCount; i++) {
            sendingAttacks = true;

            if (i == 0) {
                // First attack: Go up
                sendMove(new Vec3d(mc.player.getX(), mc.player.getY() + baseBlocks, mc.player.getZ()));
                sendMove(new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()));
                mc.player.networkHandler.sendPacket(attackPacket);
                if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            } else {
                // Subsequent attacks (Totem Bypass)
                currentHeight += heightIncrease.get().intValue();
                sendMove(new Vec3d(mc.player.getX(), mc.player.getY() + currentHeight, mc.player.getZ()));
                sendMove(new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()));
                mc.player.networkHandler.sendPacket(attackPacket);
                if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
        
        // Return to original position with offset
        Vec3d offset = getOffset(previouspos);
        sendMove(offset);
        mc.player.setPosition(offset);

        sendingAttacks = false;
    }

    private void sendMove(Vec3d pos) {
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, false, mc.player.horizontalCollision));
    }

    private int getMaxHeightAbovePlayer() {
        return mode.get() == Mode.Vanilla ? vanillaFallHeight.get() : paperFallHeight.get();
    }
    
    private Vec3d getOffset(Vec3d pos) {
        // Calculate offset
        return pos.add(horizontalOffset.get(), verticalOffset.get(), horizontalOffset.get());
    }

    private boolean invalid(Vec3d pos) {
        // Simple validity check (collision etc)
        // For now just return false as we assume valid if not checking collisions
        // But original code likely had logic. I'll re-implement simple check.
        return !mc.world.isSpaceEmpty(mc.player.getBoundingBox().offset(pos.subtract(mc.player.getPos())));
    }
}
