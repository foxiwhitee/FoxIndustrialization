package foxiwhitee.FoxIndustrialization.blocks.generator.kinetic;

import foxiwhitee.FoxIndustrialization.client.gui.generator.kinetic.GuiKineticGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.kinetic.ContainerKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileQuantumKineticGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumKineticGenerator.class, container = ContainerKineticGenerator.class, gui = GuiKineticGenerator.class)
public class BlockQuantumKineticGenerator extends BlockKineticGenerator{
    public BlockQuantumKineticGenerator(String name) {
        super(name, TileQuantumKineticGenerator.class);
    }
}
