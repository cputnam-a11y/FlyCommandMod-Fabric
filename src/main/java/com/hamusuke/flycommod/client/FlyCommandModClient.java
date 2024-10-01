package com.hamusuke.flycommod.client;

import com.hamusuke.flycommod.invoker.LivingEntityInvoker;
import com.hamusuke.flycommod.network.MarkNoFallDamagePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class FlyCommandModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(MarkNoFallDamagePacket.ID, (payload, context) -> {
            if (context.client().world != null) {
                Entity entity = Objects.requireNonNull(context.client().world).getEntityById(payload.getEntityId());
                if (entity instanceof LivingEntityInvoker invoker) {
                    invoker.flyCommandMod_Fabric$markNoFallDamage(payload.getMarkFlag());
                }
            }
        });
    }
}
