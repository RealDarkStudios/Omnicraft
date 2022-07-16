package net.darkstudios.omnicraft.screen.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModFuelSlot extends SlotItemHandler {
    private RecipeType<?> recipeType;

    public ModFuelSlot(IItemHandler handler, RecipeType<?> recipeType, Container pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
        super(handler, pSlot, pXPosition, pYPosition);
        this.recipeType = recipeType;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean mayPlace(ItemStack pStack) {
        return isFuel(pStack) || isBucket(pStack);
    }

    public int getMaxStackSize(ItemStack pStack) {
        return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isBucket(ItemStack pStack) {
        return pStack.is(Items.BUCKET);
    }

    public boolean isFuel(ItemStack pStack) {
        return ForgeHooks.getBurnTime(pStack, this.recipeType) > 0;
    }
}
