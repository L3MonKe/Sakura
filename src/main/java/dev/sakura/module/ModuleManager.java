package dev.sakura.module;

import dev.sakura.Sakura;
import dev.sakura.events.input.MouseButtonEvent;
import dev.sakura.events.misc.KeyAction;
import dev.sakura.events.misc.KeyEvent;
import dev.sakura.events.render.Render2DEvent;
import dev.sakura.gui.clickgui.ClickGuiScreen;
import dev.sakura.gui.hud.HudEditorScreen;
import dev.sakura.manager.impl.NotificationManager;
import dev.sakura.manager.impl.SoundManager;
import dev.sakura.module.impl.client.ClickGui;
import dev.sakura.module.impl.client.HudEditor;
import dev.sakura.module.impl.client.StringTest;
import dev.sakura.module.impl.combat.AutoPot;
import dev.sakura.module.impl.combat.Burrow;
import dev.sakura.module.impl.combat.Velocity;
import dev.sakura.module.impl.hud.*;
import dev.sakura.module.impl.movement.*;
import dev.sakura.module.impl.render.*;
import dev.sakura.values.Value;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.Screen;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModuleManager {
    private final Map<Class<? extends Module>, Module> modules;

    public ModuleManager() {
        modules = new LinkedHashMap<>();
    }

    @SuppressWarnings("Convert2MethodRef")
    private static class ManualLoader {
        static void load(ModuleManager manager) {
            // Combat
            manager.tryLoad(() -> new Burrow());
            manager.tryLoad(() -> new Velocity());
            manager.tryLoad(() -> new AutoPot());


            // Movement
            manager.tryLoad(() -> new AutoSprint());
            manager.tryLoad(() -> new Speed());
            manager.tryLoad(() -> new Step());
            manager.tryLoad(() -> new NoSlow());
            manager.tryLoad(() -> new HoleSnap());

            // Render
            manager.tryLoad(() -> new CameraClip());
            manager.tryLoad(() -> new Fullbright());
            manager.tryLoad(() -> new NoRender());
            manager.tryLoad(() -> new SwingAnimation());
            manager.tryLoad(() -> new ViewModel());

            // Client
            manager.tryLoad(() -> new ClickGui());
            manager.tryLoad(() -> new HudEditor());
            manager.tryLoad(() -> new StringTest());

            // HUD
            manager.tryLoad(() -> new FPSHud());
            manager.tryLoad(() -> new NotificationHud());
            manager.tryLoad(() -> new WatermarkHud());
            manager.tryLoad(() -> new MSHud());
            manager.tryLoad(() -> new DynamicIslandHud());
        }
    }

    public void initModules() {
        Sakura.EVENT_BUS.subscribe(this);

        try {
            ManualLoader.load(this);
        } catch (Throwable ignored) {
        }
    }

    private void tryLoad(Supplier<Module> supplier) {
        try {
            addModule(supplier.get());
        } catch (NoClassDefFoundError | Exception ignored) {
        }
    }

    private void addModule(Module module) {
        for (final Field field : module.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(module);
                if (obj instanceof Value<?>) module.getValues().add((Value<?>) obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        modules.put(module.getClass(), module);
    }

    public Collection<Module> getAllModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public Module getModule(String name) {
        for (Module module : modules.values()) {
            if (module.getName().equalsIgnoreCase(name)) {
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
                .sorted(Comparator.comparing(Module::getName))
                .collect(Collectors.toList());
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        Screen currentScreen = Sakura.mc.currentScreen;
        if (currentScreen instanceof ClickGuiScreen || currentScreen instanceof HudEditorScreen) {
            return;
        }

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
                SoundManager.playSound(SoundManager.ENABLE);
            } else {
                SoundManager.playSound(SoundManager.DISABLE);
            }
        }
    }

    private void sendToggleNotification(Module module, boolean enabling) {
        sendToggleNotification(module, enabling, "", true);
    }

    private void sendToggleNotification(Module module, boolean enabling, String suffix, boolean playSound) {
        NotificationManager.send(module.hashCode(), "§7" + module.getName() + (enabling ? "§a enabled" : "§c disabled") + suffix, 3000L);
        if (playSound) {
            SoundManager.playSound(enabling ? SoundManager.ENABLE : SoundManager.DISABLE);
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

    @EventHandler
    public void onRender2D(Render2DEvent event) {
        for (HudModule module : getAllHudModules()) {
            if (module.isState()) {
                module.renderInGame(event.getContext());
            }
        }
    }
}
