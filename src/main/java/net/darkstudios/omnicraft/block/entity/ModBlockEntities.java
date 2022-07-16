package net.darkstudios.omnicraft.block.entity;

import net.darkstudios.omnicraft.Omnicraft;
import net.darkstudios.omnicraft.block.ModBlocks;
import net.darkstudios.omnicraft.block.entity.custom.OmnifurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Omnicraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<OmnifurnaceBlockEntity>> OMNIFURNACE_BLOCK_ENTITY = BLOCK_ENTITIES.register("omnifurnace_block_entity",
            () -> BlockEntityType.Builder.of(OmnifurnaceBlockEntity::new, ModBlocks.OMNIFURNACE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
