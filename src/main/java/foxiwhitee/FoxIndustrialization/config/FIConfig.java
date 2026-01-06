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

    @ConfigValue(category = "Upgrades.Advanced", name = "itemsPerOpAdd", max = "64", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeAdvancedItemsPerOpAdd = 2;

    @ConfigValue(category = "Upgrades.Advanced", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeAdvancedUseMultiplier = 2;


    // Upgrades Nano
    @ConfigValue(category = "Upgrades.Nano", name = "storageMultiplier", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeNanoMultiplier = 128;

    @ConfigValue(category = "Upgrades.Nano", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeNanoMultiplier = 0.5;

    @ConfigValue(category = "Upgrades.Nano", name = "itemsPerOpAdd", max = "64", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeNanoItemsPerOpAdd = 4;

    @ConfigValue(category = "Upgrades.Nano", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeNanoUseMultiplier = 3;


    // Upgrades Quantum
    @ConfigValue(category = "Upgrades.Quantum", name = "storageMultiplier", desc = "How many times will the mechanism's storage be increased with each upgrade?")
    public static double storageUpgradeQuantumMultiplier = 512;

    @ConfigValue(category = "Upgrades.Quantum", name = "speedMultiplier", min = "0", desc = "How many times will the mechanism's speed be increased with each upgrade?")
    public static double speedUpgradeQuantumMultiplier = 0.4;

    @ConfigValue(category = "Upgrades.Quantum", name = "itemsPerOpAdd", max = "64", desc = "How much will the maximum number of processed items be increased with each upgrade?")
    public static int speedUpgradeQuantumItemsPerOpAdd = 8;

    @ConfigValue(category = "Upgrades.Quantum", name = "useMultiplier", desc = "How many times will the energy consumption of the mechanism increase with each improvement?")
    public static double speedUpgradeQuantumUseMultiplier = 2.5;


    // Upgrades Power Converter
    @ConfigValue(category = "Upgrades.PowerConverter", name = "storageMultiplierEU", desc = "How many times will the mechanism's EU storage be increased with each upgrade?")
    public static double powerConverterUpgradeEUStorageMultiplier = 2;

    @ConfigValue(category = "Upgrades.PowerConverter", name = "storageMultiplierRF", desc = "How many times will the mechanism's RF storage be increased with each upgrade?")
    public static double powerConverterUpgradeRFStorageMultiplier = 2;

    @ConfigValue(category = "Upgrades.PowerConverter", name = "outputMultiplierEU", desc = "How many times will the rate of EU energy output increase?")
    public static double powerConverterUpgradeEUOutputMultiplier = 8;

    @ConfigValue(category = "Upgrades.PowerConverter", name = "outputMultiplierRF", desc = "How many times will the rate of RF energy output increase?")
    public static double powerConverterUpgradeRFOutputMultiplier = 8;


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

    @ConfigValue(category = "Storage.Ultimate", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean energyStorageUltimateSupportsRF = true;


    // Energy Storage Quantum
    @ConfigValue(category = "Storage.Quantum", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageQuantumTier = 64;

    @ConfigValue(category = "Storage.Quantum", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageQuantumStorage = 1_000_000_000_000_000D;

    @ConfigValue(category = "Storage.Quantum", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageQuantumOutput = 1_000_000_000_000D;

    @ConfigValue(category = "Storage.Quantum", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean energyStorageQuantumSupportsRF = true;


    // Energy Storage Singular
    @ConfigValue(category = "Storage.Singular", name = "tier", desc = "The energy level of the unit. Affects the speed of charging and discharging energy. For example, MFSU has level 4")
    public static int energyStorageSingularTier = 128;

    @ConfigValue(category = "Storage.Singular", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double energyStorageSingularStorage = 15_000_000_000_000_000D;

    @ConfigValue(category = "Storage.Singular", name = "output", desc = "How much energy does it transfer at a time?")
    public static double energyStorageSingularOutput = 15_000_000_000_000D;

    @ConfigValue(category = "Storage.Singular", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean energyStorageSingularSupportsRF = true;


    // Machines Advanced
    // Compressor
    @ConfigValue(category = "Machines.Advanced.Compressor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedCompressorStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.Compressor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedCompressorItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.Compressor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedCompressorEnergyPerTick = 10;

    // Extractor
    @ConfigValue(category = "Machines.Advanced.Extractor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedExtractorStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.Extractor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedExtractorItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.Extractor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedExtractorEnergyPerTick = 10;

    // Furnace
    @ConfigValue(category = "Machines.Advanced.Furnace", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedFurnaceStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.Furnace", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedFurnaceItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.Furnace", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedFurnaceEnergyPerTick = 8;

    // Macerator
    @ConfigValue(category = "Machines.Advanced.Macerator", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedMaceratorStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.Macerator", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedMaceratorItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.Macerator", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedMaceratorEnergyPerTick = 10;

    // MetalFormer
    @ConfigValue(category = "Machines.Advanced.MetalFormer", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedMetalFormerStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.MetalFormer", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedMetalFormerItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.MetalFormer", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedMetalFormerEnergyPerTick = 12;

    // Recycler
    @ConfigValue(category = "Machines.Advanced.Recycler", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double advancedRecyclerStorage = 1_000_000;

    @ConfigValue(category = "Machines.Advanced.Recycler", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int advancedRecyclerItemsPerOp = 8;

    @ConfigValue(category = "Machines.Advanced.Recycler", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double advancedRecyclerEnergyPerTick = 8;


    // Machines Nano
    // Compressor
    @ConfigValue(category = "Machines.Nano.Compressor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoCompressorStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.Compressor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoCompressorItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.Compressor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoCompressorEnergyPerTick = 15;

    // Extractor
    @ConfigValue(category = "Machines.Nano.Extractor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoExtractorStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.Extractor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoExtractorItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.Extractor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoExtractorEnergyPerTick = 15;

    // Furnace
    @ConfigValue(category = "Machines.Nano.Furnace", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoFurnaceStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.Furnace", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoFurnaceItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.Furnace", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoFurnaceEnergyPerTick = 13;

    // Macerator
    @ConfigValue(category = "Machines.Nano.Macerator", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoMaceratorStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.Macerator", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoMaceratorItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.Macerator", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoMaceratorEnergyPerTick = 15;

    // MetalFormer
    @ConfigValue(category = "Machines.Nano.MetalFormer", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoMetalFormerStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.MetalFormer", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoMetalFormerItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.MetalFormer", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoMetalFormerEnergyPerTick = 17;

    // Recycler
    @ConfigValue(category = "Machines.Nano.Recycler", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double nanoRecyclerStorage = 2_500_000;

    @ConfigValue(category = "Machines.Nano.Recycler", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int nanoRecyclerItemsPerOp = 16;

    @ConfigValue(category = "Machines.Nano.Recycler", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double nanoRecyclerEnergyPerTick = 13;


    // Machines Quantum
    // Compressor
    @ConfigValue(category = "Machines.Quantum.Compressor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumCompressorStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.Compressor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumCompressorItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.Compressor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumCompressorEnergyPerTick = 22;

    @ConfigValue(category = "Machines.Quantum.Compressor", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumCompressorSupportsRF = true;

    // Extractor
    @ConfigValue(category = "Machines.Quantum.Extractor", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumExtractorStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.Extractor", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumExtractorItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.Extractor", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumExtractorEnergyPerTick = 22;

    @ConfigValue(category = "Machines.Quantum.Extractor", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumExtractorSupportsRF = true;

    // Furnace
    @ConfigValue(category = "Machines.Quantum.Furnace", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumFurnaceStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.Furnace", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumFurnaceItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.Furnace", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumFurnaceEnergyPerTick = 20;

    @ConfigValue(category = "Machines.Quantum.Furnace", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumFurnaceSupportsRF = true;

    // Macerator
    @ConfigValue(category = "Machines.Quantum.Macerator", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumMaceratorStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.Macerator", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumMaceratorItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.Macerator", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumMaceratorEnergyPerTick = 22;

    @ConfigValue(category = "Machines.Quantum.Macerator", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumMaceratorSupportsRF = true;

    // MetalFormer
    @ConfigValue(category = "Machines.Quantum.MetalFormer", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumMetalFormerStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.MetalFormer", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumMetalFormerItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.MetalFormer", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumMetalFormerEnergyPerTick = 24;

    @ConfigValue(category = "Machines.Quantum.MetalFormer", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumMetalFormerSupportsRF = true;

    // Recycler
    @ConfigValue(category = "Machines.Quantum.Recycler", name = "storage", desc = "The maximum amount of energy that the block can hold")
    public static double quantumRecyclerStorage = 5_000_000;

    @ConfigValue(category = "Machines.Quantum.Recycler", name = "itemsPerOp", max = "64", desc = "How many items can this block process at a time according to the standard?")
    public static int quantumRecyclerItemsPerOp = 32;

    @ConfigValue(category = "Machines.Quantum.Recycler", name = "energyPerTick", desc = "How much energy does the unit consume per tick during operation?")
    public static double quantumRecyclerEnergyPerTick = 55;

    @ConfigValue(category = "Machines.Quantum.Recycler", name = "supportsRF", desc = "Does this block support RF energy?")
    public static boolean quantumRecyclerSupportsRF = true;

    // Power Converter
    @ConfigValue(category = "PowerConverter", name = "storageEU", desc = "The maximum amount of EU energy that the block can hold")
    public static double powerConverterEUStorage = 1_000_000;

    @ConfigValue(category = "PowerConverter", name = "storageRF", desc = "The maximum amount of RF energy that the block can hold")
    public static double powerConverterRFStorage = 4_000_000;

    @ConfigValue(category = "PowerConverter", name = "outputEU", desc = "How much EU energy does it transfer at a time?")
    public static double powerConverterEUPerTick = 1024;

    @ConfigValue(category = "PowerConverter", name = "outputRF", desc = "How much RF energy does it transfer at a time?")
    public static double powerConverterRFPerTick = 4096;


    // Energy Crystals
    // Advanced Crystal
    @ConfigValue(category = "EnergyCrystals.Advanced", name = "tier", desc = "What is the item level? An item can only be charged in a block whose level is >= this value")
    public static int energyCrystalAdvancedTier = 4;

    @ConfigValue(category = "EnergyCrystals.Advanced", name = "storage", desc = "How much energy can this item hold?")
    public static double energyCrystalAdvancedStorage = 20_000_000;

    @ConfigValue(category = "EnergyCrystals.Advanced", name = "speed", desc = "The rate of energy transfer in this item")
    public static double energyCrystalAdvancedSpeed = 2_000_000;

    // Nano Crystal
    @ConfigValue(category = "EnergyCrystals.Nano", name = "tier", desc = "What is the item level? An item can only be charged in a block whose level is >= this value")
    public static int energyCrystalNanoTier = 4;

    @ConfigValue(category = "EnergyCrystals.Nano", name = "storage", desc = "How much energy can this item hold?")
    public static double energyCrystalNanoStorage = 200_000_000;

    @ConfigValue(category = "EnergyCrystals.Nano", name = "speed", desc = "The rate of energy transfer in this item")
    public static double energyCrystalNanoSpeed = 20_000_000;

    // Quantum Crystal
    @ConfigValue(category = "EnergyCrystals.Quantum", name = "tier", desc = "What is the item level? An item can only be charged in a block whose level is >= this value")
    public static int energyCrystalQuantumTier = 4;

    @ConfigValue(category = "EnergyCrystals.Quantum", name = "storage", desc = "How much energy can this item hold?")
    public static double energyCrystalQuantumStorage = 600_000_000;

    @ConfigValue(category = "EnergyCrystals.Quantum", name = "speed", desc = "The rate of energy transfer in this item")
    public static double energyCrystalQuantumSpeed = 60_000_000;

    @ConfigValue(category = "EnergyCrystals.Quantum", name = "supportsRF", desc = "Does this item support RF energy?")
    public static boolean energyCrystalQuantumSupportsRF = true;

    // Singular Crystal
    @ConfigValue(category = "EnergyCrystals.Singular", name = "tier", desc = "What is the item level? An item can only be charged in a block whose level is >= this value")
    public static int energyCrystalSingularTier = 4;

    @ConfigValue(category = "EnergyCrystals.Singular", name = "storage", desc = "How much energy can this item hold?")
    public static double energyCrystalSingularStorage = 2_000_000_000;

    @ConfigValue(category = "EnergyCrystals.Singular", name = "speed", desc = "The rate of energy transfer in this item")
    public static double energyCrystalSingularSpeed = 200_000_000;

    @ConfigValue(category = "EnergyCrystals.Singular", name = "supportsRF", desc = "Does this item support RF energy?")
    public static boolean energyCrystalSingularSupportsRF = true;


    // Generators
    // Fuel
    // Advanced
    @ConfigValue(category = "Generator.Fuel.Advanced", name = "production", desc = "How much energy does the block generate per 1 tick of combustion?")
    public static int generatorAdvancedProduction = 50;

    @ConfigValue(category = "Generator.Fuel.Advanced", name = "output", desc = "How much energy does it transfer at a time?")
    public static double generatorAdvancedOutput = 150;

    @ConfigValue(category = "Generator.Fuel.Advanced", name = "storage", desc = "How much energy can this item hold?")
    public static double generatorAdvancedStorage = 8_000;

    // Nano
    @ConfigValue(category = "Generator.Fuel.Nano", name = "production", desc = "How much energy does the block generate per 1 tick of combustion?")
    public static int generatorNanoProduction = 100;

    @ConfigValue(category = "Generator.Fuel.Nano", name = "output", desc = "How much energy does it transfer at a time?")
    public static double generatorNanoOutput = 300;

    @ConfigValue(category = "Generator.Fuel.Nano", name = "storage", desc = "How much energy can this item hold?")
    public static double generatorNanoStorage = 50_000;

    // Quantum
    @ConfigValue(category = "Generator.Fuel.Quantum", name = "production", desc = "How much energy does the block generate per 1 tick of combustion?")
    public static int generatorQuantumProduction = 200;

    @ConfigValue(category = "Generator.Fuel.Quantum", name = "output", desc = "How much energy does it transfer at a time?")
    public static double generatorQuantumOutput = 600;

    @ConfigValue(category = "Generator.Fuel.Quantum", name = "storage", desc = "How much energy can this item hold?")
    public static double generatorQuantumStorage = 100_000;

    @ConfigValue(category = "Generator.Fuel.Quantum", name = "supportsRF", desc = "Does this item support RF energy?")
    public static boolean generatorQuantumSupportsRF = true;
}
