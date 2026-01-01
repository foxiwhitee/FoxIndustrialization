package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockQuantumChargePad extends BlockChargePad {
    public BlockQuantumChargePad(String name) {
        super(name, TileQuantumChargePad.class);
    }
}
