package foxiwhitee.FoxIndustrialization.blocks.generator.fluid;

import foxiwhitee.FoxIndustrialization.client.gui.generator.fluid.GuiFluidGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fluid.ContainerFluidGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileLavaGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileLavaGenerator.class, container = ContainerFluidGenerator.class, gui = GuiFluidGenerator.class)
public class BlockLavaGenerator extends BlockFluidGenerator {
    public BlockLavaGenerator(String name) {
        super(name, TileLavaGenerator.class);
    }
}
