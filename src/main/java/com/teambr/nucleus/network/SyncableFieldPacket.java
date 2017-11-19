package com.teambr.nucleus.network;

import com.teambr.nucleus.common.tiles.Syncable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/8/2017
 */
public class SyncableFieldPacket implements IMessage, IMessageHandler<SyncableFieldPacket, IMessage> {

    public boolean returnValue;
    public int id;
    public double value;
    public BlockPos blockPosition;

    /**
     * Stub to allow registration
     */
    public SyncableFieldPacket() {}

    /**
     * Creates the packet
     * @param ping Send value back
     * @param i The field id
     * @param v The value
     * @param pos The position to write
     */
    public SyncableFieldPacket(boolean ping, int i, double v, BlockPos pos) {
        super();
        returnValue = ping;
        id = i;
        value = v;
        blockPosition = pos;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void fromBytes(ByteBuf buf) {
    returnValue = buf.readBoolean();
    id = buf.readInt();
    value = buf.readDouble();
    blockPosition = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(returnValue);
        buf.writeInt(id);
        buf.writeDouble(value);
        buf.writeLong(blockPosition.toLong());
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    @SuppressWarnings("ConstantConditions")
    @Override
    public IMessage onMessage(SyncableFieldPacket message, MessageContext ctx) {
        if(ctx.side.isServer()) {
            World world = ctx.getServerHandler().player.world;

            if(world.getTileEntity(message.blockPosition) == null)
                return null;
            else if(!(world.getTileEntity(message.blockPosition) instanceof Syncable))
                return null;

            if(message.returnValue)
                PacketManager.net.sendToAllAround(new SyncableFieldPacket(false, message.id,
                        ((Syncable)world.getTileEntity(message.blockPosition)).getVariable(message.id), message.blockPosition),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(),
                                message.blockPosition.getX(), message.blockPosition.getY(), message.blockPosition.getZ(), 25));
            else {
                ((Syncable)world.getTileEntity(message.blockPosition)).setVariable(message.id, message.value);
            }
        } else {
           onClient(message, ctx);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    private void onClient(SyncableFieldPacket message, MessageContext ctx) {
        World world = Minecraft.getMinecraft().world;

        if(world == null || message.blockPosition == null)
            return;

        if(world.getTileEntity(message.blockPosition) == null)
            return;
        else if(!(world.getTileEntity(message.blockPosition) instanceof Syncable))
            return;

        if(message.returnValue)
            PacketManager.net.sendToServer(new SyncableFieldPacket(false, message.id,
                    ((Syncable)world.getTileEntity(message.blockPosition)).getVariable(message.id), message.blockPosition));
        else
            ((Syncable)world.getTileEntity(message.blockPosition)).setVariable(message.id, message.value);
    }
}
