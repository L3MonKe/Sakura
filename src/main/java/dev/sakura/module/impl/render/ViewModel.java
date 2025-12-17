package dev.sakura.module.impl.render;

import dev.sakura.events.render.item.HeldItemRendererEvent;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Hand;

public class ViewModel extends Module {

    public ViewModel() {
        super("ViewModel", Category.Render);
    }

    private final NumberValue<Double> mainX = new NumberValue<>("MainX", 0.0, -3.0, 3.0, 0.01);
    private final NumberValue<Double> mainY = new NumberValue<>("MainY", 0.0, -3.0, 3.0, 0.01);
    private final NumberValue<Double> mainZ = new NumberValue<>("MainZ", 0.0, -3.0, 3.0, 0.01);

    private final NumberValue<Double> offX = new NumberValue<>("OffX", 0.0, -3.0, 3.0, 0.01);
    private final NumberValue<Double> offY = new NumberValue<>("OffY", 0.0, -3.0, 3.0, 0.01);
    private final NumberValue<Double> offZ = new NumberValue<>("OffZ", 0.0, -3.0, 3.0, 0.01);

    @EventHandler
    private void onHeldItemRender(HeldItemRendererEvent event) {
        if (event.getHand() == Hand.MAIN_HAND) {
            event.getMatrices().translate(mainX.getValue(), mainY.getValue(), mainZ.getValue());
        } else {
            event.getMatrices().translate(offX.getValue(), offY.getValue(), offZ.getValue());
        }
    }
}