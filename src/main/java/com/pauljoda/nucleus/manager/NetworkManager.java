package com.pauljoda.nucleus.manager;

import com.pauljoda.nucleus.Nucleus;
import com.pauljoda.nucleus.network.packets.ClientBoundPacket;
import com.pauljoda.nucleus.network.packets.ServerBoundPacket;
import com.pauljoda.nucleus.network.packets.bidirectional.SyncableFieldPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.Locale;

/**
 * Manages the network communication for the Nucleus mod.
 * <p>
 * Based on https://github.com/AppliedEnergistics/Applied-Energistics-2 for new network handling
 */
@Mod.EventBusSubscriber(modid = Nucleus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkManager {

    /**
     * Initializes the NetworkManager.
     *
     * @param event the event to handle payload registration
     */
    @SubscribeEvent
    public static void init(RegisterPayloadHandlerEvent event) {
        var registrar = event.registrar(Nucleus.MODID);

        bidirectional(registrar, SyncableFieldPacket.class, SyncableFieldPacket::decode);
    }

    /**
     * Processes a client-bound packet.
     *
     * @param registrar   the registrar to register packet payload
     * @param packetClass the class of the packet
     * @param reader      the reader to read packet data
     * @param <T>         the type of the packet
     */
    private static <T extends ClientBoundPacket> void clientbound(IPayloadRegistrar registrar, Class<T> packetClass,
                                                                  FriendlyByteBuf.Reader<T> reader) {
        var id = new ResourceLocation(Nucleus.MODID, packetClass.getSimpleName().toLowerCase(Locale.ROOT));
        registrar.play(id, reader, builder -> builder.client(ClientBoundPacket::handleOnClient));
    }

    /**
     * Processes a server-bound packet.
     *
     * @param registrar   the registrar to register packet payload
     * @param packetClass the class of the packet
     * @param reader      the reader to read packet data
     * @param <T>         the type of the packet
     */
    private static <T extends ServerBoundPacket> void serverbound(IPayloadRegistrar registrar, Class<T> packetClass,
                                                                  FriendlyByteBuf.Reader<T> reader) {
        var id = new ResourceLocation(Nucleus.MODID, packetClass.getSimpleName().toLowerCase(Locale.ROOT));
        registrar.play(id, reader, builder -> builder.server(ServerBoundPacket::handleOnServer));
    }

    /**
     * Processes a bidirectional packet.
     *
     * @param registrar   the registrar to register packet payload
     * @param packetClass the class of the packet
     * @param reader      the reader to read packet data
     * @param <T>         the type of the packet
     */
    private static <T extends ServerBoundPacket & ClientBoundPacket> void bidirectional(IPayloadRegistrar registrar,
                                                                                        Class<T> packetClass, FriendlyByteBuf.Reader<T> reader) {
        var id = new ResourceLocation(Nucleus.MODID, packetClass.getSimpleName().toLowerCase(Locale.ROOT));
        registrar.play(id, reader, builder -> {
            builder.client(ClientBoundPacket::handleOnClient);
            builder.server(ServerBoundPacket::handleOnServer);
        });
    }
}