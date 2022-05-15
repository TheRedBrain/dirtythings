package com.github.theredbrain.dirtyThings.mixin.entity.passive;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @Shadow
    protected abstract void clearSpecialPrices();

    @Shadow
    public abstract VillagerGossips getGossip();

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void forgetGossip(DamageSource source, CallbackInfo ci) {

        this.clearSpecialPrices();

        this.getGossip().remove(VillageGossipType.MAJOR_POSITIVE);
        this.getGossip().remove(VillageGossipType.MINOR_POSITIVE);
        this.getGossip().remove(VillageGossipType.MAJOR_NEGATIVE);
        this.getGossip().remove(VillageGossipType.MINOR_NEGATIVE);
        this.getGossip().remove(VillageGossipType.TRADING);
    }
}
