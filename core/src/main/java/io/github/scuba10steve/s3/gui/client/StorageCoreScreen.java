package io.github.scuba10steve.s3.gui.client;

import io.github.scuba10steve.s3.gui.server.StorageCoreMenu;
import io.github.scuba10steve.s3.platform.S3Platform;
import io.github.scuba10steve.s3.ref.RefStrings;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StorageCoreScreen extends AbstractStorageScreen<StorageCoreMenu> {

    private static final ResourceLocation NORMAL_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/storage_core.png");
    private static final ResourceLocation EXTENDED_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/storage_core_extended.png");

    public StorageCoreScreen(StorageCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, NORMAL_TEXTURE);

        this.extended = S3Platform.getConfig().isExtendedGui();

        this.normalLayout = new LayoutProfile(
            NORMAL_TEXTURE,
            195,  // imageWidth
            222,  // imageHeight
            256,  // textureSheetHeight
            6,    // storageRows
            108,  // storageAreaHeight
            128,  // inventoryLabelY (222 - 94)
            140,  // playerInvY
            198   // hotbarY
        );

        this.extendedLayout = new LayoutProfile(
            EXTENDED_TEXTURE,
            195,  // imageWidth
            255,  // imageHeight
            256,  // textureSheetHeight
            7,    // storageRows
            126,  // storageAreaHeight (7 * 18)
            161,  // inventoryLabelY
            173,  // playerInvY
            231   // hotbarY
        );

        applyCurrentLayout();
    }
}
