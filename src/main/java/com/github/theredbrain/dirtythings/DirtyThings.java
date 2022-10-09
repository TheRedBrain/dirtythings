package com.github.theredbrain.dirtythings;

import com.github.theredbrain.dirtythings.block.*;
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
	public static final String MOD_ID = "dirtythings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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

		LOGGER.info("Dirty things happened to the dirt!");

		registerBlocks();
		registerItems();
	}

	//TODO
	// falling slabs stack

	private void registerBlocks() {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "grass_slab"), GRASS_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dirt_slab"), DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "coarse_dirt_slab"), COARSE_DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "podzol_slab"), PODZOL_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "mycelium_slab"), MYCELIUM_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dirt_path_slab"), DIRT_PATH_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "rooted_dirt_slab"), ROOTED_DIRT_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "sand_slab"), SAND_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "red_sand_slab"), RED_SAND_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "gravel_slab"), GRAVEL_SLAB);
	}

	private void registerItems() {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "grass_slab"), new BlockItem(GRASS_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dirt_slab"), new BlockItem(DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "coarse_dirt_slab"), new BlockItem(COARSE_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "podzol_slab"), new BlockItem(PODZOL_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "mycelium_slab"), new BlockItem(MYCELIUM_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "rooted_dirt_slab"), new BlockItem(ROOTED_DIRT_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "sand_slab"), new BlockItem(SAND_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "red_sand_slab"), new BlockItem(RED_SAND_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gravel_slab"), new BlockItem(GRAVEL_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
