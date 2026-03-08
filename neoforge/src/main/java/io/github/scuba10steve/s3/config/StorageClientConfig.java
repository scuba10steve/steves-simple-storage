package io.github.scuba10steve.s3.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class StorageClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue COUNT_FONT_SCALE;
    public static final ModConfigSpec.BooleanValue EXTENDED_GUI;
    public static final ModConfigSpec.BooleanValue SEARCH_AUTO_FOCUS;

    static {
        BUILDER.comment("GUI Settings").push("gui");

        COUNT_FONT_SCALE = BUILDER
            .comment("Scale of the item count overlay text in the storage grid (smaller = more compact)")
            .defineInRange("countFontScale", 0.8, 0.5, 1.0);

        EXTENDED_GUI = BUILDER
            .comment("Use extended (larger) GUI layout for storage screens, showing more inventory rows")
            .define("extendedGui", false);

        SEARCH_AUTO_FOCUS = BUILDER
            .comment("Automatically focus the search box when opening a storage GUI with a Search Box attached")
            .define("searchAutoFocus", false);

        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}
