package com.pauljoda.nucleus.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
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
    public CompoundTag tag;

    /**
     * Stub
     */
    public ClientOverridePacket() {}

    /**
     * Creates a packet with the given info
     * @param pos The position to write the tag
     * @param nbt The tag to write
     */
    public ClientOverridePacket(BlockPos pos, CompoundTag nbt) {
        super();
        blockPosition = pos;
        tag = nbt;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void decode(FriendlyByteBuf buf) {
        blockPosition = BlockPos.of(buf.readLong());
        tag = new FriendlyByteBuf(buf).readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(blockPosition.asLong());
        buf.writeNbt(tag);
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(ClientOverridePacket message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                if(message.tag != null) {
                    Level world = Objects.requireNonNull(ctx.get().getSender()).level;
                    if(world.getBlockEntity(message.blockPosition) != null) {
                        Objects.requireNonNull(world.getBlockEntity(message.blockPosition)).load(message.tag);
                        world.sendBlockUpdated(message.blockPosition,
                                world.getBlockState(message.blockPosition), world.getBlockState(message.blockPosition),
                                3);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
