package foxiwhitee.FoxIndustrialization.tile.storage.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileNanoEnergyStorage extends TileNanoEnergyStorageLevel {
    private static final InfoGui info = new InfoGui("guiNanoEnergyStorage", 0, 168, 113);

    public TileNanoEnergyStorage() {
        super(FIConfig.energyStorageNanoTier, FIConfig.energyStorageNanoStorage, FIConfig.energyStorageNanoOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_NANO_ENERGY_STORAGE;
    }
}
