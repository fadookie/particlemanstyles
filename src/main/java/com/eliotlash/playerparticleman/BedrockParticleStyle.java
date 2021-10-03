package com.eliotlash.playerparticleman;

import com.eliotlash.particlelib.particles.BedrockScheme;
import dev.esophose.playerparticles.particles.PParticle;
import dev.esophose.playerparticles.particles.ParticlePair;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class BedrockParticleStyle implements ParticleStyle {
    public BedrockScheme scheme;
    public String schemeName;
    public BedrockStyleEmitter emitter;

    private int step = 0;
    private final int maxStep = 20;
    private final double radius = 1.25;
    private final int amount = 5;
    private final double spread = 0.1;
    private final double speed = 0.05;

    public BedrockParticleStyle(String schemeName, BedrockScheme scheme) {
        this.schemeName = schemeName;
        this.scheme = scheme;
        emitter = new BedrockStyleEmitter();
        emitter.setScheme(scheme);
    }


    @Override
    public List<PParticle> getParticles(ParticlePair particle, Location location) {
//        Bukkit.getLogger().info("BedrockParticleStyle getParticles START");
        emitter.start();
        emitter.update();
        emitter.render(0);
        emitter.running = emitter.sanityTicks < 2;
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

//        Bukkit.getLogger().info("BedrockParticleStyle getParticles END");
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
        return schemeName;
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
