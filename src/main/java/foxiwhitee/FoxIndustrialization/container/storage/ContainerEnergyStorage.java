package foxiwhitee.FoxIndustrialization.container.storage;

import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerEnergyStorage extends FoxBaseContainer {
    public ContainerEnergyStorage(EntityPlayer ip, TileEnergyStorage myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(myTile.getInventoryFilter(), myTile.getInternalInventory(), 0, 86, 87, ip.inventory));
        addSlotToContainer(new SlotFiltered(myTile.getInventoryFilter(), myTile.getInternalInventory(), 1, 160, 87, ip.inventory));
    }
}
