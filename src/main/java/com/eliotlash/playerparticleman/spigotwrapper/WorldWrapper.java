package com.eliotlash.playerparticleman.spigotwrapper;

import com.eliotlash.particlelib.mcwrapper.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldWrapper implements IWorld {
    World world;

    public WorldWrapper(World world) {
        this.world = world;
    }

    @Override
    public boolean isBlockLoaded(BlockPos pos) {
        // TODO This could potentially create a lot of garbage depending on how often this method is called
//        return world.getBlockAt(ConversionUtils.abstractToConcreteBlockPos(this.world, pos)).getBlockData().//isBlockLoaded(ConversionUtils.abstractToConcreteBlockPos(pos));
        return true;
    }

    @Override
    public IBlock getBlockAtPos(BlockPos pos) {
        // TODO This could potentially create a lot of garbage depending on how often this method is called
        return new BlockWrapper(world.getBlockAt(ConversionUtils.abstractToConcreteBlockPos(this.world, pos)));
    }

    @Override
    public List<AxisAlignedBB> getCollisionBoxes(Size2f size, AxisAlignedBB aabb) {
        Bukkit.getLogger().severe("WorldWrapper#getCollisionBoxes is not implemented.");
        /*
        Entity entity = (Entity)size.entity;
        // TODO This could potentially create a lot of garbage depending on how often this method is called - may need
        // to optimize allocations of abstract AABBs using an object pool, or switch to using a mixin interface
        return world.getCollisionBoxes(entity, ConversionUtils.abstractToConcreteAABB(aabb))
                .stream()
                .map(ConversionUtils::concreteToAbstractAABB)
                .collect(Collectors.toList());
         */
        return new ArrayList<>();
    }
}

