package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockSynthesizer extends ModItemBlock {
    private final double storage, output;
    private final boolean supportsRF;

    public ItemBlockSynthesizer(Block b) {
        super(b);

        if (isBlock(ModBlocks.synthesizer)) {
            this.storage = FIConfig.synthesizerStorage;
            this.output = FIConfig.synthesizerOutput;
            this.supportsRF = FIConfig.synthesizerSupportsRF;
        } else {
            this.storage = 0;
            this.output = 0;
            this.supportsRF = false;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.solarPanel.output", EnergyUtility.formatNumber(output)));
            if (supportsRF && FICore.ifCoFHCoreIsLoaded) {
                list.add(LocalizationUtils.localize("tooltip.machine.supportsRF", EnergyUtility.formatNumber(FoxLibConfig.rfInEu)));
            }
        }
    }
}
