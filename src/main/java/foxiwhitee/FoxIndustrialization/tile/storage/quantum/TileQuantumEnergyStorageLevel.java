package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyHandler;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorageLevel;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
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
        if (supportsRF()) {
            pushEnergy();
        }
    }

    private void pushEnergy() {
        if (this.energy <= 0) return;

        ForgeDirection side = getForward();
        TileEntity tile = worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);

        if (tile instanceof IDoubleEnergyHandler receiver) {
            double energyToPush =  Math.min(this.energy * FIConfig.rfInEu, this.getOutput());
            double accepted = receiver.receiveDoubleEnergy(side.getOpposite(), energyToPush, false) / FIConfig.rfInEu;
            this.energy -= accepted;
            markForUpdate();
        } else if (FICore.ifCoFHCoreIsLoaded && tile instanceof IEnergyReceiver receiver) {
            int energyToPush = (int) Math.min(this.energy * FIConfig.rfInEu, this.getOutput());
            int accepted = receiver.receiveEnergy(side.getOpposite(), energyToPush, false) / FIConfig.rfInEu;
            this.energy -= accepted;
            markForUpdate();
        }
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
        return supportsRF();
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (direction == getForward()) {
            return 0;
        }
        double energyReceived = Math.min(maxEnergy - energy, Math.min(getOutput() * FIConfig.rfInEu, maxReceive / FIConfig.rfInEu));

        if (!simulate) {
            energy += energyReceived;
            markForUpdate();
        }
        return energyReceived * FIConfig.rfInEu;
    }

    @Override
    public double extractDoubleEnergy(ForgeDirection direction, double maxExtract, boolean simulate) {
        if (direction != getForward()) {
            return 0;
        }
        double energyExtracted = Math.min(energy, Math.min(getOutput() * FIConfig.rfInEu, maxExtract / FIConfig.rfInEu));

        if (!simulate) {
            energy -= energyExtracted;
            markForUpdate();
        }
        return energyExtracted * FIConfig.rfInEu;
    }

    @Override
    public double getDoubleEnergyStored(ForgeDirection direction) {
        return getEnergy() * FIConfig.rfInEu;
    }

    @Override
    public double getMaxDoubleEnergyStored(ForgeDirection direction) {
        return getMaxEnergy() * FIConfig.rfInEu;
    }

    public abstract boolean supportsRF();
}
