package dev.mzc.client.module.impl.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.utils.render.Render3DUtil;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

import java.awt.*;

public class BlockOutline extends Module {

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Client, Mode.class);
    private final EnumValue<RenderMode> renderMode = new EnumValue<>("RenderMode", "渲染模式", RenderMode.Full, RenderMode.class);
    private final ColorValue color1 = new ColorValue("Color1", "颜色1", new Color(255, 255, 255, 255), () -> !mode.is(Mode.Client));
    private final ColorValue color2 = new ColorValue("Color2", "颜色2", new Color(0, 150, 255, 255), () -> mode.is(Mode.Double));
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 5.0, 1.0, 20.0, 0.5, () -> mode.is(Mode.Rainbow) || mode.is(Mode.Double));
    private final NumberValue<Float> lineWidth = new NumberValue<>("LineWidth", "线宽", 2.0f, 0.1f, 5.0f, 0.1f);

    public BlockOutline() {
        super("BlockOutline", "方块描边", Category.Render);
        this.setType(ModuleType.All);
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult hitResult = (BlockHitResult) mc.crosshairTarget;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = mc.world.getBlockState(pos);

        if (state.isAir() || !mc.world.getWorldBorder().contains(pos)) return;

        VoxelShape shape = state.getOutlineShape(mc.world, pos);
        if (shape.isEmpty()) return;

        Box box = shape.getBoundingBox().offset(pos);
        
        Color renderColor = getColor();
        
        if (renderMode.is(RenderMode.Full)) {
            Render3DUtil.drawBoxOutline(event.getMatrices(), box, renderColor.getRGB(), lineWidth.get());
        } else {
            Render3DUtil.drawBoxFaceOutline(event.getMatrices(), box, hitResult.getSide(), renderColor.getRGB(), lineWidth.get());
        }
    }

    private Color getColor() {
        switch (mode.get()) {
            case Client:
                return ClickGui.color(0);
            case Double:
                return fade(color1.get(), color2.get());
            case Single:
                return color1.get();
            case Rainbow:
                return getRainbow();
            default:
                return Color.WHITE;
        }
    }
    
    private Color fade(Color c1, Color c2) {
        double duration = 5.0 / speed.get(); // 1.0 speed -> 5s, 5.0 speed -> 1s
        double time = (System.currentTimeMillis() / 1000.0) % (duration * 2);
        double ratio = Math.abs(time - duration) / duration; 
        int r = (int) (c1.getRed() * ratio + c2.getRed() * (1 - ratio));
        int g = (int) (c1.getGreen() * ratio + c2.getGreen() * (1 - ratio));
        int b = (int) (c1.getBlue() * ratio + c2.getBlue() * (1 - ratio));
        int a = (int) (c1.getAlpha() * ratio + c2.getAlpha() * (1 - ratio));
        return new Color(r, g, b, a);
    }

    private Color getRainbow() {
        double duration = 5.0 / speed.get(); // 1.0 speed -> 5s cycle
        float hue = (float) ((System.currentTimeMillis() % (int)(duration * 1000)) / (duration * 1000));
        return Color.getHSBColor(hue, 0.8f, 1f);
    }

    public enum Mode {
        Client("客户端"),
        Double("双色"),
        Single("单色"),
        Rainbow("彩虹");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    public enum RenderMode {
        Full("完整"),
        Face("面");

        private final String cnName;

        RenderMode(String cnName) {
            this.cnName = cnName;
        }
    }
}
