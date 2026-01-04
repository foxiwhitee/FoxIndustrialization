package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMacerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedMacerator.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMachine.class)
public class BlockAdvancedMacerator extends BlockAdvancedMachine {
    public BlockAdvancedMacerator(String name) {
        super(name, TileAdvancedMacerator.class);
    }
}
