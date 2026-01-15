package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import foxiwhitee.FoxIndustrialization.tile.TileIC2;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IKineticSource;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileKineticGenerator extends TileIC2 implements IEnergySource {
    private final double productionPeerKineticUnit = 0.25 * (double) ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic");
    private final double output;
    private double production;

    public TileKineticGenerator(double output, double storage) {
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
        boolean needUpdate = gainEnergy();

        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
            needUpdate = true;
        }

        if (needUpdate) {
            markForUpdate();
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        data.setDouble("production", this.production);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        this.production = data.getDouble("production");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeDouble(this.production);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        double oldProduction = this.production;
        this.production = data.readDouble();
        return old || oldProduction != this.production;
    }

    private boolean gainEnergy() {
        ForgeDirection dir = getForward();
        TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
        if (te instanceof IKineticSource tile) {
            int kineticBandWith = tile.maxrequestkineticenergyTick(dir.getOpposite());
            double freeEUstorage = this.maxEnergy - this.energy;
            if (freeEUstorage > 0 && freeEUstorage < this.productionPeerKineticUnit * kineticBandWith) {
                freeEUstorage = this.productionPeerKineticUnit * kineticBandWith;
            }

            if (freeEUstorage >= this.productionPeerKineticUnit * kineticBandWith) {
                int receivedKinetic = tile.requestkineticenergy(dir.getOpposite(), kineticBandWith);
                if (receivedKinetic != 0) {
                    this.production = (double) receivedKinetic * this.productionPeerKineticUnit;
                    this.energy += this.production;
                    return true;
                }
            }
        }

        this.production = 0;
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
        return EnergyNet.instance.getTierFromPower(output);
    }

    @Override
    public boolean emitsEnergyTo(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        return false;
    }

    public double getProduction() {
        return production;
    }
}
