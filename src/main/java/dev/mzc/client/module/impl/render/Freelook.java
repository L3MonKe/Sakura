package dev.mzc.client.module.impl.render;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.math.MathHelper;

public class Freelook extends Module {
    public static float cameraYaw;
    public static float cameraPitch;

    private Perspective originalPerspective;

    public Freelook() {
        super("Freelook", "自由视角", Category.Render);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        originalPerspective = mc.options.getPerspective();
        cameraYaw = mc.player.getYaw();
        cameraPitch = mc.player.getPitch();
        mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        if (originalPerspective != null) {
            mc.options.setPerspective(originalPerspective);
        }
    }
    
    // 用于 Mixin 调用，处理鼠标移动
    public void onMouseUpdate(double dx, double dy) {
        float f = (float) dy * 0.15F;
        float g = (float) dx * 0.15F;
        cameraYaw += g;
        cameraPitch += f;
        
        // 限制 Pitch
        cameraPitch = MathHelper.clamp(cameraPitch, -90.0f, 90.0f);
    }
}
