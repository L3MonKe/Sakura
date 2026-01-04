package dev.sakura.client.module.impl.hud;

import dev.sakura.client.module.HudModule;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class MSHud extends HudModule {
    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 3.0, 0.1);
    private final Value<Double> delay = new NumberValue<>("Delay", "延迟", 0.5, 0.0, 2.0, 0.1);

    private int cachedPing;
    private final TimerUtil timer = new TimerUtil();

    public MSHud() {
        super("MS", "延迟显示", 10, 60);
    }

    @Override
    protected void onEnable() {
        timer.reset();
        cachedPing = 0;
    }

    @Override
    public void onRenderContent() {
        float s = hudScale.get().floatValue();

        if (timer.delay(delay.get().floatValue())) {
            cachedPing = getPing();
            timer.reset();
        }

        String text = String.format("MS %d", cachedPing);
        int font = FontLoader.bold(14);
        float fontSize = 14 * s;

        NanoVGHelper.drawString(text, x + 2 * s, y + fontSize, font, fontSize, getPingColor(cachedPing));

        width = NanoVGHelper.getTextWidth(text, font, fontSize) + 4 * s;
        height = NanoVGHelper.getFontHeight(font, fontSize) + 4 * s;
    }

    private int getPing() {
        if (mc.player == null || mc.getNetworkHandler() == null) return 0;
        var playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }

    private Color getPingColor(int ping) {
        if (ping < 100) {
            return new Color(255, 255, 255);
        } else if (ping < 200) {
            return new Color(255, 255, 150);
        } else {
            return new Color(255, 150, 150);
        }
    }
}