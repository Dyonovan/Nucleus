package com.teambr.nucleus.network;

import net.minecraft.network.PacketBuffer;

import java.io.Serializable;

public interface INetworkMessage extends Serializable {
    void encode(PacketBuffer buffer);
    void decode(PacketBuffer buffer);
}
