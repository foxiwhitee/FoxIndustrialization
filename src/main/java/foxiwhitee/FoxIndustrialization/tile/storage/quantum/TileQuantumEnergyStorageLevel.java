package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyContainerItem;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyHandler;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyReceiver;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorageLevel;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore")
public abstract class TileQuantumEnergyStorageLevel extends TileNanoEnergyStorageLevel implements IEnergyHandler, IDoubleEnergyHandler {
    public TileQuantumEnergyStorageLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }

    @TileEvent(TileEventType.TICK)
    @Override
    public void tick() {
        super.tick();
        if (this.worldObj.isRemote) {
            return;
        }
        if (supportsRF() && FICore.ifCoFHCoreIsLoaded) {
            boolean needUpdate = pushEnergy();

            ItemStack chargeItem = getInternalInventory().getStackInSlot(0);
            ItemStack dischargeItem = getInternalInventory().getStackInSlot(1);
            boolean doIf = supportsRF() && FICore.ifCoFHCoreIsLoaded;

            if (chargeItem != null) {
                double temp = EnergyUtility.handleItemEnergy(chargeItem, energy, getOutput(), maxEnergy, true, doIf, true);
                energy -= temp;
                needUpdate |= temp > 0;
            } else if (dischargeItem != null) {
                double temp = EnergyUtility.handleItemEnergy(dischargeItem, energy, getOutput(), maxEnergy, false, doIf, true);
                energy += temp;
                needUpdate |= temp > 0;
            }

            if (needUpdate) {
                this.markForUpdate();
            }
        }
    }

    private boolean pushEnergy() {
        double pushedEnergy = EnergyUtility.pushEnergy(getForward(), energy, getOutput(), this, supportsRF() && FICore.ifCoFHCoreIsLoaded, true);
        this.energy -= pushedEnergy;
        return pushedEnergy > 0;
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, this.receiveDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, this.extractDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, this.getDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, this.getMaxDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return canConnectDoubleEnergy(forgeDirection);
    }

    @Override
    public boolean canConnectDoubleEnergy(ForgeDirection direction) {
        return supportsRF() && FICore.ifCoFHCoreIsLoaded;
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (direction == getForward() || !(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
            return 0;
        }
        double energyReceived = Math.min(maxEnergy - energy, Math.min(getOutput() * FoxLibConfig.rfInEu, maxReceive / FoxLibConfig.rfInEu));

        if (!simulate) {
            energy += energyReceived;
            markForUpdate();
        }
        return energyReceived * FoxLibConfig.rfInEu;
    }

    @Override
    public double extractDoubleEnergy(ForgeDirection direction, double maxExtract, boolean simulate) {
        if (direction != getForward() || !(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
            return 0;
        }
        double energyExtracted = Math.min(energy, Math.min(getOutput() * FoxLibConfig.rfInEu, maxExtract / FoxLibConfig.rfInEu));

        if (!simulate) {
            energy -= energyExtracted;
            markForUpdate();
        }
        return energyExtracted * FoxLibConfig.rfInEu;
    }

    @Override
    public double getDoubleEnergyStored(ForgeDirection direction) {
        if (!(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
            return 0;
        }
        return getEnergy() * FoxLibConfig.rfInEu;
    }

    @Override
    public double getMaxDoubleEnergyStored(ForgeDirection direction) {
        if (!(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
            return 0;
        }
        return getMaxEnergy() * FoxLibConfig.rfInEu;
    }

    public abstract boolean supportsRF();
}
