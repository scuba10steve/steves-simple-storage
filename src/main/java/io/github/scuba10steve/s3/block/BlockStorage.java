package io.github.scuba10steve.s3.block;

import io.github.scuba10steve.s3.config.StorageConfig;
import io.github.scuba10steve.s3.init.ModBlocks;

public class BlockStorage extends StorageMultiblock implements StorageTier {
    private final int defaultCapacity;
    
    public BlockStorage(int defaultCapacity) {
        super(Properties.of().strength(2.0f));
        this.defaultCapacity = defaultCapacity;
    }

    @Override
    public int getCapacity() {
        // Return config value if available, otherwise use default
        if (this == ModBlocks.STORAGE_BOX.get()) {
            return StorageConfig.BASIC_CAPACITY.get();
        } else if (this == ModBlocks.CONDENSED_STORAGE_BOX.get()) {
            return StorageConfig.CONDENSED_CAPACITY.get();
        } else if (this == ModBlocks.SUPER_STORAGE_BOX.get()) {
            return StorageConfig.SUPER_CAPACITY.get();
        } else if (this == ModBlocks.ULTRA_STORAGE_BOX.get()) {
            return StorageConfig.ULTRA_CAPACITY.get();
        } else if (this == ModBlocks.HYPER_STORAGE_BOX.get()) {
            return StorageConfig.HYPER_CAPACITY.get();
        }
        return defaultCapacity;
    }
}
