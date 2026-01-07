package foxiwhitee.FoxIndustrialization.blocks.generator.infinity;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import foxiwhitee.FoxIndustrialization.client.gui.generator.infinity.GuiInfinityGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.infinity.ContainerInfinityGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.infinity.TileInfinityGenerator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileInfinityGenerator.class, container = ContainerInfinityGenerator.class, gui = GuiInfinityGenerator.class)
public class BlockInfinityGenerator extends BaseIC2Block {
    public BlockInfinityGenerator(String name) {
        super(name, TileInfinityGenerator.class);
    }

    @Override
    public String getFolder() {
        return "infinityGenerator/";
    }
}
