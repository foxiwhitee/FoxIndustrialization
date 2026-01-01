package foxiwhitee.FoxIndustrialization.tile.storage;

import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.network.INetworkClientTileEntityEventListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

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

        boolean needsInvUpdate = false;
        ItemStack chargeItem = inventory.getStackInSlot(1);
        ItemStack dischargeItem = inventory.getStackInSlot(0);
        if (this.energy >= 1 && chargeItem != null) {
            double sent = ElectricItem.manager.charge(chargeItem, energy, this.tier, false, false);
            this.energy -= sent;
            needsInvUpdate = sent > 0;
        }

        if (this.getDemandedEnergy() > 0 && dischargeItem != null) {
            double gain = discharge(dischargeItem);

            this.energy += gain;
            needsInvUpdate = gain > 0;
        }

        if (needsInvUpdate) {
            this.markForUpdate();
        }
    }

    private double discharge(ItemStack dischargeItem) {
        double realAmount = ElectricItem.manager.discharge(dischargeItem, energy, this.tier, false, true, false);
        if (realAmount <= 0) {
            realAmount = Info.itemEnergy.getEnergyValue(dischargeItem);
            if (realAmount <= 0) {
                return 0;
            }

            --dischargeItem.stackSize;
            if (dischargeItem.stackSize <= 0) {
                this.inventory.setInventorySlotContents(0, null);
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
        return !(energy >= output) ? 0 : Math.min(energy, output);
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

    public abstract InfoGui getInfoAboutGui();

    public abstract String getInventoryFilter();

    public static class InfoGui {
        private final String textureName;
        private final int yStart;
        private final int xStart;
        private final int length;

        public InfoGui(String textureName, int xStart, int yStart, int length) {
            this.textureName = textureName;
            this.yStart = yStart;
            this.xStart = xStart;
            this.length = length;
        }

        public int getLength() {
            return length;
        }

        public int getXStart() {
            return xStart;
        }

        public int getYStart() {
            return yStart;
        }

        public String getTextureName() {
            return textureName;
        }
    }
}
