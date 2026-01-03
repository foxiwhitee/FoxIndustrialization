package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
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

            if (isBlock(ModBlocks.advancedFurnace)) {
                storage = FIConfig.advancedFurnaceStorage;
                itemsPerTick = FIConfig.advancedFurnaceItemsPerOp;
            }
            if (isBlock(ModBlocks.nanoFurnace)) {
                storage = FIConfig.nanoFurnaceStorage;
                itemsPerTick = FIConfig.nanoFurnaceItemsPerOp;
            }
            if (isBlock(ModBlocks.quantumFurnace)) {
                storage = FIConfig.quantumFurnaceStorage;
                itemsPerTick = FIConfig.quantumFurnaceItemsPerOp;
            }

            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.machine.addPerOp", itemsPerTick));
        }
    }
}
