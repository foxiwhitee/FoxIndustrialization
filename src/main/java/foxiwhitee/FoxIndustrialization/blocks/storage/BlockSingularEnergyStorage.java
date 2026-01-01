package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.singular.TileSingularEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSingularEnergyStorage.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockSingularEnergyStorage extends BlockEnergyStorage {
    public BlockSingularEnergyStorage(String name) {
        super(name, TileSingularEnergyStorage.class);
    }
}
