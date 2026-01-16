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

public class ItemBlockMachine extends ModItemBlock {
    public ItemBlockMachine(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            double storage = 0;
            int itemsPerTick = 0;
            boolean supportsRF = false;
            String recyclerItem = null;

            if (isBlock(ModBlocks.advancedCompressor)) {
                storage = FIConfig.advancedCompressorStorage;
                itemsPerTick = FIConfig.advancedCompressorItemsPerOp;
            }
            if (isBlock(ModBlocks.advancedExtractor)) {
                storage = FIConfig.advancedExtractorStorage;
                itemsPerTick = FIConfig.advancedExtractorItemsPerOp;
            }
            if (isBlock(ModBlocks.advancedFurnace)) {
                storage = FIConfig.advancedFurnaceStorage;
                itemsPerTick = FIConfig.advancedFurnaceItemsPerOp;
            }
            if (isBlock(ModBlocks.advancedMacerator)) {
                storage = FIConfig.advancedMaceratorStorage;
                itemsPerTick = FIConfig.advancedMaceratorItemsPerOp;
            }
            if (isBlock(ModBlocks.advancedMetalFormer)) {
                storage = FIConfig.advancedMetalFormerStorage;
                itemsPerTick = FIConfig.advancedMetalFormerItemsPerOp;
            }
            if (isBlock(ModBlocks.advancedRecycler)) {
                storage = FIConfig.advancedRecyclerStorage;
                itemsPerTick = FIConfig.advancedRecyclerItemsPerOp;
                recyclerItem = "itemScrapName";
            }

            if (isBlock(ModBlocks.nanoCompressor)) {
                storage = FIConfig.nanoCompressorStorage;
                itemsPerTick = FIConfig.nanoCompressorItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoExtractor)) {
                storage = FIConfig.nanoExtractorStorage;
                itemsPerTick = FIConfig.nanoExtractorItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoFurnace)) {
                storage = FIConfig.nanoFurnaceStorage;
                itemsPerTick = FIConfig.nanoFurnaceItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoMacerator)) {
                storage = FIConfig.nanoMaceratorStorage;
                itemsPerTick = FIConfig.nanoMaceratorItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoMetalFormer)) {
                storage = FIConfig.nanoMetalFormerStorage;
                itemsPerTick = FIConfig.nanoMetalFormerItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoRecycler)) {
                storage = FIConfig.nanoRecyclerStorage;
                itemsPerTick = FIConfig.nanoRecyclerItemsPerOp;
                recyclerItem = "itemScrapName";
            }

            if (isBlock(ModBlocks.quantumCompressor)) {
                storage = FIConfig.quantumCompressorStorage;
                itemsPerTick = FIConfig.quantumCompressorItemsPerOp;
                supportsRF = FIConfig.quantumCompressorSupportsRF;
            }
            if (isBlock(ModBlocks.quantumExtractor)) {
                storage = FIConfig.quantumExtractorStorage;
                itemsPerTick = FIConfig.quantumExtractorItemsPerOp;
                supportsRF = FIConfig.quantumExtractorSupportsRF;
            }
            if (isBlock(ModBlocks.quantumFurnace)) {
                storage = FIConfig.quantumFurnaceStorage;
                itemsPerTick = FIConfig.quantumFurnaceItemsPerOp;
                supportsRF = FIConfig.quantumFurnaceSupportsRF;
            }
            if (isBlock(ModBlocks.quantumMacerator)) {
                storage = FIConfig.quantumMaceratorStorage;
                itemsPerTick = FIConfig.quantumMaceratorItemsPerOp;
                supportsRF = FIConfig.quantumMaceratorSupportsRF;
            }
            if (isBlock(ModBlocks.quantumMetalFormer)) {
                storage = FIConfig.quantumMetalFormerStorage;
                itemsPerTick = FIConfig.quantumMetalFormerItemsPerOp;
                supportsRF = FIConfig.quantumMetalFormerSupportsRF;
            }
            if (isBlock(ModBlocks.quantumRecycler)) {
                storage = FIConfig.quantumRecyclerStorage;
                itemsPerTick = FIConfig.quantumRecyclerItemsPerOp;
                supportsRF = FIConfig.quantumRecyclerSupportsRF;
                recyclerItem = "itemScrapBoxName";
            }

            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.machine.addPerOp", itemsPerTick));
            if (recyclerItem != null) {
                list.add(LocalizationUtils.localize("tooltip.machine.recyclerGenerating", LocalizationUtils.localize("tooltip." + recyclerItem)));
            }
            if (supportsRF && FICore.ifCoFHCoreIsLoaded) {
                list.add(LocalizationUtils.localize("tooltip.machine.supportsRF", EnergyUtility.formatNumber(FoxLibConfig.rfInEu)));
            }
        }
    }
}
