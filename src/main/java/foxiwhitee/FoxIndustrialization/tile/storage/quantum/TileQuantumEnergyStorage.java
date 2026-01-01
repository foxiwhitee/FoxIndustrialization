package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileQuantumEnergyStorage extends TileQuantumEnergyStorageLevel {
    private static final InfoGui info = new InfoGui("guiQuantumEnergyStorage", 0, 184, 131);

    public TileQuantumEnergyStorage() {
        super(FIConfig.energyStorageQuantumTier, FIConfig.energyStorageQuantumStorage, FIConfig.energyStorageQuantumOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_QUANTUM_ENERGY_STORAGE;
    }
}
