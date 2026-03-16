package dev.sakura.client.utils;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.ClickGui;

import java.util.HashMap;
import java.util.Map;

public class TranslationManager {
    private static final Map<String, Map<ClickGui.LanguageMode, String>> translations = new HashMap<>();

    static {
        register("welcome.title", "Welcome to " + Sakura.MOD_NAME, "欢迎使用 " + Sakura.MOD_NAME);
        register("welcome.subtitle", "The best utility mod for heypixel", "布吉岛最强辅助模组");

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

        register("colormode.fade", "Fade", "消散");
        register("colormode.rainbow", "Rainbow", "彩虹");
        register("colormode.astolfo", "Astolfo", "阿斯托尔福");
        register("colormode.dynamic", "Dynamic", "动态");
        register("colormode.tenacity", "Tenacity", "渐变");
        register("colormode.static", "Static", "静态");
        register("colormode.double", "Double", "双色");

        register("cloudmusic.title", "Cloud Music", "网易云音乐");
        register("cloudmusic.loading", "Loading...", "正在加载...");
        register("cloudmusic.login", "Login", "登录");
        register("cloudmusic.back", "Back", "返回");
        register("cloudmusic.sidebar.recommend", "Recommend", "推荐");
        register("cloudmusic.sidebar.search", "Search", "搜索");
        register("cloudmusic.sidebar.playlists", "My Playlists", "我的歌单");
        register("cloudmusic.empty.not_logged_in", "Not logged in, click top-right to login", "未登录，请点击右上角登录");
        register("cloudmusic.search.coming_soon", "Search is coming soon...", "搜索功能即将上线...");
        register("cloudmusic.player.no_music", "No music playing", "未播放音乐");
        register("cloudmusic.qr.loading", "Loading QR...", "二维码加载中...");
        register("cloudmusic.qr.parse_error", "QR decode error", "二维码解析错误");
        register("cloudmusic.qr.refresh", "Refresh QR", "刷新二维码");
        register("cloudmusic.status.connecting", "Connecting to Cloud Music...", "正在连接网易云接口...");
        register("cloudmusic.status.checking_login", "Checking login status...", "正在检测登录状态...");
        register("cloudmusic.status.welcome", "Welcome, %s", "欢迎你, %s");
        register("cloudmusic.status.not_logged_in", "Not logged in", "未登录");
        register("cloudmusic.status.generating_qr", "Generating QR...", "正在生成二维码...");
        register("cloudmusic.status.qr_fetch_failed", "Failed to fetch QR, please check your network", "二维码获取失败，请检查网络");
        register("cloudmusic.status.login_success", "Login successful", "登录成功");
        register("cloudmusic.status.qr_expired", "QR expired, please refresh", "二维码过期，请刷新");
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
