package io.github.scuba10steve.ezstorage.init;

import io.github.scuba10steve.ezstorage.config.EZConfig;
import io.github.scuba10steve.ezstorage.item.EZItem;
import io.github.scuba10steve.ezstorage.item.ItemDolly;
import io.github.scuba10steve.ezstorage.item.ItemKey;
import io.github.scuba10steve.ezstorage.registry.IRegistryItem;
import io.github.scuba10steve.ezstorage.registry.RegistryHelper;
import io.github.scuba10steve.ezstorage.util.JointList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Mod items */
public class EZItems {

	private static JointList<IRegistryItem> items;

	public static void mainRegistry() {
		items = new JointList();
		init();
		register();
	}

	public static EZItem key;
	public static EZItem dolly_basic;
	public static EZItem dolly_super;

	private static void init() {
		items.join(
			key = new ItemKey(),
			dolly_basic = new ItemDolly(6, "dolly"),
			dolly_super = new ItemDolly(16, "dolly_super")
		);
		if(!EZConfig.enableSecurity) items.remove(key); // security disabled
		if(!EZConfig.enableDolly) {
			items.remove(dolly_basic); // dollies disabled
			items.remove(dolly_super);
		}
	}

	private static void register() {
		RegistryHelper.registerItems(items);
	}

	/** Register model information */
	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		for (IRegistryItem item : items) {
			item.registerRender();
		}
	}

}
