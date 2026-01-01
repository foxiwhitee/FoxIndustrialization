package foxiwhitee.FoxIndustrialization.tile.storage;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.Ic2Items;
import ic2.core.util.EntityIC2FX;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public abstract class TileChargePad extends TileEnergyStorage {
    private final static int needTicks = 20;
    private boolean active = false;
    private EntityPlayer player = null;
    private int tick;
    private int meta = -1;

    public TileChargePad(int tier, double storage, double output) {
        super(tier, storage, output);
    }

    @TileEvent(TileEventType.TICK)
    @Override
    public void tick() {
        super.tick();
        if (this.worldObj.isRemote) {
            return;
        }

        if (meta == -1) {
            meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        }

        boolean needUpdate = false;
        if (tick++ >= needTicks) {
            tick = 0;
            if (this.player != null && this.energy >= (double)1.0F) {
                if (!isActive()) {
                    active = true;
                    if (meta == 0) {
                        meta = 1;
                        setNewMeta(1);
                    }
                }

                this.chargePlayerItems(this.player);
                this.player = null;
                needUpdate = true;
            } else if (isActive()) {
                active = false;
                needUpdate = true;
                if (meta == 1) {
                    meta = 0;
                    setNewMeta(0);
                }
            }

            if (needUpdate) {
                this.markForUpdate();
            }
        }
    }

    private void chargePlayerItems(EntityPlayer player) {
        for(ItemStack current : player.inventory.armorInventory) {
            if (current != null) {
                this.chargeItems(current);
            }
        }

        for(ItemStack current : player.inventory.mainInventory) {
            if (current != null) {
                this.chargeItems(current);
            }
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    @Override
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        meta = data.getInteger("meta");
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    @Override
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        data.setInteger("meta", meta);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    @Override
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean oldActive = isActive();
        active = data.readBoolean();
        return old || oldActive != active;
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    @Override
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeBoolean(isActive());
    }

    public void playerStandSat(EntityPlayer entity) {
        if (this.player == null) {
            this.player = entity;
        } else if (this.player.getUniqueID() != entity.getUniqueID()) {
            this.player = entity;
        }
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tileEntity, ForgeDirection forgeDirection) {
        if (forgeDirection == ForgeDirection.UP) {
            return false;
        }
        return super.acceptsEnergyFrom(tileEntity, forgeDirection);
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles(World world, int blockX, int blockY, int blockZ, Random rand) {
        if (this.isActive()) {
            EffectRenderer effect = FMLClientHandler.instance().getClient().effectRenderer;

            for(int particles = 20; particles > 0; --particles) {
                double x = (blockX + 0 + rand.nextFloat());
                double y = (blockY + 0.9 + rand.nextFloat());
                double z = (blockZ + 0 + rand.nextFloat());
                effect.addEffect(new EntityIC2FX(world, x, y, z, 60, new double[]{0, 0.1, 0}, new float[]{0.2F, 0.2F, 1F}));
            }
        }
    }

    protected void chargeItems(ItemStack itemstack) {
        if (itemstack.getItem() instanceof IElectricItem) {
            if (itemstack.getItem() != Ic2Items.debug.getItem()) {
                double freeAmount = ElectricItem.manager.charge(itemstack, Double.POSITIVE_INFINITY, this.getSourceTier(), true, true);
                double charge;
                if (freeAmount >= 0) {
                    if (this.energy >= freeAmount) {
                        charge = Math.min(freeAmount, getOutput());
                    } else {
                        charge = this.energy;
                    }

                    this.energy -= ElectricItem.manager.charge(itemstack, charge, this.getSourceTier(), true, false);
                }
            }
        }
    }

    private void setNewMeta(int newMeta) {
        if (worldObj == null || worldObj.isRemote)
            return;

        int oldMeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        if (oldMeta != newMeta) {
            worldObj.setBlockMetadataWithNotify(
                xCoord, yCoord, zCoord,
                newMeta,
                3
            );
        }
    }

    public boolean isActive() {
        return active;
    }
}
