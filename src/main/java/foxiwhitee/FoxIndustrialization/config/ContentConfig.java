package foxiwhitee.FoxIndustrialization.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxIndustrialization-Content")
public class ContentConfig {
    @ConfigValue(category = "Content", desc = "Enable Speed Upgrades?")
    public static boolean enableSpeedUpgrades = true;

    @ConfigValue(category = "Content", desc = "Enable Storage Upgrades?")
    public static boolean enableStorageUpgrades = true;

    @ConfigValue(category = "Content", desc = "Enable New Energy Storages?")
    public static boolean enableNewEnergyStorages = true;


    // Machines Advanced
    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Compressor?")
    public static boolean enableAdvancedCompressor = true;

    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Extractor?")
    public static boolean enableAdvancedExtractor = true;

    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Electric Furnace?")
    public static boolean enableAdvancedFurnace = true;

    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Macerator?")
    public static boolean enableAdvancedMacerator = true;

    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Metal Former?")
    public static boolean enableAdvancedMetalFormer = true;

    @ConfigValue(category = "Content.Machines.Advanced", desc = "Enable Advanced Recycler?")
    public static boolean enableAdvancedRecycler = true;


    // Machines Nano
    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Compressor?")
    public static boolean enableNanoCompressor = true;

    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Extractor?")
    public static boolean enableNanoExtractor = true;

    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Electric Furnace?")
    public static boolean enableNanoFurnace = true;

    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Macerator?")
    public static boolean enableNanoMacerator = true;

    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Metal Former?")
    public static boolean enableNanoMetalFormer = true;

    @ConfigValue(category = "Content.Machines.Nano", desc = "Enable Nano Recycler?")
    public static boolean enableNanoRecycler = true;


    // Machines Quantum
    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Compressor?")
    public static boolean enableQuantumCompressor = true;

    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Extractor?")
    public static boolean enableQuantumExtractor = true;

    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Electric Furnace?")
    public static boolean enableQuantumFurnace = true;

    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Macerator?")
    public static boolean enableQuantumMacerator = true;

    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Metal Former?")
    public static boolean enableQuantumMetalFormer = true;

    @ConfigValue(category = "Content.Machines.Quantum", desc = "Enable Quantum Recycler?")
    public static boolean enableQuantumRecycler = true;


    @ConfigValue(category = "Content", desc = "Enable Power Converter?")
    public static boolean enablePowerConverter = true;
}
