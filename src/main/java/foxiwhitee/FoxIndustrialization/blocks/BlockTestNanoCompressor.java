package foxiwhitee.FoxIndustrialization.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxIndustrialization.client.gui.GuiTestNanoCOmpressor;
import foxiwhitee.FoxIndustrialization.container.ContainerTestNanoCompressor;
import foxiwhitee.FoxIndustrialization.tile.machines.TIleTestNanoCompressor;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TIleTestNanoCompressor.class, gui = GuiTestNanoCOmpressor.class, container = ContainerTestNanoCompressor.class)
public class BlockTestNanoCompressor extends BaseIC2Block {
    public BlockTestNanoCompressor() {
        super("name", TIleTestNanoCompressor.class);
    }
}
