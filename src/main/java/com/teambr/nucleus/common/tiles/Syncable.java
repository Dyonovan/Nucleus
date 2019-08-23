package com.teambr.nucleus.common.tiles;

import com.teambr.nucleus.network.PacketManager;
import com.teambr.nucleus.network.SyncableFieldPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * This file was created for Nucleus - Java
 * 
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public abstract class Syncable extends UpdatingTile {

    public Syncable(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to set the value of a field
     * @param id The field id
     * @param value The value of the field
     */
    public abstract void setVariable(int id, double value);

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    public abstract Double getVariable(int id);

    /*******************************************************************************************************************
     * Syncable                                                                                                        *
     *******************************************************************************************************************/

    /**
     * Sends the value to the server, you should probably only call this from the client
     */
    public void sendValueToServer(int id, double value) {
        PacketManager.INSTANCE.sendToServer(new SyncableFieldPacket(false, id, value, getPos()));
    }

    /**
     * Will get the value from the server and set it to our current value, call from client
     * Only use if you lose data and want to update from server. Few cases for this
     */
    public void updateClientValueFromServer(int id) {
        PacketManager.INSTANCE.sendToServer(new SyncableFieldPacket(true, id, 0, getPos()));
    }

    /**
     * Sends the value to the clients nearby
     */
    public void sendValueToClient(int id, double value) {
        PacketManager.INSTANCE.send(PacketDistributor.NEAR.with( () ->
                new PacketDistributor.TargetPoint(
                getPos().getX(), getPos().getY(), getPos().getZ(),
                25, getWorld().getDimension().getType())),
                new SyncableFieldPacket(false, id, value, getPos()));
    }
}
