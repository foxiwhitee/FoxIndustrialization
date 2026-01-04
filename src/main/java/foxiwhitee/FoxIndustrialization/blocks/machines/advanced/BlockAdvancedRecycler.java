package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedRecycler;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedRecycler.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMachine.class)
public class BlockAdvancedRecycler extends BlockAdvancedMachine {
    public BlockAdvancedRecycler(String name) {
        super(name, TileAdvancedRecycler.class);
    }
}
