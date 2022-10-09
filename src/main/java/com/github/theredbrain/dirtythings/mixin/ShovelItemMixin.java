package com.github.theredbrain.dirtythings.mixin;

import com.github.theredbrain.dirtythings.DirtyThings;
import com.github.theredbrain.dirtythings.block.CustomSlabBlock;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    private static final Map<Block, BlockState> PATH_SLAB_STATES;
    private static final Map<Block, BlockState> PATH_DOUBLE_SLAB_STATES;
    private static final Map<Block, BlockState> PATH_WATERLOGGED_SLAB_STATES;

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void alsoUseOnSlabs(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (context.getSide() == Direction.DOWN) {
            cir.setReturnValue(ActionResult.PASS);
        } else {
            PlayerEntity playerEntity = context.getPlayer();
            BlockState blockState2;
            // block.contains(Properties.WATERLOGGED)
            if (blockState.getBlock() instanceof CustomSlabBlock) {
                if ((Boolean)(blockState.get(CustomSlabBlock.getType()) == SlabType.DOUBLE)) {
                    blockState2 = (BlockState)PATH_DOUBLE_SLAB_STATES.get(blockState.getBlock());
//                    DirtyThings.LOGGER.info("This is a double slab");
                }
                else if ((Boolean)blockState.get(CustomSlabBlock.getWaterlogged())) {
                    blockState2 = (BlockState)PATH_WATERLOGGED_SLAB_STATES.get(blockState.getBlock());
//                    DirtyThings.LOGGER.info("This is a waterlogged slab");
                } else {
                    blockState2 = (BlockState)PATH_SLAB_STATES.get(blockState.getBlock());
//                    DirtyThings.LOGGER.info("This is a slab");
                }
            } else {
                blockState2 = (BlockState)ShovelItemAccessor.getPathStates().get(blockState.getBlock());
//                DirtyThings.LOGGER.info("This is a block");
            }
            BlockState blockState3 = null;
            if (blockState2 != null && (world.getBlockState(blockPos.up()).isAir() || world.getBlockState(blockPos.up()).isIn(BlockTags.FENCE_GATES))) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockState3 = blockState2;
            } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean)blockState.get(CampfireBlock.LIT)) {
                if (!world.isClient()) {
                    world.syncWorldEvent((PlayerEntity)null, 1009, blockPos, 0);
                }

                CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
                blockState3 = (BlockState)blockState.with(CampfireBlock.LIT, false);
            }

            if (blockState3 != null) {
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState3, 11);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState3));
                    if (playerEntity != null) {
                        context.getStack().damage(1, playerEntity, (p) -> {
                            p.sendToolBreakStatus(context.getHand());
                        });
                    }
                }
                cir.setReturnValue(ActionResult.success(world.isClient));
            } else {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }

    static {
        PATH_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(DirtyThings.GRASS_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .put(DirtyThings.DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .put(DirtyThings.PODZOL_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .put(DirtyThings.COARSE_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .put(DirtyThings.MYCELIUM_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .put(DirtyThings.ROOTED_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState())
                .build());
        PATH_DOUBLE_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(DirtyThings.GRASS_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(DirtyThings.DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(DirtyThings.PODZOL_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(DirtyThings.COARSE_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(DirtyThings.MYCELIUM_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(DirtyThings.ROOTED_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .build());
        PATH_WATERLOGGED_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(DirtyThings.GRASS_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(DirtyThings.DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(DirtyThings.PODZOL_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(DirtyThings.COARSE_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(DirtyThings.MYCELIUM_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(DirtyThings.ROOTED_DIRT_SLAB, DirtyThings.DIRT_PATH_SLAB.getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .build());
    }
}
