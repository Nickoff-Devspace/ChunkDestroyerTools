package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.tools.ChunkPickaxe;
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

    public static void register(){}
}
