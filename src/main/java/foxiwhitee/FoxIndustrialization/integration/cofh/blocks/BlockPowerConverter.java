package foxiwhitee.FoxIndustrialization.integration.cofh.blocks;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import foxiwhitee.FoxIndustrialization.integration.cofh.client.gui.GuiPowerConverter;
import foxiwhitee.FoxIndustrialization.integration.cofh.container.ContainerPowerConverter;
import foxiwhitee.FoxIndustrialization.integration.cofh.tile.TilePowerConverter;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.tileentity.TileEntity;

@SimpleGuiHandler(tile = TilePowerConverter.class, container = ContainerPowerConverter.class, gui = GuiPowerConverter.class)
public class BlockPowerConverter extends BaseIC2Block {
    public BlockPowerConverter(String name) {
        super(name, TilePowerConverter.class);
    }
}
