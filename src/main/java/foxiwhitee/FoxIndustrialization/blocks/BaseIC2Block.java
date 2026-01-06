package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BaseIC2Block extends FoxBaseBlock {

    public BaseIC2Block(String name, Class<? extends TileEntity> tileEntityClass) {
        super(FICore.MODID, name);
        setCreativeTab(FICore.TAB);
        setTileEntityType(tileEntityClass);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int b) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileIC2Inv invTile) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            invTile.getDrops(world, x, y, z, drops);
            spawnDrops(world, x, y, z, drops);
        }

        super.breakBlock(world, x, y, z, block, b);
    }
}
