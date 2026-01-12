package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.TileMatterSynthesizer;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMatterSynthesizer extends FoxBaseContainer {
    public ContainerMatterSynthesizer(EntityPlayer ip, TileMatterSynthesizer myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 200);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_FLUID_GENERATOR, myTile.getInternalInventory(), 0, 160, 96, ip.inventory));
        addSlotToContainer(new Slot(myTile.getOutputInventory(), 0, 160, 132) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SCRAP, myTile.getScrapInventory(), 0, 86, 114, ip.inventory));

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotUpgrade(myTile.getUpgradesInventory(), myTile, i, 23, 112 + i * 19));
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_MATTER_SYNTHESIZER, myTile.getGeneratorsInventory(), i, 47 + i * 19, 61, ip.inventory));
        }
    }
}
