package foxiwhitee.FoxIndustrialization.container.generator.fuel;

import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerNanoGenerator extends ContainerGenerator {
    public ContainerNanoGenerator(EntityPlayer ip, TileNanoGenerator myTile) {
        super(ip, myTile, FilterInitializer.FILTER_NANO_GENERATOR);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 0, 87, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 1, 123, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 2, 158, 66, ip.inventory));
    }
}
