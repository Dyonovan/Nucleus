package com.teambr.nucleus.common.tiles.nbt;

import com.teambr.nucleus.annotation.NBTSave;
import com.teambr.nucleus.common.tiles.nbt.java.IntegerNBTHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NBTManager {

    private static NBTManager ourInstance = new NBTManager();
    private List<INBTHandler> handlerList;
    private HashMap<Class<? extends TileEntity>, List<Field>> tileFieldList;

    private NBTManager() {
        handlerList = new ArrayList<>();
        tileFieldList = new HashMap<>();
        handlerList.add(new IntegerNBTHandler());
    }

    public static NBTManager getInstance() {
        return ourInstance;
    }

    /**
     * Scans a {@link TileEntity} class for {@link NBTSave}.
     *
     * @param entity The TileEntity class
     */
    public void scanTileClassForAnnotations(Class<? extends TileEntity> entity) {
        List<Field> fields = new ArrayList<>();
        for (Field field : entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(NBTSave.class)) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        for (Field field : entity.getFields()) {
            if (field.isAnnotationPresent(NBTSave.class)) fields.add(field);
        }
        if (!fields.isEmpty()) tileFieldList.put(entity, fields);
    }

    /**
     * Writes all the values that have {@link NBTSave} annotation to the NBTTagCompound.
     *
     * @param entity   The tile entity instance.
     * @param compound The NBTTagCompound to save the values.
     * @return the modified NBTTagCompound.
     */
    public NBTTagCompound writeTileEntity(TileEntity entity, NBTTagCompound compound) {
        if (tileFieldList.containsKey(entity.getClass())) {
            for (Field field : tileFieldList.get(entity.getClass())) {
                NBTSave save = field.getAnnotation(NBTSave.class);
                try {
                    compound = handleNBTWrite(compound, save.name().isEmpty() ? field.getName() : save.name(), field.get(entity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return compound;
    }

    /**
     * Reads all the values from the NBTTagCompound and saves it to the Fields.
     *
     * @param entity   The tile entity instance.
     * @param compound The NBTTagCompound to save the values.
     */
    public void readTileEntity(TileEntity entity, NBTTagCompound compound) {
        if (tileFieldList.containsKey(entity.getClass())) {
            for (Field field : tileFieldList.get(entity.getClass())) {
                NBTSave save = field.getAnnotation(NBTSave.class);
                try {
                    Object value = handleNBTRead(compound, save.name().isEmpty() ? field.getName() : save.name(), field.get(entity));
                    field.set(entity, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks for a {@link INBTHandler} that can handle the write of the value and writes it to the NBTTagCompound.
     *
     * @param compound The NBTTagCompound to write the value.
     * @param name     The name of the tag for the NBTTagCompound.
     * @param value    The value to store in the NBTTagCompound.
     * @return the modified NBTTagCompound.
     */
    private NBTTagCompound handleNBTWrite(NBTTagCompound compound, String name, Object value) {
        for (INBTHandler handler : handlerList) {
            if (handler.isClassValid(value.getClass())) {
                if (handler.storeToNBT(compound, name, value)) return compound;
            }
        }
        return compound;
    }

    /**
     * Checks for a {@link INBTHandler} that can handle the read of the value from the NBTTagCompound.
     *
     * @param compound The NBTTagCompound to read the value.
     * @param name     The name of the tag for the NBTTagCompound.
     * @param value    The current value.
     * @return
     */
    private Object handleNBTRead(NBTTagCompound compound, String name, Object value) {
        for (INBTHandler handler : handlerList) {
            if (handler.isClassValid(value.getClass())) {
                Object readedValue = handler.readFromNBT(compound, name);
                if (readedValue != null) {
                    return readedValue;
                }
            }
        }
        return value;
    }
}
