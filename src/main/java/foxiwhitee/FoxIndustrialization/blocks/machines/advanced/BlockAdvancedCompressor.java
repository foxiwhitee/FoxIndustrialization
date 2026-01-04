package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedCompressor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedCompressor.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMachine.class)
public class BlockAdvancedCompressor extends BlockAdvancedMachine {
    public BlockAdvancedCompressor(String name) {
        super(name, TileAdvancedCompressor.class);
    }
}
