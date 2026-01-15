package foxiwhitee.FoxIndustrialization.tile.storage.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileHybridChargePad extends TileNanoChargePadLevel {
    public TileHybridChargePad() {
        super(FIConfig.energyStorageHybridTier, FIConfig.energyStorageHybridStorage, FIConfig.energyStorageHybridOutput);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.HYBRID_ENERGY_STORAGE;
    }

    @Override
    public String getInventoryFilter() {
        return FilterInitializer.FILTER_HYBRID_ENERGY_STORAGE;
    }
}
