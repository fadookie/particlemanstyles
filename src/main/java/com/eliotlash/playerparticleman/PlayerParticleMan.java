package com.eliotlash.playerparticleman;

import com.eliotlash.particlelib.Settings;
import com.eliotlash.particlelib.mcwrapper.ResourceLocation;
import com.eliotlash.particlelib.particles.BedrockScheme;
import com.eliotlash.playerparticleman.spigotwrapper.ConversionUtils;
import dev.esophose.playerparticles.event.ParticleStyleRegistrationEvent;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class PlayerParticleMan extends JavaPlugin implements Listener {
    static {
        Settings.setBlockLookupImpl(ConversionUtils::blockLookup);
        BedrockScheme.setJsonAdapter(new DefaultBedrockSchemeJsonAdapter()); // this is client render stuff that won't be used
        BedrockScheme.setDefaultTexture(new ResourceLocation("minecraft:textures/particle/generic_0.png"));
    }

    private static PlayerParticleMan INSTANCE;
    private static final ParticleStyle EXAMPLE_STYLE_CIRCLE_SPIN = new ExampleParticleStyleCircleSpin();
    private static final ParticleStyle EXAMPLE_STYLE_LINE_MOVE = new ExampleParticleStyleLineMove();
    private PlayerParticlesAPI ppAPI;
    private HashMap<String, BedrockParticleStyle> styles = new HashMap<>();

    public PlayerParticleMan() {
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

        registerCommands();

        reload();
    }

    protected void generateStyles() {
        File dataFolder = getDataFolder();
        Bukkit.getLogger().finest("dataFolder:" + dataFolder);
        File particlesFolder = new File(dataFolder, "particles");
        Bukkit.getLogger().finest("particlesFolder:" + particlesFolder);
        File[] directoryListing = particlesFolder.listFiles();
        if (directoryListing != null) {
            for (File schemeFile : directoryListing) {
                try {
                    if (!schemeFile.getName().endsWith(".json")) continue;
                    Bukkit.getLogger().info("loading schemeFile:" + schemeFile);
                    String schemeJson = new String(Files.readAllBytes(schemeFile.toPath()));
                    Bukkit.getLogger().finest("schemeJson:" + schemeJson);
                    BedrockScheme scheme = BedrockScheme.parse(schemeJson);
                    BedrockParticleStyle bedrockParticleStyle = new BedrockParticleStyle(schemeFile.getName(), scheme);
                    styles.put(schemeFile.getName(), bedrockParticleStyle);
                } catch (Exception e) {
                    Bukkit.getLogger().severe("loadScheme exception:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    protected void registerCommands() {
        this.getCommand("ppmreload").setExecutor(new ReloadCommand());
    }

    public void reload() {
        generateStyles();
        getLogger().info("Reloaded particle data.");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    @EventHandler
    public void onParticleStyleRegistration(ParticleStyleRegistrationEvent event) {
        event.registerStyle(EXAMPLE_STYLE_CIRCLE_SPIN);
        event.registerStyle(EXAMPLE_STYLE_LINE_MOVE);
        getLogger().info("onParticleStyleRegistration registered example styles");
        for (BedrockParticleStyle style : styles.values()) {
            getLogger().info("onParticleStyleRegistration registering:" + style.schemeName);
            event.registerStyle(style);
        }
    }

    public static PlayerParticleMan getInstance() {
        return INSTANCE;
    }
}
