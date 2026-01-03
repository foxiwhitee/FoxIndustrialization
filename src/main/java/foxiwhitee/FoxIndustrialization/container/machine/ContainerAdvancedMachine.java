package foxiwhitee.FoxIndustrialization.container.machine;

import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAdvancedMachine extends ContainerMachine {
    public ContainerAdvancedMachine(EntityPlayer ip, TileAdvancedMachine myTile) {
        super(ip, myTile);

        addSlotToContainer(new Slot(myTile.getInternalInventory(), 0, 105, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 1, 141, 66));

        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 105, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 1, 141, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
