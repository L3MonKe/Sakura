package dev.sakura.events.player;

import dev.sakura.events.Cancellable;
import net.bytebuddy.agent.builder.AgentBuilder;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/17 21:55
 * @Filename：PlayerTickEvent
 */
public class PlayerTickEvent extends Cancellable {
    public boolean cancelled;
}
