package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyHandler;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoChargePadLevel;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore")
public abstract class TileQuantumChargePadLevel extends TileNanoChargePadLevel implements IEnergyHandler, IDoubleEnergyHandler {
    public TileQuantumChargePadLevel(int tier, double storage, double output) {
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

    @Override
    protected void chargePlayerItems(EntityPlayer player) {
        boolean doIf = supportsRF() && FICore.ifCoFHCoreIsLoaded;

        for(ItemStack current : player.inventory.armorInventory) {
            if (current != null) {
                double temp = EnergyUtility.handleItemEnergy(current, energy, getOutput(), maxEnergy, true, doIf, true);
                energy -= temp;
                if (temp <= 0) {
                    this.chargeItems(current);
                }
            }
        }

        for(ItemStack current : player.inventory.mainInventory) {
            if (current != null) {
                double temp = EnergyUtility.handleItemEnergy(current, energy, getOutput(), maxEnergy, true, doIf, true);
                energy -= temp;
                if (temp <= 0) {
                    this.chargeItems(current);
                }
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
        return supportsRF() && direction != ForgeDirection.UP && FICore.ifCoFHCoreIsLoaded;
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (direction == ForgeDirection.UP || direction == getForward() || !(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
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
        if (direction == ForgeDirection.UP || direction != getForward() || !(supportsRF() && FICore.ifCoFHCoreIsLoaded)) {
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
