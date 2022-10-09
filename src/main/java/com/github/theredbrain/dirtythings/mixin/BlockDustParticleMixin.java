package com.github.theredbrain.dirtythings.mixin;

import com.github.theredbrain.dirtythings.DirtyThings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BlockDustParticle.class)
public abstract class BlockDustParticleMixin extends SpriteBillboardParticle {

    protected BlockDustParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Inject(method="<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", at=@At("TAIL"))
    private void init(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState state, BlockPos blockPos, CallbackInfo info) {
        // inject into a constructor
        if (state.isOf(DirtyThings.GRASS_SLAB)) {
            int i = MinecraftClient.getInstance().getBlockColors().getColor(state, world, blockPos, 0);
            this.red = 0.6F;
            this.green = 0.6F;
            this.blue = 0.6F;
        }
    }
}
