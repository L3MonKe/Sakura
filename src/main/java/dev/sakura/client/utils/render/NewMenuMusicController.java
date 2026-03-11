package dev.sakura.client.utils.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.manager.Managers;
import net.minecraft.sound.SoundCategory;

import static dev.sakura.client.Sakura.mc;

public final class NewMenuMusicController {
    private static boolean delayNextStart;
    private static boolean wasInNewMenu;
    private static boolean playedInCurrentEntry;
    private static boolean waitingReplay;
    private static long playAtMs = -1L;
    private static long replayAtMs = -1L;

    private NewMenuMusicController() {
    }

    public static void requestDelayNextStart() {
        delayNextStart = true;
    }

    public static void tick() {
        if (mc == null || Sakura.CONFIG == null || Managers.SOUND == null) {
            return;
        }
        boolean inWorld = mc.world != null;
        boolean inNewMenu = !inWorld && Sakura.CONFIG.getClientConfig().useNewMainMenu;

        if (inWorld) {
            Managers.SOUND.stopNewMenuMusic();
            resetState();
            return;
        }

        if (!inNewMenu) {
            Managers.SOUND.stopNewMenuMusic();
            resetState();
            return;
        }

        mc.getSoundManager().stopSounds(null, SoundCategory.MUSIC);

        if (!wasInNewMenu) {
            playedInCurrentEntry = false;
            waitingReplay = false;
            playAtMs = System.currentTimeMillis() + (delayNextStart ? 3000L : 0L);
            replayAtMs = -1L;
            delayNextStart = false;
        }

        if (!playedInCurrentEntry && playAtMs >= 0L && System.currentTimeMillis() >= playAtMs) {
            Managers.SOUND.playNewMenuMusic();
            playedInCurrentEntry = true;
            waitingReplay = false;
            replayAtMs = -1L;
        }

        if (playedInCurrentEntry && !Managers.SOUND.isNewMenuMusicPlaying()) {
            if (!waitingReplay) {
                waitingReplay = true;
                replayAtMs = System.currentTimeMillis() + 5000L;
            } else if (replayAtMs >= 0L && System.currentTimeMillis() >= replayAtMs) {
                Managers.SOUND.playNewMenuMusic();
                waitingReplay = false;
                replayAtMs = -1L;
            }
        }

        wasInNewMenu = true;
    }

    private static void resetState() {
        wasInNewMenu = false;
        playedInCurrentEntry = false;
        waitingReplay = false;
        playAtMs = -1L;
        replayAtMs = -1L;
    }
}
