package foxiwhitee.FoxIndustrialization.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.api.IHasActiveState;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public abstract class BaseIC2Block extends FoxBaseBlock {
    protected IIcon topActiveIcon;
    protected IIcon downActiveIcon;
    protected IIcon frontActiveIcon;
    protected IIcon backActiveIcon;
    protected IIcon eastActiveIcon;
    protected IIcon westActiveIcon;

    public BaseIC2Block(String name, Class<? extends TileEntity> tileEntityClass) {
        super(FICore.MODID, name);
        setCreativeTab(FICore.TAB);
        setTileEntityType(tileEntityClass);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        String base = this.getTextureName();
        this.topActiveIcon = this.addIfExists(register, base + "Active", this.topIcon);
        this.downActiveIcon = this.addIfExists(register, base + "DownActive", this.downIcon);
        IIcon sideIcon = this.addIfExists(register, base + "Side", this.topIcon);
        IIcon sideActiveIcon = this.addIfExists(register, base + "SideActive", sideIcon);
        this.frontActiveIcon = this.addIfExists(register, base + "FrontActive", sideActiveIcon);
        this.backActiveIcon = this.addIfExists(register, base + "BackActive", sideActiveIcon);
        this.eastActiveIcon = this.addIfExists(register, base + "EastActive", sideActiveIcon);
        this.westActiveIcon = this.addIfExists(register, base + "WestActive", sideActiveIcon);
    }

    @Override
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
        TileEntity ori = w.getTileEntity(x, y, z);
        int meta = w.getBlockMetadata(x, y, z);
        if (ori instanceof IHasActiveState state) {
            meta = state.isActive() ? 1 : meta;
        }
        if (ori instanceof IOrientable orientable) {

            ForgeDirection sideWorld = ForgeDirection.getOrientation(s);
            ForgeDirection logicalSide = this.mapRotation(orientable, sideWorld);
            return this.getIcon(logicalSide.ordinal(), meta);
        } else {
            return this.getIcon(s, meta);
        }
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return switch (side) {
            case 0 -> metadata == 1 ? this.downActiveIcon : this.downIcon;
            case 2 -> metadata == 1 ? this.backActiveIcon : this.backIcon;
            case 3 -> metadata == 1 ? this.frontActiveIcon : this.frontIcon;
            case 4 -> metadata == 1 ? this.westActiveIcon : this.westIcon;
            case 5 -> metadata == 1 ? this.eastActiveIcon : this.eastIcon;
            default -> metadata == 1 ? this.topActiveIcon : this.topIcon;
        };
    }

    private IIcon addIfExists(IIconRegister register, String name, IIcon defaultIcon) {
        return this.exists(name) ? register.registerIcon(name) : defaultIcon;
    }

    private boolean exists(String texturePath) {
        String domain = texturePath.split(":")[0];
        String path = texturePath.split(":")[1];
        return this.getClass().getResource("/assets/" + domain + "/textures/blocks/" + path + ".png") != null;
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
