package io.github.scuba10steve.s3.compat;

import io.github.scuba10steve.s3.config.StorageConfig;
import io.github.scuba10steve.s3.init.ModCriteriaTriggers;
import io.github.scuba10steve.s3.ref.RefStrings;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * Optional compatibility with Patchouli. Gifts the S3 guidebook to each player the first time
 * they earn the advancement configured by {@code patchouliGiftAdvancement} while Patchouli is loaded.
 * <p>
 * Registered only when Patchouli is loaded (mod id: "patchouli").
 */
public class PatchouliCompat {

    private static final String GIFT_RECEIVED_KEY = "s3_received_guidebook";
    private static final ResourceLocation GUIDEBOOK_ID = ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "guidebook");

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PatchouliCompat::onPlayerLogin);
        NeoForge.EVENT_BUS.addListener(PatchouliCompat::onAdvancementEarned);
    }

    private static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ModCriteriaTriggers.GUIDEBOOK.get().trigger((ServerPlayer) event.getEntity());
    }

    private static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        String configuredAdvancement = StorageConfig.PATCHOULI_GIFT_ADVANCEMENT.get();
        if (configuredAdvancement.isEmpty()) {
            return;
        }
        if (!event.getAdvancement().id().equals(ResourceLocation.parse(configuredAdvancement))) {
            return;
        }

        Player player = event.getEntity();
        if (player.getPersistentData().getBoolean(GIFT_RECEIVED_KEY)) {
            return;
        }

        player.addItem(PatchouliAPI.get().getBookStack(GUIDEBOOK_ID));
        player.sendSystemMessage(Component.literal("Simple Steve has left you a gift! You should check your inventory..."));
        player.getPersistentData().putBoolean(GIFT_RECEIVED_KEY, true);
    }
}
