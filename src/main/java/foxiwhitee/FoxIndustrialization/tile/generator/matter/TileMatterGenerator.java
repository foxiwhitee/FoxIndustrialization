package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyReceiver;
import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import ic2.core.Ic2Items;
import ic2.core.upgrade.IUpgradeItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.*;

@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHCore")
public abstract class TileMatterGenerator extends TileIC2Inv implements IFluidHandler, IEnergyReceiver, IDoubleEnergyReceiver, IUpgradableTile {
    protected static Fluid fluid;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory scrap = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3);
    protected final FluidTank tank;
    protected int amount;
    private int amplifier;

    private int scanTimer;
    private final ISidedInventory[] adjacentInventories = new ISidedInventory[6];
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
                updateAdjacentCache();
                scanTimer = 20;
            }
            if (hasPuller) {
                pullIfCan();
            }
            if (hasEjector) {
                pushIfCan();
            }
            if (hasEjectorFluid) {
                pushFluidIfCan();
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

                if (tank.getFluidAmount() >= amountNeeded && canInsert(output, filledContainer, 0)) {

                    tank.drain(amountNeeded, true);
                    insert(output, filledContainer, 0);

                    input.stackSize--;
                    if (input.stackSize <= 0) {
                        inventory.setInventorySlotContents(0, null);
                    }

                    this.markForUpdate();
                }
            }
        }
    }

    private boolean canInsert(FoxInternalInventory inv, ItemStack stack, int idx) {
        ItemStack inSlot = inv.getStackInSlot(idx);
        if (inSlot == null) return true;
        return ItemStackUtil.stackEquals(stack, inSlot) && (inSlot.getMaxStackSize() - inSlot.stackSize) >= stack.stackSize;
    }

    private boolean canInsert(FoxInternalInventory inv, ItemStack stack) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (canInsert(inv, stack, i)) return true;
        }
        return false;
    }

    private void insert(FoxInternalInventory inv, ItemStack stack, int idx) {
        ItemStack inSlot = inv.getStackInSlot(idx);
        stack = stack.copy();
        if (inSlot == null) {
            inv.setInventorySlotContents(idx, stack);
            return;
        }
        if (ItemStackUtil.stackEquals(inSlot, stack)) {
            int transfer = Math.min(stack.stackSize, inSlot.getMaxStackSize() - inSlot.stackSize);
            inSlot.stackSize += transfer;
            stack.stackSize -= transfer;
        }
    }

    private void insert(FoxInternalInventory inv, ItemStack stack) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            insert(inv, stack, i);
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        scrap.writeToNBT(data, "scrap");
        upgrades.writeToNBT(data, "upgrades");
        tank.writeToNBT(data.getCompoundTag("storageTank"));
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
        tank.readFromNBT(data.getCompoundTag("storageTank"));
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
        FluidStack fs = tank.getFluid();
        if (fs != null) {
            data.writeBoolean(true);
            data.writeInt(tank.getCapacity());
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
        int oldAmount = amount;
        amount = data.readInt();
        boolean changed = false;
        boolean hasFluid = data.readBoolean();
        if (hasFluid) {
            tank.setCapacity(data.readInt());
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
        return changed || old || oldAmount != amount;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    public FoxInternalInventory getScrapInventory() {
        return scrap;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            List<Integer> newPushRaw = new ArrayList<>();
            List<Integer> newPushFluidRaw = new ArrayList<>();
            List<Integer> newPullRaw = new ArrayList<>();

            List<UpgradesTypes> available = Arrays.asList(getAvailableTypes());
            boolean canEject = available.contains(UpgradesTypes.EJECTOR);
            boolean canEjectFluid = available.contains(UpgradesTypes.FLUID_EJECTOR);
            boolean canPull = available.contains(UpgradesTypes.PULLING);

            for (int j = 0; j < upgrades.getSizeInventory(); j++) {
                ItemStack stack = upgrades.getStackInSlot(j);
                if (stack == null) continue;

                if (stack.getItem() instanceof IUpgradeItem) {
                    switch (stack.getItemDamage()) {
                        case 3: // Ejector
                            if (canEject) {
                                NBTTagCompound nbt = stack.getTagCompound();
                                newPushRaw.add((nbt != null && nbt.hasKey("dir")) ? (int)nbt.getByte("dir") : 0);
                            }
                            break;
                        case 4: // Ejector Fluid
                            if (canEjectFluid) {
                                NBTTagCompound nbt = stack.getTagCompound();
                                newPushFluidRaw.add((nbt != null && nbt.hasKey("dir")) ? (int)nbt.getByte("dir") : 0);
                            }
                            break;
                        case 6: // Pulling
                            if (canPull) {
                                NBTTagCompound nbt = stack.getTagCompound();
                                newPullRaw.add((nbt != null && nbt.hasKey("dir")) ? (int)nbt.getByte("dir") : 0);
                            }
                            break;
                    }
                }
            }
            this.pushSides = parseSides(newPushRaw);
            this.pushFluidSides = parseSides(newPushFluidRaw);
            this.pullSides = parseSides(newPullRaw);
            this.hasEjector = pushSides.length > 0;
            this.hasEjectorFluid = pushFluidSides.length > 0;
            this.hasPuller = pullSides.length > 0;

        }
    }

    private ForgeDirection[] parseSides(List<Integer> raw) {
        if (raw.isEmpty()) return new ForgeDirection[0];
        if (raw.contains(0)) return ForgeDirection.VALID_DIRECTIONS;

        Set<ForgeDirection> dirs = new HashSet<>();
        for (int s : raw) {
            switch (s) {
                case 1: dirs.add(ForgeDirection.WEST); break;
                case 2: dirs.add(ForgeDirection.EAST); break;
                case 3: dirs.add(ForgeDirection.DOWN); break;
                case 4: dirs.add(ForgeDirection.UP); break;
                case 5: dirs.add(ForgeDirection.NORTH); break;
                case 6: dirs.add(ForgeDirection.SOUTH); break;
            }
        }
        return dirs.toArray(new ForgeDirection[0]);
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

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return (int) Math.min(Integer.MAX_VALUE, this.receiveDoubleEnergy(forgeDirection, i, b));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, this.getDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return (int) Math.min(Integer.MAX_VALUE, this.getMaxDoubleEnergyStored(forgeDirection));
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return canConnectDoubleEnergy(forgeDirection);
    }

    @Override
    public boolean canConnectDoubleEnergy(ForgeDirection direction) {
        return supportsRF();
    }

    @Override
    public double receiveDoubleEnergy(ForgeDirection direction, double maxReceive, boolean simulate) {
        if (direction == ForgeDirection.UP || direction == getForward() || !(supportsRF())) {
            return 0;
        }
        if (this.energy >= this.maxEnergy) {
            return 0;
        }
        double energyReceived = Math.min(maxEnergy, maxReceive / FoxLibConfig.rfInEu);

        if (!simulate) {
            injectEnergy(direction, energyReceived, 0);
            markForUpdate();
        }
        return energyReceived * FoxLibConfig.rfInEu;
    }

    @Override
    public double getDoubleEnergyStored(ForgeDirection direction) {
        if (!(supportsRF())) {
            return 0;
        }
        return getEnergy() * FoxLibConfig.rfInEu;
    }

    @Override
    public double getMaxDoubleEnergyStored(ForgeDirection direction) {
        if (!(supportsRF())) {
            return 0;
        }
        return getMaxEnergy() * FoxLibConfig.rfInEu;
    }

    protected boolean supportsRF() {
        return false;
    }

    private void updateAdjacentCache() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.blockExists(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)) {
                TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                adjacentInventories[dir.ordinal()] = (te instanceof ISidedInventory) ? (ISidedInventory) te : null;
                adjacentFluidHandlers[dir.ordinal()] = (te instanceof IFluidHandler) ? (IFluidHandler) te : null;
            }
        }
    }

    private void pushFluidIfCan() {
        if (tank.getFluidAmount() <= 0) return;

        for (ForgeDirection side : pushFluidSides) {
            IFluidHandler neighbor = adjacentFluidHandlers[side.ordinal()];

            if (neighbor instanceof IFluidHandler  && !((TileEntity)neighbor).isInvalid()) {
                tryPushFluid(neighbor, side);
            }
        }
    }

    private void tryPushFluid(IFluidHandler neighbor, ForgeDirection side) {
        ForgeDirection sideTo = side.getOpposite();
        FluidStack stackToPush = tank.getFluid().copy();

        if (neighbor.canFill(sideTo, stackToPush.getFluid())) {
            int filled = neighbor.fill(sideTo, stackToPush, true);

            if (filled > 0) {
                tank.drain(filled, true);

                this.markForUpdate();
                if (neighbor instanceof TileEntity tile) {
                    tile.markDirty();
                }
            }
        }
    }

    private void pullIfCan() {
        for (ForgeDirection side : pullSides) {
            ISidedInventory neighbor = adjacentInventories[side.ordinal()];
            if (neighbor == null || ((TileEntity)neighbor).isInvalid()) continue;

            int sideFrom = side.getOpposite().ordinal();

            int[] accessibleSlots = neighbor.getAccessibleSlotsFromSide(sideFrom);

            for (int slot : accessibleSlots) {
                ItemStack stack = neighbor.getStackInSlot(slot);

                if (stack != null && neighbor.canExtractItem(slot, stack, sideFrom)) {
                    if (this.canInsert(inventory, stack)) {
                        this.insert(inventory, stack);

                        neighbor.setInventorySlotContents(slot, null);
                        neighbor.markDirty();
                    }
                }
            }
        }
    }

    private void pushIfCan() {
        for (ForgeDirection side : pushSides) {
            ISidedInventory neighbor = adjacentInventories[side.ordinal()];

            if (neighbor instanceof ISidedInventory && !((TileEntity)neighbor).isInvalid()) {

                for (int i = 0; i < output.getSizeInventory(); i++) {
                    ItemStack stack = output.getStackInSlot(i);
                    if (stack != null) {
                        tryPushStack(stack, i, neighbor, side);
                    }
                }
            }
        }
    }

    private void tryPushStack(ItemStack stackToPush, int mySlot, ISidedInventory neighbor, ForgeDirection side) {
        int sideTo = side.getOpposite().ordinal();

        int[] accessibleSlots = neighbor.getAccessibleSlotsFromSide(sideTo);

        for (int targetSlot : accessibleSlots) {
            if (!neighbor.isItemValidForSlot(targetSlot, stackToPush) ||
                !neighbor.canInsertItem(targetSlot, stackToPush, sideTo)) {
                continue;
            }

            ItemStack stackInTarget = neighbor.getStackInSlot(targetSlot);

            if (stackInTarget == null) {
                neighbor.setInventorySlotContents(targetSlot, stackToPush.copy());
                output.setInventorySlotContents(mySlot, null);
                neighbor.markDirty();
                this.markDirty();
                break;
            } else if (ItemStackUtil.stackEquals(stackInTarget, stackToPush)) {
                int limit = Math.min(neighbor.getInventoryStackLimit(), stackInTarget.getMaxStackSize());
                int spaceLeft = limit - stackInTarget.stackSize;

                if (spaceLeft > 0) {
                    int amountToTransfer = Math.min(stackToPush.stackSize, spaceLeft);
                    stackInTarget.stackSize += amountToTransfer;
                    stackToPush.stackSize -= amountToTransfer;

                    if (stackToPush.stackSize <= 0) {
                        output.setInventorySlotContents(mySlot, null);
                    }

                    neighbor.markDirty();
                    this.markDirty();

                    if (output.getStackInSlot(mySlot) == null) break;
                }
            }
        }
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
