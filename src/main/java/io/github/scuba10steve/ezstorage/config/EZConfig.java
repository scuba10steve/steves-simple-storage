package io.github.scuba10steve.ezstorage.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class EZConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    
    // Basic configuration values
    public static final ModConfigSpec.IntValue BASIC_CAPACITY = BUILDER
        .comment("Basic storage capacity")
        .defineInRange("basicCapacity", 10000, 1, Integer.MAX_VALUE);
    
    public static final ModConfigSpec SPEC = BUILDER.build();
}
