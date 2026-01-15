package foxiwhitee.FoxIndustrialization.blocks.generator.fuel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.fuel.GuiNanoGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerNanoGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoGenerator.class, container = ContainerNanoGenerator.class, gui = GuiNanoGenerator.class)
public class BlockNanoGenerator extends BlockGenerator {
    public BlockNanoGenerator(String name) {
        super(name, TileNanoGenerator.class);
    }
}
