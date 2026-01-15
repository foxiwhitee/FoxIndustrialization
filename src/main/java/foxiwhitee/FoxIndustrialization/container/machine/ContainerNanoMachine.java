package foxiwhitee.FoxIndustrialization.container.machine;

import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNanoMachine extends ContainerMachine {
    public ContainerNanoMachine(EntityPlayer ip, TileNanoMachine myTile) {
        super(ip, myTile);

        addSlotToContainer(new Slot(myTile.getInternalInventory(), 0, 87, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 1, 123, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 2, 158, 66));

        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 87, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 1, 123, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 2, 158, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
