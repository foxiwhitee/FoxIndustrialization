package foxiwhitee.FoxIndustrialization.blocks.generator.kinetic;

import foxiwhitee.FoxIndustrialization.client.gui.generator.kinetic.GuiKineticGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.kinetic.ContainerKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileNanoKineticGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoKineticGenerator.class, container = ContainerKineticGenerator.class, gui = GuiKineticGenerator.class)
public class BlockNanoKineticGenerator extends BlockKineticGenerator{
    public BlockNanoKineticGenerator(String name) {
        super(name, TileNanoKineticGenerator.class);
    }
}
