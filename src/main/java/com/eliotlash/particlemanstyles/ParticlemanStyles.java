package com.eliotlash.particlemanstyles;

import dev.esophose.playerparticles.event.ParticleStyleRegistrationEvent;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;

public class ParticlemanStyles extends JavaPlugin implements Listener {
    private static final ParticleStyle EXAMPLE_STYLE = new ExampleParticleStyle();
    private PlayerParticlesAPI ppAPI;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            this.ppAPI = PlayerParticlesAPI.getInstance();
            getLogger().info("Found Player Particles v" + ppAPI.getVersion());
            Bukkit.getPluginManager().registerEvents(this, this);

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

    @EventHandler
    public void onParticleStyleRegistration(ParticleStyleRegistrationEvent event) {
        event.registerStyle(EXAMPLE_STYLE);
    }
}
