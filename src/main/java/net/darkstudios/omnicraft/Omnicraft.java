package net.darkstudios.omnicraft;

import com.mojang.logging.LogUtils;
import net.darkstudios.omnicraft.block.ModBlocks;
import net.darkstudios.omnicraft.block.entity.ModBlockEntities;
import net.darkstudios.omnicraft.item.ModItems;
import net.darkstudios.omnicraft.recipes.ModRecipes;
import net.darkstudios.omnicraft.screen.ModMenuTypes;
import net.darkstudios.omnicraft.screen.OmnifurnaceScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Omnicraft.MOD_ID)
public class Omnicraft {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "omnicraft";

    public Omnicraft() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        ModRecipes.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.OMNIFURNACE_MENU.get(), OmnifurnaceScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Omnicraft Setup");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
