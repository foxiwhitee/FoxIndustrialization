package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileBasicEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileBasicChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockBasicChargePad extends BlockChargePad {
    public BlockBasicChargePad(String name) {
        super(name, TileBasicChargePad.class);
    }
}
