package foxiwhitee.FoxIndustrialization.container.machine;

import foxiwhitee.FoxIndustrialization.container.slots.SlotMachineUpgrade;
import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerMachine extends FoxBaseContainer {
    public ContainerMachine(EntityPlayer ip, TileBaseMachine myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotMachineUpgrade(myTile.getUpgradesInventory(), myTile, l, 23, 85 + l * 18 + l ));
        }
    }
}
