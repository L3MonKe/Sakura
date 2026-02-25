package dev.sakura.client.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.sakura.client.Sakura;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final List<LiteralArgumentBuilder<CommandSource>> builders = new ArrayList<>();

    public Command(String name, String description, LiteralArgumentBuilder<CommandSource> builder) {
        this.name = name;
        this.description = description;
        builders.add(builder);
    }

    public Command(String name, String description, List<LiteralArgumentBuilder<CommandSource>> builders) {
        this.name = name;
        this.description = description;
        this.builders.addAll(builders);
    }

    public abstract void buildCommand(LiteralArgumentBuilder<CommandSource> builder);

    protected static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    protected static List<LiteralArgumentBuilder<CommandSource>> literal(String... names) {
        List<LiteralArgumentBuilder<CommandSource>> builders = Lists.newArrayList();
        for (String name : names) {
            builders.add(LiteralArgumentBuilder.literal(name));
        }
        return builders;
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected static com.mojang.brigadier.suggestion.SuggestionProvider<CommandSource> suggest(String... suggestions) {
        return (context, builder) -> CommandSource.suggestMatching(Lists.newArrayList(suggestions), builder);
    }

    public List<LiteralArgumentBuilder<CommandSource>> getCommandBuilders() {
        return builders;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        LiteralArgumentBuilder<CommandSource> builder = builders.getFirst();
        return Sakura.COMMAND.getDispatcher().getAllUsage(builder.build(), Sakura.COMMAND.getSource(), false)[0];
    }
}
