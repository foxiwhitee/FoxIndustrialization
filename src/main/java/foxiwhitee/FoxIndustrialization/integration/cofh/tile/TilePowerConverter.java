package foxiwhitee.FoxIndustrialization.integration.cofh.tile;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.api.IPowerConverterUpgradeItem;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyHandler;
import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyReceiver;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.integration.cofh.utils.ButtonConverterMode;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.api.energy.tile.IEnergySource;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePowerConverter extends TileIC2Inv implements IEnergyHandler, IDoubleEnergyHandler, IEnergySource {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 3);
    private double energyRF, maxEnergyRF, outputEU, outputRF;
    private ButtonConverterMode mode = ButtonConverterMode.EU;

    public TilePowerConverter() {
        this.maxEnergy = FIConfig.powerConverterEUStorage;
        this.maxEnergyRF = FIConfig.powerConverterRFStorage;
        this.outputEU = FIConfig.powerConverterEUPerTick;
        this.outputRF = FIConfig.powerConverterRFPerTick;
    }

    @TileEvent(TileEventType.TICK)
    @Override
    public void tick() {
        super.tick();
        if (worldObj.isRemote) {
            return;
        }
        if (mode == ButtonConverterMode.EU) {
            double needEnergy = maxEnergy - energy;
            if (needEnergy > 0 && energyRF > 0 && energyRF % FIConfig.rfInEu == 0) {
                double canGetEnergy = energyRF / FIConfig.rfInEu;
                energyRF -= Math.min(needEnergy, canGetEnergy) * FIConfig.rfInEu;
                energy += Math.min(needEnergy, canGetEnergy);
                markForUpdate();
            }
        } else if (mode == ButtonConverterMode.RF) {
            pushEnergy();
            double needEnergy = maxEnergyRF - energyRF;
            if (needEnergy > 0 && energy > 0) {
                if (needEnergy % FIConfig.rfInEu != 0) {
                    needEnergy -= needEnergy % FIConfig.rfInEu;
                }
                double canGetEnergy = energy * FIConfig.rfInEu;
                energy -= Math.min(needEnergy, canGetEnergy) / FIConfig.rfInEu;
                energyRF += Math.min(needEnergy, canGetEnergy);
                markForUpdate();
            }
        }
    }

    private void pushEnergy() {
        if (this.energyRF <= 0) return;

        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);

            if (tile instanceof IDoubleEnergyReceiver receiver) {
                double energyToPush =  Math.min(this.energyRF, outputRF);
                double accepted = receiver.receiveDoubleEnergy(side.getOpposite(), energyToPush, false);
                this.energyRF -= accepted;
                markForUpdate();
            } else if (tile instanceof IEnergyReceiver receiver) {
                int energyToPush = (int) Math.min(this.energyRF, outputRF);
                int accepted = receiver.receiveEnergy(side.getOpposite(), energyToPush, false);
                this.energyRF -= accepted;
                markForUpdate();
            }
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    @Override
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        data.setDouble("energyRF", energyRF);
        data.setDouble("maxEnergyRF", maxEnergyRF);
        data.setDouble("outputEU", outputEU);
        data.setDouble("outputRF", outputRF);
        data.setByte("mode", (byte) mode.ordinal());
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    @Override
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        this.energyRF = data.getDouble("energyRF");
        this.maxEnergyRF = data.getDouble("maxEnergyRF");
        this.outputEU = data.getDouble("outputEU");
        this.outputRF = data.getDouble("outputRF");
        this.mode = ButtonConverterMode.values()[data.getByte("mode")];
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    @Override
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeDouble(energyRF);
        data.writeDouble(maxEnergyRF);
        data.writeDouble(outputEU);
        data.writeDouble(outputRF);
        data.writeByte((byte) mode.ordinal());
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    @Override
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        double oldEnergyRF = energyRF;
        double oldMaxEnergyRF = maxEnergyRF;
        double oldOutputEU = outputEU;
        double oldOutputRF = outputRF;
        ButtonConverterMode oldMode = mode;
        this.energyRF = data.readDouble();
        this.maxEnergyRF = data.readDouble();
        this.outputEU = data.readDouble();
        this.outputRF = data.readDouble();
        this.mode = ButtonConverterMode.values()[data.readByte()];
        return old || oldEnergyRF != energyRF || oldMaxEnergyRF != maxEnergyRF || oldMode != mode || oldOutputEU != outputEU || oldOutputRF != outputRF;
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
        if (iInventory == inventory) {
            this.maxEnergy = FIConfig.powerConverterEUStorage;
            this.maxEnergyRF = FIConfig.powerConverterRFStorage;
            this.outputEU = FIConfig.powerConverterEUPerTick;
            this.outputRF = FIConfig.powerConverterRFPerTick;
            for (int j = 0; j < inventory.getSizeInventory(); j++) {
                ItemStack stack = inventory.getStackInSlot(j);
                if (stack != null) {
                    if (stack.getItem() instanceof IAdvancedUpgradeItem item) {
                        this.maxEnergy *= item.getStorageEnergyMultiplier(stack);
                        this.maxEnergyRF *= item.getStorageEnergyMultiplier(stack);
                    } else if (stack.getItem() instanceof IPowerConverterUpgradeItem item) {
                        this.maxEnergy *= item.getStorageEnergyEUMultiplier(stack);
                        this.maxEnergyRF *= item.getStorageEnergyRFMultiplier(stack);
                        this.outputEU *= item.getOutputEnergyEUMultiplier(stack);
                        this.outputRF *= item.getOutputEnergyRFMultiplier(stack);
                    }
                }
            }
            if (this.maxEnergy < 0) {
                this.maxEnergy = Double.MAX_VALUE;
            }
            if (this.maxEnergyRF < 0) {
                this.maxEnergyRF = Double.MAX_VALUE;
            }
            System.out.println("Ener " + maxEnergyRF);
            this.energy = Math.min(this.energy, this.maxEnergy);
            this.energyRF = Math.min(this.energyRF, this.maxEnergyRF);
            markForUpdate();
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, receiveDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, receiveDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, getDoubleEnergyStored(forgeDirection));
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, getMaxDoubleEnergyStored(forgeDirection));
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public double extractDoubleEnergy(ForgeDirection direction, double maxExtract, boolean simulate) {
        if (mode == ButtonConverterMode.EU) {
            return 0;
        }
        double energyExtracted = Math.min(energyRF, Math.min(outputRF, maxExtract));

        if (!simulate) {
            energyRF -= energyExtracted;
            markForUpdate();
        }
        return energyExtracted;
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (mode == ButtonConverterMode.RF) {
            return 0;
        }
        double energyReceived = Math.min(maxEnergyRF - energyRF, outputRF);

        if (!simulate) {
            energyRF += energyReceived;
            markForUpdate();
        }
        return energyReceived;
    }

    @Override
    public double getDoubleEnergyStored(ForgeDirection direction) {
        return energyRF;
    }

    @Override
    public double getMaxDoubleEnergyStored(ForgeDirection direction) {
        return maxEnergyRF;
    }

    @Override
    public boolean canConnectDoubleEnergy(ForgeDirection direction) {
        return true;
    }

    @Override
    public double getOfferedEnergy() {
        if (this.mode == ButtonConverterMode.EU)
            return Math.min(this.energyRF, this.outputEU);
        return 0.0D;
    }

    @Override
    public double getDemandedEnergy() {
        if (this.mode == ButtonConverterMode.RF)
            return Math.max(this.maxEnergy - this.energy, 0.0D);
        return 0.0D;
    }

    @Override
    public double injectEnergy(ForgeDirection forgeDirection, double amount, double v1) {
        if (this.mode == ButtonConverterMode.RF) {
            if (this.energy >= this.maxEnergy) {
                return amount;
            }
            double add = Math.min(amount, maxEnergy - energy);
            this.energy += add;
            markForUpdate();
            return 0.0D;
        }
        return amount;
    }

    @Override
    public void drawEnergy(double v) {
        this.energy -= v;
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
        return mode == ButtonConverterMode.EU;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return mode == ButtonConverterMode.RF;
    }

    public void setMode(ButtonConverterMode mode) {
        this.mode = mode;
        onUnloaded();
        initialize();
        markForUpdate();
    }

    public ButtonConverterMode getMode() {
        return mode;
    }

    public double getEnergyRF() {
        return energyRF;
    }

    public double getMaxEnergyRF() {
        return maxEnergyRF;
    }

    public double getOutputEU() {
        return outputEU;
    }

    public double getOutputRF() {
        return outputRF;
    }
}
