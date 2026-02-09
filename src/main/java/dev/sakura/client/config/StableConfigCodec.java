package dev.sakura.client.config;

import com.google.gson.*;
import dev.sakura.client.module.Category;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public final class StableConfigCodec {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private StableConfigCodec() {
    }

    public static String encode(ClientConfig cfg) {
        if (cfg == null) {
            cfg = new ClientConfig();
        }

        JsonObject root = new JsonObject();
        root.addProperty("version", cfg.version);
        root.addProperty("prefix", cfg.prefix == null ? "." : cfg.prefix);

        JsonObject gui = new JsonObject();
        JsonObject hudPanel = new JsonObject();
        if (cfg.gui != null && cfg.gui.hudPanel != null) {
            hudPanel.addProperty("x", cfg.gui.hudPanel.x);
            hudPanel.addProperty("y", cfg.gui.hudPanel.y);
        } else {
            hudPanel.addProperty("x", 0.0f);
            hudPanel.addProperty("y", 0.0f);
        }
        gui.add("hudPanel", hudPanel);

        List<ClientConfig.Panel> panels = cfg.gui == null || cfg.gui.panels == null ? List.of() : cfg.gui.panels;
        var panelsArr = new com.google.gson.JsonArray();
        for (ClientConfig.Panel p : panels) {
            if (p == null) continue;
            JsonObject o = new JsonObject();
            o.addProperty("category", p.category == null ? "" : p.category);
            o.addProperty("x", p.x);
            o.addProperty("y", p.y);
            o.addProperty("opened", p.opened);
            panelsArr.add(o);
        }
        gui.add("panels", panelsArr);
        root.add("gui", gui);

        JsonObject modules = new JsonObject();
        if (cfg.modules != null) {
            for (var e : cfg.modules.entrySet()) {
                if (e.getKey() == null || e.getKey().isBlank() || e.getValue() == null) continue;
                ClientConfig.ModuleData d = e.getValue();
                JsonObject o = new JsonObject();
                o.addProperty("enabled", d.enabled);
                o.addProperty("keybind", d.keybind);
                o.addProperty("bindMode", normalizeBindMode(d.bindMode));
                o.addProperty("suffix", d.suffix == null ? "" : d.suffix);
                if (d.hudX != null) o.addProperty("hudX", d.hudX);
                if (d.hudY != null) o.addProperty("hudY", d.hudY);
                if (d.values != null) {
                    JsonObject vo = new JsonObject();
                    for (var ve : d.values.entrySet()) {
                        if (ve.getKey() == null || ve.getKey().isBlank()) continue;
                        JsonElement el = ve.getValue();
                        vo.add(ve.getKey(), el == null ? JsonNull.INSTANCE : el);
                    }
                    o.add("values", vo);
                }
                modules.add(e.getKey(), o);
            }
        }
        root.add("modules", modules);

        return GSON.toJson(root);
    }

    public static ClientConfig decode(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            JsonElement rootEl = JsonParser.parseString(json);
            if (!rootEl.isJsonObject()) {
                return null;
            }
            JsonObject root = rootEl.getAsJsonObject();
            if (root.has("modules") || root.has("gui") || root.has("prefix") || root.has("version")) {
                return decodeStable(root);
            }
            return decodeObfuscated(root);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static ClientConfig decodeStable(JsonObject root) {
        ClientConfig cfg = new ClientConfig();
        cfg.version = getInt(root, "version", 1);
        cfg.prefix = getString(root, "prefix", ".");

        JsonObject guiObj = getObject(root, "gui");
        if (guiObj != null) {
            cfg.gui = new ClientConfig.Gui();
            JsonObject hudObj = getObject(guiObj, "hudPanel");
            if (hudObj != null) {
                cfg.gui.hudPanel.x = getFloat(hudObj, "x", 0.0f);
                cfg.gui.hudPanel.y = getFloat(hudObj, "y", 0.0f);
            }

            var panelsArr = guiObj.get("panels");
            if (panelsArr != null && panelsArr.isJsonArray()) {
                for (JsonElement el : panelsArr.getAsJsonArray()) {
                    if (el == null || !el.isJsonObject()) continue;
                    JsonObject o = el.getAsJsonObject();
                    ClientConfig.Panel p = new ClientConfig.Panel();
                    p.category = getString(o, "category", "");
                    p.x = getFloat(o, "x", 0.0f);
                    p.y = getFloat(o, "y", 0.0f);
                    p.opened = getBool(o, "opened", false);
                    cfg.gui.panels.add(p);
                }
            }
        }

        JsonObject modulesObj = getObject(root, "modules");
        cfg.modules = new LinkedHashMap<>();
        if (modulesObj != null) {
            for (var e : modulesObj.entrySet()) {
                if (e.getKey() == null || e.getKey().isBlank()) continue;
                if (e.getValue() == null || !e.getValue().isJsonObject()) continue;
                JsonObject o = e.getValue().getAsJsonObject();
                ClientConfig.ModuleData d = new ClientConfig.ModuleData();
                d.enabled = getBool(o, "enabled", false);
                d.keybind = getInt(o, "keybind", -1);
                d.bindMode = normalizeBindMode(getString(o, "bindMode", "Toggle"));
                d.suffix = getString(o, "suffix", "");
                if (o.has("hudX")) d.hudX = safeGetFloatObj(o.get("hudX"));
                if (o.has("hudY")) d.hudY = safeGetFloatObj(o.get("hudY"));
                JsonObject values = getObject(o, "values");
                if (values != null) {
                    d.values = new LinkedHashMap<>();
                    for (var ve : values.entrySet()) {
                        if (ve.getKey() == null || ve.getKey().isBlank()) continue;
                        d.values.put(ve.getKey(), ve.getValue() == null ? JsonNull.INSTANCE : ve.getValue());
                    }
                }
                cfg.modules.put(e.getKey(), d);
            }
        }

        return cfg;
    }

    private static ClientConfig decodeObfuscated(JsonObject root) {
        ClientConfig cfg = new ClientConfig();
        cfg.version = guessVersion(root);
        cfg.prefix = guessPrefix(root);

        JsonObject guiObj = guessGuiObject(root);
        if (guiObj != null) {
            cfg.gui = new ClientConfig.Gui();
            var panelsArr = guessPanelsArray(guiObj);
            if (panelsArr != null) {
                for (JsonElement el : panelsArr) {
                    if (el == null || !el.isJsonObject()) continue;
                    JsonObject o = el.getAsJsonObject();
                    ClientConfig.Panel p = new ClientConfig.Panel();
                    p.category = guessCategory(o);
                    float[] xy = guessTwoFloats(o);
                    p.x = xy[0];
                    p.y = xy[1];
                    p.opened = guessFirstBool(o, false);
                    cfg.gui.panels.add(p);
                }
            }

            JsonObject hudPanel = guessHudPanelObject(guiObj);
            if (hudPanel != null) {
                float[] xy = guessTwoFloats(hudPanel);
                cfg.gui.hudPanel.x = xy[0];
                cfg.gui.hudPanel.y = xy[1];
            }
        }

        JsonObject modulesObj = guessModulesObject(root);
        cfg.modules = new LinkedHashMap<>();
        if (modulesObj != null) {
            for (var e : modulesObj.entrySet()) {
                if (e.getKey() == null || e.getKey().isBlank()) continue;
                if (e.getValue() == null || !e.getValue().isJsonObject()) continue;
                JsonObject o = e.getValue().getAsJsonObject();
                ClientConfig.ModuleData d = new ClientConfig.ModuleData();
                d.enabled = guessFirstBool(o, false);
                d.keybind = guessFirstInt(o, -1);
                d.bindMode = normalizeBindMode(guessBindMode(o));
                d.suffix = guessSuffix(o, d.bindMode);
                Float[] hud = guessTwoOptionalFloats(o);
                d.hudX = hud[0];
                d.hudY = hud[1];
                JsonObject values = guessValuesObject(o);
                if (values != null) {
                    d.values = new LinkedHashMap<>();
                    for (var ve : values.entrySet()) {
                        if (ve.getKey() == null || ve.getKey().isBlank()) continue;
                        d.values.put(ve.getKey(), ve.getValue() == null ? JsonNull.INSTANCE : ve.getValue());
                    }
                }
                cfg.modules.put(e.getKey(), d);
            }
        }

        return cfg;
    }

    private static int guessVersion(JsonObject root) {
        for (var e : root.entrySet()) {
            JsonElement v = e.getValue();
            if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isNumber()) {
                int n = safeGetInt(v);
                if (n >= 0 && n <= 100) {
                    return n;
                }
            }
        }
        return 1;
    }

    private static String guessPrefix(JsonObject root) {
        for (var e : root.entrySet()) {
            JsonElement v = e.getValue();
            if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isString()) {
                String s = v.getAsString();
                if (s != null && !s.isBlank() && s.length() <= 8) {
                    return s;
                }
            }
        }
        return ".";
    }

    private static JsonObject guessGuiObject(JsonObject root) {
        for (var e : root.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonObject()) continue;
            JsonObject o = v.getAsJsonObject();
            if (guessPanelsArray(o) != null) {
                return o;
            }
        }
        return null;
    }

    private static com.google.gson.JsonArray guessPanelsArray(JsonObject guiObj) {
        for (var e : guiObj.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonArray()) continue;
            com.google.gson.JsonArray arr = v.getAsJsonArray();
            for (JsonElement el : arr) {
                if (el == null || !el.isJsonObject()) continue;
                if (!guessCategory(el.getAsJsonObject()).isEmpty()) {
                    return arr;
                }
            }
        }
        return null;
    }

    private static JsonObject guessHudPanelObject(JsonObject guiObj) {
        for (var e : guiObj.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonObject()) continue;
            JsonObject o = v.getAsJsonObject();
            float[] xy = guessTwoFloats(o);
            if (xy[0] != 0.0f || xy[1] != 0.0f) {
                return o;
            }
        }
        return null;
    }

    private static String guessCategory(JsonObject panelObj) {
        for (var e : panelObj.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isString()) continue;
            String s = v.getAsString();
            if (s == null) continue;
            for (Category c : Category.values()) {
                if (c.name().equalsIgnoreCase(s)) {
                    return c.name();
                }
            }
        }
        return "";
    }

    private static float[] guessTwoFloats(JsonObject o) {
        List<Float> nums = new ArrayList<>();
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isNumber()) continue;
            String raw = v.getAsString();
            if (raw != null && raw.contains(".")) {
                nums.add(safeGetFloat(v));
            }
        }
        if (nums.size() >= 2) {
            return new float[]{nums.get(0), nums.get(1)};
        }

        nums.clear();
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isNumber()) continue;
            nums.add(safeGetFloat(v));
        }
        if (nums.size() >= 2) {
            return new float[]{nums.get(0), nums.get(1)};
        }
        return new float[]{0.0f, 0.0f};
    }

    private static Float[] guessTwoOptionalFloats(JsonObject o) {
        List<Float> nums = new ArrayList<>();
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isNumber()) continue;
            String raw = v.getAsString();
            if (raw != null && raw.contains(".")) {
                nums.add(safeGetFloat(v));
            }
        }
        if (nums.size() >= 2) {
            return new Float[]{nums.get(0), nums.get(1)};
        }
        return new Float[]{null, null};
    }

    private static boolean guessFirstBool(JsonObject o, boolean def) {
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isBoolean()) {
                return v.getAsBoolean();
            }
        }
        return def;
    }

    private static int guessFirstInt(JsonObject o, int def) {
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isNumber()) continue;
            String raw = v.getAsString();
            if (raw != null && !raw.contains(".")) {
                return safeGetInt(v);
            }
        }
        return def;
    }

    private static String guessBindMode(JsonObject o) {
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isString()) continue;
            String s = v.getAsString();
            if (s == null) continue;
            String n = normalizeBindMode(s);
            if (n.equals("Toggle") || n.equals("Hold")) {
                return n;
            }
        }
        return "Toggle";
    }

    private static String guessSuffix(JsonObject o, String bindMode) {
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonPrimitive() || !v.getAsJsonPrimitive().isString()) continue;
            String s = v.getAsString();
            if (s == null) continue;
            String n = normalizeBindMode(s);
            if (n.equals("Toggle") || n.equals("Hold")) continue;
            if (s.length() <= 32 && !s.equals(".")) {
                return s;
            }
        }
        return "";
    }

    private static JsonObject guessValuesObject(JsonObject moduleObj) {
        JsonObject best = null;
        int bestSize = 0;
        for (var e : moduleObj.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonObject()) continue;
            JsonObject o = v.getAsJsonObject();
            int size = o.size();
            if (size <= 0) continue;
            if (size > bestSize) {
                best = o;
                bestSize = size;
            }
        }
        return best;
    }

    private static JsonObject guessModulesObject(JsonObject root) {
        JsonObject best = null;
        int bestScore = 0;
        for (var e : root.entrySet()) {
            JsonElement v = e.getValue();
            if (v == null || !v.isJsonObject()) continue;
            JsonObject o = v.getAsJsonObject();
            int score = 0;
            int entries = 0;
            for (var me : o.entrySet()) {
                entries++;
                if (me.getValue() != null && me.getValue().isJsonObject()) {
                    JsonObject mo = me.getValue().getAsJsonObject();
                    if (hasBoolPrimitive(mo)) score++;
                }
            }
            if (entries >= 1 && score > bestScore) {
                bestScore = score;
                best = o;
            }
        }
        return best;
    }

    private static boolean hasBoolPrimitive(JsonObject o) {
        for (var e : o.entrySet()) {
            JsonElement v = e.getValue();
            if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isBoolean()) {
                return true;
            }
        }
        return false;
    }

    private static String normalizeBindMode(String s) {
        if (s == null) return "Toggle";
        String v = s.trim();
        if (v.isEmpty()) return "Toggle";
        String up = v.toUpperCase(Locale.ROOT);
        if (up.equals("TOGGLE")) return "Toggle";
        if (up.equals("HOLD")) return "Hold";
        if (v.equalsIgnoreCase("Toggle")) return "Toggle";
        if (v.equalsIgnoreCase("Hold")) return "Hold";
        return v;
    }

    private static JsonObject getObject(JsonObject o, String key) {
        JsonElement el = o.get(key);
        if (el == null || !el.isJsonObject()) return null;
        return el.getAsJsonObject();
    }

    private static String getString(JsonObject o, String key, String def) {
        JsonElement el = o.get(key);
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isString()) return def;
        String s = el.getAsString();
        return s == null || s.isBlank() ? def : s;
    }

    private static boolean getBool(JsonObject o, String key, boolean def) {
        JsonElement el = o.get(key);
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isBoolean()) return def;
        return el.getAsBoolean();
    }

    private static int getInt(JsonObject o, String key, int def) {
        JsonElement el = o.get(key);
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isNumber()) return def;
        return safeGetInt(el);
    }

    private static float getFloat(JsonObject o, String key, float def) {
        JsonElement el = o.get(key);
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isNumber()) return def;
        return safeGetFloat(el);
    }

    private static int safeGetInt(JsonElement el) {
        try {
            return el.getAsInt();
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static float safeGetFloat(JsonElement el) {
        try {
            return el.getAsFloat();
        } catch (Exception ignored) {
            return 0.0f;
        }
    }

    private static Float safeGetFloatObj(JsonElement el) {
        try {
            return el.getAsFloat();
        } catch (Exception ignored) {
            return null;
        }
    }
}
