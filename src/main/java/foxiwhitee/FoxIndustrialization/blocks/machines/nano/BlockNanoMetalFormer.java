package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMetalFormer;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMetalFormer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoMetalFormer.class, container = ContainerNanoMachine.class, gui = GuiNanoMetalFormer.class)
public class BlockNanoMetalFormer extends BlockNanoMachine {
    public BlockNanoMetalFormer(String name) {
        super(name, TileNanoMetalFormer.class);
    }
}
