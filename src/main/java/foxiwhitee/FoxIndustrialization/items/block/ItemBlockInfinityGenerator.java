package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.api.IHasSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockInfinityGenerator extends ModItemBlock implements IHasSynthesizerIntegration {
    private final double generating, output, storage;

    public ItemBlockInfinityGenerator(Block b) {
        super(b);

        if (isBlock(ModBlocks.infinityGenerator)) {
            this.generating = FIConfig.infinityGeneratorGenerating;
            this.output = FIConfig.infinityGeneratorOutput;
            this.storage = FIConfig.infinityGeneratorStorage;
        } else {
            this.generating = 0;
            this.output = 0;
            this.storage = 0;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.solarPanel.output", EnergyUtility.formatNumber(output)));
            list.add(LocalizationUtils.localize("tooltip.generator.generate", EnergyUtility.formatNumber(generating)));
            if (FIConfig.infinityGeneratorSupportsRF && FICore.ifCoFHCoreIsLoaded) {
                list.add(LocalizationUtils.localize("tooltip.machine.supportsRF", FoxLibConfig.rfInEu));
            }
        }
    }

    @Override
    public double getDayGen(ItemStack stack) {
        return this.generating;
    }

    @Override
    public double getNightGen(ItemStack stack) {
        return this.generating;
    }
}
