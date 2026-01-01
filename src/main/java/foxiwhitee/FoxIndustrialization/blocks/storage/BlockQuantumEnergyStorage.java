package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.quantum.TileQuantumEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumEnergyStorage.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockQuantumEnergyStorage extends BlockEnergyStorage {
    public BlockQuantumEnergyStorage(String name) {
        super(name, TileQuantumEnergyStorage.class);
    }
}
