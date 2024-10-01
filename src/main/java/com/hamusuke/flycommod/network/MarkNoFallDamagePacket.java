package com.hamusuke.flycommod.network;

import com.hamusuke.flycommod.FlyCommandMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class MarkNoFallDamagePacket implements CustomPayload {
    public static final Codec<MarkNoFallDamagePacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("entityId").forGetter(MarkNoFallDamagePacket::getEntityId),
            Codec.BOOL.fieldOf("flag").forGetter(MarkNoFallDamagePacket::getMarkFlag)
    ).apply(instance, MarkNoFallDamagePacket::new));
    public static final Id<MarkNoFallDamagePacket> ID =  new Id<>(Identifier.of(FlyCommandMod.MOD_ID, "no_fall_mark_packet"));
    private final int entityId;
    private final boolean flag;

    public MarkNoFallDamagePacket(int entityId, boolean flag) {
        this.entityId = entityId;
        this.flag = flag;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public boolean getMarkFlag() {
        return this.flag;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
