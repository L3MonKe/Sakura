package dev.mzc.client.module.impl.misc;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.manager.impl.NotificationManager;
import dev.mzc.client.module.impl.client.ClickGui;

public class Auto32k extends Module {

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Sword);
    private enum Mode {
        Sword("32k 剑"),
        Axe("32k 斧"),
        PickaxeFortune("32k 时运镐"),
        PickaxeSilkTouch("32k 精准镐"),
        Shovel("32k 铲"),
        Mace("32k 锤"),
        Armor("32k 装备"),
        Bedrock("基岩"),
        Barrier("屏障"),
        CommandBlock("命令方块");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }

        public String getCnName() {
            return cnName;
        }


    }

    public Auto32k() {
        super("Auto32k", "自动32k物品", Category.Misc);
        this.setType(ModuleType.Hack);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            this.toggle();
            return;
        }

        switch (mode.get()) {
            case Sword:
                sendCommand("/give @p netherite_sword[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:sharpness\":255,\"minecraft:smite\":255,\"minecraft:bane_of_arthropods\":255}}] 1");
                break;
            case Axe:
                sendCommand("/give @p netherite_axe[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:sharpness\":255,\"minecraft:efficiency\":255,\"minecraft:smite\":255,\"minecraft:bane_of_arthropods\":255}}] 1");
                break;
            case PickaxeFortune:
                sendCommand("/give @p netherite_pickaxe[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:efficiency\":255,\"minecraft:fortune\":255}}] 1");
                break;
            case PickaxeSilkTouch:
                sendCommand("/give @p netherite_pickaxe[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:efficiency\":255,\"minecraft:silk_touch\":1}}] 1");
                break;
            case Shovel:
                sendCommand("/give @p netherite_shovel[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:efficiency\":255}}] 1");
                break;
            case Mace:
                sendCommand("/give @p mace[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:density\":255,\"minecraft:breach\":255,\"minecraft:wind_burst\":2}}] 1");
                break;
            case Armor:
                sendCommand("/give @p netherite_helmet[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:protection\":255}}] 1");
                sendCommand("/give @p netherite_chestplate[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:protection\":255}}] 1");
                sendCommand("/give @p netherite_leggings[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:protection\":255}}] 1");
                sendCommand("/give @p netherite_boots[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}',unbreakable={},enchantments={levels:{\"minecraft:protection\":255}}] 1");
                break;
            case Bedrock:
                sendCommand("/give @p bedrock[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}'] 64");
                break;
            case Barrier:
                sendCommand("/give @p barrier[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}'] 64");
                break;
            case CommandBlock:
                sendCommand("/give @p command_block[custom_name='{\"text\":\"MZC-Client\",\"italic\":false}'] 64");
                break;
        }

        if (ClickGui.language.get() == ClickGui.Language.Chinese) {
            NotificationManager.send("已获取 " + mode.get().getCnName());
        } else {
            NotificationManager.send("Obtained " + mode.get().name());
        }

        // Automatically disable the module to prevent spam
        this.toggle();
    }

    private void sendCommand(String command) {
        if (!command.isEmpty()) {
            mc.player.networkHandler.sendChatCommand(command.substring(1));
        }
    }
}
