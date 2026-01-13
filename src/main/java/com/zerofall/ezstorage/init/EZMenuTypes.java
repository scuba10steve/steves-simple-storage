package com.zerofall.ezstorage.init;

import com.zerofall.ezstorage.gui.server.StorageCoreMenu;
import com.zerofall.ezstorage.ref.RefStrings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EZMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = 
        DeferredRegister.create(BuiltInRegistries.MENU, RefStrings.MODID);

    public static final Supplier<MenuType<StorageCoreMenu>> STORAGE_CORE = 
        MENU_TYPES.register("storage_core", () -> 
            IMenuTypeExtension.create((windowId, inv, data) -> 
                new StorageCoreMenu(windowId, inv, data.readBlockPos())));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
