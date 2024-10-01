package com.hamusuke.flycommod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.PacketCodecs;

public final class NetworkManager {
    public static void init() {
        PayloadTypeRegistry.playS2C().register(MarkNoFallDamagePacket.ID, PacketCodecs.codec(MarkNoFallDamagePacket.CODEC));
    }
}
