package io.github.scuba10steve.s3.gui.server;

import io.github.scuba10steve.s3.blockentity.StorageCoreBlockEntity;
import io.github.scuba10steve.s3.network.StorageSyncPacket;
import io.github.scuba10steve.s3.platform.S3Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class StatisticsBoxMenu extends AbstractContainerMenu {

    private final StorageCoreBlockEntity core;
    private final BlockPos pos;

    // Client constructor
    public StatisticsBoxMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, getCore(playerInventory, data.readBlockPos()));
    }

    // Server constructor
    public StatisticsBoxMenu(int containerId, Inventory playerInventory, StorageCoreBlockEntity core) {
        super(S3Platform.getStatisticsBoxMenuType(), containerId);
        this.core = core;
        this.pos = core.getBlockPos();

        // Send sync packet so client has current stats
        if (playerInventory.player instanceof ServerPlayer serverPlayer) {
            S3Platform.getNetworkHelper().sendToPlayer(
                serverPlayer,
                new StorageSyncPacket(pos, core.getInventory().getStoredItems(),
                    core.getInventory().getMaxItems(), core.hasSearchBox(), core.hasSortBox(),
                    core.getSortMode().ordinal(), core.hasStatisticsBox(),
                    core.getTierBreakdown(), core.getTotalBlockCount())
            );
        }
    }

    private static StorageCoreBlockEntity getCore(Inventory playerInventory, BlockPos pos) {
        if (playerInventory.player.level().getBlockEntity(pos) instanceof StorageCoreBlockEntity core) {
            return core;
        }
        throw new IllegalStateException("No StorageCoreBlockEntity at " + pos);
    }

    public StorageCoreBlockEntity getCore() {
        return core;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }
}
