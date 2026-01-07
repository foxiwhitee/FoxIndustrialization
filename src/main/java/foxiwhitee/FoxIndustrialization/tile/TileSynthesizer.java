package foxiwhitee.FoxIndustrialization.tile;

import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.api.IHasSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.api.ISynthesizerSunUpgrade;
import foxiwhitee.FoxIndustrialization.api.ISynthesizerUpgrade;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyProvider;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import ic2.api.energy.tile.IEnergySource;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHCore")
public class TileSynthesizer extends TileIC2Inv implements IEnergySource, IEnergyProvider, IDoubleEnergyProvider {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 21);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 2, 1);
    private boolean isDay, needSun = true;
    private double generating, bonus, updateTick, output;

    public TileSynthesizer() {
        this.output = FIConfig.synthesizerOutput;
        this.maxEnergy = FIConfig.synthesizerStorage;
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (worldObj.isRemote) {
            return;
        }
        int needTicksToUpdate = 20;
        if (updateTick++ >= needTicksToUpdate) {
            updateTick = 0;
            if (needUpdate()) {
                updateGenerating();
            }
        }
        boolean needUpdate = false;

        if (this.generating > 0) {
            if (this.energy + this.generating <= this.maxEnergy) {
                this.energy += this.generating;
            } else {
                this.energy = this.maxEnergy;
            }
            needUpdate = true;
        }
        if (supportsRF()) {
            pushEnergy();
        }

        if (needUpdate) {
            markForUpdate();
        }
    }

    private void pushEnergy() {
        boolean needUpdate = false;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            double pushedEnergy = EnergyUtility.pushEnergy(side, energy, output, this, true, false);
            this.energy -= pushedEnergy;
            needUpdate |= pushedEnergy > 0;
        }
        if (needUpdate) {
            markForUpdate();
        }
    }

    private boolean needUpdate() {
        if (this.needSun) {
            boolean oldIsDay = this.isDay;
            this.isDay = worldObj.isDaytime();
            return oldIsDay != this.isDay;
        }
        return false;
    }

    private void updateGenerating() {
        boolean sun = this.isDay || !this.needSun;
        this.generating = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof IHasSynthesizerIntegration item) {
                this.generating += (sun ? item.getDayGen(stack) : item.getNightGen(stack)) * stack.stackSize;
            }
        }
        this.generating += this.generating * (bonus / 100);
        markForUpdate();
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        upgrades.writeToNBT(data, "upgrades");
        data.setBoolean("isDay", isDay);
        data.setBoolean("needSun", needSun);
        data.setDouble("generating", generating);
        data.setDouble("bonus", bonus);
        data.setDouble("output", output);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        upgrades.readFromNBT(data, "upgrades");
        isDay = data.getBoolean("isDay");
        needSun = data.getBoolean("needSun");
        generating = data.getDouble("generating");
        bonus = data.getDouble("bonus");
        output = data.getDouble("output");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeDouble(generating);
        data.writeDouble(output);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        double oldGenerating = generating, oldOutput = output;
        generating = data.readDouble();
        output = data.readDouble();
        return old || oldGenerating != generating || oldOutput != output;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            this.needSun = true;
            this.bonus = 0;
            this.output = FIConfig.synthesizerOutput;
            this.maxEnergy = FIConfig.synthesizerStorage;

            for (int j = 0; j < upgrades.getSizeInventory(); j++) {
                ItemStack stack = upgrades.getStackInSlot(j);
                if (stack != null) {
                    if (stack.getItem() instanceof ISynthesizerSunUpgrade item) {
                        this.needSun = item.needSun(stack);
                    }
                    if (stack.getItem() instanceof ISynthesizerUpgrade item) {
                        this.bonus = item.getBonus(stack);
                        this.output = safeMultiply(this.output, item.getOutputMultiplier(stack));
                        this.maxEnergy = safeMultiply(this.maxEnergy, item.getStorageMultiplier(stack));
                    }
                }
            }
            this.energy = Math.min(this.energy, this.maxEnergy);
        }
        updateGenerating();
    }

    private boolean supportsRF() {
        return FIConfig.synthesizerSupportsRF && FICore.ifCoFHCoreIsLoaded;
    }

    @Override
    public double getOfferedEnergy() {
        return Math.min(energy, output);
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
        return true;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return false;
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
        double energyExtracted = Math.min(energy, Math.min(FIConfig.infinityGeneratorOutput * FoxLibConfig.rfInEu, maxExtract / FoxLibConfig.rfInEu));

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
        return supportsRF();
    }

    public double getGenerating() {
        return generating;
    }

    public double getOutput() {
        return output;
    }
}
