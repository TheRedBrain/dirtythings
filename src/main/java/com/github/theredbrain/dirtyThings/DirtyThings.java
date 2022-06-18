package com.github.theredbrain.dirtyThings;

import com.github.theredbrain.dirtyThings.block.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
	public static final Block DIRT_SLAB = new DirtSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).ticksRandomly().strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block COARSE_DIRT_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block PODZOL_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	public static final Block MYCELIUM_SLAB = new MyceliumSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS));
	public static final Block ROOTED_DIRT_SLAB = new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.ROOTED_DIRT));
	public static final Block DIRT_PATH_SLAB = new DirtPathSlabBlock(FabricBlockSettings.of(Material.SOIL).strength(0.65F).sounds(BlockSoundGroup.GRASS).blockVision((state, world, pos) -> {return true;}).suffocates((state, world, pos) -> {return true;}));
	public static final Block SAND_SLAB = new FallingSlabBlock(14406560, FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND));
	public static final Block RED_SAND_SLAB = new FallingSlabBlock(11098145, FabricBlockSettings.of(Material.AGGREGATE, MapColor.ORANGE).strength(0.5F).sounds(BlockSoundGroup.SAND));
	public static final Block GRAVEL_SLAB = new FallingSlabBlock(-8356741, FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.6F).sounds(BlockSoundGroup.GRAVEL));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Dirty things happened to the dirt!");

		registerBlocks();
		registerItems();
	}

	// TODO falling slabs stack

	private void registerBlocks() {
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "grass_slab"), GRASS_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "dirt_slab"), DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "coarse_dirt_slab"), COARSE_DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "podzol_slab"), PODZOL_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "mycelium_slab"), MYCELIUM_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "dirt_path_slab"), DIRT_PATH_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "rooted_dirt_slab"), ROOTED_DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "sand_slab"), SAND_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "red_sand_slab"), RED_SAND_SLAB);
		Registry.register(Registry.BLOCK, new Identifier("dirty-things", "gravel_slab"), GRAVEL_SLAB);
	}

	private void registerItems() {
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "grass_slab"), new BlockItem(GRASS_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "dirt_slab"), new BlockItem(DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "coarse_dirt_slab"), new BlockItem(COARSE_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "podzol_slab"), new BlockItem(PODZOL_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "mycelium_slab"), new BlockItem(MYCELIUM_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "rooted_dirt_slab"), new BlockItem(ROOTED_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "sand_slab"), new BlockItem(SAND_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "red_sand_slab"), new BlockItem(RED_SAND_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "gravel_slab"), new BlockItem(GRAVEL_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "dirt_pile"), new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "gravel_pile"), new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "red_sand_pile"), new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
		Registry.register(Registry.ITEM, new Identifier("dirty-things", "sand_pile"), new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
	}
}
