package com.zeta.client.manager.impl;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

import static com.zeta.client.Zeta.mc;

public class SoundManager {
    private final Set<String> REGISTERED_SOUND_FILES = new HashSet<>();

    public SoundEvent ENABLE = registerSound("enable");
    public SoundEvent DISABLE = registerSound("disable");
    public SoundEvent JELLO_ENABLE = registerSound("activate");
    public SoundEvent JELLO_DISABLE = registerSound("deactivate");

    private SoundEvent registerSound(String name) {
        registerSoundFile(name + ".ogg");
        Identifier id = Identifier.of("zeta", name);
        SoundEvent event = SoundEvent.of(id);
        try {
            return Registry.register(Registries.SOUND_EVENT, id, event);
        } catch (IllegalStateException ignored) {
            return event;
        }
    }

    private void registerSoundFile(String soundFile) {
        REGISTERED_SOUND_FILES.add(soundFile);
    }

    public void playSound(SoundEvent sound) {
        playSound(sound, 1.2f, 0.75f);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (sound == null || mc.player == null) return;
        mc.executeSync(() -> mc.player.playSound(sound, volume, pitch));
    }

    public Set<String> getRegisteredSoundFiles() {
        return new HashSet<>(REGISTERED_SOUND_FILES);
    }
}
