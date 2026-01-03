package foxiwhitee.FoxIndustrialization.blocks.machines;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockMachine extends BaseIC2Block {
    public BlockMachine(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }
}
