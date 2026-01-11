package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.api.IHasActiveState;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.recipes.IUniversalFluidComplexRecipe;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import ic2.core.upgrade.IUpgradeItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.*;

public class TileUniversalFluidComplex extends TileIC2Inv implements IUpgradableTile, IFluidHandler, IHasActiveState {
    private final static ItemStack someItem = new ItemStack(Blocks.command_block, 64);
    private final int[] cachedAllSlots;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 3);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 3);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 4);
    private final FluidTank inputTank1;
    private final FluidTank inputTank2;
    private final FluidTank inputTank3;
    private final FluidTank outputTank;

    private int scanTimer, itemsPerOp, ticksNeed = 100, tick, energyPerTickMultiplier = 1;
    private final ISidedInventory[] adjacentInventories = new ISidedInventory[6];
    private final IFluidHandler[] adjacentFluidHandlers = new IFluidHandler[6];
    private ForgeDirection[] pushSides = {}, pushFluidSides = {}, pullSides = {};
    private boolean hasEjector, hasEjectorFluid, hasPuller, active;

    private IUniversalFluidComplexRecipe recipe;

    public TileUniversalFluidComplex() {
        this.maxEnergy = FIConfig.ufcStorage;
        this.itemsPerOp = FIConfig.ufcItemsPerOp;
        this.inputTank1 = new FluidTank(FIConfig.ufcFluidStorage);
        this.inputTank2 = new FluidTank(FIConfig.ufcFluidStorage);
        this.inputTank3 = new FluidTank(FIConfig.ufcFluidStorage);
        this.outputTank = new FluidTank(FIConfig.ufcFluidStorage);

        this.cachedAllSlots = new int[inventory.getSizeInventory() + output.getSizeInventory()];
        for (int i = 0; i < cachedAllSlots.length; i++) cachedAllSlots[i] = i;
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (worldObj.isRemote) {
            return;
        }

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

        if (recipe == null) {
            getRecipe();
        }

        if (recipe == null) {
            if (active) {
                active = false;
                markForUpdate();
            }
            return;
        }

        double energyNeedPerTick = (recipe.getEnergyNeed() / ticksNeed) * energyPerTickMultiplier;

        if (energy < energyNeedPerTick) {
            if (active) {
                active = false;
                markForUpdate();
            }
            return;
        }

        if (tick == 0) {
            if (!canStartCrafting()) {
                if (active) {
                    active = false;
                    markForUpdate();
                }
                return;
            }
        }

        energy -= energyNeedPerTick;
        tick++;
        active = true;

        if (tick >= ticksNeed) {
            tick = 0;
            for (int i = 0; i < itemsPerOp; i++) {
                if (!doCraft()) {
                    break;
                }
            }
            active = false;
            recipe = null;
        }

        markForUpdate();
    }

    private boolean canStartCrafting() {
        for (ItemStack stack : recipe.getOutputs()) {
            if (stack == null) {
                continue;
            }
            if (!canInsert(output, stack)) {
                return false;
            };
        }

        if (!canInsert(outputTank, recipe.getOutputFluid())) {
            return false;
        }

        return canCraft();
    }

    public boolean canCraft() {
        if (this.energy < recipe.getEnergyNeed()) return false;

        if (recipe.getInputs() != null) {
            for (ItemStack input : recipe.getInputs()) {
                if (input != null && !hasInputItem(input)) return false;
            }
        }

        if (recipe.getInputsFluid() != null) {
            for (FluidStack inputFluid : recipe.getInputsFluid()) {
                if (inputFluid != null && !hasInputFluid(inputFluid)) return false;
            }
        }

        if (recipe.getOutputs() != null) {
            if (!canFitOutputs(recipe.getOutputs())) return false;
        }

        FluidStack outFluid = recipe.getOutputFluid();
        if (outFluid != null) {
            if (outputTank.fill(outFluid, false) < outFluid.amount) return false;
        }

        return true;
    }

    private boolean hasInputItem(ItemStack needed) {
        int count = 0;
        int size = inventory.getSizeInventory();
        for (int i = 0; i < size; i++) {
            ItemStack inSlot = inventory.getStackInSlot(i);
            if (inSlot != null && ItemStackUtil.stackEquals(inSlot, needed)) {
                count += inSlot.stackSize;
                if (count >= needed.stackSize) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasInputFluid(FluidStack needed) {
        int count = 0;
        for (FluidTank tank : new FluidTank[] {inputTank1, inputTank2, inputTank3}) {
            FluidStack inTank = tank.getFluid();
            if (inTank != null && inTank.isFluidEqual(needed)) {
                count += inTank.amount;
                if (count >= needed.amount) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFitOutputs(List<ItemStack> outputs) {
        if (outputs.size() == 1) {
            return canInsert(output, outputs.get(0));
        }

        int[] extraSize = new int[output.getSizeInventory()];

        for (ItemStack toFit : outputs) {
            if (toFit == null) continue;
            boolean fitted = false;

            for (int i = 0; i < output.getSizeInventory(); i++) {
                ItemStack inSlot = output.getStackInSlot(i);
                int currentSize = (inSlot == null ? 0 : inSlot.stackSize) + extraSize[i];
                int maxSize = (inSlot == null ? toFit.getMaxStackSize() : inSlot.getMaxStackSize());

                if (inSlot == null || ItemStackUtil.stackEquals(inSlot, toFit)) {
                    if (maxSize - currentSize >= toFit.stackSize) {
                        extraSize[i] += toFit.stackSize;
                        fitted = true;
                        break;
                    }
                }
            }
            if (!fitted) return false;
        }
        return true;
    }

    public boolean doCraft() {
        if (recipe == null || !canCraft()) return false;
        craftRecipe();
        return true;
    }

    public void craftRecipe() {
        IUniversalFluidComplexRecipe recipe = this.recipe;

        if (recipe.getInputs() != null) {
            for (ItemStack inputIngredient : recipe.getInputs()) {
                if (inputIngredient != null) consumeInputItem(inputIngredient);
            }
        }

        if (recipe.getInputsFluid() != null) {
            for (FluidStack inputFluid : recipe.getInputsFluid()) {
                if (inputFluid != null) consumeInputFluid(inputFluid);
            }
        }

        if (recipe.getOutputs() != null) {
            for (ItemStack outputStack : recipe.getOutputs()) {
                if (outputStack != null) {
                    insert(output, outputStack.copy());
                }
            }
        }
        FluidStack outputFluid = recipe.getOutputFluid();
        if (outputFluid != null) {
            this.outputTank.fill(outputFluid.copy(), true);
        }
    }

    private void consumeInputFluid(FluidStack ingredient) {
        int amountNeeded = ingredient.amount;

        for (FluidTank tank : new FluidTank[] {inputTank1, inputTank2, inputTank3}) {
            FluidStack inTank = tank.getFluid();

            if (inTank != null && inTank.isFluidEqual(ingredient)) {
                FluidStack drained = tank.drain(amountNeeded, true);
                if (drained != null) {
                    amountNeeded -= drained.amount;
                }

                if (amountNeeded <= 0) return;
            }
        }
    }

    private void consumeInputItem(ItemStack ingredient) {
        int amountNeeded = ingredient.stackSize;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slotStack = inventory.getStackInSlot(i);

            if (slotStack != null && ItemStackUtil.stackEquals(slotStack, ingredient)) {
                int toTake = Math.min(slotStack.stackSize, amountNeeded);
                inventory.decrStackSize(i, toTake);
                amountNeeded -= toTake;

                if (amountNeeded <= 0) return;
            }
        }
    }

    private List<ItemStack> getAllStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    private List<FluidStack> getAllFluids() {
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (FluidTank tank : new FluidTank[]{inputTank1, inputTank2, inputTank2}) {
            if (tank != null) {
                FluidStack tankFluid = tank.getFluid();
                if (tankFluid != null) {
                    fluidStacks.add(tankFluid);
                }
            }
        }
        return fluidStacks;
    }

    private void getRecipe() {
        List<ItemStack> stacks = getAllStacks();
        List<FluidStack> fluidStacks = getAllFluids();
        for (var recipe : ModRecipes.universalFluidComplexRecipes) {
            if (recipe.matches(stacks, fluidStacks)) {
                this.recipe = recipe;
                return;
            }
        }
        this.recipe = null;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        upgrades.writeToNBT(data, "upgrades");
        data.setBoolean("hasPuller", hasPuller);
        data.setBoolean("hasEjector", hasEjector);
        data.setBoolean("hasEjectorFluid", hasEjectorFluid);
        data.setInteger("tick", tick);
        data.setInteger("ticksNeed", ticksNeed);
        data.setInteger("energyPerTickMultiplier", energyPerTickMultiplier);
        data.setInteger("itemsPerOp", itemsPerOp);
        data.setBoolean("active", active);
        inputTank1.writeToNBT(data.getCompoundTag("tank1"));
        inputTank2.writeToNBT(data.getCompoundTag("tank2"));
        inputTank3.writeToNBT(data.getCompoundTag("tank3"));
        outputTank.writeToNBT(data.getCompoundTag("tankOut"));
        if (pullSides.length > 0) {
            int[] pullSidesNbt = new int[pullSides.length];
            for (int i = 0; i < pullSidesNbt.length; i++) {
                if (pullSides[i] == null) {
                    pullSidesNbt[i] = -1;
                } else {
                    pullSidesNbt[i] = pullSides[i].ordinal();
                }
            }
            data.setIntArray("pullSides", pullSidesNbt);
        }
        if (pushSides.length > 0) {
            int[] pushSidesNbt = new int[pushSides.length];
            for (int i = 0; i < pushSides.length; i++) {
                if (pullSides[i] == null) {
                    pushSidesNbt[i] = -1;
                } else {
                    pushSidesNbt[i] = pushSides[i].ordinal();
                }
            }
            data.setIntArray("pushSides", pushSidesNbt);
        }
        if (pushFluidSides.length > 0) {
            int[] pushFluidSidesNbt = new int[pushFluidSides.length];
            for (int i = 0; i < pushFluidSides.length; i++) {
                if (pullSides[i] == null) {
                    pushFluidSidesNbt[i] = -1;
                } else {
                    pushFluidSidesNbt[i] = pushFluidSides[i].ordinal();
                }
            }
            data.setIntArray("pushFluidSides", pushFluidSidesNbt);
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        output.readFromNBT(data, "output");
        upgrades.readFromNBT(data, "upgrades");
        hasPuller = data.getBoolean("hasPuller");
        hasEjector = data.getBoolean("hasEjector");
        hasEjectorFluid = data.getBoolean("hasEjectorFluid");
        tick = data.getInteger("tick");
        ticksNeed = data.getInteger("ticksNeed");
        energyPerTickMultiplier = data.getInteger("energyPerTickMultiplier");
        itemsPerOp = data.getInteger("itemsPerOp");
        active = data.getBoolean("active");
        inputTank1.readFromNBT(data.getCompoundTag("tank1"));
        inputTank2.readFromNBT(data.getCompoundTag("tank2"));
        inputTank3.readFromNBT(data.getCompoundTag("tank3"));
        outputTank.readFromNBT(data.getCompoundTag("tankOut"));
        if (data.hasKey("pullSides")) {
            int[] pullSidesNbt = data.getIntArray("pullSides");
            pullSides = new ForgeDirection[pullSidesNbt.length];
            for (int i = 0; i < pullSidesNbt.length; i++) {
                if (pullSidesNbt[i] == -1) {
                    pullSides[i] = null;
                } else {
                    pullSides[i] = ForgeDirection.getOrientation(pullSidesNbt[i]);
                }
            }
        }
        if (data.hasKey("pushSides")) {
            int[] pushSidesNbt = data.getIntArray("pushSides");
            pushSides = new ForgeDirection[pushSidesNbt.length];
            for (int i = 0; i < pushSidesNbt.length; i++) {
                if (pushSidesNbt[i] == -1) {
                    pushSides[i] = null;
                } else {
                    pushSides[i] = ForgeDirection.getOrientation(pushSidesNbt[i]);
                }
            }
        }
        if (data.hasKey("pushFluidSides")) {
            int[] pushFluidSidesNbt = data.getIntArray("pushFluidSides");
            pushFluidSides = new ForgeDirection[pushFluidSidesNbt.length];
            for (int i = 0; i < pushFluidSidesNbt.length; i++) {
                if (pushFluidSidesNbt[i] == -1) {
                    pushFluidSides[i] = null;
                } else {
                    pushFluidSides[i] = ForgeDirection.getOrientation(pushFluidSidesNbt[i]);
                }
            }
        }
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(this.tick);
        data.writeInt(this.ticksNeed);
        data.writeBoolean(this.active);

        FluidTank[] allTanks = {inputTank1, inputTank2, inputTank3, outputTank};
        for (FluidTank tank : allTanks) {
            FluidStack fs = tank.getFluid();
            if (fs != null) {
                data.writeBoolean(true);
                data.writeInt(FluidRegistry.getFluidID(fs.getFluid()));
                data.writeInt(fs.amount);
            } else {
                data.writeBoolean(false);
            }
        }
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean oldActive = active;
        int oldTick = tick;
        int oldTicksNeed = ticksNeed;
        tick = data.readInt();
        ticksNeed = data.readInt();
        active = data.readBoolean();
        boolean tanksChanged = false;

        FluidTank[] allTanks = {inputTank1, inputTank2, inputTank3, outputTank};

        for (FluidTank tank : allTanks) {
            boolean hasFluid = data.readBoolean();
            if (hasFluid) {
                int fluidID = data.readInt();
                int amount = data.readInt();
                Fluid fluid = FluidRegistry.getFluid(fluidID);

                if (tank.getFluid() == null ||
                    tank.getFluid().getFluid() != fluid ||
                    tank.getFluid().amount != amount) {

                    tank.setFluid(new FluidStack(fluid, amount));
                    tanksChanged = true;
                }
            } else {
                if (tank.getFluid() != null) {
                    tank.setFluid(null);
                    tanksChanged = true;
                }
            }
        }

        return old || tanksChanged || oldActive != active || oldTicksNeed != ticksNeed || oldTick != tick;
    }

    public int getTick() {
        return tick;
    }

    public int getTicksNeed() {
        return ticksNeed;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
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

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            this.maxEnergy = FIConfig.ufcStorage;
            this.itemsPerOp = FIConfig.ufcItemsPerOp;
            this.energyPerTickMultiplier = 0;
            this.ticksNeed = 100;

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

                if (stack.getItem() instanceof IAdvancedUpgradeItem upgrade) {
                    this.ticksNeed *= (int) upgrade.getSpeedMultiplier(stack);
                    this.itemsPerOp += upgrade.getItemsPerOpAdd(stack);
                    this.maxEnergy = safeMultiply(maxEnergy, upgrade.getStorageEnergyMultiplier(stack));
                    this.energyPerTickMultiplier += upgrade.getEnergyUseMultiplier(stack);
                } else if (stack.getItem() instanceof IUpgradeItem) {
                    switch (stack.getItemDamage()) {
                        case 0: // Speed
                            this.ticksNeed *= (int) Math.pow(0.7, stack.stackSize);
                            this.energyPerTickMultiplier += Math.pow(1.6, stack.stackSize);
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
            this.energyPerTickMultiplier = Math.max(1, this.energyPerTickMultiplier);
            this.pushSides = parseSides(newPushRaw);
            this.pushFluidSides = parseSides(newPushFluidRaw);
            this.pullSides = parseSides(newPullRaw);
            this.hasEjector = pushSides.length > 0;
            this.hasEjectorFluid = pushFluidSides.length > 0;
            this.hasPuller = pullSides.length > 0;

            if (this.ticksNeed < 1) {
                this.ticksNeed = 1;
            }
            this.itemsPerOp = Math.min(this.itemsPerOp, 64);
            if (this.maxEnergy < 0) this.maxEnergy = Double.MAX_VALUE;
            this.energy = Math.min(this.maxEnergy, energy);

            markForUpdate();
        } else if (iInventory == inventory) {
            checkResetRecipe();
        }
    }

    private void checkResetRecipe() {
        boolean needUpdate = false;
        IUniversalFluidComplexRecipe old = recipe;
        if (old != null) {
            getRecipe();
            if (recipe == null || !old.matches(recipe.getInputs(), recipe.getInputsFluid())) {
                tick = 0;
                needUpdate = true;
            }
        }
        if (needUpdate) {
            markForUpdate();
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
        if (outputTank.getFluidAmount() <= 0) return;

        for (ForgeDirection side : pushFluidSides) {
            IFluidHandler neighbor = adjacentFluidHandlers[side.ordinal()];

            if (neighbor instanceof IFluidHandler  && !((TileEntity)neighbor).isInvalid()) {
                tryPushFluid(neighbor, side);
            }
        }
    }

    private void tryPushFluid(IFluidHandler neighbor, ForgeDirection side) {
        ForgeDirection sideTo = side.getOpposite();
        FluidStack stackToPush = outputTank.getFluid().copy();

        if (neighbor.canFill(sideTo, stackToPush.getFluid())) {
            int filled = neighbor.fill(sideTo, stackToPush, true);

            if (filled > 0) {
                outputTank.drain(filled, true);

                this.markForUpdate();
                if (neighbor instanceof TileEntity tile) {
                    tile.markDirty();
                }
            }
        }
    }

    private void pullIfCan() {
        for (ForgeDirection side : pullSides) {
            IInventory neighbor = adjacentInventories[side.ordinal()];
            if (neighbor == null || ((TileEntity)neighbor).isInvalid()) continue;

            int sideFrom = side.getOpposite().ordinal();

            if (neighbor instanceof ISidedInventory sided) {
                int[] accessibleSlots = sided.getAccessibleSlotsFromSide(sideFrom);

                for (int slot : accessibleSlots) {
                    ItemStack stack = sided.getStackInSlot(slot);

                    if (stack != null && sided.canExtractItem(slot, stack, sideFrom)) {
                        if (this.canInsert(inventory, stack)) {
                            this.insert(inventory, stack);

                            sided.setInventorySlotContents(slot, null);
                            sided.markDirty();
                        }
                    }
                }
            }
        }
    }

    private void pushIfCan() {
        for (ForgeDirection side : pushSides) {
            ISidedInventory neighbor = adjacentInventories[side.ordinal()];

            if (neighbor instanceof ISidedInventory && !((TileEntity)neighbor).isInvalid()) {
                ISidedInventory sidedNeighbor = neighbor;

                for (int i = 0; i < output.getSizeInventory(); i++) {
                    ItemStack stack = output.getStackInSlot(i);
                    if (stack != null) {
                        tryPushStack(stack, i, sidedNeighbor, side);
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

    protected boolean canInsert(FluidTank tank, FluidStack stack) {
        if (stack == null || stack.getFluid() == null) return true;

        FluidStack inTank = tank.getFluid();

        if (inTank == null) {
            return tank.getCapacity() >= stack.amount;
        }

        return inTank.isFluidEqual(stack) && (tank.getCapacity() - inTank.amount) >= stack.amount;
    }

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return UpgradesTypes.values();
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.QUANTUM;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null) return 0;
        FluidTank[] inputTanks = {inputTank1, inputTank2, inputTank3};

        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() != null && tank.getFluid().isFluidEqual(resource)) {
                int filled = tank.fill(resource, doFill);
                if (filled > 0) {
                    markForUpdate();
                }
                return filled;
            }
        }

        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() == null) {
                int filled = tank.fill(resource, doFill);
                if (filled > 0) {
                    markForUpdate();
                }
                return filled;
            }
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || outputTank.getFluid() == null) return null;

        if (outputTank.getFluid().isFluidEqual(resource)) {
            FluidStack drained = outputTank.drain(resource.amount, doDrain);
            if (drained.amount > 0) {
                markForUpdate();
            }
            return drained;
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack drained = outputTank.drain(maxDrain, doDrain);
        if (drained.amount > 0) {
            markForUpdate();
        }
        return drained;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        FluidTank[] inputTanks = {inputTank1, inputTank2, inputTank3};

        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() == null) return true;
            if (tank.getFluid().getFluid() == fluid) return true;
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return outputTank.getFluid() != null && outputTank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] info = new FluidTankInfo[4];

        info[0] = inputTank1.getInfo();
        info[1] = inputTank2.getInfo();
        info[2] = inputTank3.getInfo();

        info[3] = outputTank.getInfo();
        return info;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public FluidTank getInputTank1() {
        return inputTank1;
    }

    public FluidTank getInputTank2() {
        return inputTank2;
    }

    public FluidTank getInputTank3() {
        return inputTank3;
    }

    public FluidTank getOutputTank() {
        return outputTank;
    }

    public void clearTank(int id) {
        switch (id) {
            case 0: clearTank(this.outputTank); break;
            case 1: clearTank(this.inputTank1); break;
            case 2: clearTank(this.inputTank2); break;
            case 3: clearTank(this.inputTank3); break;
        }
    }

    private void clearTank(FluidTank tank) {
        if (tank != null && tank.getFluid() != null) {
            tank.setFluid(null);
            checkResetRecipe();
            markForUpdate();
        }
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
}
