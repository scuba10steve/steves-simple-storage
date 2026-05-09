package io.github.scuba10steve.s3.compat;

import io.github.scuba10steve.s3.ref.RefStrings;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * Optional compatibility with Patchouli. Gifts the S3 guidebook to each player the first time
 * they log in while Patchouli is loaded.
 *
 * Registered only when Patchouli is loaded (mod id: "patchouli").
 */
public class PatchouliCompat {

    private static final String GIFT_RECEIVED_KEY = "s3_received_guidebook";
    private static final ResourceLocation GUIDEBOOK_ID = ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, "guidebook");

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PatchouliCompat::onPlayerLogin);
    }

    private static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        if (player.getPersistentData().getBoolean(GIFT_RECEIVED_KEY)) {
            return;
        }

        player.addItem(PatchouliAPI.get().getBookStack(GUIDEBOOK_ID));
        player.sendSystemMessage(Component.literal("Simple Steve has left you a gift! You should check your inventory..."));
        player.getPersistentData().putBoolean(GIFT_RECEIVED_KEY, true);
    }
}
