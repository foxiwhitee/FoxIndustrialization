package foxiwhitee.FoxIndustrialization.tile.storage.singular;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;

public class TileSingularChargePad extends TileSingularChargePadLevel {
    private static final InfoGui info = new InfoGui("guiSingularEnergyStorage", 0, 192, 137);

    public TileSingularChargePad() {
        super(FIConfig.energyStorageSingularTier, FIConfig.energyStorageSingularStorage, FIConfig.energyStorageSingularOutput);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
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
