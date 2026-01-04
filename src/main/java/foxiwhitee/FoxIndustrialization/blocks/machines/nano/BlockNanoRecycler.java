package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoRecycler;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoRecycler.class, container = ContainerNanoMachine.class, gui = GuiNanoMachine.class)
public class BlockNanoRecycler extends BlockNanoMachine {
    public BlockNanoRecycler(String name) {
        super(name, TileNanoRecycler.class);
    }
}
