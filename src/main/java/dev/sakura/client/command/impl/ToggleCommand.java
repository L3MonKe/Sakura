package dev.sakura.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.command.Command;
import dev.sakura.client.command.ModuleArgumentType;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("Toggle", "Enables/Disables a module", literal("toggle", "t"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                        .executes(c -> {
                            Module module = ModuleArgumentType.getModule(c, "module");
                            module.toggle();
                            ChatUtil.addChatMessage(module.getEnglishName() + " is now " +
                                    (module.isEnabled() ? "§aenabled" : "§cdisabled") + "§f.");
                            return 1;
                        }))
                .executes(c -> {
                    ChatUtil.addChatMessage("Usage: .toggle <module>");
                    return 1;
                });
    }
}
