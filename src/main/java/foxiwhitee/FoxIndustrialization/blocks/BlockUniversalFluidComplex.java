package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiUniversalFluidComplex;
import foxiwhitee.FoxIndustrialization.container.ContainerUniversalFluidComplex;
import foxiwhitee.FoxIndustrialization.tile.TileUniversalFluidComplex;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileUniversalFluidComplex.class, container = ContainerUniversalFluidComplex.class, gui = GuiUniversalFluidComplex.class)
public class BlockUniversalFluidComplex extends BaseIC2Block {
    public BlockUniversalFluidComplex(String name) {
        super(name, TileUniversalFluidComplex.class);
    }
}
