package dev.mzc.client.events.player;

public class SlowdownEvent {
    private boolean slowdown;
    private final Type type;

    public enum Type {
        Item,
        Web,
        Sneak,
        SoulSand,
        BerryBush
    }

    public SlowdownEvent(Type type, boolean slowdown) {
        this.type = type;
        this.slowdown = slowdown;
    }

    public SlowdownEvent(boolean slowdown) {
        this(Type.Item, slowdown); // Default to Item for backward compatibility if needed
    }

    public boolean isSlowdown() {
        return this.slowdown;
    }

    public void setSlowdown(boolean slowdown) {
        this.slowdown = slowdown;
    }

    public Type getType() {
        return type;
    }
}
