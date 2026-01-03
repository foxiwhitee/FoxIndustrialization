package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public abstract class TileNanoMachine extends TileAdvancedMachine {
    public TileNanoMachine(MachineTier tier, double maxEnergy, int itemsPerOp, double energyPerTick) {
        super(tier, maxEnergy, itemsPerOp, energyPerTick);
    }
}
