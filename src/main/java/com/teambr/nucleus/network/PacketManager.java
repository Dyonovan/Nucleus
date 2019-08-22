package com.teambr.nucleus.network;

import com.teambr.nucleus.lib.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
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
public class PacketManager {
    // Our network wrapper
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    /**
     * Registers all packets
     */
    public static void initPackets() {
        registerMessage(ClientOverridePacket.class, ClientOverridePacket::process);
        registerMessage(SyncableFieldPacket.class,  SyncableFieldPacket::process);
    }

    // Local hold for next packet id
    private static int nextPacketId = 0;

    /**
     * Registers a message to the network registry
     * @param packet The packet class
     */
    @SuppressWarnings("unchecked")
    private static <T extends INetworkMessage> void registerMessage(Class<T> packet,
                                                                    BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(nextPacketId, packet,
                INetworkMessage::encode,
                (buf) -> {
                    try {
                        T msg = packet.newInstance();
                        msg.decode(buf);
                        return msg;
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                },
                messageConsumer);
        nextPacketId++;
    }

    /*******************************************************************************************************************
     * Helper Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Reverse of normal syncing, will send client side data to server to replace
     * @param tile The tile you wish to update to server
     */
    public static void updateTileWithClientInfo(TileEntity tile) {
        CompoundNBT tag = new CompoundNBT();
        tile.write(tag);
        ClientOverridePacket updateMessage = new ClientOverridePacket(tile.getPos(), tag);
        INSTANCE.send(PacketDistributor.ALL.noArg(), updateMessage);
    }
}