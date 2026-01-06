package foxiwhitee.FoxIndustrialization.container.generator.fuel;

import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileGenerator;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerGenerator extends FoxBaseContainer {
    public ContainerGenerator(EntityPlayer ip, TileGenerator myTile, String filter) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotFiltered(filter, myTile.getChargeInventory(), i, 207, 69 + 18 * i + i, ip.inventory));
        }
    }
}
