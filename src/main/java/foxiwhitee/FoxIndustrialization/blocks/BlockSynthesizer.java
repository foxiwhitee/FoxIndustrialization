package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiSynthesizer;
import foxiwhitee.FoxIndustrialization.container.ContainerSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.TileSynthesizer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSynthesizer.class, container = ContainerSynthesizer.class, gui = GuiSynthesizer.class)
public class BlockSynthesizer extends BaseIC2Block {
    public BlockSynthesizer(String name) {
        super(name, TileSynthesizer.class);
    }
}
