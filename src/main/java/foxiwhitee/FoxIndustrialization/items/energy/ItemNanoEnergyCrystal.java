package foxiwhitee.FoxIndustrialization.items.energy;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class ItemNanoEnergyCrystal extends ItemEnergyCrystal {
    public ItemNanoEnergyCrystal(String name) {
        super(name);
    }

    @Override
    protected double getMaxStorage() {
        return FIConfig.energyCrystalNanoStorage;
    }

    @Override
    protected double getOutput() {
        return FIConfig.energyCrystalNanoSpeed;
    }

    @Override
    protected int getTier() {
        return FIConfig.energyCrystalNanoTier;
    }
}
