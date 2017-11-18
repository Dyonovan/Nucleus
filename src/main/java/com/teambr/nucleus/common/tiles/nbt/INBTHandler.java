package com.teambr.nucleus.common.tiles.nbt;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

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
 */
public interface INBTHandler<T> {

    /**
     * Checks if the NBTHanlder can handle a class.
     *
     * @param aClass The class that wants to be checked.
     * @return true if the handler can handle the class or false if it can't.
     */
    boolean isClassValid(Class<?> aClass);

    /**
     * Stores a value as the given name in the NBT.
     *
     * @param compound The NBT where the object needs to be stored.
     * @param name     The name as it will be stored.
     * @param object   The object value to be stored.
     * @return true if the Object was successfully stored in the NBT
     */
    boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull T object);

    /**
     * Reads the value from the NBT to be stored in the Field.
     *
     * @param compound The NBT that stores all the information.
     * @param name     The name of the object stored in the NBT.
     * @return The object if it was successfully stored or null if it wasn't giving the next handlers a chance to store the value.
     */
    T readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name);
}
