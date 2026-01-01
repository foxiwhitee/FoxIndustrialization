package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.client.gui.storage.GuiEnergyStorage;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileAdvancedChargePad;
import foxiwhitee.FoxIndustrialization.tile.storage.advanced.TileAdvancedEnergyStorage;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedChargePad.class, gui = GuiEnergyStorage.class, container = ContainerEnergyStorage.class)
public class BlockAdvancedChargePad extends BlockChargePad {
    public BlockAdvancedChargePad(String name) {
        super(name, TileAdvancedChargePad.class);
    }
}
