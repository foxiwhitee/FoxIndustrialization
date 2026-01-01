package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.tile.storage.TileChargePad;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockChargePad extends BlockEnergyStorage {
    protected IIcon activeIcon;

    public BlockChargePad(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);

        String base = this.getTextureName();
        this.activeIcon = register.registerIcon(base + "Active");
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return switch (side) {
            case 0 -> this.downIcon;
            case 2 -> this.backIcon;
            case 3 -> this.frontIcon;
            case 4 -> this.westIcon;
            case 5 -> this.eastIcon;
            default -> metadata == 1 ? this.activeIcon : this.topIcon;
        };
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (world.isRemote) {
            TileEntity te = this.getTileEntity(world, x, y, z);
            if (te instanceof TileChargePad tile) {
                tile.spawnParticles(world, x, y, z, random);
            }
        }
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote) {
            if (entity instanceof EntityPlayer) {
                TileEntity te = this.getTileEntity(world, x, y, z);
                if (te instanceof TileChargePad tile) {
                    tile.playerStandSat((EntityPlayer)entity);
                }
            }
        }
    }

    @Override
    protected boolean hasUpDownRotate() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isNormalCube(IBlockAccess world, int i, int j, int k) {
        return false;
    }

    @Override
    public String getFolder() {
        return "chargePad/";
    }
}
