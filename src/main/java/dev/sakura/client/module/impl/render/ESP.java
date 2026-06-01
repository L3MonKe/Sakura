package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;

public class ESP extends Module {
    public ESP() {
        super("ESP", "ES的P", Category.Render);
    }

    private enum ColorMode {
        Solid,
        Gradient
    }

    private final BoolValue players = new BoolValue("Players", "玩家", true);
    private final BoolValue self = new BoolValue("Self", "自个儿", true, players::get);
    private final BoolValue crystals = new BoolValue("Crystals", "水晶", true);
    private final BoolValue creatures = new BoolValue("Creatures", "生物", false);
    private final BoolValue monsters = new BoolValue("Monsters", "怪物", false);
    private final BoolValue ambients = new BoolValue("Ambients", "不知道", false);
    private final BoolValue others = new BoolValue("Others", "其他", false);

    public final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Solid);
    public final NumberValue<Integer> radius = new NumberValue<>("Radius", "半径", 4, 2, 30, 1);
    public final NumberValue<Double> exposure = new NumberValue<>("Exposure", "曝光", 2.2, 0.5, 3.5, 0.1);
    public final ColorValue color = new ColorValue("Color", "颜色", new Color(255, 183, 197), () -> colorMode.is(ColorMode.Solid));
    public final ColorValue color1 = new ColorValue("Color1", "颜色1", new Color(255, 183, 197), () -> colorMode.is(ColorMode.Gradient));
    public final ColorValue color2 = new ColorValue("Color2", "颜色2", new Color(0, 255, 200), () -> colorMode.is(ColorMode.Gradient));
    public final NumberValue<Double> gradientSpeed = new NumberValue<>("Gradient Speed", "渐变速度", 2.0, 0.1, 10.0, 0.1, () -> colorMode.is(ColorMode.Gradient));

    public boolean shouldRender(Entity entity) {
        if (entity == null || mc.player == null) {
            return false;
        }

        if (entity instanceof PlayerEntity) {
            if (entity == mc.player && !self.get()) {
                return false;
            }
            return players.get();
        }

        if (entity instanceof EndCrystalEntity) {
            return crystals.get();
        }

        SpawnGroup group = entity.getType().getSpawnGroup();
        if (group == SpawnGroup.CREATURE || group == SpawnGroup.WATER_CREATURE) return creatures.get();
        if (group == SpawnGroup.MONSTER) return monsters.get();
        if (group == SpawnGroup.AMBIENT || group == SpawnGroup.WATER_AMBIENT) return ambients.get();
        return others.get();
    }

    public boolean isGradient() {
        return colorMode.is(ColorMode.Gradient);
    }
}
