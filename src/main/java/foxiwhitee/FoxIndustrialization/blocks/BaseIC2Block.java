package foxiwhitee.FoxIndustrialization.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BaseIC2Block extends FoxBaseBlock {

    public BaseIC2Block(String name, Class<? extends TileEntity> tileEntityClass) {
        super(FICore.MODID, name);
        setCreativeTab(FICore.TAB);
        setTileEntityType(tileEntityClass);
    }

}
