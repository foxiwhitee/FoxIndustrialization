package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemFluidGeneratorUpgrade extends ItemWithMeta {
    public ItemFluidGeneratorUpgrade(String name) {
        super(name, "upgrades/", "Water", "Lava");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.fluidUpgrade." + (stack.getItemDamage() == 0 ? "Water" : "Lava") + ".desc"));
        }
    }
}
