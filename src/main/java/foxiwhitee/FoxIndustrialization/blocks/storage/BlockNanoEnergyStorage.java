package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoEnergyStorage.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockNanoEnergyStorage extends BlockEnergyStorage {
    public BlockNanoEnergyStorage(String name) {
        super(name, TileNanoEnergyStorage.class);
    }
}
