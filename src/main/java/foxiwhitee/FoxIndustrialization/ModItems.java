package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.ItemSpeedUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemStorageUpgrade;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item SPEED_UPGRADE = new ItemSpeedUpgrade("speedUpgrade");
    public static final Item STORAGE_UPGRADE = new ItemStorageUpgrade("storageUpgrade");

    public static void registerItems() {
        if (ContentConfig.enableSpeedUpgrades) {
            RegisterUtils.registerItem(SPEED_UPGRADE);
        }
        if (ContentConfig.enableStorageUpgrades) {
            RegisterUtils.registerItem(STORAGE_UPGRADE);
        }
    }
}
