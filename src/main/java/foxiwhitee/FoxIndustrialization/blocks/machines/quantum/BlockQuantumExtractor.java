package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumExtractor;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumExtractor.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMachine.class)
public class BlockQuantumExtractor extends BlockQuantumMachine {
    public BlockQuantumExtractor(String name) {
        super(name, TileQuantumExtractor.class);
    }
}
