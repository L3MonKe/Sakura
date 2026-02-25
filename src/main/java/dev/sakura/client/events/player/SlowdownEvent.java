package dev.sakura.client.events.player;

public class SlowdownEvent {
    private boolean slowdown;

    public boolean isSlowdown() {
        return this.slowdown;
    }

    public void setSlowdown(boolean slowdown) {
        this.slowdown = slowdown;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SlowdownEvent other)) {
            return false;
        } else {
            return other.canEqual(this) && this.isSlowdown() == other.isSlowdown();
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof SlowdownEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        return result * 59 + (this.isSlowdown() ? 79 : 97);
    }

    @Override
    public String toString() {
        return "EventSlowdown(slowdown=" + this.isSlowdown() + ")";
    }

    public SlowdownEvent(boolean slowdown) {
        this.slowdown = slowdown;
    }
}
