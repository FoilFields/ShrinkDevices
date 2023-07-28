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
 * The PersonalGrowthRay class represents a growth ray item in the ShrinkDevices mod.
 * This ray is used to grow the size of the player's entity by modifying their scale.
 */
public class PersonalGrowthRay extends AbstractPersonalRay {

    /**
     * Constructs a new PersonalGrowthRay with the given settings.
     *
     * @param settings The settings for the growth ray item.
     */
    public PersonalGrowthRay(Settings settings) {
        super(settings);
    }

    /**
     * Handles the behavior of the growth ray during each usage tick.
     * If the player's scale is already at the maximum value (3.0), the idle effect is triggered.
     * Otherwise, the growth sound and a custom particle effect are played, and the player's scale is increased slightly.
     *
     * @param world  The World instance.
     * @param stack  The ItemStack representing the growth ray.
     * @param player The PlayerEntity using the growth ray.
     */
    @Override
    public void onUseTick(World world, ItemStack stack, PlayerEntity player) {
        ScaleData scaleData = ScaleData.Builder.create().entity(player).type(ScaleTypes.BASE).build();

        float scale = scaleData.getScale();

        if (scale > 2.99f) {
            idleEffect(world, player.getX(), player.getY(), player.getZ(), scale);
        } else {
            playSound(ShrinkRays.GROW_SOUND_EVENT, world, player.getX(), player.getY(), player.getZ());
            if (!world.isClient()) {
                DustColorTransitionParticleEffect particleEffect = new DustColorTransitionParticleEffect(new Vector3f(1f, 0.749f, 0f), new Vector3f(1, 0.478f, 0), scale);
                ((ServerWorld) world).spawnParticles(particleEffect, player.getX(), player.getY() + scale, player.getZ(), 1, 0, scale / 2, 0, 0);
            }
        }

        scaleData.setScale(Math.min(scale + 0.003f, 3));
    }
}
