package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.api.ISynthesizerUpgrade;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSynthesizerUpgrade extends ItemWithMeta implements ISynthesizerUpgrade {
    public ItemSynthesizerUpgrade(String name) {
        super(name, "upgrades/", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.upgrade.synthesizerBonus", getBonus(stack)));
            list.add(LocalizationUtils.localize("tooltip.upgrade.storageMult", EnergyUtility.formatNumber(getStorageMultiplier(stack))));
            list.add(LocalizationUtils.localize("tooltip.upgrade.outputMult", EnergyUtility.formatNumber(getOutputMultiplier(stack))));
        }
    }

    @Override
    public double getBonus(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.synthesizerUpgrade1Bonus;
            case 1 -> FIConfig.synthesizerUpgrade2Bonus;
            case 2 -> FIConfig.synthesizerUpgrade3Bonus;
            case 3 -> FIConfig.synthesizerUpgrade4Bonus;
            case 4 -> FIConfig.synthesizerUpgrade5Bonus;
            case 5 -> FIConfig.synthesizerUpgrade6Bonus;
            case 6 -> FIConfig.synthesizerUpgrade7Bonus;
            case 7 -> FIConfig.synthesizerUpgrade8Bonus;
            case 8 -> FIConfig.synthesizerUpgrade9Bonus;
            case 9 -> FIConfig.synthesizerUpgrade10Bonus;
            default -> 0;
        };
    }

    @Override
    public double getStorageMultiplier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.synthesizerUpgrade1StorageMultiplier;
            case 1 -> FIConfig.synthesizerUpgrade2StorageMultiplier;
            case 2 -> FIConfig.synthesizerUpgrade3StorageMultiplier;
            case 3 -> FIConfig.synthesizerUpgrade4StorageMultiplier;
            case 4 -> FIConfig.synthesizerUpgrade5StorageMultiplier;
            case 5 -> FIConfig.synthesizerUpgrade6StorageMultiplier;
            case 6 -> FIConfig.synthesizerUpgrade7StorageMultiplier;
            case 7 -> FIConfig.synthesizerUpgrade8StorageMultiplier;
            case 8 -> FIConfig.synthesizerUpgrade9StorageMultiplier;
            case 9 -> FIConfig.synthesizerUpgrade10StorageMultiplier;
            default -> 0;
        };
    }

    @Override
    public double getOutputMultiplier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 0 -> FIConfig.synthesizerUpgrade1OutputMultiplier;
            case 1 -> FIConfig.synthesizerUpgrade2OutputMultiplier;
            case 2 -> FIConfig.synthesizerUpgrade3OutputMultiplier;
            case 3 -> FIConfig.synthesizerUpgrade4OutputMultiplier;
            case 4 -> FIConfig.synthesizerUpgrade5OutputMultiplier;
            case 5 -> FIConfig.synthesizerUpgrade6OutputMultiplier;
            case 6 -> FIConfig.synthesizerUpgrade7OutputMultiplier;
            case 7 -> FIConfig.synthesizerUpgrade8OutputMultiplier;
            case 8 -> FIConfig.synthesizerUpgrade9OutputMultiplier;
            case 9 -> FIConfig.synthesizerUpgrade10OutputMultiplier;
            default -> 0;
        };
    }
}
