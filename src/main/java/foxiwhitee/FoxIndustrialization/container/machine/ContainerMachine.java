package foxiwhitee.FoxIndustrialization.container.machine;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ContainerMachine extends FoxBaseContainer {
    public ContainerMachine(EntityPlayer ip, TileBaseMachine myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, l, 23, 85 + l * 18 + l ));
        }
    }
}
