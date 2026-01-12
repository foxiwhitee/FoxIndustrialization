package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.*;
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

    public static final Item fluidUpgrades = new ItemFluidGeneratorUpgrade("fluidUpgrades");

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
        if (ContentConfig.enableSynthesizer) {
            RegisterUtils.registerItems(synthesizerUpgrade, synthesizerUpgradeSun);
        }
        if (ContentConfig.enableFluidUpgrades) {
            RegisterUtils.registerItem(fluidUpgrades);
        }
        if (ContentConfig.enableEnergyCrystals) {
            RegisterUtils.registerItems(advancedEnergyCrystal, nanoEnergyCrystal, quantumEnergyCrystal, singularEnergyCrystal);
        }
    }
}
