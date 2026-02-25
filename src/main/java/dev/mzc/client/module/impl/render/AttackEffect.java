package dev.mzc.client.module.impl.render;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

public class AttackEffect extends Module {
    public enum ParticleMode {
        HEART("爱心", ParticleTypes.HEART),
        FLAME("火焰", ParticleTypes.FLAME),
        VILLAGER_HAPPY("村民快乐", ParticleTypes.HAPPY_VILLAGER),
        NOTE("音符", ParticleTypes.NOTE),
        CLOUD("云", ParticleTypes.CLOUD),
        SMOKE("烟雾", ParticleTypes.SMOKE),
        SOUL_FLAME("灵魂火", ParticleTypes.SOUL_FIRE_FLAME),
        LAVA("岩浆", ParticleTypes.LAVA),
        ENCHANT("附魔", ParticleTypes.ENCHANT),
        WITCH("女巫", ParticleTypes.WITCH),
        DAMAGE("伤害", ParticleTypes.DAMAGE_INDICATOR);

        private final String cnName;
        private final SimpleParticleType effect;

        ParticleMode(String cnName, SimpleParticleType effect) {
            this.cnName = cnName;
            this.effect = effect;
        }

        public ParticleEffect getEffect() {
            return effect;
        }
    }
    public final BoolValue always = new BoolValue("Always", "平砍触发", false);
    public final EnumValue<ParticleMode> particle = new EnumValue<>("Particle", "粒子类型", ParticleMode.HEART);
    public final NumberValue<Double> velocityMultiplier = new NumberValue<>("Velocity Multiplier", "速度倍率", 0.6, 0.1, 2.0, 0.1);
    public final NumberValue<Double> lifeMultiplier = new NumberValue<>("Life Multiplier", "寿命倍率", 0.7, 0.1, 2.0, 0.1);

    public AttackEffect() {
        super("AttackEffect", "攻击特效", Category.Render);
        this.setType(ModuleType.All);
    }
}
