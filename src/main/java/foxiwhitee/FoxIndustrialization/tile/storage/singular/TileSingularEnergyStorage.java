package foxiwhitee.FoxIndustrialization.tile.storage.singular;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileSingularEnergyStorage extends TileSingularEnergyStorageLevel {
    public TileSingularEnergyStorage() {
        super(FIConfig.energyStorageSingularTier, FIConfig.energyStorageSingularStorage, FIConfig.energyStorageSingularOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.SINGULAR_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_SINGULAR_ENERGY_STORAGE;
    }

    @Override
    public boolean supportsRF() {
        return FIConfig.energyStorageSingularSupportsRF;
    }
}
