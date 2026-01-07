package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.List;

public abstract class TileGenerator extends TileIC2Inv implements IEnergySource {
    private final FoxInternalInventory inventory;
    private final FoxInternalInventory charge = new FoxInternalInventory(this, 3, 1);
    protected final double output;
    private int[] fuel;
    private int[] fuelNeed;
    private final int production;

    public TileGenerator(MachineTier tier, double output, int production, double storage) {
        this.inventory = new FoxInternalInventory(this, tier.getInvInpSize());
        this.output = output;
        this.production = production;
        this.maxEnergy = storage;
        this.fuel = new int[tier.getMaxOperations()];
        this.fuelNeed = new int[tier.getMaxOperations()];
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        boolean needUpdate = false;
        if (needsFuel()) {
            needUpdate |= gainFuel();
        }

        needUpdate |= gainEnergy();

        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
            needUpdate = true;
        }

        needUpdate |= chargeItems();

        if (needUpdate) {
            markForUpdate();
        }
    }

    protected boolean chargeItems() {
        boolean needUpdate = false;
        for (int i = 0; i < charge.getSizeInventory(); i++) {
            ItemStack chargeItem = charge.getStackInSlot(i);
            if (this.energy >= 1 && chargeItem != null) {
                double sent = ElectricItem.manager.charge(chargeItem, energy, 64, false, false);
                this.energy -= sent;
                needUpdate |= sent > 0;
            }
        }
        return needUpdate;
    }

    private boolean gainEnergy() {
        boolean output = false;
        for (int i = 0; i < this.fuel.length; i++) {
            if (this.fuel[i] > 0 && isConverting()) {
                this.energy += this.production;
                this.fuel[i]--;
                output = true;
            }
        }
        return output;
    }

    private boolean isConverting() {
        return (this.getAllFuel() > 0 && this.energy + this.production <= this.maxEnergy);
    }

    private boolean needsFuel() {
        return (this.anyFuelIsNull() && this.energy + this.production <= this.maxEnergy);
    }

    private boolean anyFuelIsNull() {
        for (int j : this.fuel) {
            if (j == 0) {
                return true;
            }
        }
        return false;
    }

    private int getAllFuel() {
        int fuel = 0;
        for (int j : this.fuel) {
            fuel += j;
        }
        return fuel;
    }

    private boolean gainFuel() {
        boolean needUpdate = false;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && this.fuel[i] <= 0) {
                this.fuel[i] += TileEntityFurnace.getItemBurnTime(stack);
                this.fuelNeed[i] = TileEntityFurnace.getItemBurnTime(stack);
                stack.stackSize--;
                if (stack.stackSize <= 0) {
                    inventory.setInventorySlotContents(i, null);
                }
                needUpdate = true;
            }
        }
        return needUpdate;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        charge.writeToNBT(data, "charge");
        data.setIntArray("fuel", fuel);
        data.setIntArray("fuelNeed", fuelNeed);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        charge.readFromNBT(data, "charge");
        fuel = data.getIntArray("fuel");
        fuelNeed = data.getIntArray("fuelNeed");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        for (int j : fuel) {
            data.writeInt(j);
        }
        for (int j : fuelNeed) {
            data.writeInt(j);
        }
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        int[] oldFuel = Arrays.copyOf(fuel, fuel.length);
        this.fuel = new int[fuel.length];
        for (int i = 0; i < this.fuel.length; i++) {
            this.fuel[i] = data.readInt();
        }
        int[] oldFuelNeed = Arrays.copyOf(fuelNeed, fuelNeed.length);
        this.fuelNeed = new int[fuelNeed.length];
        for (int i = 0; i < this.fuelNeed.length; i++) {
            this.fuelNeed[i] = data.readInt();
        }
        return old || !Arrays.equals(oldFuel, fuel) || !Arrays.equals(oldFuelNeed, fuelNeed);
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getChargeInventory() {
        return charge;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        int[] slots = new int[getSizeInventory()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return false;
    }

    @Override
    public double getOfferedEnergy() {
        return Math.min(this.energy, this.output);
    }

    @Override
    public void drawEnergy(double amount) {
        this.energy -= amount;
        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
        }
        markForUpdate();
    }

    @Override
    public int getSourceTier() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean emitsEnergyTo(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return true;
    }

    public int[] getFuel() {
        return fuel;
    }

    public int[] getFuelNeed() {
        return fuelNeed;
    }

    public int getProduction() {
        return production;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);

        for (int i = 0; i < charge.getSizeInventory(); i++) {
            if (charge.getStackInSlot(i) != null) {
                drops.add(charge.getStackInSlot(i));
            }
        }
    }
}
