package foxiwhitee.FoxIndustrialization.blocks.generator.fluid;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockFluidGenerator extends BaseIC2Block {
    public BlockFluidGenerator(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "fluidGenerators/";
    }
}
