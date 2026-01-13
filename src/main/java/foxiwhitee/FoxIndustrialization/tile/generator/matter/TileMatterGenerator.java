package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxIndustrialization.utils.InventoryUtils;
import foxiwhitee.FoxIndustrialization.utils.UpgradeUtils;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.core.Ic2Items;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.*;

public abstract class TileMatterGenerator extends TileIC2Inv implements IFluidHandler, IUpgradableTile {
    protected static Fluid fluid;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory scrap = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3);
    protected final FluidTank tank;
    protected int amount;
    private int amplifier;

    private int scanTimer;
    private final IInventory[] adjacentInventories = new IInventory[6];
    private final IFluidHandler[] adjacentFluidHandlers = new IFluidHandler[6];
    private ForgeDirection[] pushSides = {}, pushFluidSides = {}, pullSides = {};
    private boolean hasEjector, hasEjectorFluid, hasPuller;

    public TileMatterGenerator(int tankSize, double needEnergy, int amount) {
        this.tank = new FluidTank(tankSize);
        this.amount = amount;
        this.maxEnergy = needEnergy;
        if (fluid == null) {
            fluid = FluidRegistry.getFluid("ic2uumatter");
        }
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (!worldObj.isRemote) {
            if (scanTimer-- <= 0) {
                UpgradeUtils.updateAdjacentCache(this, adjacentInventories, adjacentFluidHandlers);
                scanTimer = 20;
            }
            if (hasPuller) {
                UpgradeUtils.pullIfCan(pullSides, adjacentInventories, inventory);
            }
            if (hasEjector) {
                if (UpgradeUtils.pushIfCan(pushSides, adjacentInventories, output)) {
                    markForUpdate();
                }
            }
            if (hasEjectorFluid) {
                if (UpgradeUtils.pushFluidIfCan(tank, pushFluidSides, adjacentFluidHandlers)) {
                    markForUpdate();
                }
            }

            if (amplifier < 10000) {
                getAmplifier();
            }
            if (this.energy >= this.maxEnergy) {
                if (amount > 0 && tank.getFluidAmount() + amount <= tank.getCapacity()) {
                    this.energy -= this.maxEnergy;
                    if (tank.getFluid() == null) {
                        tank.setFluid(new FluidStack(fluid, amount));
                    } else {
                        tank.fill(new FluidStack(fluid, amount), true);
                    }
                    markForUpdate();
                }
            }
            fillContainerInSlot();
        }
    }

    private void getAmplifier() {
        ItemStack stack = scrap.getStackInSlot(0);
        if (stack != null) {
            if (stack.getItem() == Ic2Items.scrap.getItem()) {
                amplifier += 5000;
                stack.stackSize--;
            } else if (stack.getItem() == Ic2Items.scrapBox.getItem()) {
                amplifier += 45000;
                stack.stackSize--;
            }
            if (stack.stackSize <= 0) {
                scrap.setInventorySlotContents(0, null);
            }
        }
    }

    private void fillContainerInSlot() {
        ItemStack input = inventory.getStackInSlot(0);
        if (input == null || tank.getFluidAmount() <= 0) return;

        if (FluidContainerRegistry.isContainer(input)) {
            FluidStack tankFluid = tank.getFluid();

            ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer(tankFluid, input);

            if (filledContainer != null) {
                int amountNeeded = FluidContainerRegistry.getContainerCapacity(filledContainer);

                if (tank.getFluidAmount() >= amountNeeded && InventoryUtils.canInsert(output, filledContainer, 0)) {

                    tank.drain(amountNeeded, true);
                    InventoryUtils.insert(output, filledContainer, 0);

                    input.stackSize--;
                    if (input.stackSize <= 0) {
                        inventory.setInventorySlotContents(0, null);
                    }

                    this.markForUpdate();
                }
            }
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        scrap.writeToNBT(data, "scrap");
        upgrades.writeToNBT(data, "upgrades");
        InventoryUtils.writeTankToNbt(data, "tank", this.tank);
        data.setInteger("amplifier", amplifier);
        data.setBoolean("hasPuller", hasPuller);
        data.setBoolean("hasEjector", hasEjector);
        data.setBoolean("hasEjectorFluid", hasEjectorFluid);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        output.readFromNBT(data, "output");
        scrap.readFromNBT(data, "scrap");
        upgrades.readFromNBT(data, "upgrades");
        InventoryUtils.readTankFromNbt(data, "tank", this.tank);
        amplifier = data.getInteger("amplifier");
        hasPuller = data.getBoolean("hasPuller");
        hasEjector = data.getBoolean("hasEjector");
        hasEjectorFluid = data.getBoolean("hasEjectorFluid");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(amount);
        InventoryUtils.writeTankToStream(data, tank);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        int oldAmount = amount;
        amount = data.readInt();
        boolean tankChanged = InventoryUtils.readTankFromStream(data, tank);
        return tankChanged || old || oldAmount != amount;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return scrap;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    public FoxInternalInventory getRealInventory() {
        return inventory;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[] {0};
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            var handler = UpgradeUtils.newHandler(this, upgrades)
                .ejector()
                .ejectorFluid()
                .puller()
                .process();

            this.hasEjector = handler.hasEjector();
            this.hasEjectorFluid = handler.hasEjectorFluid();
            this.hasPuller = handler.hasPuller();
            this.pushSides = handler.getPushSides();
            this.pushFluidSides = handler.getPushFluidSides();
            this.pullSides = handler.getPullSides();
        }
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        if (output.getStackInSlot(0) != null) {
            drops.add(output.getStackInSlot(0));
        }
        if (scrap.getStackInSlot(0) != null) {
            drops.add(scrap.getStackInSlot(0));
        }
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack stack = upgrades.getStackInSlot(i);
            if (stack != null) {
                drops.add(stack);
            }
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

    @Override
    public double injectEnergy(ForgeDirection forgeDirection, double amount, double v1) {
        if (!(this.energy >= this.maxEnergy)) {
            int bonus = Math.min((int)amount, this.amplifier);
            this.amplifier -= bonus;
            this.energy += amount + (double)(5 * bonus);

            markForUpdate();
            return 0;
        } else {
            return amount;
        }
    }

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.EJECTOR, UpgradesTypes.PULLING, UpgradesTypes.FLUID_EJECTOR};
    }

    public FluidTank getTank() {
        return tank;
    }

    public int getAmount() {
        return amount;
    }

    public abstract InfoGui getInfoAboutGui();

    public static class InfoGui {
        private final String textureName;
        private final int yStart;
        private final int xStart;
        private final int length;

        public InfoGui(String textureName, int xStart, int yStart, int length) {
            this.textureName = textureName;
            this.yStart = yStart;
            this.xStart = xStart;
            this.length = length;
        }

        public String getTextureName() {
            return textureName;
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
