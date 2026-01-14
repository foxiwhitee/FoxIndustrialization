package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.api.IWitherKillerUpgrade;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemWitherKillerUpgrade extends ItemWithMeta implements IWitherKillerUpgrade {
    public ItemWitherKillerUpgrade(String name) {
        super(name, "upgrades/", "1", "2", "3");
        this.setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.upgrade.speed", EnergyUtility.formatNumber(getSpeedMultiplier(stack) * 100)));
            list.add(LocalizationUtils.localize("tooltip.upgrade.power", EnergyUtility.formatNumber(getEnergyUseMultiplier(stack) * 100)));
            list.add(LocalizationUtils.localize("tooltip.upgrade.storageMult", EnergyUtility.formatNumber(getStorageEnergyMultiplier(stack))));
            list.add(LocalizationUtils.localize("tooltip.upgrade.stars", EnergyUtility.formatNumber(getBonusStars(stack))));
        }
    }

    @Override
    public double getSpeedMultiplier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.witherKillerUpgrade1SpeedMultiplier;
            case 1 -> FIConfig.witherKillerUpgrade2SpeedMultiplier;
            case 2 -> FIConfig.witherKillerUpgrade3SpeedMultiplier;
            default -> 1;
        };
    }

    @Override
    public double getStorageEnergyMultiplier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.witherKillerUpgrade1StorageMultiplier;
            case 1 -> FIConfig.witherKillerUpgrade2StorageMultiplier;
            case 2 -> FIConfig.witherKillerUpgrade3StorageMultiplier;
            default -> 1;
        };
    }

    @Override
    public int getBonusStars(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.witherKillerUpgrade1BonusStars;
            case 1 -> FIConfig.witherKillerUpgrade2BonusStars;
            case 2 -> FIConfig.witherKillerUpgrade3BonusStars;
            default -> 0;
        };
    }

    @Override
    public double getEnergyUseMultiplier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.witherKillerUpgrade1UseMultiplier;
            case 1 -> FIConfig.witherKillerUpgrade2UseMultiplier;
            case 2 -> FIConfig.witherKillerUpgrade3UseMultiplier;
            default -> 1;
        };
    }
}
