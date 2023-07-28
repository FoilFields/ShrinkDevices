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
 * The PersonalReturnRay class represents a return ray item in the ShrinkDevices mod.
 * This ray is used to return the size of the player's entity back to normal.
 */
public class PersonalReturnRay extends AbstractPersonalRay {

    /**
     * Constructs a new PersonalReturnRay with the given settings.
     *
     * @param settings The settings for the return ray item.
     */
    public PersonalReturnRay(Settings settings) {
        super(settings);
    }

    /**
     * Handles the behavior of the return ray during each usage tick.
     * Slightly increases or decreases the player's scale to return them to default.
     * Appropriate sound effects and a custom particle effect are played accordingly.
     *
     * @param world  The World instance.
     * @param stack  The ItemStack representing the return ray.
     * @param player The PlayerEntity using the return ray.
     */
    @Override
    public void onUseTick(World world, ItemStack stack, PlayerEntity player) {
        ScaleData scaleData = ScaleData.Builder.create().entity(player).type(ScaleTypes.BASE).build();
        float scale = scaleData.getScale();

        if (Math.abs(1 - scale) < 0.003f) {
            // If the scale is very close to 1.0, set it to exactly 1.0 and trigger idle effect
            scaleData.setScale(1);
            idleEffect(world, player.getX(), player.getY(), player.getZ(), scale);
        } else {
            if (scale < 1) {
                // If the scale is smaller than 1.0, increase it slightly
                scaleData.setScale(scale + 0.003f);
                playSound(ShrinkRays.GROW_SOUND_EVENT, world, player.getX(), player.getY(), player.getZ());
            } else {
                // If the scale is larger than 1.0, decrease it slightly
                scaleData.setScale(scale - 0.003f);
                playSound(ShrinkRays.SHRINK_SOUND_EVENT, world, player.getX(), player.getY(), player.getZ());
            }

            if (!world.isClient()) {
                // Create and spawn a custom dust color transition particle effect on the server side
                DustColorTransitionParticleEffect particleEffect = new DustColorTransitionParticleEffect(new Vector3f(0.212f, 1, 0), new Vector3f(0.714f, 1, 0.631f), scale);
                ((ServerWorld) world).spawnParticles(particleEffect, player.getX(), player.getY() + scale, player.getZ(), 1, 0, scale / 2, 0, 0);
            }
        }
    }
}
