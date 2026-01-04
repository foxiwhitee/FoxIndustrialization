package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumRecycler;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumRecycler.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMachine.class)
public class BlockQuantumRecycler extends BlockQuantumMachine {
    public BlockQuantumRecycler(String name) {
        super(name, TileQuantumRecycler.class);
    }
}
