package foxiwhitee.FoxIndustrialization.tile.storage.singular;

import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumEnergyStorageLevel;

public abstract class TileSingularEnergyStorageLevel extends TileQuantumEnergyStorageLevel {
    public TileSingularEnergyStorageLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
