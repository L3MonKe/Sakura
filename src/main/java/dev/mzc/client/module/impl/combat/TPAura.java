package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;


public class TPAura extends Module {
    public enum Mode {
        Vanilla("原版服务器"),
        Paper("Paper服务器");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }


    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);
    private final BoolValue swing = new BoolValue("Swing", "挥手", true);
    private final NumberValue<Integer> attackDelay = new NumberValue<>("AttackDelay", "攻击延迟", 0, 0, 20, 1);

    // Targets
    private final BoolValue players = new BoolValue("Players", "玩家", true);
    private final BoolValue mobs = new BoolValue("Mobs", "怪物", false);
    private final BoolValue animals = new BoolValue("Animals", "动物", false);
    private final BoolValue teamCheck = new BoolValue("TeamCheck", "队伍检测", true);

    // Vanilla
    private final NumberValue<Integer> vanillaPackets = new NumberValue<>("Packets", "发包数", 4, 1, 5, 1,() -> mode.get() == Mode.Vanilla);
    private final NumberValue<Double> vanillaDistance = new NumberValue<>("Distance", "距离", 22.0, 1.0, 22.0, 0.5,() -> mode.get() == Mode.Vanilla);

    // Paper
    private final NumberValue<Integer> paperPackets = new NumberValue<>("Packets", "发包数", 8, 1, 20, 1,() -> mode.get() == Mode.Paper);
    private final NumberValue<Double> paperDistance = new NumberValue<>("Distance", "距离", 49.0, 1.0, 99.0, 0.5,() -> mode.get() == Mode.Paper);

    // Totem Bypass
    private final BoolValue totemBypass = new BoolValue("BypassTotems", "绕过图腾", false);
    private final NumberValue<Integer> attacks = new NumberValue<>("Attacks", "攻击次数", 2, 0, 3, 1);
    private final NumberValue<Integer> heightIncrease = new NumberValue<>("Increase", "高度增加", 9, 1, 100, 1);

    private double maxDistance;
    private int entityAttackTicks = 0;

    public TPAura() {
        super("TPAura", "TP杀戮", Category.Combat);
        this.setType(ModuleType.Hack);
    }

    @Override
    public void onEnable() {
        updateDistance();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null || mc.getNetworkHandler() == null) return;

        updateDistance();

        entityAttackTicks++;
        if (entityAttackTicks > attackDelay.get()){
            hitEntity();
            entityAttackTicks = 0;
        }
    }
    
    private void updateDistance() {
        if (mode.get() == Mode.Vanilla) maxDistance = vanillaDistance.get();
        else maxDistance = paperDistance.get();
    }

    private Entity findClosestTarget() {
        Entity closest = null;
        double closestDist = Double.MAX_VALUE;

        for (Entity entity : mc.world.getEntities()) {
            if (!isValidListTarget(entity)) continue;
            double dist = entity.squaredDistanceTo(mc.player);
            if (dist <= maxDistance * maxDistance && dist < closestDist) {
                closest = entity;
                closestDist = dist;
            }
        }
        return closest;
    }

    private boolean isValidListTarget(Entity entity) {
        if (entity == null || entity == mc.player || !entity.isAlive()) return false;
        if (entity instanceof PlayerEntity) {
            if (!players.get()) return false;
            if (teamCheck.get() && isTeammate((PlayerEntity) entity)) return false;
        } else if (entity instanceof Monster) {
            if (!mobs.get()) return false;
        } else if (entity instanceof AnimalEntity) {
            if (!animals.get()) return false;
        } else {
            return false;
        }
        return true;
    }

    private boolean isTeammate(PlayerEntity entity) {
        // Implement simple team check logic if available or just return false
        return false; 
    }

    private void hitEntity() {
        Entity target = findClosestTarget();
        if (target == null) return;

        Vec3d startPos = mc.player.getPos();
        Vec3d targetPos = target.getPos();
        
        // Simplified Logic: Just teleport and attack
        // TPAura usually does the teleport sequence
        
        // Calculate sequence positions
        // Re-using logic from TPAttack/MaceKill but simplified for Aura
        
        int packetCount = mode.get() == Mode.Vanilla ? vanillaPackets.get() : paperPackets.get();
        
        // Send spam packets
        for (int i = 0; i < packetCount; i++) {
             mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false, mc.player.horizontalCollision));
        }

        int attackCount = totemBypass.get() ? attacks.get() : 1;
        
        for (int i = 0; i < attackCount; i++) {
            // TP Logic
            Vec3d topPos = startPos.add(0, maxDistance, 0); // Above self
            Vec3d targetTopPos = targetPos.add(0, maxDistance, 0); // Above target
            
            // For Paper we usually go up
            if (mode.get() == Mode.Paper) {
                sendMove(topPos);
                sendMove(targetTopPos);
            }
            
            // TP to target
            sendMove(targetPos);
            
            // Attack
            mc.interactionManager.attackEntity(mc.player, target);
            if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            
            // Return
            if (mode.get() == Mode.Paper) {
                sendMove(targetTopPos);
                sendMove(topPos);
            }
            sendMove(startPos);
        }
        
        // Final offset
        Vec3d offset = startPos.add(0.05, 0.01, 0.05); // Hardcoded offset
        sendMove(offset);
        mc.player.setPosition(offset);
    }
    
    private void sendMove(Vec3d pos) {
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, false, mc.player.horizontalCollision));
    }
}
