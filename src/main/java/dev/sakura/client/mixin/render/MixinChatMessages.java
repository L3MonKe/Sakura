package dev.sakura.client.mixin.render;

import com.google.common.collect.Lists;
import dev.sakura.client.utils.render.ChatGradientText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ChatMessages.class)
public class MixinChatMessages {
    private static final OrderedText SAKURA_SPACES = OrderedText.styled(32, Style.EMPTY);

    @Inject(method = "breakRenderedChatMessageLines", at = @At("HEAD"), cancellable = true)
    private static void onBreakRenderedChatMessageLines(StringVisitable message, int width, TextRenderer textRenderer, CallbackInfoReturnable<List<OrderedText>> cir) {
        TextCollector textCollector = new TextCollector();
        message.visit((style, text) -> {
            String rendered = MinecraftClient.getInstance().options.getChatColors().getValue() ? text : Formatting.strip(text);
            textCollector.add(StringVisitable.styled(rendered, style));
            return Optional.empty();
        }, Style.EMPTY);

        ArrayList<OrderedText> list = Lists.newArrayList();
        textRenderer.getTextHandler().wrapLines(textCollector.getCombined(), width, Style.EMPTY, (text, lastLineWrapped) -> {
            OrderedText orderedText = Language.getInstance().reorder(text);
            OrderedText out = lastLineWrapped ? OrderedText.concat(SAKURA_SPACES, orderedText) : orderedText;
            list.add(out);

            String raw = rawString(text);
            ChatGradientText.putRawLine(out, lastLineWrapped ? " " + raw : raw);
        });

        if (list.isEmpty()) {
            cir.setReturnValue(Lists.newArrayList(OrderedText.EMPTY));
        } else {
            cir.setReturnValue(list);
        }
        cir.cancel();
    }

    private static String rawString(StringVisitable visitable) {
        StringBuilder sb = new StringBuilder();
        visitable.visit(s -> {
            sb.append(s);
            return Optional.empty();
        });
        return sb.toString();
    }
}

