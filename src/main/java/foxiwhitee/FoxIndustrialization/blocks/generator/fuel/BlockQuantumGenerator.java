package foxiwhitee.FoxIndustrialization.blocks.generator.fuel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.fuel.GuiQuantumGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerQuantumGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileQuantumGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumGenerator.class, container = ContainerQuantumGenerator.class, gui = GuiQuantumGenerator.class)
public class BlockQuantumGenerator extends BlockGenerator {
    public BlockQuantumGenerator(String name) {
        super(name, TileQuantumGenerator.class);
    }
}
