package dev.sakura.client.module;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.input.MouseButtonEvent;
import dev.sakura.client.events.misc.KeyAction;
import dev.sakura.client.events.misc.KeyEvent;
import dev.sakura.client.events.render.Render2DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.NotificationManager;
import dev.sakura.client.module.impl.client.Capes;
import dev.sakura.client.module.impl.client.Chat;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.module.impl.combat.*;
import dev.sakura.client.module.impl.hud.*;
import dev.sakura.client.module.impl.movement.*;
import dev.sakura.client.module.impl.player.*;
import dev.sakura.client.module.impl.player.mine.PacketMine;
import dev.sakura.client.module.impl.render.*;
import dev.sakura.client.values.Value;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static dev.sakura.client.Sakura.mc;

public class ModuleManager {
    private final Map<Class<? extends Module>, Module> modules;

    public ModuleManager() {
        modules = new LinkedHashMap<>();
        Sakura.EVENT_BUS.subscribe(this);

        // Combat
        add(new AnchorAura());
        add(new AutoPot());
        add(new AutoTotem());
        add(new AutoTrap());
        add(new Burrow());
        add(new CrystalAura());
        add(new KillAura());
        add(new SelfTrap());
        add(new Surround());
        add(new Velocity());
        add(new WebAura());

        // Movement
        add(new ArmorFly());
        add(new AutoSprint());
        add(new ElytraFly());
        add(new HoleSnap());
        add(new MoveFix());
        add(new NoFall());
        add(new NoSlow());
        add(new Phase());
        add(new Scaffold());
        add(new Speed());
        add(new Step());

        // Player
        add(new AntiHunger());
        add(new AutoArmor());
        add(new AutoPearl());
        add(new Blink());
        add(new BowBomb());
        add(new FakePlayer());
        add(new InventorySort());
        add(new NoRotate());
        add(new PacketEat());
        add(new PacketMine());
        add(new Replenish());
        add(new TimerModule());

        // Render
        add(new AspectRatio());
        add(new Atmosphere());
        add(new CameraClip());
        add(new Crystal());
        add(new Fullbright());
        add(new Glow());
        add(new Hat());
        add(new JumpCircles());
        add(new NameTags());
        add(new NoRender());
        add(new SwingAnimation());
        add(new TargetESP());
        add(new TotemParticles());
        add(new ViewModel());
        add(new XRay());

        // Client
        add(new Capes());
        add(new Chat());
        add(new ClickGui());
        add(new HudEditor());

        // HUD
        add(new DynamicIslandHud());
        add(new FPSHud());
        add(new HotbarHud());
        add(new KeyStrokesHud());
        add(new ModuleListHud());
        add(new MSHud());
        add(new NotificationHud());
        add(new NotifyHud());
        add(new TargetHud());
        add(new WatermarkHud());
    }

    private void add(Module module) {
        for (final Field field : module.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(module);
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

        boolean isPress = event.getAction() == KeyAction.Press;
        boolean isRelease = event.getAction() == KeyAction.Release;

        List<Module> affectedModules = new ArrayList<>();
        boolean hasEnabling = false;

        for (Module module : modules.values()) {
            if (module.getKey() != event.getKey()) continue;

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
                sendToggleNotification(module, enabling, "", false);
                module.toggle();
            } else if (module.getBindMode() == Module.BindMode.Hold) {
                if (isPress && !module.isEnabled()) {
                    sendToggleNotification(module, true, " §8(Hold)", false);
                    module.setState(true);
                } else if (isRelease && module.isEnabled()) {
                    sendToggleNotification(module, false, "", false);
                    module.setState(false);
                }
            }
        }

        if (!affectedModules.isEmpty()) {
            if (hasEnabling) {
                Managers.SOUND.playSound(Managers.SOUND.ENABLE);
            } else {
                Managers.SOUND.playSound(Managers.SOUND.DISABLE);
            }
        }
    }

    private void sendToggleNotification(Module module, boolean enabling, String suffix, boolean playSound) {
        String name = module.getDisplayName();
        String status;
        if (ClickGui.language.get() == ClickGui.Language.Chinese) {
            status = enabling ? "§a 已开启" : "§c 已关闭";
        } else {
            status = enabling ? "§a enabled" : "§c disabled";
        }
        NotificationManager.send(module.hashCode(), "§7" + name + status + suffix, 3000L);
        if (playSound) {
            Managers.SOUND.playSound(enabling ? Managers.SOUND.ENABLE : Managers.SOUND.DISABLE);
        }
    }

    @EventHandler
    public void onKey(MouseButtonEvent e) {
        if (e.getAction() == KeyAction.Press) {
            if (e.getButton() == 3 || e.getButton() == 4) {
                for (Module module : modules.values()) {
                    if (module.getKey() == -e.getButton()) {
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