package net.darkstudios.omnicraft.block.entity.custom;

import net.darkstudios.omnicraft.block.entity.ModBlockEntities;
import net.darkstudios.omnicraft.recipes.custom.OmnifurnaceRecipe;
import net.darkstudios.omnicraft.screen.OmnifurnaceMenu;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class OmnifurnaceBlockEntity extends AbstractFurnaceBlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;

    //BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, RecipeType<? extends AbstractCookingRecipe> pRecipeType

    public OmnifurnaceBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.OMNIFURNACE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState, OmnifurnaceRecipe.Type.INSTANCE);
        this.data = new ContainerData() {
            public int get(int index) {
                switch(index) {
                    case 0:
                        return OmnifurnaceBlockEntity.this.litTime;
                    case 1:
                        return OmnifurnaceBlockEntity.this.litDuration;
                    case 2:
                        return OmnifurnaceBlockEntity.this.cookingProgress;
                    case 3:
                        return OmnifurnaceBlockEntity.this.cookingTotalTime;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0:
                        OmnifurnaceBlockEntity.this.litTime = value;
                        break;
                    case 1:
                        OmnifurnaceBlockEntity.this.litDuration = value;
                        break;
                    case 2:
                        OmnifurnaceBlockEntity.this.cookingProgress = value;
                        break;
                    case 3:
                        OmnifurnaceBlockEntity.this.cookingTotalTime = value;
                }

            }

            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("container.omnicraft.omnifurnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory) {
        return new OmnifurnaceMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
