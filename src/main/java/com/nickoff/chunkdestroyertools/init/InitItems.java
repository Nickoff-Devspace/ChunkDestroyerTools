package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.tools.ChunkAxe;
import com.nickoff.chunkdestroyertools.tools.ChunkPickaxe;
import com.nickoff.chunkdestroyertools.tools.ChunkShovel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;

public class InitItems {

    public static final RegistryObject<Item> WOODEN_CHUNK_PICKAXE =
            Init.ITEMS.register("wooden_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.WOOD, 3));
    public static final RegistryObject<Item> STONE_CHUNK_PICKAXE =
            Init.ITEMS.register("stone_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.STONE, 7));
    public static final RegistryObject<Item> IRON_CHUNK_PICKAXE =
            Init.ITEMS.register("iron_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.IRON, 17));
    public static final RegistryObject<Item> GOLDEN_CHUNK_PICKAXE =
            Init.ITEMS.register("golden_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.GOLD, 15));
    public static final RegistryObject<Item> DIAMOND_CHUNK_PICKAXE =
            Init.ITEMS.register("diamond_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.DIAMOND, 31));
    public static final RegistryObject<Item> NETHERITE_CHUNK_PICKAXE =
            Init.ITEMS.register("netherite_chunk_pickaxe", () -> new ChunkPickaxe(ItemTier.NETHERITE, 81));

    public static final RegistryObject<Item> WOODEN_CHUNK_AXE =
            Init.ITEMS.register("wooden_chunk_axe", () -> new ChunkAxe(ItemTier.WOOD, 3));
    public static final RegistryObject<Item> STONE_CHUNK_AXE =
            Init.ITEMS.register("stone_chunk_axe", () -> new ChunkAxe(ItemTier.STONE, 7));
    public static final RegistryObject<Item> IRON_CHUNK_AXE =
            Init.ITEMS.register("iron_chunk_axe", () -> new ChunkAxe(ItemTier.IRON, 17));
    public static final RegistryObject<Item> GOLDEN_CHUNK_AXE =
            Init.ITEMS.register("golden_chunk_axe", () -> new ChunkAxe(ItemTier.GOLD, 15));
    public static final RegistryObject<Item> DIAMOND_CHUNK_AXE =
            Init.ITEMS.register("diamond_chunk_axe", () -> new ChunkAxe(ItemTier.DIAMOND, 31));
    public static final RegistryObject<Item> NETHERITE_CHUNK_AXE =
            Init.ITEMS.register("netherite_chunk_axe", () -> new ChunkAxe(ItemTier.NETHERITE, 81));

    public static final RegistryObject<Item> WOODEN_CHUNK_SHOVEL =
            Init.ITEMS.register("wooden_chunk_shovel", () -> new ChunkShovel(ItemTier.WOOD, 3));
    public static final RegistryObject<Item> STONE_CHUNK_SHOVEL =
            Init.ITEMS.register("stone_chunk_shovel", () -> new ChunkShovel(ItemTier.STONE, 7));
    public static final RegistryObject<Item> IRON_CHUNK_SHOVEL =
            Init.ITEMS.register("iron_chunk_shovel", () -> new ChunkShovel(ItemTier.IRON, 17));
    public static final RegistryObject<Item> GOLDEN_CHUNK_SHOVEL =
            Init.ITEMS.register("golden_chunk_shovel", () -> new ChunkShovel(ItemTier.GOLD, 15));
    public static final RegistryObject<Item> DIAMOND_CHUNK_SHOVEL =
            Init.ITEMS.register("diamond_chunk_shovel", () -> new ChunkShovel(ItemTier.DIAMOND, 31));
    public static final RegistryObject<Item> NETHERITE_CHUNK_SHOVEL =
            Init.ITEMS.register("netherite_chunk_shovel", () -> new ChunkShovel(ItemTier.NETHERITE, 81));

    public static void register(){}
}
