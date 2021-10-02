package com.eliotlash.particlemanstyles;

import dev.esophose.playerparticles.particles.PParticle;
import dev.esophose.playerparticles.particles.ParticlePair;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExampleParticleStyle implements ParticleStyle {

    private int step = 0;
    private final int maxStep = 20;
    private final double radius = 1.25;
    private final int amount = 5;
    private final double spread = 0.1;
    private final double speed = 0.05;

    @Override
    public List<PParticle> getParticles(ParticlePair particle, Location location) {
        List<PParticle> particles = new ArrayList<>();

        double angle = Math.PI * 2 * (this.step / (double) this.maxStep);
        double x = Math.cos(angle) * this.radius;
        double z = Math.sin(angle) * this.radius;
        Location point = location.clone().add(x, 0, z);

        for (int i = 0; i < this.amount; i++)
            particles.add(new PParticle(point, this.spread, this.spread, this.spread, this.speed));

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
        return "test";
    }

    @Override
    public Material getGuiIconMaterial() {
        return Material.EMERALD;
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
