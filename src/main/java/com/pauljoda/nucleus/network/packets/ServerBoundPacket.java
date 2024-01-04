package com.pauljoda.nucleus.network.packets;

import com.pauljoda.nucleus.Nucleus;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Locale;

/**
 * ServerBoundPacket Interface that handles packets on the server side.
 */
public interface ServerBoundPacket extends CustomPacketPayload {

    /**
     * Handle the packet on the server side.
     *
     * @param player The server player to handle the packet for
     */
    void handleOnServer(ServerPlayer player);
    
    /**
     * Handle the packet on the server side with PlayPayloadContext.
     *
     * @param context Provides context for the payload being handled
     */
    default void handleOnServer(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            if (context.player().orElse(null) instanceof ServerPlayer serverPlayer) {
                handleOnServer(serverPlayer);
            }
        });
    }
}