package com.teambr.nucleus.common.tiles.nbt.java;

import com.teambr.nucleus.common.tiles.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class IntegerNBTHandler implements INBTHandler<Integer> {

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return int.class.isAssignableFrom(aClass) || Integer.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull Integer object) {
        compound.setInteger(name, object);
        return true;
    }

    @Override
    public Integer readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name) {
        return compound.hasKey(name) ? compound.getInteger(name) : null;
    }
}
