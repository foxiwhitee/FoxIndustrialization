package foxiwhitee.FoxIndustrialization.items.energy;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class ItemSingularEnergyCrystal extends ItemEnergyCrystalWithRF {
    public ItemSingularEnergyCrystal(String name) {
        super(name);
    }

    @Override
    protected boolean hasRFEnergySupport() {
        return FICore.ifCoFHCoreIsLoaded && FIConfig.energyCrystalSingularSupportsRF;
    }

    @Override
    protected double getMaxStorage() {
        return FIConfig.energyCrystalSingularStorage;
    }

    @Override
    protected double getOutput() {
        return FIConfig.energyCrystalSingularSpeed;
    }

    @Override
    protected int getTier() {
        return FIConfig.energyCrystalSingularTier;
    }
}
