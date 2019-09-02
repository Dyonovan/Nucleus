package com.teambr.nucleus.manager;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class Config {

    private static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ConfigDev CONFIGDEV = new ConfigDev(BUILDER);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static class ConfigDev {
        public final ForgeConfigSpec.ConfigValue<Boolean> debug;

        public ConfigDev(ForgeConfigSpec.Builder builder) {
            builder.push("Development Settings");
            debug = builder
                    .comment("Enable debug mode")
                    .worldRestart()
                    .define("debug_mode", false);
            builder.pop();
        }
    }
}
