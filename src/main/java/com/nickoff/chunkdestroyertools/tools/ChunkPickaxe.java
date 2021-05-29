package com.nickoff.chunkdestroyertools.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkPickaxe extends PickaxeItem {
    private int range;
    private int partialRange;
    private int doubledRange;

    public ChunkPickaxe(IItemTier tier, int range)
    {
        super(tier, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS));

        this.range = range;
        this.partialRange = Math.floorDiv(range, 2);
        this.doubledRange = partialRange * 2;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState initialState, BlockPos initialPos, LivingEntity player) {
        if (!world.isClientSide && initialState.getDestroySpeed(world, initialPos) != 0.0F) {
                RayTraceResult raytraceresult = getPlayerPOVHitResult(world, (PlayerEntity) player, RayTraceContext.FluidMode.SOURCE_ONLY);
                BlockRayTraceResult blocktraceresult = (BlockRayTraceResult) raytraceresult;

                int ix = initialPos.getX(),
                    iy = initialPos.getY(),
                    iz = initialPos.getZ();
                int xstart = ix - partialRange,
                    ystart = iy - partialRange,
                    zstart = iz - partialRange;
                int xlimit = ix + partialRange,
                    ylimit = iy + partialRange,
                    zlimit = iz + partialRange;

                switch(blocktraceresult.getDirection())
                {
                    case UP:
                        ystart = iy - doubledRange;
                        ylimit = iy;
                        break;
                    case DOWN:
                        ystart = iy;
                        ylimit = iy + doubledRange;
                        break;
                    case SOUTH:
                        zstart = iz - doubledRange;
                        zlimit = iz;
                        break;
                    case NORTH:
                        zstart = iz;
                        zlimit = iz + doubledRange;
                        break;
                    case EAST:
                        xstart = ix - doubledRange;
                        xlimit = ix;
                        break;
                    case WEST:
                        xstart = ix;
                        xlimit = ix + doubledRange;
                        break;
                }

                Map<Integer, Integer> drops = new HashMap<Integer, Integer>();
                Map<Integer, List<ItemStack>> loots = new HashMap<Integer, List<ItemStack>>();

                int id;

                for (int x = xstart; x <= xlimit; x++)
                    for(int y = ystart; y <= ylimit; y++)
                        for(int z = zstart; z <= zlimit; z++)
                        {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState state = world.getBlockState(pos);
                            if (canHarvestBlock(stack, state)) {
                                id = Block.getId(state);
                                if (drops.containsKey(id))
                                    drops.put(id, drops.get(id) + 1);
                                else {
                                    drops.put(id, 1);
                                    loots.put(id, Block.getDrops(state,
                                            ((ServerWorld) world).getWorldServer(), pos, null));
                                }

                                world.removeBlock(pos, false);
                            }
                        }

                for (int key : drops.keySet()){
                    for(ItemStack loot : loots.get(key)){
                        loot.setCount(drops.get(key));
                        ItemEntity entity = new ItemEntity(world, ix, iy, iz, loot);
                        entity.spawnAtLocation(entity.getItem());
                    }
                }

                stack.hurtAndBreak(1, player, (p_220038_0_) -> {
                    p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                });
        }

        return true;
    }
}
