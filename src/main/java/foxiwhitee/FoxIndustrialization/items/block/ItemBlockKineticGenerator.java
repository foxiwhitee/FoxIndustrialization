package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockKineticGenerator extends ModItemBlock {
    public ItemBlockKineticGenerator(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            boolean supportsRF = false;
            double storage = 0;
            double output = 0;
            if (isBlock(ModBlocks.advancedKineticGenerator)) {
                storage = FIConfig.kineticGeneratorAdvancedStorage;
                output = FIConfig.kineticGeneratorAdvancedOutput;
            }
            if (isBlock(ModBlocks.nanoKineticGenerator)) {
                storage = FIConfig.kineticGeneratorNanoStorage;
                output = FIConfig.kineticGeneratorNanoOutput;
            }
            if (isBlock(ModBlocks.quantumKineticGenerator)) {
                storage = FIConfig.kineticGeneratorQuantumStorage;
                output = FIConfig.kineticGeneratorQuantumOutput;
                supportsRF = FIConfig.kineticGeneratorQuantumSupportsRF && FICore.ifCoFHCoreIsLoaded;
            }
            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.generator.output", EnergyUtility.formatNumber(output)));
            if (supportsRF) {
                list.add(LocalizationUtils.localize("tooltip.machine.supportsRF", EnergyUtility.formatNumber(FoxLibConfig.rfInEu)));
            }
        }
    }
}
