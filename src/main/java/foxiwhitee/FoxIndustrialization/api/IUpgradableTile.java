package foxiwhitee.FoxIndustrialization.api;

import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;

public interface IUpgradableTile {
    UpgradesTypes[] getAvailableTypes();
    MachineTier getTier();
}
