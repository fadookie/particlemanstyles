package com.eliotlash.particlemanstyles.spigotwrapper;

import com.eliotlash.particlelib.mcwrapper.*;
import org.bukkit.World;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class ConversionUtils {
    public static Location abstractToConcreteBlockPos(World world,
                                                      com.eliotlash.particlelib.mcwrapper.BlockPos abstractPos) {
        return new Location(world, abstractPos.getX(), abstractPos.getY(), abstractPos.getZ());
    }

    // TODO don't know if these exist in bukkit API
    public static String abstractToConcreteRL(ResourceLocation abstractRL) {
        return abstractRL.namespace + ":" + abstractRL.path;
    }

    public static ResourceLocation concreteToAbstractRL(String concreteRL) {
        return new ResourceLocation(concreteRL);
    }

    public static BoundingBox abstractToConcreteAABB(AxisAlignedBB aabb) {
        return new BoundingBox(aabb.getXMin(), aabb.getYMin(), aabb.getZMin(), aabb.getXMax(), aabb.getYMax(), aabb.getZMax());
    }

    public static AxisAlignedBB concreteToAbstractAABB(BoundingBox aabb) {
        return new AxisAlignedBB(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }

    public static Size2f entityToSize(Entity entity) {
        return new Size2f((float)entity.getWidth(), (float)entity.getHeight(), entity);
    }

    // TODO: Not sure if there is a static block registry in bukkit API
    public static IBlock blockLookup(ResourceLocation resourceLocation) {
        return new BlockWrapper(resourceLocation);
//        return new BlockWrapper(ForgeRegistries.BLOCKS.getValue(abstractToConcreteRL(resourceLocation)));
    }

    // TODO fix this?
    private static class BlockWrapper implements IBlock {
        private final ResourceLocation location;
        public BlockWrapper(ResourceLocation location) {
            this.location = location;
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return location;
        }
    }
}
