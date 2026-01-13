package io.github.scuba10steve.ezstorage.storage;

import net.minecraft.world.item.ItemStack;

public class StoredItemStack {
    private final ItemStack itemStack;
    private long count;
    
    public StoredItemStack(ItemStack itemStack, long count) {
        this.itemStack = itemStack.copy();
        this.count = count;
    }
    
    public ItemStack getItemStack() {
        return itemStack.copy();
    }
    
    public long getCount() {
        return count;
    }
    
    public void addCount(long amount) {
        this.count += amount;
    }
    
    public void removeCount(long amount) {
        this.count = Math.max(0, this.count - amount);
    }
    
    public void setCount(long count) {
        this.count = Math.max(0, count);
    }
}
