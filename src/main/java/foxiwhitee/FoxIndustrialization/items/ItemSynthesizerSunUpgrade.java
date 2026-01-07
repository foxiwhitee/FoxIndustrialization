package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.api.ISynthesizerSunUpgrade;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class  ItemSynthesizerSunUpgrade extends DefaultItem implements ISynthesizerSunUpgrade {
    public ItemSynthesizerSunUpgrade(String name) {
        super(name, "upgrades/");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.upgrade.sun.desc"));
        }
    }

    @Override
    public boolean needSun(ItemStack stack) {
        return false;
    }
}
