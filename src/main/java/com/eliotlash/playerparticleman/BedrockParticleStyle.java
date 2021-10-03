package com.eliotlash.playerparticleman;

import com.eliotlash.particlelib.mcwrapper.Size2f;
import com.eliotlash.particlelib.particles.BedrockScheme;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import dev.esophose.playerparticles.particles.PParticle;
import dev.esophose.playerparticles.particles.ParticlePair;
import dev.esophose.playerparticles.styles.ParticleStyle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class BedrockParticleStyle implements ParticleStyle {
    public BedrockScheme scheme;
    public String schemeName;
    public BedrockStyleEmitter emitter;

    public BedrockParticleStyle(String schemeName, BedrockScheme scheme) {
        this.schemeName = schemeName;
        this.scheme = scheme;
        emitter = new BedrockStyleEmitter();
        emitter.setScheme(scheme);
        // Set target to default player AABB for bounding box shape emitter
        emitter.setTarget(new Size2f(0.6f, 1.8f, null), null);
    }


    @Override
    public List<PParticle> getParticles(ParticlePair particle, Location location) {
        List<PParticle> particles = emitter.getPParticles(location);
        emitter.postUpdate();
        // We can't move particles so I don't think this matters
//        emitter.lastGlobal.set(location.getX(), location.getY(), location.getZ());

        return particles;
    }

    @Override
    public void updateTimers() {
//        Player p = (Player)Bukkit.getServer().getOnlinePlayers().toArray()[0];
//        PlayerParticleMan.getInstance().getLogger().info("player aabb w:" + p.getBoundingBox().getWidthX() + " h:" + p.getBoundingBox().getHeight());
//        emitter.setTarget(new Size2f((float)p.getBoundingBox().getWidthX(), (float)p.getBoundingBox().getHeight(), null), null);
        emitter.update();
        emitter.render(0);
        emitter.running = emitter.sanityTicks < 2;
        /* Screw interpolation for now */
//        emitter.lastGlobal.set(location.getX(), location.getY(), location.getZ());
        /* Sanity ticks is a stupid workaround to see if the emitter's controller
         * is still in the world, when the sanity ticks reaches 2, it stops itself */
        emitter.sanityTicks = 0;
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
    public String getName() {
        return scheme.identifier;
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
