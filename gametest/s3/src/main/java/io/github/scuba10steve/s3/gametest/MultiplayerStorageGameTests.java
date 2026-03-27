package io.github.scuba10steve.s3.gametest;

import io.github.scuba10steve.s3.blockentity.StorageCoreBlockEntity;
import io.github.scuba10steve.s3.gui.server.StorageCoreCraftingMenu;
import io.github.scuba10steve.s3.storage.StorageInventory;
import io.github.scuba10steve.s3.storage.StoredItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameTestHolder("s3")
@PrefixGameTestTemplate(false)
public class MultiplayerStorageGameTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplayerStorageGameTests.class);
    private static final BlockPos CORE_POS = new BlockPos(1, 1, 1);

    @GameTest(template = "core_with_storage_box", setupTicks = 5)
    public static void two_players_sequential_insert(GameTestHelper helper) {
        helper.runAfterDelay(5, () -> {
            StorageCoreBlockEntity core = (StorageCoreBlockEntity) helper.getBlockEntity(CORE_POS);
            if (core == null) {
                helper.fail("Storage core block entity not found");
                return;
            }

            StorageInventory inv = core.getInventory();

            helper.makeMockServerPlayerInLevel();
            core.insertItem(new ItemStack(Items.DIAMOND, 32));

            helper.makeMockServerPlayerInLevel();
            core.insertItem(new ItemStack(Items.EMERALD, 16));

            long totalCount = inv.getTotalItemCount();
            if (totalCount != 48) {
                helper.fail("Expected total item count 48, got " + totalCount);
                return;
            }

            int distinctTypes = inv.getStoredItems().size();
            if (distinctTypes != 2) {
                helper.fail("Expected 2 distinct item types, got " + distinctTypes);
                return;
            }

            LOGGER.info("two_players_sequential_insert: PASSED");
            helper.succeed();
        });
    }

    @GameTest(template = "core_with_storage_box", setupTicks = 5)
    public static void two_players_sequential_extract(GameTestHelper helper) {
        helper.runAfterDelay(5, () -> {
            StorageCoreBlockEntity core = (StorageCoreBlockEntity) helper.getBlockEntity(CORE_POS);
            if (core == null) {
                helper.fail("Storage core block entity not found");
                return;
            }

            StorageInventory inv = core.getInventory();

            // Pre-fill
            core.insertItem(new ItemStack(Items.DIAMOND, 64));
            core.insertItem(new ItemStack(Items.EMERALD, 64));

            long preFillTotal = inv.getTotalItemCount();
            if (preFillTotal != 128) {
                helper.fail("Expected pre-fill total 128, got " + preFillTotal);
                return;
            }

            helper.makeMockServerPlayerInLevel();
            ItemStack extractedA = core.extractItem(new ItemStack(Items.DIAMOND, 1), 10);
            if (extractedA.getCount() != 10) {
                helper.fail("Player A: expected to extract 10 diamonds, got " + extractedA.getCount());
                return;
            }

            helper.makeMockServerPlayerInLevel();
            ItemStack extractedB = core.extractItem(new ItemStack(Items.EMERALD, 1), 5);
            if (extractedB.getCount() != 5) {
                helper.fail("Player B: expected to extract 5 emeralds, got " + extractedB.getCount());
                return;
            }

            long finalTotal = inv.getTotalItemCount();
            if (finalTotal != 113) {
                helper.fail("Expected final total 113, got " + finalTotal);
                return;
            }

            long remainingDiamonds = inv.getStoredItems().stream()
                .filter(s -> s.getItemStack().getItem() == Items.DIAMOND)
                .mapToLong(StoredItemStack::getCount)
                .sum();
            if (remainingDiamonds != 54) {
                helper.fail("Expected 54 remaining diamonds, got " + remainingDiamonds);
                return;
            }

            long remainingEmeralds = inv.getStoredItems().stream()
                .filter(s -> s.getItemStack().getItem() == Items.EMERALD)
                .mapToLong(StoredItemStack::getCount)
                .sum();
            if (remainingEmeralds != 59) {
                helper.fail("Expected 59 remaining emeralds, got " + remainingEmeralds);
                return;
            }

            LOGGER.info("two_players_sequential_extract: PASSED");
            helper.succeed();
        });
    }

    @GameTest(template = "core_with_storage_box", setupTicks = 5)
    public static void insert_while_full_second_player_rejected(GameTestHelper helper) {
        helper.runAfterDelay(5, () -> {
            StorageCoreBlockEntity core = (StorageCoreBlockEntity) helper.getBlockEntity(CORE_POS);
            if (core == null) {
                helper.fail("Storage core block entity not found");
                return;
            }

            StorageInventory inv = core.getInventory();
            long capacity = inv.getMaxItems();

            helper.makeMockServerPlayerInLevel();
            bulkInsert(inv, Items.STONE, capacity);

            long totalAfterFill = inv.getTotalItemCount();
            if (totalAfterFill != capacity) {
                helper.fail("Expected storage full at " + capacity + ", got " + totalAfterFill);
                return;
            }

            helper.makeMockServerPlayerInLevel();
            ItemStack playerBRemainder = core.insertItem(new ItemStack(Items.DIAMOND, 1));

            if (playerBRemainder.getCount() != 1) {
                helper.fail("Player B: expected remainder count 1, got " + playerBRemainder.getCount());
                return;
            }
            if (playerBRemainder.getItem() != Items.DIAMOND) {
                helper.fail("Player B: expected remainder item DIAMOND, got " + playerBRemainder.getItem());
                return;
            }

            long totalAfterRejection = inv.getTotalItemCount();
            if (totalAfterRejection != capacity) {
                helper.fail("Expected storage count unchanged at " + capacity + ", got " + totalAfterRejection);
                return;
            }

            LOGGER.info("insert_while_full_second_player_rejected: PASSED");
            helper.succeed();
        });
    }

    @GameTest(template = "core_with_storage_box", setupTicks = 5)
    public static void crafting_menu_open_while_second_player_inserts(GameTestHelper helper) {
        helper.runAfterDelay(5, () -> {
            StorageCoreBlockEntity core = (StorageCoreBlockEntity) helper.getBlockEntity(CORE_POS);
            if (core == null) {
                helper.fail("Storage core block entity not found");
                return;
            }

            StorageInventory inv = core.getInventory();
            long initialCount = inv.getTotalItemCount();

            ServerPlayer playerA = helper.makeMockServerPlayerInLevel();
            BlockPos absPos = helper.absolutePos(CORE_POS);
            StorageCoreCraftingMenu menuA = new StorageCoreCraftingMenu(0, playerA.getInventory(), absPos);

            menuA.getSlot(1).set(new ItemStack(Items.DIAMOND, 1));
            menuA.getSlot(2).set(new ItemStack(Items.GOLD_INGOT, 1));
            menuA.getSlot(3).set(new ItemStack(Items.IRON_INGOT, 1));

            helper.makeMockServerPlayerInLevel();
            core.insertItem(new ItemStack(Items.COBBLESTONE, 10));

            long countAfterBInsert = inv.getTotalItemCount();
            if (countAfterBInsert != initialCount + 10) {
                helper.fail("Expected count " + (initialCount + 10) + " after Player B insert, got " + countAfterBInsert);
                return;
            }

            menuA.removed(playerA);

            for (int i = 1; i <= 9; i++) {
                if (!menuA.getSlot(i).getItem().isEmpty()) {
                    helper.fail("Crafting grid slot " + i + " should be empty after removed(), has " + menuA.getSlot(i).getItem());
                    return;
                }
            }

            long finalCount = inv.getTotalItemCount();
            long expectedFinal = initialCount + 10 + 3;
            if (finalCount != expectedFinal) {
                helper.fail("Expected final count " + expectedFinal + ", got " + finalCount);
                return;
            }

            LOGGER.info("crafting_menu_open_while_second_player_inserts: PASSED");
            helper.succeed();
        });
    }

    private static void bulkInsert(StorageInventory inv, Item item, long amount) {
        while (amount > 0) {
            int batch = (int) Math.min(amount, Integer.MAX_VALUE);
            inv.insertItem(new ItemStack(item, batch));
            amount -= batch;
        }
    }
}
