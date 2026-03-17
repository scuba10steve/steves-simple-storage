package io.github.scuba10steve.s3.compat;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.illusivesoulworks.polymorph.api.client.widgets.PlayerRecipesWidget;
import io.github.scuba10steve.s3.gui.client.StorageCoreCraftingScreen;
import io.github.scuba10steve.s3.platform.S3Platform;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Optional compatibility with Polymorph, which resolves recipe conflicts when multiple mods
 * register recipes with the same crafting pattern. Without this integration, S3's crafting box
 * would always use whichever recipe the RecipeManager returns first, ignoring the player's choice.
 *
 * Registered only when Polymorph is loaded (mod id: "polymorph").
 */
public class PolymorphCompat {

    /**
     * Overrides S3's recipe lookup to delegate to Polymorph's player-recipe selection,
     * so that the recipe the player picked in Polymorph's UI is the one that gets crafted.
     */
    public static void register() {
        S3Platform.setCraftingRecipeLookup((menu, input, level, player) ->
                PolymorphApi.getInstance().getRecipeManager()
                        .getPlayerRecipe(menu, RecipeType.CRAFTING, input, level, player));
    }

    /**
     * Registers a Polymorph widget factory for S3's crafting screen so the conflict-selection
     * button appears when multiple recipes match the current crafting grid.
     */
    @OnlyIn(Dist.CLIENT)
    public static void registerClientWidget() {
        PolymorphWidgets.getInstance().registerWidget(screen -> {
            if (!(screen instanceof StorageCoreCraftingScreen)) {
                return null;
            }
            Slot resultSlot = PolymorphWidgets.getInstance().findResultSlot(screen);
            if (resultSlot == null) {
                return null;
            }
            return new PlayerRecipesWidget(screen, resultSlot);
        });
    }
}
