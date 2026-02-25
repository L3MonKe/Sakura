package dev.sakura.client;

import dev.sakura.client.command.CommandManager;
import dev.sakura.client.config.ConfigManager;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.gui.hud.HudEditorScreen;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.ModuleManager;
import dev.sakura.client.utils.render.Shader2DUtil;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='

//         .............................................
//                  佛祖保佑             永无BUG
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

// 程序出Bug了？
// 　　　∩∩
// 　　（´･ω･）
// 　 ＿|　⊃／(＿＿_
// 　／ └-(＿＿＿／
// 　￣￣￣￣￣￣￣
// 算了反正不是我写的
// 　　 ⊂⌒／ヽ-、＿
// 　／⊂_/＿＿＿＿ ／
// 　￣￣￣￣￣￣￣
// 万一是我写的呢
// 　　　∩∩
// 　　（´･ω･）
// 　 ＿|　⊃／(＿＿_
// 　／ └-(＿＿＿／
// 　￣￣￣￣￣￣￣
// 算了反正改了一个又出三个
// 　　 ⊂⌒／ヽ-、＿
// 　／⊂_/＿＿＿＿ ／
// 　￣￣￣￣￣￣￣

/**
 * 江城子 . 程序员之歌
 * <p>
 * 十年生死两茫茫，写程序，到天亮。
 * 千行代码，Bug何处藏。
 * 纵使上线又怎样，朝令改，夕断肠。
 * <p>
 * 领导每天新想法，天天改，日日忙。
 * 相顾无言，惟有泪千行。
 * 每晚灯火阑珊处，夜难寐，加班狂。
 */

public class Sakura implements ClientModInitializer {
    public static final String MOD_NAME = "Sakura";
    public static final String MOD_VER = BuildConfig.VERSION + "-" + BuildConfig.BUILD_IDENTIFIER;

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final IEventBus EVENT_BUS = new EventBus();

    public static Executor EXECUTOR;

    public static MinecraftClient mc;

    public static ModuleManager MODULES;
    public static ConfigManager CONFIG;
    public static CommandManager COMMAND;
    public static ClickGuiScreen CLICKGUI;
    public static HudEditorScreen HUDEDITOR;

    @Override
    public void onInitializeClient() {
        LOGGER.info("正在开始初始化!");

        mc = MinecraftClient.getInstance();

        // 事件巴士(doge 初始化
        EVENT_BUS.registerLambdaFactory(Sakura.class.getPackageName(), (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        EXECUTOR = Executors.newFixedThreadPool(1);

        // 初始化Managers
        Managers.init();

        // 初始化Modules
        MODULES = new ModuleManager();

        // 初始化配置文件系统
        CONFIG = new ConfigManager();

        // 初始化Command系统
        COMMAND = new CommandManager();

        // 初始化ClickGui
        CLICKGUI = new ClickGuiScreen();

        // 初始化HudEditor
        HUDEDITOR = new HudEditorScreen();


        // 初始化Shaders
        Shader2DUtil.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("正在保存配置并且关闭游戏!");
            CONFIG.saveDefaultConfig();
        }));

        LOGGER.info("初始化完成!");
    }
}