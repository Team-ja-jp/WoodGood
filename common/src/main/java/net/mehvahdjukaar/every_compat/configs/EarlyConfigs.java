package net.mehvahdjukaar.every_compat.configs;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

//loaded before registry
public class EarlyConfigs {

    public static ConfigSpec SPEC;

    public static Supplier<Boolean> TAB_ENABLED;
    public static Supplier<Boolean> REMAP_COMPAT;
    public static Supplier<Boolean> REMAP_OWN;
    public static Supplier<Boolean> DEPEND_ON_PACKS;
    public static Supplier<Boolean> DEBUG_RESOURCES;

    public static void init() {
        ConfigBuilder builder = ConfigBuilder.create(EveryCompat.res("registry"), ConfigType.COMMON);

        builder.push("general");
        TAB_ENABLED = builder.comment("Puts all the added items into a new Every Compat tab instead of their own mod tabs. Be warned that if disabled it could cause some issue with some mods that have custom tabs")
                .define("creative_tab", true);
        REMAP_COMPAT = builder.comment("Allows the mod to try to remap and convert other blocks and items from other compat mods that have been uninstalled from one world. This was made so one can uninstall such mods seamlessly having their blocks converted into Evety Compat counterparts")
                .define("remap_other_mods", false);
        REMAP_OWN = builder.comment("Clears out and remaps all blocks registered by this mod belonging to uninstalled wood types to air or oak wood")
                .define("remap_self", true);
        DEPEND_ON_PACKS = builder.comment("Makes dynamic assets that are generated depend on loaded resource packs. Turn off to make them just use vanilla assets")
                .define("assets_depend_on_loaded_packs", true);
        DEBUG_RESOURCES = builder.comment("Creates a debug folder inside your instance directory where all the dynamically generated resources will be saved")
                        .define("debug_resources",true);
        builder.pop();

        SPEC = builder.buildAndRegister();

        SPEC.loadFromFile(); //manually load early
    }

}