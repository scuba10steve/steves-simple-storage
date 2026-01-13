package io.github.scuba10steve.ezstorage.block;

import io.github.scuba10steve.ezstorage.config.EZConfig;

import net.minecraft.block.material.Material;

public class BlockStorage extends StorageMultiblock {

	public BlockStorage() {
		super("storage_box", Material.WOOD);
	}

	public BlockStorage(String name, Material material) {
		super(name, material);
	}

	public int getCapacity() {
		return EZConfig.basicCapacity;
	}
}