package io.github.scuba10steve.s3.blockentity;

import io.github.scuba10steve.s3.platform.S3Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StatisticsBoxBlockEntity extends MultiblockBlockEntity {

    public StatisticsBoxBlockEntity(BlockPos pos, BlockState state) {
        super(S3Platform.getStatisticsBoxBEType(), pos, state);
    }
}
