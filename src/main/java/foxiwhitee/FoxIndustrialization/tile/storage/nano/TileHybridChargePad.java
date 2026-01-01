package foxiwhitee.FoxIndustrialization.tile.storage.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileHybridChargePad extends TileNanoChargePadLevel {
    private static final InfoGui info = new InfoGui("guiNanoEnergyStorage", 0, 160, 125);

    public TileHybridChargePad() {
        super(FIConfig.energyStorageHybridTier, FIConfig.energyStorageHybridStorage, FIConfig.energyStorageHybridOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_HYBRID_ENERGY_STORAGE;
    }
}
