package com.github.theredbrain.dirtyThings;

import com.github.theredbrain.dirtyThings.block.DirtPathSlabBlock;
import com.github.theredbrain.dirtyThings.block.GrassSlabBlock;
import com.github.theredbrain.dirtyThings.block.MyceliumSlabBlock;
import com.github.theredbrain.dirtyThings.block.SoilSlabBlock;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DirtyThings implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("dirty-things");

	public static final Block GRASS_SLAB = new GrassSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS));
	public static final Block DIRT_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block COARSE_DIRT_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block PODZOL_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block MYCELIUM_SLAB = new MyceliumSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS));
	public static final Block ROOTED_DIRT_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.ROOTED_DIRT));
	//public static final Block DIRT_PATH_SLAB = new DirtPathSlabBlock(FabricBlockSettings.of(Material.SOIL).strength(0.65F).sounds(BlockSoundGroup.GRASS).blockVision(Blocks::always).suffocates(Blocks::always));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// TODO configure snowy texture for double grass, podzol and mycelium slabs
		// TODO giant spruce tree growing converts dirt slabs into podzol slabs
		// TODO dirt_path_slab?
		// TODO implement pile items, dropped from soil blocks when broken without a shovel (maybe with a loss), used to craft slabs and blocks
		// TODO implement slabs for sand, red_sand and gravel


		LOGGER.info("Dirty things happened to the dirt!");

		registerBlocks();
		registerItems();
	}

	private void registerBlocks() {
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "grass_slab"), GRASS_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "dirt_slab"), DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "coarse_dirt_slab"), COARSE_DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "podzol_slab"), PODZOL_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "mycelium_slab"), MYCELIUM_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "rooted_dirt_slab"), ROOTED_DIRT_SLAB);
	}

	private void registerItems() {
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "grass_slab"), new BlockItem(GRASS_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "dirt_slab"), new BlockItem(DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "coarse_dirt_slab"), new BlockItem(COARSE_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "podzol_slab"), new BlockItem(PODZOL_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "mycelium_slab"), new BlockItem(MYCELIUM_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "rooted_dirt_slab"), new BlockItem(ROOTED_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
