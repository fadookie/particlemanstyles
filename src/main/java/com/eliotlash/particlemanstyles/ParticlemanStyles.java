package com.eliotlash.particlemanstyles;

import org.bukkit.plugin.java.JavaPlugin;

public class ParticlemanStyles extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
