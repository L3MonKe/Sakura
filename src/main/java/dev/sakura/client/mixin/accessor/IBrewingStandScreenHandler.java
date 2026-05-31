package dev.sakura.client.mixin.accessor;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BrewingStandScreenHandler.class)
public interface IBrewingStandScreenHandler {

    @Accessor("inventory")
    Inventory getInventory();

}
