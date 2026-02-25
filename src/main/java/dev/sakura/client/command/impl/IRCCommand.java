package dev.sakura.client.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.Command;
import dev.sakura.client.events.client.ChatMessageEvent;
import dev.sakura.lemonchat.client.ChatClient;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class IRCCommand extends Command {
    public IRCCommand() {
        super("irc", "Lemon chat.", literal("irc"));
    }

    private final MessageHandler handler = new MessageHandler();

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(literal("bind").executes((context -> {
                    Sakura.EVENT_BUS.subscribe(handler);
                    return SINGLE_SUCCESS;
                }))
        );

        literalArgumentBuilder.then(literal("unbind").executes((context -> {
                    Sakura.EVENT_BUS.unsubscribe(handler);
                    return SINGLE_SUCCESS;
                }))
        );

        literalArgumentBuilder.then(literal("disconnect").executes((context -> {
                    Sakura.EVENT_BUS.unsubscribe(handler);
                    if (ChatClient.get().session != null)
                        ChatClient.get().session.disconnect("Disconnect.");
                    return SINGLE_SUCCESS;
                }))
        );

        literalArgumentBuilder.then(literal("chat").then(argument("message", StringArgumentType.string()).executes((context -> {
                    ChatClient.get().chat(StringArgumentType.getString(context, "message"));
                    return SINGLE_SUCCESS;
                })))
        );

        literalArgumentBuilder.then(literal("login").executes((context -> {
                    if (mc != null && mc.world != null && mc.player != null) {
                        if (ChatClient.get().session != null) {
                            if (!ChatClient.get().session.isConnected()) {
                                try {
                                    ChatClient.get().connect();
                                } catch (InterruptedException ignored) {
                                }
                            }

                            String srv = (mc.isInSingleplayer() ? "local" : mc.getNetworkHandler().getServerInfo().address);
                            ChatClient.get().session.login(mc.getSession().getUsername(), srv);
                        }
                    }
                    return SINGLE_SUCCESS;
                }))
        );
    }

    public static class MessageHandler {
        @EventHandler
        public void onMessage(ChatMessageEvent.Client event) {
            event.cancel();
            ChatClient.get().chat(event.getMessage());
        }
    }
}
