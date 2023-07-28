package foilfields.shrinkdevices.items;

import foilfields.shrinkrays.ShrinkRays;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.joml.Vector3f;

/**
 The AbstractPersonalRay class is an abstract item representing a personal ray in the ShrinkDevices mod.
 Provides common functionality for the shrink ray, growth ray, and return ray items.
 */
public abstract class AbstractPersonalRay extends Item {

    private static final int TICK_DELAY = 5;
    private int tickCounter = 0;

    /**
     Constructs a new AbstractPersonalRay with the given settings.
     @param settings The settings for the ray item.
     */
    public AbstractPersonalRay(Settings settings) {
        super(settings);
    }

    /**
     Returns the UseAction for the personal ray, which is set to UseAction.BOW in order to enable usageTick.
     @param stack The ItemStack representing the personal ray.
     @return The UseAction for the personal ray, set to UseAction.BOW.
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    /**
     Handles the usage of the personal ray by the player.
     Sets the player's current hand and consumes the ray item.
     @param world The World instance.
     @param user The LivingEntity using the personal ray (usually a PlayerEntity).
     @param hand The hand with which the player is using the personal ray.
     @return A TypedActionResult with the consumed item stack.
     */
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    /**
     Triggered every tick during item usage.
     Increments the tick counter and calls the onUseTick method for player-specific handling.
     @param world The World instance.
     @param user The LivingEntity using the personal ray (usually a PlayerEntity).
     @param stack The ItemStack representing the personal ray.
     @param remainingUseTicks The remaining use ticks for the item.
     */
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            tickCounter++;
            onUseTick(world, user.getMainHandStack(), (PlayerEntity) user);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    /**
     Abstract method to be implemented by subclasses to handle custom behavior during each usage tick of the personal ray.
     @param world The World instance.
     @param stack The ItemStack representing the personal ray.
     @param player The PlayerEntity using the personal ray.
     */
    public abstract void onUseTick(World world, ItemStack stack, PlayerEntity player);

    /**
     Plays a sound effect at the specified location in the world with a given sound event.
     The sound is played after a certain number of tick delays (TICK_DELAY) to avoid spamming.
     @param sound The SoundEvent to be played.
     @param world The World instance.
     @param x The x-coordinate of the sound source.
     @param y The y-coordinate of the sound source.
     @param z The z-coordinate of the sound source.
     */
    public void playSound(SoundEvent sound, World world, double x, double y, double z) {
        if (tickCounter >= TICK_DELAY) {
            world.playSound(null, x, y, z, sound, SoundCategory.BLOCKS, 0.15f, 1.0f);
            tickCounter = 0;
        }
    }

    /**
     Creates an idle effect (sound and particle) at the specified location in the world.
     This effect is triggered when the ray is on but idle.
     The particle effect is only spawned on the server side to prevent visual discrepancies.
     @param world The World instance.
     @param x The x-coordinate of the effect.
     @param y The y-coordinate of the effect.
     @param z The z-coordinate of the effect.
     @param scale The scale of the particle effect.
     */
    public void idleEffect(World world, double x, double y, double z, float scale) {
        playSound(ShrinkRays.IDLE_SOUND_EVENT, world, x, y, z);

        if (!world.isClient()) {
            // Create and spawn a dust color transition particle effect on the server side
            DustColorTransitionParticleEffect particleEffect = new DustColorTransitionParticleEffect(new Vector3f(0.078f, 0.078f, 0.078f), new Vector3f(0.38f, 0.38f, 0.38f), scale);
            ((ServerWorld) world).spawnParticles(particleEffect, x, y + scale, z, 1, 0, scale / 2, 0, 0);
        }
    }
}
