package dev.sakura.client.module;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.hud.DynamicIslandHud;
import dev.sakura.client.module.impl.hud.ModuleListHud;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.DecelerateAnimation;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public enum BindMode {
        Toggle, Hold
    }

    private final String englishName;
    private final String chineseName;
    private boolean state;
    private final Category category;
    private String suffix = "";
    private int key = -1;
    private BindMode bindMode = BindMode.Toggle;
    private final BoolValue hidden; // 控制模块是否在ModuleListHud中显示
    public final List<Value<?>> values = new ArrayList<>();
    private final Animation animations = new DecelerateAnimation(250, 1).setDirection(Direction.BACKWARDS);

    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String englishName, String chineseName, Category category) {
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.category = category;
        this.hidden = new BoolValue("Hidden", "隐藏", false);
        this.values.add(this.hidden);
    }

    protected boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public boolean isEnabled() {
        return state;
    }

    public boolean isDisabled() {
        return !state;
    }

    public <M extends Module> boolean isEnabled(Class<M> module) {
        Module mod = Sakura.MODULES.getModule(module);
        return mod != null && mod.isEnabled();
    }

    public void setSuffix(String tag) {
        if (tag != null && !tag.isEmpty()) {
            this.suffix = "" + tag;
        } else {
            this.suffix = "";
        }
    }

    public void toggle() {
        setState(!state);
    }

    public void setState(boolean state) {
        if (this.state != state) {
            this.state = state;

            DynamicIslandHud.onModuleToggle(this, state);
            ModuleListHud.onModuleToggle(this, state);

            if (state) {
                Sakura.EVENT_BUS.subscribe(this);
                onEnable();
            } else {
                Sakura.EVENT_BUS.unsubscribe(this);
                onDisable();
            }
        }
    }

    protected void setInitialState(boolean state) {
        this.state = state;
    }

    public void reset() {
        setState(false);
        if (!englishName.equalsIgnoreCase("ClickGui")) {
            setKey(-1);
        }
        setBindMode(BindMode.Toggle);
        for (Value<?> value : values) {
            value.reset();
        }
    }

    public String getDisplayName() {
        if (ClickGui.language.get() == ClickGui.LanguageMode.Chinese) {
            return chineseName == null ? englishName : chineseName;
        }
        return englishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public boolean isState() {
        return state;
    }

    public Category getCategory() {
        return category;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getKey() {
        return key;
    }

    public List<Value<?>> getValues() {
        return values;
    }

    public Animation getAnimations() {
        return animations;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public BindMode getBindMode() {
        return bindMode;
    }

    public void setBindMode(BindMode bindMode) {
        this.bindMode = bindMode;
    }

    public boolean isHidden() {
        return hidden.get();
    }

    public void setHidden(boolean hidden) {
        this.hidden.set(hidden);
    }
}
