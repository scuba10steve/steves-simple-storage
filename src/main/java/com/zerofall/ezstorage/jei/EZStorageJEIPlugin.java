package com.zerofall.ezstorage.jei;

import com.zerofall.ezstorage.gui.server.StorageCoreCraftingMenu;
import com.zerofall.ezstorage.init.EZMenuTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class EZStorageJEIPlugin implements IModPlugin {
    
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("ezstorage", "jei_plugin");
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        // Basic recipe transfer for crafting menu
        registration.addRecipeTransferHandler(
            StorageCoreCraftingMenu.class,
            EZMenuTypes.STORAGE_CORE_CRAFTING.get(),
            RecipeTypes.CRAFTING,
            1, 9,  // crafting slots: start=1, count=9
            10, 36 // inventory slots: start=10, count=36
        );
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JEIIntegration.setJeiRuntime(jeiRuntime);
    }
}
