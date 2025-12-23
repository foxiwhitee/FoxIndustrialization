package foxiwhitee.FoxIndustrialization.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxIndustrialization")
public class FIConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enableTooltips = true;


    @ConfigValue(category = "Upgrades.Advanced", name = "storageMultiplier", min = "0.1", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeAdvancedMultiplier = 0.16;

    @ConfigValue(category = "Upgrades.Advanced", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeAdvancedMultiplier = 0.6;

    @ConfigValue(category = "Upgrades.Advanced", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeAdvancedItemsPerOpAdd = 2;

    @ConfigValue(category = "Upgrades.Advanced", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeAdvancedUseMultiplier = 2;


    @ConfigValue(category = "Upgrades.Nano", name = "storageMultiplier", min = "0.1", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeNanoMultiplier = 1.28;

    @ConfigValue(category = "Upgrades.Nano", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeNanoMultiplier = 0.5;

    @ConfigValue(category = "Upgrades.Nano", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeNanoItemsPerOpAdd = 4;

    @ConfigValue(category = "Upgrades.Nano", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeNanoUseMultiplier = 3;


    @ConfigValue(category = "Upgrades.Quantum", name = "storageMultiplier", min = "0.1", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeQuantumMultiplier = 5.12;

    @ConfigValue(category = "Upgrades.Quantum", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeQuantumMultiplier = 0.4;

    @ConfigValue(category = "Upgrades.Quantum", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeQuantumItemsPerOpAdd = 8;

    @ConfigValue(category = "Upgrades.Quantum", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeQuantumUseMultiplier = 2.5;

}
