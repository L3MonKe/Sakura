package dev.sakura.config;

import com.google.gson.*;
import dev.sakura.Sakura;
import dev.sakura.gui.clickgui.panel.CategoryPanel;
import dev.sakura.gui.hud.HudPanel;
import dev.sakura.module.HudModule;
import dev.sakura.module.Module;
import dev.sakura.values.Value;
import dev.sakura.values.impl.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = Paths.get("sakura-config");
    private static final Path MODULES_DIR = CONFIG_DIR.resolve("modules");
    private static final Path CLICKGUI_FILE = CONFIG_DIR.resolve("clickgui.json");

    public ConfigManager() {
        createConfigDir();
    }

    private void createConfigDir() {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
            if (!Files.exists(MODULES_DIR)) {
                Files.createDirectories(MODULES_DIR);
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to create config directory: {}", e.getMessage());
        }
    }

    public void saveDefaultConfig() {
        saveModules();
        saveClickGui();
    }

    public void loadDefaultConfig() {
        loadModules();
        loadClickGui();
    }

    private void saveModules() {
        for (Module module : Sakura.MODULE.getAllModules()) {
            saveModule(module);
        }
    }

    private void saveModule(Module module) {
        try {
            Path moduleFile = MODULES_DIR.resolve(module.getName() + ".json");
            JsonObject moduleObject = new JsonObject();

            moduleObject.addProperty("enabled", module.isEnabled());
            moduleObject.addProperty("keybind", module.getKey());
            moduleObject.addProperty("bindMode", module.getBindMode().name());
            moduleObject.addProperty("suffix", module.getSuffix());

            if (module instanceof HudModule hudModule) {
                moduleObject.addProperty("hudX", hudModule.getX());
                moduleObject.addProperty("hudY", hudModule.getY());
            }

            JsonObject valuesObject = new JsonObject();
            for (Value<?> value : module.getValues()) {
                valuesObject.add(value.getName(), saveValue(value));
            }
            moduleObject.add("values", valuesObject);

            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(moduleFile.toFile()), StandardCharsets.UTF_8)) {
                GSON.toJson(moduleObject, writer);
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to save module {}: {}", module.getName(), e.getMessage());
        }
    }

    private void loadModules() {
        try {
            if (!Files.exists(MODULES_DIR)) return;

            Files.list(MODULES_DIR)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        String moduleName = path.getFileName().toString();
                        moduleName = moduleName.substring(0, moduleName.length() - 5);
                        Module module = Sakura.MODULE.getModule(moduleName);
                        if (module != null) {
                            loadModule(module, path);
                        }
                    });
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to load modules: {}", e.getMessage());
        }
    }

    private void loadModule(Module module, Path path) {
        try {
            JsonObject moduleObject = JsonParser.parseString(Files.readString(path)).getAsJsonObject();

            if (moduleObject.has("enabled")) {
                module.setState(moduleObject.get("enabled").getAsBoolean());
            }
            if (moduleObject.has("keybind")) {
                module.setKey(moduleObject.get("keybind").getAsInt());
            }
            if (moduleObject.has("bindMode")) {
                try {
                    module.setBindMode(Module.BindMode.valueOf(moduleObject.get("bindMode").getAsString()));
                } catch (IllegalArgumentException ignored) {
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
                for (Value<?> value : module.getValues()) {
                    if (valuesObject.has(value.getName())) {
                        JsonElement valueElement = valuesObject.get(value.getName());
                        loadValue(value, valueElement);
                    }
                }
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to load module {}: {}", module.getName(), e.getMessage());
        }
    }

    public void saveClickGui() {
        try {
            JsonObject clickGuiObject = new JsonObject();

            JsonArray panelsArray = new JsonArray();
            if (Sakura.CLICKGUI != null) {
                for (CategoryPanel panel : Sakura.CLICKGUI.getPanels()) {
                    JsonObject panelObject = new JsonObject();
                    panelObject.addProperty("category", panel.getCategory().name());
                    panelObject.addProperty("x", panel.getX());
                    panelObject.addProperty("y", panel.getY());
                    panelObject.addProperty("opened", panel.isOpened());
                    panelsArray.add(panelObject);
                }
            }
            clickGuiObject.add("panels", panelsArray);

            if (Sakura.HUDEDITOR != null) {
                HudPanel hudPanel = Sakura.HUDEDITOR.getHudPanel();
                if (hudPanel != null) {
                    JsonObject hudPanelObject = new JsonObject();
                    hudPanelObject.addProperty("x", hudPanel.getX());
                    hudPanelObject.addProperty("y", hudPanel.getY());
                    clickGuiObject.add("hudPanel", hudPanelObject);
                }
            }

            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(CLICKGUI_FILE.toFile()), StandardCharsets.UTF_8)) {
                GSON.toJson(clickGuiObject, writer);
            }
            System.out.println("ClickGui saved to: " + CLICKGUI_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save clickgui: " + e.getMessage());
        }
    }

    public void loadClickGui() {
        try {
            if (!Files.exists(CLICKGUI_FILE)) return;

            String content = new String(Files.readAllBytes(CLICKGUI_FILE), StandardCharsets.UTF_8);
            JsonObject clickGuiObject = JsonParser.parseString(content).getAsJsonObject();

            if (clickGuiObject.has("panels") && Sakura.CLICKGUI != null) {
                JsonArray panelsArray = clickGuiObject.getAsJsonArray("panels");
                for (JsonElement element : panelsArray) {
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
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to load clickgui: {}", e.getMessage());
        }
    }

    private JsonElement saveValue(Value<?> value) {
        Object val = value.get();

        if (value instanceof BoolValue) {
            return new JsonPrimitive((Boolean) val);
        } else if (value instanceof NumberValue<?> numberValue) {
            if (numberValue.getValue() instanceof Integer) {
                return new JsonPrimitive(numberValue.getValue().intValue());
            } else {
                return new JsonPrimitive(numberValue.getValue().doubleValue());
            }
        } else if (value instanceof StringValue) {
            return new JsonPrimitive(((StringValue) value).get());
        } else if (value instanceof EnumValue) {
            return new JsonPrimitive(((Enum<?>) val).name());
        } else if (value instanceof ColorValue colorValue) {
            JsonObject colorObject = new JsonObject();
            colorObject.addProperty("hue", colorValue.getHue());
            colorObject.addProperty("saturation", colorValue.getSaturation());
            colorObject.addProperty("brightness", colorValue.getBrightness());
            colorObject.addProperty("alpha", colorValue.getAlpha());
            colorObject.addProperty("rainbow", colorValue.isRainbow());
            colorObject.addProperty("expand", colorValue.isExpand());
            return colorObject;
        } else if (value instanceof MultiBoolValue multiBoolValue) {
            JsonObject multiObject = new JsonObject();
            for (int i = 0; i < multiBoolValue.getValues().size(); i++) {
                BoolValue boolValue = multiBoolValue.getValues().get(i);
                multiObject.addProperty(boolValue.getName(), boolValue.get());
            }
            return multiObject;
        }

        return JsonNull.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private void loadValue(Value<?> value, JsonElement valueElement) {
        try {
            if (value instanceof BoolValue && valueElement.isJsonPrimitive()) {
                ((Value<Boolean>) value).set(valueElement.getAsBoolean());
            } else if (value instanceof NumberValue<?> numberValue && valueElement.isJsonPrimitive()) {
                if (numberValue.getValue() instanceof Integer) {
                    ((NumberValue<Integer>) numberValue).setValue(valueElement.getAsInt());
                } else {
                    ((NumberValue<Double>) numberValue).setValue(valueElement.getAsDouble());
                }
            } else if (value instanceof StringValue && valueElement.isJsonPrimitive()) {
                ((StringValue) value).setText(valueElement.getAsString());
            } else if (value instanceof EnumValue && valueElement.isJsonPrimitive()) {
                ((EnumValue<?>) value).setMode(valueElement.getAsString());
            } else if (value instanceof ColorValue && valueElement.isJsonObject()) {
                JsonObject colorObject = valueElement.getAsJsonObject();
                ColorValue colorValue = (ColorValue) value;

                if (colorObject.has("hue")) colorValue.setHue(colorObject.get("hue").getAsFloat());
                if (colorObject.has("saturation")) colorValue.setSaturation(colorObject.get("saturation").getAsFloat());
                if (colorObject.has("brightness")) colorValue.setBrightness(colorObject.get("brightness").getAsFloat());
                if (colorObject.has("alpha")) colorValue.setAlpha(colorObject.get("alpha").getAsFloat());
                if (colorObject.has("rainbow")) colorValue.setRainbow(colorObject.get("rainbow").getAsBoolean());
                if (colorObject.has("expand")) colorValue.setExpand(colorObject.get("expand").getAsBoolean());
            } else if (value instanceof MultiBoolValue multiBoolValue && valueElement.isJsonObject()) {
                JsonObject multiObject = valueElement.getAsJsonObject();
                for (String optionName : multiObject.keySet()) {
                    multiBoolValue.set(optionName, multiObject.get(optionName).getAsBoolean());
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load value " + value.getName() + ": " + e.getMessage());
        }
    }

    public List<String> getConfigList() {
        List<String> configs = new ArrayList<>();
        try {
            Files.list(MODULES_DIR)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        configs.add(fileName.substring(0, fileName.length() - 5));
                    });
        } catch (IOException e) {
            System.err.println("Failed to list configs: " + e.getMessage());
        }
        return configs;
    }

    public void savePrefix(String prefix) {
        try {
            Path prefixFile = CONFIG_DIR.resolve("prefix.json");
            JsonObject prefixObject = new JsonObject();
            prefixObject.addProperty("prefix", prefix);

            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(prefixFile.toFile()), StandardCharsets.UTF_8)) {
                GSON.toJson(prefixObject, writer);
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to save prefix: {}", e.getMessage());
        }
    }

    public String loadPrefix() {
        try {
            Path prefixFile = CONFIG_DIR.resolve("prefix.json");
            if (!Files.exists(prefixFile)) return ".";

            String content = new String(Files.readAllBytes(prefixFile), StandardCharsets.UTF_8);
            JsonObject prefixObject = JsonParser.parseString(content).getAsJsonObject();

            if (prefixObject.has("prefix")) {
                return prefixObject.get("prefix").getAsString();
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to load prefix: {}", e.getMessage());
        }
        return ".";
    }
}
