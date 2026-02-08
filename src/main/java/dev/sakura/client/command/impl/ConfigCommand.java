package dev.sakura.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public final class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "Open client config folder or manage cloud configs.", literal("config", "conf", "cfg"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            printUsage();
            return 1;
        });

        builder.then(literal("list").executes(c -> {
            Sakura.CONFIG.cloudList();
            return 1;
        }));

        builder.then(literal("load")
                .then(argument("name", string()).executes(c -> {
                    Sakura.CONFIG.cloudLoad("", getString(c, "name"));
                    return 1;
                }))
                .then(argument("user", string())
                        .then(argument("name", string()).executes(c -> {
                            Sakura.CONFIG.cloudLoad(getString(c, "user"), getString(c, "name"));
                            return 1;
                        })))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config load [用户] <配置名>");
                    return 1;
                }));

        builder.then(literal("save")
                .then(argument("name", string()).executes(c -> {
                    String name = getString(c, "name");
                    Sakura.CONFIG.cloudSave(name);
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config save <name>");
                    return 1;
                }));

        builder.then(literal("upload")
                .then(argument("name", string()).executes(c -> {
                    String name = getString(c, "name");
                    Sakura.CONFIG.cloudSave(name);
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config save <name>");
                    return 1;
                }));

        builder.then(literal("delete")
                .then(argument("name", string()).executes(c -> {
                    Sakura.CONFIG.cloudDelete("", getString(c, "name"));
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config delete <name>");
                    return 1;
                }));

        builder.then(literal("remove")
                .then(argument("name", string()).executes(c -> {
                    Sakura.CONFIG.cloudDelete("", getString(c, "name"));
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config delete <name>");
                    return 1;
                }));
    }

    private static void printUsage() {
        ChatUtil.addChatMessage("§bConfig 命令用法:");
        ChatUtil.addChatMessage("§7.config list §f- 列出云配置");
        ChatUtil.addChatMessage("§7.config load [用户] <配置名> §f- 加载云配置");
        ChatUtil.addChatMessage("§7.config save <name> §f- 保存云配置");
        ChatUtil.addChatMessage("§7.config delete <name> §f- 删除云配置");
    }
}
