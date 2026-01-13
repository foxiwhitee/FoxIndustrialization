package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxLib.tile.FoxBaseTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileIC2 extends FoxBaseTile implements IEnergySink {
    protected double maxEnergy = 1000000, energy;
    public boolean loaded, addedToEnergyNet, initialized;

    public TileIC2() {

    }

    @TileEvent(TileEventType.TICK)
    public void tick() {
        if (!this.initialized && super.worldObj != null) {
            this.initialize();
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        data.setDouble("energy", energy);
        data.setDouble("maxEnergy", maxEnergy);
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        energy = data.getDouble("energy");
        maxEnergy = data.getDouble("maxEnergy");
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        data.writeDouble(energy);
        data.writeDouble(maxEnergy);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        double oldEnergy = energy, oldMaxEnergy = maxEnergy;
        energy = data.readDouble();
        maxEnergy = data.readDouble();
        return oldEnergy != energy || oldMaxEnergy != maxEnergy;
    }

    public void validate() {
        super.validate();
        if (!this.isInvalid() && super.worldObj.blockExists(super.xCoord, super.yCoord, super.zCoord)) {
            this.onLoaded();
        }
    }

    public void invalidate() {
        if (this.loaded) {
            this.onUnloaded();
        }

        super.invalidate();
    }

    public void onLoaded() {
        if (!super.worldObj.isRemote) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

        this.loaded = true;
    }

    public void onChunkUnload() {
        if (this.loaded) {
            this.onUnloaded();
        }

        super.onChunkUnload();
    }

    public void onUnloaded() {
        if (!super.worldObj.isRemote && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        this.loaded = false;
    }

    public void initialize() {
        this.initialized = true;
        if (!this.addedToEnergyNet) {
            this.onLoaded();
        }
    }

    @Override
    public double getDemandedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int getSinkTier() {
        return 1;
    }

    @Override
    public double injectEnergy(ForgeDirection forgeDirection, double amount, double v1) {
        if (this.energy >= this.maxEnergy) {
            return amount;
        } else {
            double add = Math.min(amount, maxEnergy - energy);
            this.energy += add;
            markForUpdate();
            return 0;
        }
    }

    public double getEnergy() {
        return energy;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return true;
    }
}
