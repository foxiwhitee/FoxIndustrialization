package foxiwhitee.FoxIndustrialization.tile.storage;

import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEnergyStorage extends TileIC2Inv implements IEnergySource {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 2, 1);
    private final int tier;
    private final double output;

    public TileEnergyStorage(int tier, double storage, double output) {
        this.tier = tier;
        this.output = output;
        this.maxEnergy = storage;
    }

    @TileEvent(TileEventType.TICK)
    @Override
    public void tick() {
        super.tick();
        if (this.worldObj.isRemote) {
            return;
        }

        boolean needUpdate = false;
        ItemStack chargeItem = inventory.getStackInSlot(0);
        ItemStack dischargeItem = inventory.getStackInSlot(1);
        if (this.energy >= 1 && chargeItem != null) {
            double sent = ElectricItem.manager.charge(chargeItem, energy, this.tier, false, false);
            this.energy -= sent;
            needUpdate |= sent > 0;
        }

        if (this.getDemandedEnergy() > 0 && dischargeItem != null) {
            double gain = discharge(dischargeItem);

            this.energy += gain;
            needUpdate |= gain > 0;
        }

        if (needUpdate) {
            this.markForUpdate();
        }
    }

    private double discharge(ItemStack dischargeItem) {
        double realAmount = ElectricItem.manager.discharge(dischargeItem, maxEnergy - energy, this.tier, false, true, false);
        if (realAmount <= 0) {
            realAmount = Info.itemEnergy.getEnergyValue(dischargeItem);
            if (realAmount <= 0) {
                return 0;
            }

            --dischargeItem.stackSize;
            if (dischargeItem.stackSize <= 0) {
                this.inventory.setInventorySlotContents(1, null);
            }
        }

        return realAmount;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return forgeDirection != getForward();
    }

    @Override
    public double getOfferedEnergy() {
        return Math.min(energy, output);
    }

    @Override
    public void drawEnergy(double amount) {
        this.energy -= amount;
        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
        }
        markForUpdate();
    }

    public double getOutput() {
        return output;
    }

    @Override
    public int getSourceTier() {
        return tier;
    }

    @Override
    public boolean emitsEnergyTo(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return forgeDirection == getForward();
    }

    public void setEnergy(double value) {
        this.energy = value;
        markForUpdate();
    }

    public abstract String getInventoryFilter();
}
