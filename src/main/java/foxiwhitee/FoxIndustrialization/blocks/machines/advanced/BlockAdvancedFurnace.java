package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.blocks.machines.BlockMachine;
import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedFurnace;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.tileentity.TileEntity;

@SimpleGuiHandler(tile = TileAdvancedFurnace.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMachine.class)
public class BlockAdvancedFurnace extends BlockMachine {
    public BlockAdvancedFurnace(String name) {
        super(name, TileAdvancedFurnace.class);
    }
}
