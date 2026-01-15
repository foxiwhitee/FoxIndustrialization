package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.TileWitherKiller;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWitherKiller extends FoxBaseContainer {
    public ContainerWitherKiller(EntityPlayer ip, TileWitherKiller myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, l, 23, 85 + l * 18 + l ));
        }

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_WITHER_SKULL, myTile.getInternalInventory(), i, 51 + i * 19, 66, ip.inventory));
        }
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SOUL_SAND, myTile.getInternalInventory(), i + 3, 51 + i * 19, 85, ip.inventory));
        }
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SOUL_SAND, myTile.getInternalInventory(), 6, 70, 104, ip.inventory));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new Slot(myTile.getOutputInventory(), j + i * 3, 157 + j * 19, 66 + i * 19) {
                    @Override
                    public boolean isItemValid(ItemStack stack) {
                        return false;
                    }
                });
            }
        }
    }
}
