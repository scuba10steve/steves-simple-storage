package io.github.scuba10steve.s3.gui.client;

import io.github.scuba10steve.s3.gui.server.StorageCoreCraftingMenu;
import io.github.scuba10steve.s3.network.ClearCraftingGridPacket;
import io.github.scuba10steve.s3.platform.S3Platform;
import io.github.scuba10steve.s3.ref.RefStrings;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageCoreCraftingScreen extends AbstractStorageScreen<StorageCoreCraftingMenu> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageCoreCraftingScreen.class);

    private static final ResourceLocation NORMAL_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/storage_crafting_gui.png");
    private static final ResourceLocation EXTENDED_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/storage_crafting_gui_extended.png");

    // Normal-mode positions for crafting slots (used as base for delta calculation)
    private static final int NORMAL_CRAFTING_GRID_Y = 99;
    private static final int NORMAL_RESULT_SLOT_Y = 117;

    public StorageCoreCraftingScreen(StorageCoreCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, NORMAL_TEXTURE);

        this.extended = S3Platform.getConfig().isExtendedGui();

        this.normalLayout = new LayoutProfile(
            NORMAL_TEXTURE,
            256,  // imageWidth
            256,  // imageHeight
            256,  // textureSheetHeight
            4,    // storageRows
            72,   // storageAreaHeight (4 * 18)
            151,  // inventoryLabelY
            162,  // playerInvY
            220   // hotbarY
        );

        this.extendedLayout = new LayoutProfile(
            EXTENDED_TEXTURE,
            256,  // imageWidth
            290,  // imageHeight
            320,  // textureSheetHeight (256x320 PNG)
            6,    // storageRows
            108,  // storageAreaHeight (6 * 18)
            186,  // inventoryLabelY (151 + 35)
            197,  // playerInvY (162 + 35)
            255   // hotbarY (220 + 35)
        );

        applyCurrentLayout();

        LOGGER.debug("StorageCoreCraftingScreen created");
    }

    @Override
    protected void init() {
        super.init();

        int delta = extended ? (extendedLayout.playerInvY() - normalLayout.playerInvY()) : 0;

        Button clearButton = Button.builder(
                Component.translatable("gui.s3.clear"),
                btn -> S3Platform.getNetworkHelper().sendToServer(new ClearCraftingGridPacket(menu.getPos()))
            )
            .bounds(this.leftPos + 108, this.topPos + 137 + delta, 30, 12)
            .build();
        this.addRenderableWidget(clearButton);
    }

    @Override
    protected void repositionSlots(LayoutProfile layout) {
        super.repositionSlots(layout);

        int delta = layout.playerInvY() - normalLayout.playerInvY();

        for (Slot slot : this.menu.slots) {
            if (slot instanceof ResultSlot) {
                slot.x = 116;
                slot.y = NORMAL_RESULT_SLOT_Y + delta;
            } else if (slot.container instanceof CraftingContainer) {
                int gridIndex = slot.getContainerSlot();
                int row = gridIndex / 3;
                int col = gridIndex % 3;
                slot.x = 44 + col * 18;
                slot.y = NORMAL_CRAFTING_GRID_Y + row * 18 + delta;
            }
        }
    }
}
