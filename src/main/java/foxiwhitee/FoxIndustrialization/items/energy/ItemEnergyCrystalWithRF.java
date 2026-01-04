package foxiwhitee.FoxIndustrialization.items.energy;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyContainerItem;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

@Optional.Interface(iface = "cofh.api.energy.IEnergyContainerItem", modid = "CoFHCore")
public abstract class ItemEnergyCrystalWithRF extends ItemEnergyCrystal implements IEnergyContainerItem, IDoubleEnergyContainerItem {
    public ItemEnergyCrystalWithRF(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        super.addInformation(stack, player, list, b);
        if (FIConfig.enableTooltips) {
            if (hasRFEnergySupport()) {
                list.add(LocalizationUtils.localize("tooltip.energyCrystal.supportsRF", FIConfig.rfInEu));
            }
        }
    }

    @Override
    protected void chargeRange(EntityPlayer player, ItemStack charger, int start, int end) {
        super.chargeRange(player, charger, start, end);
        if (hasRFEnergySupport()) {
            for (int i = start; i < end; i++) {
                ItemStack target = (i >= 100) ? player.inventory.armorInventory[i - 100] : player.inventory.getStackInSlot(i);
                if (target != null && target != charger && !(target.getItem() instanceof ItemEnergyCrystal)) {
                    if (target.getItem() instanceof IDoubleEnergyContainerItem electricItem) {
                        double rfToGive = getOutput() * FIConfig.rfInEu;
                        double acceptedRF = electricItem.receiveDoubleEnergy(target, rfToGive, false);

                        if (acceptedRF > 0) {
                            double euToRecord = acceptedRF / FIConfig.rfInEu;
                            ElectricItem.manager.discharge(charger, euToRecord, getTier(), true, false, false);
                        }
                    } else {
                        chargeRangeRF(target, charger);
                    }
                }
            }
        }
    }

    @Optional.Method(modid = "CoFHCore")
    private void chargeRangeRF(ItemStack target, ItemStack charger) {
        if (target != null && target != charger && target.getItem() instanceof IEnergyContainerItem electricItem && !(target.getItem() instanceof ItemEnergyCrystal)) {
            int rfToGive = (int) (getOutput() * FIConfig.rfInEu);
            int acceptedRF = electricItem.receiveEnergy(target, rfToGive, false);

            if (acceptedRF > 0) {
                double euToRecord = (double) acceptedRF / FIConfig.rfInEu;
                ElectricItem.manager.discharge(charger, euToRecord, getTier(), true, false, false);
            }
        }
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int receiveEnergy(ItemStack itemStack, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, receiveDoubleEnergy(itemStack, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int extractEnergy(ItemStack itemStack, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, extractDoubleEnergy(itemStack, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getEnergyStored(ItemStack itemStack) {
        return (int) Math.min(Integer.MAX_VALUE, getDoubleEnergyStored(itemStack));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getMaxEnergyStored(ItemStack itemStack) {
        return (int) Math.min(Integer.MAX_VALUE, getMaxDoubleEnergyStored(itemStack));
    }

    @Override
    public double receiveDoubleEnergy(ItemStack stack, double maxReceive, boolean simulate) {
        if (!hasRFEnergySupport()) {
            return 0;
        }
        double energyStored = getDoubleEnergyStored(stack);
        double canReceive = Math.max(0, Math.min(Math.min(this.getMaxStorage() - energyStored, this.getOutput()), maxReceive));
        canReceive -= canReceive % FIConfig.rfInEu;
        if (!simulate) {
            ElectricItem.manager.charge(stack, canReceive / FIConfig.rfInEu, getTier(), false, false);
        }
        return canReceive;
    }

    @Override
    public double extractDoubleEnergy(ItemStack stack, double maxExtract, boolean simulate) {
        if (!hasRFEnergySupport()) {
            return 0;
        }
        double energyStored = getDoubleEnergyStored(stack);
        double canExtract = Math.max(0, Math.min(Math.min(energyStored, this.getOutput()), maxExtract));
        canExtract -= canExtract % FIConfig.rfInEu;
        if (!simulate) {
            ElectricItem.manager.discharge(stack, canExtract / FIConfig.rfInEu, getTier(), true, false, false);
        }
        return canExtract;
    }

    @Override
    public double getDoubleEnergyStored(ItemStack stack) {
        if (!hasRFEnergySupport()) {
            return 0;
        }
        return ElectricItem.manager.getCharge(stack) * FIConfig.rfInEu;
    }

    @Override
    public double getMaxDoubleEnergyStored(ItemStack stack) {
        if (!hasRFEnergySupport()) {
            return 0;
        }
        return getMaxStorage() * FIConfig.rfInEu;
    }

    @Override
    public boolean canWorkWithEnergy(ItemStack stack) {
        return hasRFEnergySupport();
    }

    protected abstract boolean hasRFEnergySupport();
}
