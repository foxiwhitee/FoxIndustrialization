package foxiwhitee.FoxIndustrialization.container.machine;

import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuantumMachine extends ContainerMachine {
    public ContainerQuantumMachine(EntityPlayer ip, TileQuantumMachine myTile) {
        super(ip, myTile);

        addSlotToContainer(new Slot(myTile.getInternalInventory(), 0, 69, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 1, 105, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 2, 141, 66));
        addSlotToContainer(new Slot(myTile.getInternalInventory(), 3, 177, 66));

        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 69, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 1, 105, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 2, 141, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 3, 177, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
