package io.github.scuba10steve.s3.client;

import io.github.scuba10steve.s3.gui.client.StorageCoreScreen;
import io.github.scuba10steve.s3.init.EZMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(EZMenuTypes.STORAGE_CORE.get(), StorageCoreScreen::new);
    }
}
