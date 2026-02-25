package dev.sakura.client.module.impl.render;

import dev.sakura.client.mixin.accessor.IMinecraftClient;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class XRay extends Module {
    public XRay() {
        super("XRay", "透视", Category.Render);
        initOreMap();
    }

    private final BoolValue coal = new BoolValue("Coal", "煤矿", false);
    private final BoolValue iron = new BoolValue("Iron", "铁矿", true);
    private final BoolValue gold = new BoolValue("Gold", "金矿", true);
    private final BoolValue redstone = new BoolValue("Redstone", "红石", true);
    private final BoolValue lapis = new BoolValue("Lapis", "青金石", true);
    private final BoolValue diamond = new BoolValue("Diamond", "钻石", true);
    private final BoolValue emerald = new BoolValue("Emerald", "绿宝石", true);
    private final BoolValue copper = new BoolValue("Copper", "铜矿", true);
    private final BoolValue netherite = new BoolValue("Netherite", "下界合金", true);
    private final BoolValue netherGold = new BoolValue("NetherGold", "下界金矿", true);
    private final BoolValue netherQuartz = new BoolValue("NetherQuartz", "下界石英", false);

    private final Map<Block, BoolValue> oreMap = new HashMap<>();

    private void initOreMap() {
        Set.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_BLOCK).forEach(b -> oreMap.put(b, coal));
        Set.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.RAW_IRON_BLOCK, Blocks.IRON_BLOCK).forEach(b -> oreMap.put(b, iron));
        Set.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_BLOCK).forEach(b -> oreMap.put(b, gold));
        Set.of(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_BLOCK).forEach(b -> oreMap.put(b, redstone));
        Set.of(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_BLOCK).forEach(b -> oreMap.put(b, lapis));
        Set.of(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_BLOCK).forEach(b -> oreMap.put(b, diamond));
        Set.of(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_BLOCK).forEach(b -> oreMap.put(b, emerald));
        Set.of(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.RAW_COPPER_BLOCK, Blocks.COPPER_BLOCK).forEach(b -> oreMap.put(b, copper));
        Set.of(Blocks.ANCIENT_DEBRIS, Blocks.NETHERITE_BLOCK).forEach(b -> oreMap.put(b, netherite));
        Set.of(Blocks.NETHER_GOLD_ORE, Blocks.GILDED_BLACKSTONE).forEach(b -> oreMap.put(b, netherGold));
        Set.of(Blocks.NETHER_QUARTZ_ORE, Blocks.QUARTZ_BLOCK).forEach(b -> oreMap.put(b, netherQuartz));
    }

    @Override
    protected void onEnable() {
        ((IMinecraftClient) mc).setChunkCullingEnabled(false);
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    protected void onDisable() {
        ((IMinecraftClient) mc).setChunkCullingEnabled(true);
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    public boolean shouldRender(Block block) {
        if (!isEnabled()) return true;
        BoolValue setting = oreMap.get(block);
        return setting != null && setting.get();
    }

    public boolean isOre(Block block) {
        return oreMap.containsKey(block);
    }
}