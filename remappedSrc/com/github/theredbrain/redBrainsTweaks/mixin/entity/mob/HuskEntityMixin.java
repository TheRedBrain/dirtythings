package com.github.theredbrain.dirtyThings.mixin.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(HuskEntity.class)
public class HuskEntityMixin extends ZombieEntity {

    public HuskEntityMixin(EntityType<? extends HuskEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void spawnEverywhere(EntityType<HuskEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(canSpawnInDark(type, world, spawnReason, pos, random) && (spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.NATURAL));
    }
}
