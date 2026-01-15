package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileQuantumEnergyStorage extends TileQuantumEnergyStorageLevel {
    public TileQuantumEnergyStorage() {
        super(FIConfig.energyStorageQuantumTier, FIConfig.energyStorageQuantumStorage, FIConfig.energyStorageQuantumOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_QUANTUM_ENERGY_STORAGE;
    }

    @Override
    public boolean supportsRF() {
        return FIConfig.energyStorageQuantumSupportsRF;
    }
}
