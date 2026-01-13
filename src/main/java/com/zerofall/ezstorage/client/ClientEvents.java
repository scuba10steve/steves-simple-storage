package com.zerofall.ezstorage.client;

import com.zerofall.ezstorage.gui.client.StorageCoreScreen;
import com.zerofall.ezstorage.init.EZMenuTypes;
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
