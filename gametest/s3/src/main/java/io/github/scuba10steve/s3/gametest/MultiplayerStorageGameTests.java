package io.github.scuba10steve.s3.gametest;

import io.github.scuba10steve.s3.blockentity.StorageCoreBlockEntity;
import io.github.scuba10steve.s3.gui.server.StorageCoreCraftingMenu;
import io.github.scuba10steve.s3.storage.StorageInventory;
import io.github.scuba10steve.s3.storage.StoredItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
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
}
