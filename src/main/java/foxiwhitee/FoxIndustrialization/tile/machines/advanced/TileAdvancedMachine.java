package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;

public abstract class TileAdvancedMachine extends TileBaseMachine {
    public TileAdvancedMachine(MachineTier tier, double maxEnergy, int itemsPerOp, double energyPerTick) {
        super(tier, maxEnergy, itemsPerOp, energyPerTick);
    }

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.EJECTOR, UpgradesTypes.PULLING, UpgradesTypes.REDSTONE, UpgradesTypes.SPEED, UpgradesTypes.STORAGE};
    }
}
