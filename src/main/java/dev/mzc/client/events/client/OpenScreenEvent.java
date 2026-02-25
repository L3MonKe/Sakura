package dev.mzc.client.events.client;

import dev.mzc.client.events.Event;
import net.minecraft.client.gui.screen.Screen;

public class OpenScreenEvent extends Event {
    public final Screen screen;

    public OpenScreenEvent(Screen screen) {
        this.screen = screen;
    }
}
