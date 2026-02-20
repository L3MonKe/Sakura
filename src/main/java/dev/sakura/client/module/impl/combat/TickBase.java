package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.BlinkNoSlow;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.entity.PlayerSimulationCache;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TickBase extends Module {

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Past, Mode.class);

    private final NumberValue<Double> minRange = new NumberValue<>("Min Range", "最小范围", 2.5, 0.0, 8.0, 0.1);
    private final NumberValue<Double> maxRange = new NumberValue<>("Max Range", "最大范围", 4.0, 0.0, 8.0, 0.1);

    private final NumberValue<Double> balanceRecoveryIncrement = new NumberValue<>("Balance Recovery", "平衡恢复", 1.0, 0.0, 2.0, 0.1);
    private final NumberValue<Integer> balanceMaxValue = new NumberValue<>("Balance Max", "最大平衡", 20, 0, 200, 1);
    private final NumberValue<Integer> maxTicksAtATime = new NumberValue<>("Max Ticks", "最大Tick", 4, 1, 20, 1);
    private final BoolValue pauseOnFlag = new BoolValue("Pause On Flag", "被拉回暂停", true);
    private final NumberValue<Integer> pause = new NumberValue<>("Pause", "暂停Tick", 0, 0, 20, 1);
    private final NumberValue<Integer> cooldown = new NumberValue<>("Cooldown", "冷却", 0, 0, 100, 1);
    private final BoolValue pauseOnHit = new BoolValue("Pause On Hit", "受击暂停", true);
    private final NumberValue<Integer> pauseHitTicks = new NumberValue<>("Hit Pause Ticks", "受击暂停时长", 10, 1, 40, 1);
    private final BoolValue forceGround = new BoolValue("Force Ground", "强制地面", false);

    private final BoolValue debug = new BoolValue("Debug", "调试信息", false);

    private int ticksToSkip = 0;
    private volatile double tickBalance = 0.0;
    private boolean reachedTheLimit = false;

    private final List<TickData> tickBuffer = new ArrayList<>();

    private int cooldownTicksRemaining = 0;
    private int scheduledTicksToRun = 0;
    private Mode scheduledMode = null;

    // Flag to prevent recursive calls when calling mc.tick()
    private boolean executingExtraTicks = false;

    public TickBase() {
        super("TickBase", "TickBase", Category.Combat);
    }

    @Override
    protected void onEnable() {
        ticksToSkip = 0;
        tickBalance = 0f;
        reachedTheLimit = false;
        tickBuffer.clear();
        cooldownTicksRemaining = 0;
        scheduledTicksToRun = 0;
        scheduledMode = null;

        // Ensure simulation cache is initialized
        PlayerSimulationCache.init();
    }

    @Override
    protected void onDisable() {
        ticksToSkip = 0;
        tickBalance = 0f;
        reachedTheLimit = false;
        tickBuffer.clear();
        cooldownTicksRemaining = 0;
        scheduledTicksToRun = 0;
        scheduledMode = null;
    }

    @EventHandler
    public void onPlayerTick(PlayerTickEvent event) {
        if (nullCheck()) return;

        // We do not want this module to conflict with blink
        if (mc.player.hasVehicle() || Sakura.MODULES.getModule(BlinkNoSlow.class).isEnabled()) {
            return;
        }

        if (ticksToSkip > 0) {
            ticksToSkip--;
            event.cancel();
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // Prevent recursive execution if we are calling mc.tick() ourselves
        if (executingExtraTicks) {
            return;
        }

        // We do not want this module to conflict with blink
        if (mc.player.hasVehicle() || Sakura.MODULES.getModule(BlinkNoSlow.class).isEnabled()) {
            return;
        }

        if (tickBuffer.isEmpty()) {
            return;
        }

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);

        // Handle cooldown
        if (cooldownTicksRemaining > 0) {
            cooldownTicksRemaining--;
            return;
        }

        // If we are currently skipping ticks (frozen), do not process logic
        if (ticksToSkip > 0) {
            return;
        }

        // Handle scheduled Past mode execution
        if (scheduledTicksToRun > 0 && scheduledMode == Mode.Past) {
            executeExtraTicks(scheduledTicksToRun);
            if (debug.get()) {
                ChatUtil.clientMessage("TickBase: Executed " + scheduledTicksToRun + " ticks.");
            }
            scheduledTicksToRun = 0;
            scheduledMode = null;
            cooldownTicksRemaining = cooldown.get();
            return;
        }

        LivingEntity target;
        if (!killAura.isEnabled()) return;
        target = killAura.getCurrentTarget();

        if (target == null) return;

        double currentDistanceSq = mc.player.squaredDistanceTo(target);
        double minRangeSq = minRange.get() * minRange.get();
        double maxRangeSq = maxRange.get() * maxRange.get();

        // Find the best tick that is able to hit the target and is not too far away
        List<Integer> possibleTicks = new ArrayList<>();
        Vec3d targetPos = target.getEntityPos();

        for (int i = 0; i < tickBuffer.size(); i++) {
            TickData tick = tickBuffer.get(i);
            double distSq = tick.position.squaredDistanceTo(targetPos);

            // Check if this tick is closer than current position and within range
            if (distSq < currentDistanceSq && distSq >= minRangeSq && distSq <= maxRangeSq) {
                possibleTicks.add(i);
            }
        }

        if (forceGround.get()) {
            possibleTicks.removeIf(i -> !tickBuffer.get(i).onGround);
        }

        // Ensure we have valid ticks
        if (possibleTicks.isEmpty()) return;

        // Find a tick where we can critical hit (fall distance > 0)
        int bestTickIndex = -1;
        for (int i : possibleTicks) {
            if (tickBuffer.get(i).fallDistance > 0.0) {
                bestTickIndex = i;
                break;
            }
        }

        // Fallback to the first possible tick if no crit tick found
        if (bestTickIndex == -1) {
            bestTickIndex = possibleTicks.get(0);
        }

        if (bestTickIndex == 0) {
            return;
        }

        if (!killAura.isEnabled() || killAura.getCurrentTarget() == null) {
            return;
        }

        if (mode.get() == Mode.Past) {
            // Schedule the skip and execution for the next tick/frame
            ticksToSkip = bestTickIndex + pause.get();
            scheduledTicksToRun = bestTickIndex;
            scheduledMode = Mode.Past;

            if (debug.get()) {
                ChatUtil.clientMessage("TickBase: Scheduled skip " + ticksToSkip + " ticks.");
            }
        } else {
            // Future mode: Execute ticks immediately until requirement is broken or limit reached
            int totalSkipped = 0;
            for (int i = 0; i < bestTickIndex; i++) {
                // Check if KillAura requirement is still met during simulation
                if (!killAura.isEnabled() || killAura.getCurrentTarget() == null) {
                    break;
                }

                runTick();
                tickBalance -= 1;
                totalSkipped++;
            }

            if (debug.get()) {
                ChatUtil.clientMessage("TickBase: Skipped " + totalSkipped + " ticks.");
            }

            ticksToSkip = totalSkipped + pause.get();
            cooldownTicksRemaining = cooldown.get();
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;

        // We do not want this module to conflict with blink
        if (mc.player.hasVehicle() || Sakura.MODULES.getModule(BlinkNoSlow.class).isEnabled()) {
            return;
        }

        tickBuffer.clear();

        PlayerSimulationCache.SimulatedPlayerCache cache = PlayerSimulationCache.getSimulationForLocalPlayer();
        if (cache == null) return;

        if (tickBalance <= 0) reachedTheLimit = true;
        if (tickBalance * 2 > balanceMaxValue.get()) reachedTheLimit = false;

        if (tickBalance <= balanceMaxValue.get()) {
            tickBalance += balanceRecoveryIncrement.get();
        }

        if (reachedTheLimit) return;

        int limit = Math.min((int) tickBalance, maxTicksAtATime.get());
        if (limit <= 0) return;

        List<PlayerSimulationCache.SimulatedPlayerSnapshot> snapshots = cache.getSnapshotsBetween(0, limit);

        // Populate tick buffer with simulation data
        for (PlayerSimulationCache.SimulatedPlayerSnapshot snapshot : snapshots) {
            tickBuffer.add(new TickData(
                    snapshot.pos(),
                    snapshot.fallDistance(),
                    snapshot.velocity(),
                    snapshot.onGround()
            ));
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (event.getType() == EventType.RECEIVE) {
            if (pauseOnHit.get()) {
                if ((event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) ||
                        event.getPacket() instanceof ExplosionS2CPacket) {

                    cooldownTicksRemaining = pauseHitTicks.get();
                    ticksToSkip = 0;
                    scheduledTicksToRun = 0;
                    scheduledMode = null;

                    if (debug.get()) {
                        ChatUtil.clientMessage("TickBase: Paused due to hit.");
                    }
                }
            }

            // Reset balance if we get flagged (teleported back by server)
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket && pauseOnFlag.get()) {
                tickBalance = 0f;
                if (debug.get()) {
                    ChatUtil.clientMessage("TickBase: Flag detected, balance reset.");
                }
            }
        }
    }

    private void executeExtraTicks(int ticks) {
        for (int i = 0; i < ticks; i++) {
            runTick();
            tickBalance -= 1;
        }
    }

    private void runTick() {
        if (mc.player == null) return;
        mc.player.tick();
    }

    // Data class to store simulated tick information
    private record TickData(Vec3d position, double fallDistance, Vec3d velocity, boolean onGround) {
    }

    public enum Mode {
        Past, Future
    }
}
