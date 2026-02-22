package io.github.scuba10steve.s3.jei;

import io.github.scuba10steve.s3.gui.client.StorageCoreCraftingScreen;
import io.github.scuba10steve.s3.gui.client.StorageCoreScreen;
import io.github.scuba10steve.s3.gui.server.StorageCoreCraftingMenu;
import io.github.scuba10steve.s3.init.ModMenuTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class StorageJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("s3", "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // Register handlers for JEI recipe/usage lookups (R/U keys) on storage items
        registration.addGuiContainerHandler(
            StorageCoreScreen.class,
            new StorageGuiContainerHandler<>()
        );

        registration.addGuiContainerHandler(
            StorageCoreCraftingScreen.class,
            new StorageGuiContainerHandler<>()
        );
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        // Basic recipe transfer for crafting menu
        registration.addRecipeTransferHandler(
            StorageCoreCraftingMenu.class,
            ModMenuTypes.STORAGE_CORE_CRAFTING.get(),
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
