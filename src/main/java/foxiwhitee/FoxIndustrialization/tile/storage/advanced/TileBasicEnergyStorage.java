package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileBasicEnergyStorage extends TileAdvancedEnergyStorageLevel {
    private static final InfoGui info = new InfoGui("guiAdvancedEnergyStorage", 0, 144, 119);

    public TileBasicEnergyStorage() {
        super(FIConfig.energyStorageBasicTier, FIConfig.energyStorageBasicStorage, FIConfig.energyStorageBasicOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_BASIC_ENERGY_STORAGE;
    }
}
