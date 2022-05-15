package com.github.theredbrain.dirtyThings.mixin.entity.player;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

	@Shadow
	private int foodLevel;

	@Shadow
	public abstract void addExhaustion(float exhaustion);

	int regenTick = 0;

	@Inject(at = @At("TAIL"), method = "update")
	private void hardcoreRegeneration(PlayerEntity player, CallbackInfo ci) {

		if(player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && this.foodLevel >= 8 && player.getHealth() < player.getMaxHealth()) {

			regenTick++;
			if(regenTick >= 80) {
				regenTick = 0;
				player.heal(1);
				this.addExhaustion(6.0f);
			}
		} else {
			regenTick = 0;
		}
	}
}
