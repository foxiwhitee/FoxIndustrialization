package foxiwhitee.FoxIndustrialization.api;

import net.minecraft.item.ItemStack;

public interface ISynthesizerUpgrade {
    double getBonus(ItemStack stack);
    double getStorageMultiplier(ItemStack stack);
    double getOutputMultiplier(ItemStack stack);
}
