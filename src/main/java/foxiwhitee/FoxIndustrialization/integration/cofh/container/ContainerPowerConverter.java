package foxiwhitee.FoxIndustrialization.integration.cofh.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotMachineUpgrade;
import foxiwhitee.FoxIndustrialization.integration.cofh.CoFHIntegration;
import foxiwhitee.FoxIndustrialization.integration.cofh.tile.TilePowerConverter;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerPowerConverter extends FoxBaseContainer {
    public ContainerPowerConverter(EntityPlayer ip, TilePowerConverter myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 195);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotFiltered(CoFHIntegration.FILTER_POWER_CONVERTER, myTile.getInternalInventory(), l, 23, 107 + l * 18 + l, ip.inventory));
        }
    }
}
