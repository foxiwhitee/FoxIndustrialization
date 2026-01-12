package foxiwhitee.FoxIndustrialization.tile.generator.fluid;

import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.List;

public abstract class TileFluidGenerator extends TileIC2Inv implements IFluidHandler {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 1);
    private final FluidTank tank;

    public TileFluidGenerator(int tankSize) {
        this.tank = new FluidTank(tankSize);
        this.maxEnergy = tankSize;
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (!worldObj.isRemote) {
            int need = tank.getCapacity() - tank.getFluidAmount();

            if (need > 0) {
                int can = (int) Math.min(need, this.energy);
                this.energy -= can;
                if (tank.getFluid() == null) {
                    tank.setFluid(new FluidStack(getFluid(), can));
                } else {
                    tank.fill(new FluidStack(getFluid(), can), true);
                }
                markForUpdate();
            }
            fillContainerInSlot();
        }
    }

    protected abstract Fluid getFluid();

    private void fillContainerInSlot() {
        ItemStack input = inventory.getStackInSlot(0);
        if (input == null || tank.getFluidAmount() <= 0) return;

        if (FluidContainerRegistry.isContainer(input)) {
            FluidStack tankFluid = tank.getFluid();

            ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer(tankFluid, input);

            if (filledContainer != null) {
                int amountNeeded = FluidContainerRegistry.getContainerCapacity(filledContainer);

                if (tank.getFluidAmount() >= amountNeeded && canInsert(output, filledContainer)) {

                    tank.drain(amountNeeded, true);
                    insert(output, filledContainer);

                    input.stackSize--;
                    if (input.stackSize <= 0) {
                        inventory.setInventorySlotContents(0, null);
                    }

                    this.markForUpdate();
                }
            }
        }
    }

    private boolean canInsert(FoxInternalInventory inv, ItemStack stack) {
        ItemStack inSlot = inv.getStackInSlot(0);
        if (inSlot == null) return true;
        return ItemStackUtil.stackEquals(stack, inSlot) && (inSlot.getMaxStackSize() - inSlot.stackSize) >= stack.stackSize;
    }

    private void insert(FoxInternalInventory inv, ItemStack stack) {
        ItemStack inSlot = inv.getStackInSlot(0);
        stack = stack.copy();
        if (inSlot == null) {
            inv.setInventorySlotContents(0, stack);
            return;
        }
        if (ItemStackUtil.stackEquals(inSlot, stack)) {
            int transfer = Math.min(stack.stackSize, inSlot.getMaxStackSize() - inSlot.stackSize);
            inSlot.stackSize += transfer;
            stack.stackSize -= transfer;
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        tank.writeToNBT(data.getCompoundTag("storageTank"));
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        output.readFromNBT(data, "output");
        tank.readFromNBT(data.getCompoundTag("storageTank"));
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        FluidStack fs = tank.getFluid();
        if (fs != null) {
            data.writeBoolean(true);
            data.writeInt(FluidRegistry.getFluidID(fs.getFluid()));
            data.writeInt(fs.amount);
        } else {
            data.writeBoolean(false);
        }
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean changed = false;
        boolean hasFluid = data.readBoolean();
        if (hasFluid) {
            int id = data.readInt();
            int amount = data.readInt();
            Fluid f = FluidRegistry.getFluid(id);
            if (tank.getFluid() == null || tank.getFluid().getFluid() != f || tank.getFluid().amount != amount) {
                tank.setFluid(new FluidStack(f, amount));
                changed = true;
            }
        } else {
            if (tank.getFluid() != null) {
                tank.setFluid(null);
                changed = true;
            }
        }
        return changed || old;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        if (output.getStackInSlot(0) != null) {
            drops.add(output.getStackInSlot(0));
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || tank.getFluid() == null) {
            return null;
        }

        if (!tank.getFluid().isFluidEqual(resource)) {
            return null;
        }

        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) { return false; } // Входу немає

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) { return true; }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{ tank.getInfo() };
    }

    public FluidTank getTank() {
        return tank;
    }

    public abstract InfoGui getInfoAboutGui();

    public static class InfoGui {
        private final int yStart;
        private final int xStart;
        private final int length;

        public InfoGui(int xStart, int yStart, int length) {
            this.yStart = yStart;
            this.xStart = xStart;
            this.length = length;
        }

        public int getLength() {
            return length;
        }

        public int getXStart() {
            return xStart;
        }

        public int getYStart() {
            return yStart;
        }

    }
}
