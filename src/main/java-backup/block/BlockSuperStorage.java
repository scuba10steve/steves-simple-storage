package io.github.scuba10steve.ezstorage.block;

import net.minecraft.block.material.Material;

import io.github.scuba10steve.ezstorage.config.EZConfig;
import io.github.scuba10steve.ezstorage.registry.IRegistryBlock;

/** A super storage box */
public class BlockSuperStorage extends BlockStorage implements IRegistryBlock {

	public BlockSuperStorage() {
		super("super_storage_box", Material.IRON);
	}

	@Override
	public int getCapacity() {
		return EZConfig.superCapacity;
	}

}
