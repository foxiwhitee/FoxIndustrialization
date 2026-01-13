package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.api.IHasMatterSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockMatterGenerator extends ModItemBlock implements IHasMatterSynthesizerIntegration {
    private final int storage;
    private final double energyNeed;
    private final int production;

    public ItemBlockMatterGenerator(Block b) {
        super(b);
        if (isBlock(ModBlocks.advancedMatterGenerator)) {
            storage = FIConfig.advancedMatterGeneratorTank;
            energyNeed = FIConfig.advancedMatterGeneratorEnergyNeed;
            production = FIConfig.advancedMatterGeneratorGenerate;
        } else if (isBlock(ModBlocks.nanoMatterGenerator)) {
            storage = FIConfig.nanoMatterGeneratorTank;
            energyNeed = FIConfig.nanoMatterGeneratorEnergyNeed;
            production = FIConfig.nanoMatterGeneratorGenerate;
        } else if (isBlock(ModBlocks.quantumMatterGenerator)) {
            storage = FIConfig.quantumMatterGeneratorTank;
            energyNeed = FIConfig.quantumMatterGeneratorEnergyNeed;
            production = FIConfig.quantumMatterGeneratorGenerate;
        } else if (isBlock(ModBlocks.singularMatterGenerator)) {
            storage = FIConfig.singularMatterGeneratorTank;
            energyNeed = FIConfig.singularMatterGeneratorEnergyNeed;
            production = FIConfig.singularMatterGeneratorGenerate;
        } else {
            storage = 0;
            energyNeed = 0;
            production = 0;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.fluidGenerator.capacity", storage));
            list.add(LocalizationUtils.localize("tooltip.matter.generating", EnergyUtility.formatNumber(production), EnergyUtility.formatNumber(energyNeed)));
        }
    }

    @Override
    public double getEnergyNeed(ItemStack stack) {
        return energyNeed * stack.stackSize;
    }

    @Override
    public int getTankCapacity(ItemStack stack) {
        return storage * stack.stackSize;
    }

    @Override
    public int getGenerating(ItemStack stack) {
        return production * stack.stackSize;
    }
}
