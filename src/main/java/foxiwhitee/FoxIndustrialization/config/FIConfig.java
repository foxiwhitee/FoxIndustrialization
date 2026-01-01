package foxiwhitee.FoxIndustrialization.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxIndustrialization")
public class FIConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enableTooltips = true;

    // Upgrades Advanced
    @ConfigValue(category = "Upgrades.Advanced", name = "storageMultiplier", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeAdvancedMultiplier = 16;

    @ConfigValue(category = "Upgrades.Advanced", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeAdvancedMultiplier = 0.6;

    @ConfigValue(category = "Upgrades.Advanced", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeAdvancedItemsPerOpAdd = 2;

    @ConfigValue(category = "Upgrades.Advanced", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeAdvancedUseMultiplier = 2;


    // Upgrades Nano
    @ConfigValue(category = "Upgrades.Nano", name = "storageMultiplier", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeNanoMultiplier = 128;

    @ConfigValue(category = "Upgrades.Nano", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeNanoMultiplier = 0.5;

    @ConfigValue(category = "Upgrades.Nano", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeNanoItemsPerOpAdd = 4;

    @ConfigValue(category = "Upgrades.Nano", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeNanoUseMultiplier = 3;


    // Upgrades Quantum
    @ConfigValue(category = "Upgrades.Quantum", name = "storageMultiplier", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeQuantumMultiplier = 512;

    @ConfigValue(category = "Upgrades.Quantum", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeQuantumMultiplier = 0.4;

    @ConfigValue(category = "Upgrades.Quantum", name = "itemsPerOpAdd", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeQuantumItemsPerOpAdd = 8;

    @ConfigValue(category = "Upgrades.Quantum", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeQuantumUseMultiplier = 2.5;


    // Energy Storage Basic
    @ConfigValue(category = "Storage.Basic", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageBasicTier = 8;

    @ConfigValue(category = "Storage.Basic", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageBasicStorage = 100_000_000;

    @ConfigValue(category = "Storage.Basic", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageBasicOutput = 65_536;


    // Energy Storage Advanced
    @ConfigValue(category = "Storage.Advanced", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageAdvancedTier = 16;

    @ConfigValue(category = "Storage.Advanced", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageAdvancedStorage = 500_000_000;

    @ConfigValue(category = "Storage.Advanced", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageAdvancedOutput = 655_360;


    // Energy Storage Hybrid
    @ConfigValue(category = "Storage.Hybrid", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageHybridTier = 16;

    @ConfigValue(category = "Storage.Hybrid", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageHybridStorage = 10_000_00_000;

    @ConfigValue(category = "Storage.Hybrid", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageHybridOutput = 6_553_600;


    // Energy Storage Nano
    @ConfigValue(category = "Storage.Nano", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageNanoTier = 16;

    @ConfigValue(category = "Storage.Nano", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageNanoStorage = 500_000_000_000D;

    @ConfigValue(category = "Storage.Nano", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageNanoOutput = 65_536_000;


    // Energy Storage Ultimate
    @ConfigValue(category = "Storage.Ultimate", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageUltimateTier = 32;

    @ConfigValue(category = "Storage.Ultimate", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageUltimateStorage = 100_000_000_000_000D;

    @ConfigValue(category = "Storage.Ultimate", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageUltimateOutput = 100_000_000_000D;


    // Energy Storage Quantum
    @ConfigValue(category = "Storage.Quantum", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageQuantumTier = 64;

    @ConfigValue(category = "Storage.Quantum", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageQuantumStorage = 1_000_000_000_000_000D;

    @ConfigValue(category = "Storage.Quantum", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageQuantumOutput = 1_000_000_000_000D;


    // Energy Storage Singular
    @ConfigValue(category = "Storage.Singular", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageSingularTier = 128;

    @ConfigValue(category = "Storage.Singular", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageSingularStorage = 15_000_000_000_000_000D;

    @ConfigValue(category = "Storage.Singular", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageSingularOutput = 15_000_000_000_000D;
}
