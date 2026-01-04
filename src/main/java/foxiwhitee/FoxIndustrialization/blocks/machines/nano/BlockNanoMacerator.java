package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMacerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoMacerator.class, container = ContainerNanoMachine.class, gui = GuiNanoMachine.class)
public class BlockNanoMacerator extends BlockNanoMachine {
    public BlockNanoMacerator(String name) {
        super(name, TileNanoMacerator.class);
    }
}
