package io.github.scuba10steve.ezstorage;

import io.github.scuba10steve.ezstorage.config.EZConfig;
import io.github.scuba10steve.ezstorage.events.CoreEvents;
import io.github.scuba10steve.ezstorage.events.SecurityEvents;
import io.github.scuba10steve.ezstorage.network.EZNetwork;
import io.github.scuba10steve.ezstorage.ref.EZTab;
import io.github.scuba10steve.ezstorage.ref.Log;
import io.github.scuba10steve.ezstorage.ref.RefStrings;
import io.github.scuba10steve.ezstorage.util.EZStorageUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

/** EZStorage main mod class */
@Mod(RefStrings.MODID)
public class EZStorage {

	public static EZStorage instance;
	public static EZTab creativeTab;

	public EZStorage(IEventBus modEventBus, ModContainer modContainer) {
		instance = this;
		
		modEventBus.addListener(this::commonSetup);
		modContainer.registerConfig(ModConfig.Type.COMMON, EZConfig.SPEC);
		
		this.creativeTab = new EZTab();
		
		NeoForge.EVENT_BUS.register(new CoreEvents());
		NeoForge.EVENT_BUS.register(new SecurityEvents());
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		EZNetwork.registerNetwork();
		EZStorageUtils.getModNameFromID(RefStrings.MODID);
		Log.logger.info("Loading complete.");
	}
}
