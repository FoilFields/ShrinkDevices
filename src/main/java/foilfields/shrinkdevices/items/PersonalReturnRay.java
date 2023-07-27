package foilfields.shrinkdevices.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class PersonalReturnRay extends AbstractPersonalRay {
    public PersonalReturnRay(Settings settings) {
        super(settings);
    }

    @Override
    public void onUseTick(ItemStack stack, PlayerEntity player) {
        ScaleData scaleData = ScaleData.Builder.create().entity(player).type(ScaleTypes.BASE).build();
        float scale = scaleData.getScale();

        if (Math.abs(1 - scale) < 0.003f) {
            scaleData.setScale(1);
        } else {
            scaleData.setScale(scale < 1 ? scale + 0.003f : scale - 0.003f);
        }
    }
}
