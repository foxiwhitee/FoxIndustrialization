package foxiwhitee.FoxIndustrialization.tile.storage.singular;

import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumChargePadLevel;
import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumEnergyStorageLevel;

public abstract class TileSingularChargePadLevel extends TileQuantumChargePadLevel {
    public TileSingularChargePadLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
