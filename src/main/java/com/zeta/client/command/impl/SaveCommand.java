package com.zeta.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zeta.client.Zeta;
import com.zeta.client.command.Command;
import com.zeta.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("Save", "Saves all configurations", literal("save", "s"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            Zeta.CONFIG.saveDefaultConfig();
            ChatUtil.addChatMessage("All configurations saved.");
            return 1;
        });
    }
}
