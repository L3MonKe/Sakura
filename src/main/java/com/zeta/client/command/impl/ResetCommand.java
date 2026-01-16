package com.zeta.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zeta.client.Zeta;
import com.zeta.client.command.Command;
import com.zeta.client.command.ModuleArgumentType;
import com.zeta.client.gui.clickgui.panel.CategoryPanel;
import com.zeta.client.module.Module;
import com.zeta.client.utils.client.ChatUtil;
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
                    ChatUtil.addChatMessage("  §7" + Zeta.COMMAND.getPrefix() + "reset all §f- 重置所有配置");
                    ChatUtil.addChatMessage("  §7" + Zeta.COMMAND.getPrefix() + "reset module <名称> §f- 重置指定模块");
                    return 1;
                });
    }

    private void resetAll() {
        for (Module module : Zeta.MODULES.getAllModules()) {
            module.reset();
        }
        resetClickGuiPanels();
    }

    private void resetClickGuiPanels() {
        float xOffset = 50;
        for (CategoryPanel panel : Zeta.CLICKGUI.getPanels()) {
            panel.setX(xOffset);
            panel.setY(20);
            panel.setOpened(true);
            xOffset += panel.getWidth() + 20;
        }
        Zeta.CLICKGUI.scroll = 0;
    }
}
