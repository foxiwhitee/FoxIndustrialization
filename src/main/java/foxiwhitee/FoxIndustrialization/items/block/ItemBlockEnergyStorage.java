package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ItemBlockEnergyStorage extends ModItemBlock {
    public ItemBlockEnergyStorage(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setDouble("energy", 0);
                stack.setTagCompound(tag);
            }
            double energy = tag.getDouble("energy");
            if (isBlock(ModBlocks.basicEnergyStorage, ModBlocks.basicChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageBasicOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageBasicStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
            }
            if (isBlock(ModBlocks.advancedEnergyStorage, ModBlocks.advancedChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageAdvancedOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageAdvancedStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
            }
            if (isBlock(ModBlocks.hybridEnergyStorage, ModBlocks.hybridChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageHybridOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageHybridStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
            }
            if (isBlock(ModBlocks.nanoEnergyStorage, ModBlocks.nanoChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageNanoOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageNanoStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
            }
            if (isBlock(ModBlocks.ultimateEnergyStorage, ModBlocks.ultimateChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageUltimateOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageUltimateStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
                if (FICore.ifCoFHCoreIsLoaded && FIConfig.energyStorageUltimateSupportsRF) {
                    list.add(LocalizationUtils.localize("tooltip.energyStorage.supportsRF", FoxLibConfig.rfInEu));
                }
            }
            if (isBlock(ModBlocks.quantumEnergyStorage, ModBlocks.quantumChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageQuantumOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageQuantumStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
                if (FICore.ifCoFHCoreIsLoaded && FIConfig.energyStorageQuantumSupportsRF) {
                    list.add(LocalizationUtils.localize("tooltip.energyStorage.supportsRF", FoxLibConfig.rfInEu));
                }
            }
            if (isBlock(ModBlocks.singularEnergyStorage, ModBlocks.singularChargePad)) {
                list.add(LocalizationUtils.localize("tooltip.energyStorage.output", EnergyUtility.formatNumber(FIConfig.energyStorageSingularOutput)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.capacity", EnergyUtility.formatNumber(FIConfig.energyStorageSingularStorage)));
                list.add(LocalizationUtils.localize("tooltip.energyStorage.store", EnergyUtility.formatNumber(energy)));
                if (FICore.ifCoFHCoreIsLoaded && FIConfig.energyStorageSingularSupportsRF) {
                    list.add(LocalizationUtils.localize("tooltip.energyStorage.supportsRF", FoxLibConfig.rfInEu));
                }
            }
        }
    }
}
