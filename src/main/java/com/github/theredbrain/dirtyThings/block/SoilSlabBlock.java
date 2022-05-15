package com.github.theredbrain.dirtyThings.block;

import net.minecraft.block.Block;
import net.minecraft.state.property.Properties;

public class SoilSlabBlock extends CustomSlabBlock {

    public SoilSlabBlock(Settings settings) {
        super(settings);
    }

    static {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
//        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
