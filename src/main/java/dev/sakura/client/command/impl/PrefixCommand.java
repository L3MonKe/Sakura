package dev.sakura.client.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;
import org.lwjgl.glfw.GLFW;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("Prefix", "Changes the command prefix", literal("prefix"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("prefix", StringArgumentType.greedyString())
                        .executes(c -> {
                            String newPrefix = StringArgumentType.getString(c, "prefix");
                            if (newPrefix.isEmpty()) {
                                ChatUtil.addChatMessage("Prefix cannot be empty.");
                                return 0;
                            }

                            int prefixKey = getPrefixKey(newPrefix);
                            Sakura.COMMAND.setPrefix(newPrefix, prefixKey);
                            Sakura.CONFIG.savePrefix(newPrefix);
                            ChatUtil.addChatMessage("Command prefix set to: §a" + newPrefix);
                            return 1;
                        }))
                .executes(c -> {
                    ChatUtil.addChatMessage("Current prefix: §a" + Sakura.COMMAND.getPrefix());
                    ChatUtil.addChatMessage("Usage: " + Sakura.COMMAND.getPrefix() + "prefix <new prefix>");
                    return 1;
                });
    }

    private int getPrefixKey(String prefix) {
        if (prefix.isEmpty()) return GLFW.GLFW_KEY_PERIOD;

        char firstChar = prefix.charAt(0);
        return switch (firstChar) {
            case '.' -> GLFW.GLFW_KEY_PERIOD;
            case ',' -> GLFW.GLFW_KEY_COMMA;
            case '/' -> GLFW.GLFW_KEY_SLASH;
            case '-' -> GLFW.GLFW_KEY_MINUS;
            case '!' -> GLFW.GLFW_KEY_1;
            case '@' -> GLFW.GLFW_KEY_2;
            case '#' -> GLFW.GLFW_KEY_3;
            case '$' -> GLFW.GLFW_KEY_4;
            case '%' -> GLFW.GLFW_KEY_5;
            case '^' -> GLFW.GLFW_KEY_6;
            case '&' -> GLFW.GLFW_KEY_7;
            case '*' -> GLFW.GLFW_KEY_8;
            case ';' -> GLFW.GLFW_KEY_SEMICOLON;
            default -> GLFW.GLFW_KEY_UNKNOWN;
        };
    }
}
