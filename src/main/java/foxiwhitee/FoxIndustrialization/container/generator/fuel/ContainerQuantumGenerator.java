package foxiwhitee.FoxIndustrialization.container.generator.fuel;

import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileQuantumGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQuantumGenerator extends ContainerGenerator {
    public ContainerQuantumGenerator(EntityPlayer ip, TileQuantumGenerator myTile) {
        super(ip, myTile, FilterInitializer.FILTER_QUANTUM_GENERATOR);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 0, 69, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 1, 105, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 2, 141, 66, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLAMMABLE_MATERIAL, myTile.getInternalInventory(), 3, 177, 66, ip.inventory));
    }
}
