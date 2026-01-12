package foxiwhitee.FoxIndustrialization.blocks.generator.matter;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockMatterGenerator extends BaseIC2Block {
    public BlockMatterGenerator(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "matterGenerators/";
    }
}
