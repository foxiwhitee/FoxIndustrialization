package foxiwhitee.FoxIndustrialization.blocks.generator.matter;

import foxiwhitee.FoxIndustrialization.client.gui.generator.matter.GuiMatterGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.matter.ContainerMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileQuantumMatterGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumMatterGenerator.class, container = ContainerMatterGenerator.class, gui = GuiMatterGenerator.class)
public class BlockQuantumMatterGenerator extends BlockMatterGenerator {
    public BlockQuantumMatterGenerator(String name) {
        super(name, TileQuantumMatterGenerator.class);
    }
}
