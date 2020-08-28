package com.pauljoda.nucleus.network.packet;

import com.pauljoda.nucleus.Nucleus;
import com.pauljoda.nucleus.client.gui.ISyncingTileScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/2019
 */
public class SyncTileScreenPacket implements INetworkMessage {

    // Variables
    public CompoundNBT tag;

    /**
     * Stub
     */
    public SyncTileScreenPacket() {}

    /**
     * Creates a packet with the given info
     * @param nbt The tag to write
     */
    public SyncTileScreenPacket(CompoundNBT nbt) {
        super();
        tag = nbt;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void decode(PacketBuffer buf) {
        tag = new PacketBuffer(buf).readCompoundTag();
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeCompoundTag(tag);
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(SyncTileScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                if(message.tag != null && Nucleus.proxy.getPlayer() != null) {
                    PlayerEntity player = Nucleus.proxy.getPlayer();
                    if(player.openContainer instanceof ISyncingTileScreen) {
                        ISyncingTileScreen syncingScreen = (ISyncingTileScreen) player.openContainer;
                        syncingScreen.acceptServerValues(message.tag);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
