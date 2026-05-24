package dev.sakura.client.module.impl.hud;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.gui.hudeditor.HudEditorScreen;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.HealthBarShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseOutSine;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.render.SakuraPipelines;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class TargetHud extends HudModule {
    public TargetHud() {
        super("TargetHud", "目标显示", 150, 50);
        this.width = 150;
        this.height = 50;
    }

    public enum HPmodeEn {
        HP, Percentage, TextHP
    }

    public enum StyleEn {
        ThunderHack, Sakura, Hanabi
    }

    public enum ImageModeEn {
        None, Anime, Custom
    }

    public enum AvatarPosEn {
        Left, OnBar
    }

    public enum MahiroParticleModeEn {
        Spread, Fall
    }

    public enum MahiroBarShapeEn {
        Style1, Style2
    }

    public enum BarModeEn {
        Normal, Shader
    }

    private final EnumValue<StyleEn> style = new EnumValue<>("Style", "样式", StyleEn.Sakura);

    // Sakura Settings
    private final EnumValue<MahiroBarShapeEn> MahiroBarShape = new EnumValue<>("BarShape", "血条形状", MahiroBarShapeEn.Style1, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroScale = new NumberValue<>("Scale", "整体缩放", 1.0, 0.5, 2.0, 0.1, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroWidth = new NumberValue<>("Width", "宽度", 150.0, 100.0, 300.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroHeight = new NumberValue<>("Height", "高度", 50.0, 30.0, 100.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroRadius = new NumberValue<>("Radius", "圆角半径", 10.0, 0.0, 20.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroBlurRadius = new NumberValue<>("BlurRadius", "模糊半径", 10.0, 1.0, 50.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroBarHeight = new NumberValue<>("BarHeight", "血条粗细", 10.0, 2.0, 30.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroBarRadius1 = new NumberValue<>("BarRadius1", "形状1圆角", 4.0, 0.0, 15.0, 1.0, () -> style.get() == StyleEn.Sakura && MahiroBarShape.get() == MahiroBarShapeEn.Style1);
    private final NumberValue<Double> MahiroBarRadius2 = new NumberValue<>("BarRadius2", "形状2圆角", 0.0, 0.0, 15.0, 1.0, () -> style.get() == StyleEn.Sakura && MahiroBarShape.get() == MahiroBarShapeEn.Style2);
    private final EnumValue<AvatarPosEn> MahiroAvatarPos = new EnumValue<>("AvatarPos", "头像位置", AvatarPosEn.Left, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroNameSize = new NumberValue<>("NameSize", "名字大小", 14.0, 8.0, 24.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroNameX = new NumberValue<>("NameX", "名字X偏移", 0.0, -50.0, 50.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroNameY = new NumberValue<>("NameY", "名字Y偏移", 0.0, -50.0, 50.0, 1.0, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroOnBarHeight = new NumberValue<>("OnBarHeight", "悬浮高度", 15.0, 0.0, 50.0, 1.0, () -> style.get() == StyleEn.Sakura && MahiroAvatarPos.get() == AvatarPosEn.OnBar);
    private final ColorValue MahiroBgColor = new ColorValue("BgColor", "背景颜色", new Color(0, 0, 0, 80), () -> style.get() == StyleEn.Sakura);
    private final ColorValue MahiroBarBgColor = new ColorValue("BarBgColor", "血条背景颜色", new Color(30, 30, 30), () -> style.get() == StyleEn.Sakura);
    private final BoolValue MahiroShadow = new BoolValue("Shadow", "背景阴影", false, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> MahiroShadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 1.0, () -> style.get() == StyleEn.Sakura && MahiroShadow.get());
    private final NumberValue<Double> MahiroShadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.05, () -> style.get() == StyleEn.Sakura && MahiroShadow.get());

    public enum MahiroShadowModeEn {Solid, Gradient}

    private final EnumValue<MahiroShadowModeEn> MahiroShadowMode = new EnumValue<>("ShadowMode", "阴影模式", MahiroShadowModeEn.Solid, () -> style.get() == StyleEn.Sakura && MahiroShadow.get());

    // Sakura Delay Settings
    private final BoolValue MahiroDelay = new BoolValue("DelayBar", "延迟血条", true, () -> style.get() == StyleEn.Sakura);
    private final BoolValue MahiroDelayWait = new BoolValue("WaitMode", "受伤等待", true, () -> style.get() == StyleEn.Sakura && MahiroDelay.get());
    private final NumberValue<Integer> MahiroDelayTime = new NumberValue<>("DelayTime", "延迟时间(ms)", 600, 0, 2000, 50, () -> style.get() == StyleEn.Sakura && MahiroDelay.get() && MahiroDelayWait.get());
    private final NumberValue<Double> MahiroDelaySpeed = new NumberValue<>("DelaySpeed", "延迟动画速度", 2.0, 0.1, 10.0, 0.1, () -> style.get() == StyleEn.Sakura && MahiroDelay.get());
    private final ColorValue MahiroDelayColor = new ColorValue("DelayColor", "延迟血条颜色", new Color(255, 255, 0, 150), () -> style.get() == StyleEn.Sakura && MahiroDelay.get());

    // Sakura Particle Settings
    private final BoolValue MahiroParticles = new BoolValue("Particles", "粒子效果", false, () -> style.get() == StyleEn.Sakura);
    private final BoolValue MahiroBarParticles = new BoolValue("BarParticles", "血条粒子", true, () -> style.get() == StyleEn.Sakura && MahiroParticles.get());
    private final BoolValue MahiroAvatarParticles = new BoolValue("AvatarParticles", "头像粒子", true, () -> style.get() == StyleEn.Sakura && MahiroParticles.get());
    private final NumberValue<Integer> MahiroParticleAmount = new NumberValue<>("ParticleAmount", "粒子数量", 8, 3, 20, 1, () -> style.get() == StyleEn.Sakura && MahiroParticles.get());
    private final NumberValue<Double> MahiroParticleRange = new NumberValue<>("ParticleRange", "粒子范围", 2.0, 0.5, 5.0, 0.5, () -> style.get() == StyleEn.Sakura && MahiroParticles.get());
    private final EnumValue<MahiroParticleModeEn> MahiroParticleMode = new EnumValue<>("ParticleMode", "粒子模式", MahiroParticleModeEn.Spread, () -> style.get() == StyleEn.Sakura && MahiroParticles.get());

    private final BoolValue hanabiBarGlow = new BoolValue("HanabiBarGlow", "Hanabi血条发光", true, () -> style.get() == StyleEn.Hanabi);
    private final NumberValue<Double> hanabiBarGlowRange = new NumberValue<>("HanabiGlowRange", "Hanabi发光范围", 14.0, 0.0, 40.0, 1.0, () -> style.get() == StyleEn.Hanabi && hanabiBarGlow.get());
    private final NumberValue<Double> hanabiBarGlowStrength = new NumberValue<>("HanabiGlowStrength", "Hanabi发光强度", 0.9, 0.0, 1.0, 0.05, () -> style.get() == StyleEn.Hanabi && hanabiBarGlow.get());

    private static final float HANABI_NAME_SIZE = 11f;
    private static final float HANABI_NAME_X = 0f;
    private static final float HANABI_NAME_Y = 2f;

    private static final float HANABI_INFO_SIZE = 8f;
    private static final float HANABI_INFO_X = 0f;
    private static final float HANABI_INFO_Y = 6f;

    private static final float HANABI_HEALTH_SIZE = 9f;
    private static final float HANABI_HEALTH_TEXT_RIGHT_PAD = 6f;
    private static final float HANABI_HEALTH_TEXT_Y = 33f;
    private static final float HANABI_HEART_SIZE = 12f;
    private static final float HANABI_HEART_GAP = 3f;
    private static final Color HANABI_LABEL_COLOR = new Color(200, 200, 200, 170);
    private static final Color HANABI_VALUE_COLOR = new Color(240, 240, 240, 230);
    private static final Color HANABI_HP_TEXT_COLOR = new Color(240, 240, 240, 230);
    private static final Color HANABI_HEART_NORMAL_COLOR = new Color(200, 200, 200, 200);
    private static final Color HANABI_HEART_HURT_COLOR = new Color(195, 0, 255, 230);

    private final EnumValue<HPmodeEn> hpMode = new EnumValue<>("HP Mode", "血量模式", HPmodeEn.HP);
    private final EnumValue<ImageModeEn> imageMode = new EnumValue<>("Image", "图片模式", ImageModeEn.Anime, () -> style.get() == StyleEn.ThunderHack);

    // 3D ESP Settings
    private final BoolValue espEnabled = new BoolValue("ESP", "ESP", true);

    private enum ESPMode {CaptureMark, Firefly, Circle}

    private final EnumValue<ESPMode> espMode = new EnumValue<>("ESP Mode", "ESP模式", ESPMode.Firefly, espEnabled::get);
    private final ColorValue fireflyColor = new ColorValue("Firefly Color", "萤火虫颜色", new Color(149, 149, 149, 80), () -> espEnabled.get() && espMode.is(ESPMode.Firefly));
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 183, 197), espEnabled::get);
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(255, 133, 161), espEnabled::get);
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Integer> fireflyLength = new NumberValue<>("Length", "长度", 14, 8, 128, 1, () -> espEnabled.get() && espMode.is(ESPMode.Firefly));
    private final NumberValue<Integer> fireflyFactor = new NumberValue<>("Factor", "因子", 8, 1, 10, 1, () -> espEnabled.get() && espMode.is(ESPMode.Firefly));
    private final NumberValue<Double> fireflyShaking = new NumberValue<>("Shaking", "抖动", 1.8, 0.25, 10.0, 0.25, () -> espEnabled.get() && espMode.is(ESPMode.Firefly));
    private final NumberValue<Double> fireflyAmplitude = new NumberValue<>("Amplitude", "振幅", 3.0, 0.0, 10.0, 0.25, () -> espEnabled.get() && espMode.is(ESPMode.Firefly));
    private final NumberValue<Double> circleRadius = new NumberValue<>("Circle Radius", "圆形半径", 0.75, 0.1, 2.0, 0.05, () -> espEnabled.get() && espMode.is(ESPMode.Circle));

    // Health Bar Settings
    private final BoolValue healthBypass = new BoolValue("HealthBypass", "血量绕过", true);
    private final EnumValue<BarModeEn> barMode = new EnumValue<>("BarMode", "血条模式", BarModeEn.Normal);
    private final ColorValue healthColor = new ColorValue("HealthColor", "血条颜色", new Color(0, 255, 0), () -> barMode.get() == BarModeEn.Normal);
    private final BoolValue healthGradient = new BoolValue("HealthGradient", "血条渐变", false, () -> barMode.get() == BarModeEn.Normal);
    private final ColorValue healthColor2 = new ColorValue("HealthColor2", "渐变颜色2", new Color(0, 255, 255), () -> healthGradient.get() && barMode.get() == BarModeEn.Normal);
    private final NumberValue<Double> gradientSpeed = new NumberValue<>("GradientSpeed", "渐变速度", 3.0, 0.1, 10.0, 0.1, () -> healthGradient.get() && barMode.get() == BarModeEn.Normal);
    private final NumberValue<Double> colorLength = new NumberValue<>("ColorLength", "颜色长度", 1.0, 0.1, 5.0, 0.1, () -> (healthGradient.get() && barMode.get() == BarModeEn.Normal) || espEnabled.get());
    private final NumberValue<Double> gradientRepeat = new NumberValue<>("GradientRepeat", "渐变循环次数", 1.0, 0.1, 10.0, 0.1, () -> healthGradient.get() && barMode.get() == BarModeEn.Normal);

    private final NumberValue<Double> shaderHueMin = new NumberValue<>("ShaderHueMin", "着色器色相最小", 0.85, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderHueMax = new NumberValue<>("ShaderHueMax", "着色器色相最大", 0.95, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderSatMin = new NumberValue<>("ShaderSatMin", "着色器饱和度最小", 0.5, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderSatMax = new NumberValue<>("ShaderSatMax", "着色器饱和度最大", 0.55, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderValMin = new NumberValue<>("ShaderValMin", "着色器明度最小", 0.75, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderValMax = new NumberValue<>("ShaderValMax", "着色器明度最大", 1.0, 0.0, 1.0, 0.01, () -> barMode.get() == BarModeEn.Shader);
    private final NumberValue<Double> shaderSpeed = new NumberValue<>("ShaderSpeed", "着色器速度", 1.0, 0.1, 5.0, 0.1, () -> barMode.get() == BarModeEn.Shader);

    // TextHP Settings
    private final NumberValue<Double> textHpSize = new NumberValue<>("TextHpSize", "HP文字大小", 9.0, 4.0, 20.0, 0.5, () -> hpMode.get() == HPmodeEn.TextHP && style.get() == StyleEn.Sakura);
    private final NumberValue<Double> textHpX = new NumberValue<>("TextHpX", "HP文字X偏移", 0.0, -50.0, 50.0, 1.0, () -> hpMode.get() == HPmodeEn.TextHP && style.get() == StyleEn.Sakura);
    private final NumberValue<Double> textHpY = new NumberValue<>("TextHpY", "HP文字Y偏移", 0.0, -50.0, 50.0, 1.0, () -> hpMode.get() == HPmodeEn.TextHP && style.get() == StyleEn.Sakura);
    private final BoolValue textHpGlow = new BoolValue("TextHpGlow", "HP文字发光", false, () -> hpMode.get() == HPmodeEn.TextHP && style.get() == StyleEn.Sakura);
    private final BoolValue textHpGradient = new BoolValue("TextHpGradient", "HP文字渐变", false, () -> hpMode.get() == HPmodeEn.TextHP && style.get() == StyleEn.Sakura);

    // Name Gradient Settings
    private final BoolValue nameGradient = new BoolValue("NameGradient", "名字渐变", false, () -> style.get() == StyleEn.Sakura);
    private final NumberValue<Double> nameGradSpeed = new NumberValue<>("NameGradSpeed", "名字渐变速度", 0.6, 0.0, 5.0, 0.05, () -> nameGradient.get() && style.get() == StyleEn.Sakura);
    private final NumberValue<Integer> nameGradSpread = new NumberValue<>("NameGradSpread", "名字渐变跨度", 15, 1, 400, 1, () -> nameGradient.get() && style.get() == StyleEn.Sakura);
    private final NumberValue<Integer> nameGradBlockDist = new NumberValue<>("NameGradBlockDist", "名字渐变块距", 100, 1, 100, 1, () -> nameGradient.get() && style.get() == StyleEn.Sakura);

    // Glow Settings
    private final BoolValue nameGlow = new BoolValue("NameGlow", "名字发光", true);
    private final BoolValue hpGlow = new BoolValue("HpGlow", "血条发光", true);
    private final NumberValue<Double> glowStrength = new NumberValue<>("GlowStrength", "发光强度", 5.0, 1.0, 20.0, 1.0, () -> nameGlow.get() || hpGlow.get());
    private final BoolValue showArmor = new BoolValue("Armor", "显示装备", true);

    private final ColorValue color = new ColorValue("Color1", "颜色1", new Color(4, 59, 95));
    private final ColorValue color2 = new ColorValue("Color2", "颜色2", new Color(4, 59, 95));

    private final BoolValue absorp = new BoolValue("Absorption", "伤害吸收", true);


    private static final Identifier TARGET_TEX = Identifier.of("sakura", "textures/particles/target.png");
    private static final Identifier THUD_TEX = Identifier.of("sakura", "textures/hud/thud.png");
    private static final Identifier FIREFLY_TEX = Identifier.of("sakura", "textures/particles/firefly.png");

    // Animations
    private final Animation animation = new EaseOutSine(300, 1.0, Direction.BACKWARDS);
    private final Animation damageAnim = new EaseOutSine(150, 1.0, Direction.BACKWARDS);
    private final Animation circleAnim = new EaseOutSine(300, 1.0, Direction.BACKWARDS);

    private float displayHealth = -1;
    private float lastTargetHealth = -1;
    private float delayHealth = -1;
    private final TimerUtil damageTimer = new TimerUtil();

    private LivingEntity target;
    private float rotation = 0f;
    private final Map<Integer, Integer> skinImageCache = new ConcurrentHashMap<>();

    // Particles
    private final ArrayList<Particles> particles = new ArrayList<>();
    private final TimerUtil timer = new TimerUtil();
    private boolean sentParticles = false;
    private float ticks = 0;
    private boolean needsCacheClear = false;
    private KillAura killAuraModule;

    // Sakura Particle Tracking
    private boolean mahiroSentBarParticles = false;
    private boolean mahiroSentAvatarParticles = false;
    private final ArrayList<Particle> mahiroBarParticles = new ArrayList<>();
    private final ArrayList<Particle> mahiroAvatarParticles = new ArrayList<>();
    private final TimerUtil mahiroParticleTimer = new TimerUtil();

    private float hanabiHealthBarWidth = 140f;
    private float hanabiHealthBarWidth2 = 140f;
    private float hanabiHudHeight = 0f;
    private final float[] hanabiGlowSegmentRects = new float[4];
    private final float[] hanabiGlowSegmentRadii = new float[]{0f};

    private float shaderBarX;
    private float shaderBarY;
    private float shaderBarW;
    private float shaderBarH;
    private float shaderBarRadius;

    private final RenderPipeline TARGET_ICON_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_TEX_COLOR_SNIPPET)
            .withLocation("pipeline/sakura_target_icon")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withCull(false)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .build()
    );

    private final Function<Identifier, RenderLayer> TARGET_ICON_LAYER = Util.memoize(texture -> RenderLayer.of(
            "sakura_target_icon",
            RenderSetup.builder(TARGET_ICON_PIPELINE)
                    .texture("Sampler0", texture)
                    .translucent()
                    .layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                    .outputTarget(OutputTarget.MAIN_TARGET)
                    .build()
    ));

    private final RenderPipeline fireflyPipeline = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_TEX_COLOR_SNIPPET).withLocation("pipeline/sakura_firefly").withBlend(BlendFunction.LIGHTNING).withCull(false).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).build());
    private final RenderLayer fireflyLayer = RenderLayer.of("sakura_firefly_layer", RenderSetup.builder(fireflyPipeline).texture("Sampler0", FIREFLY_TEX).translucent().layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.MAIN_TARGET).build());

    @Override
    protected void onEnable() {
        target = null;
        killAuraModule = Sakura.MODULES.getModule(KillAura.class);
        animation.setDirection(Direction.BACKWARDS);
        damageAnim.setDirection(Direction.BACKWARDS);
        circleAnim.setDirection(Direction.BACKWARDS);
        displayHealth = -1;
        lastTargetHealth = -1;
        delayHealth = -1;
        particles.clear();
        mahiroBarParticles.clear();
        mahiroAvatarParticles.clear();
        needsCacheClear = true;
    }

    @Override
    protected void onDisable() {
        needsCacheClear = true;
        particles.clear();
        mahiroBarParticles.clear();
        mahiroAvatarParticles.clear();
    }

    private LivingEntity getCurrentTarget() {
        if (killAuraModule.isEnabled()) {
            Entity target = killAuraModule.getCurrentTarget();
            if (target instanceof LivingEntity living) {
                return living;
            }
        }
        if (mc.currentScreen instanceof HudEditorScreen) {
            return mc.player;
        }
        return null;
    }

    @Override
    public void onRender(DrawContext context) {
        if (needsCacheClear) {
            for (int imageId : skinImageCache.values()) {
                NanoVGHelper.deleteTexture(imageId);
            }
            skinImageCache.clear();
            needsCacheClear = false;
        }

        LivingEntity currentTarget = getCurrentTarget();
        boolean hasTarget = currentTarget != null;

        if (hasTarget) {
            target = currentTarget;
            animation.setDirection(Direction.FORWARDS);
            circleAnim.setDirection(Direction.FORWARDS);
        } else {
            if (style.get() == StyleEn.Hanabi) {
                target = null;
            }
            animation.setDirection(Direction.BACKWARDS);
            circleAnim.setDirection(Direction.BACKWARDS);
        }

        if (style.get() != StyleEn.Hanabi) {
            if (animation.getOutput().floatValue() <= 0.01f && !hasTarget) {
                target = null;
                return;
            }
        }

        float animValue = animation.getOutput().floatValue();

        // Update Health
        float health = 0;
        float maxHealth = 20;
        if (target != null) {
            health = healthBypass.get() ? Managers.HEALTH.getHealth(target) : target.getHealth() + target.getAbsorptionAmount();
            if (absorp.get()) {
                health += target.getAbsorptionAmount();
            }
            maxHealth = target.getMaxHealth() + target.getAbsorptionAmount();
            health = Math.min(maxHealth, health);

            // Damage Animation Logic
            if (lastTargetHealth == -1) lastTargetHealth = health;
            if (health < lastTargetHealth) {
                damageAnim.setDirection(Direction.FORWARDS);
                damageTimer.reset(); // Reset timer on damage
            }
            lastTargetHealth = health;

            // Damage Pulse Logic (Reset if done)
            if (damageAnim.getDirection() == Direction.FORWARDS && damageAnim.isDone()) {
                damageAnim.setDirection(Direction.BACKWARDS);
            }
        }

        // Smooth Health Logic
        if (displayHealth == -1) displayHealth = health;
        if (delayHealth == -1) delayHealth = health;

        // DrawContext doesn't have getTickDelta() directly in some mappings/versions
        // Usually we can get it from RenderTickCounter or just use a fixed step for smoothing
        // Since we are in onRender(DrawContext), let's check if we can get partial ticks from MC
        float tickDelta = mc.getRenderTickCounter().getTickProgress(false);
        displayHealth = MathHelper.lerp(tickDelta * 0.2f, displayHealth, health);

        // Delay Health Logic
        if (MahiroDelay.get()) {
            if (health < delayHealth) {
                // If WaitMode is ON, check timer. If OFF, bypass timer.
                if (!MahiroDelayWait.get() || damageTimer.passedMillise(MahiroDelayTime.get())) {
                    // Slowly decrease delayHealth
                    delayHealth = MathHelper.lerp(tickDelta * MahiroDelaySpeed.get().floatValue() * 0.05f, delayHealth, health);
                }
            } else if (health > delayHealth) {
                // Heal or new target, reset delay bar immediately
                delayHealth = health;
            }
        } else {
            delayHealth = health;
        }

        if (style.get() == StyleEn.Hanabi) {
            updateHanabiState(target, health, maxHealth);
        }

        // Render Background and Main Elements via NanoVG
        final LivingEntity renderTarget = target;
        if (style.get() != StyleEn.Hanabi && renderTarget == null) return;

        final float finalHealth = displayHealth; // Use smooth health
        final float finalMaxHealth = maxHealth;
        final float damageFactor = damageAnim.getOutput().floatValue();

        if (style.get() == StyleEn.Sakura) {
            renderMahiroBackground(animValue);
            renderMahiroGlow(renderTarget, finalHealth, finalMaxHealth, animValue);
        }

        // 1. Render NanoVG elements (Backgrounds, Bars, Text)
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.save();

            // No custom X/Y animation translation anymore as per request

            if (style.get() != StyleEn.Hanabi) {
                float centerX = x + width / 2f;
                float centerY = y + height / 2f;
                NanoVGHelper.translate(vg, centerX, centerY);
                NanoVGHelper.scale(vg, animValue, animValue);
                NanoVGHelper.translate(vg, -centerX, -centerY);
            }

            if (style.get() == StyleEn.Sakura) {
                renderMahiro(vg, renderTarget, finalHealth, finalMaxHealth, damageFactor, animValue);
            } else if (style.get() == StyleEn.Hanabi) {
                renderHanabi(vg, renderTarget, finalHealth, finalMaxHealth);
            } else {
                renderThunderHack(vg, renderTarget, finalHealth, finalMaxHealth, damageFactor, animValue);
            }

            NanoVGHelper.restore();
        });

        //     renderMahiroGlow(renderTarget, finalHealth, finalMaxHealth, animValue);
        // }

        if (barMode.get() == BarModeEn.Shader && shaderBarW > 0 && shaderBarH > 0) {
            HealthBarShader.render(
                    shaderBarX, shaderBarY, shaderBarW, shaderBarH, shaderBarRadius,
                    shaderHueMin.get().floatValue(), shaderHueMax.get().floatValue(),
                    shaderSatMin.get().floatValue(), shaderSatMax.get().floatValue(),
                    shaderValMin.get().floatValue(), shaderValMax.get().floatValue(),
                    shaderSpeed.get().floatValue()
            );
        }

        if (style.get() == StyleEn.Hanabi) {
            if (target != null && hanabiHudHeight > 0.5f && hanabiHealthBarWidth > 0.5f) {
                if (hanabiBarGlow.get() && barMode.get() != BarModeEn.Shader) {
                    int healthColor = getHanabiHealthColor(health, maxHealth).getRGB();
                    Color start = new Color(0, 81, 179, 255);
                    Color endBase = new Color(healthColor, true);
                    Color end = new Color(endBase.getRed(), endBase.getGreen(), endBase.getBlue(), 255);

                    float barX = x;
                    float barY = y + 37f;
                    float barW = hanabiHealthBarWidth;
                    float barH = 3f;

                    hanabiGlowSegmentRects[0] = barX;
                    hanabiGlowSegmentRects[1] = barY;
                    hanabiGlowSegmentRects[2] = barW;
                    hanabiGlowSegmentRects[3] = barH;
                    hanabiGlowSegmentRadii[0] = 0f;
                    ShadowShader.drawStairShadowGradient(
                            barX,
                            barY,
                            barW,
                            barH,
                            hanabiBarGlowRange.get().floatValue(),
                            hanabiBarGlowStrength.get().floatValue(),
                            start,
                            end,
                            hanabiGlowSegmentRects,
                            hanabiGlowSegmentRadii,
                            1
                    );
                }
            }
        }

        // 2. Render Items (Armor, Hands) - Must be done outside NanoVG frame usually to use DrawContext
        if (target instanceof PlayerEntity player && animValue > 0.1f) {
            context.getMatrices().pushMatrix();

            // No custom X/Y animation translation anymore as per request

            if (style.get() != StyleEn.Hanabi) {
                float centerX = x + width / 2f;
                float centerY = y + height / 2f;
                context.getMatrices().translate(centerX, centerY);
                context.getMatrices().scale(animValue, animValue);
                context.getMatrices().translate(-centerX, -centerY);
            }

            if (style.get() != StyleEn.Hanabi) {
                renderThunderHackItems(context, player);
            }

            context.getMatrices().popMatrix();
        }
    }

    private void renderThunderHack(long vg, LivingEntity target, float health, float maxHealth, float damageFactor, float animValue) {
        NanoVGHelper.drawRoundRect(x, y, 70, 50, 6, new Color(0, 0, 0, 139));
        NanoVGHelper.drawRoundRect(x + 50, y, 100, 50, 6, new Color(0, 0, 0, 255));
        this.width = 150;
        this.height = 50;

        // Custom Image Logic
        if (imageMode.get() != ImageModeEn.None) {
            if (imageMode.get() == ImageModeEn.Anime || imageMode.get() == ImageModeEn.Custom) {
                // Draw THUD_TEX in the background rect
                int imageId = getSkinImageId(THUD_TEX);
                if (imageId != -1) {
                    NanoVGHelper.save();
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                        // Pattern fill for the image
                        NanoVG.nvgImagePattern(vg, x + 50, y, 100, 50, 0, imageId, 1f, paint);

                        // Masking with rounded rect
                        NanoVG.nvgBeginPath(vg);
                        NanoVG.nvgRoundedRect(vg, x + 50, y, 100, 50, 6);
                        NanoVG.nvgFillPaint(vg, paint);
                        NanoVG.nvgFill(vg);

                        // Dark overlay if needed
                        NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(0, 0, 0, 80)));
                        NanoVG.nvgFill(vg);
                    }
                    NanoVGHelper.restore();
                }
            }
        }

        // Particles Logic
        updateParticles();

        if (target instanceof PlayerEntity player) {
            // Damage Effect: Shrink scale (1.0 -> 0.85 -> 1.0)
            float damageScale = 1.0f - (damageFactor * 0.15f);
            drawPlayerAvatar(player, x + 2.5f, y + 2.5f, 45, 5, damageScale, damageFactor);
        }

        // NanoVGHelper.drawShadow(x + 55, y + 22, 90, 8, blurRadius.get().floatValue(), new Color(0, 0, 0), 5, 0, 0);

        // Adjust Y positions based on showArmor
        float yOffset = showArmor.get() ? 0 : 5;

        float healthWidth = MathHelper.clamp(90 * (health / maxHealth), 3, 90);

        NanoVGHelper.drawGradientRRect(x + 55, y + 21 + yOffset, 90, 10, 2, new Color(20, 20, 20), new Color(40, 40, 40));

        float thunderBarX = x + 55;
        float thunderBarY = y + 21 + yOffset;
        float thunderBarW = healthWidth;
        float thunderBarH = 10;
        float thunderBarRadius = 2;

        float centerX = x + width / 2f;
        float centerY = y + height / 2f;
        shaderBarX = centerX + (thunderBarX - centerX) * animValue;
        shaderBarY = centerY + (thunderBarY - centerY) * animValue;
        shaderBarW = thunderBarW * animValue;
        shaderBarH = thunderBarH * animValue;
        shaderBarRadius = thunderBarRadius * animValue;

        if (barMode.get() == BarModeEn.Shader) {
            // skip normal health bar fill, shader will render after NanoVG
        } else {
            // Calculate gradient colors
            Color c1 = healthColor.get();
            Color c2 = healthColor.get().darker();

            if (healthGradient.get()) {
                double speed = gradientSpeed.get();
                float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);

                float length = colorLength.get().floatValue();
                float frequency = 1.0f / length;

                float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
                float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);

                c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
                c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
            }

            // Health Bar Glow
            if (hpGlow.get()) {
                float strength = glowStrength.get().floatValue();

                for (float i = 1.0f; i <= strength; i += 1.0f) {
                    float normalizedDist = i / (strength + 2);
                    float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                    float alpha = alphaFactor * 0.25f;

                    int alphaInt = MathHelper.clamp((int) (alpha * 255), 0, 255);
                    if (alphaInt > 0) {
                        if (healthGradient.get()) {
                            Color gc1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt);
                            Color gc2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alphaInt);
                            NanoVGHelper.drawGradientRRect2(x + 55 - i, y + 21 + yOffset - i, healthWidth + i * 2, 10 + i * 2, 2 + i, gc1, gc2);
                        } else {
                            NanoVGHelper.drawRoundRect(x + 55 - i, y + 21 + yOffset - i, healthWidth + i * 2, 10 + i * 2, 2 + i, new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt));
                        }
                    }
                }
            }

            // Draw Health Bar
            if (healthGradient.get()) {
                NanoVGHelper.drawGradientRRect2(x + 55, y + 21 + yOffset, healthWidth, 10, 2, c1, c2);
            } else {
                NanoVGHelper.drawGradientRRect(x + 55, y + 21 + yOffset, healthWidth, 10, 2, c1, c2);
            }
        }

        String hpText = hpMode.get() == HPmodeEn.Percentage ? String.format("%.0f%%", (health / maxHealth) * 100) : String.format("%.1f", health);
        NanoVGHelper.drawCenteredString(hpText, x + 102, y + 24f + 3 + yOffset, FontLoader.bold(), 10, Color.WHITE);

        // Name Glow
        if (nameGlow.get()) {
            float strength = glowStrength.get().floatValue();
            NanoVGHelper.drawGlowingString(target.getName().getString(), x + 55, y + 14 + yOffset, FontLoader.bold(), 12, Color.WHITE, strength, 2);
        } else {
            NanoVGHelper.drawString(target.getName().getString(), x + 55, y + 14 + yOffset, FontLoader.bold(), 12, -1, Color.WHITE);
        }
    }

    private void renderThunderHackItems(DrawContext context, PlayerEntity target) {
        if (!showArmor.get()) {
            return;
        }

        // Armor
        ItemStack[] items = new ItemStack[]{target.getMainHandStack(), target.getEquippedStack(EquipmentSlot.HEAD), target.getEquippedStack(EquipmentSlot.CHEST), target.getEquippedStack(EquipmentSlot.LEGS), target.getEquippedStack(EquipmentSlot.FEET), target.getOffHandStack()};

        float xItemOffset = x + 60;
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) continue;
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(xItemOffset, y + 35);
            context.getMatrices().scale(0.75f, 0.75f);
            context.drawItem(itemStack, 0, 0);
            context.getMatrices().popMatrix();
            xItemOffset += 14;
        }
    }

    private void updateParticles() {
        if (timer.passedMillise(1000.0 / 60.0)) {
            ticks += 0.1f;
            for (int i = 0; i < particles.size(); i++) {
                Particles p = particles.get(i);
                p.updatePosition();
                if (p.opacity < 1) {
                    particles.remove(i);
                    i--;
                }
            }
            timer.reset();
        }

        if (target != null && target.hurtTime == 9 && !sentParticles) {
            for (int i = 0; i <= 6; i++) {
                Particles p = new Particles();
                Color c1 = color.get();
                Color c2 = color2.get();
                float mixFactor = (float) ((Math.sin(ticks + x * 0.4f + i) + 1) * 0.5f);
                Color c = ColorUtil.interpolateColor(c1, c2, mixFactor);

                p.init(x + 75, y + 25, ThreadLocalRandom.current().nextFloat() * 6 - 3, ThreadLocalRandom.current().nextFloat() * 6 - 3, 20, c);
                particles.add(p);
            }
            sentParticles = true;
        }
        if (target != null && target.hurtTime == 8) sentParticles = false;

        for (Particles p : particles) {
            if (p.opacity > 4) {
                p.render();
            }
        }
    }

    private void updateMahiroParticles(long vg) {
        if (!MahiroParticles.get()) return;

        float globalScale = MahiroScale.get().floatValue();
        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();
        float barH = MahiroBarHeight.get().floatValue();
        float padding = 6f;
        float avatarSize = baseH - padding * 2;
        float heightIncrease = 0;

        if (MahiroAvatarPos.get() == AvatarPosEn.OnBar) {
            heightIncrease = MahiroOnBarHeight.get().floatValue();
        }

        float totalH = baseH + heightIncrease;
        float contentX = x + padding + avatarSize + padding;
        float contentW = baseW - (padding + avatarSize + padding + padding);

        if (MahiroAvatarPos.get() == AvatarPosEn.OnBar) {
            contentX = x + padding;
            contentW = baseW - (padding + padding);
        }

        float barY = y + totalH - padding - barH;
        float barX = contentX;
        float avatarX = x + padding;
        float avatarY = y + padding;

        if (mahiroParticleTimer.passedMillise(1000.0 / 60.0)) {
            mahiroBarParticles.removeIf(p -> !p.isAlive());
            mahiroAvatarParticles.removeIf(p -> !p.isAlive());
            mahiroParticleTimer.reset();
        }

        for (Particle p : mahiroBarParticles) {
            p.update();
        }
        for (Particle p : mahiroAvatarParticles) {
            p.update();
        }

        if (target == null) return;

        int amount = MahiroParticleAmount.get();
        float range = MahiroParticleRange.get().floatValue();
        MahiroParticleModeEn mode = MahiroParticleMode.get();

        if (MahiroBarParticles.get() && target.hurtTime == 9 && !mahiroSentBarParticles) {
            float barW = contentW * MathHelper.clamp((healthBypass.get() ? Managers.HEALTH.getHealth(target) : target.getHealth() + target.getAbsorptionAmount()) / target.getMaxHealth(), 0f, 1f);
            for (int i = 0; i < amount; i++) {
                Color c1 = healthColor.get();
                Color c2 = healthColor2.get();
                float mixFactor = (float) ((Math.sin(System.currentTimeMillis() * 0.01 + i) + 1) * 0.5f);
                Color c;
                if (healthGradient.get()) {
                    c = ColorUtil.interpolateColor(c1, c2, mixFactor);
                } else {
                    c = c1;
                }
                float px = barX + ThreadLocalRandom.current().nextFloat() * barW;
                float py = barY + ThreadLocalRandom.current().nextFloat() * barH;
                Particle p = new Particle(px, py, c, mode, range);
                mahiroBarParticles.add(p);
            }
            mahiroSentBarParticles = true;
        }
        if (target.hurtTime == 8) mahiroSentBarParticles = false;

        boolean isAttacking = killAuraModule != null && killAuraModule.isEnabled() && target.hurtTime > 0;
        if (MahiroAvatarParticles.get() && isAttacking && !mahiroSentAvatarParticles) {
            for (int i = 0; i < amount; i++) {
                Color c1 = healthColor.get();
                Color c2 = healthColor2.get();
                float mixFactor = (float) ((Math.sin(System.currentTimeMillis() * 0.01 + i) + 1) * 0.5f);
                Color c;
                if (healthGradient.get()) {
                    c = ColorUtil.interpolateColor(c1, c2, mixFactor);
                } else {
                    c = c1;
                }
                float px = avatarX + ThreadLocalRandom.current().nextFloat() * avatarSize;
                float py = avatarY + ThreadLocalRandom.current().nextFloat() * avatarSize;
                Particle p = new Particle(px, py, c, mode, range);
                mahiroAvatarParticles.add(p);
            }
            mahiroSentAvatarParticles = true;
        }
        if (target.hurtTime == 8) mahiroSentAvatarParticles = false;

        for (Particle p : mahiroBarParticles) {
            p.render(vg);
        }
        for (Particle p : mahiroAvatarParticles) {
            p.render(vg);
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!espEnabled.get() || nullCheck()) return;
        LivingEntity target = getCurrentTarget();
        if (target == null) return;
        switch (espMode.get()) {
            case CaptureMark -> renderEsp(target, event.getMatrices(), event.getTickDelta());
            case Firefly -> firefly(target, event.getMatrices(), event.getTickDelta());
            case Circle -> circle(target, event.getMatrices(), event.getTickDelta());
        }
    }

    private void renderEsp(LivingEntity target, MatrixStack matrices, float tickDelta) {
        rotation -= rotationSpeed.get().floatValue();
        if (rotation <= -360f) rotation += 360f;

        Vec3d cam = mc.getEntityRenderDispatcher().camera.getCameraPos();

        double ex = MathHelper.lerp(tickDelta, target.lastX, target.getX()) - cam.x;
        double ey = MathHelper.lerp(tickDelta, target.lastY, target.getY()) - cam.y;
        double ez = MathHelper.lerp(tickDelta, target.lastZ, target.getZ()) - cam.z;

        float entityHeight = target.getHeight();
        float size = espSize.get().floatValue() * 0.5f;

        matrices.push();
        matrices.translate(ex, ey + entityHeight * 0.5, ez);

        Camera camera = mc.gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

        drawTextureQuad(matrices, size);

        matrices.pop();
    }

    private void drawTextureQuad(MatrixStack matrices, float size) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        Color c1 = getColorForProgress(0);
        Color c2 = getColorForProgress(0.25f);
        Color c3 = getColorForProgress(0.5f);
        Color c4 = getColorForProgress(0.75f);

        buffer.vertex(matrix, -size, -size, 0).texture(0, 0).color(c1.getRGB());
        buffer.vertex(matrix, -size, size, 0).texture(0, 1).color(c2.getRGB());
        buffer.vertex(matrix, size, size, 0).texture(1, 1).color(c3.getRGB());
        buffer.vertex(matrix, size, -size, 0).texture(1, 0).color(c4.getRGB());

        TARGET_ICON_LAYER.apply(TARGET_TEX).draw(buffer.end());
    }

    private Color getColorForProgress(float progress) {
        float time = (float) ((System.currentTimeMillis() % 2000000) * waveSpeed.get() / 1000.0);
        float length = colorLength.get().floatValue();
        float frequency = 1.0f / length;
        float spatialPhase = progress * (float) Math.PI * 2 * frequency;
        float wave = (float) ((Math.sin(time + spatialPhase) + 1.0) / 2.0);
        return ColorUtil.interpolateColor(espColor1.get(), espColor2.get(), wave);
    }

    private void firefly(LivingEntity target, MatrixStack matrices, float tickDelta) {
        Camera camera = mc.gameRenderer.getCamera();

        double tPosX = MathHelper.lerp(tickDelta, target.lastX, target.getX()) - camera.getCameraPos().x;
        double tPosY = MathHelper.lerp(tickDelta, target.lastY, target.getY()) - camera.getCameraPos().y;
        double tPosZ = MathHelper.lerp(tickDelta, target.lastZ, target.getZ()) - camera.getCameraPos().z;
        float iAge = (float) (target.age - 1) + tickDelta;

        int espLength = fireflyLength.get();
        int factor = fireflyFactor.get();
        float shaking = fireflyShaking.get().floatValue();
        float amplitude = fireflyAmplitude.get().floatValue();

        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i <= espLength; i++) {
                double radians = Math.toRadians((((float) i / 1.5f + iAge) * factor + (j * 120)) % (factor * 360));
                double sinQuad = Math.sin(Math.toRadians(iAge * 2.5f + i * (j + 1)) * amplitude) / shaking;

                float offset = (float) i / (float) espLength;

                matrices.push();
                matrices.translate(tPosX + Math.cos(radians) * target.getWidth(), tPosY + target.getHeight() * 0.5 + sinQuad, tPosZ + Math.sin(radians) * target.getWidth());
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));

                Matrix4f matrix = matrices.peek().getPositionMatrix();
                float scale = Math.max(0.24f * (offset), 0.2f);
                int color = fireflyColor.get().getRGB();

                buffer.vertex(matrix, -scale, scale, 0).texture(0f, 1f).color(color);
                buffer.vertex(matrix, scale, scale, 0).texture(1f, 1f).color(color);
                buffer.vertex(matrix, scale, -scale, 0).texture(1f, 0f).color(color);
                buffer.vertex(matrix, -scale, -scale, 0).texture(0f, 0f).color(color);

                matrices.pop();
            }
        }

        fireflyLayer.draw(buffer.end());
    }

    private void circle(LivingEntity target, MatrixStack matrices, float tickDelta) {
        float radius = circleRadius.get().floatValue();
        float alpha = circleAnim.getOutput().floatValue();

        if (alpha <= 0.01f) return;

        Color color1 = healthColor.get();
        Color color2 = healthColor2.get();

        float tick = (float) (System.currentTimeMillis() % 1000000) * 0.004f;

        double x = MathHelper.lerp(tickDelta, target.lastX, target.getX()) - mc.getEntityRenderDispatcher().camera.getCameraPos().x;
        double y = MathHelper.lerp(tickDelta, target.lastY, target.getY()) - mc.getEntityRenderDispatcher().camera.getCameraPos().y + Math.sin(tick) + 1;
        double z = MathHelper.lerp(tickDelta, target.lastZ, target.getZ()) - mc.getEntityRenderDispatcher().camera.getCameraPos().z;

        matrices.push();
        matrices.translate(x, y, z);

        BufferBuilder triBuffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        for (float i = 0; i <= (Math.PI * 2); i += (Math.PI * 2) / 64.F) {
            float vecX = (float) (radius * Math.cos(i));
            float vecZ = (float) (radius * Math.sin(i));

            float t = ((i + tick) % ((float) Math.PI * 2)) / ((float) Math.PI * 2);
            int r = (int) (color1.getRed() + (color2.getRed() - color1.getRed()) * t);
            int g = (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * t);
            int b = (int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * t);

            triBuffer.vertex(matrix, vecX, (float) (-Math.sin(tick + 1) / 2.7f), vecZ).color(r / 255.0f, g / 255.0f, b / 255.0f, 0.0f);
            triBuffer.vertex(matrix, vecX, 0, vecZ).color(r / 255.0f, g / 255.0f, b / 255.0f, 0.52f * alpha);
        }

        SakuraPipelines.TRIANGLE_STRIP.draw(triBuffer.end());

        BufferBuilder lineBuffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);
        MatrixStack.Entry entry = matrices.peek();

        for (int i = 0; i <= 180; i++) {
            float radAngle = (float) (i * Math.PI * 2 / 90);
            float t = ((radAngle + tick) % ((float) Math.PI * 2)) / ((float) Math.PI * 2);
            int r = (int) (color1.getRed() + (color2.getRed() - color1.getRed()) * t);
            int g = (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * t);
            int b = (int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * t);
            int lineColor = ((int) (0.5f * alpha * 255) & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);

            float lineX = (float) (-Math.sin(radAngle) * radius);
            float lineZ = (float) (Math.cos(radAngle) * radius);
            float nextAngle = (float) ((i + 1) * Math.PI * 2 / 90);
            float nextX = (float) (-Math.sin(nextAngle) * radius);
            float nextZ = (float) (Math.cos(nextAngle) * radius);

            float dx = nextX - lineX;
            float dz = nextZ - lineZ;
            float len = MathHelper.sqrt(dx * dx + dz * dz);
            if (len < 1.0E-6f) continue;
            float nx = dx / len;
            float nz = dz / len;

            lineBuffer.vertex(entry, lineX, 0, lineZ).color(lineColor).normal(entry, nx, 0.0f, nz).lineWidth(1.5f);
            lineBuffer.vertex(entry, nextX, 0, nextZ).color(lineColor).normal(entry, nx, 0.0f, nz).lineWidth(1.5f);
        }
        SakuraPipelines.LINES.draw(lineBuffer.end());

        matrices.pop();
    }

    private void drawPlayerAvatar(PlayerEntity player, float x, float y, float size, float radius, float scale, float damageFactor) {
        if (!(player instanceof AbstractClientPlayerEntity clientPlayer)) {
            NanoVGHelper.drawRoundRect(x, y, size, size, radius, new Color(80, 80, 80, 200));
            return;
        }
        Identifier skinTexture = clientPlayer.getSkin().body().texturePath();
        int imageId = getSkinImageId(skinTexture);
        if (imageId != -1) {
            long vg = NanoVGRenderer.INSTANCE.getContext();
            NanoVGHelper.save();

            // Apply scale centered on avatar
            float cx = x + size / 2f;
            float cy = y + size / 2f;
            NanoVGHelper.translate(vg, cx, cy);
            NanoVGHelper.scale(vg, scale, scale);
            NanoVGHelper.translate(vg, -cx, -cy);

            try (MemoryStack stack = MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);

                // Calculate pattern to focus on the face (8, 8) with size (8, 8) in a 64x64 texture
                float faceScale = 8.0f;
                float ox = x - size;
                float oy = y - size;
                float ex = size * faceScale;
                float ey = size * faceScale;

                NanoVG.nvgImagePattern(vg, ox, oy, ex, ey, 0, imageId, 1f, paint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRoundedRect(vg, x, y, size, size, radius);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);

                // Red Damage Overlay
                if (damageFactor > 0.01f) {
                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgRoundedRect(vg, x, y, size, size, radius);
                    // Use damageFactor for alpha (max 0.6 to not fully obscure)
                    int alpha = (int) (damageFactor * 150);
                    NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 0, 0, alpha)));
                    NanoVG.nvgFill(vg);
                }
            }
            NanoVGHelper.restore();
        } else {
            NanoVGHelper.drawRoundRect(x, y, size, size, radius, new Color(80, 80, 80, 200));
        }
    }

    private int getSkinImageId(Identifier skinTexture) {
        if (!(mc.getTextureManager().getTexture(skinTexture).getGlTexture() instanceof GlTexture glTexture)) return -1;

        int glId = glTexture.getGlId();

        Integer cached = skinImageCache.get(glId);
        if (cached != null) {
            return cached;
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        int imageId = NanoVGHelper.createImageFromHandle(glId, 64, 64);
        if (imageId != -1) {
            skinImageCache.put(glId, imageId);
        }
        return imageId;
    }

    private void renderMahiroBackground(float animValue) {
        float globalScale = MahiroScale.get().floatValue();

        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();

        AvatarPosEn avatarPos = MahiroAvatarPos.get();
        float heightIncrease = 0;

        if (avatarPos == AvatarPosEn.OnBar) {
            heightIncrease = MahiroOnBarHeight.get().floatValue();
        }

        this.width = baseW * globalScale;
        this.height = (baseH + heightIncrease) * globalScale;

        float cx = x + width / 2f;
        float cy = y + height / 2f;

        float w = width * animValue;
        float h = height * animValue;
        float rx = cx - w / 2f;
        float ry = cy - h / 2f;

        float r = MahiroRadius.get().floatValue() * globalScale * animValue;
        float blur = MahiroBlurRadius.get().floatValue() * globalScale;

        if (MahiroShadow.get() && w > 0.0f && h > 0.0f && animValue > 0.01f) {
            float[] rects = new float[]{rx, ry, w, h};
            float[] radii = new float[]{r};

            if (MahiroShadowMode.is(MahiroShadowModeEn.Gradient)) {
                Color c1 = healthColor.get();
                Color c2 = healthGradient.get() ? healthColor2.get() : healthColor.get().darker();

                double speed = gradientSpeed.get();
                double t = (System.currentTimeMillis() / 1000.0) * speed;
                double len = Math.max(0.001, colorLength.get());
                float t1 = (float) ((Math.sin(t) + 1) / 2);
                float t2 = (float) ((Math.sin(t + len) + 1) / 2);

                Color start = ColorUtil.interpolateColor(c1, c2, t1);
                Color end = ColorUtil.interpolateColor(c1, c2, t2);
                start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);

                ShadowShader.drawStairShadowGradient(rx, ry, w, h, MahiroShadowRange.get().floatValue() * globalScale, MahiroShadowStrength.get().floatValue(), start, end, rects, radii, 1);
            } else {
                ShadowShader.drawStairShadow(rx, ry, w, h, MahiroShadowRange.get().floatValue() * globalScale, MahiroShadowStrength.get().floatValue(), new Color(0, 0, 0), rects, radii, 1);
            }
        }

        BlurShader.drawRoundedBlur(rx, ry, w, h, r, blur);
    }

    private void renderMahiro(long vg, LivingEntity target, float health, float maxHealth, float damageFactor, float animValue) {
        float globalScale = MahiroScale.get().floatValue();

        NanoVGHelper.save();
        NanoVGHelper.translate(vg, x, y);
        NanoVGHelper.scale(vg, globalScale, globalScale);
        NanoVGHelper.translate(vg, -x, -y);

        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();
        float radius = MahiroRadius.get().floatValue();
        float barRadius = MahiroBarShape.get() == MahiroBarShapeEn.Style1 ? MahiroBarRadius1.get().floatValue() : MahiroBarRadius2.get().floatValue();
        float nameSize = MahiroNameSize.get().floatValue();
        AvatarPosEn avatarPos = MahiroAvatarPos.get();

        float padding = 6f;
        float avatarSize = baseH - padding * 2;
        float avatarX = x + padding;
        float avatarY = y + padding; // Avatar stays at top

        float contentYOffset = 0;
        float heightIncrease = 0;

        if (avatarPos == AvatarPosEn.OnBar) {
            float offset = MahiroOnBarHeight.get().floatValue();
            contentYOffset = offset; // Shift text/bar down
            heightIncrease = offset; // Increase BG height
        }

        float totalH = baseH + heightIncrease;

        // Background Rect
        NanoVGHelper.drawRoundRect(x, y, baseW, totalH, radius, MahiroBgColor.get());

        float contentX = x + padding + avatarSize + padding;
        float contentW = baseW - (padding + avatarSize + padding + padding);

        if (avatarPos == AvatarPosEn.OnBar) {
            contentX = x + padding;
            contentW = baseW - (padding + padding);
        }

        float barH = MahiroBarHeight.get().floatValue();

        // Name
        float nameXOffset = MahiroNameX.get().floatValue();
        float nameYOffset = MahiroNameY.get().floatValue();

        float nameY = y + padding + (nameSize / 2) + 2 + contentYOffset + nameYOffset;

        float textX = contentX + nameXOffset;
        if (avatarPos == AvatarPosEn.OnBar) {
            textX = x + padding + 2 + nameXOffset;
        }

        String targetName = target.getName().getString();

        if (nameGradient.get()) {
            double offsetDeg;
            int colorStepDeg = nameGradSpread.get();
            int blockDist = nameGradBlockDist.get();
            float textW = NanoVGHelper.getTextWidth(targetName, FontLoader.bold(), nameSize);
            float glowR = nameGlow.get() ? glowStrength.get().floatValue() : 0;
            int glowI = nameGlow.get() ? 2 : 0;

            if (barMode.get() == BarModeEn.Shader) {
                offsetDeg = (System.currentTimeMillis() / 20.0) * shaderSpeed.get();
                renderStringLineGradientWithGlow(vg, textX, nameY, FontLoader.bold(), nameSize, targetName, offsetDeg, colorStepDeg, textW, blockDist, this::getShaderStepColor, glowR, glowI);
            } else {
                offsetDeg = (System.currentTimeMillis() / 20.0) * nameGradSpeed.get();
                renderStringLineGradientWithGlow(vg, textX, nameY, FontLoader.bold(), nameSize, targetName, offsetDeg, colorStepDeg, textW, blockDist, this::getGradientStepColor, glowR, glowI);
            }
        } else {
            if (nameGlow.get()) {
                NanoVGHelper.drawGlowingString(targetName, textX, nameY, FontLoader.bold(), nameSize, Color.WHITE, glowStrength.get().floatValue(), 2);
            } else {
                NanoVGHelper.drawString(targetName, textX, nameY, FontLoader.bold(), nameSize, Color.WHITE);
            }
        }

        // HP Text
        if (hpMode.get() == HPmodeEn.TextHP) {
            String hpStr = "HP: " + String.format("%.1f", health);
            float hpTextSize = textHpSize.get().floatValue();
            float hpTextX = textX + textHpX.get().floatValue();
            float fontH = NanoVGHelper.getFontHeight(FontLoader.comfortaa(), nameSize);
            float hpTextY = nameY + fontH + 2 + textHpY.get().floatValue();
            if (textHpGradient.get()) {
                double offsetDeg;
                int colorStepDeg = nameGradSpread.get();
                int blockDist = nameGradBlockDist.get();
                float textW = NanoVGHelper.getTextWidth(hpStr, FontLoader.comfortaa(), hpTextSize);
                float glowR = textHpGlow.get() ? glowStrength.get().floatValue() : 0;
                int glowI = textHpGlow.get() ? 2 : 0;

                if (barMode.get() == BarModeEn.Shader) {
                    offsetDeg = (System.currentTimeMillis() / 20.0) * shaderSpeed.get();
                    renderStringLineGradientWithGlow(vg, hpTextX, hpTextY, FontLoader.comfortaa(), hpTextSize, hpStr, offsetDeg, colorStepDeg, textW, blockDist, this::getShaderStepColor, glowR, glowI);
                } else {
                    offsetDeg = (System.currentTimeMillis() / 20.0) * nameGradSpeed.get();
                    renderStringLineGradientWithGlow(vg, hpTextX, hpTextY, FontLoader.comfortaa(), hpTextSize, hpStr, offsetDeg, colorStepDeg, textW, blockDist, this::getGradientStepColor, glowR, glowI);
                }
            } else if (textHpGlow.get()) {
                NanoVGHelper.drawGlowingString(hpStr, hpTextX, hpTextY, FontLoader.comfortaa(), hpTextSize, Color.WHITE, glowStrength.get().floatValue(), 2);
            } else {
                NanoVGHelper.drawString(hpStr, hpTextX, hpTextY, FontLoader.comfortaa(), hpTextSize, Color.WHITE);
            }
        } else {
            String hpText = hpMode.get() == HPmodeEn.HP ? String.format("%.1f", health) : String.format("%.0f%%", (health / maxHealth) * 100);
            float hpW = NanoVGHelper.getTextWidth(hpText, FontLoader.bold(), nameSize);
            NanoVGHelper.drawString(hpText, x + baseW - padding - hpW, nameY, FontLoader.bold(), nameSize, Color.WHITE);
        }

        // Health Bar
        // barY calculation: start from bottom of total height
        float barY = y + totalH - padding - barH;

        float healthPct = MathHelper.clamp(health / maxHealth, 0f, 1f);
        float delayPct = MathHelper.clamp(delayHealth / maxHealth, 0f, 1f);

        float barW = contentW * healthPct;
        float delayBarW = contentW * delayPct;

        // Bar Bg
        NanoVGHelper.drawRoundRect(contentX, barY, contentW, barH, barRadius, MahiroBarBgColor.get());

        // Delay Bar
        if (MahiroDelay.get() && delayHealth > health) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGColor col = org.lwjgl.nanovg.NVGColor.malloc(stack);
                Color c = MahiroDelayColor.get();
                NanoVG.nvgRGBA((byte) c.getRed(), (byte) c.getGreen(), (byte) c.getBlue(), (byte) c.getAlpha(), col);

                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRoundedRect(vg, contentX, barY, delayBarW, barH, barRadius);
                NanoVG.nvgFillColor(vg, col);
                NanoVG.nvgFill(vg);
            }
        }

        // Bar Gradient Logic
        Color c1 = healthColor.get();
        Color c2 = healthColor.get().darker();
        if (healthGradient.get()) {
            double speed = gradientSpeed.get();
            float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);
            float length = colorLength.get().floatValue();
            float frequency = 1.0f / length;
            float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
            float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);
            c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
            c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
        }

        float sx = x + (contentX - x) * globalScale;
        float sy = y + (barY - y) * globalScale;
        float sw = barW * globalScale;
        float sh = barH * globalScale;
        float sr = barRadius * globalScale;

        float cx = x + this.width / 2f;
        float cy = y + this.height / 2f;
        shaderBarX = cx + (sx - cx) * animValue;
        shaderBarY = cy + (sy - cy) * animValue;
        shaderBarW = sw * animValue;
        shaderBarH = sh * animValue;
        shaderBarRadius = sr * animValue;

        if (barMode.get() == BarModeEn.Shader) {
            // skip normal health bar fill, shader will render after NanoVG
        } else {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                org.lwjgl.nanovg.NVGColor col1 = org.lwjgl.nanovg.NVGColor.malloc(stack);
                org.lwjgl.nanovg.NVGColor col2 = org.lwjgl.nanovg.NVGColor.malloc(stack);

                NanoVG.nvgRGBA((byte) c1.getRed(), (byte) c1.getGreen(), (byte) c1.getBlue(), (byte) c1.getAlpha(), col1);
                NanoVG.nvgRGBA((byte) c2.getRed(), (byte) c2.getGreen(), (byte) c2.getBlue(), (byte) c2.getAlpha(), col2);

                if (healthGradient.get()) {
                    float repeatFactor = gradientRepeat.get().floatValue();
                    float gradEndX = contentX + contentW * repeatFactor;
                    NanoVG.nvgLinearGradient(vg, contentX, barY, gradEndX, barY, col1, col2, paint);
                } else {
                    NanoVG.nvgLinearGradient(vg, contentX, barY, contentX, barY + barH, col1, col2, paint);
                }

                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRoundedRect(vg, contentX, barY, barW, barH, barRadius);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);
            }
        }

        if (target instanceof PlayerEntity player) {
            float damageScale = 1.0f - (damageFactor * 0.15f);
            drawPlayerAvatar(player, avatarX, avatarY, avatarSize, 6f, damageScale, damageFactor);
        } else {
            NanoVGHelper.drawRoundRect(avatarX, avatarY, avatarSize, avatarSize, 6f, new Color(80, 80, 80));
        }

        updateMahiroParticles(vg);
        NanoVGHelper.restore();
    }

    private Color interpolateGradientColor(Color c1, Color c2, float t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(r, g, b, a);
    }

    private Color getGradientStepColor(double offsetDeg) {
        double rad = Math.toRadians(offsetDeg);
        float t1 = (float) ((Math.sin(rad) + 1.0) / 2.0);
        float t2 = (float) ((Math.sin(rad * 2.0 + 1.5) + 1.0) / 2.0);
        float mix = t1 * 0.65f + t2 * 0.35f;
        mix = Math.max(0f, Math.min(1f, mix));
        Color c1 = healthGradient.get() ? healthColor2.get() : healthColor.get().darker();
        Color c2 = healthColor.get();
        return interpolateGradientColor(c1, c2, mix);
    }

    private int[] hsvToRgb(float h, float s, float v) {
        float c = v * s;
        float x = c * (1 - Math.abs((h * 6) % 2 - 1));
        float m = v - c;
        float r1, g1, b1;
        int hi = (int) (h * 6) % 6;
        switch (hi) {
            case 0:
                r1 = c;
                g1 = x;
                b1 = 0;
                break;
            case 1:
                r1 = x;
                g1 = c;
                b1 = 0;
                break;
            case 2:
                r1 = 0;
                g1 = c;
                b1 = x;
                break;
            case 3:
                r1 = 0;
                g1 = x;
                b1 = c;
                break;
            case 4:
                r1 = x;
                g1 = 0;
                b1 = c;
                break;
            default:
                r1 = c;
                g1 = 0;
                b1 = x;
                break;
        }
        return new int[]{
                Math.round((r1 + m) * 255),
                Math.round((g1 + m) * 255),
                Math.round((b1 + m) * 255)
        };
    }

    private float shaderRange(float val, float mi, float ma) {
        return val * (ma - mi) + mi;
    }

    private Color getShaderStepColor(double offsetDeg) {
        double factor = (Math.sin(Math.toRadians(offsetDeg)) + 1.0) / 2.0;
        float f = (float) factor;
        float hue = shaderRange(f, shaderHueMin.get().floatValue(), shaderHueMax.get().floatValue());
        float sat = shaderRange(f, shaderSatMin.get().floatValue(), shaderSatMax.get().floatValue());
        float val = shaderRange(f, shaderValMin.get().floatValue(), shaderValMax.get().floatValue());
        hue = hue % 1.0f;
        if (hue < 0) hue += 1.0f;
        int[] rgb = hsvToRgb(hue, sat, val);
        return new Color(rgb[0], rgb[1], rgb[2], 255);
    }

    private void renderStringLineGradient(long vg, float x, float baseY, int font, float size, String text, double offsetDeg, int colorStepDeg, float textW, int blockDist) {
        renderStringLineGradient(vg, x, baseY, font, size, text, offsetDeg, colorStepDeg, textW, blockDist, this::getGradientStepColor, 0, 0);
    }

    private void renderShaderStringLineGradient(long vg, float x, float baseY, int font, float size, String text, double offsetDeg, int colorStepDeg, float textW, int blockDist) {
        renderStringLineGradient(vg, x, baseY, font, size, text, offsetDeg, colorStepDeg, textW, blockDist, this::getShaderStepColor, 0, 0);
    }

    private void renderStringLineGradientWithGlow(long vg, float x, float baseY, int font, float size, String text, double offsetDeg, int colorStepDeg, float textW, int blockDist, StepColorProvider colorProvider, float glowRadius, int glowIntensity) {
        renderStringLineGradient(vg, x, baseY, font, size, text, offsetDeg, colorStepDeg, textW, blockDist, colorProvider, glowRadius, glowIntensity);
    }

    @FunctionalInterface
    private interface StepColorProvider {
        Color getColor(double offsetDeg);
    }

    private void renderStringLineGradient(long vg, float x, float baseY, int font, float size, String text, double offsetDeg, int colorStepDeg, float textW, int blockDist, StepColorProvider colorProvider, float glowRadius, int glowIntensity) {
        if (text == null || text.isEmpty()) return;

        float totalW = Math.max(1.0f, textW);
        float blockW = (float) blockDist;
        int segments = (int) Math.max(16, Math.min(260, Math.ceil(totalW / Math.max(1.0f, blockW))));
        float segW = totalW / segments;
        float fontH = NanoVGHelper.getFontHeight(font, size);
        float scissorY = baseY - fontH - 2.0f;
        float scissorH = fontH + 4.0f;
        float overlap = 0.75f;

        NanoVG.nvgFontFaceId(vg, font);
        NanoVG.nvgFontSize(vg, size);
        NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE);

        if (glowRadius > 0 && glowIntensity > 0) {
            NanoVG.nvgFontBlur(vg, glowRadius);

            Color leftColor = colorProvider.getColor(offsetDeg);
            Color rightColor = colorProvider.getColor(offsetDeg + (double) segments * colorStepDeg);
            leftColor = new Color(leftColor.getRed(), leftColor.getGreen(), leftColor.getBlue(), 200);
            rightColor = new Color(rightColor.getRed(), rightColor.getGreen(), rightColor.getBlue(), 200);

            try (org.lwjgl.system.MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                NanoVG.nvgLinearGradient(vg, x, baseY, x + totalW, baseY, NanoVGHelper.nvgColor(leftColor), NanoVGHelper.nvgColor(rightColor), paint);

                for (int g = 0; g < glowIntensity; g++) {
                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgText(vg, x, baseY, text);
                }
            }

            NanoVG.nvgFontBlur(vg, 0);
        }

        for (int i = 0; i < segments; i++) {
            float segX = x + i * segW;
            Color col = colorProvider.getColor(offsetDeg + (double) i * colorStepDeg);
            col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 255);

            NanoVG.nvgSave(vg);
            NanoVG.nvgScissor(vg, segX - overlap, scissorY, segW + overlap * 2.0f, scissorH);
            NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(col));
            NanoVG.nvgText(vg, x, baseY, text);
            NanoVG.nvgRestore(vg);
        }
    }

    private void renderMahiroGlow(LivingEntity target, float health, float maxHealth, float animValue) {
        if (!hpGlow.get()) return;
        if (barMode.get() == BarModeEn.Shader) return;

        float globalScale = MahiroScale.get().floatValue();
        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();
        float barRadius = MahiroBarShape.get() == MahiroBarShapeEn.Style1 ? MahiroBarRadius1.get().floatValue() : MahiroBarRadius2.get().floatValue();
        float barH = MahiroBarHeight.get().floatValue();

        float padding = 6f;
        float avatarSize = baseH - padding * 2;

        float heightIncrease = 0;

        if (MahiroAvatarPos.get() == AvatarPosEn.OnBar) {
            heightIncrease = MahiroOnBarHeight.get().floatValue();
        }

        float totalH = baseH + heightIncrease;

        float contentX = x + padding + avatarSize + padding;
        float contentW = baseW - (padding + avatarSize + padding + padding);

        if (MahiroAvatarPos.get() == AvatarPosEn.OnBar) {
            contentX = x + padding;
            contentW = baseW - (padding + padding);
        }

        float barY = y + totalH - padding - barH;
        float healthPct = MathHelper.clamp(health / maxHealth, 0f, 1f);
        float barW = contentW * healthPct;

        float sx = x + (contentX - x) * globalScale;
        float sy = y + (barY - y + 0.5f) * globalScale;
        float sw = barW * globalScale;
        float sh = barH * globalScale;

        // Apply Glow Offset (Shrink/Expand)
        float glowOffset = -0.5f * globalScale;
        sx -= glowOffset;
        sy -= glowOffset;
        sw += glowOffset * 2;
        sh += glowOffset * 2;

        float sr = barRadius * globalScale;

        float centerX = x + this.width / 2f;
        float centerY = y + this.height / 2f;

        float finalX = centerX + (sx - centerX) * animValue;
        float finalY = centerY + (sy - centerY) * animValue;
        float finalW = sw * animValue;
        float finalH = sh * animValue;
        float finalR = sr * animValue;

        if (finalW <= 0 || finalH <= 0) return;

        Color c1 = healthColor.get();
        Color c2 = healthColor.get().darker();
        if (healthGradient.get()) {
            double speed = gradientSpeed.get();
            float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);
            float length = colorLength.get().floatValue();
            float frequency = 1.0f / length;
            float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
            float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);
            c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
            c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
        }

        float[] rects = new float[]{finalX, finalY, finalW, finalH};
        float[] radii = new float[]{finalR};

        float range = glowStrength.get().floatValue() * globalScale;
        float strength = 1.0f;

        Color start = c1;
        Color end = healthGradient.get() ? c2 : c1;

        start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
        end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);

        if (healthGradient.get()) {
            ShadowShader.drawStairShadowGradient(finalX, finalY, finalW, finalH, range, strength, start, end, rects, radii, 1);
        } else {
            ShadowShader.drawStairShadow(finalX, finalY, finalW, finalH, range, strength, start, rects, radii, 1);
        }
    }

    private void renderHanabi(long vg, LivingEntity target, float health, float maxHealth) {
        renderHanabiFlat(vg, target, health, maxHealth);
    }

    private void renderHanabiFlat(long vg, LivingEntity target, float health, float maxHealth) {
        float w = 140f;
        float h = 40f;
        this.width = w;
        this.height = h;

        boolean noTarget = target == null;
        if (noTarget && hanabiHudHeight == 0.0f) return;

        int healthColor;
        String healthStr;
        boolean hurtNow;
        if (noTarget) {
            healthStr = String.valueOf(0.0f);
            healthColor = getHanabiHealthColor(0.0f, 20.0f).getRGB();
            hurtNow = false;
        } else {
            healthStr = String.valueOf(((int) health) / 2f);
            healthColor = getHanabiHealthColor(health, maxHealth).getRGB();
            hurtNow = target.hurtTime > 0;
        }

        NanoVG.nvgScissor(vg, x, y + (h - hanabiHudHeight), w, hanabiHudHeight);

        NanoVGHelper.drawRect(x, y, w, h, new Color(0, 0, 0, 180));

        NanoVGHelper.drawRect(x, y + 37f, w, 3f, new Color(0, 0, 0, 49));
        NanoVGHelper.drawRect(x, y + 37f, hanabiHealthBarWidth2, 3f, new Color(255, 0, 213, 220));

        shaderBarX = x;
        shaderBarY = y + 37f;
        shaderBarW = hanabiHealthBarWidth;
        shaderBarH = 3f;
        shaderBarRadius = 0f;

        if (barMode.get() == BarModeEn.Shader) {
            // skip normal health bar fill, shader will render after NanoVG
        } else {
            NanoVGHelper.drawGradientRRect2(x, y + 37f, hanabiHealthBarWidth, 3f, 0f, new Color(0, 81, 179), new Color(healthColor));
        }

        float hpTextWidth = NanoVGHelper.getTextWidth(healthStr, FontLoader.bold(), HANABI_HEALTH_SIZE);
        float hpTextX = x + w - HANABI_HEALTH_TEXT_RIGHT_PAD - hpTextWidth;
        float hpTextY = y + HANABI_HEALTH_TEXT_Y;
        NanoVGHelper.drawString(healthStr, hpTextX, hpTextY, FontLoader.bold(), HANABI_HEALTH_SIZE, HANABI_HP_TEXT_COLOR);
        float heartW = NanoVGHelper.getTextWidth("❤", FontLoader.bold(), HANABI_HEART_SIZE);
        NanoVGHelper.drawString("❤", hpTextX - HANABI_HEART_GAP - heartW, hpTextY, FontLoader.bold(), HANABI_HEART_SIZE, hurtNow ? HANABI_HEART_HURT_COLOR : HANABI_HEART_NORMAL_COLOR);

        float infoX = x + 37f + HANABI_INFO_X;
        float infoY = y + 17f + HANABI_INFO_Y;

        NanoVGHelper.drawString("XYZ:", infoX, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_LABEL_COLOR);
        float cursor = infoX + NanoVGHelper.getTextWidth("XYZ:", FontLoader.regular(), HANABI_INFO_SIZE);

        String xStr = noTarget ? "0" : String.valueOf((int) target.getX());
        String yStr = noTarget ? "0" : String.valueOf((int) target.getY());
        String zStr = noTarget ? "0" : String.valueOf((int) target.getZ());

        NanoVGHelper.drawString(" " + xStr, cursor, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_VALUE_COLOR);
        cursor += NanoVGHelper.getTextWidth(" " + xStr, FontLoader.regular(), HANABI_INFO_SIZE);
        NanoVGHelper.drawString(" " + yStr, cursor, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_VALUE_COLOR);
        cursor += NanoVGHelper.getTextWidth(" " + yStr, FontLoader.regular(), HANABI_INFO_SIZE);
        NanoVGHelper.drawString(" " + zStr, cursor, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_VALUE_COLOR);
        cursor += NanoVGHelper.getTextWidth(" " + zStr, FontLoader.regular(), HANABI_INFO_SIZE);

        NanoVGHelper.drawString(" | Hurt:", cursor, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_LABEL_COLOR);
        cursor += NanoVGHelper.getTextWidth(" | Hurt:", FontLoader.regular(), HANABI_INFO_SIZE);
        NanoVGHelper.drawString(String.valueOf(hurtNow), cursor, infoY, FontLoader.regular(), HANABI_INFO_SIZE, HANABI_LABEL_COLOR);

        NanoVGHelper.drawString(noTarget ? "(No target)" : target.getName().getString(), x + 36f + HANABI_NAME_X, y + 10f + HANABI_NAME_Y, FontLoader.bold(), HANABI_NAME_SIZE, Color.WHITE);

        if (target instanceof PlayerEntity player) {
            drawPlayerAvatar(player, x + 3f, y + 3f, 32f, 0f, 1f, 0f);
        } else {
            NanoVGHelper.drawRect(x + 3f, y + 3f, 32f, 32f, new Color(80, 80, 80, 200));
        }

        NanoVG.nvgResetScissor(vg);
    }

    private void updateHanabiState(LivingEntity target, float health, float maxHealth) {
        float w = 140f;
        float h = 40f;
        this.width = w;
        this.height = h;

        boolean noTarget = target == null;
        double hpPercentage;
        if (noTarget) {
            hpPercentage = 0.0;
        } else {
            hpPercentage = health / Math.max(0.001f, maxHealth);
        }
        hpPercentage = MathHelper.clamp(hpPercentage, 0.0, 1.0);
        double hpWidth = 140.0 * hpPercentage;

        float fps = mc.getCurrentFps() > 0 ? mc.getCurrentFps() : 60f;
        if (noTarget) {
            hanabiHealthBarWidth2 = (float) hanabiGetAnimationStateSmooth(0.0, hanabiHealthBarWidth2, 6.0 / fps);
            hanabiHealthBarWidth = (float) hanabiGetAnimationStateSmooth(0.0, hanabiHealthBarWidth, 14.0 / fps);
            hanabiHudHeight = (float) hanabiGetAnimationStateSmooth(0.0, hanabiHudHeight, 8.0 / fps);
        } else {
            hanabiHealthBarWidth2 = hanabiMoveUD(hanabiHealthBarWidth2, (float) hpWidth, 6.0f / fps, 3.0f / fps);
            hanabiHealthBarWidth = (float) hanabiGetAnimationStateSmooth(hpWidth, hanabiHealthBarWidth, 14.0 / fps);
            hanabiHudHeight = (float) hanabiGetAnimationStateSmooth(40.0, hanabiHudHeight, 8.0 / fps);
        }

        if (hanabiHudHeight == 0.0f) {
            hanabiHealthBarWidth2 = w;
            hanabiHealthBarWidth = w;
        }
    }

    private static float hanabiMoveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;
        if (movement > 0.0f) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0.0f) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }
        return current + movement;
    }

    private static double hanabiGetAnimationStateSmooth(double target, double current, double speed) {
        boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        if (target == current) {
            return target;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            if (current + factor > target) {
                current = target;
            } else {
                current += factor;
            }
        } else if (current - factor < target) {
            current = target;
        } else {
            current -= factor;
        }
        return current;
    }

    private static Color getHanabiHealthColor(float health, float maxHealth) {
        float progress = MathHelper.clamp(health / Math.max(0.001f, maxHealth), 0f, 1f);
        Color[] colors = new Color[]{new Color(0, 81, 179), new Color(0, 153, 255), new Color(47, 154, 241)};
        float[] fractions = new float[]{0f, 0.5f, 1f};
        return blendColors(fractions, colors, progress).brighter();
    }

    private static Color blendColors(float[] fractions, Color[] colors, float progress) {
        int[] indices = getFractionIndices(fractions, progress);
        float[] range = new float[]{fractions[indices[0]], fractions[indices[1]]};
        Color[] colorRange = new Color[]{colors[indices[0]], colors[indices[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    private static int[] getFractionIndices(float[] fractions, float progress) {
        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            startPoint++;
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        return new int[]{Math.max(0, startPoint - 1), startPoint};
    }

    private static Color blend(Color color1, Color color2, float ratio) {
        float ir = 1.0f - ratio;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color(rgb1[0] * ratio + rgb2[0] * ir, rgb1[1] * ratio + rgb2[1] * ir, rgb1[2] * ratio + rgb2[2] * ir);
    }

    // ====================================================================================
    //                                  PARTICLES
    // ====================================================================================

    private static class Particles {
        float x, y;
        float motionX, motionY;
        float opacity;
        Color color;

        public void init(float x, float y, float motionX, float motionY, float opacity, Color color) {
            this.x = x;
            this.y = y;
            this.motionX = motionX;
            this.motionY = motionY;
            this.opacity = opacity;
            this.color = color;
        }

        public void updatePosition() {
            this.x += this.motionX;
            this.y += this.motionY;
            this.opacity -= 0.5f;
        }

        public void render() {
            if (opacity <= 0) return;
            int alpha = (int) MathHelper.clamp(opacity * 12, 0, 255);
            Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
            NanoVGHelper.drawCircle(x, y, 2f, c);
        }
    }

    private static class Particle {
        public float x, y;
        public float velocityX, velocityY;
        public float size;
        public Color color;
        public float alpha;
        public float life;
        public float maxLife;
        public float startX, startY;
        public float gravity;
        public MahiroParticleModeEn mode;

        public Particle(float x, float y, Color color, MahiroParticleModeEn mode, float range) {
            this.x = x;
            this.y = y;
            this.startX = x;
            this.startY = y;
            this.size = 1.0f + (float) (Math.random() * 2.0f);
            this.color = color;
            this.alpha = 1.0f;
            this.maxLife = 100f + (float) (Math.random() * 100f);
            this.life = maxLife;
            this.mode = mode;
            this.gravity = 0.05f;

            float speed = 0.3f + (float) (Math.random() * range);
            double angle = Math.random() * Math.PI * 2;
            this.velocityX = (float) (Math.cos(angle) * speed);
            this.velocityY = (float) (Math.sin(angle) * speed);
        }

        public void update() {
            if (mode == MahiroParticleModeEn.Spread) {
                x += velocityX;
                y += velocityY;
            } else if (mode == MahiroParticleModeEn.Fall) {
                x += velocityX;
                y += velocityY;
                velocityY += gravity;
            }
            life -= 1.0f;
            alpha = life / maxLife;
        }

        public boolean isAlive() {
            return life > 0;
        }

        public void render(long vg) {
            if (!isAlive()) return;
            Color particleColor = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    (int) (color.getAlpha() * alpha)
            );

            org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
            org.lwjgl.nanovg.NanoVG.nvgCircle(vg, x, y, size);
            org.lwjgl.nanovg.NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(particleColor));
            org.lwjgl.nanovg.NanoVG.nvgFill(vg);

            org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
            org.lwjgl.nanovg.NanoVG.nvgCircle(vg, x, y, size * 1.5f);
            org.lwjgl.nanovg.NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 255, 255, (int) (50 * alpha))));
            org.lwjgl.nanovg.NanoVG.nvgFill(vg);
        }
    }
}
