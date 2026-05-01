package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.player.NameProtect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * MixinFont - 拦截 TextRenderer 的文本渲染
 * 完全参考 LiquidBounce 的 MixinFont 实现
 * Yarn 映射: prepare() = Mojang 映射: prepareText()
 */
@Mixin(TextRenderer.class)
public abstract class MixinFont {

    /**
     * 拦截 prepare 方法 - String 版本
     * 完全参考 LiquidBounce 的 injectNameProtectA
     */
    @ModifyArg(
            method = "prepare(Ljava/lang/String;FFIZI)Lnet/minecraft/client/font/TextRenderer$GlyphDrawable;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"
            ),
            index = 0
    )
    private String injectNameProtectA(String text) {
        if (Sakura.MODULES == null) {
            return text;
        }
        NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
        // LiquidBounce: return ModuleNameProtect.INSTANCE.replace(text)
        // replace 方法内部会检查 running 状态
        if (nameProtect != null) {
            return nameProtect.replace(text);
        }
        return text;
    }

    /**
     * 拦截 prepare 方法 - OrderedText 版本
     * 完全参考 LiquidBounce 的 injectNameProtectB
     */
    @Redirect(
            method = "prepare(Lnet/minecraft/text/OrderedText;FFIZZI)Lnet/minecraft/client/font/TextRenderer$GlyphDrawable;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/text/OrderedText;accept(Lnet/minecraft/text/CharacterVisitor;)Z"
            )
    )
    private boolean injectNameProtectB(OrderedText orderedText, CharacterVisitor visitor) {
        if (Sakura.MODULES == null) {
            return orderedText.accept(visitor);
        }
        NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
        // LiquidBounce: return ModuleNameProtect.INSTANCE.wrap(orderedText).accept(visitor)
        // wrap 方法内部会检查 running 状态
        if (nameProtect != null) {
            return nameProtect.wrap(orderedText).accept(visitor);
        }
        return orderedText.accept(visitor);
    }

    /**
     * 拦截 getWidth 方法 - String 版本
     * 完全参考 LiquidBounce: if (text != null && ModuleNameProtect.INSTANCE.getRunning())
     */
    @ModifyArg(
            method = "getWidth(Ljava/lang/String;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextHandler;getWidth(Ljava/lang/String;)F"
            ),
            index = 0
    )
    private @Nullable String injectNameProtectWidthA(@Nullable String text) {
        if (text != null && Sakura.MODULES != null) {
            NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
            // LiquidBounce 检查 getRunning() 而不是 isEnabled()
            if (nameProtect != null && nameProtect.isEnabled()) {
                return nameProtect.replace(text);
            }
        }
        return text;
    }

    /**
     * 拦截 getWidth 方法 - OrderedText 版本
     * 完全参考 LiquidBounce: return ModuleNameProtect.INSTANCE.wrap(text)
     */
    @ModifyArg(
            method = "getWidth(Lnet/minecraft/text/OrderedText;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextHandler;getWidth(Lnet/minecraft/text/OrderedText;)F"
            ),
            index = 0
    )
    private OrderedText injectNameProtectWidthB(OrderedText text) {
        if (Sakura.MODULES == null) {
            return text;
        }
        NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
        // LiquidBounce 直接调用 wrap，wrap 内部会检查 running
        if (nameProtect != null) {
            return nameProtect.wrap(text);
        }
        return text;
    }
}
