package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileAdvancedEnergyStorage extends TileAdvancedEnergyStorageLevel {
    private static final InfoGui info = new InfoGui("guiAdvancedEnergyStorage", 0, 152, 131);

    public TileAdvancedEnergyStorage() {
        super(FIConfig.energyStorageAdvancedTier, FIConfig.energyStorageAdvancedStorage, FIConfig.energyStorageAdvancedOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_ADVANCED_ENERGY_STORAGE;
    }
}
