package net.darkstudios.omnicraft.recipes.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.darkstudios.omnicraft.block.ModBlocks;
import net.darkstudios.omnicraft.recipes.custom.serializer.OmnifurnaceSerializer;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class OmnifurnaceRecipe extends AbstractCookingRecipe implements Container {
    public ItemStack result;
    public OmnifurnaceSerializer<OmnifurnaceRecipe> ingredient;

    public OmnifurnaceRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(Type.INSTANCE, pId, pGroup, pIngredient, pResult, pExperience, pCookingTime);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.OMNIFURNACE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    public static class Type implements RecipeType<OmnifurnaceRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "omnifurnace";
    }

    public static class Serializer<T extends OmnifurnaceRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
        public static final Serializer<OmnifurnaceRecipe> INSTANCE = new Serializer<>(OmnifurnaceRecipe::new, 50);
        private final int defaultCookingTime;
        private final CookieBaker<T> factory;

        public Serializer(CookieBaker<T> pFactory, int pDefaultCookingTime) {
            this.defaultCookingTime = pDefaultCookingTime;
            this.factory = pFactory;
        }

        public T fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            JsonElement jsonelement = (JsonElement)(GsonHelper.isArrayNode(pJson, "ingredient") ? GsonHelper.getAsJsonArray(pJson, "ingredient") : GsonHelper.getAsJsonObject(pJson, "ingredient"));
            Ingredient ingredient = Ingredient.fromJson(jsonelement);
            //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
            if (!pJson.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack itemstack;
            if (pJson.get("result").isJsonObject()) itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            else {
                String s1 = GsonHelper.getAsString(pJson, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new IllegalStateException("Item: " + s1 + " does not exist");
                }));
            }
            float f = GsonHelper.getAsFloat(pJson, "experience", 0.0F);
            int i = GsonHelper.getAsInt(pJson, "cookingtime", this.defaultCookingTime);
            return this.factory.create(pRecipeId, s, ingredient, itemstack, f, i);
        }

        public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            float f = pBuffer.readFloat();
            int i = pBuffer.readVarInt();
            return this.factory.create(pRecipeId, s, ingredient, itemstack, f, i);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pRecipe.ingredient.toNetwork(pBuffer, pRecipe);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeFloat(pRecipe.getExperience());
            pBuffer.writeVarInt(pRecipe.getCookingTime());
        }

        public interface CookieBaker<T extends AbstractCookingRecipe> {
            T create(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime);
        }
    }
}
