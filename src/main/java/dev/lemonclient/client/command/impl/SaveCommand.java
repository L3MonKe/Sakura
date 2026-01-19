package dev.lemonclient.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.command.Command;
import dev.lemonclient.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("Save", "Saves all configurations", literal("save", "s"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            LemonClient.CONFIG.saveDefaultConfig();
            ChatUtil.addChatMessage("All configurations saved.");
            return 1;
        });
    }
}
