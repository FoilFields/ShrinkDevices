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

public class ShrinkDevices implements ModInitializer {
    public static final Item PERSONAL_SHRINK_RAY = new PersonalShrinkRay(new FabricItemSettings().maxCount(1));
    public static final Item PERSONAL_GROWTH_RAY = new PersonalGrowthRay(new FabricItemSettings().maxCount(1));
    public static final Item PERSONAL_RETURN_RAY = new PersonalReturnRay(new FabricItemSettings().maxCount(1));

    @Override
    public void onInitialize() {

        Registry.register(Registries.ITEM, identifier("personal_growth_ray"), PERSONAL_GROWTH_RAY);
        Registry.register(Registries.ITEM, identifier("personal_shrink_ray"), PERSONAL_SHRINK_RAY);
        Registry.register(Registries.ITEM, identifier("personal_return_ray"), PERSONAL_RETURN_RAY);

        ItemGroupEvents.modifyEntriesEvent(ShrinkRays.ITEM_GROUP).register((entries -> {
            entries.add(PERSONAL_SHRINK_RAY);
            entries.add(PERSONAL_GROWTH_RAY);
            entries.add(PERSONAL_RETURN_RAY);
        }));
    }

    public static Identifier identifier(String name) {
        return new Identifier("shrink_rays", name);
    }
}
