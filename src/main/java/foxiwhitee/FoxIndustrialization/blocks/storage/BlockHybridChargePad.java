package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileHybridChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileHybridEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileHybridChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockHybridChargePad extends BlockChargePad {
    public BlockHybridChargePad(String name) {
        super(name, TileHybridChargePad.class);
    }
}
