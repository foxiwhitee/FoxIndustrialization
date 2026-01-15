package foxiwhitee.FoxIndustrialization.container.generator.kinetic;

import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileKineticGenerator;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerKineticGenerator extends FoxBaseContainer {
    public ContainerKineticGenerator(EntityPlayer ip, TileKineticGenerator myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 43, 173);
    }
}
