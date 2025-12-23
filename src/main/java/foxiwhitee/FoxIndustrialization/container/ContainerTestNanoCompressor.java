package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.tile.machines.TIleTestNanoCompressor;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerTestNanoCompressor extends FoxBaseContainer {
    public ContainerTestNanoCompressor(EntityPlayer ip, TIleTestNanoCompressor myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 38, 152);

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new Slot(myTile.getUpgradesInventory(), i, 31, 47 + i * 18 + 2));
        }
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new Slot(myTile.getInternalInventory(), i, 70, 58 + i * 18 ));
        }
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new Slot(myTile.getOutputInventory(), i, 88, 58 + i * 18 ));
        }
    }
}
