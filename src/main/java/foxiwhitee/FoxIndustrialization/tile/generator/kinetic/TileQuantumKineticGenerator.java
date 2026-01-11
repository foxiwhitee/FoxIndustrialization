package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyProvider;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHCore")
public class TileQuantumKineticGenerator extends TileKineticGenerator implements IEnergyProvider, IDoubleEnergyProvider {
    private final static InfoGui info = new InfoGui("guiQuantumKineticGenerator", 0, 273, 149);

    public TileQuantumKineticGenerator() {
        super(FIConfig.kineticGeneratorQuantumOutput, FIConfig.kineticGeneratorQuantumStorage);
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (supportsRF()) {
            pushEnergy();
        }
    }

    private void pushEnergy() {
        boolean needUpdate = false;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            double pushedEnergy = EnergyUtility.pushEnergy(side, energy, FIConfig.infinityGeneratorOutput, this, true, false);
            this.energy -= pushedEnergy;
            needUpdate |= pushedEnergy > 0;
        }
        if (needUpdate) {
            markForUpdate();
        }
    }

    private boolean supportsRF() {
        return FIConfig.kineticGeneratorQuantumSupportsRF && FICore.ifCoFHCoreIsLoaded;
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

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
