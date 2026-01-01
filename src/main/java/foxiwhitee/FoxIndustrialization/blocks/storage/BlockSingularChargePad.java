package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.singular.TileSingularChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.singular.TileSingularEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSingularChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockSingularChargePad extends BlockChargePad {
    public BlockSingularChargePad(String name) {
        super(name, TileSingularChargePad.class);
    }
}
