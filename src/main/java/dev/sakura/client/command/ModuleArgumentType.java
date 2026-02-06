package dev.sakura.client.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.Module;
import net.minecraft.command.CommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModuleArgumentType implements ArgumentType<Module> {

    public static ModuleArgumentType module() {
        return new ModuleArgumentType();
    }

    public static Module getModule(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Module.class);
    }

    @Override
    public Module parse(StringReader reader) throws CommandSyntaxException {
        String string = reader.readString();
        Module module = Sakura.MODULES.getModuleByString(string);
        if (module == null) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().createWithContext(reader, null);
        }
        return module;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context,
                                                              final SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(
                Sakura.MODULES.getAllModules().stream().map(Module::getEnglishName),
                builder
        );
    }

    @Override
    public Collection<String> getExamples() {
        return Sakura.MODULES.getAllModules().stream()
                .map(Module::getEnglishName)
                .limit(10)
                .toList();
    }
}
