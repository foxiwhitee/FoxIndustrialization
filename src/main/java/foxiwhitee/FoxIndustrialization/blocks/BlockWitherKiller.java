package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiWitherKiller;
import foxiwhitee.FoxIndustrialization.container.ContainerWitherKiller;
import foxiwhitee.FoxIndustrialization.tile.TileWitherKiller;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.tileentity.TileEntity;

@SimpleGuiHandler(tile = TileWitherKiller.class, container = ContainerWitherKiller.class, gui = GuiWitherKiller.class)
public class BlockWitherKiller extends BaseIC2Block {
    public BlockWitherKiller(String name) {
        super(name, TileWitherKiller.class);
    }

    @Override
    public String getFolder() {
        return "witherKiller";
    }
}
