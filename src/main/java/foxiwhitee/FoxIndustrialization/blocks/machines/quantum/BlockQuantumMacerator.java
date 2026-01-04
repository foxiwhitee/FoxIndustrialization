package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumMacerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumMacerator.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMachine.class)
public class BlockQuantumMacerator extends BlockQuantumMachine {
    public BlockQuantumMacerator(String name) {
        super(name, TileQuantumMacerator.class);
    }
}
