package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiQuantumMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumFurnace;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumFurnace.class, container = ContainerQuantumMachine.class, gui = GuiQuantumMachine.class)
public class BlockQuantumFurnace extends BlockQuantumMachine {
    public BlockQuantumFurnace(String name) {
        super(name, TileQuantumFurnace.class);
    }
}
