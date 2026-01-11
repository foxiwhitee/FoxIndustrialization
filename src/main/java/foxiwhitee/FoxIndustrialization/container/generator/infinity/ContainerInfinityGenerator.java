package foxiwhitee.FoxIndustrialization.container.generator.infinity;

import foxiwhitee.FoxIndustrialization.tile.generator.infinity.TileInfinityGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerInfinityGenerator extends FoxBaseContainer {
    public ContainerInfinityGenerator(EntityPlayer ip, TileInfinityGenerator myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_INFINITY_GENERATOR, myTile.getInternalInventory(), 0, 86, 87, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_INFINITY_GENERATOR, myTile.getInternalInventory(), 1, 160, 87, ip.inventory));
    }
}
