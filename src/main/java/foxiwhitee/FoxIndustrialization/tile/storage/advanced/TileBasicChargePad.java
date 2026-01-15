package foxiwhitee.FoxIndustrialization.tile.storage.advanced;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileBasicChargePad extends TileAdvancedChargePadLevel {
    public TileBasicChargePad() {
        super(FIConfig.energyStorageBasicTier, FIConfig.energyStorageBasicStorage, FIConfig.energyStorageBasicOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.BASIC_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_BASIC_ENERGY_STORAGE;
    }
}
