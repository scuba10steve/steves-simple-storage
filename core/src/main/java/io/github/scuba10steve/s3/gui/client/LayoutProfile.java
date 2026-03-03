package io.github.scuba10steve.s3.gui.client;

import net.minecraft.resources.ResourceLocation;

/**
 * Immutable bundle of layout-dependent GUI parameters.
 * Each storage screen provides a normal and an extended profile.
 */
public record LayoutProfile(
    ResourceLocation texture,
    int imageWidth,
    int imageHeight,
    int textureSheetHeight,
    int storageRows,
    int storageAreaHeight,
    int inventoryLabelY,
    int playerInvY,
    int hotbarY
) {}
