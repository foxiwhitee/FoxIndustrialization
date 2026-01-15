package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileAdvancedEnergyStorage extends TileAdvancedEnergyStorageLevel {
    public TileAdvancedEnergyStorage() {
        super(FIConfig.energyStorageAdvancedTier, FIConfig.energyStorageAdvancedStorage, FIConfig.energyStorageAdvancedOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_ADVANCED_ENERGY_STORAGE;
    }
}
