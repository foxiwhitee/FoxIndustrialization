package foxiwhitee.FoxIndustrialization.api;

import net.minecraft.item.ItemStack;

public interface IWitherKillerUpgrade {
    double getSpeedMultiplier(ItemStack stack);

    double getStorageEnergyMultiplier(ItemStack stack);

    int getBonusStars(ItemStack stack);

    double getEnergyUseMultiplier(ItemStack stack);
}
