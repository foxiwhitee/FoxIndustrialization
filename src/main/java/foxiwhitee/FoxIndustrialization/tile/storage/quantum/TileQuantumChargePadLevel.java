package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoChargePadLevel;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorageLevel;

public abstract class TileQuantumChargePadLevel extends TileNanoChargePadLevel {
    public TileQuantumChargePadLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
