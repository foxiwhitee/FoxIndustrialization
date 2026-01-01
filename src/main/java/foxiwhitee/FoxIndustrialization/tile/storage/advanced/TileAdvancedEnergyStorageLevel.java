package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;

public abstract class TileAdvancedEnergyStorageLevel extends TileEnergyStorage {
    public TileAdvancedEnergyStorageLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
