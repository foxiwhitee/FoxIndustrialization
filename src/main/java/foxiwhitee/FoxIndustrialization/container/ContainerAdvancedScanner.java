package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.tile.TileAdvancedScanner;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerAdvancedScanner extends FoxBaseContainer {
    public ContainerAdvancedScanner(EntityPlayer ip, TileAdvancedScanner myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCANNER_SLOT, myTile.getInternalInventory(), 0, 123, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCANNER_CRYSTAL, myTile.getDiskInventory(), 0, 123, 109, ip.inventory));
    }
}
