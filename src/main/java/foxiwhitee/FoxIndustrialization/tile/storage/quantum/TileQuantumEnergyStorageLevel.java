package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorageLevel;

public abstract class TileQuantumEnergyStorageLevel extends TileNanoEnergyStorageLevel {
    public TileQuantumEnergyStorageLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
