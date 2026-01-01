package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileBasicEnergyStorage.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockBasicEnergyStorage extends BlockEnergyStorage {
    public BlockBasicEnergyStorage(String name) {
        super(name, TileBasicEnergyStorage.class);
    }
}
