package io.github.scuba10steve.s3.item;

public class ItemDolly extends EZItem {
    private final int capacity;
    
    public ItemDolly(int capacity) {
        super();
        this.capacity = capacity;
    }
    
    public int getCapacity() {
        return capacity;
    }
}
