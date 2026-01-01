package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.ItemSpeedUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemStorageUpgrade;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item speedUpgrade = new ItemSpeedUpgrade("speedUpgrade");
    public static final Item storageUpgrade = new ItemStorageUpgrade("storageUpgrade");

    public static void registerItems() {
        if (ContentConfig.enableSpeedUpgrades) {
            RegisterUtils.registerItem(speedUpgrade);
        }
        if (ContentConfig.enableStorageUpgrades) {
            RegisterUtils.registerItem(storageUpgrade);
        }
    }
}
