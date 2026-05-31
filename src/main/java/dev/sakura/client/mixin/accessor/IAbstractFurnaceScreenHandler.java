package dev.sakura.client.mixin.accessor;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceScreenHandler.class)
public interface IAbstractFurnaceScreenHandler {

    @Accessor("inventory")
    Inventory getInventory();

}
