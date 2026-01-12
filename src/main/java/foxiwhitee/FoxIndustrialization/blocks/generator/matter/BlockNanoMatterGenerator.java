package foxiwhitee.FoxIndustrialization.blocks.generator.matter;

import foxiwhitee.FoxIndustrialization.client.gui.generator.matter.GuiMatterGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.matter.ContainerMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileNanoMatterGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoMatterGenerator.class, container = ContainerMatterGenerator.class, gui = GuiMatterGenerator.class)
public class BlockNanoMatterGenerator extends BlockMatterGenerator {
    public BlockNanoMatterGenerator(String name) {
        super(name, TileNanoMatterGenerator.class);
    }
}
