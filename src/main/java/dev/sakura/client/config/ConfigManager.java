package dev.sakura.client.config;

import com.google.gson.*;
import dev.sakura.client.Sakura;
import dev.sakura.client.account.type.MinecraftAccount;
import dev.sakura.client.account.type.impl.CrackedAccount;
import dev.sakura.client.account.type.impl.MicrosoftAccount;
import dev.sakura.client.gui.clickgui.panel.CategoryPanel;
import dev.sakura.client.gui.hud.HudPanel;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Path CONFIG_DIR = Paths.get("sakura-config");
    private static final Path MODULES_DIR = CONFIG_DIR.resolve("modules");
    private static final Path CLICKGUI_FILE = CONFIG_DIR.resolve("clickgui.json");
    private static final Path ACCOUNTS_FILE = CONFIG_DIR.resolve("accounts.json");
    private static final Path ENCRYPTED_ACCOUNTS_FILE = CONFIG_DIR.resolve("accounts_enc.json");

    private String currentPassword = null;

    public ConfigManager() {
        createConfigDir();

        loadModules();
        loadClickGui();
        loadAccounts();
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
        saveAccounts();
    }

    public void saveAccounts() {
        try {
            JsonArray array = new JsonArray();
            for (final MinecraftAccount account : Managers.ACCOUNT.getAccounts()) {
                try {
                    array.add(account.toJSON());
                } catch (RuntimeException e) {
                    Sakura.LOGGER.error(e.getMessage());
                }
            }

            String jsonString = GSON.toJson(array);

            if (currentPassword != null) {
                // 加密保存
                try {
                    String encrypted = encrypt(jsonString, currentPassword);
                    Files.writeString(ENCRYPTED_ACCOUNTS_FILE, encrypted, StandardCharsets.UTF_8);

                    // 如果存在明文文件则删除
                    if (Files.exists(ACCOUNTS_FILE)) {
                        Files.delete(ACCOUNTS_FILE);
                    }
                } catch (Exception e) {
                    Sakura.LOGGER.error("Failed to encrypt accounts: {}", e.getMessage());
                }
            } else {
                // 明文保存
                try (Writer writer = new OutputStreamWriter(
                        new FileOutputStream(ACCOUNTS_FILE.toFile()), StandardCharsets.UTF_8)) {
                    writer.write(jsonString);
                }

                // 如果存在加密文件则删除
                if (Files.exists(ENCRYPTED_ACCOUNTS_FILE)) {
                    Files.delete(ENCRYPTED_ACCOUNTS_FILE);
                }
            }
        } catch (IOException e) {
            Sakura.LOGGER.error("Failed to save accounts: {}", e.getMessage());
        }
    }

    public void loadAccounts() {
        try {
            String content = null;

            if (Files.exists(ENCRYPTED_ACCOUNTS_FILE)) {
                if (currentPassword != null) {
                    try {
                        String encrypted = Files.readString(ENCRYPTED_ACCOUNTS_FILE, StandardCharsets.UTF_8);
                        content = decrypt(encrypted, currentPassword);
                    } catch (Exception e) {
                        Sakura.LOGGER.error("Failed to decrypt accounts: {}", e.getMessage());
                        return;
                    }
                } else {
                    Sakura.LOGGER.info("Encrypted accounts file found, waiting for password.");
                    return;
                }
            } else if (Files.exists(ACCOUNTS_FILE)) {
                content = Files.readString(ACCOUNTS_FILE, StandardCharsets.UTF_8);
            }

            if (content == null) return;

            JsonArray json = JsonParser.parseString(content).getAsJsonArray();

            Managers.ACCOUNT.getAccounts().clear();
            for (JsonElement element : json) {
                if (!(element instanceof JsonObject object)) {
                    continue;
                }

                MinecraftAccount account = null;
                if (object.has("email") && object.has("password")) {
                    account = new MicrosoftAccount(object.get("email").getAsString(),
                            object.get("password").getAsString());
                    if (object.has("username")) {
                        ((MicrosoftAccount) account).setUsername(object.get("username").getAsString());
                    }
                } else if (object.has("token")) {
                    if (!object.has("username")) {
                        Sakura.LOGGER.error("Browser account does not have a username set?");
                        continue;
                    }
                    account = new MicrosoftAccount(object.get("token").getAsString());
                    ((MicrosoftAccount) account).setUsername(object.get("username").getAsString());
                } else {
                    if (object.has("username")) {
                        account = new CrackedAccount(object.get("username").getAsString());
                    }
                }

                if (account != null) {
                    Managers.ACCOUNT.register(account, false);
                } else {
                    Sakura.LOGGER.error("Could not parse account JSON.\nRaw: {}", object.toString());
                }
            }
        } catch (IOException | IllegalStateException e) {
            Sakura.LOGGER.error("Failed to load accounts: {}", e.getMessage());
        }
    }

    public void setEncryptionPassword(String password) {
        this.currentPassword = password;
        if (password != null) {
            if (Files.exists(ENCRYPTED_ACCOUNTS_FILE) && Managers.ACCOUNT.getAccounts().isEmpty()) {
                loadAccounts();
            } else {
                saveAccounts();
            }
        } else {
            saveAccounts();
        }
    }

    public boolean isEncrypted() {
        return Files.exists(ENCRYPTED_ACCOUNTS_FILE) || currentPassword != null;
    }

    private static String encrypt(String data, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String decrypt(String encryptedData, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, "AES");
    }

    private void saveModules() {
        for (Module module : Sakura.MODULES.getAllModules()) {
            saveModule(module);
        }
    }

    private void saveModule(Module module) {
        try {
            Path moduleFile = MODULES_DIR.resolve(module.getEnglishName() + ".json");
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
            Sakura.LOGGER.error("Failed to save module {}: {}", module.getEnglishName(), e.getMessage());
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
                        Module module = Sakura.MODULES.getModuleByString(moduleName);
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
            Sakura.LOGGER.error("Failed to load module {}: {}", module.getEnglishName(), e.getMessage());
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
            if (numberValue.get() instanceof Integer) {
                return new JsonPrimitive(numberValue.get().intValue());
            } else if (numberValue.get() instanceof Float) {
                return new JsonPrimitive(numberValue.get().floatValue());
            } else {
                return new JsonPrimitive(numberValue.get().doubleValue());
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
                if (numberValue.get() instanceof Integer) {
                    ((NumberValue<Integer>) numberValue).set(valueElement.getAsInt());
                } else if (numberValue.get() instanceof Float) {
                    ((NumberValue<Float>) numberValue).set(valueElement.getAsFloat());
                } else {
                    ((NumberValue<Double>) numberValue).set(valueElement.getAsDouble());
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
