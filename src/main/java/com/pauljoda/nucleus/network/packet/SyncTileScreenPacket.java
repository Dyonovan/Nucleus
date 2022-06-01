package com.pauljoda.nucleus.network.packet;

import com.pauljoda.nucleus.Nucleus;
import com.pauljoda.nucleus.client.gui.ISyncingTileScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

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
    public CompoundTag tag;

    /**
     * Stub
     */
    public SyncTileScreenPacket() {}

    /**
     * Creates a packet with the given info
     * @param nbt The tag to write
     */
    public SyncTileScreenPacket(CompoundTag nbt) {
        super();
        tag = nbt;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void decode(FriendlyByteBuf buf) {
        tag = new FriendlyByteBuf(buf).readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(SyncTileScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                if(message.tag != null && Nucleus.proxy.getPlayer() != null) {
                    if(Minecraft.getInstance().screen instanceof ISyncingTileScreen syncingScreen) {
                        syncingScreen.acceptServerValues(message.tag);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
