package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumCompressor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumCompressor.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMachine.class)
public class BlockQuantumCompressor extends BlockQuantumMachine {
    public BlockQuantumCompressor(String name) {
        super(name, TileQuantumCompressor.class);
    }
}
