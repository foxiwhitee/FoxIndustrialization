package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMetalFormer;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumMetalFormer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumMetalFormer.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMetalFormer.class)
public class BlockQuantumMetalFormer extends BlockQuantumMachine {
    public BlockQuantumMetalFormer(String name) {
        super(name, TileQuantumMetalFormer.class);
    }
}
