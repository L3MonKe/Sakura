package dev.sakura.client.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCTransport;
import dev.sakura.client.verify.packet.implemention.c2s.ServerBoundCloudConfigPacket;
import dev.sakura.client.verify.protocol.IRCProtocol;
import dev.sakura.client.verify.util.ExitUtil;
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
            IRCTransport t = requireAuthedTransport();
            if (t == null) {
                return 1;
            }
            t.listCloudConfigs();
            return 1;
        }));

        builder.then(literal("load")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    t.getCloudConfig(getString(c, "name"));
                    return 1;
                }))
                .then(argument("user", string())
                        .then(argument("name", string()).executes(c -> {
                            IRCTransport t = requireAuthedTransport();
                            if (t == null) {
                                return 1;
                            }
                            t.getCloudConfig(getString(c, "user"), getString(c, "name"));
                            return 1;
                        })))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config load [用户] <配置名>");
                    return 1;
                }));

        builder.then(literal("get")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    t.getCloudConfig(getString(c, "name"));
                    return 1;
                }))
                .then(argument("user", string())
                        .then(argument("name", string()).executes(c -> {
                            IRCTransport t = requireAuthedTransport();
                            if (t == null) {
                                return 1;
                            }
                            t.getCloudConfig(getString(c, "user"), getString(c, "name"));
                            return 1;
                        })))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config load [用户] <配置名>");
                    return 1;
                }));

        builder.then(literal("save")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    String content = Sakura.CONFIG.saveConfigToString();
                    String name = getString(c, "name");
                    ServerBoundCloudConfigPacket packet = new ServerBoundCloudConfigPacket("upload", "", name, content);
                    if (!ensurePayloadSizeOk(packet)) {
                        return 1;
                    }
                    ChatUtil.addChatMessage("§7正在上传云配置: §f" + name);
                    t.uploadCloudConfig(name, content);
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config save <name>");
                    return 1;
                }));

        builder.then(literal("upload")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    String content = Sakura.CONFIG.saveConfigToString();
                    String name = getString(c, "name");
                    ServerBoundCloudConfigPacket packet = new ServerBoundCloudConfigPacket("upload", "", name, content);
                    if (!ensurePayloadSizeOk(packet)) {
                        return 1;
                    }
                    ChatUtil.addChatMessage("§7正在上传云配置: §f" + name);
                    t.uploadCloudConfig(name, content);
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config save <name>");
                    return 1;
                }));

        builder.then(literal("delete")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    t.deleteCloudConfig(getString(c, "name"));
                    return 1;
                }))
                .executes(c -> {
                    ChatUtil.addChatMessage("用法: .config delete <name>");
                    return 1;
                }));

        builder.then(literal("remove")
                .then(argument("name", string()).executes(c -> {
                    IRCTransport t = requireAuthedTransport();
                    if (t == null) {
                        return 1;
                    }
                    t.deleteCloudConfig(getString(c, "name"));
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

    private static IRCTransport requireAuthedTransport() {
        if (!AuthState.isAuthed() || AuthState.getExpireAt() <= System.currentTimeMillis()) {
            AuthState.clear();
            ChatUtil.addChatMessage("§c请先完成验证登录/注册，再使用云配置。");
            return null;
        }
        ExitUtil.ensureVerifiedOrExit();
        IRCTransport t = VerificationClient.getTransport();
        if (t == null) {
            try {
                t = VerificationClient.connect(null);
            } catch (Exception e) {
                ChatUtil.addChatMessage("§c验证连接建立失败。");
                return null;
            }

            String user = AuthState.getCurrentUser();
            if (user != null && !user.isBlank()) {
                t.connect(user, dev.sakura.client.verify.util.AuthUtil.authed.get());
            }
        }
        return t;
    }

    private static boolean ensurePayloadSizeOk(ServerBoundCloudConfigPacket packet) {
        try {
            int size = new IRCProtocol().encode(packet).length;
            if (size > 8 * 1024 * 1024) {
                ChatUtil.addChatMessage("§c配置内容过大，无法上传（" + (size / (1024 * 1024)) + "MB）。");
                return false;
            }
            return true;
        } catch (Exception e) {
            ChatUtil.addChatMessage("§c配置编码失败。");
            return false;
        }
    }
}
