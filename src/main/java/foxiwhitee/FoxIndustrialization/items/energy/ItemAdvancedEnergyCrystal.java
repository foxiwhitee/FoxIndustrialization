package foxiwhitee.FoxIndustrialization.items.energy;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class ItemAdvancedEnergyCrystal extends ItemEnergyCrystal {
    public ItemAdvancedEnergyCrystal(String name) {
        super(name);
    }

    @Override
    protected double getMaxStorage() {
        return FIConfig.energyCrystalAdvancedStorage;
    }

    @Override
    protected double getOutput() {
        return FIConfig.energyCrystalAdvancedSpeed;
    }

    @Override
    protected int getTier() {
        return FIConfig.energyCrystalAdvancedTier;
    }
}
