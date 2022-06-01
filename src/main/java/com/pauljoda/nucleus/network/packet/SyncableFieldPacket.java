package com.pauljoda.nucleus.network.packet;

import com.pauljoda.nucleus.Nucleus;
import com.pauljoda.nucleus.common.tiles.Syncable;
import com.pauljoda.nucleus.network.PacketManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

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
public class SyncableFieldPacket implements INetworkMessage {

    public boolean returnValue;
    public int id;
    public double value;
    public BlockPos blockPosition;

    /**
     * Stub
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
    public void decode(FriendlyByteBuf buf) {
        returnValue = buf.readBoolean();
        id = buf.readInt();
        value = buf.readDouble();
        blockPosition = BlockPos.of(buf.readLong());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(returnValue);
        buf.writeInt(id);
        buf.writeDouble(value);
        buf.writeLong(blockPosition.asLong());
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    @SuppressWarnings("ConstantConditions")
    public static void process(SyncableFieldPacket message, Supplier<NetworkEvent.Context> ctx) {
        // Start syncing
        ctx.get().enqueueWork(() -> {
            // Run when sent to server
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                Level world = ctx.get().getSender().level;

                // Safety check for non syncable tiles
                if (world.getBlockEntity(message.blockPosition) == null ||
                        !(world.getBlockEntity(message.blockPosition) instanceof Syncable))
                    return;

                // If true, other client wanted all around to see value change
                if (message.returnValue)
                    PacketManager.INSTANCE.send(PacketDistributor.NEAR.with(() ->
                                    new PacketDistributor.TargetPoint(
                                             message.blockPosition.getX(),
                                             message.blockPosition.getY(),
                                             message.blockPosition.getZ(),
                                            25,
                                            world.dimension())),
                            new SyncableFieldPacket(false, message.id,
                                    ((Syncable) world.getBlockEntity(message.blockPosition)).getVariable(message.id), message.blockPosition));
                else // On server update
                    ((Syncable) world.getBlockEntity(message.blockPosition)).setVariable(message.id, message.value);
                ctx.get().setPacketHandled(true);
            } else if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) { // Run when send to client
                Level world = Nucleus.proxy.getClientWorld();

                // Safety check
                if(world == null || message.blockPosition == null ||
                        world.getBlockEntity(message.blockPosition) == null ||
                        !(world.getBlockEntity(message.blockPosition) instanceof Syncable))
                    return;

                // If wanting to ping back server for whatever reason
                if(message.returnValue)
                    PacketManager.INSTANCE.sendToServer(new SyncableFieldPacket(false, message.id,
                            ((Syncable)world.getBlockEntity(message.blockPosition)).getVariable(message.id), message.blockPosition));
                else
                    ((Syncable)world.getBlockEntity(message.blockPosition)).setVariable(message.id, message.value);
                ctx.get().setPacketHandled(true);
            }
        });
    }
}
