package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.Constants;
import com.nickoff.chunkdestroyertools.tools.ChunkPickaxe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Init {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static void register()
    {
        InitItems.register();
        InitBlocks.register();
        IEventBus ebus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(ebus);
        BLOCKS.register(ebus);
    }
}
