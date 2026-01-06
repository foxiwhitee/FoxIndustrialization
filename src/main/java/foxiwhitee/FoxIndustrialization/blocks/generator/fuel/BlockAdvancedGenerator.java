package foxiwhitee.FoxIndustrialization.blocks.generator.fuel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.fuel.GuiAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileAdvancedGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedGenerator.class, container = ContainerAdvancedGenerator.class, gui = GuiAdvancedGenerator.class)
public class BlockAdvancedGenerator extends BlockGenerator {
    public BlockAdvancedGenerator(String name) {
        super(name, TileAdvancedGenerator.class);
    }
}
