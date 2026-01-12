package foxiwhitee.FoxIndustrialization.container.generator.matter;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileMatterGenerator;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMatterGenerator extends FoxBaseContainer {
    public ContainerMatterGenerator(EntityPlayer ip, TileMatterGenerator myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLUID_GENERATOR, myTile.getInternalInventory(), 0, 160, 69, ip.inventory));
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 160, 105) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCRAP, myTile.getScrapInventory(), 0, 86, 87, ip.inventory));

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, i, 23, 85 + i * 19));
        }
    }
}
