package com.nickoff.chunkdestroyertools.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class PackedBlock extends Block {
    public PackedBlock(Material material, SoundType sound) {
        super(AbstractBlock.Properties.of(material)
                .requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                .sound(sound).harvestLevel(0));
    }
}
