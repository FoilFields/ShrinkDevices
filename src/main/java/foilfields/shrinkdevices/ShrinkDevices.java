package foilfields.shrinkdevices;

import foilfields.shrinkdevices.items.PersonalGrowthRay;
import foilfields.shrinkdevices.items.PersonalReturnRay;
import foilfields.shrinkdevices.items.PersonalShrinkRay;
import foilfields.shrinkrays.ShrinkRays;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Main entry point for ShrinkDevices mod. Initializes, registers items and adds them to an item group.
 */
public class ShrinkDevices implements ModInitializer {

    /**
     * Personal shrink ray item, used to shrink entities or blocks.
     */
    public static final Item PERSONAL_SHRINK_RAY = new PersonalShrinkRay(new FabricItemSettings().maxCount(1));

    /**
     * Personal growth ray item, used to grow entities or blocks.
     */
    public static final Item PERSONAL_GROWTH_RAY = new PersonalGrowthRay(new FabricItemSettings().maxCount(1));

    /**
     * Personal return ray item, used to return entities or blocks to their original size.
     */
    public static final Item PERSONAL_RETURN_RAY = new PersonalReturnRay(new FabricItemSettings().maxCount(1));

    /**
     * Registers personal shrink, growth, and return rays in the item registry,
     * and modifies the item group to include these rays.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, identifier("personal_shrink_ray"), PERSONAL_SHRINK_RAY);
        Registry.register(Registries.ITEM, identifier("personal_growth_ray"), PERSONAL_GROWTH_RAY);
        Registry.register(Registries.ITEM, identifier("personal_return_ray"), PERSONAL_RETURN_RAY);

        // Add items to the ShrinkRays item group
        ItemGroupEvents.modifyEntriesEvent(ShrinkRays.ITEM_GROUP).register((entries) -> {
            entries.add(PERSONAL_SHRINK_RAY);
            entries.add(PERSONAL_GROWTH_RAY);
            entries.add(PERSONAL_RETURN_RAY);
        });
    }

    /**
     * Creates an Identifier for the given name under the "shrink_rays" namespace.
     *
     * @param name The name of the item.
     * @return The Identifier representing the item in the "shrink_rays" namespace.
     */
    public static Identifier identifier(String name) {
        return new Identifier("shrink_rays", name);
    }
}