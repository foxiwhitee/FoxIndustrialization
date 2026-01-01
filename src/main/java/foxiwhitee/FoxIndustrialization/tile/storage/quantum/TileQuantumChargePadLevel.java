package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyContainerItem;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyHandler;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoChargePadLevel;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorageLevel;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
        if (supportsRF()) {
            pushEnergy();

            boolean needUpdate = false;
            ItemStack chargeItem = getInternalInventory().getStackInSlot(1);
            ItemStack dischargeItem = getInternalInventory().getStackInSlot(0);

            if (chargeItem != null && chargeItem.getItem() instanceof IDoubleEnergyContainerItem item) {
                needUpdate |= chargeDoubleRFItem(chargeItem);
            } else if (dischargeItem != null && dischargeItem.getItem() instanceof IDoubleEnergyContainerItem item) {
                needUpdate |= dischargeDoubleRFItem(dischargeItem);
            } else if (FICore.ifCoFHCoreIsLoaded) {
                needUpdate |= handleCoFHItems(chargeItem, dischargeItem);
            }

            if (needUpdate) {
                this.markForUpdate();
            }
        }
    }

    @Override
    protected void chargePlayerItems(EntityPlayer player) {
        for(ItemStack current : player.inventory.armorInventory) {
            if (current != null) {
                if (current.getItem() instanceof IDoubleEnergyContainerItem) {
                    chargeDoubleRFItem(current);
                } else if (FICore.ifCoFHCoreIsLoaded) {
                    if (!tryChargeRFItem(current)) {
                        this.chargeItems(current);
                    }
                } else {
                    this.chargeItems(current);
                }
            }
        }

        for(ItemStack current : player.inventory.mainInventory) {
            if (current != null) {
                if (current.getItem() instanceof IDoubleEnergyContainerItem) {
                    chargeDoubleRFItem(current);
                } else if (FICore.ifCoFHCoreIsLoaded) {
                    if (!tryChargeRFItem(current)) {
                        this.chargeItems(current);
                    }
                } else {
                    this.chargeItems(current);
                }
            }
        }
    }

    protected boolean tryChargeRFItem(ItemStack chargeItem) {
        boolean charged = chargeRFItem(chargeItem);
        return charged;
    }

    protected boolean chargeDoubleRFItem(ItemStack chargeItem) {
        if (this.energy > 0 && chargeItem != null && chargeItem.getItem() instanceof IDoubleEnergyContainerItem item) {
            double toSend = Math.min(this.energy, this.getOutput() * FIConfig.rfInEu);
            double accepted = item.receiveDoubleEnergy(chargeItem, toSend, false);

            this.energy -= accepted / FIConfig.rfInEu;
            return accepted > 0;
        }
        return false;
    }

    protected boolean dischargeDoubleRFItem(ItemStack dischargeItem) {
        double demand = this.maxEnergy - this.energy;
        if (demand > 0 && dischargeItem != null && dischargeItem.getItem() instanceof IDoubleEnergyContainerItem item) {
            double toExtract = Math.min(demand, this.getOutput() * FIConfig.rfInEu);
            double extracted = item.extractDoubleEnergy(dischargeItem, toExtract, false);

            this.energy += extracted / FIConfig.rfInEu;
            return extracted > 0;
        }
        return false;
    }

    protected boolean handleCoFHItems(ItemStack chargeItem, ItemStack dischargeItem) {
        boolean charged = chargeRFItem(chargeItem);
        boolean discharged = dischargeRFItem(dischargeItem);
        return charged || discharged;
    }

    @Optional.Method(modid = "CoFHCore")
    protected boolean chargeRFItem(ItemStack chargeItem) {
        if (this.energy > 0 && chargeItem != null && chargeItem.getItem() instanceof IEnergyContainerItem item) {
            int toSend = (int) Math.min(this.energy, this.getOutput() * FIConfig.rfInEu);
            int accepted = item.receiveEnergy(chargeItem, toSend, false);

            this.energy -= accepted / FIConfig.rfInEu;
            return accepted > 0;
        }
        return false;
    }

    @Optional.Method(modid = "CoFHCore")
    protected boolean dischargeRFItem(ItemStack dischargeItem) {
        int demand = (int) (this.maxEnergy - this.energy);
        if (demand > 0 && dischargeItem != null && dischargeItem.getItem() instanceof IEnergyContainerItem item) {
            int toExtract = (int) Math.min(demand, this.getOutput() * FIConfig.rfInEu);
            int extracted = item.extractEnergy(dischargeItem, toExtract, false);

            this.energy += extracted / FIConfig.rfInEu;
            return extracted > 0;
        }
        return false;
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
        } else if (FICore.ifCoFHCoreIsLoaded) {
            pushIntRFEnergy(tile, side);
        }
    }

    @Optional.Method(modid = "CoFHCore")
    protected void pushIntRFEnergy(TileEntity tile, ForgeDirection side) {
        if (tile instanceof IEnergyReceiver receiver) {
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
        return supportsRF() && direction != ForgeDirection.UP;
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (direction == ForgeDirection.UP || direction == getForward()) {
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
        if (direction == ForgeDirection.UP || direction != getForward()) {
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
