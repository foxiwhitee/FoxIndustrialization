package foxiwhitee.FoxIndustrialization.blocks.generator.kinetic;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockKineticGenerator extends BaseIC2Block {
    public BlockKineticGenerator(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "kineticGenerators/";
    }
}
