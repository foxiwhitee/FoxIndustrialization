package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockUFC extends ModItemBlock {
    public ItemBlockUFC(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            if (isBlock(ModBlocks.universalFluidComplex)) {
                list.add(LocalizationUtils.localize("tooltip.machine.capacity", FIConfig.ufcStorage));
                list.add(LocalizationUtils.localize("tooltip.machine.addPerOp", FIConfig.ufcItemsPerOp));
                list.add(LocalizationUtils.localize("tooltip.fluidGenerator.capacity", FIConfig.ufcFluidStorage));
            }
        }
    }
}
