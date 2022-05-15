package com.github.theredbrain.dirtyThings.mixin.entity.player;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"),
            method = "canFoodHeal",
            cancellable = true)
    private void hardcoreHealing(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
