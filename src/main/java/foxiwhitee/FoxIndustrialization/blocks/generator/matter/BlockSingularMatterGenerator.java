package foxiwhitee.FoxIndustrialization.blocks.generator.matter;

import foxiwhitee.FoxIndustrialization.client.gui.generator.matter.GuiMatterGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.matter.ContainerMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileSingularMatterGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSingularMatterGenerator.class, container = ContainerMatterGenerator.class, gui = GuiMatterGenerator.class)
public class BlockSingularMatterGenerator extends BlockMatterGenerator {
    public BlockSingularMatterGenerator(String name) {
        super(name, TileSingularMatterGenerator.class);
    }
}
