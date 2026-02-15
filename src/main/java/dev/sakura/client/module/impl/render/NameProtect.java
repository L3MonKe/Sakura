package dev.sakura.client.module.impl.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import dev.sakura.client.values.impl.StringValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NameProtect extends Module {
    private final StringValue name = new StringValue("Name", "名字", "Sakura");
    private final BoolValue color = new BoolValue("Color", "启用颜色", true);
    
    private enum ColorMode {
        Single,
        Gradient
    }
    
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("ColorMode", "颜色模式", ColorMode.Single, color::get);
    
    private final ColorValue colorValue = new ColorValue("ColorValue", "单色", new Color(255, 255, 255), () -> color.get() && colorMode.is(ColorMode.Single));
    
    private final ColorValue gradientColor1 = new ColorValue("Gradient1", "渐变色1", new Color(255, 183, 197), () -> color.get() && colorMode.is(ColorMode.Gradient));
    private final ColorValue gradientColor2 = new ColorValue("Gradient2", "渐变色2", new Color(255, 133, 161), () -> color.get() && colorMode.is(ColorMode.Gradient));
    
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 4.0, 1.0, 10.0, 0.5, () -> color.get() && colorMode.is(ColorMode.Gradient));
    private final NumberValue<Double> separation = new NumberValue<>("Separation", "间隔", 20.0, 1.0, 100.0, 1.0, () -> color.get() && colorMode.is(ColorMode.Gradient));

    public NameProtect() {
        super("NameProtect", "名字保护", Category.Render);
    }

    public static String replace(String text) {
        if (text == null) return null;
        NameProtect module = Sakura.MODULES.getModule(NameProtect.class);
        if (module == null || !module.isEnabled()) return text;

        String username = MinecraftClient.getInstance().getSession().getUsername();
        if (text.contains(username)) {
            return text.replace(username, module.name.get());
        }
        return text;
    }

    public static OrderedText replace(OrderedText text) {
        if (text == null) return null;
        NameProtect module = Sakura.MODULES.getModule(NameProtect.class);
        if (module == null || !module.isEnabled()) return text;

        String username = MinecraftClient.getInstance().getSession().getUsername();

        List<MappedCharacter> characters = new ArrayList<>();
        text.accept((index, style, codePoint) -> {
            characters.add(new MappedCharacter(style, codePoint));
            return true;
        });

        StringBuilder sb = new StringBuilder();
        for (MappedCharacter c : characters) {
            sb.appendCodePoint(c.codePoint);
        }
        String content = sb.toString();

        if (!content.contains(username)) return text;

        String replacement = module.name.get();
        boolean enableColor = module.color.get();
        ColorMode mode = module.colorMode.get();

        int singleColor = module.colorValue.get().getRGB();

        Color g1 = module.gradientColor1.get();
        Color g2 = module.gradientColor2.get();
        int gradSpeed = Math.max(1, module.speed.get().intValue());
        int gradSep = Math.max(1, module.separation.get().intValue());

        List<MappedCharacter> newCharacters = new ArrayList<>();
        int currentIndex = 0;

        while (currentIndex < content.length()) {
            int index = content.indexOf(username, currentIndex);
            if (index == -1) {
                for (int i = currentIndex; i < characters.size(); i++) {
                    newCharacters.add(characters.get(i));
                }
                break;
            }

            for (int i = currentIndex; i < index; i++) {
                newCharacters.add(characters.get(i));
            }

            Style style = characters.get(index).style;
            
            for (int i = 0; i < replacement.length(); i++) {
                Style charStyle = style;
                
                if (enableColor) {
                    int charColor;
                    if (mode == ColorMode.Single) {
                        charColor = singleColor;
                    } else {
                        Color pulse = ColorUtil.interpolateColorsBackAndForth(gradSpeed, gradSep * i, g1, g2, false);
                        charColor = pulse.getRGB();
                    }
                    charStyle = style.withColor(charColor);
                }
                
                newCharacters.add(new MappedCharacter(charStyle, replacement.codePointAt(i)));
            }

            currentIndex = index + username.length();
        }

        return new WrappedOrderedText(newCharacters);
    }

    private static class MappedCharacter {
        final Style style;
        final int codePoint;

        MappedCharacter(Style style, int codePoint) {
            this.style = style;
            this.codePoint = codePoint;
        }
    }

    private static class WrappedOrderedText implements OrderedText {
        private final List<MappedCharacter> characters;

        WrappedOrderedText(List<MappedCharacter> characters) {
            this.characters = characters;
        }

        @Override
        public boolean accept(CharacterVisitor visitor) {
            for (int i = 0; i < characters.size(); i++) {
                MappedCharacter c = characters.get(i);
                if (!visitor.accept(i, c.style, c.codePoint)) {
                    return false;
                }
            }
            return true;
        }
    }
}
