package com.eliotlash.particlemanstyles.spigotwrapper;

import com.eliotlash.particlelib.mcwrapper.IBlock;
import com.eliotlash.particlelib.mcwrapper.ResourceLocation;
import com.eliotlash.particlemanstyles.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class BlockWrapper implements IBlock {
    private Block block;

    public BlockWrapper(Block block) {
        this.block = block;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        Bukkit.getLogger().severe("BlockWrapper#getResourceLocation is not implemented.");
        return new ResourceLocation("null");
    }
}
