package io.github.scuba10steve.s3.block;

import io.github.scuba10steve.s3.blockentity.StatisticsBoxBlockEntity;
import io.github.scuba10steve.s3.blockentity.StorageCoreBlockEntity;
import io.github.scuba10steve.s3.gui.server.StatisticsBoxMenu;
import io.github.scuba10steve.s3.platform.S3Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockStatisticsBox extends StorageMultiblock implements EntityBlock {

    public BlockStatisticsBox() {
        super(Properties.of().strength(2.0f));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StatisticsBoxBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StatisticsBoxBlockEntity statisticsBox) {
                StorageCoreBlockEntity core = statisticsBox.getCore();
                if (core != null && player instanceof ServerPlayer serverPlayer) {
                    S3Platform.openMenu(serverPlayer, new StatisticsMenuProvider(core), core.getBlockPos());
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private record StatisticsMenuProvider(StorageCoreBlockEntity core) implements MenuProvider {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.s3.statistics_box");
        }

        @Override
        public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
            return new StatisticsBoxMenu(containerId, playerInventory, core);
        }
    }
}
