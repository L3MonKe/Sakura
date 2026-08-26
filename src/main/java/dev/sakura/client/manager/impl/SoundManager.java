package dev.sakura.client.manager.impl;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static dev.sakura.client.Sakura.mc;

public class SoundManager {
    public SoundEvent ON = registerSound("on");
    public SoundEvent OFF = registerSound("off");
    public SoundEvent ENABLE = registerSound("enable");
    public SoundEvent DISABLE = registerSound("disable");
    public SoundEvent ACTIVATE = registerSound("activate");
    public SoundEvent DEACTIVATE = registerSound("deactivate");
    public SoundEvent START_JI = registerSound("start_ji");
    public SoundEvent NEW_MENU_MUSIC = registerSound("new_menu_music");
    public SoundEvent MENU_CONFIRM = registerSound("menu_confirm");
    public SoundEvent MENU_CANCEL = registerSound("menu_cancel");
    private SoundInstance newMenuMusicInstance;

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

        mc.executeSync(() -> {
            if (mc.player != null) {
                mc.player.playSound(sound, volume, pitch);
                return;
            }
            mc.getSoundManager().play(PositionedSoundInstance.master(sound, pitch));
        });
    }

    public void playNewMenuMusic() {
        if (mc == null || NEW_MENU_MUSIC == null) {
            return;
        }
        mc.executeSync(() -> {
            if (newMenuMusicInstance != null) {
                mc.getSoundManager().stop(newMenuMusicInstance);
            }
            newMenuMusicInstance = PositionedSoundInstance.master(NEW_MENU_MUSIC, 1.0f);
            mc.getSoundManager().play(newMenuMusicInstance);
        });
    }

    public void stopNewMenuMusic() {
        if (mc == null) {
            return;
        }
        mc.executeSync(() -> {
            if (newMenuMusicInstance != null) {
                mc.getSoundManager().stop(newMenuMusicInstance);
                newMenuMusicInstance = null;
            }
        });
    }

    public boolean isNewMenuMusicPlaying() {
        if (mc == null || newMenuMusicInstance == null) {
            return false;
        }
        return mc.getSoundManager().isPlaying(newMenuMusicInstance);
    }
}
