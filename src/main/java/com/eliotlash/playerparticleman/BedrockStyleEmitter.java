package com.eliotlash.playerparticleman;

import com.eliotlash.particlelib.mcwrapper.Size2f;
import com.eliotlash.particlelib.particles.components.IComponentParticleRenderBase;
import com.eliotlash.particlelib.particles.emitter.BedrockEmitter;
import com.eliotlash.particlelib.particles.emitter.BedrockParticle;
//import com.eliotlash.particleman.client.textures.GifTexture;
//import com.eliotlash.particleman.client.particles.components.IComponentParticleRender;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.util.math.BlockPos;
import com.eliotlash.playerparticleman.spigotwrapper.ConversionUtils;
import com.eliotlash.playerparticleman.spigotwrapper.WorldWrapper;
import dev.esophose.playerparticles.particles.PParticle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
//import org.lwjgl.opengl.GL11;

//import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BedrockStyleEmitter extends BedrockEmitter
{
    private World concreteWorld;
    public HashSet<BedrockParticle> alreadySpawnedParticles = new HashSet<>();

    public void setTarget(Entity target)
    {
        this.target = ConversionUtils.entityToSize(target);
        setWorld(target == null ? null : target.getWorld());
    }

    public void setTarget(Size2f size, World world)
    {
        target = size;
        setWorld(world);
    }

    public void setWorld(World world)
    {
        concreteWorld = world;
        this.world = concreteWorld == null ? null : new WorldWrapper(concreteWorld);
    }

    @Override
    public double getDistanceSq() {
        double dx = this.lastGlobal.x;
        double dy = this.lastGlobal.y;
        double dz = this.lastGlobal.z;

        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public void renderOnScreen(int i, int i1, float v) {
    }

    @Override
    public void render(float partialTicks) {
        List<IComponentParticleRenderBase> renders = this.scheme.particleRender;
        for (BedrockParticle particle : this.particles)
        {
            this.setEmitterVariables(partialTicks);
            this.setParticleVariables(particle, partialTicks);
        }

        for (IComponentParticleRenderBase component : renders)
        {
            component.preRender(this, partialTicks);
            component.postRender(this, partialTicks);
        }
    }

    @Override
    public void setupCameraProperties(float v) {
    }

    @Override
    public int getBrightnessForRender(float v, double v1, double v2, double v3) {
        return 0;
    }

    public List<PParticle> getPParticles(Location location) {
        List<PParticle> pParticles = particles
                .stream()
                .filter(particle -> !alreadySpawnedParticles.contains(particle))
                .map(particle -> new PParticle(
                        location.clone().add(particle.position.x, particle.position.y, particle.position.z),
                        0, 0, 0,
//                        particle.acceleration.x,
//                        particle.acceleration.y,
//                        particle.acceleration.z,
                        particle.speed.length() * 0.025f // Eyeballed this scalar to get it to look similar to Snowstorm
                ))
                .collect(Collectors.toList());
        // We can't move particles from a plugin so we need to immediately filter out the ones we're rendering to prevent them from re-spawning next update
        alreadySpawnedParticles.addAll(particles);
        return pParticles;
    }

    public void postUpdate() {
        Iterator it = this.alreadySpawnedParticles.iterator();

        while(it.hasNext()) {
            BedrockParticle particle = (BedrockParticle)it.next();
            if(particle.dead) {
                it.remove();
            }
        }
    }

//
////    private BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
//
//    /* Camera properties */
//    public int perspective;
//    public float cYaw;
//    public float cPitch;
//
//    public double cX;
//    public double cY;
//    public double cZ;
//
//    public double getDistanceSq()
//    {
//        this.setupCameraProperties(0F);
//
//        double dx = this.cX - this.lastGlobal.x;
//        double dy = this.cY - this.lastGlobal.y;
//        double dz = this.cZ - this.lastGlobal.z;
//
//        return dx * dx + dy * dy + dz * dz;
//    }
//
//
//    public double getDistanceSq(BedrockParticle particle)
//    {
//        Vector3d pos = particle.getGlobalPosition(this);
//
//        double dx = cX - pos.x;
//        double dy = cY - pos.y;
//        double dz = cZ - pos.z;
//
//        return dx * dx + dy * dy + dz * dz;
//    }


//    /**
//     * Render the particle on screen
//     */
//    public void renderOnScreen(int x, int y, float scale)
//    {
//        if (this.scheme == null)
//        {
//            return;
//        }
//
//        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
//        List<IComponentParticleRender> list = this.scheme.getComponents(IComponentParticleRender.class);
//
//        if (!list.isEmpty())
//        {
//            // TODO cache or safe delete
//            GifTexture.bindTexture(ConversionUtils.abstractToConcreteRL(this.scheme.texture));
//
//            GlStateManager.enableBlend();
//            GlStateManager.disableCull();
//
//            if (this.guiParticle == null || this.guiParticle.dead)
//            {
//                this.guiParticle = this.createParticle(true);
//            }
//
//            this.rotation.setIdentity();
//            this.guiParticle.update(this);
//            this.setEmitterVariables(partialTicks);
//            this.setParticleVariables(this.guiParticle, partialTicks);
//
//            for (IComponentParticleRender render : list)
//            {
//                render.renderOnScreen(this.guiParticle, x, y, scale, partialTicks);
//            }
//
//            GlStateManager.disableBlend();
//            GlStateManager.enableCull();
//        }
//    }
//
//    /**
//     * Render all the particles in this particle emitter
//     */
//    public void render(float partialTicks)
//    {
//        if (this.scheme == null)
//        {
//            return;
//        }
//
//        this.setupCameraProperties(partialTicks);
//
//        BufferBuilder builder = Tessellator.getInstance().getBuffer();
//        List<IComponentParticleRenderBase> renders = this.scheme.particleRender;
//
//        for (IComponentParticleRenderBase component : renders)
//        {
//            component.preRender(this, partialTicks);
//        }
//
//        if (!this.particles.isEmpty())
//        {
//            if (Settings.getParticleSorting())
//            {
//                this.particles.sort((a, b) ->
//                {
//                    double ad = this.getDistanceSq(a);
//                    double bd = this.getDistanceSq(b);
//
//                    if (ad < bd)
//                    {
//                        return 1;
//                    }
//                    else if (ad > bd)
//                    {
//                        return -1;
//                    }
//
//                    return 0;
//                });
//            }
//
//            // TODO cache or delete
//            GifTexture.bindTexture(ConversionUtils.abstractToConcreteRL(this.scheme.texture), this.age, partialTicks);
//            builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
//
//            for (BedrockParticle particle : this.particles)
//            {
//                this.setEmitterVariables(partialTicks);
//                this.setParticleVariables(particle, partialTicks);
//
//                for (IComponentParticleRenderBase component : renders)
//                {
//                    if(component instanceof IComponentParticleRender) {
//                        ((IComponentParticleRender) component).render(this, particle, builder, partialTicks);
//                    }
//                }
//            }
//
//            Tessellator.getInstance().draw();
//        }
//
//        for (IComponentParticleRenderBase component : renders)
//        {
//            component.postRender(this, partialTicks);
//        }
//    }
//
//    public void setupCameraProperties(float partialTicks)
//    {
//        if (this.concreteWorld != null)
//        {
//            Entity camera = Minecraft.getMinecraft().getRenderViewEntity();
//
//            this.perspective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
//            this.cYaw = 180 - Interpolations.lerp(camera.prevRotationYaw, camera.rotationYaw, partialTicks);
//            this.cPitch = 180 - Interpolations.lerp(camera.prevRotationPitch, camera.rotationPitch, partialTicks);
//            this.cX = Interpolations.lerp(camera.prevPosX, camera.posX, partialTicks);
//            this.cY = Interpolations.lerp(camera.prevPosY, camera.posY, partialTicks) + camera.getEyeHeight();
//            this.cZ = Interpolations.lerp(camera.prevPosZ, camera.posZ, partialTicks);
//        }
//    }
//
//    /**
//     * Get brightness for the block
//     */
//    public int getBrightnessForRender(float partialTicks, double x, double y, double z)
//    {
//        if (this.lit || this.concreteWorld == null)
//        {
//            return 15728880;
//        }
//
//        this.blockPos.setPos(x, y, z);
//
//        return this.concreteWorld.isBlockLoaded(this.blockPos) ? this.concreteWorld.getCombinedLight(this.blockPos, 0) : 0;
//    }
}
