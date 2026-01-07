package foxiwhitee.FoxIndustrialization.container.generator.panel;

import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCustomSolarPanel extends FoxBaseContainer {
    public ContainerCustomSolarPanel(EntityPlayer ip, TileCustomSolarPanel myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);

        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_CUSTOM_SOLAR_PANEL, myTile.getInternalInventory(), 0, 86, 87, ip.inventory));
        addSlotToContainer(new SlotFiltered(FilterInitializer.FILTER_CUSTOM_SOLAR_PANEL, myTile.getInternalInventory(), 1, 160, 87, ip.inventory));
    }
}
