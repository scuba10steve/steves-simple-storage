package io.github.scuba10steve.ezstorage.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import io.github.scuba10steve.ezstorage.gui.client.GuiCraftingCore;
import io.github.scuba10steve.ezstorage.gui.client.GuiExtractPort;
import io.github.scuba10steve.ezstorage.gui.client.GuiSecurityBox;
import io.github.scuba10steve.ezstorage.gui.client.GuiStorageCore;
import io.github.scuba10steve.ezstorage.gui.server.ContainerExtractPort;
import io.github.scuba10steve.ezstorage.gui.server.ContainerSecurityBox;
import io.github.scuba10steve.ezstorage.gui.server.ContainerStorageCore;
import io.github.scuba10steve.ezstorage.gui.server.ContainerStorageCoreCrafting;
import io.github.scuba10steve.ezstorage.tileentity.TileEntityExtractPort;
import io.github.scuba10steve.ezstorage.tileentity.TileEntitySecurityBox;

/** The mod gui handler */
public class GuiHandler implements IGuiHandler {

	public static final int STORAGE = 1;
	public static final int CRAFTING = 2;
	public static final int EXTRACT = 3;
	public static final int SECURITY = 4;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if (tileEntity != null) {
			switch (ID) {
			case STORAGE:
				return new ContainerStorageCore(player, world, x, y, z);
			case CRAFTING:
				return new ContainerStorageCoreCrafting(player, world, x, y, z);
			case EXTRACT:
				return new ContainerExtractPort(player.inventory, (TileEntityExtractPort) tileEntity);
			case SECURITY:
				return new ContainerSecurityBox(player.inventory, (TileEntitySecurityBox) tileEntity);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if (tileEntity != null) {
			switch (ID) {
			case STORAGE:
				return new GuiStorageCore(player, world, x, y, z);
			case CRAFTING:
				return new GuiCraftingCore(player, world, x, y, z);
			case EXTRACT:
				return new GuiExtractPort(player.inventory, (TileEntityExtractPort) tileEntity, new BlockPos(x, y, z));
			case SECURITY:
				return new GuiSecurityBox(player.inventory, (TileEntitySecurityBox) tileEntity, new BlockPos(x, y, z));
			}
		}
		return null;
	}

}
