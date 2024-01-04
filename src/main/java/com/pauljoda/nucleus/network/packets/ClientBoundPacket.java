package com.pauljoda.nucleus.network.packets;

import com.pauljoda.nucleus.Nucleus;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Locale;

/**
 * Represents a packet sent from server to client.
 */
public interface ClientBoundPacket extends CustomPacketPayload {

    /**
     * Performs an action related to this packet on the client side, specific to a particular player.
     *
     * @param player The player entity where the packet is used.
     */
    void handleOnClient(Player player);

    /**
     * Returns the ID of the mod registering the packet.
     *
     * @return Resource location that includes the namespace (mod ID) and name (class simple name in lower case).
     */
    @Override
    default ResourceLocation id() {
        return new ResourceLocation(Nucleus.MODID, getClass().getSimpleName().toLowerCase(Locale.ROOT));
    }

    /**
     * Performs an action related to this packet on the client side.
     *
     * @param context The context of the client where the packet is used.
     */
    default void handleOnClient(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            context.player().ifPresent(this::handleOnClient);
        });
    }
}