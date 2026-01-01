package foxiwhitee.FoxIndustrialization.tile.machines;

import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.event.TickRateModulation;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import ic2.api.Direction;
import ic2.core.upgrade.IUpgradeItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public abstract class TileBaseMachine extends TileIC2Inv {
    private final static ItemStack someItem = new ItemStack(Blocks.command_block, 64);
    private final FoxInternalInventory inventory;
    private final FoxInternalInventory output;
    private final FoxInternalInventory upgrades;
    protected final MachineTier tier;
    protected final IRecipeIC2[] currentRecipes;

    private int[] ticks;
    private int itemsPerOp, ticksNeed;

    private double energyPerTick;
    private final double defaultMaxEnergy, defaultItemsPerOp, defaultEnergyPerTick;

    private ForgeDirection[] pushSides = {}, pullSides = {};
    private final IInventory[] adjacentInventories = new IInventory[6];
    private int scanTimer = 0;
    private boolean hasEjector = false;
    private boolean hasPuller = false;
    private boolean mustHaveRedstoneSignal = false;
    private final int[] cachedAllSlots;

    private boolean toSleepPush = false, toSleepPull = false;

    public TileBaseMachine(MachineTier tier, double maxEnergy, int itemsPerOp, double energyPerTick) {
        this.inventory = new FoxInternalInventory(this, tier.getInvInpSize());
        this.output = new FoxInternalInventory(this, tier.getInvOutSize());
        this.upgrades = new FoxInternalInventory(this, tier.getInvUpgradesSize());
        this.currentRecipes = new IRecipeIC2[tier.getMaxOperations()];
        this.ticks = new int[tier.getMaxOperations()];
        for (int i = 0; i < tier.getMaxOperations(); i++) {
            ticks[i] = 0;
        }
        this.tier = tier;
        this.defaultMaxEnergy = maxEnergy;
        this.maxEnergy = maxEnergy;
        this.itemsPerOp = itemsPerOp;
        this.defaultItemsPerOp = itemsPerOp;
        this.energyPerTick = energyPerTick;
        this.defaultEnergyPerTick = energyPerTick;
        this.ticksNeed = 100;

        this.cachedAllSlots = new int[inventory.getSizeInventory() + output.getSizeInventory()];
        for (int i = 0; i < cachedAllSlots.length; i++) cachedAllSlots[i] = i;
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        if (worldObj.isRemote) return;
        super.tick();

        if (scanTimer-- <= 0) {
            updateAdjacentCache();
            scanTimer = 20;
        }

        if (canWork()) {
            for (int i = 0; i < ticks.length; i++) {
                if (currentRecipes[i] == null) {
                    getRecipe(inventory.getStackInSlot(i), i);
                }

                IRecipeIC2 recipe = currentRecipes[i];
                if (recipe == null) continue;

                if (energy < energyPerTick) continue;
                if (!canInsert(output, recipe.getOut(), i)) continue;

                ticks[i]++;
                energy -= energyPerTick;

                if (ticks[i] < ticksNeed) {
                    markForUpdate();
                    continue;
                }

                ticks[i] = 0;

                for (int j = 0; j < itemsPerOp; j++) {

                    ItemStack in = inventory.getStackInSlot(i);
                    if (in == null) break;

                    int need = getCountOfInput(recipe.getInput());
                    if (in.stackSize < need) break;

                    if (!canInsert(output, recipe.getOut(), i)) break;

                    in.stackSize -= need;
                    if (in.stackSize == 0) {
                        inventory.setInventorySlotContents(i, null);
                    }

                    insert(output, recipe.getOut().copy(), i);
                }

                currentRecipes[i] = null;

                markForUpdate();
            }
        }
    }

    @TileEvent(TileEventType.TICK_SPEED)
    public TickRateModulation tickPull() {
        if (toSleepPull) {
            toSleepPull = false;
            return TickRateModulation.SLEEP;
        }
        if (hasPuller && pullIfCan()) {
            return TickRateModulation.FASTER;
        }

        return TickRateModulation.IDLE;
    }

    @TileEvent(TileEventType.TICK_SPEED)
    public TickRateModulation tickPush() {
        if (toSleepPush) {
            toSleepPush = false;
            return TickRateModulation.SLEEP;
        }
        if (hasEjector && pushIfCan()) {
            return TickRateModulation.FASTER;
        }

        return TickRateModulation.IDLE;
    }

    protected void getRecipe(ItemStack stack, int idx) {
        if (stack == null) {
            currentRecipes[idx] = null;
            return;
        }
        stack = stack.copy();
        for (IRecipeIC2 recipe : getRecipes()) {
            if (ItemStackUtil.matchesStackAndOther(stack, recipe.getInput()) && stack.stackSize >= getCountOfInput(recipe.getInput())) {
                currentRecipes[idx] = recipe;
                return;
            }
        }
        currentRecipes[idx] = null;
    }

    private int getCountOfInput(Object o) {
        if (o instanceof ItemStack stack) {
            return stack.stackSize;
        } else if (o instanceof StackOreDict ore) {
            return ore.getCount();

        }
        return 1;
    }

    private void updateAdjacentCache() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.blockExists(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)) {
                TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                adjacentInventories[dir.ordinal()] = (te instanceof IInventory) ? (IInventory) te : null;
            }
        }
    }

    private boolean pullIfCan() {
        if (!canInsert(inventory, someItem)) return false;

        for (ForgeDirection side : pullSides) {
            IInventory neighbor = adjacentInventories[side.ordinal()];
            if (neighbor == null || ((TileEntity)neighbor).isInvalid()) continue;

            for (int slot = 0; slot < neighbor.getSizeInventory(); slot++) {
                ItemStack stackInSlot = neighbor.getStackInSlot(slot);
                if (stackInSlot != null) {
                    if (this.canInsert(inventory, stackInSlot)) {
                        this.insert(inventory, stackInSlot);

                        neighbor.setInventorySlotContents(slot, null);
                        neighbor.markDirty();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pushIfCan() {
        for (ForgeDirection side : pushSides) {
            IInventory neighbor = adjacentInventories[side.ordinal()];
            if (neighbor == null || ((TileEntity)neighbor).isInvalid()) continue;

            for (int i = 0; i < output.getSizeInventory(); i++) {
                ItemStack stack = output.getStackInSlot(i);
                if (stack != null) {
                    return tryPushStack(stack, i, neighbor);
                }
            }
        }
        return false;
    }

    private boolean tryPushStack(ItemStack stackToPush, int mySlot, IInventory neighbor) {
        boolean changed = false;
        for (int targetSlot = 0; targetSlot < neighbor.getSizeInventory(); targetSlot++) {
            if (!neighbor.isItemValidForSlot(targetSlot, stackToPush)) continue;

            ItemStack stackInTarget = neighbor.getStackInSlot(targetSlot);

            if (stackInTarget == null) {
                neighbor.setInventorySlotContents(targetSlot, stackToPush.copy());
                output.setInventorySlotContents(mySlot, null);
                changed = true;
                neighbor.markDirty();
                this.markDirty();
                break;
            } else if (ItemStackUtil.stackEquals(stackInTarget, stackToPush)) {
                int spaceLeft = Math.min(neighbor.getInventoryStackLimit(), stackInTarget.getMaxStackSize()) - stackInTarget.stackSize;
                if (spaceLeft > 0) {
                    int amountToTransfer = Math.min(stackToPush.stackSize, spaceLeft);
                    stackInTarget.stackSize += amountToTransfer;
                    stackToPush.stackSize -= amountToTransfer;

                    if (stackToPush.stackSize <= 0) output.setInventorySlotContents(mySlot, null);

                    changed = true;
                    neighbor.markDirty();
                    this.markDirty();
                    if (output.getStackInSlot(mySlot) == null) break;
                }
            }
        }
        return changed;
    }

    protected boolean canInsert(FoxInternalInventory inv, ItemStack stack, int idx) {
        ItemStack inSlot = inv.getStackInSlot(idx);
        if (inSlot == null) return true;
        return ItemStackUtil.stackEquals(stack, inSlot) && (inSlot.getMaxStackSize() - inSlot.stackSize) >= stack.stackSize;
    }

    protected boolean canInsert(FoxInternalInventory inv, ItemStack stack) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (canInsert(inv, stack, i)) return true;
        }
        return false;
    }

    protected boolean insert(FoxInternalInventory inv, ItemStack stack) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            boolean b = insert(inv, stack, i);
            if (b) return true;
        }
        return false;
    }

    protected boolean insert(FoxInternalInventory inv, ItemStack stack, int idx) {
        ItemStack inSlot = inv.getStackInSlot(idx);
        stack = stack.copy();
        if (inSlot == null) {
            inv.setInventorySlotContents(idx, stack);
            return true;
        }
        if (ItemStackUtil.stackEquals(inSlot, stack)) {
            int transfer = Math.min(stack.stackSize, inSlot.getMaxStackSize() - inSlot.stackSize);
            inSlot.stackSize += transfer;
            stack.stackSize -= transfer;
            return stack.stackSize <= 0;
        }
        return false;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        upgrades.writeToNBT(data, "upgrades");
        data.setIntArray("ticks", ticks);
        data.setInteger("ticksNeed", ticksNeed);
        data.setDouble("energyPerTick", energyPerTick);
        data.setInteger("itemsPerOp", itemsPerOp);
        int[] pullSidesNbt = new int[pullSides.length];
        for (int i = 0; i < pullSidesNbt.length; i++) {
            pullSidesNbt[i] = pullSides[i].ordinal();
        }
        data.setIntArray("pullSides", pullSidesNbt);
        int[] pushSidesNbt = new int[pushSides.length];
        for (int i = 0; i < pullSidesNbt.length; i++) {
            pushSidesNbt[i] = pushSides[i].ordinal();
        }
        data.setIntArray("pullSides", pushSidesNbt);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        output.readFromNBT(data, "output");
        upgrades.readFromNBT(data, "upgrades");
        ticks = data.getIntArray("ticks");
        ticksNeed = data.getInteger("ticksNeed");
        energyPerTick = data.getDouble("energyPerTick");
        itemsPerOp = data.getInteger("itemsPerOp");
        int[] pullSidesNbt = data.getIntArray("pullSides");
        pullSides = new ForgeDirection[pullSidesNbt.length];
        for (int i = 0; i < pullSidesNbt.length; i++) {
            pullSides[i] = ForgeDirection.getOrientation(pullSidesNbt[i]);
        }
        int[] pushSidesNbt = data.getIntArray("pushSides");
        pushSides = new ForgeDirection[pushSidesNbt.length];
        for (int i = 0; i < pushSidesNbt.length; i++) {
            pushSides[i] = ForgeDirection.getOrientation(pushSidesNbt[i]);
        }
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        for (int tick : ticks) {
            data.writeInt(tick);
        }
        data.writeInt(ticksNeed);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        int[] oldTicks = Arrays.copyOf(ticks, ticks.length);
        int oldNeedTicks = ticksNeed;
        for (int i = 0; i < ticks.length; i++) {
            ticks[i] = data.readInt();
        }
        ticksNeed = data.readInt();
        return old || oldNeedTicks != ticksNeed || !Arrays.equals(oldTicks, ticks);
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory() + output.getSizeInventory();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot < inventory.getSizeInventory();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot >= inventory.getSizeInventory();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < inventory.getSizeInventory()) {
            return inventory.decrStackSize(slot, amount);
        }
        return output.decrStackSize(slot - inventory.getSizeInventory(), amount);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < inventory.getSizeInventory()) {
            inventory.setInventorySlotContents(slot, stack);
        } else {
            output.setInventorySlotContents(slot - inventory.getSizeInventory(), stack);
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < inventory.getSizeInventory())
            return inventory.getStackInSlot(slot);

        return output.getStackInSlot(slot - inventory.getSizeInventory());
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection side) {
        return cachedAllSlots;
    }

    protected boolean canWork() {
        if (!mustHaveRedstoneSignal) return true;
        return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
    }

    @Override
    public void onChangeInventory(IInventory iInv, int slot, InvOperation op, ItemStack removed, ItemStack added) {
        if (iInv == upgrades) {
            this.maxEnergy = defaultMaxEnergy;
            this.itemsPerOp = 0;
            this.energyPerTick = defaultEnergyPerTick;
            this.mustHaveRedstoneSignal = false;
            this.ticksNeed = 100;
            this.toSleepPull = true;
            this.toSleepPush = true;

            List<Integer> newPushRaw = new ArrayList<>();
            List<Integer> newPullRaw = new ArrayList<>();

            List<UpgradesTypes> available = Arrays.asList(getAvailableTypes());
            boolean canEject = available.contains(UpgradesTypes.EJECTOR);
            boolean canPull = available.contains(UpgradesTypes.PULLING);
            boolean canRS = available.contains(UpgradesTypes.REDSTONE);

            for (int j = 0; j < upgrades.getSizeInventory(); j++) {
                ItemStack stack = upgrades.getStackInSlot(j);
                if (stack == null) continue;

                if (stack.getItem() instanceof IAdvancedUpgradeItem upgrade) {
                    this.ticksNeed *= upgrade.getSpeedMultiplier(stack);
                    this.itemsPerOp += upgrade.getItemsPerOpAdd(stack);
                    this.maxEnergy *= upgrade.getStorageEnergyMultiplier(stack);
                    this.energyPerTick *= upgrade.getEnergyUseMultiplier(stack);
                } else if (stack.getItem() instanceof IUpgradeItem) {
                    switch (stack.getItemDamage()) {
                        case 0: // Speed
                            this.ticksNeed *= Math.pow(0.7, stack.stackSize);
                            this.energyPerTick *= Math.pow(1.6, stack.stackSize);
                            break;
                        case 2: // Energy Storage
                            this.maxEnergy += 10000 * stack.stackSize;
                            break;
                        case 3: // Ejector
                            if (canEject) {
                                NBTTagCompound nbt = stack.getTagCompound();
                                newPushRaw.add((nbt != null && nbt.hasKey("dir")) ? (int)nbt.getByte("dir") : 0);
                            }
                            break;
                        case 6: // Pulling
                            if (canPull) {
                                NBTTagCompound nbt = stack.getTagCompound();
                                newPullRaw.add((nbt != null && nbt.hasKey("dir")) ? (int)nbt.getByte("dir") : 0);
                            }
                            break;
                        case 5: // Redstone
                            if (canRS) mustHaveRedstoneSignal = true;
                            break;
                    }
                }
            }

            this.pushSides = parseSides(newPushRaw);
            this.pullSides = parseSides(newPullRaw);
            this.hasEjector = pushSides.length > 0;
            this.hasPuller = pullSides.length > 0;

            if (this.ticksNeed < 1) {
                this.ticksNeed = 1;
            }
            this.itemsPerOp = (int) Math.max(this.itemsPerOp, defaultItemsPerOp);
            this.itemsPerOp = (int) Math.min(this.itemsPerOp, 64);
            this.maxEnergy = Math.max(this.maxEnergy, this.energyPerTick);
            if (this.maxEnergy < 0) this.maxEnergy = Double.MAX_VALUE;
            this.energy = Math.min(this.maxEnergy, energy);

            if (toSleepPull && hasPuller) {
                toSleepPull = false;
                awakeTickableFunction("tickPull");
            }
            if (toSleepPush && hasEjector) {
                toSleepPush = false;
                awakeTickableFunction("tickPush");
            }

            markForUpdate();
        } else if (iInv == inventory) {
            boolean needUpdate = false;
            for (int i = 0; i < currentRecipes.length; i++) {
                IRecipeIC2 old = currentRecipes[i];
                if (old != null) {
                    getRecipe(inventory.getStackInSlot(i), i);
                    if (currentRecipes[i] == null || !ItemStackUtil.matches(currentRecipes[i].getInput(), old.getInput())) {
                        ticks[i] = 0;
                        needUpdate = true;
                    }
                }
            }
            if (needUpdate) {
                markForUpdate();
            }
        }
    }

    private ForgeDirection[] parseSides(List<Integer> raw) {
        if (raw.isEmpty()) return ForgeDirection.VALID_DIRECTIONS;
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
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack stack = upgrades.getStackInSlot(i);
            if (stack != null) {
                drops.add(stack);
            }
        }
    }

    public double getTicksNeed() {
        return ticksNeed;
    }

    public int[] getTicks() {
        return ticks;
    }

    protected abstract UpgradesTypes[] getAvailableTypes();

    protected abstract List<? extends IRecipeIC2> getRecipes();
}
