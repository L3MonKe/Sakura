package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.StringValue;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NameProtect 模块 - 参考 LiquidBounce 实现
 * 在客户端替换玩家名称以保护隐私
 */
public class NameProtect extends Module {

    // 配置选项
    private final StringValue replacement = new StringValue("Replacement", "替换名称", "Sakura");
    private final BoolValue obfuscateOthers = new BoolValue("ObfuscateOthers", "混淆其他玩家", true);

    // 名称映射管理
    private final NameProtectMappings mappings = new NameProtectMappings();

    // 缓存系统 - 参考 LiquidBounce 的 LfuCache
    private final Map<String, String> stringCache = new ConcurrentHashMap<>(512);
    private final Map<OrderedText, WrappedOrderedText> orderedTextCache = new ConcurrentHashMap<>(512);

    public NameProtect() {
        super("NameProtect", "名称保护", Category.Player);
    }

    @Override
    protected void onEnable() {
        stringCache.clear();
        orderedTextCache.clear();
    }

    @Override
    protected void onDisable() {
        stringCache.clear();
        orderedTextCache.clear();
    }

    @EventHandler
    public void onTick(PlayerTickEvent event) {
        if (nullCheck()) return;

        // 更新映射
        String playerName = mc.player.getNameForScoreboard();

        // 获取其他玩家列表
        List<String> otherPlayers = new ArrayList<>();
        if (obfuscateOthers.get() && mc.getNetworkHandler() != null) {
            mc.getNetworkHandler().getPlayerList().forEach(entry -> {
                String name = entry.getProfile().name();
                if (name != null && !name.equals(playerName)) {
                    otherPlayers.add(name);
                }
            });
        }

        // 更新映射
        mappings.update(playerName, replacement.get(), otherPlayers);

        // 清理缓存（每秒清理一次）
        if (mc.player.age % 20 == 0) {
            if (stringCache.size() > 1000) {
                stringCache.clear();
            }
            if (orderedTextCache.size() > 1000) {
                orderedTextCache.clear();
            }
        }
    }

    /**
     * 替换字符串中的玩家名称 - 完全参考 LiquidBounce 的 replace 方法
     */
    public String replace(String original) {
        // LiquidBounce: when { !running -> original
        if (!isEnabled()) {
            return original;
        }

        if (original == null || original.isEmpty()) {
            return original;
        }

        // 使用缓存 - LiquidBounce: mc.isSameThread -> stringMappingCache.getOrPut
        return stringCache.computeIfAbsent(original, this::uncachedReplace);
    }

    /**
     * 不使用缓存的替换方法 - 参考 LiquidBounce 的 uncachedReplace
     */
    private String uncachedReplace(String original) {
        List<NameProtectMappings.Replacement> replacements = mappings.findReplacements(original);

        if (replacements.isEmpty()) {
            return original;
        }

        StringBuilder result = new StringBuilder(original.length());
        int currReplacementIndex = 0;
        int currentIndex = 0;

        while (currentIndex < original.length()) {
            NameProtectMappings.Replacement replacement =
                    currReplacementIndex < replacements.size() ? replacements.get(currReplacementIndex) : null;

            int replacementStartIdx = replacement != null ? replacement.start : -1;

            if (replacementStartIdx == currentIndex) {
                result.append(replacement.newName);
                currentIndex = replacement.end + 1;
                currReplacementIndex++;
            } else {
                int maxCopyIdx = replacementStartIdx != -1 ? replacementStartIdx : original.length();
                result.append(original, currentIndex, maxCopyIdx);
                currentIndex = maxCopyIdx;
            }
        }

        return result.toString();
    }

    /**
     * 包装 OrderedText 以应用名称保护 - 完全参考 LiquidBounce 的 wrap 方法
     */
    public OrderedText wrap(OrderedText original) {
        // LiquidBounce: when { !running -> original
        if (!isEnabled()) {
            return original;
        }

        if (original == null) {
            return original;
        }

        // 使用缓存 - LiquidBounce: mc.isSameThread -> orderedTextMappingCache.getOrPut
        return orderedTextCache.computeIfAbsent(original, this::uncachedWrap);
    }

    /**
     * 不使用缓存的包装方法 - 参考 LiquidBounce 的 uncachedWrap
     */
    private WrappedOrderedText uncachedWrap(OrderedText original) {
        List<MappedCharacter> originalCharacters = new ArrayList<>();

        // 提取原始字符
        original.accept((index, style, codePoint) -> {
            originalCharacters.add(new MappedCharacter(style, codePoint));
            return true;
        });

        // 构建文本字符串用于查找替换
        StringBuilder textBuilder = new StringBuilder(originalCharacters.size());
        for (MappedCharacter c : originalCharacters) {
            textBuilder.appendCodePoint(c.codePoint);
        }

        List<NameProtectMappings.Replacement> replacements = mappings.findReplacements(textBuilder.toString());

        if (replacements.isEmpty()) {
            return new WrappedOrderedText(originalCharacters);
        }

        // 构建新的字符列表
        List<MappedCharacter> mappedCharacters = new ArrayList<>();
        int currReplacementIndex = 0;
        int currentIndex = 0;

        while (currentIndex < originalCharacters.size()) {
            NameProtectMappings.Replacement replacement =
                    currReplacementIndex < replacements.size() ? replacements.get(currReplacementIndex) : null;

            int replacementStartIdx = replacement != null ? replacement.start : -1;

            if (replacementStartIdx == currentIndex) {
                // 应用替换
                String newName = replacement.newName;

                // 如果是玩家自己，使用渐变色并添加阴影
                if (replacement.useGradient) {
                    for (int i = 0; i < newName.length(); i++) {
                        // 使用 ColorUtil.interpolateColorsBackAndForth 实现丝滑渐变
                        Color gradientColor = dev.sakura.client.utils.color.ColorUtil.interpolateColorsBackAndForth(
                                10,  // speed - 渐变速度（越小越快）
                                i * 15,  // index - 字符索引 * 颜色跨度
                                dev.sakura.client.module.impl.client.ClickGui.mainColor.get(),
                                dev.sakura.client.module.impl.client.ClickGui.secondColor.get(),
                                false  // trueColor - 使用 RGB 插值而不是 HSB
                        );

                        // 计算阴影颜色（原色的 25% 亮度）
                        int shadowColor = net.minecraft.util.math.ColorHelper.scaleRgb(gradientColor.getRGB(), 0.25f);
                        Style coloredStyle = originalCharacters.get(currentIndex).style
                                .withColor(gradientColor.getRGB())
                                .withShadowColor(shadowColor);
                        mappedCharacters.add(new MappedCharacter(coloredStyle, newName.charAt(i)));
                    }
                } else {
                    // 其他玩家使用白色，不添加阴影
                    Style coloredStyle = originalCharacters.get(currentIndex).style
                            .withColor(replacement.color.getRGB());
                    for (char c : newName.toCharArray()) {
                        mappedCharacters.add(new MappedCharacter(coloredStyle, c));
                    }
                }

                currentIndex = replacement.end + 1;
                currReplacementIndex++;
            } else {
                int maxCopyIdx = replacementStartIdx != -1 ? replacementStartIdx : originalCharacters.size();

                // 复制未替换的字符
                for (int i = currentIndex; i < maxCopyIdx; i++) {
                    mappedCharacters.add(originalCharacters.get(i));
                }

                currentIndex = maxCopyIdx;
            }
        }

        return new WrappedOrderedText(mappedCharacters);
    }

    /**
     * 映射字符类 - 参考 LiquidBounce 的 MappedCharacter
     */
    private static class MappedCharacter {
        final Style style;
        final int codePoint;

        MappedCharacter(Style style, int codePoint) {
            this.style = style;
            this.codePoint = codePoint;
        }
    }

    /**
     * 包装的 OrderedText - 参考 LiquidBounce 的 WrappedOrderedText
     */
    private static class WrappedOrderedText implements OrderedText {
        final List<MappedCharacter> mappedCharacters;

        WrappedOrderedText(List<MappedCharacter> mappedCharacters) {
            this.mappedCharacters = mappedCharacters;
        }

        @Override
        public boolean accept(CharacterVisitor visitor) {
            for (int i = 0; i < mappedCharacters.size(); i++) {
                MappedCharacter c = mappedCharacters.get(i);
                if (!visitor.accept(i, c.style, c.codePoint)) {
                    return false;
                }
            }
            return true;
        }
    }
}
