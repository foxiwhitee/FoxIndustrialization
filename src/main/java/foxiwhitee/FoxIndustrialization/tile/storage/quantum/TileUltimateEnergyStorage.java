package foxiwhitee.FoxIndustrialization.tile.storage.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileUltimateEnergyStorage extends TileQuantumEnergyStorageLevel {
    public TileUltimateEnergyStorage() {
        super(FIConfig.energyStorageUltimateTier, FIConfig.energyStorageUltimateStorage, FIConfig.energyStorageUltimateOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ULTIMATE_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_ULTIMATE_ENERGY_STORAGE;
    }

    @Override
    public boolean supportsRF() {
        return FIConfig.energyStorageUltimateSupportsRF;
    }
}
