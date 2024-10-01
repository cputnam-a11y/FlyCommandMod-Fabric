package com.hamusuke.flycommod.mixin;

import com.hamusuke.flycommod.invoker.LivingEntityInvoker;
import com.hamusuke.flycommod.network.MarkNoFallDamagePacket;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityInvoker {
    @Unique
    private boolean isNoFallDamageMarked;

    @SuppressWarnings("unused")
    LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapMethod(method = "handleFallDamage")
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, Operation<Boolean> original) {
        if (this.flyCommandMod_Fabric$isNoFallDamageMarked()) {
            this.flyCommandMod_Fabric$markNoFallDamage(false);
            return false;
        }
        return original.call(fallDistance, damageMultiplier, damageSource);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("noFallDamageMarked", this.flyCommandMod_Fabric$isNoFallDamageMarked());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.flyCommandMod_Fabric$markNoFallDamage(nbt.getBoolean("noFallDamageMarked"));
    }

    @Override
    public void flyCommandMod_Fabric$markNoFallDamage(boolean flag) {
        if (!this.getWorld().isClient) {
            Objects.requireNonNull(this.getWorld().getServer()).getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> serverPlayerEntity.networkHandler.sendPacket(new CustomPayloadS2CPacket(new MarkNoFallDamagePacket(this.getId(), flag))));
        }

        this.isNoFallDamageMarked = flag;
    }

    @Override
    public boolean flyCommandMod_Fabric$isNoFallDamageMarked() {
        return this.isNoFallDamageMarked;
    }
}
