package dev.mahiro.client.utils;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.client.ClickGui;

import java.util.HashMap;
import java.util.Map;

public class TranslationManager {
    private static final Map<String, Map<ClickGui.LanguageMode, String>> translations = new HashMap<>();

    static {
        register("welcome.title", "Welcome to LemonClient", "欢迎使用 " + Mahiro.MOD_NAME);
        register("welcome.subtitle", "The best utility mod for BJD", "布吉岛最强辅助模组");

        register("nav.prev", "Previous", "上一步");
        register("nav.next", "Next", "下一步");
        register("nav.finish", "Finish", "完成");

        register("wizard.step.welcome", "Welcome", "欢迎");
        register("wizard.step.language", "LanguageMode", "语言设置");
        register("wizard.step.theme", "Theme & Preview", "主题与预览");
        register("wizard.step.ready", "Ready", "准备就绪");

        register("theme.main_color", "Main Color", "主色调");
        register("theme.preview", "ClickGui Preview", "界面预览");
        register("theme.preview.desc", "Real-time preview of your theme", "主题颜色实时预览");

        register("ready.title", "You are all set!", "配置已完成！");
        register("ready.info", "Press 'Right Shift' to open ClickGUI in game.", "按 '右 Shift' 键在游戏中打开 ClickGUI。");

        register("settings.title", "Settings", "设置");
        register("settings.back", "Back", "返回");
        register("settings.language", "LanguageMode: ", "语言: ");

        register("theme.main", "Main", "主色");
        register("theme.second", "Second", "副色");

        register("color.red", "R", "红");
        register("color.green", "G", "绿");
        register("color.blue", "B", "蓝");
        register("color.alpha", "Alpha", "透明度");
        register("color.hex", "Hex", "十六进制");

        register("colormode.fade", "Fade", "渐变");
        register("colormode.rainbow", "Rainbow", "彩虹");
        register("colormode.astolfo", "Astolfo", "阿斯托尔福");
        register("colormode.dynamic", "Dynamic", "动态");
        register("colormode.tenacity", "Tenacity", "Tenacity");
        register("colormode.static", "Static", "静态");
        register("colormode.double", "Double", "双色");
    }

    public static void register(String key, String en, String zh) {
        Map<ClickGui.LanguageMode, String> langMap = new HashMap<>();
        langMap.put(ClickGui.LanguageMode.English, en);
        langMap.put(ClickGui.LanguageMode.Chinese, zh);
        translations.put(key, langMap);
    }

    public static String get(String key) {
        Map<ClickGui.LanguageMode, String> langMap = translations.get(key);
        if (langMap == null) return key;
        return langMap.getOrDefault(ClickGui.language.get(), key);
    }
}
