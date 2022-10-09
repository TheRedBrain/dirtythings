package com.github.theredbrain.dirtythings.client;

import com.github.theredbrain.dirtythings.DirtyThings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class DirtyThingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerColors();
    }

    private void registerColors() {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)), DirtyThings.GRASS_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), DirtyThings.GRASS_SLAB);
        BlockRenderLayerMap.INSTANCE.putBlock(DirtyThings.GRASS_SLAB, RenderLayer.getCutoutMipped());
    }
}
