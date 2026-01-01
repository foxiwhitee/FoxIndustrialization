package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileUltimateEnergyStorage extends TileQuantumEnergyStorageLevel {
    private static final InfoGui info = new InfoGui("guiQuantumEnergyStorage", 0, 176, 137);

    public TileUltimateEnergyStorage() {
        super(FIConfig.energyStorageUltimateTier, FIConfig.energyStorageUltimateStorage, FIConfig.energyStorageUltimateOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_ULTIMATE_ENERGY_STORAGE;
    }
}
