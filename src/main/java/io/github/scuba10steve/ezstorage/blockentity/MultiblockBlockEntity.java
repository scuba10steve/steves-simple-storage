package io.github.scuba10steve.ezstorage.blockentity;

import io.github.scuba10steve.ezstorage.block.BlockStorageCore;
import io.github.scuba10steve.ezstorage.block.StorageMultiblock;
import io.github.scuba10steve.ezstorage.util.BlockRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MultiblockBlockEntity extends EZBlockEntity {
    protected StorageCoreBlockEntity core;
    private static final int UPDATE_PERIOD = 10;
    
    public MultiblockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    public void tick() {
        if (!hasCore()) {
            if (!level.isClientSide && level.getGameTime() % UPDATE_PERIOD == 0) {
                StorageMultiblock block = (StorageMultiblock) getBlockState().getBlock();
                core = block.attemptMultiblock(level, worldPosition);
            }
        } else {
            if (!isCorePartOfMultiblock()) {
                core = null;
            }
        }
    }
    
    public boolean hasCore() {
        return core != null && !core.isRemoved();
    }
    
    public boolean isCorePartOfMultiblock() {
        return hasCore() && core.isPartOfMultiblock(new BlockRef(getBlockState().getBlock(), worldPosition));
    }
}
