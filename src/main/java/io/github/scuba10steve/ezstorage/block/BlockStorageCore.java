package io.github.scuba10steve.ezstorage.block;

import io.github.scuba10steve.ezstorage.blockentity.StorageCoreBlockEntity;
import io.github.scuba10steve.ezstorage.init.EZBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public class BlockStorageCore extends StorageMultiblock implements EntityBlock {
    public BlockStorageCore() {
        super(Properties.of().strength(2.0f));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageCoreBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == EZBlockEntities.STORAGE_CORE.get() ? (level1, pos, state1, blockEntity) -> {
            // Ticker logic will be implemented later
        } : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StorageCoreBlockEntity storageCore) {
                player.openMenu(storageCore, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
