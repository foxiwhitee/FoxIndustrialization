package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.TileQuantumReplicator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuantumReplicator extends FoxBaseContainer {
    public ContainerQuantumReplicator(EntityPlayer ip, TileQuantumReplicator myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 178);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, l, 23, 85 + l * 18 + l ));
        }

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCANNER_SLOT, myTile.getInputsInventory(), 0, 105, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCANNER_SLOT, myTile.getInputsInventory(), 1, 141, 66, ip.inventory));

        addSlotToContainer(new Slot(myTile.getInternalInventory(), 0, 105, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 1, 141, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
