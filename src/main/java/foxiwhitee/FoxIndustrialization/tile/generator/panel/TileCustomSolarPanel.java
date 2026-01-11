package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
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
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileCustomSolarPanel extends TileIC2Inv implements IEnergySource {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 2, 1);
    private boolean sunIsUp, skyIsVisible, wetBiome, noSunWorld;
    private int updateTick;
    private final double dayGenerating, nightGenerating, output;
    protected double generating;

    public TileCustomSolarPanel(double dayGenerating, double nightGenerating, double output, double storage) {
        this.dayGenerating = dayGenerating;
        this.nightGenerating = nightGenerating;
        this.output = output;
        this.maxEnergy = storage;
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
            updateVisibility();
        }
        boolean needUpdate = gainGenerating();

        if (this.generating > 0) {
            if (this.energy + this.generating <= this.maxEnergy) {
                this.energy += this.generating;
            } else {
                this.energy = this.maxEnergy;
            }
            needUpdate = true;
        }
        needUpdate |= chargeItems();

        if (needUpdate) {
            markForUpdate();
        }
    }

    protected boolean gainGenerating() {
        double oldGenerating = this.generating;
        this.generating = 0;
        if (this.sunIsUp && this.skyIsVisible) {
            this.generating = dayGenerating;
        } else if (this.skyIsVisible) {
            this.generating = nightGenerating;
        }
        return oldGenerating != this.generating;
    }

    protected boolean chargeItems() {
        boolean needUpdate = false;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack chargeItem = inventory.getStackInSlot(i);
            if (this.energy >= 1 && chargeItem != null) {
                double sent = ElectricItem.manager.charge(chargeItem, energy, 64, false, false);
                this.energy -= sent;
                needUpdate |= sent > 0;
            }
        }
        return needUpdate;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        data.setBoolean("sunIsUp", sunIsUp);
        data.setBoolean("skyIsVisible", skyIsVisible);
        data.setBoolean("wetBiome", wetBiome);
        data.setBoolean("noSunWorld", noSunWorld);
        data.setDouble("generating", generating);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        sunIsUp = data.getBoolean("sunIsUp");
        skyIsVisible = data.getBoolean("skyIsVisible");
        wetBiome = data.getBoolean("wetBiome");
        noSunWorld = data.getBoolean("noSunWorld");
        generating = data.getDouble("generating");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeDouble(generating);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        double oldGenerating = this.generating;
        this.generating = data.readDouble();
        return old || oldGenerating != this.generating;
    }

    public void updateVisibility() {
        boolean isRaining = worldObj.isRaining() || worldObj.isThundering();
        boolean rainWeather = this.wetBiome && isRaining;

        this.sunIsUp = worldObj.isDaytime() && !rainWeather;

        boolean canSeeSky = worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord);
        this.skyIsVisible = canSeeSky && !this.noSunWorld;
    }

    @Override
    public void initialize() {
        super.initialize();
        this.wetBiome = super.worldObj.getWorldChunkManager().getBiomeGenAt(super.xCoord, super.zCoord).getIntRainfall() > 0;
        this.noSunWorld = super.worldObj.provider.hasNoSky;
        updateVisibility();
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

    public double getGenerating() {
        return generating;
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
}
