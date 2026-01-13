package io.github.scuba10steve.ezstorage.init;

import io.github.scuba10steve.ezstorage.block.BlockAccessTerminal;
import io.github.scuba10steve.ezstorage.block.BlockBlankBox;
import io.github.scuba10steve.ezstorage.block.BlockCondensedStorage;
import io.github.scuba10steve.ezstorage.block.BlockCraftingBox;
import io.github.scuba10steve.ezstorage.block.BlockEjectPort;
import io.github.scuba10steve.ezstorage.block.BlockExtractPort;
import io.github.scuba10steve.ezstorage.block.BlockHyperStorage;
import io.github.scuba10steve.ezstorage.block.BlockInputPort;
import io.github.scuba10steve.ezstorage.block.BlockSearchBox;
import io.github.scuba10steve.ezstorage.block.BlockSecurityBox;
import io.github.scuba10steve.ezstorage.block.BlockSortBox;
import io.github.scuba10steve.ezstorage.block.BlockStorage;
import io.github.scuba10steve.ezstorage.block.BlockStorageCore;
import io.github.scuba10steve.ezstorage.block.BlockSuperStorage;
import io.github.scuba10steve.ezstorage.block.BlockUltraStorage;
import io.github.scuba10steve.ezstorage.block.EZBlock;
import io.github.scuba10steve.ezstorage.config.EZConfig;
import io.github.scuba10steve.ezstorage.ref.RefStrings;
import io.github.scuba10steve.ezstorage.registry.IRegistryBlock;
import io.github.scuba10steve.ezstorage.registry.RegistryHelper;
import io.github.scuba10steve.ezstorage.tileentity.TileEntityEjectPort;
import io.github.scuba10steve.ezstorage.tileentity.TileEntityExtractPort;
import io.github.scuba10steve.ezstorage.tileentity.TileEntityInputPort;
import io.github.scuba10steve.ezstorage.tileentity.TileEntitySecurityBox;
import io.github.scuba10steve.ezstorage.tileentity.TileEntityStorageCore;
import io.github.scuba10steve.ezstorage.util.JointList;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Mod blocks */
public class EZBlocks {

	private static JointList<IRegistryBlock> blocks;

	public static void mainRegistry() {
		blocks = new JointList();
		init();
		register();
	}

	public static EZBlock blank_box;
	public static EZBlock storage_core;
	public static EZBlock storage_box;
	public static EZBlock condensed_storage_box;
	public static EZBlock super_storage_box;
	public static EZBlock ultra_storage_box;
	public static EZBlock hyper_storage_box;
	public static EZBlock input_port;
	public static EZBlock output_port;
	public static EZBlock extract_port;
	public static EZBlock crafting_box;
	public static EZBlock search_box;
	public static EZBlock sort_box;
	public static EZBlock access_terminal;
	public static EZBlock security_box;

	private static void init() {
		blocks.join(blank_box = new BlockBlankBox(), storage_core = new BlockStorageCore(), storage_box = new BlockStorage(),
				condensed_storage_box = new BlockCondensedStorage(), super_storage_box = new BlockSuperStorage(),
				ultra_storage_box = new BlockUltraStorage(), hyper_storage_box = new BlockHyperStorage(), input_port = new BlockInputPort(),
				output_port = new BlockEjectPort(), extract_port = new BlockExtractPort(), crafting_box = new BlockCraftingBox(),
				search_box = new BlockSearchBox(), sort_box = new BlockSortBox(), access_terminal = new BlockAccessTerminal(),
				security_box = new BlockSecurityBox());
		if (!EZConfig.enableTerminal)
			blocks.remove(access_terminal); // terminal disabled
		if (!EZConfig.enableSecurity)
			blocks.remove(security_box); // security disabled
	}

	/** Register the blocks and tile entities */
	private static void register() {
		RegistryHelper.registerBlocks(blocks);
		GameRegistry.registerTileEntity(TileEntityStorageCore.class, RefStrings.MODID + ":TileEntityStorageCore");
		GameRegistry.registerTileEntity(TileEntityInputPort.class, RefStrings.MODID + ":TileEntityInputPort");
		GameRegistry.registerTileEntity(TileEntityEjectPort.class, RefStrings.MODID + ":TileEntityOutputPort");
		GameRegistry.registerTileEntity(TileEntityExtractPort.class, RefStrings.MODID + ":TileEntityExtractPort");
		GameRegistry.registerTileEntity(TileEntitySecurityBox.class, RefStrings.MODID + ":TileEntitySecurityBox");
	}

	/** Register model information */
	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		for (IRegistryBlock block : blocks) {
			block.registerRender();
		}
	}
}
