package com.teambr.nucleus.common.tiles.nbt.data;

import com.teambr.nucleus.common.tiles.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class BlockPosNBTHandler implements INBTHandler<BlockPos> {

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return BlockPos.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull BlockPos object) {
        compound.setLong(name, object.toLong());
        return false;
    }

    @Override
    public BlockPos readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name) {
        return compound.hasKey(name) ? BlockPos.fromLong(compound.getLong(name)) : null;
    }
}
