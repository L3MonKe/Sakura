package dev.sakura.client.module;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.EventPriority;
import dev.sakura.client.event.impl.input.MouseClickEvent;
import dev.sakura.client.event.impl.key.KeyEvent;
import dev.sakura.client.event.impl.render.Render2DEvent;
import dev.sakura.client.event.type.KeyAction;
import dev.sakura.client.manager.impl.NotificationManager;
import dev.sakura.client.module.impl.client.*;
import dev.sakura.client.module.impl.combat.*;
import dev.sakura.client.module.impl.hud.*;
import dev.sakura.client.module.impl.movement.*;
import dev.sakura.client.module.impl.player.*;
import dev.sakura.client.module.impl.player.Timer;
import dev.sakura.client.module.impl.player.inventory.InvManager;
import dev.sakura.client.module.impl.player.inventory.Stealer;
import dev.sakura.client.module.impl.render.*;
import dev.sakura.client.module.impl.settings.RenderSetting;
import dev.sakura.client.values.Value;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static dev.sakura.client.Sakura.mc;

public class ModuleManager {
    private final Map<Class<? extends Module>, Module> modules = new LinkedHashMap<>();

    private void init() {
        // Combat
        add(new AntiBot());
        add(new AttackCrystal());
        add(new AutoWeapon());
        //add(new Criticals());
        add(new KillAura());
        add(new AttackCrystal());
        add(new AutoAnchor());
        add(new AutoTotem());
        add(new AutoThrow());
        add(new LegitCrystal());
        add(new SafeHotbar());
        add(new TickBase());
        add(new TpAura());

        // Movement
        add(new AutoSprint());
        add(new AutoStuck());
        add(new BlinkNoSlow());
        add(new Flight());
        add(new GuiMove());
        add(new FastWeb());
        add(new JumpCooldown());
        add(new KeepSprint());
        add(new NoFall());
        add(new NoSlow());
        add(new SafeWalk());
        add(new Scaffold());
        add(new Stuck());
        add(new Velocity());
        add(new TargetStrafe());
        add(new Speed());

        // Player
        add(new AntiHunger());
        add(new AutoDick());
        add(new AutoSoup());
        add(new AutoTool());
        add(new BowBomb());
        add(new BreakCooldown());
        add(new Disabler());
        add(new MCP());
        add(new FakePlayer());
        add(new GhostHand());
//        add(new PacketMine());
        add(new Stealer());
        add(new Timer());
        add(new ViewLock());
        add(new InvManager());

        // Render
        add(new AspectRatio());
        add(new Animations());
        add(new WorldTweaks());
        add(new CameraClip());
        add(new Chams());
        add(new ChestESP());
        add(new Fullbright());
        add(new Hat());
        add(new ItemPhysics());
        add(new JumpCircles());
        add(new KillEffect());
//        add(new MotionBlur());
        add(new NameTags());
        add(new NoFov());
        add(new NoRender());
        add(new Rainy());
        add(new Shaders());
        add(new TotemParticles());
        add(new Trajectories());
        add(new ViewModel());
        add(new GlowESP());

                // Client
        add(new AutoHeypixel());
        add(new Capes());
//        add(new Chat());
        add(new ClickGui());
        add(new PanelGui());
        add(new HudEditor());
        add(new Targets());
        add(new Teams());

        // Settings
        add(new RenderSetting());

        // HUD
        add(new DynamicIslandHud());
        add(new FPSHud());
        add(new HotbarHud());
        add(new KeyStrokesHud());
        add(new ModuleListHud());
        add(new MSHud());
        add(new NotificationHud());
        add(new NotifyHud());
        add(new PotionHud());
        add(new TargetHud());
        add(new ScoreBoardHud());
        add(new TimeChargeHud());
        add(new WatermarkHud());
    }

    public ModuleManager() {
        Sakura.EVENT_BUS.subscribe(this);
        init();
    }

    private void add(Module module) {
        for (Field field : module.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object obj = field.get(module);
                if (obj instanceof Value<?>) module.getValues().add((Value<?>) obj);
            } catch (IllegalAccessException ignored) {
            }
        }
        modules.put(module.getClass(), module);
    }

    public Collection<Module> getAllModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public Module getModuleByString(String name) {
        for (Module module : modules.values()) {
            if (module.getEnglishName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> cls) {
        return cls.cast(modules.get(cls));
    }

    public List<Module> getModsByCategory(Category m) {
        return modules.values().stream()
                .filter(module -> module.getCategory() == m)
                .sorted(Comparator.comparing(Module::getEnglishName))
                .collect(Collectors.toList());
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (mc.currentScreen != null) return;

        int keyCode = event.getKey();
        if (keyCode == GLFW.GLFW_KEY_UNKNOWN) return;

        boolean isPress = event.getAction() == KeyAction.Press;
        boolean isRelease = event.getAction() == KeyAction.Release;

        List<Module> affectedModules = new ArrayList<>();
        boolean hasEnabling = false;

        for (Module module : modules.values()) {
            if (module.getKey() != keyCode) continue;

            if (module.getBindMode() == Module.BindMode.Toggle && isPress) {
                if (!module.isEnabled()) hasEnabling = true;
                affectedModules.add(module);
            } else if (module.getBindMode() == Module.BindMode.Hold) {
                if (isPress && !module.isEnabled()) {
                    hasEnabling = true;
                    affectedModules.add(module);
                } else if (isRelease && module.isEnabled()) {
                    affectedModules.add(module);
                }
            }
        }

        for (Module module : affectedModules) {
            if (module.getBindMode() == Module.BindMode.Toggle) {
                boolean enabling = !module.isEnabled();
                sendToggleNotification(module, enabling, "");
                module.toggle();
            } else if (module.getBindMode() == Module.BindMode.Hold) {
                if (isPress && !module.isEnabled()) {
                    sendToggleNotification(module, true, " §8(Hold)");
                    module.setState(true);
                } else if (isRelease && module.isEnabled()) {
                    sendToggleNotification(module, false, "");
                    module.setState(false);
                }
            }
        }

        if (!affectedModules.isEmpty()) {
            if (hasEnabling) {
                ClickGui.playEnableSound();
            } else {
                ClickGui.playDisableSound();
            }
        }
    }

    private void sendToggleNotification(Module module, boolean enabling, String suffix) {
        String name = module.getDisplayName();
        String status;
        if (ClickGui.language.get() == ClickGui.LanguageMode.Chinese) {
            status = enabling ? "§a 已开启" : "§c 已关闭";
        } else {
            status = enabling ? "§a enabled" : "§c disabled";
        }
        NotificationManager.send(module.hashCode(), "§7" + name + status + suffix, 3000L);
    }

    @EventHandler
    public void onKey(MouseClickEvent event) {
        if (event.getAction() == KeyAction.Press) {
            if (event.getButton() == 3 || event.getButton() == 4) {
                for (Module module : modules.values()) {
                    if (module.getKey() == -event.getButton()) {
                        module.toggle();
                    }
                }
            }
        }
    }

    public Collection<HudModule> getAllHudModules() {
        return modules.values().stream()
                .filter(HudModule.class::isInstance)
                .map(HudModule.class::cast)
                .collect(Collectors.toList());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRender2D(Render2DEvent event) {
        for (HudModule module : getAllHudModules()) {
            if (module.isState()) {
                module.renderInGame(event.getContext());
            }
        }
    }
}
