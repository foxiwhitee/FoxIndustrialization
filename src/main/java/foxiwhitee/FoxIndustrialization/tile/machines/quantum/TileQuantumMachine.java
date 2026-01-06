package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyReceiver;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHCore")
public abstract class TileQuantumMachine extends TileNanoMachine implements IEnergyReceiver, IDoubleEnergyReceiver {
    public TileQuantumMachine(MachineTier tier, double maxEnergy, int itemsPerOp, double energyPerTick) {
        super(tier, maxEnergy, itemsPerOp, energyPerTick);
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, this.receiveDoubleEnergy(forgeDirection, i, b));
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
        if (direction == ForgeDirection.UP || direction == getForward() || !(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
            return 0;
        }
        double energyReceived = Math.min(maxEnergy - energy, maxReceive / FoxLibConfig.rfInEu);

        if (!simulate) {
            energy += energyReceived;
            markForUpdate();
        }
        return energyReceived * FoxLibConfig.rfInEu;
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

    protected abstract boolean supportsRF();
}
