package foxiwhitee.FoxIndustrialization.tile.storage.nano;

import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileAdvancedChargePadLevel;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileAdvancedEnergyStorageLevel;

public abstract class TileNanoChargePadLevel extends TileAdvancedChargePadLevel {
    public TileNanoChargePadLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
