package io.github.scuba10steve.s3.gui.client;

import io.github.scuba10steve.s3.gui.server.StatisticsBoxMenu;
import io.github.scuba10steve.s3.ref.RefStrings;
import io.github.scuba10steve.s3.storage.StorageInventory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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

        int y = 20;
        int labelColor = 0x404040;
        int valueColor = 0x202020;

        // Storage Overview header
        guiGraphics.drawString(this.font, "Storage Overview", 8, y, labelColor, false);
        y += 14;

        // Total items / capacity
        guiGraphics.drawString(this.font, "Items: " + formatCount(totalItems) + " / " + formatCount(maxCapacity),
            12, y, valueColor, false);
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
        guiGraphics.drawString(this.font, "Free Space: " + formatCount(freeSpace), 12, y, valueColor, false);
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

        // Feature boxes
        y += 4;
        if (y + 10 < imageHeight - 4) {
            guiGraphics.drawString(this.font, "Features", 8, y, labelColor, false);
            y += 12;
            StringBuilder features = new StringBuilder();
            if (inventory.hasSearchBox()) features.append("Search ");
            if (inventory.hasSortBox()) features.append("Sort ");
            if (inventory.hasStatisticsBox()) features.append("Stats ");
            if (features.isEmpty()) features.append("None");
            guiGraphics.drawString(this.font, features.toString().trim(), 12, y, valueColor, false);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private static String formatCount(long count) {
        if (count >= 1_000_000_000) return String.format("%.1fB", count / 1_000_000_000.0);
        if (count >= 1_000_000) return String.format("%.1fM", count / 1_000_000.0);
        if (count >= 1_000) return String.format("%.1fK", count / 1_000.0);
        return String.valueOf(count);
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
