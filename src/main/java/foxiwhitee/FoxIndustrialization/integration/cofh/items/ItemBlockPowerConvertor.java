package foxiwhitee.FoxIndustrialization.integration.cofh.items;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.integration.cofh.CoFHIntegration;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockPowerConvertor extends ModItemBlock {
    public ItemBlockPowerConvertor(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            if (isBlock(CoFHIntegration.powerConverter)) {
                list.add(LocalizationUtils.localize("tooltip.powerConverter.description", FoxLibConfig.rfInEu));
                list.add(LocalizationUtils.localize("tooltip.powerConverter.storageEU", EnergyUtility.formatNumber(FIConfig.powerConverterEUStorage)));
                list.add(LocalizationUtils.localize("tooltip.powerConverter.storageRF", EnergyUtility.formatNumber(FIConfig.powerConverterRFStorage)));
                list.add(LocalizationUtils.localize("tooltip.powerConverter.outputEU", EnergyUtility.formatNumber(FIConfig.powerConverterEUPerTick)));
                list.add(LocalizationUtils.localize("tooltip.powerConverter.outputRF", EnergyUtility.formatNumber(FIConfig.powerConverterRFPerTick)));
            }
        }
    }
}
