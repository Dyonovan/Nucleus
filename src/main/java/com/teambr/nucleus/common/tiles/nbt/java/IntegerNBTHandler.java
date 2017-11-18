package com.teambr.nucleus.common.tiles.nbt.java;

import com.teambr.nucleus.common.tiles.nbt.INBTHandler;
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
public class IntegerNBTHandler implements INBTHandler<Integer> {

    /**
     * Checks if the NBTHanlder can handle a class.
     *
     * @param aClass The class that wants to be checked.
     * @return true if the handler can handle the class or false if it can't.
     */
    @Override
    public boolean isClassValid(Class<?> aClass) {
        return int.class.isAssignableFrom(aClass) || Integer.class.isAssignableFrom(aClass);
    }

    /**
     * Stores a value as the given name in the NBT.
     *
     * @param compound The NBT where the object needs to be stored.
     * @param name     The name as it will be stored.
     * @param object   The object value to be stored.
     * @return true if the Object was successfully stored in the NBT
     */
    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull Integer object) {
        compound.setInteger(name, object);
        return true;
    }

    /**
     * Reads the value from the NBT to be stored in the Field.
     *
     * @param compound The NBT that stores all the information.
     * @param name     The name of the object stored in the NBT.
     * @return The object if it was successfully stored or null if it wasn't giving the next handlers a chance to store the value.
     */
    @Override
    public Integer readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name) {
        return compound.hasKey(name) ? compound.getInteger(name) : null;
    }
}
