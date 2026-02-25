package dev.mzc.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.mzc.client.Sakura;
import dev.mzc.client.command.Command;
import dev.mzc.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("Save", "Saves all configurations", literal("save", "s"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            Sakura.CONFIG.saveDefaultConfig();
            ChatUtil.addChatMessage("All configurations saved.");
            return 1;
        });
    }
}
