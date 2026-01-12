package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockFluidGenerator extends ModItemBlock {
    public ItemBlockFluidGenerator(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            int storage = 0;
            if (isBlock(ModBlocks.waterGenerator)) {
                storage = FIConfig.waterGeneratorFluidStorage;
            }
            if (isBlock(ModBlocks.lavaGenerator)) {
                storage = FIConfig.lavaGeneratorFluidStorage;
            }
            list.add(LocalizationUtils.localize("tooltip.fluidGenerator.capacity", storage));
        }
    }
}
