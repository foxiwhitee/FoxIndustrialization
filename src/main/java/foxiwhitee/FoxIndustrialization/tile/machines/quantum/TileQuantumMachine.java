package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public abstract class TileQuantumMachine extends TileNanoMachine {
    public TileQuantumMachine(MachineTier tier, double maxEnergy, int itemsPerOp, double energyPerTick) {
        super(tier, maxEnergy, itemsPerOp, energyPerTick);
    }
}
