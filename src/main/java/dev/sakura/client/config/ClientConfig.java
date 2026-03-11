package dev.sakura.client.config;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ClientConfig {
    public int version = 1;
    public boolean customMainMenu = true;
    public boolean useNewMainMenu = true;
    public String prefix = ".";
    public Gui gui = new Gui();
    public Map<String, ModuleData> modules = new LinkedHashMap<>();

    public static final class Gui {
        public List<Panel> panels = new ArrayList<>();
        public HudPanel hudPanel = new HudPanel();
    }

    public static final class Panel {
        public String category = "";
        public float x;
        public float y;
        public boolean opened;
    }

    public static final class HudPanel {
        public float x;
        public float y;
    }

    public static final class ModuleData {
        public boolean enabled;
        public int keybind;
        public String bindMode = "TOGGLE";
        public String suffix = "";
        public Float hudX;
        public Float hudY;
        public Map<String, JsonElement> values = new LinkedHashMap<>();
    }
}

