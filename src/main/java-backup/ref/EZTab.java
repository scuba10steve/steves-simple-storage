package io.github.scuba10steve.ezstorage.ref;

import io.github.scuba10steve.ezstorage.init.EZBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EZTab extends CreativeTabs {

	public EZTab() {
		super("EZStorage");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(EZBlocks.condensed_storage_box);
	}
}
