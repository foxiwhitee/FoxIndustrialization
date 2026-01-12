package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.blocks.generator.matter.BlockMatterGenerator;
import foxiwhitee.FoxIndustrialization.client.gui.GuiMatterSynthesizer;
import foxiwhitee.FoxIndustrialization.container.ContainerMatterSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.TileMatterSynthesizer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileMatterSynthesizer.class, container = ContainerMatterSynthesizer.class, gui = GuiMatterSynthesizer.class)
public class BlockMatterSynthesizer extends BlockMatterGenerator {
    public BlockMatterSynthesizer(String name) {
        super(name, TileMatterSynthesizer.class);
    }
}
