package com.pauljoda.nucleus.network.packet;

import net.minecraft.network.PacketBuffer;

import java.io.Serializable;

public interface INetworkMessage extends Serializable {
    void encode(PacketBuffer buffer);
    void decode(PacketBuffer buffer);
}
