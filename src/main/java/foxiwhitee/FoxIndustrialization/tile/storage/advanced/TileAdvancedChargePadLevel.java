package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.tile.storage.TileChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;

public abstract class TileAdvancedChargePadLevel extends TileChargePad {
    public TileAdvancedChargePadLevel(int tier, double storage, double output) {
        super(tier, storage, output);
    }
}
