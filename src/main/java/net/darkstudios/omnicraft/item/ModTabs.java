package net.darkstudios.omnicraft.item;

import net.darkstudios.omnicraft.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModTabs {
    public static final CreativeModeTab OMNICRAFT_ITEMS = new CreativeModeTab("omnicraft_items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AIR);
        }
    };

    public static final CreativeModeTab OMNICRAFT_RESOURCES = new CreativeModeTab("omnicraft_resources") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.OMNIFUEL.get());
        }
    };

    public static final CreativeModeTab OMNICRAFT_BLOCKS = new CreativeModeTab("omnicraft_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.OMNIFURNACE.get());
        }
    };
}
