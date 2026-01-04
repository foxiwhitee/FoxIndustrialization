package foxiwhitee.FoxIndustrialization.items.energy;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class ItemQuantumEnergyCrystal extends ItemEnergyCrystalWithRF {
    public ItemQuantumEnergyCrystal(String name) {
        super(name);
    }

    @Override
    protected boolean hasRFEnergySupport() {
        return FICore.ifCoFHCoreIsLoaded && FIConfig.energyCrystalQuantumSupportsRF;
    }

    @Override
    protected double getMaxStorage() {
        return FIConfig.energyCrystalQuantumStorage;
    }

    @Override
    protected double getOutput() {
        return FIConfig.energyCrystalQuantumSpeed;
    }

    @Override
    protected int getTier() {
        return FIConfig.energyCrystalQuantumTier;
    }
}
