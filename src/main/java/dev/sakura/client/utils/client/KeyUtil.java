package dev.sakura.client.utils.client;

import net.minecraft.client.util.InputUtil;

import java.util.HashMap;
import java.util.Map;

public class KeyUtil {

    private static final Map<String, String> KEY_NAME_MAP = new HashMap<>();

    static {
        KEY_NAME_MAP.put("lshift", "key.keyboard.left.shift");
        KEY_NAME_MAP.put("rshift", "key.keyboard.right.shift");
        KEY_NAME_MAP.put("lctrl", "key.keyboard.left.control");
        KEY_NAME_MAP.put("rctrl", "key.keyboard.right.control");
        KEY_NAME_MAP.put("lalt", "key.keyboard.left.alt");
        KEY_NAME_MAP.put("ralt", "key.keyboard.right.alt");
        KEY_NAME_MAP.put("space", "key.keyboard.space");
        KEY_NAME_MAP.put("enter", "key.keyboard.enter");
        KEY_NAME_MAP.put("tab", "key.keyboard.tab");
        KEY_NAME_MAP.put("escape", "key.keyboard.escape");
        KEY_NAME_MAP.put("caps", "key.keyboard.caps.lock");
        KEY_NAME_MAP.put("backspace", "key.keyboard.backspace");
        KEY_NAME_MAP.put("delete", "key.keyboard.delete");
        KEY_NAME_MAP.put("insert", "key.keyboard.insert");
        KEY_NAME_MAP.put("home", "key.keyboard.home");
        KEY_NAME_MAP.put("end", "key.keyboard.end");
        KEY_NAME_MAP.put("pageup", "key.keyboard.page.up");
        KEY_NAME_MAP.put("pagedown", "key.keyboard.page.down");
    }

    public static InputUtil.Key getKeyFromName(String name) {
        String lower = name.toLowerCase();

        String translationKey = KEY_NAME_MAP.getOrDefault(lower,
                lower.startsWith("key.keyboard.") ? lower : "key.keyboard." + lower);

        try {
            InputUtil.Key key = InputUtil.fromTranslationKey(translationKey);
            return key != null ? key : InputUtil.UNKNOWN_KEY;
        } catch (Exception e) {
            return InputUtil.UNKNOWN_KEY;
        }
    }
}
