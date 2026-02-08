package dev.sakura.client.mixin.accessor;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextFieldWidget.class)
public interface ITextFieldWidget {
    @Accessor("textX")
    int getTextX();

    @Accessor("textY")
    int getTextY();

    @Accessor("firstCharacterIndex")
    int getFirstCharacterIndex();
}

