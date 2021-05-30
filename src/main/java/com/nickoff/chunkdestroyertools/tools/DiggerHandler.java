package com.nickoff.chunkdestroyertools.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.stream.IntStream;

import static net.minecraft.util.Direction.*;
import static net.minecraftforge.common.ForgeHooks.canHarvestBlock;

class DigJob implements Iterator<Integer>
{
    private BlockPos initialPos;
    private Direction direction;

    private ToolItem tool;

    private int partialRange;
    private int range;
    private int doubledRange;

    private int i;
    private int incr;
    private int iLimit;

    private int initialJ;
    private int initialK;
    private int endJ;
    private int endK;

    private World world;

    private static final int MINIMUM_Y = 2;

    private int processInitialY(BlockPos pos)
    {
        if (pos.getY() < MINIMUM_Y)
            return MINIMUM_Y;
        else
            return pos.getY() - 1;
    }

    public DigJob(BlockPos initialPos, int range, Direction direction, ToolItem tool, World world)
    {
        this.initialPos = initialPos;
        this.range = range;
        this.partialRange = Math.floorDiv(range, 2);
        this.doubledRange = partialRange * 2;

        this.direction = direction;
        this.tool = tool;

        this.world = world;

        if (direction == UP || direction == DOWN)
        {
            i = initialPos.getY();
            if (direction == UP)
            {
                iLimit = i - range;
                if (iLimit < MINIMUM_Y){
                    iLimit = MINIMUM_Y;
                    if (i < MINIMUM_Y)
                        i = MINIMUM_Y;
                }

                incr = -1;
            }
            else
            {
                iLimit = i + range;
                incr = 1;
            }
            initialJ = initialPos.getX() - partialRange;
            initialK = initialPos.getZ() - partialRange;
        }
        else if (direction == NORTH || direction == SOUTH)
        {
            i = initialPos.getZ();
            if (direction == NORTH)
            {
                iLimit = i + range;
                incr = 1;
            }
            else
            {
                iLimit = i - range;
                incr = -1;
            }
            initialJ = initialPos.getX() - partialRange;
            //initialK = initialPos.getY() - partialRange;
            initialK = processInitialY(initialPos);
        }
        else
        {
            i = initialPos.getX();
            if (direction == EAST)
            {
                iLimit = i - range;
                incr = -1;
            }
            else
            {
                iLimit = i + range;
                incr = 1;
            }
            //initialJ = initialPos.getY() - partialRange;
            initialJ = processInitialY(initialPos);
            initialK = initialPos.getZ() - partialRange;
        }
        this.endJ = initialJ + range;
        this.endK = initialK + range;
    }

    public World getWorld(){
        return world;
    }

    public BlockPos getInitialPos() {
        return initialPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getInitialJ() {
        return initialJ;
    }

    public int getInitialK() {
        return initialK;
    }

    public boolean canHarvestBlock(BlockState state)
    {
        if (((ChunkPickaxe) tool).harvestCheck(state))
            return true;
        return false;
    }

    public int getEndJ() {
        return endJ;
    }

    public int getEndK() {
        return endK;
    }

    @Override
    public boolean hasNext() {
        if (i != iLimit)
            return true;
        else
            return false;
    }

    @Override
    public Integer next() {
        int tmp = i;
        i += incr;
        return tmp;
    }
}

public class DiggerHandler {

    private static final int TICKS_INTERVAL = 2;

    private List<DigJob> jobList;
    private int ticks = 0;

    public DiggerHandler()
    {
        this.jobList = new ArrayList<DigJob>();
        this.ticks = 0;
    }

    void addJob(DigJob job){ jobList.add(job); }

    private void breakAreaRemoveBlocks(DigJob job,
                                       World world,
                                       Map<Integer, Integer> drops,
                                       Map<Integer, List<ItemStack>> loots,
                                       int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = world.getBlockState(pos);

        if (job.canHarvestBlock(state)) {
            int id = Block.getId(state);
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

    private void breakAreaSpawnLoot(World world,
                                    Map<Integer, Integer> drops,
                                    Map<Integer, List<ItemStack>> loots,
                                    int x, int y, int z)
    {
        for (int key : drops.keySet()){
            for(ItemStack loot : loots.get(key)){
                loot.setCount(drops.get(key));
                ItemEntity entity = new ItemEntity(world, x, y, z, loot);
                entity.spawnAtLocation(entity.getItem());
            }
        }
        drops.clear();
        loots.clear();
    }

    public boolean breakArea(DigJob job)
    {
        if (job.hasNext())
        {
            World world = job.getWorld();
            int i = job.next();
            int j = job.getInitialJ();
            int jLim = job.getEndJ(), kLim = job.getEndK();

            Map<Integer, Integer> drops = new HashMap<Integer, Integer>();
            Map<Integer, List<ItemStack>> loots = new HashMap<Integer, List<ItemStack>>();

            BlockPos iPos = job.getInitialPos();
            Direction direction = job.getDirection();

            if (direction == UP || direction == DOWN)
            {
                for (; j < jLim; j++)
                    for (int k = job.getInitialK(); k < kLim; k++)
                        breakAreaRemoveBlocks(job, world, drops, loots, j, i, k);
                breakAreaSpawnLoot(world, drops, loots, iPos.getX(), i, iPos.getZ());
            }
            else if (direction == NORTH || direction == SOUTH)
            {
                for (; j < jLim; j++)
                    for (int k = job.getInitialK(); k < kLim; k++)
                        breakAreaRemoveBlocks(job, world, drops, loots, j, k, i);
                breakAreaSpawnLoot(world, drops, loots, iPos.getX(), iPos.getY(), i);
            }
            else
            {
                for (; j < jLim; j++)
                    for (int k = job.getInitialK(); k < kLim; k++)
                        breakAreaRemoveBlocks(job, world, drops, loots, i, j, k);
                breakAreaSpawnLoot(world, drops, loots, i, iPos.getY(), iPos.getZ());
            }
            return false;
        }
        else
            return true;
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.ServerTickEvent event)
    {
            ticks++;
            if (ticks % TICKS_INTERVAL == 0) {
                Iterator<DigJob> iter = jobList.iterator();
                while (iter.hasNext())
                    if (breakArea(iter.next()))
                        iter.remove();
                ticks = 0;
            }
    }

}
