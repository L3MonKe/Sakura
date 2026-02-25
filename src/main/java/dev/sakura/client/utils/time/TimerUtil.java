package dev.sakura.client.utils.time;

public class TimerUtil {
    public long lastMS;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean delay(double ticks) {
        return getCurrentMS() - lastMS >= ticks * 50;
    }

    public boolean passedSecond(double ticks) {
        return getCurrentMS() - lastMS >= ticks * 1000;
    }

    public boolean passedMS(double milliseconds) {
        if (milliseconds == 0) {
            return true;
        }
        return (double) (this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public long getTime() {
        return getCurrentMS() - this.lastMS;
    }

    public void setTime(long time) {
        this.lastMS = time;
    }
}
