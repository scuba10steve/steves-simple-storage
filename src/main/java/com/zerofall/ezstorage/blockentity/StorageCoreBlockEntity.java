package com.zerofall.ezstorage.blockentity;

import com.zerofall.ezstorage.gui.server.StorageCoreMenu;
import com.zerofall.ezstorage.init.EZBlockEntities;
import com.zerofall.ezstorage.storage.EZInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class StorageCoreBlockEntity extends EZBlockEntity implements MenuProvider {
    private final EZInventory inventory = new EZInventory();
    
    public StorageCoreBlockEntity(BlockPos pos, BlockState state) {
        super(EZBlockEntities.STORAGE_CORE.get(), pos, state);
    }

    public ItemStack insertItem(ItemStack stack) {
        setChanged();
        return inventory.insertItem(stack);
    }
    
    public ItemStack extractItem(ItemStack template, int amount) {
        setChanged();
        return inventory.extractItem(template, amount);
    }
    
    public EZInventory getInventory() {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        // TODO: Save inventory data to NBT
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // TODO: Load inventory data from NBT
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.ezstorage.storage_core");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new StorageCoreMenu(containerId, playerInventory, this.worldPosition);
    }
}
