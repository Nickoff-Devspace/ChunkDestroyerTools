package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.ChunkDestroyerTools;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Init {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChunkDestroyerTools.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChunkDestroyerTools.MOD_ID);

    public static void register()
    {
        InitBlocks.register();
        InitItems.register();
        IEventBus ebus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(ebus);
        BLOCKS.register(ebus);
        InitHandlers.register();
    }
}
