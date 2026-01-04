package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoExtractor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoExtractor.class, container = ContainerNanoMachine.class, gui = GuiNanoMachine.class)
public class BlockNanoExtractor extends BlockNanoMachine {
    public BlockNanoExtractor(String name) {
        super(name, TileNanoExtractor.class);
    }
}
