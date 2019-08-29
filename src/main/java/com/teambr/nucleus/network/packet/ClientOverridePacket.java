package com.teambr.nucleus.network.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
public class ClientOverridePacket implements INetworkMessage {

    // Variables
    public BlockPos blockPosition;
    public CompoundNBT tag;

    /**
     * Stub
     */
    public ClientOverridePacket() {}

    /**
     * Creates a packet with the given info
     * @param pos The position to write the tag
     * @param nbt The tag to write
     */
    public ClientOverridePacket(BlockPos pos, CompoundNBT nbt) {
        super();
        blockPosition = pos;
        tag = nbt;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void decode(PacketBuffer buf) {
        blockPosition = BlockPos.fromLong(buf.readLong());
        tag = new PacketBuffer(buf).readCompoundTag();
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeLong(blockPosition.toLong());
        buf.writeCompoundTag(tag);
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(ClientOverridePacket message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                if(message.tag != null) {
                    World world = ctx.get().getSender().world;
                    if(world.getTileEntity(message.blockPosition) != null) {
                        world.getTileEntity(message.blockPosition).setPos(message.blockPosition);
                        world.getTileEntity(message.blockPosition).read(message.tag);
                        world.notifyBlockUpdate(message.blockPosition,
                                world.getBlockState(message.blockPosition), world.getBlockState(message.blockPosition),
                                3);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
