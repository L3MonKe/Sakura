package dev.sakura.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.command.impl.*;
import dev.sakura.client.events.client.ChatMessageEvent;
import dev.sakura.client.events.client.GameJoinEvent;
import dev.sakura.client.events.client.SuggestChatEvent;
import dev.sakura.client.events.misc.KeyAction;
import dev.sakura.client.events.misc.KeyEvent;
import dev.sakura.client.module.impl.client.Chat;
import dev.sakura.lemonchat.client.ChatClient;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private final List<Command> commands = new ArrayList<>();
    private String prefix = ".";
    private int prefixKey = GLFW.GLFW_KEY_PERIOD;

    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    private final CommandSource source = new ClientCommandSource(null, mc);

    public CommandManager() {
        Sakura.EVENT_BUS.subscribe(this);

        String savedPrefix = Sakura.CONFIG.loadPrefix();
        if (savedPrefix != null && !savedPrefix.isEmpty()) {
            this.prefix = savedPrefix;
            this.prefixKey = getPrefixKey(savedPrefix);
        }

        register(
                new BindCommand(),
                new HelpCommand(),
                new IRCCommand(),
                new PrefixCommand(),
                new ResetCommand(),
                new SaveCommand(),
                new ToggleCommand()
        );

        for (Command command : commands) {
            for (LiteralArgumentBuilder<CommandSource> builder : command.getCommandBuilders()) {
                command.buildCommand(builder);
                dispatcher.register(builder);
            }
        }
    }

    @EventHandler
    private void onJoin(GameJoinEvent event) {
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

    @EventHandler
    private void onSendMessage(ChatMessageEvent.Client event) {
        String msg = event.getMessage();
        Chat chat = Sakura.MODULES.getModule(Chat.class);

        if (chat.enable.get()) {
            String prefix = chat.prefix.get();

            if (msg.startsWith(prefix)) {
                event.cancel();
                ChatClient.get().chat(msg.substring(prefix.length()));
            }
        }
    }

    @EventHandler(priority = 999)
    public void onChatMessage(ChatMessageEvent.Client event) {
        final String text = event.getMessage().trim();
        if (text.startsWith(prefix)) {
            String literal = text.substring(prefix.length());
            event.setCancelled(true);
            mc.inGameHud.getChatHud().addToMessageHistory(text);
            try {
                dispatcher.execute(dispatcher.parse(literal, source));
            } catch (Exception ignored) {
            }
        }
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (event.getAction() == KeyAction.Press && event.getKey() == prefixKey && mc.currentScreen == null) {
            event.setCancelled(true);
            mc.setScreen(new ChatScreen(""));
        }
    }

    @EventHandler
    public void onChatSuggest(SuggestChatEvent event) {
        event.setPrefix(prefix);
        event.setDispatcher(dispatcher);
        event.setSource(source);
    }

    private void register(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix, int prefixKey) {
        this.prefix = prefix;
        this.prefixKey = prefixKey;
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return dispatcher;
    }

    public CommandSource getSource() {
        return source;
    }

    private int getPrefixKey(String prefix) {
        if (prefix.isEmpty()) return GLFW.GLFW_KEY_PERIOD;

        char firstChar = prefix.charAt(0);
        return switch (firstChar) {
            case '.' -> GLFW.GLFW_KEY_PERIOD;
            case ',' -> GLFW.GLFW_KEY_COMMA;
            case '/' -> GLFW.GLFW_KEY_SLASH;
            case '-' -> GLFW.GLFW_KEY_MINUS;
            case ';' -> GLFW.GLFW_KEY_SEMICOLON;
            default -> GLFW.GLFW_KEY_UNKNOWN;
        };
    }
}
