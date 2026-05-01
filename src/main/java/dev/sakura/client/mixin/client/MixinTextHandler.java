package dev.sakura.client.mixin.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.player.NameProtect;
import net.minecraft.client.font.TextHandler;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * MixinTextHandler - 拦截 TextHandler 的文本宽度计算
 * 用于修复 Tab 栏和名字标签的宽度问题
 * 参考 LiquidBounce 的 MixinStringSplitter
 */
@Mixin(TextHandler.class)
public abstract class MixinTextHandler {

    @Shadow
    @Final
    private TextHandler.WidthRetriever widthRetriever;

    /**
     * 拦截 getWidth(StringVisitable) 方法
     * 这个方法用于计算 FormattedText/StringVisitable 的宽度
     * 对应 LiquidBounce 的 stringWidth(FormattedText) 方法
     */
    @Inject(method = "getWidth(Lnet/minecraft/text/StringVisitable;)F", at = @At("HEAD"), cancellable = true)
    private void injectNameProtectWidth(StringVisitable text, CallbackInfoReturnable<Float> cir) {
        if (Sakura.MODULES == null) {
            return;
        }
        
        NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
        if (nameProtect == null || !nameProtect.isEnabled()) {
            return;
        }

        // 使用 MutableFloat 来累积宽度
        final float[] totalWidth = {0.0f};
        
        // 访问文本内容并替换名称
        text.visit((style, asString) -> {
            // 替换名称
            String replaced = nameProtect.replace(asString);
            
            // 计算替换后文本的宽度
            for (int i = 0; i < replaced.length(); i++) {
                int codePoint = replaced.codePointAt(i);
                totalWidth[0] += widthRetriever.getWidth(codePoint, style);
                
                // 处理代理对（surrogate pairs）
                if (Character.isSupplementaryCodePoint(codePoint)) {
                    i++;
                }
            }
            
            return Optional.empty();
        }, Style.EMPTY);

        cir.setReturnValue(totalWidth[0]);
    }
}
