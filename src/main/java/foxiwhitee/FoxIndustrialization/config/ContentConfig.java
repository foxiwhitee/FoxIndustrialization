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

}
