package io.github.scuba10steve.s3.gui.client;

import io.github.scuba10steve.s3.gui.server.StatisticsBoxMenu;
import io.github.scuba10steve.s3.ref.RefStrings;
import io.github.scuba10steve.s3.storage.StorageInventory;
import io.github.scuba10steve.s3.util.CountFormatter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class StatisticsBoxScreen extends AbstractContainerScreen<StatisticsBoxMenu> {

    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "textures/gui/statistics_box.png");

    public StatisticsBoxScreen(StatisticsBoxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Title centered
        guiGraphics.drawString(this.font, this.title,
            this.imageWidth / 2 - this.font.width(this.title) / 2, 6, 0x404040, false);

        StorageInventory inventory = menu.getCore().getInventory();
        long totalItems = inventory.getTotalItemCount();
        long maxCapacity = inventory.getMaxItems();
        int uniqueTypes = inventory.getStoredItems().size();
        long freeSpace = maxCapacity - totalItems;
        int percentage = maxCapacity > 0 ? (int) (totalItems * 100 / maxCapacity) : 0;

        boolean showExact = hasShiftDown();

        int y = 20;
        int labelColor = 0x404040;
        int valueColor = 0x202020;

        // Storage Overview header
        guiGraphics.drawString(this.font, "Storage Overview", 8, y, labelColor, false);
        y += 14;

        // Total items / capacity
        String itemsText = "Items: " + formatValue(totalItems, showExact) + " / " + formatValue(maxCapacity, showExact);
        guiGraphics.drawString(this.font, itemsText, 12, y, valueColor, false);
        y += 11;

        // Progress bar (152px wide, 6px tall)
        int barX = 12;
        int barWidth = 152;
        int barHeight = 6;
        guiGraphics.fill(barX, y, barX + barWidth, y + barHeight, 0xFF555555);
        int filledWidth = maxCapacity > 0 ? (int) (barWidth * totalItems / maxCapacity) : 0;
        int barColor = percentage > 90 ? 0xFFFF4444 : percentage > 70 ? 0xFFFFAA00 : 0xFF44CC44;
        if (filledWidth > 0) {
            guiGraphics.fill(barX, y, barX + filledWidth, y + barHeight, barColor);
        }
        // Percentage text right-aligned
        String pctText = percentage + "%";
        guiGraphics.drawString(this.font, pctText,
            barX + barWidth - this.font.width(pctText), y + barHeight + 2, valueColor, false);
        y += barHeight + 14;

        // Unique types
        guiGraphics.drawString(this.font, "Unique Types: " + uniqueTypes, 12, y, valueColor, false);
        y += 11;

        // Free space
        guiGraphics.drawString(this.font, "Free Space: " + formatValue(freeSpace, showExact), 12, y, valueColor, false);
        y += 18;

        // Multiblock Composition header
        guiGraphics.drawString(this.font, "Multiblock Composition", 8, y, labelColor, false);
        y += 14;

        // Total blocks
        int totalBlocks = inventory.getTotalBlockCount();
        guiGraphics.drawString(this.font, "Total Blocks: " + totalBlocks, 12, y, valueColor, false);
        y += 11;

        // Tier breakdown
        Map<String, Integer> tiers = inventory.getTierBreakdown();
        for (Map.Entry<String, Integer> entry : tiers.entrySet()) {
            String tierName = capitalize(entry.getKey());
            guiGraphics.drawString(this.font, tierName + ": " + entry.getValue(), 16, y, valueColor, false);
            y += 10;
        }

        // Attached components as block icons
        y += 4;
        if (y + 16 < imageHeight - 4) {
            guiGraphics.drawString(this.font, "Components", 8, y, labelColor, false);
            y += 12;

            List<String> components = inventory.getPresentComponents();
            int iconX = 12;
            for (String component : components) {
                if (iconX + 16 > imageWidth - 8) break; // Don't overflow
                ItemStack icon = getItemForComponent(component);
                if (!icon.isEmpty()) {
                    guiGraphics.renderItem(icon, iconX, y);
                    iconX += 18;
                }
            }

            if (components.isEmpty()) {
                guiGraphics.drawString(this.font, "None", 12, y + 4, valueColor, false);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private static String formatValue(long count, boolean exact) {
        if (exact) {
            return CountFormatter.formatExactCount(count);
        }
        return CountFormatter.formatCount(count);
    }

    private static ItemStack getItemForComponent(String registryPath) {
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, registryPath);
        Item item = BuiltInRegistries.ITEM.get(itemId);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item);
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
