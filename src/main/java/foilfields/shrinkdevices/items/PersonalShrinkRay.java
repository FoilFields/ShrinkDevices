package foilfields.shrinkdevices.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class PersonalShrinkRay extends AbstractPersonalRay {

    public PersonalShrinkRay(Settings settings) {
        super(settings);
    }

    @Override
    public void onUseTick(ItemStack stack, PlayerEntity player) {
        ScaleData scaleData = ScaleData.Builder.create().entity(player).type(ScaleTypes.BASE).build();

        scaleData.setScale(Math.max(scaleData.getScale() - 0.003f, 0.05f));
    }
}
