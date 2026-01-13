package com.zerofall.ezstorage.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class EZStorageUtils {
    public static void getModNameFromID(String modId) {
        // Placeholder for mod name mapping
    }
    
    public static List<BlockRef> getNeighbors(BlockPos pos, Level level) {
        List<BlockRef> neighbors = new ArrayList<>();
        
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            Block block = level.getBlockState(neighborPos).getBlock();
            neighbors.add(new BlockRef(block, neighborPos));
        }
        
        return neighbors;
    }
}
