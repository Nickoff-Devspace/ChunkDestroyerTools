package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.blocks.PackedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class InitBlocks {

    public static final RegistryObject<Block> PACKED_LOG_BLOCK = register("packed_log_block",
            () -> new PackedBlock(Material.WOOD, SoundType.WOOD));

    public static final RegistryObject<Block> PACKED_STONE_BLOCK = register("packed_stone_block",
            () -> new PackedBlock(Material.STONE, SoundType.STONE));

    public static final RegistryObject<Block> PACKED_IRON_BLOCK = register("packed_iron_block",
            () -> new PackedBlock(Material.METAL, SoundType.METAL));

    public static final RegistryObject<Block> PACKED_GOLD_BLOCK = register("packed_gold_block",
            () -> new PackedBlock(Material.METAL, SoundType.METAL));

    public static final RegistryObject<Block> PACKED_DIAMOND_BLOCK = register("packed_diamond_block",
            () -> new PackedBlock(Material.METAL, SoundType.METAL));

    public static final RegistryObject<Block> PACKED_NETHERITE_BLOCK = register("packed_netherite_block",
            () -> new PackedBlock(Material.HEAVY_METAL, SoundType.METAL));

    public static void register(){}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Init.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Init.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
        return ret;
    }
}
