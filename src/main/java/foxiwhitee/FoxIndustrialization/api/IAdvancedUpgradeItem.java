package foxiwhitee.FoxIndustrialization.api;

import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import net.minecraft.item.ItemStack;

public interface IAdvancedUpgradeItem {
    double getSpeedMultiplier(ItemStack stack);

    double getStorageEnergyMultiplier(ItemStack stack);

    int getItemsPerOpAdd(ItemStack stack);

    double getEnergyUseMultiplier(ItemStack stack);

    MachineTier getTier(ItemStack stack);
}
