package net.darkstudios.omnicraft.block;

import net.darkstudios.omnicraft.Omnicraft;
import net.darkstudios.omnicraft.block.custom.OmnifurnaceBlock;
import net.darkstudios.omnicraft.item.ModItems;
import net.darkstudios.omnicraft.item.ModTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Omnicraft.MOD_ID);

    public static final RegistryObject<Block> OMNIFURNACE = registerBlock("omnifurnace", () -> new OmnifurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.5F).lightLevel(litBlockEmission(13))), new Item.Properties().tab(ModTabs.OMNICRAFT_BLOCKS));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Item.Properties settings) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, settings);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block, Item.Properties settings) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Item.Properties settings) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), settings));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (blockState) -> {
            return blockState.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
