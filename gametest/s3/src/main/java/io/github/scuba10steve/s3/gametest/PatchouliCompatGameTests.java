package io.github.scuba10steve.s3.gametest;

import io.github.scuba10steve.s3.init.ModCriteriaTriggers;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("s3")
@PrefixGameTestTemplate(false)
public class PatchouliCompatGameTests {

    private static final ResourceLocation RECEIVE_GUIDEBOOK = ResourceLocation.fromNamespaceAndPath("s3", "receive_guidebook");

    @GameTest(template = "empty", setupTicks = 2)
    public static void guidebook_trigger_awards_advancement(GameTestHelper helper) {
        helper.runAfterDelay(2, () -> {
            ServerPlayer player = helper.makeMockServerPlayerInLevel();

            AdvancementHolder advancement = helper.getLevel().getServer().getAdvancements().get(RECEIVE_GUIDEBOOK);
            if (advancement == null) {
                helper.fail("s3:receive_guidebook advancement not registered");
                return;
            }

            if (player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                helper.fail("Advancement should not be done before trigger fires");
                return;
            }

            ModCriteriaTriggers.GUIDEBOOK.get().trigger(player);

            if (!player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                helper.fail("Advancement should be done after trigger fires");
                return;
            }

            helper.succeed();
        });
    }

    @GameTest(template = "empty", setupTicks = 2)
    public static void guidebook_trigger_only_awards_advancement_once(GameTestHelper helper) {
        helper.runAfterDelay(2, () -> {
            ServerPlayer player = helper.makeMockServerPlayerInLevel();

            AdvancementHolder advancement = helper.getLevel().getServer().getAdvancements().get(RECEIVE_GUIDEBOOK);
            if (advancement == null) {
                helper.fail("s3:receive_guidebook advancement not registered");
                return;
            }

            ModCriteriaTriggers.GUIDEBOOK.get().trigger(player);

            if (!player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                helper.fail("Advancement should be done after first trigger");
                return;
            }

            // Firing a second time should be a no-op — the advancement system removes
            // listeners once an advancement is complete, so the trigger cannot re-award it.
            ModCriteriaTriggers.GUIDEBOOK.get().trigger(player);

            if (!player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                helper.fail("Advancement should remain done after second trigger");
                return;
            }

            helper.succeed();
        });
    }
}
