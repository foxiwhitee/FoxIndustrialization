package foxiwhitee.FoxIndustrialization.integration.cofh.items;

import foxiwhitee.FoxIndustrialization.api.IPowerConverterUpgradeItem;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.items.ItemWithMeta;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPowerConverterUpgrade extends ItemWithMeta implements IPowerConverterUpgradeItem {
    public ItemPowerConverterUpgrade(String name) {
        super(name, "upgrades/", "EU", "RF");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            if (stack.getItemDamage() == 0) {
                list.add(LocalizationUtils.localize("tooltip.upgrade.pc.storageMult", "§3", "EU", EnergyUtility.formatNumber(getStorageEnergyEUMultiplier(stack))));
                list.add(LocalizationUtils.localize("tooltip.upgrade.pc.outputMult", "§3", "EU", EnergyUtility.formatNumber(getOutputEnergyEUMultiplier(stack))));
            } else if (stack.getItemDamage() == 1) {
                list.add(LocalizationUtils.localize("tooltip.upgrade.pc.storageMult", "§4", "RF", EnergyUtility.formatNumber(getStorageEnergyRFMultiplier(stack))));
                list.add(LocalizationUtils.localize("tooltip.upgrade.pc.outputMult", "§4", "RF", EnergyUtility.formatNumber(getOutputEnergyRFMultiplier(stack))));
            }
        }
    }

    @Override
    public double getStorageEnergyEUMultiplier(ItemStack stack) {
        if (stack.getItemDamage() == 0) {
            return Math.pow(FIConfig.powerConverterUpgradeEUStorageMultiplier, stack.stackSize);
        } else {
            return 1;
        }
    }

    @Override
    public double getStorageEnergyRFMultiplier(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return Math.pow(FIConfig.powerConverterUpgradeRFStorageMultiplier, stack.stackSize);
        } else {
            return 1;
        }
    }

    @Override
    public double getOutputEnergyEUMultiplier(ItemStack stack) {
        if (stack.getItemDamage() == 0) {
            return Math.pow(FIConfig.powerConverterUpgradeEUOutputMultiplier, stack.stackSize);
        } else {
            return 1;
        }
    }

    @Override
    public double getOutputEnergyRFMultiplier(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return Math.pow(FIConfig.powerConverterUpgradeRFOutputMultiplier, stack.stackSize);
        } else {
            return 1;
        }
    }
}
