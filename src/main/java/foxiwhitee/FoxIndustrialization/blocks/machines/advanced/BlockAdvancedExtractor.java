package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedExtractor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedExtractor.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMachine.class)
public class BlockAdvancedExtractor extends BlockAdvancedMachine {
    public BlockAdvancedExtractor(String name) {
        super(name, TileAdvancedExtractor.class);
    }
}
