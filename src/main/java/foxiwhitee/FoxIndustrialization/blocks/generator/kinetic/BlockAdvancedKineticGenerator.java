package foxiwhitee.FoxIndustrialization.blocks.generator.kinetic;

import foxiwhitee.FoxIndustrialization.client.gui.generator.kinetic.GuiKineticGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.kinetic.ContainerKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileAdvancedKineticGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedKineticGenerator.class, container = ContainerKineticGenerator.class, gui = GuiKineticGenerator.class)
public class BlockAdvancedKineticGenerator extends BlockKineticGenerator{
    public BlockAdvancedKineticGenerator(String name) {
        super(name, TileAdvancedKineticGenerator.class);
    }
}
