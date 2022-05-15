package com.github.theredbrain.dirtyThings.mixin.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Shadow
    Fluid fluid;

    @Inject(method = "placeFluid", at = @At("HEAD"), cancellable = true)
    void doNotPlaceSource(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.fluid instanceof FlowableFluid)) {
            cir.setReturnValue(false);
        } else {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBucketPlace(this.fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
            if (!bl2) {
                cir.setReturnValue(false);
            } else if (world.getDimension().isUltrawarm() && this.fluid.isIn(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                cir.setReturnValue(true);
            } else if (block instanceof FluidFillable && this.fluid == Fluids.WATER) {
                cir.setReturnValue(false);
            } else {
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }

                if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !blockState.getFluidState().isStill()) {
                    cir.setReturnValue(false);
                } else {
                    // TODO find way to set the fluid level higher
                    if(this.fluid == Fluids.WATER) {
                        world.setBlockState(pos, Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 6));
                    } else if(this.fluid == Fluids.LAVA) {
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, 6));
                    }
                    SoundEvent soundEvent = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                    world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
