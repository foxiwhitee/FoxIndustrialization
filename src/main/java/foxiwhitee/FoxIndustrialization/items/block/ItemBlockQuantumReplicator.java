package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockQuantumReplicator extends ModItemBlock {
    public ItemBlockQuantumReplicator(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            if (isBlock(ModBlocks.quantumReplicator)) {
                list.add(LocalizationUtils.localize("tooltip.replicator.desc1"));
                list.add(LocalizationUtils.localize("tooltip.replicator.desc2"));
                list.add(LocalizationUtils.localize("tooltip.replicator.desc3"));
            }
        }
    }
}
