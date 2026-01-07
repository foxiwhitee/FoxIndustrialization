package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.tile.TileSynthesizer;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSynthesizer extends FoxBaseContainer {
    public ContainerSynthesizer(EntityPlayer ip, TileSynthesizer myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 183);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SUN_UPGRADE, myTile.getUpgradesInventory(), 0, 23, 83, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_UPGRADE, myTile.getUpgradesInventory(), 1, 23, 102, ip.inventory));

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SLOTS, myTile.getInternalInventory(), i, 140 + i * 18 + i, 55, ip.inventory));
        }
        for (int i = 0; i < 5; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SLOTS, myTile.getInternalInventory(), i + 3, 121 + i * 18 + i, 74, ip.inventory));
        }
        for (int i = 0; i < 5; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SLOTS, myTile.getInternalInventory(), i + 8, 121 + i * 18 + i, 93, ip.inventory));
        }
        for (int i = 0; i < 5; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SLOTS, myTile.getInternalInventory(), i + 13, 121 + i * 18 + i, 112, ip.inventory));
        }
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_SYNTHESIZER_SLOTS, myTile.getInternalInventory(), i + 18, 140 + i * 18 + i, 131, ip.inventory));
        }
    }
}
