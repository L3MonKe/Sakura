package dev.sakura.client.events.client;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class SuggestChatEvent {
    private CommandDispatcher<CommandSource> dispatcher;
    private CommandSource source;
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public CommandSource getSource() {
        return source;
    }

    public void setSource(CommandSource source) {
        this.source = source;
    }

    public void setDispatcher(CommandDispatcher<CommandSource> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return dispatcher;
    }
}
