package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import net.minecraft.tileentity.TileEntity;

public abstract class BaseIC2Block extends FoxBaseBlock {
    public BaseIC2Block(String name, Class<? extends TileEntity> tileEntityClass) {
        super(FICore.MODID, name);
        setCreativeTab(FICore.TAB);
        setTileEntityType(tileEntityClass);
    }
}
