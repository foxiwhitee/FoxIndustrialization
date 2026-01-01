package foxiwhitee.FoxIndustrialization.blocks.storage;

import foxiwhitee.FoxIndustrialization.blocks.BaseIC2Block;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockEnergyStorage extends BaseIC2Block {
    public BlockEnergyStorage(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "storage/";
    }

    @Override
    protected boolean hasUpDownRotate() {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEnergyStorage tile) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null && tag.hasKey("energy")) {
                tile.setEnergy(tag.getDouble("energy"));
            }
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (willHarvest) return true;
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        int count = quantityDropped(metadata, fortune, world.rand);

        TileEntity te = world.getTileEntity(x, y, z);
        double energyToSave = 0;

        if (te instanceof TileEnergyStorage) {
            energyToSave = ((TileEnergyStorage)te).getEnergy();
        }

        for(int i = 0; i < count; i++) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ItemStack stack = new ItemStack(item, 1, damageDropped(metadata));
                NBTTagCompound nbt = new NBTTagCompound();

                nbt.setDouble("energy", energyToSave);
                stack.setTagCompound(nbt);

                ret.add(stack);
            }
        }
        return ret;
    }
}
