package foxiwhitee.FoxIndustrialization.blocks.generator.matter;

import foxiwhitee.FoxIndustrialization.client.gui.generator.matter.GuiMatterGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.matter.ContainerMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileAdvancedMatterGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedMatterGenerator.class, container = ContainerMatterGenerator.class, gui = GuiMatterGenerator.class)
public class BlockAdvancedMatterGenerator extends BlockMatterGenerator {
    public BlockAdvancedMatterGenerator(String name) {
        super(name, TileAdvancedMatterGenerator.class);
    }
}
