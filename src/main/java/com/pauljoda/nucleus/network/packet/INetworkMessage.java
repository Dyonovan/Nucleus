package com.pauljoda.nucleus.network.packet;

import net.minecraft.network.FriendlyByteBuf;

import java.io.Serializable;

public interface INetworkMessage extends Serializable {
    void encode(FriendlyByteBuf buffer);
    void decode(FriendlyByteBuf buffer);
}
