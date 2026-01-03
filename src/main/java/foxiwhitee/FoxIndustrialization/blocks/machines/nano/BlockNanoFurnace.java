package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.blocks.machines.BlockMachine;
import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiNanoMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedFurnace;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoFurnace;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileNanoFurnace.class, container = ContainerNanoMachine.class, gui = GuiNanoMachine.class)
public class BlockNanoFurnace extends BlockMachine {
    public BlockNanoFurnace(String name) {
        super(name, TileNanoFurnace.class);
    }
}
