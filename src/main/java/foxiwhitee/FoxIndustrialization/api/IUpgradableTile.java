package foxiwhitee.FoxIndustrialization.api;

import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;

public interface IUpgradableTile {
    UpgradesTypes[] getAvailableTypes();
    MachineTier getTier();
    FoxInternalInventory getUpgradesInventory();
}
