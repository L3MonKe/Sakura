package com.zeta.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zeta.client.Zeta;
import com.zeta.client.command.Command;
import com.zeta.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("Help", "Shows all available commands", literal("help", "h", "?"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            ChatUtil.addChatMessage("§7--- §fAvailable Commands §7---");
            for (Command command : Zeta.COMMAND.getCommands()) {
                ChatUtil.addChatMessage("§7." + command.getName().toLowerCase() + " §f- " + command.getDescription());
            }
            return 1;
        });
    }
}
