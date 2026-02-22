package io.github.scuba10steve.s3;

import io.github.scuba10steve.s3.config.StorageConfig;
import io.github.scuba10steve.s3.init.ModBlockEntities;
import io.github.scuba10steve.s3.init.ModBlocks;
import io.github.scuba10steve.s3.init.ModCreativeTabs;
import io.github.scuba10steve.s3.init.ModItems;
import io.github.scuba10steve.s3.init.ModMenuTypes;
import io.github.scuba10steve.s3.ref.Log;
import io.github.scuba10steve.s3.ref.RefStrings;
import io.github.scuba10steve.s3.util.StorageUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(RefStrings.MODID)
public class StevesSimpleStorage {
	public static final String MODID = RefStrings.MODID;

	public static StevesSimpleStorage instance;

	public StevesSimpleStorage(IEventBus modEventBus, ModContainer modContainer) {
		instance = this;

		// Register deferred registers
		ModBlocks.register(modEventBus);
		ModItems.register(modEventBus);
		ModBlockEntities.register(modEventBus);
		ModMenuTypes.register(modEventBus);
		ModCreativeTabs.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		modContainer.registerConfig(ModConfig.Type.COMMON, StorageConfig.SPEC);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		StorageUtils.getModNameFromID(RefStrings.MODID);
		Log.logger.info("Loading complete.");
	}
}
