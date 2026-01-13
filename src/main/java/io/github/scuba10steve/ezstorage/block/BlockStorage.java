package io.github.scuba10steve.ezstorage.block;

public class BlockStorage extends StorageMultiblock {
    private final int capacity;
    
    public BlockStorage(int capacity) {
        super(Properties.of().strength(2.0f));
        this.capacity = capacity;
    }
    
    public int getCapacity() {
        return capacity;
    }
}
