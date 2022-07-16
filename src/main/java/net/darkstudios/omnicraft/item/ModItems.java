package net.darkstudios.omnicraft.item;

import net.darkstudios.omnicraft.Omnicraft;
import net.darkstudios.omnicraft.item.custom.OmnifuelItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Omnicraft.MOD_ID);

    public static final RegistryObject<Item> OMNIFUEL = ITEMS.register("omnifuel", () -> new OmnifuelItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
