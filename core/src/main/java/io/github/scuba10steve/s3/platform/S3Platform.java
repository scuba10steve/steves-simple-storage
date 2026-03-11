package io.github.scuba10steve.s3.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Central holder for platform-specific references.
 * Initialized by the loader-specific module (NeoForge) during mod construction.
 */
public final class S3Platform {
    private S3Platform() {}

    // --- Config ---
    private static S3Config config;

    public static S3Config getConfig() {
        return config;
    }

    public static void setConfig(S3Config config) {
        S3Platform.config = config;
    }

    // --- Block Entity Types ---
    private static Supplier<BlockEntityType<?>> storageCoreBEType;
    private static Supplier<BlockEntityType<?>> craftingBoxBEType;
    private static Supplier<BlockEntityType<?>> searchBoxBEType;
    private static Supplier<BlockEntityType<?>> sortBoxBEType;
    private static Supplier<BlockEntityType<?>> inputPortBEType;
    private static Supplier<BlockEntityType<?>> extractPortBEType;
    private static Supplier<BlockEntityType<?>> ejectPortBEType;
    private static Supplier<BlockEntityType<?>> securityBoxBEType;
    private static Supplier<BlockEntityType<?>> storageInterfaceBEType;
    private static Supplier<BlockEntityType<?>> statisticsBoxBEType;

    public static BlockEntityType<?> getStorageCoreBEType() { return storageCoreBEType.get(); }
    public static BlockEntityType<?> getCraftingBoxBEType() { return craftingBoxBEType.get(); }
    public static BlockEntityType<?> getSearchBoxBEType() { return searchBoxBEType.get(); }
    public static BlockEntityType<?> getSortBoxBEType() { return sortBoxBEType.get(); }
    public static BlockEntityType<?> getInputPortBEType() { return inputPortBEType.get(); }
    public static BlockEntityType<?> getExtractPortBEType() { return extractPortBEType.get(); }
    public static BlockEntityType<?> getEjectPortBEType() { return ejectPortBEType.get(); }
    public static BlockEntityType<?> getSecurityBoxBEType() { return securityBoxBEType.get(); }
    public static BlockEntityType<?> getStorageInterfaceBEType() { return storageInterfaceBEType.get(); }
    public static BlockEntityType<?> getStatisticsBoxBEType() { return statisticsBoxBEType.get(); }

    public static void setBlockEntityTypes(
            Supplier<BlockEntityType<?>> storageCore,
            Supplier<BlockEntityType<?>> craftingBox,
            Supplier<BlockEntityType<?>> searchBox,
            Supplier<BlockEntityType<?>> sortBox,
            Supplier<BlockEntityType<?>> inputPort,
            Supplier<BlockEntityType<?>> extractPort,
            Supplier<BlockEntityType<?>> ejectPort,
            Supplier<BlockEntityType<?>> securityBox,
            Supplier<BlockEntityType<?>> storageInterface,
            Supplier<BlockEntityType<?>> statisticsBox) {
        storageCoreBEType = storageCore;
        craftingBoxBEType = craftingBox;
        searchBoxBEType = searchBox;
        sortBoxBEType = sortBox;
        inputPortBEType = inputPort;
        extractPortBEType = extractPort;
        ejectPortBEType = ejectPort;
        securityBoxBEType = securityBox;
        storageInterfaceBEType = storageInterface;
        statisticsBoxBEType = statisticsBox;
    }

    // --- Menu Types ---
    private static Supplier<MenuType<?>> storageCoreMenuType;
    private static Supplier<MenuType<?>> storageCraftingMenuType;
    private static Supplier<MenuType<?>> extractPortMenuType;
    private static Supplier<MenuType<?>> securityBoxMenuType;
    private static Supplier<MenuType<?>> statisticsBoxMenuType;

    public static MenuType<?> getStorageCoreMenuType() { return storageCoreMenuType.get(); }
    public static MenuType<?> getStorageCraftingMenuType() { return storageCraftingMenuType.get(); }
    public static MenuType<?> getExtractPortMenuType() { return extractPortMenuType.get(); }
    public static MenuType<?> getSecurityBoxMenuType() { return securityBoxMenuType.get(); }
    public static MenuType<?> getStatisticsBoxMenuType() { return statisticsBoxMenuType.get(); }

    public static void setMenuTypes(
            Supplier<MenuType<?>> storageCore,
            Supplier<MenuType<?>> storageCrafting,
            Supplier<MenuType<?>> extractPort,
            Supplier<MenuType<?>> securityBox,
            Supplier<MenuType<?>> statisticsBox) {
        storageCoreMenuType = storageCore;
        storageCraftingMenuType = storageCrafting;
        extractPortMenuType = extractPort;
        securityBoxMenuType = securityBox;
        statisticsBoxMenuType = statisticsBox;
    }

    // --- Items ---
    private static Supplier<Item> keyItem;

    public static Item getKeyItem() { return keyItem.get(); }

    public static void setKeyItem(Supplier<Item> key) {
        keyItem = key;
    }

    // --- Network Helper ---
    private static S3NetworkHelper networkHelper;

    public static S3NetworkHelper getNetworkHelper() { return networkHelper; }

    public static void setNetworkHelper(S3NetworkHelper helper) {
        networkHelper = helper;
    }

    // --- Crafting Recipe Lookup ---
    @FunctionalInterface
    public interface CraftingRecipeLookup {
        Optional<RecipeHolder<CraftingRecipe>> find(
                AbstractContainerMenu menu,
                CraftingInput input,
                Level level,
                Player player);
    }

    private static CraftingRecipeLookup craftingRecipeLookup =
            (menu, input, level, player) ->
                    level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);

    public static Optional<RecipeHolder<CraftingRecipe>> findCraftingRecipe(
            AbstractContainerMenu menu, CraftingInput input, Level level, Player player) {
        return craftingRecipeLookup.find(menu, input, level, player);
    }

    public static void setCraftingRecipeLookup(CraftingRecipeLookup lookup) {
        craftingRecipeLookup = lookup;
    }

    // --- Menu Opener ---
    @FunctionalInterface
    public interface MenuOpener {
        void openMenu(ServerPlayer player, MenuProvider provider, BlockPos pos);
    }

    private static MenuOpener menuOpener;

    public static void openMenu(ServerPlayer player, MenuProvider provider, BlockPos pos) {
        menuOpener.openMenu(player, provider, pos);
    }

    public static void setMenuOpener(MenuOpener opener) {
        menuOpener = opener;
    }
}
