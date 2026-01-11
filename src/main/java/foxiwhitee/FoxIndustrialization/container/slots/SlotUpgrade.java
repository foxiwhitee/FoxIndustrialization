package foxiwhitee.FoxIndustrialization.container.slots;

import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.items.ItemSpeedUpgrade;
import foxiwhitee.FoxIndustrialization.items.ItemStorageUpgrade;
import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.container.slots.FoxSlot;
import ic2.core.upgrade.IUpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SlotUpgrade extends FoxSlot {
    private final IUpgradableTile tile;

    public SlotUpgrade(IInventory inv, IUpgradableTile tile, int idx, int x, int y) {
        super(inv, idx, x, y);
        this.tile = tile;
    }

    public boolean isItemValid(ItemStack i) {
        if (!this.isEnabled()) {
            return false;
        } else if (i == null) {
            return false;
        } else if (i.getItem() == null) {
            return false;
        } else if (!this.inventory.isItemValidForSlot(this.getSlotIndex(), i)) {
            return false;
        } else {
            List<UpgradesTypes> types = Arrays.asList(tile.getAvailableTypes());

            if (i.getItem() instanceof IAdvancedUpgradeItem item) {
                if (item.getTier(i).ordinal() <= tile.getTier().ordinal()) {
                    if (item instanceof ItemSpeedUpgrade && types.contains(UpgradesTypes.SPEED)) {
                        return true;
                    } else return item instanceof ItemStorageUpgrade && types.contains(UpgradesTypes.STORAGE);
                }
            } else if (i.getItem() instanceof IUpgradeItem item) {
                switch (i.getItemDamage()) {
                    case 0: return types.contains(UpgradesTypes.SPEED);
                    case 2: return types.contains(UpgradesTypes.STORAGE);
                    case 3: return types.contains(UpgradesTypes.EJECTOR);
                    case 4: return types.contains(UpgradesTypes.FLUID_EJECTOR);
                    case 6: return types.contains(UpgradesTypes.PULLING);
                }
            }

            return false;
        }
    }
}
