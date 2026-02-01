package dev.mahiro.client.manager.impl;

import dev.mahiro.client.auth.AuthGate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static dev.mahiro.client.Mahiro.mc;

public class SoundManager {
    public SoundEvent ENABLE = registerSound("enable");
    public SoundEvent DISABLE = registerSound("disable");
    public SoundEvent JELLO_ENABLE = registerSound("activate");
    public SoundEvent JELLO_DISABLE = registerSound("deactivate");

    private SoundEvent registerSound(String name) {
        Identifier id = Identifier.of("mahiro", name);
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
        if (sound == null || mc.player == null) return;
        AuthGate.doTickCheck(mc);
        mc.executeSync(() -> mc.player.playSound(sound, volume, pitch));
    }
}
