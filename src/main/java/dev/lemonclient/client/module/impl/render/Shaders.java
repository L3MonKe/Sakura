package dev.lemonclient.client.module.impl.render;

import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;

public class Shaders extends Module {
    public Shaders() {
        super("Shaders", "着色器", Category.Render);
    }

    /*private final BoolValue hands = new BoolValue("Hands", "小手手", true);
    private final BoolValue players = new BoolValue("Players", "玩家", true);
    private final BoolValue self = new BoolValue("Self", "自个儿", true, players::get);
    //private final BoolValue friends = new BoolValue("Friends", "队友", true);
    private final BoolValue crystals = new BoolValue("Crystals", "水晶", true);
    private final BoolValue creatures = new BoolValue("Creatures", "生物", false);
    private final BoolValue monsters = new BoolValue("Monsters", "怪物", false);
    private final BoolValue ambients = new BoolValue("Ambients", "不知道", false);
    private final BoolValue others = new BoolValue("Others", "其他", false);

    public EnumValue<ShaderManager.Shader> mode = new EnumValue<>("Mode", "模式", ShaderManager.Shader.Default);
    public EnumValue<ShaderManager.Shader> handsMode = new EnumValue<>("Hands Mode", "手部模式", ShaderManager.Shader.Default);

    public final NumberValue<Integer> maxRange = new NumberValue<>("MaxRange", "最大距离", 64, 16, 256, 10, () -> players.get() || crystals.get() || creatures.get() || monsters.get() || ambients.get() || others.get());
    public final NumberValue<Double> factor = new NumberValue<>("Gradient Factor", "Gradient速度", 2.0, 0.0, 20.0, 1.0, () -> mode.is(ShaderManager.Shader.Gradient) || handsMode.is(ShaderManager.Shader.Gradient));
    public final NumberValue<Double> gradient = new NumberValue<>("Gradient", "Gradient", 2.0, 0.0, 20.0, 1.0, () -> mode.is(ShaderManager.Shader.Gradient) || handsMode.is(ShaderManager.Shader.Gradient));
    public final NumberValue<Integer> alpha2 = new NumberValue<>("Gradient Alpha", "Gradient透明度", 170, 0, 255, 10, () -> mode.is(ShaderManager.Shader.Gradient) || handsMode.is(ShaderManager.Shader.Gradient));
    public final NumberValue<Integer> lineWidth = new NumberValue<>("Line Width", "线条宽度", 2, 0, 500, 10);
    public final NumberValue<Integer> quality = new NumberValue<>("Quality", "Quality", 3, 0, 6, 1);
    public final NumberValue<Integer> octaves = new NumberValue<>("Smoke Octaves", "Smoke Octaves", 10, 5, 30, 1);
    public final NumberValue<Integer> fillAlpha = new NumberValue<>("Fill Alpha", "填充透明度", 170, 0, 255, 10);
    public final BoolValue glow = new BoolValue("SmokeGlow", "抽烟Glow", true);

    public final ColorValue outlineColor = new ColorValue("Outline", "外边", new Color(0x8800FF00));
    public final ColorValue outlineColor1 = new ColorValue("Smoke Outline", "烟雾外边", new Color(0x8800FF00), () -> mode.is(ShaderManager.Shader.Smoke) || handsMode.is(ShaderManager.Shader.Smoke));
    public final ColorValue outlineColor2 = new ColorValue("Smoke Outline2", "烟雾次外边", new Color(0x8800FF00), () -> mode.is(ShaderManager.Shader.Smoke) || handsMode.is(ShaderManager.Shader.Smoke));
    public final ColorValue fillColor1 = new ColorValue("Fill", "填充", new Color(0x8800FF00));
    public final ColorValue fillColor2 = new ColorValue("Smoke Fill", "烟雾填充", new Color(0x8800FF00));
    public final ColorValue fillColor3 = new ColorValue("Smoke Fill2", "烟雾次填充", new Color(0x8800FF00));

    public boolean shouldRender(Entity entity) {
        if (entity == null || mc.player == null) {
            return false;
        }

        if (mc.player.squaredDistanceTo(entity.getPos()) > maxRange.get() * maxRange.get()) {
            return false;
        }

        if (entity instanceof PlayerEntity) {
            if (entity == mc.player && !self.get()) {
                return false;
            }

            return players.get();
        }

        if (entity instanceof EndCrystalEntity)
            return crystals.get();

        return switch (entity.getType().getSpawnGroup()) {
            case CREATURE, WATER_CREATURE -> creatures.get();
            case MONSTER -> monsters.get();
            case AMBIENT, WATER_AMBIENT -> ambients.get();
            default -> others.get();
        };
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (hands.get()) {
            Managers.SHADER.renderShader(() -> ((IGameRenderer) mc.gameRenderer).irenderHand(mc.gameRenderer.getCamera(), mc.getRenderTickCounter().getTickDelta(true), event.getMatrices().peek().getPositionMatrix()), handsMode.get());
        }
    }

    @Override
    public void onDisable() {
    }*/
}
