package dev.mzc.client.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.mzc.client.Sakura;
import dev.mzc.client.command.Command;
import dev.mzc.client.command.ModuleArgumentType;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.client.ChatUtil;
import dev.mzc.client.utils.client.KeyUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

import net.minecraft.world.GameMode;

import dev.mzc.client.manager.Managers;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.impl.render.AntiVanish;
import dev.mzc.client.module.impl.render.AttackEffect;
import dev.mzc.client.module.impl.render.BlockESP;
import dev.mzc.client.module.impl.render.Hat;
import dev.mzc.client.module.impl.render.JumpCircles;
import dev.mzc.client.module.impl.render.KillEffect;
import dev.mzc.client.module.impl.misc.NameProtect;
import dev.mzc.client.module.impl.render.NameTags;
import dev.mzc.client.module.impl.render.Trail;
import dev.mzc.client.module.impl.render.ViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MZCCommand extends Command {
    private static GameMode originalGameMode = null;
    private static final List<Module> hiddenModules = new ArrayList<>();
    
    // We store the class names because referencing the classes directly in the static list 
    // before ModuleManager is initialized might cause issues if they haven't been loaded,
    // though here it's likely fine. But to be safe and cleaner:
    private static final List<Class<? extends Module>> RENDER_MODULES_TO_HIDE = new ArrayList<>();

    static {
        RENDER_MODULES_TO_HIDE.add(Hat.class);
        RENDER_MODULES_TO_HIDE.add(JumpCircles.class);
        RENDER_MODULES_TO_HIDE.add(Trail.class);
        RENDER_MODULES_TO_HIDE.add(KillEffect.class);
        RENDER_MODULES_TO_HIDE.add(NameTags.class);
        RENDER_MODULES_TO_HIDE.add(ViewModel.class);
        RENDER_MODULES_TO_HIDE.add(AttackEffect.class);
        RENDER_MODULES_TO_HIDE.add(BlockESP.class);
        RENDER_MODULES_TO_HIDE.add(AntiVanish.class);
        RENDER_MODULES_TO_HIDE.add(NameProtect.class);
    }

    public MZCCommand() {
        super("MZC", "MZC main command", literal("MZC", "mzc"));
    }

    private void hideHudModules() {
        hiddenModules.clear();
        for (Module module : Sakura.MODULES.getAllModules()) {
            boolean shouldHide = false;
            if (module instanceof HudModule) {
                shouldHide = true;
            } else {
                for (Class<? extends Module> renderClass : RENDER_MODULES_TO_HIDE) {
                    if (renderClass.isInstance(module)) {
                        shouldHide = true;
                        break;
                    }
                }
            }

            if (shouldHide && module.isEnabled()) {
                hiddenModules.add(module);
                module.setState(false);
            }
        }
        Sakura.UI_HIDDEN = true;
    }

    private void showHudModules() {
        Sakura.UI_HIDDEN = false;
        for (Module module : hiddenModules) {
            if (!module.isEnabled()) {
                module.setState(true);
            }
        }
        hiddenModules.clear();
    }

    private void updateOriginalGameMode() {
        if (originalGameMode == null) {
            originalGameMode = mc.interactionManager.getCurrentGameMode();
        }
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(literal("gamemode")
                .then(literal("creative")
                    .executes(c -> {
                        updateOriginalGameMode();
                        mc.interactionManager.setGameMode(GameMode.CREATIVE);
                        ChatUtil.addChatMessage("Set client-side gamemode to §aCreative§f.");
                        return 1;
                    }))
                .then(literal("survival")
                    .executes(c -> {
                        updateOriginalGameMode();
                        mc.interactionManager.setGameMode(GameMode.SURVIVAL);
                        ChatUtil.addChatMessage("Set client-side gamemode to §aSurvival§f.");
                        return 1;
                    }))
                .then(literal("adventure")
                    .executes(c -> {
                        updateOriginalGameMode();
                        mc.interactionManager.setGameMode(GameMode.ADVENTURE);
                        ChatUtil.addChatMessage("Set client-side gamemode to §aAdventure§f.");
                        return 1;
                    }))
                .then(literal("spectator")
                    .executes(c -> {
                        updateOriginalGameMode();
                        mc.interactionManager.setGameMode(GameMode.SPECTATOR);
                        ChatUtil.addChatMessage("Set client-side gamemode to §aSpectator§f.");
                        return 1;
                    }))
                .then(literal("default")
                    .executes(c -> {
                        if (originalGameMode != null) {
                            mc.interactionManager.setGameMode(originalGameMode);
                            ChatUtil.addChatMessage("Reverted client-side gamemode to §a" + originalGameMode.getName() + "§f.");
                            originalGameMode = null;
                        } else {
                            ChatUtil.addChatMessage("§cNo previous gamemode stored. Defaulting to Survival.");
                            mc.interactionManager.setGameMode(GameMode.SURVIVAL);
                        }
                        return 1;
                    })))
            .then(literal("toggle")
                .then(argument("module", ModuleArgumentType.module())
                    .executes(c -> {
                        Module module = ModuleArgumentType.getModule(c, "module");
                        module.toggle();
                        ChatUtil.addChatMessage(module.getEnglishName() + " is now " +
                                (module.isEnabled() ? "§aenabled" : "§cdisabled") + "§f.");
                        return 1;
                    })
                    .then(literal("on")
                        .executes(c -> {
                            Module module = ModuleArgumentType.getModule(c, "module");
                            if (!module.isEnabled()) {
                                module.toggle();
                                ChatUtil.addChatMessage(module.getEnglishName() + " is now §aenabled§f.");
                            } else {
                                ChatUtil.addChatMessage(module.getEnglishName() + " is already §aenabled§f.");
                            }
                            return 1;
                        }))
                    .then(literal("off")
                        .executes(c -> {
                            Module module = ModuleArgumentType.getModule(c, "module");
                            if (module.isEnabled()) {
                                module.toggle();
                                ChatUtil.addChatMessage(module.getEnglishName() + " is now §cdisabled§f.");
                            } else {
                                ChatUtil.addChatMessage(module.getEnglishName() + " is already §cdisabled§f.");
                            }
                            return 1;
                        }))))
            .then(literal("config")
                .then(literal("save")
                    .then(argument("name", StringArgumentType.string())
                        .executes(c -> {
                            String name = StringArgumentType.getString(c, "name");
                            if (Sakura.CONFIG.saveConfig(name)) {
                                ChatUtil.addChatMessage("Saved config §a" + name + "§f.");
                            } else {
                                ChatUtil.addChatMessage("§cFailed to save config " + name + ".");
                            }
                            return 1;
                        })))
                .then(literal("load")
                    .then(argument("name", StringArgumentType.string())
                        .executes(c -> {
                            String name = StringArgumentType.getString(c, "name");
                            if (Sakura.CONFIG.loadConfig(name)) {
                                ChatUtil.addChatMessage("Loaded config §a" + name + "§f.");
                            } else {
                                ChatUtil.addChatMessage("§cFailed to load config " + name + " (not found?).");
                            }
                            return 1;
                        }))))
            .then(literal("friend")
                .then(literal("add")
                    .then(argument("name", StringArgumentType.string())
                        .suggests((context, suggestionsBuilder) -> {
                            if (mc.getNetworkHandler() != null) {
                                return CommandSource.suggestMatching(
                                        mc.getNetworkHandler().getPlayerList().stream()
                                                .map(entry -> entry.getProfile().getName())
                                                .filter(name -> !Managers.FRIEND.isFriend(name)),
                                        suggestionsBuilder);
                            }
                            return suggestionsBuilder.buildFuture();
                        })
                        .executes(c -> {
                            String name = StringArgumentType.getString(c, "name");
                            if (Managers.FRIEND.isFriend(name)) {
                                ChatUtil.addChatMessage("§c" + name + " is already a friend.");
                            } else {
                                Managers.FRIEND.addFriend(name);
                                ChatUtil.addChatMessage("Added §a" + name + "§f as a friend.");
                            }
                            return 1;
                        })))
                .then(literal("remove")
                    .then(argument("name", StringArgumentType.string())
                        .suggests((context, suggestionsBuilder) -> CommandSource.suggestMatching(Managers.FRIEND.getFriends(), suggestionsBuilder))
                        .executes(c -> {
                            String name = StringArgumentType.getString(c, "name");
                            if (!Managers.FRIEND.isFriend(name)) {
                                ChatUtil.addChatMessage("§c" + name + " is not a friend.");
                            } else {
                                Managers.FRIEND.removeFriend(name);
                                ChatUtil.addChatMessage("Removed §a" + name + "§f from friends.");
                            }
                            return 1;
                        })))
                .then(literal("list")
                    .executes(c -> {
                        if (Managers.FRIEND.getFriends().isEmpty()) {
                            ChatUtil.addChatMessage("You have no friends.");
                        } else {
                            ChatUtil.addChatMessage("Friends: §a" + String.join(", ", Managers.FRIEND.getFriends()));
                        }
                        return 1;
                    }))
                .then(literal("clear")
                    .executes(c -> {
                        Managers.FRIEND.clearFriends();
                        ChatUtil.addChatMessage("Cleared all friends.");
                        return 1;
                    })))
            .then(literal("hide")
                .executes(c -> {
                    if (Sakura.UI_HIDDEN) {
                        showHudModules();
                        ChatUtil.addChatMessage("UI is now §ashown§f.");
                    } else {
                        hideHudModules();
                        ChatUtil.addChatMessage("UI is now §chidden§f.");
                    }
                    return 1;
                })
                .then(literal("on")
                    .executes(c -> {
                        if (!Sakura.UI_HIDDEN) {
                            hideHudModules();
                            ChatUtil.addChatMessage("UI is now §chidden§f.");
                        } else {
                            ChatUtil.addChatMessage("UI is already §chidden§f.");
                        }
                        return 1;
                    }))
                .then(literal("off")
                    .executes(c -> {
                        if (Sakura.UI_HIDDEN) {
                            showHudModules();
                            ChatUtil.addChatMessage("UI is now §ashown§f.");
                        } else {
                            ChatUtil.addChatMessage("UI is already §ashown§f.");
                        }
                        return 1;
                    })))
            .then(literal("bind")
                .then(argument("module", ModuleArgumentType.module())
                    .executes(c -> {
                        Module module = ModuleArgumentType.getModule(c, "module");
                        module.setKey(InputUtil.UNKNOWN_KEY.getCode());
                        ChatUtil.addChatMessage("Unbound " + module.getEnglishName() + ".");
                        Sakura.CONFIG.saveDefaultConfig();
                        return 1;
                    })
                    .then(argument("key", StringArgumentType.string())
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
                            // Default to toggle mode for quick bind
                            module.setBindMode(Module.BindMode.Toggle); 
                            ChatUtil.addChatMessage("Bound " + module.getEnglishName() + " to " + keyName.toUpperCase() + ".");
                            Sakura.CONFIG.saveDefaultConfig();
                            return 1;
                        }))))
            .then(literal("tp")
                .then(argument("x", DoubleArgumentType.doubleArg())
                    .then(argument("y", DoubleArgumentType.doubleArg())
                        .then(argument("z", DoubleArgumentType.doubleArg())
                            .executes(c -> {
                                double x = DoubleArgumentType.getDouble(c, "x");
                                double y = DoubleArgumentType.getDouble(c, "y");
                                double z = DoubleArgumentType.getDouble(c, "z");

                                mc.player.updatePosition(x, y, z);
                                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true, false));

                                ChatUtil.addChatMessage("Teleported to " + x + ", " + y + ", " + z);
                                return 1;
                            }))))
                .then(argument("player", StringArgumentType.string())
                    .suggests((context, suggestionsBuilder) -> {
                        if (mc.getNetworkHandler() != null) {
                            return CommandSource.suggestMatching(
                                    mc.getNetworkHandler().getPlayerList().stream()
                                            .map(entry -> entry.getProfile().getName()),
                                    suggestionsBuilder);
                        }
                        return suggestionsBuilder.buildFuture();
                    })
                    .executes(c -> {
                        String targetName = StringArgumentType.getString(c, "player");
                        PlayerEntity target = null;
                        for (PlayerEntity p : mc.world.getPlayers()) {
                            if (p.getName().getString().equalsIgnoreCase(targetName)) {
                                target = p;
                                break;
                            }
                        }

                        if (target != null) {
                            double x = target.getX();
                            double y = target.getY();
                            double z = target.getZ();

                            mc.player.updatePosition(x, y, z);
                            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true, false));

                            ChatUtil.addChatMessage("Teleported to " + targetName + " (" + (int)x + ", " + (int)y + ", " + (int)z + ")");
                        } else {
                            ChatUtil.addChatMessage("§cPlayer " + targetName + " not found.");
                        }
                        return 1;
                    })))
            .executes(c -> {
                ChatUtil.addChatMessage("Usage: /MZC <command>");
                ChatUtil.addChatMessage("Commands: toggle, bind, gamemode, config, friend, hide, tp");
                return 1;
            });
    }
}
