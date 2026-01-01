package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.nano.TileNanoEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockNanoChargePad extends BlockChargePad {
    public BlockNanoChargePad(String name) {
        super(name, TileNanoChargePad.class);
    }
}
