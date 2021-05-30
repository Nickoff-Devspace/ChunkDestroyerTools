package com.nickoff.chunkdestroyertools.tools;

import com.nickoff.chunkdestroyertools.init.InitHandlers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkPickaxe extends PickaxeItem {

    private ItemStack pickaxeStack;
    private int range;

    public ChunkPickaxe(IItemTier tier, int range)
    {
        super(tier, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS));
        this.range = range;
        this.pickaxeStack = new ItemStack(this);
    }

    public Direction blockFacePlayerIsLookingAt(World world, LivingEntity player)
    {
        RayTraceResult raytraceresult = getPlayerPOVHitResult(world, (PlayerEntity) player, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blocktraceresult = (BlockRayTraceResult) raytraceresult;
        return blocktraceresult.getDirection();
    }

    public boolean harvestCheck(BlockState state) {
        return this.canHarvestBlock(pickaxeStack, state);
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState initialState, BlockPos initialPos, LivingEntity player) {
        if (!world.isClientSide && initialState.getDestroySpeed(world, initialPos) != 0.0F) {
            InitHandlers.DIGGER_HANDLER.addJob(new DigJob(initialPos,
                    this.range, this.blockFacePlayerIsLookingAt(world, player),
                    this, world));

            stack.hurtAndBreak(1, player, (p_220038_0_) -> {
                p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }
}
