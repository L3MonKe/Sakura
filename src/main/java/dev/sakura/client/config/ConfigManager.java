package dev.sakura.client.config;

import com.google.gson.*;
import dev.sakura.client.Sakura;
import dev.sakura.client.gui.clickgui.panel.CategoryPanel;
import dev.sakura.client.gui.hud.HudPanel;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.*;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Path CONFIG_DIR = Paths.get("sakura-config");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("config.json");
    private static final Path LEGACY_MODULES_DIR = CONFIG_DIR.resolve("modules");
    private static final Path LEGACY_CLICKGUI_FILE = CONFIG_DIR.resolve("clickgui.json");

    private final CloudConfigService cloud = new CloudConfigService();

    private volatile ClientConfig current = new ClientConfig();

    public ConfigManager() {
        createConfigDir();
        VerificationClient.addHandler(new IRCHandler() {
            @Override
            public void onMessage(String sender, String message) {
            }

            @Override
            public void onDisconnected(String message) {
                MinecraftClient.getInstance().execute(() -> ChatUtil.clientMessage("§cIRC服务器连接断开: " + (message == null ? "" : message)));
            }

            @Override
            public void onConnected() {
                MinecraftClient.getInstance().execute(() -> ChatUtil.clientMessage("§aIRC服务器已连接"));
            }

            @Override
            public String getInGameUsername() {
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc.player == null) return mc.getSession().getUsername();
                return mc.player.getName().getString();
            }
        });
        VerificationClient.addHandler(cloud.asHandler());
        loadLocal();
    }

    public void saveDefaultConfig() {
        saveLocal();
    }

    public CompletableFuture<CloudConfigService.ListResult> cloudList() {
        return cloud.list();
    }

    public CompletableFuture<CloudConfigService.UploadResult> cloudSave(String name) {
        String content = saveConfigToString();
        return cloud.upload(name, content);
    }

    public CompletableFuture<CloudConfigService.GetResult> cloudLoad(String owner, String name) {
        return cloud.get(owner, name);
    }

    public CompletableFuture<CloudConfigService.DeleteResult> cloudDelete(String owner, String name) {
        return cloud.delete(owner, name);
    }

    public String saveConfigToString() {
        updateFromRuntime();
        return GSON.toJson(current);
    }

    public void loadConfigFromString(String json) {
        if (json == null || json.isBlank()) {
            return;
        }
        try {
            ClientConfig cfg = GSON.fromJson(json, ClientConfig.class);
            if (cfg == null) {
                return;
            }
            apply(cfg);
            saveLocal();
        } catch (Exception e) {
            Sakura.LOGGER.error("Failed to load cloud config: {}", e.getMessage());
        }
    }

    public void savePrefix(String prefix) {
        String p = prefix == null || prefix.isEmpty() ? "." : prefix;
        current.prefix = p;
        saveLocal();
    }

    public String loadPrefix() {
        String v = current == null ? null : current.prefix;
        if (v != null && !v.isBlank()) {
            return v;
        }
        try {
            if (Files.exists(CONFIG_FILE)) {
                ClientConfig cfg = GSON.fromJson(Files.readString(CONFIG_FILE, StandardCharsets.UTF_8), ClientConfig.class);
                if (cfg != null && cfg.prefix != null && !cfg.prefix.isBlank()) {
                    return cfg.prefix;
                }
            }
        } catch (Exception ignored) {
        }
        return ".";
    }

    private void createConfigDir() {
        try {
            Files.createDirectories(CONFIG_DIR);
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to create config directory: {}", e.getMessage());
        }
    }

    private void loadLocal() {
        if (Files.exists(CONFIG_FILE)) {
            try {
                ClientConfig cfg = GSON.fromJson(Files.readString(CONFIG_FILE, StandardCharsets.UTF_8), ClientConfig.class);
                if (cfg != null) {
                    apply(cfg);
                    return;
                }
            } catch (Exception e) {
                Sakura.LOGGER.error("Failed to load config: {}", e.getMessage());
            }
        }

        loadLegacyModules();
        loadLegacyClickGui();
        current.prefix = migrateLegacyPrefix();
        saveLocal();
    }

    private void saveLocal() {
        try {
            updateFromRuntime();
            Files.createDirectories(CONFIG_DIR);
            Path tmp = CONFIG_DIR.resolve("config.json.tmp");
            Files.writeString(tmp, GSON.toJson(current), StandardCharsets.UTF_8);
            try {
                Files.move(tmp, CONFIG_FILE, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (Exception ignored) {
                Files.move(tmp, CONFIG_FILE, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            Sakura.LOGGER.error("Failed to save config: {}", e.getMessage());
        }
    }

    private void apply(ClientConfig cfg) {
        current = cfg;

        for (var entry : cfg.modules.entrySet()) {
            String moduleName = entry.getKey();
            ClientConfig.ModuleData data = entry.getValue();
            Module module = Sakura.MODULES.getModuleByString(moduleName);
            if (module == null || data == null) {
                continue;
            }

            module.setKey(data.keybind);
            if (data.bindMode != null) {
                try {
                    module.setBindMode(Module.BindMode.valueOf(data.bindMode));
                } catch (Exception ignored) {
                }
            }
            if (data.suffix != null) {
                module.setSuffix(data.suffix);
            }
            module.setState(data.enabled);

            if (module instanceof HudModule hudModule) {
                if (data.hudX != null) {
                    hudModule.setX(data.hudX);
                }
                if (data.hudY != null) {
                    hudModule.setY(data.hudY);
                }
            }

            if (data.values != null) {
                for (Value<?> v : module.getValues()) {
                    JsonElement el = data.values.get(v.getName());
                    if (el != null) {
                        decodeValue(v, el);
                    }
                }
            }
        }

        if (Sakura.CLICKGUI != null && cfg.gui != null && cfg.gui.panels != null) {
            for (ClientConfig.Panel p : cfg.gui.panels) {
                if (p == null || p.category == null) {
                    continue;
                }
                for (CategoryPanel panel : Sakura.CLICKGUI.getPanels()) {
                    if (panel.getCategory().name().equals(p.category)) {
                        panel.setX(p.x);
                        panel.setY(p.y);
                        panel.setOpened(p.opened);
                        break;
                    }
                }
            }
        }

        if (Sakura.HUDEDITOR != null && cfg.gui != null && cfg.gui.hudPanel != null) {
            HudPanel hudPanel = Sakura.HUDEDITOR.getHudPanel();
            if (hudPanel != null) {
                hudPanel.setX(cfg.gui.hudPanel.x);
                hudPanel.setY(cfg.gui.hudPanel.y);
            }
        }
    }

    private void updateFromRuntime() {
        ClientConfig cfg = new ClientConfig();
        cfg.version = 1;
        String prefixValue = current == null ? null : current.prefix;
        cfg.prefix = prefixValue == null || prefixValue.isBlank() ? "." : prefixValue;

        for (Module module : Sakura.MODULES.getAllModules()) {
            ClientConfig.ModuleData data = new ClientConfig.ModuleData();
            data.enabled = module.isEnabled();
            data.keybind = module.getKey();
            data.bindMode = module.getBindMode() == null ? "TOGGLE" : module.getBindMode().name();
            data.suffix = module.getSuffix();

            if (module instanceof HudModule hudModule) {
                data.hudX = hudModule.getX();
                data.hudY = hudModule.getY();
            }

            for (Value<?> value : module.getValues()) {
                data.values.put(value.getName(), encodeValue(value));
            }

            cfg.modules.put(module.getEnglishName(), data);
        }

        if (Sakura.CLICKGUI != null) {
            for (CategoryPanel panel : Sakura.CLICKGUI.getPanels()) {
                ClientConfig.Panel panelConfig = new ClientConfig.Panel();
                panelConfig.category = panel.getCategory().name();
                panelConfig.x = panel.getX();
                panelConfig.y = panel.getY();
                panelConfig.opened = panel.isOpened();
                cfg.gui.panels.add(panelConfig);
            }
        }

        if (Sakura.HUDEDITOR != null) {
            HudPanel hudPanel = Sakura.HUDEDITOR.getHudPanel();
            if (hudPanel != null) {
                cfg.gui.hudPanel.x = hudPanel.getX();
                cfg.gui.hudPanel.y = hudPanel.getY();
            }
        }

        current = cfg;
    }

    private static JsonElement encodeValue(Value<?> value) {
        Object val = value.get();
        if (value instanceof BoolValue) {
            return new JsonPrimitive((Boolean) val);
        }
        if (value instanceof NumberValue<?> numberValue) {
            Number n = numberValue.get();
            if (n instanceof Integer) {
                return new JsonPrimitive(n.intValue());
            }
            if (n instanceof Float) {
                return new JsonPrimitive(n.floatValue());
            }
            return new JsonPrimitive(n.doubleValue());
        }
        if (value instanceof StringValue stringValue) {
            return new JsonPrimitive(stringValue.get());
        }
        if (value instanceof EnumValue) {
            return new JsonPrimitive(((Enum<?>) val).name());
        }
        if (value instanceof ColorValue colorValue) {
            JsonObject o = new JsonObject();
            o.addProperty("hue", colorValue.getHue());
            o.addProperty("saturation", colorValue.getSaturation());
            o.addProperty("brightness", colorValue.getBrightness());
            o.addProperty("alpha", colorValue.getAlpha());
            o.addProperty("rainbow", colorValue.isRainbow());
            o.addProperty("expand", colorValue.isExpand());
            return o;
        }
        if (value instanceof MultiBoolValue multiBoolValue) {
            JsonObject o = new JsonObject();
            List<BoolValue> vs = multiBoolValue.getValues();
            for (BoolValue b : vs) {
                o.addProperty(b.getName(), b.get());
            }
            return o;
        }
        return JsonNull.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private static void decodeValue(Value<?> value, JsonElement el) {
        try {
            if (value instanceof BoolValue && el.isJsonPrimitive()) {
                ((Value<Boolean>) value).set(el.getAsBoolean());
                return;
            }
            if (value instanceof NumberValue<?> numberValue && el.isJsonPrimitive()) {
                if (numberValue.get() instanceof Integer) {
                    ((NumberValue<Integer>) numberValue).set(el.getAsInt());
                } else if (numberValue.get() instanceof Float) {
                    ((NumberValue<Float>) numberValue).set(el.getAsFloat());
                } else {
                    ((NumberValue<Double>) numberValue).set(el.getAsDouble());
                }
                return;
            }
            if (value instanceof StringValue && el.isJsonPrimitive()) {
                ((StringValue) value).setText(el.getAsString());
                return;
            }
            if (value instanceof EnumValue && el.isJsonPrimitive()) {
                ((EnumValue<?>) value).setMode(el.getAsString());
                return;
            }
            if (value instanceof ColorValue cv && el.isJsonObject()) {
                JsonObject o = el.getAsJsonObject();
                if (o.has("hue")) cv.setHue(o.get("hue").getAsFloat());
                if (o.has("saturation")) cv.setSaturation(o.get("saturation").getAsFloat());
                if (o.has("brightness")) cv.setBrightness(o.get("brightness").getAsFloat());
                if (o.has("alpha")) cv.setAlpha(o.get("alpha").getAsFloat());
                if (o.has("rainbow")) cv.setRainbow(o.get("rainbow").getAsBoolean());
                if (o.has("expand")) cv.setExpand(o.get("expand").getAsBoolean());
                return;
            }
            if (value instanceof MultiBoolValue mb && el.isJsonObject()) {
                JsonObject o = el.getAsJsonObject();
                for (String k : o.keySet()) {
                    mb.set(k, o.get(k).getAsBoolean());
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void loadLegacyModules() {
        if (!Files.exists(LEGACY_MODULES_DIR)) {
            return;
        }
        try {
            Files.list(LEGACY_MODULES_DIR)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> {
                        String moduleName = p.getFileName().toString();
                        moduleName = moduleName.substring(0, moduleName.length() - 5);
                        Module module = Sakura.MODULES.getModuleByString(moduleName);
                        if (module == null) {
                            return;
                        }
                        try {
                            JsonObject moduleObject = JsonParser.parseString(Files.readString(p, StandardCharsets.UTF_8)).getAsJsonObject();
                            if (moduleObject.has("enabled")) {
                                module.setState(moduleObject.get("enabled").getAsBoolean());
                            }
                            if (moduleObject.has("keybind")) {
                                module.setKey(moduleObject.get("keybind").getAsInt());
                            }
                            if (moduleObject.has("bindMode")) {
                                try {
                                    module.setBindMode(Module.BindMode.valueOf(moduleObject.get("bindMode").getAsString()));
                                } catch (Exception ignored) {
                                }
                            }
                            if (moduleObject.has("suffix")) {
                                module.setSuffix(moduleObject.get("suffix").getAsString());
                            }
                            if (module instanceof HudModule hudModule) {
                                if (moduleObject.has("hudX")) {
                                    hudModule.setX(moduleObject.get("hudX").getAsFloat());
                                }
                                if (moduleObject.has("hudY")) {
                                    hudModule.setY(moduleObject.get("hudY").getAsFloat());
                                }
                            }
                            if (moduleObject.has("values")) {
                                JsonObject valuesObject = moduleObject.getAsJsonObject("values");
                                for (Value<?> v : module.getValues()) {
                                    if (valuesObject.has(v.getName())) {
                                        decodeValue(v, valuesObject.get(v.getName()));
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    });
        } catch (Exception ignored) {
        }
    }

    private void loadLegacyClickGui() {
        if (!Files.exists(LEGACY_CLICKGUI_FILE)) {
            return;
        }
        try {
            JsonObject clickGuiObject = JsonParser.parseString(Files.readString(LEGACY_CLICKGUI_FILE, StandardCharsets.UTF_8)).getAsJsonObject();
            if (clickGuiObject.has("panels") && Sakura.CLICKGUI != null) {
                for (JsonElement element : clickGuiObject.getAsJsonArray("panels")) {
                    JsonObject panelObject = element.getAsJsonObject();
                    String categoryName = panelObject.get("category").getAsString();
                    for (CategoryPanel panel : Sakura.CLICKGUI.getPanels()) {
                        if (panel.getCategory().name().equals(categoryName)) {
                            if (panelObject.has("x")) panel.setX(panelObject.get("x").getAsFloat());
                            if (panelObject.has("y")) panel.setY(panelObject.get("y").getAsFloat());
                            if (panelObject.has("opened")) panel.setOpened(panelObject.get("opened").getAsBoolean());
                            break;
                        }
                    }
                }
            }
            if (clickGuiObject.has("hudPanel") && Sakura.HUDEDITOR != null) {
                JsonObject hudPanelObject = clickGuiObject.getAsJsonObject("hudPanel");
                HudPanel hudPanel = Sakura.HUDEDITOR.getHudPanel();
                if (hudPanel != null) {
                    if (hudPanelObject.has("x")) hudPanel.setX(hudPanelObject.get("x").getAsFloat());
                    if (hudPanelObject.has("y")) hudPanel.setY(hudPanelObject.get("y").getAsFloat());
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static String migrateLegacyPrefix() {
        Path prefixFile = CONFIG_DIR.resolve("prefix.json");
        try {
            if (!Files.exists(prefixFile)) {
                return ".";
            }
            JsonObject o = JsonParser.parseString(Files.readString(prefixFile, StandardCharsets.UTF_8)).getAsJsonObject();
            if (o.has("prefix")) {
                String p = o.get("prefix").getAsString();
                Files.deleteIfExists(prefixFile);
                return p == null || p.isBlank() ? "." : p;
            }
        } catch (Exception ignored) {
        }
        try {
            Files.deleteIfExists(prefixFile);
        } catch (Exception ignored) {
        }
        return ".";
    }
}
