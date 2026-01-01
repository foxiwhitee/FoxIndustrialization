package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileHybridEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileHybridEnergyStorage.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockHybridEnergyStorage extends BlockEnergyStorage {
    public BlockHybridEnergyStorage(String name) {
        super(name, TileHybridEnergyStorage.class);
    }
}
