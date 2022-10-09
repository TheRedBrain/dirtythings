package com.github.theredbrain.dirtythings.block;

import com.github.theredbrain.dirtythings.DirtyThings;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public abstract class CustomSlabBlock extends Block implements Waterloggable {

    protected static EnumProperty<SlabType> TYPE;
    protected static BooleanProperty WATERLOGGED;
    protected static final BooleanProperty SNOWY;

    protected CustomSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false).with(SNOWY, false));
    }

    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TYPE, WATERLOGGED, SNOWY});
    }

    private static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        BlockState blockStateUp = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        if (blockState.getBlock() == this) {
            return (BlockState)((BlockState)blockState.with(TYPE, SlabType.DOUBLE)).with(WATERLOGGED, false).with(SNOWY, isSnow(blockStateUp));
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            return (BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(SNOWY, false);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = (SlabType)state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem())) {
            if (context.canReplaceExisting()) {
                boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5D;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                }
                else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.canFillWithFluid(world, pos, state, fluid) : false;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.UP && (state.isOf(DirtyThings.GRASS_SLAB) || state.isOf(DirtyThings.PODZOL_SLAB) || state.isOf(DirtyThings.MYCELIUM_SLAB)) && state.get(TYPE) == SlabType.DOUBLE) {
            return (BlockState)state.with(SNOWY, isSnow(neighborState));
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch(type) {
            case LAND:
                return false;
            case WATER:
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public static BooleanProperty getWaterlogged() {
        return WATERLOGGED;
    }

    public static EnumProperty<SlabType> getType() {
        return TYPE;
    }

//    public static BooleanProperty getSnowy() {
//        return SNOWY;
//    }

    static {
        SNOWY = Properties.SNOWY;
    }
}
