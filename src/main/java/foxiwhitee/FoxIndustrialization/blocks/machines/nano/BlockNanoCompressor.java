package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoCompressor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoCompressor.class, container = ContainerNanoMachine.class, gui = GuiNanoMachine.class)
public class BlockNanoCompressor extends BlockNanoMachine {
    public BlockNanoCompressor(String name) {
        super(name, TileNanoCompressor.class);
    }
}
