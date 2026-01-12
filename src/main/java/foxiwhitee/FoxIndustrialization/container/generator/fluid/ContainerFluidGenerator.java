package foxiwhitee.FoxIndustrialization.container.generator.fluid;

import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileFluidGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFluidGenerator extends FoxBaseContainer {
    public ContainerFluidGenerator(EntityPlayer ip, TileFluidGenerator myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLUID_GENERATOR, myTile.getInternalInventory(), 0, 86, 87, ip.inventory));
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 160, 87) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
