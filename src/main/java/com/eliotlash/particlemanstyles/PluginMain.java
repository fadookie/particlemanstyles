package com.eliotlash.particlemanstyles;

import com.eliotlash.particlelib.Settings;
import com.eliotlash.particlelib.mcwrapper.ResourceLocation;
import com.eliotlash.particlelib.particles.BedrockScheme;
import com.eliotlash.particlemanstyles.spigotwrapper.ConversionUtils;
import dev.esophose.playerparticles.PlayerParticles;
import dev.esophose.playerparticles.event.ParticleStyleRegistrationEvent;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;

public class PluginMain extends JavaPlugin implements Listener {
    static {
        Settings.setBlockLookupImpl(ConversionUtils::blockLookup);
        BedrockScheme.setJsonAdapter(new MockBedrockSchemeJsonAdapter()); // this is client render stuff that won't be used
        BedrockScheme.setDefaultTexture(new ResourceLocation("minecraft:textures/particle/generic_0.png"));
    }

    private static PluginMain INSTANCE;
    private static final ParticleStyle EXAMPLE_STYLE = new ExampleParticleStyle();
    private static final ParticleStyle BEDROCK_STYLE = new BedrockParticleStyle();
    private PlayerParticlesAPI ppAPI;

    public PluginMain() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            getLogger().severe("Fatal error: PlayerParticles API is not loaded, perhaps the plugin is not installed or the wrong version is installed.");
            return;
        }

        ppAPI = PlayerParticlesAPI.getInstance();

        // When you want to access the API, check if the instance is null
        if (ppAPI == null) {
            getLogger().severe("Fatal error: PlayerParticles API is null, perhaps the plugin is not yet initialized.");
            return;
        }

        // Do stuff with the API here
        getLogger().info("Found PlayerParticles:" + ppAPI);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    @EventHandler
    public void onParticleStyleRegistration(ParticleStyleRegistrationEvent event) {
        getLogger().info("onParticleStyleRegistration");
        event.registerStyle(EXAMPLE_STYLE);
        event.registerStyle(BEDROCK_STYLE);
    }

    public static PluginMain getInstance() {
        return INSTANCE;
    }
}
