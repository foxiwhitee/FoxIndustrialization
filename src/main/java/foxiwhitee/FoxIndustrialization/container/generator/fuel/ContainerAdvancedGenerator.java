package foxiwhitee.FoxIndustrialization.container.generator.fuel;

import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerAdvancedGenerator extends ContainerGenerator {
    public ContainerAdvancedGenerator(EntityPlayer ip, TileAdvancedGenerator myTile) {
        super(ip, myTile, FilterInitializer.FILTER_ADVANCED_GENERATOR);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 0, 105, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 1, 141, 66, ip.inventory));
    }
}
