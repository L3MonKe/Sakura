package dev.sakura.client.utils.player;

import java.util.concurrent.atomic.AtomicBoolean;

public class PacketLockUtils {
    private static final AtomicBoolean attackLock = new AtomicBoolean(false);
    private static final AtomicBoolean swingLock = new AtomicBoolean(false);

    public static boolean attackAndLock() {
        return attackLock.compareAndSet(false, true);
    }

    public static boolean swingAndLock() {
        return swingLock.compareAndSet(false, true);
    }

    public static void reset() {
        attackLock.set(false);
        swingLock.set(false);
    }
}
