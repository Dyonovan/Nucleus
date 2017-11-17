package com.teambr.nucleus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Buuz135
 * @since 11/17/2018
 * <p>
 * Add this annotation over the tile entity object to have it registered
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RegisteringTileEntity {

    /**
     * Defined name for the registed tile.
     *
     * @return The name of the tile.
     */
    String name();

    /**
     * Used to scan the tile class for {@link NBTSave} annotation to be stored automatically.
     *
     * @return true if it should be scanned.
     */
    boolean scanForNBTStorage() default false;
}
