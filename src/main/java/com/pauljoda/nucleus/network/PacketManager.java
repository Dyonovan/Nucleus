package com.pauljoda.nucleus.network;

import com.pauljoda.nucleus.lib.Reference;
import com.pauljoda.nucleus.network.packet.ClientOverridePacket;
import com.pauljoda.nucleus.network.packet.INetworkMessage;
import com.pauljoda.nucleus.network.packet.SyncTileScreenPacket;
import com.pauljoda.nucleus.network.packet.SyncableFieldPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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
        registerMessage(SyncTileScreenPacket.class, SyncTileScreenPacket::process);
    }

    // Local hold for next packet id
    private static int nextPacketId = 0;

    /**
     * Registers a message to the network registry
     * @param packet The packet class
     */
    @SuppressWarnings("unchecked")
    public static <T extends INetworkMessage> void registerMessage(Class<T> packet,
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
     * Send an update to the player with this container open
     * @param player The player to update on client end
     * @param entity The entity to write the data
     */
    public static void updateClientContainerInfo(ServerPlayer player, BlockEntity entity) {
        CompoundTag tag = new CompoundTag();
        entity.handleUpdateTag(tag);
        SyncTileScreenPacket packet = new SyncTileScreenPacket(tag);
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    /**
     * Reverse of normal syncing, will send client side data to server to replace
     * @param tile The tile you wish to update to server
     */
    public static void updateTileWithClientInfo(BlockEntity tile) {
        CompoundTag tag = new CompoundTag();
        tile.load(tag);
        ClientOverridePacket updateMessage = new ClientOverridePacket(tile.getBlockPos(), tag);
        INSTANCE.sendToServer(updateMessage);
    }
}