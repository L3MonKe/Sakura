package dev.sakura.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.command.ModuleArgumentType;
import dev.sakura.client.gui.clickgui.panel.CategoryPanel;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import net.minecraft.command.CommandSource;

public class ResetCommand extends Command {
    public ResetCommand() {
        super("Reset", "Resets module settings or HUD positions", literal("reset"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder
                .then(literal("all").executes(c -> {
                    resetAll();
                    ChatUtil.addChatMessage("§a已重置所有配置为默认值。");
                    return 1;
                }))
                .then(literal("module")
                        .then(argument("module", ModuleArgumentType.module())
                                .executes(c -> {
                                    Module module = ModuleArgumentType.getModule(c, "module");
                                    module.reset();
                                    ChatUtil.addChatMessage("§a已重置模块 " + module.getEnglishName() + " 为默认值。");
                                    return 1;
                                })))
                .executes(c -> {
                    ChatUtil.addChatMessage("§e用法:");
                    ChatUtil.addChatMessage("  §7" + Sakura.COMMAND.getPrefix() + "reset all §f- 重置所有配置");
                    ChatUtil.addChatMessage("  §7" + Sakura.COMMAND.getPrefix() + "reset module <名称> §f- 重置指定模块");
                    return 1;
                });
    }

    private void resetAll() {
        for (Module module : Sakura.MODULES.getAllModules()) {
            module.reset();
        }
        resetClickGuiPanels();
    }

    private void resetClickGuiPanels() {
        float xOffset = 50;
        for (CategoryPanel panel : Sakura.CLICKGUI.getPanels()) {
            panel.setX(xOffset);
            panel.setY(20);
            panel.setOpened(true);
            xOffset += panel.getWidth() + 20;
        }
        Sakura.CLICKGUI.scroll = 0;
    }
}
