package foxiwhitee.FoxIndustrialization.blocks.generator.fuel;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockGenerator extends BaseIC2Block {
    public BlockGenerator(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "generators/";
    }
}
