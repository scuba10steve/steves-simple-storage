package io.github.scuba10steve.s3.init;

import io.github.scuba10steve.s3.criteria.GuidebookTrigger;
import io.github.scuba10steve.s3.ref.RefStrings;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, RefStrings.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, GuidebookTrigger> GUIDEBOOK =
            TRIGGERS.register("receive_guidebook", GuidebookTrigger::new);

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }
}
