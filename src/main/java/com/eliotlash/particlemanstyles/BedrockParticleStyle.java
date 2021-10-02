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
//        String schemeJson = "{\"format_version\":\"1.10.0\",\"particle_effect\":{\"description\":{\"identifier\":\"snowstorm:rainbow\",\"basic_render_parameters\":{\"material\":\"particles_alpha\",\"texture\":\"textures/particle/particles\"}},\"curves\":{\"variable.rainbow\":{\"type\":\"catmull_rom\",\"input\":\"variable.particle_random_2\",\"horizontal_range\":1,\"nodes\":[1,0,1,1.18]},\"variable.psize\":{\"type\":\"catmull_rom\",\"input\":\"variable.particle_age\",\"horizontal_range\":\"variable.particle_lifetime\",\"nodes\":[0,0,1,0,0]}},\"components\":{\"minecraft:emitter_initialization\":{\"creation_expression\":\"variable.radius = 2.5;test = 1;\"},\"minecraft:emitter_rate_steady\":{\"spawn_rate\":250,\"max_particles\":500},\"minecraft:emitter_lifetime_looping\":{\"active_time\":2},\"minecraft:emitter_shape_point\":{\"offset\":[\"math.cos(variable.emitter_age * 90) * (2.5-variable.particle_random_2)\",\"math.sin(variable.emitter_age * 90) * (2.5-variable.particle_random_2)\",0]},\"minecraft:particle_lifetime_expression\":{\"max_lifetime\":1.5},\"minecraft:particle_initial_speed\":0,\"minecraft:particle_motion_dynamic\":{\"linear_acceleration\":[0,1,0]},\"minecraft:particle_appearance_billboard\":{\"size\":[\"0.12 * variable.psize\",\"0.12 * variable.psize\"],\"facing_camera_mode\":\"rotate_xyz\",\"uv\":{\"texture_width\":128,\"texture_height\":128,\"uv\":[32,88],\"uv_size\":[8,8]}},\"minecraft:particle_appearance_tinting\":{\"color\":{\"interpolant\":\"variable.rainbow\",\"gradient\":{\"0.0\":\"#d71c1c\",\"0.16\":\"#ffdf00\",\"0.33\":\"#08ff00\",\"0.5\":\"#00ffff\",\"0.67\":\"#0000ff\",\"0.83\":\"#ff00ff\",\"1.0\":\"#e21111\"}}}}}}"; // default rainbow
        String schemeJson = "{\"format_version\":\"1.10.0\",\"particle_effect\":{\"description\":{\"identifier\":\"snowstorm:rainbow\",\"basic_render_parameters\":{\"material\":\"particles_alpha\",\"texture\":\"textures/particle/particles\"}},\"curves\":{\"variable.rainbow\":{\"type\":\"catmull_rom\",\"input\":\"variable.particle_random_2\",\"horizontal_range\":1,\"nodes\":[1,0,1,1.18]},\"variable.psize\":{\"type\":\"catmull_rom\",\"input\":\"variable.particle_age\",\"horizontal_range\":\"variable.particle_lifetime\",\"nodes\":[0,0,1,0,0]}},\"components\":{\"minecraft:emitter_initialization\":{\"creation_expression\":\"variable.radius = 2.5;\"},\"minecraft:emitter_local_space\":{\"position\":true,\"rotation\":true,\"velocity\":true},\"minecraft:emitter_rate_steady\":{\"spawn_rate\":250,\"max_particles\":500},\"minecraft:emitter_lifetime_looping\":{\"active_time\":2},\"minecraft:emitter_shape_point\":{\"offset\":[\"math.cos(variable.emitter_age * 90) * (2.5-variable.particle_random_2)\",\"math.sin(variable.emitter_age * 90) * (2.5-variable.particle_random_2)\",0]},\"minecraft:particle_lifetime_expression\":{\"max_lifetime\":1.5},\"minecraft:particle_initial_speed\":0,\"minecraft:particle_motion_dynamic\":{\"linear_acceleration\":[0,1,0]},\"minecraft:particle_appearance_billboard\":{\"size\":[\"0.12 * variable.psize\",\"0.12 * variable.psize\"],\"facing_camera_mode\":\"rotate_xyz\",\"uv\":{\"texture_width\":128,\"texture_height\":128,\"uv\":[32,88],\"uv_size\":[8,8]}},\"minecraft:particle_appearance_tinting\":{\"color\":{\"interpolant\":\"variable.rainbow\",\"gradient\":{\"0.0\":\"#ffd71c1c\",\"0.16\":\"#ffffdf00\",\"0.33\":\"#ff08ff00\",\"0.5\":\"#ff00ffff\",\"0.67\":\"#ff0000ff\",\"0.83\":\"#ffff00ff\",\"1.0\":\"#ffe21111\"}}}}}}"; // local position + rotation
        scheme = BedrockScheme.parse(schemeJson);
        emitter = new BedrockParticleStyleEmitter();
        emitter.setScheme(scheme);
        Bukkit.getLogger().info("BedrockParticleStyle ctor END");
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
