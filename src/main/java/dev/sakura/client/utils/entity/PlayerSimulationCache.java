package dev.sakura.client.utils.entity;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.utils.movement.DirectionalInput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PlayerSimulationCache {
    private static final PlayerSimulationCache INSTANCE = new PlayerSimulationCache();
    private static final Map<PlayerEntity, SimulatedPlayerCache> otherPlayerCache = new ConcurrentHashMap<>();
    private static SimulatedPlayerCache localPlayerCache;
    private static boolean registered = false;

    public static void init() {
        if (!registered) {
            Sakura.EVENT_BUS.subscribe(INSTANCE);
            registered = true;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        otherPlayerCache.clear();
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        updatePlayerCache(new DirectionalInput(event.toPlayerInput()), true);
    }

    private static void updatePlayerCache(DirectionalInput directionalInput, boolean verify) {
        if (verify && localPlayerCache != null && localPlayerCache.simulatedPlayer.input.directionalInput.equals(directionalInput)) {
            return;
        }

        SimulatedPlayer.SimulatedPlayerInput input = SimulatedPlayer.SimulatedPlayerInput.fromClientPlayer(directionalInput);
        if (input == null) return;

        SimulatedPlayer simulatedPlayer = SimulatedPlayer.fromClientPlayer(input);

        if (simulatedPlayer != null) {
            localPlayerCache = new SimulatedPlayerCache(simulatedPlayer);
        }
    }

    public static SimulatedPlayerCache getSimulationForLocalPlayer() {
        if (localPlayerCache != null) {
            return localPlayerCache;
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return null;

        // Ensure we have an input
        DirectionalInput input = new DirectionalInput(mc.player.input);
        SimulatedPlayer.SimulatedPlayerInput simInput = SimulatedPlayer.SimulatedPlayerInput.fromClientPlayer(input);
        if (simInput == null) return null;

        SimulatedPlayer simulatedPlayer = SimulatedPlayer.fromClientPlayer(simInput);

        if (simulatedPlayer != null) {
            localPlayerCache = new SimulatedPlayerCache(simulatedPlayer);
        }
        return localPlayerCache;
    }

    public static class SimulatedPlayerCache {
        public final SimulatedPlayer simulatedPlayer;
        private int currentSimulationStep = 0;
        private final List<SimulatedPlayerSnapshot> simulationSteps = new ArrayList<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public SimulatedPlayerCache(SimulatedPlayer simulatedPlayer) {
            this.simulatedPlayer = simulatedPlayer;
            this.simulationSteps.add(new SimulatedPlayerSnapshot(simulatedPlayer));
        }

        public void simulateUntil(int ticks) {
            if (ticks < 0) throw new IllegalArgumentException("ticks may not be negative");
            if (currentSimulationStep >= ticks) return;

            lock.writeLock().lock();
            try {
                while (currentSimulationStep < ticks) {
                    simulatedPlayer.tick();
                    simulationSteps.add(new SimulatedPlayerSnapshot(simulatedPlayer));
                    currentSimulationStep++;
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public List<SimulatedPlayerSnapshot> getSnapshotsBetween(int start, int end) {
            if (end >= 60 * 20)
                throw new IllegalArgumentException("tried to simulate a player for more than a minute!");
            simulateUntil(end + 1);

            lock.readLock().lock();
            try {
                // Ensure indices are valid
                if (start < 0) start = 0;
                if (end >= simulationSteps.size()) end = simulationSteps.size() - 1;
                if (start > end) return new ArrayList<>();

                return new ArrayList<>(simulationSteps.subList(start, end + 1));
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    public record SimulatedPlayerSnapshot(Vec3d pos, double fallDistance, Vec3d velocity, boolean onGround) {
        public SimulatedPlayerSnapshot(SimulatedPlayer s) {
            this(s.getPos(), s.fallDistance, s.velocity, s.onGround);
        }
    }
}
