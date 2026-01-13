package com.zerofall.ezstorage.gui.server;

import com.zerofall.ezstorage.blockentity.StorageCoreBlockEntity;
import com.zerofall.ezstorage.init.EZMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

public class StorageCoreMenu extends AbstractContainerMenu {
    
    private final StorageCoreBlockEntity blockEntity;
    private final BlockPos pos;

    public StorageCoreMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(EZMenuTypes.STORAGE_CORE.get(), containerId);
        this.pos = pos;
        this.blockEntity = (StorageCoreBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        
        // Add storage slots (6 rows of 9 slots)
        addStorageSlots();
        
        // Add player inventory slots
        addPlayerInventory(playerInventory);
    }
    
    private void addStorageSlots() {
        // TODO: Add actual storage slots when storage GUI is implemented
        // For now, this is just the basic container structure
    }

    private void addPlayerInventory(Inventory playerInventory) {
        // Player inventory (3 rows)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }
        
        // Player hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 198));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null && 
               player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }
}
