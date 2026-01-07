package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.ItemSpeedUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemStorageUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemSynthesizerSunUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemSynthesizerUpgrade;
import foxiwhitee.FoxIndustrialization.items.energy.ItemAdvancedEnergyCrystal;
import foxiwhitee.FoxIndustrialization.items.energy.ItemNanoEnergyCrystal;
import foxiwhitee.FoxIndustrialization.items.energy.ItemQuantumEnergyCrystal;
import foxiwhitee.FoxIndustrialization.items.energy.ItemSingularEnergyCrystal;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item speedUpgrade = new ItemSpeedUpgrade("speedUpgrade");
    public static final Item storageUpgrade = new ItemStorageUpgrade("storageUpgrade");

    public static final Item synthesizerUpgradeSun = new ItemSynthesizerSunUpgrade("synthesizerUpgradeSun");
    public static final Item synthesizerUpgrade = new ItemSynthesizerUpgrade("synthesizerUpgrade");

    public static final Item advancedEnergyCrystal = new ItemAdvancedEnergyCrystal("advancedEnergyCrystal");
    public static final Item nanoEnergyCrystal = new ItemNanoEnergyCrystal("nanoEnergyCrystal");
    public static final Item quantumEnergyCrystal = new ItemQuantumEnergyCrystal("quantumEnergyCrystal");
    public static final Item singularEnergyCrystal = new ItemSingularEnergyCrystal("singularEnergyCrystal");

    public static void registerItems() {
        if (ContentConfig.enableSpeedUpgrades) {
            RegisterUtils.registerItem(speedUpgrade);
        }
        if (ContentConfig.enableStorageUpgrades) {
            RegisterUtils.registerItem(storageUpgrade);
        }
        if (ContentConfig.enableEnergyCrystals) {
            RegisterUtils.registerItems(advancedEnergyCrystal, nanoEnergyCrystal, quantumEnergyCrystal, singularEnergyCrystal);
        }
        if (ContentConfig.enableSynthesizer) {
            RegisterUtils.registerItems(synthesizerUpgrade, synthesizerUpgradeSun);
        }
    }
}
