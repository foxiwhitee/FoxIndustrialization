package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiAdvancedScanner;
import foxiwhitee.FoxIndustrialization.container.ContainerAdvancedScanner;
import foxiwhitee.FoxIndustrialization.tile.TileAdvancedScanner;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.tileentity.TileEntity;

@SimpleGuiHandler(tile = TileAdvancedScanner.class, container = ContainerAdvancedScanner.class, gui = GuiAdvancedScanner.class)
public class BlockAdvancedScanner extends BaseIC2Block {
    public BlockAdvancedScanner(String name) {
        super(name, TileAdvancedScanner.class);
    }

    @Override
    public String getFolder() {
        return "advancedScanner/";
    }
}
