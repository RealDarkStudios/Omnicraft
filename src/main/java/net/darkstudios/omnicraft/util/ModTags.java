package net.darkstudios.omnicraft.util;

import net.darkstudios.omnicraft.Omnicraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Omnicraft.MOD_ID, name));
        }

        public static final TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
    public static class Items {
        public static final TagKey<Item> OMNIFURNACE_LOW_TIER_FUEL = tag("omnifurnace_low_tier_fuel");
        public static final TagKey<Item> OMNIFURNACE_MEDIUM_TIER_FUEL = tag("omnifurnace_medium_tier_fuel");
        public static final TagKey<Item> OMNIFURNACE_HIGH_TIER_FUEL = tag("omnifurnace_high_tier_fuel");

        public static final TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Omnicraft.MOD_ID, name));
        }

        public static final TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
