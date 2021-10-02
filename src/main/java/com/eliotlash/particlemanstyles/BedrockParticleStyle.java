package com.eliotlash.particlemanstyles;

import com.eliotlash.particlelib.Settings;
import com.eliotlash.particlelib.mcwrapper.ResourceLocation;
import com.eliotlash.particlelib.particles.BedrockScheme;
import dev.esophose.playerparticles.particles.PParticle;
import dev.esophose.playerparticles.particles.ParticlePair;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BedrockParticleStyle implements ParticleStyle {
    public BedrockScheme scheme;
    public String debugSchemeName;
    public BedrockParticleStyleEmitter emitter;

    private int step = 0;
    private final int maxStep = 20;
    private final double radius = 1.25;
    private final int amount = 5;
    private final double spread = 0.1;
    private final double speed = 0.05;

    public BedrockParticleStyle() {
        Bukkit.getLogger().info("BedrockParticleStyle ctor START");
        String schemeJson = "";
        scheme = BedrockScheme.parse(schemeJson);
        emitter = new BedrockParticleStyleEmitter();
        emitter.setScheme(scheme);
        Bukkit.getLogger().info("BedrockParticleStyle ctor END");
    }

    @Override
    public List<PParticle> getParticles(ParticlePair particle, Location location) {
        Bukkit.getLogger().info("BedrockParticleStyle getParticles START");
        emitter.update();
        /* Screw interpolation for now */
        emitter.lastGlobal.set(location.getX(), location.getY(), location.getZ());
        /* Sanity ticks is a stupid workaround to see if the emitter's controller
         * is still in the world, when the sanity ticks reaches 2, it stops itself */
        emitter.sanityTicks = 0;

        List<PParticle> particles = emitter.getPParticles(location);

        /*
        double angle = Math.PI * 2 * (this.step / (double) this.maxStep);
        double x = Math.cos(angle) * this.radius;
        double z = Math.sin(angle) * this.radius;
        Location point = location.clone().add(x, 0, z);

        for (int i = 0; i < this.amount; i++)
            particles.add(new PParticle(point, this.spread, this.spread, this.spread, this.speed));

         */

        Bukkit.getLogger().info("BedrockParticleStyle getParticles END");
        return particles;
    }

    @Override
    public void updateTimers() {
        this.step = (this.step + 1) % this.maxStep;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getInternalName() {
        Bukkit.getLogger().info("BedrockParticleStyle getInternalName");
        return "bedrock";
    }

    @Override
    public Material getGuiIconMaterial() {
        return Material.BEDROCK;
    }

    @Override
    public boolean canBeFixed() {
        return true;
    }

    @Override
    public boolean canToggleWithMovement() {
        return true;
    }

    @Override
    public double getFixedEffectOffset() {
        return 0;
    }

}
