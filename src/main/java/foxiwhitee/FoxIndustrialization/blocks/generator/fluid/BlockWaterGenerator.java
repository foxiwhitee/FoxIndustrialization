package foxiwhitee.FoxIndustrialization.blocks.generator.fluid;

import foxiwhitee.FoxIndustrialization.client.gui.generator.fluid.GuiFluidGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fluid.ContainerFluidGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileWaterGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileWaterGenerator.class, container = ContainerFluidGenerator.class, gui = GuiFluidGenerator.class)
public class BlockWaterGenerator extends BlockFluidGenerator {
    public BlockWaterGenerator(String name) {
        super(name, TileWaterGenerator.class);
    }
}
