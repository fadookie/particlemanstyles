package com.eliotlash.particlemanstyles;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;

public class ParticlemanStyles extends JavaPlugin {
    private PlayerParticlesAPI ppAPI;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            this.ppAPI = PlayerParticlesAPI.getInstance();
            getLogger().info("Found Player Particles v" + ppAPI.getVersion());

        } else {
            getLogger().severe("Fatal error: Cannot find PlayerParticles API, perhaps the plugin is not installed or the wrong version is installed.");
            return;
        }

        // When you want to access the API, check if the instance is null
        if (this.ppAPI != null) {
            // Do stuff with the API here
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
