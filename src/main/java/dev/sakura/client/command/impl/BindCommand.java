package dev.sakura.client.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.command.ModuleArgumentType;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.client.KeyUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import org.lwjgl.glfw.GLFW;

public class BindCommand extends Command {
    public BindCommand() {
        super("Bind", "Keybinds a module", literal("bind", "b"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                        .then(argument("key", StringArgumentType.string())
                                .then(argument("mode", StringArgumentType.string())
                                        .executes(c -> {
                                            Module module = ModuleArgumentType.getModule(c, "module");
                                            String keyName = StringArgumentType.getString(c, "key");
                                            String modeName = StringArgumentType.getString(c, "mode");

                                            if (keyName.equalsIgnoreCase("none")) {
                                                module.setKey(InputUtil.UNKNOWN_KEY.getCode());
                                                ChatUtil.addChatMessage("Unbound " + module.getEnglishName() + ".");
                                                Sakura.CONFIG.saveDefaultConfig();
                                                return 1;
                                            }

                                            InputUtil.Key key = KeyUtil.getKeyFromName(keyName);
                                            if (key == InputUtil.UNKNOWN_KEY || key.getCode() == GLFW.GLFW_KEY_UNKNOWN) {
                                                ChatUtil.addChatMessage("Invalid key: " + keyName);
                                                return 0;
                                            }

                                            Module.BindMode bindMode = parseBindMode(modeName);
                                            if (bindMode == null) {
                                                ChatUtil.addChatMessage("Invalid mode: " + modeName + ". Use 'toggle' or 'hold'.");
                                                return 0;
                                            }

                                            module.setKey(key.getCode());
                                            module.setBindMode(bindMode);
                                            ChatUtil.addChatMessage("Bound " + module.getEnglishName() + " to " + keyName.toUpperCase() + " (" + bindMode.name() + ").");
                                            Sakura.CONFIG.saveDefaultConfig();
                                            return 1;
                                        }))
                                .executes(c -> {
                                    Module module = ModuleArgumentType.getModule(c, "module");
                                    String keyName = StringArgumentType.getString(c, "key");

                                    if (keyName.equalsIgnoreCase("none")) {
                                        module.setKey(InputUtil.UNKNOWN_KEY.getCode());
                                        ChatUtil.addChatMessage("Unbound " + module.getEnglishName() + ".");
                                        Sakura.CONFIG.saveDefaultConfig();
                                        return 1;
                                    }

                                    InputUtil.Key key = KeyUtil.getKeyFromName(keyName);
                                    if (key == InputUtil.UNKNOWN_KEY || key.getCode() == GLFW.GLFW_KEY_UNKNOWN) {
                                        ChatUtil.addChatMessage("Invalid key: " + keyName);
                                        return 0;
                                    }

                                    module.setKey(key.getCode());
                                    ChatUtil.addChatMessage("Bound " + module.getEnglishName() + " to " + keyName.toUpperCase() + " (" + module.getBindMode().name() + ").");
                                    Sakura.CONFIG.saveDefaultConfig();
                                    return 1;
                                }))
                        .executes(c -> {
                            ChatUtil.addChatMessage("Usage: .bind <module> <key> [toggle/hold]");
                            return 1;
                        }))
                .executes(c -> {
                    ChatUtil.addChatMessage("Usage: .bind <module> <key> [toggle/hold]");
                    return 1;
                });
    }

    private Module.BindMode parseBindMode(String mode) {
        if (mode.equalsIgnoreCase("toggle") || mode.equalsIgnoreCase("t")) {
            return Module.BindMode.Toggle;
        } else if (mode.equalsIgnoreCase("hold") || mode.equalsIgnoreCase("h")) {
            return Module.BindMode.Hold;
        }
        return null;
    }
}
