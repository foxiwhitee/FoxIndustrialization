package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiMolecularTransformer;
import foxiwhitee.FoxIndustrialization.container.ContainerMolecularTransformer;
import foxiwhitee.FoxIndustrialization.tile.TileMolecularTransformer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileMolecularTransformer.class, container = ContainerMolecularTransformer.class, gui = GuiMolecularTransformer.class)
public class BlockMolecularTransformer extends BaseIC2Block {
    public BlockMolecularTransformer(String name) {
        super(name, TileMolecularTransformer.class);
    }

    @Override
    public String getFolder() {
        return "molecularTransformer/";
    }
}
