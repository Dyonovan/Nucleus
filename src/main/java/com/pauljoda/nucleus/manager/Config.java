package com.pauljoda.nucleus.manager;

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

    public static class Server {
        public ForgeConfigSpec.BooleanValue debug;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Development settings")
                    .push("dev");

            debug = builder
                    .comment("Enable debug mode")
                    .worldRestart()
                    .define("debug_mode", false);
        }
    }

    public static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}
