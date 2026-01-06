package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyContainerItem;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyProvider;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyReceiver;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import ic2.api.item.ElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHCore")
public class TileQuantumGenerator extends TileGenerator implements IEnergyProvider, IDoubleEnergyProvider {
    public TileQuantumGenerator() {
        super(MachineTier.QUANTUM, FIConfig.generatorQuantumOutput, FIConfig.generatorQuantumProduction, FIConfig.generatorQuantumStorage);
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (worldObj.isRemote) {
            return;
        }
        boolean needUpdate = false;
        if (FIConfig.generatorQuantumSupportsRF && FICore.ifCoFHCoreIsLoaded) {
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                double pushedEnergy = EnergyUtility.pushEnergy(side, energy, output, this, true, true);
                this.energy -= pushedEnergy;
                needUpdate |= pushedEnergy > 0;
            }
        }
        if (needUpdate) {
            markForUpdate();
        }
    }

    protected boolean chargeItems() {
        boolean needUpdate = false;
        for (int i = 0; i < getChargeInventory().getSizeInventory(); i++) {
            ItemStack chargeItem = getChargeInventory().getStackInSlot(0);
            if (this.energy >= 1 && chargeItem != null) {
                double temp = EnergyUtility.handleItemEnergy(chargeItem, energy, output, maxEnergy, true, FIConfig.generatorQuantumSupportsRF && FICore.ifCoFHCoreIsLoaded, true);
                this.energy -= temp;
                needUpdate |= temp > 0;
                if (temp <= 0) {
                    temp = ElectricItem.manager.charge(chargeItem, energy, 64, false, false);
                    this.energy -= temp;
                    needUpdate |= temp > 0;
                }
            }
        }
        return needUpdate;
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, extractDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, getDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, getMaxDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return canConnectDoubleEnergy(forgeDirection);
    }

    @Override
    public double extractDoubleEnergy(ForgeDirection direction, double maxExtract, boolean simulate) {
        if (!canConnectEnergy(direction)) {
            return 0;
        }
        double energyExtracted = Math.min(energy, Math.min(output * FoxLibConfig.rfInEu, maxExtract / FoxLibConfig.rfInEu));

        if (!simulate) {
            energy -= energyExtracted;
            markForUpdate();
        }
        return energyExtracted * FoxLibConfig.rfInEu;
    }

    @Override
    public double getDoubleEnergyStored(ForgeDirection direction) {
        if (!canConnectEnergy(direction)) {
            return 0;
        }
        return energy * FoxLibConfig.rfInEu;
    }

    @Override
    public double getMaxDoubleEnergyStored(ForgeDirection direction) {
        if (!canConnectEnergy(direction)) {
            return 0;
        }
        return maxEnergy * FoxLibConfig.rfInEu;
    }

    @Override
    public boolean canConnectDoubleEnergy(ForgeDirection direction) {
        return FIConfig.generatorQuantumSupportsRF && FICore.ifCoFHCoreIsLoaded;
    }
}
