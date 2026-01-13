package com.zerofall.ezstorage.blockentity;

import com.zerofall.ezstorage.block.BlockStorage;
import com.zerofall.ezstorage.block.StorageMultiblock;
import com.zerofall.ezstorage.gui.server.StorageCoreMenu;
import com.zerofall.ezstorage.init.EZBlockEntities;
import com.zerofall.ezstorage.network.StorageSyncPacket;
import com.zerofall.ezstorage.storage.EZInventory;
import com.zerofall.ezstorage.util.BlockRef;
import com.zerofall.ezstorage.util.EZStorageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StorageCoreBlockEntity extends EZBlockEntity implements MenuProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageCoreBlockEntity.class);
    
    private final EZInventory inventory = new EZInventory();
    private final Set<BlockRef> multiblock = new HashSet<>();
    
    public StorageCoreBlockEntity(BlockPos pos, BlockState state) {
        super(EZBlockEntities.STORAGE_CORE.get(), pos, state);
    }

    public void scanMultiblock() {
        LOGGER.info("Scanning multiblock at {}", worldPosition);
        
        long totalCapacity = 0;
        multiblock.clear();
        
        BlockRef coreRef = new BlockRef(getBlockState().getBlock(), worldPosition);
        multiblock.add(coreRef);
        getValidNeighbors(coreRef);
        
        // Calculate total capacity from storage blocks
        for (BlockRef blockRef : multiblock) {
            if (blockRef.block instanceof BlockStorage storage) {
                totalCapacity += storage.getCapacity();
                LOGGER.debug("Found storage block at {} with capacity {}", blockRef.pos, storage.getCapacity());
            }
        }
        
        LOGGER.info("Multiblock scan complete. Found {} blocks, total capacity: {}", multiblock.size(), totalCapacity);
        inventory.setMaxItems(totalCapacity);
        setChanged();
        syncToClients();
    }
    
    private void getValidNeighbors(BlockRef br) {
        List<BlockRef> neighbors = EZStorageUtils.getNeighbors(br.pos, level);
        for (BlockRef blockRef : neighbors) {
            if (blockRef.block instanceof StorageMultiblock) {
                if (multiblock.add(blockRef)) {
                    getValidNeighbors(blockRef);
                }
            }
        }
    }
    
    public boolean isPartOfMultiblock(BlockRef blockRef) {
        return multiblock.contains(blockRef);
    }

    public ItemStack insertItem(ItemStack stack) {
        LOGGER.debug("Attempting to insert item: {} x{}", stack.getItem(), stack.getCount());
        
        if (stack.isEmpty()) {
            LOGGER.debug("Stack is empty, returning");
            return ItemStack.EMPTY;
        }
        
        ItemStack result = inventory.insertItem(stack);
        LOGGER.debug("Insert result: {} remaining", result.getCount());
        
        setChanged();
        syncToClients();
        return result;
    }
    
    public ItemStack extractItem(ItemStack template, int amount) {
        LOGGER.debug("Attempting to extract item: {} x{}", template.getItem(), amount);
        
        ItemStack result = inventory.extractItem(template, amount);
        LOGGER.debug("Extract result: {} x{}", result.getItem(), result.getCount());
        
        setChanged();
        syncToClients();
        return result;
    }
    
    private void syncToClients() {
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(
                serverLevel, 
                level.getChunkAt(worldPosition).getPos(),
                new StorageSyncPacket(worldPosition, inventory.getStoredItems())
            );
        }
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
