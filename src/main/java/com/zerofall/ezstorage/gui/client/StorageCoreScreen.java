package com.zerofall.ezstorage.gui.client;

import com.zerofall.ezstorage.gui.server.StorageCoreMenu;
import com.zerofall.ezstorage.ref.RefStrings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StorageCoreScreen extends AbstractContainerScreen<StorageCoreMenu> {
    
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/storage_core.png");

    public StorageCoreScreen(StorageCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 222; // Original height to accommodate 6 rows + player inventory
        this.imageWidth = 176;
        
        // Adjust label positions to prevent overlap with slots
        this.titleLabelY = 6;
        this.inventoryLabelY = this.imageHeight - 94; // Position above player inventory
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
