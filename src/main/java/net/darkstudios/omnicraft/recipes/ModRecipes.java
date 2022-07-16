package net.darkstudios.omnicraft.recipes;

import net.darkstudios.omnicraft.Omnicraft;
import net.darkstudios.omnicraft.recipes.custom.OmnifurnaceRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Omnicraft.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OmnifurnaceRecipe>> OMNIFURNACE = SERIALIZERS.register("omnifurnace",() -> OmnifurnaceRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
