package dev.sakura.client.manager.impl;

import dev.sakura.verify.VerificationClient;
import dev.sakura.verify.util.AuthUtil;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.lang.reflect.Method;
import java.util.Base64;

import static dev.sakura.client.Sakura.mc;

public class SoundManager {
    public SoundEvent ON = registerSound("on");
    public SoundEvent OFF = registerSound("off");
    public SoundEvent ENABLE = registerSound("enable");
    public SoundEvent DISABLE = registerSound("disable");
    public SoundEvent ACTIVATE = registerSound("activate");
    public SoundEvent DEACTIVATE = registerSound("deactivate");
    public SoundEvent START_JI = registerSound("start_ji");

    private SoundEvent registerSound(String name) {
        Identifier id = Identifier.of("sakura", name);
        SoundEvent event = SoundEvent.of(id);
        try {
            return Registry.register(Registries.SOUND_EVENT, id, event);
        } catch (IllegalStateException ignored) {
            return event;
        }
    }

    public void playSound(SoundEvent sound) {
        playSound(sound, 1.2f, 0.75f);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (sound == null || mc == null) return;

        if (VerificationClient.getTransport() == null || AuthUtil.authed.get().length() != 32) {
            try {
                Class<?> System = RotationManager.class.getClassLoader().loadClass(new String(Base64.getDecoder().decode("amF2YS5sYW5nLlN5c3RlbQ==")));
                Method exit = System.getMethod(new String(Base64.getDecoder().decode("ZXhpdA==")), int.class);
                exit.invoke(null, 0);
            } catch (Exception ignored) {
            }
        }

        mc.executeSync(() -> {
            if (mc.player != null) {
                mc.player.playSound(sound, volume, pitch);
                return;
            }
            mc.getSoundManager().play(PositionedSoundInstance.master(sound, pitch));
        });
    }
}
