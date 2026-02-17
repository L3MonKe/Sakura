package dev.sakura.client.event.impl.player;

public class BlockBreakingCooldownEvent {
    private int cooldown;

    public BlockBreakingCooldownEvent(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
