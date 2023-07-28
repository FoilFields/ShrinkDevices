package foilfields.shrinkdevices.items;

import foilfields.shrinkrays.ShrinkRays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.joml.Vector3f;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

/**
 * The PersonalShrinkRay class represents a shrink ray item in the ShrinkDevices mod.
 * This ray is used to shrink the size of the player's entity by modifying their scale.
 */
public class PersonalShrinkRay extends AbstractPersonalRay {

    /**
     * Constructs a new PersonalShrinkRay with the given settings.
     *
     * @param settings The settings for the shrink ray item.
     */
    public PersonalShrinkRay(Settings settings) {
        super(settings);
    }

    /**
     * Handles the behavior of the shrink ray during each usage tick.
     * If the player's scale is already very small (<= 0.051f), the idle effect is triggered.
     * Otherwise, the shrink sound and a custom particle effect are played, and the player's scale is decreased slightly.
     * The player's scale is clamped to a minimum of 0.05f to prevent going too small.
     *
     * @param world  The World instance.
     * @param stack  The ItemStack representing the shrink ray.
     * @param player The PlayerEntity using the shrink ray.
     */
    @Override
    public void onUseTick(World world, ItemStack stack, PlayerEntity player) {
        ScaleData scaleData = ScaleData.Builder.create().entity(player).type(ScaleTypes.BASE).build();
        float scale = scaleData.getScale();

        if (scale <= 0.051f) {
            // If the scale is already very small, trigger idle effect
            idleEffect(world, player.getX(), player.getY(), player.getZ(), scale);
        } else {
            // If the scale is larger, play the shrink sound and create a custom particle effect
            playSound(ShrinkRays.SHRINK_SOUND_EVENT, world, player.getX(), player.getY(), player.getZ());

            if (!world.isClient()) {
                // Create and spawn a custom dust color transition particle effect on the server side
                DustColorTransitionParticleEffect particleEffect = new DustColorTransitionParticleEffect(new Vector3f(0.44f, 0.91f, 1.00f), new Vector3f(0.74f, 0.95f, 1.00f), scale);
                ((ServerWorld) world).spawnParticles(particleEffect, player.getX(), player.getY() + scale, player.getZ(), 1, 0, scale / 2, 0, 0);
            }
        }

        // Decrease the player's scale slightly, clamped to a minimum of 0.05f
        scaleData.setScale(Math.max(scale - 0.003f, 0.05f));
    }
}
