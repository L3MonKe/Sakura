package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Items;

public class AutoSoup extends Module {
    public AutoSoup() {
        super("AutoSoup", "自动汤", Category.Player);
    }

    public static Integer back;

    @Override
    public void onEnable() {
        back = null;
    }

    @Override
    public void onDisable() {
        back = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (back == null) {
            if (mc.player.age % 10 != 0) return;
            if (mc.player.getHealth() < mc.player.getMaxHealth() / 2) {
                FindItemResult soup = InvUtil.findInHotbar(Items.MUSHROOM_STEW);
                if (soup.found()) {
                    back = mc.player.getInventory().getSelectedSlot();
                    mc.player.getInventory().setSelectedSlot(soup.slot());
                    KeyBinding.onKeyPressed(mc.options.useKey.getDefaultKey());
                }
            }
        } else {
            mc.player.getInventory().setSelectedSlot(back);
            back = null;
        }
    }
}
