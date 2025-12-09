package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxLib.tile.FoxBaseTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import ic2.api.energy.tile.IEnergySink;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileIC2 extends FoxBaseTile implements IEnergySink {
    protected double maxEnergy = 5000, energy;

    public TileIC2() {

    }

    @TileEvent(TileEventType.TICK)
    public void tick() {

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
        if (this.energy >= (double)this.maxEnergy) {
            return amount;
        } else {
            this.energy += amount;
            return (double)0.0F;
        }
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return true;
    }
}
