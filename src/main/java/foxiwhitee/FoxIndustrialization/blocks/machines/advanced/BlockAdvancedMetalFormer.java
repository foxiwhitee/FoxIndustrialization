package foxiwhitee.FoxIndustrialization.blocks.machines.advanced;

import foxiwhitee.FoxIndustrialization.client.gui.machine.GuiAdvancedMetalFormer;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMetalFormer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileAdvancedMetalFormer.class, container = ContainerAdvancedMachine.class, gui = GuiAdvancedMetalFormer.class)
public class BlockAdvancedMetalFormer extends BlockAdvancedMachine {
    public BlockAdvancedMetalFormer(String name) {
        super(name, TileAdvancedMetalFormer.class);
    }
}
