package foxiwhitee.FoxIndustrialization.tile.storage.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileNanoChargePad extends TileNanoChargePadLevel {
    public TileNanoChargePad() {
        super(FIConfig.energyStorageNanoTier, FIConfig.energyStorageNanoStorage, FIConfig.energyStorageNanoOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_NANO_ENERGY_STORAGE;
    }
}
