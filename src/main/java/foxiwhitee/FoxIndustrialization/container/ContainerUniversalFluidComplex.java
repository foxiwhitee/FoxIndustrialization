package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.TileUniversalFluidComplex;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUniversalFluidComplex extends FoxBaseContainer {
    public ContainerUniversalFluidComplex(EntityPlayer ip, TileUniversalFluidComplex myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 51, 198);

        for (int i = 0; i < 4; i++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, i, 23, 91 + i * 19));
        }

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new Slot(myTile.getInternalInventory(), i, 59 + i * 19, 60));
        }

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new Slot(myTile.getOutputInventory(), i, 165 + i * 19, 60) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return false;
                }
            });
        }
    }
}
