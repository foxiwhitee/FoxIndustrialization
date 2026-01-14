package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.api.IHasActiveState;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IUniversalFluidComplexRecipe;
import foxiwhitee.FoxIndustrialization.recipes.UniversalFluidComplexRecipe;
import foxiwhitee.FoxIndustrialization.utils.InventoryUtils;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradeUtils;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
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

import java.util.*;

public class TileUniversalFluidComplex extends TileIC2Inv implements IUpgradableTile, IFluidHandler, IHasActiveState {
    private final int[] cachedAllSlots;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 3);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 3);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 4);
    private final FluidTank inputTank1;
    private final FluidTank inputTank2;
    private final FluidTank inputTank3;
    private final FluidTank outputTank;

    private int scanTimer, itemsPerOp, ticksNeed = 100, tick, energyPerTickMultiplier = 1;
    private final IInventory[] adjacentInventories = new IInventory[6];
    private final IFluidHandler[] adjacentFluidHandlers = new IFluidHandler[6];
    private ForgeDirection[] pushSides = {}, pushFluidSides = {}, pullSides = {};
    private boolean hasEjector, hasEjectorFluid, hasPuller, active, hasWaterGenerator, hasLavaGenerator, needFluidUpdate;

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

        if (needFluidUpdate) {
            needFluidUpdate = false;
            updateFluids();
        }
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
            if (UpgradeUtils.pushFluidIfCan(outputTank, pushSides, adjacentFluidHandlers)) {
                markForUpdate();
            }
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
            needFluidUpdate = true;
            active = false;
            recipe = null;
        }

        markForUpdate();
    }

    private void updateFluids() {
        if (hasWaterGenerator) {
            this.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, FIConfig.ufcFluidStorage), true);
        }
        if (hasLavaGenerator) {
            this.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.LAVA, FIConfig.ufcFluidStorage), true);
        }
    }

    private boolean canStartCrafting() {
        for (ItemStack stack : recipe.getOutputs()) {
            if (stack == null) {
                continue;
            }
            if (!InventoryUtils.canInsert(output, stack)) {
                return false;
            }
        }

        if (!InventoryUtils.canInsert(outputTank, recipe.getOutputFluid())) {
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
            return outputTank.fill(outFluid, false) >= outFluid.amount;
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
            return InventoryUtils.canInsert(output, outputs.get(0));
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
                    InventoryUtils.insert(output, outputStack.copy());
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
        this.recipe = findCellRecipe(stacks, fluidStacks);
    }

    private UniversalFluidComplexRecipe findCellRecipe(List<ItemStack> items, List<FluidStack> fluids) {
        for (ItemStack stack : items) {
            if (stack == null) continue;

            FluidStack containedFluid = FluidContainerRegistry.getFluidForFilledItem(stack);
            if (containedFluid != null) {
                ItemStack copy = stack.copy();
                copy.stackSize = 1;
                ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(copy);
                return new UniversalFluidComplexRecipe(200, containedFluid, Collections.singletonList(emptyContainer), null, Collections.singletonList(copy));
            }
        }

        for (ItemStack stack : items) {
            if (stack == null) continue;

            for (FluidStack availableFluid : fluids) {
                if (availableFluid == null || availableFluid.amount <= 0) continue;

                ItemStack filledStack = FluidContainerRegistry.fillFluidContainer(availableFluid, stack);

                if (filledStack != null) {
                    ItemStack copy = stack.copy();
                    copy.stackSize = 1;
                    FluidStack resultFluid = FluidContainerRegistry.getFluidForFilledItem(filledStack);
                    return new UniversalFluidComplexRecipe(200, null, Collections.singletonList(filledStack), Collections.singletonList(resultFluid), Collections.singletonList(copy));
                }
            }
        }

        return null;
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
        data.setBoolean("hasWaterGenerator", hasWaterGenerator);
        data.setBoolean("hasLavaGenerator", hasLavaGenerator);
        data.setBoolean("needFluidUpdate", needFluidUpdate);
        data.setInteger("tick", tick);
        data.setInteger("ticksNeed", ticksNeed);
        data.setInteger("energyPerTickMultiplier", energyPerTickMultiplier);
        data.setInteger("itemsPerOp", itemsPerOp);
        data.setBoolean("active", active);

        InventoryUtils.writeTankToNbt(data, "tank1", this.inputTank1);
        InventoryUtils.writeTankToNbt(data, "tank2", this.inputTank2);
        InventoryUtils.writeTankToNbt(data, "tank3", this.inputTank3);
        InventoryUtils.writeTankToNbt(data, "tankOut", this.outputTank);
        UpgradeUtils.writeDirectionsToNbt(data, "pullSides", pullSides);
        UpgradeUtils.writeDirectionsToNbt(data, "pushSides", pushSides);
        UpgradeUtils.writeDirectionsToNbt(data, "pushFluidSides", pushFluidSides);
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
        hasWaterGenerator = data.getBoolean("hasWaterGenerator");
        hasLavaGenerator = data.getBoolean("hasLavaGenerator");
        needFluidUpdate = data.getBoolean("needFluidUpdate");
        tick = data.getInteger("tick");
        ticksNeed = data.getInteger("ticksNeed");
        energyPerTickMultiplier = data.getInteger("energyPerTickMultiplier");
        itemsPerOp = data.getInteger("itemsPerOp");
        active = data.getBoolean("active");
        InventoryUtils.readTankFromNbt(data, "tank1", this.inputTank1);
        InventoryUtils.readTankFromNbt(data, "tank2", this.inputTank2);
        InventoryUtils.readTankFromNbt(data, "tank3", this.inputTank3);
        InventoryUtils.readTankFromNbt(data, "tankOut", this.outputTank);
        pullSides = UpgradeUtils.readDirectionsFromNbt(data, "pullSides");
        pushSides = UpgradeUtils.readDirectionsFromNbt(data, "pushSides");
        pushFluidSides = UpgradeUtils.readDirectionsFromNbt(data, "pushFluidSides");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(this.tick);
        data.writeInt(this.ticksNeed);
        data.writeBoolean(this.active);

        InventoryUtils.writeTankToStream(data, this.inputTank1);
        InventoryUtils.writeTankToStream(data, this.inputTank2);
        InventoryUtils.writeTankToStream(data, this.inputTank3);
        InventoryUtils.writeTankToStream(data, this.outputTank);
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
        boolean tank1Changed = InventoryUtils.readTankFromStream(data, inputTank1);
        boolean tank2Changed = InventoryUtils.readTankFromStream(data, inputTank2);
        boolean tank3Changed = InventoryUtils.readTankFromStream(data, inputTank3);
        boolean tankOutChanged = InventoryUtils.readTankFromStream(data, outputTank);

        return old || tank1Changed || tank2Changed || tank3Changed || tankOutChanged || oldActive != active || oldTicksNeed != ticksNeed || oldTick != tick;
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
            var handler = UpgradeUtils.newHandler(this)
                .storage(FIConfig.ufcStorage)
                .speed(100, 0, FIConfig.ufcItemsPerOp, 64)
                .ejector()
                .ejectorFluid()
                .puller()
                .water()
                .lava()
                .process();

            this.maxEnergy = handler.getStorage();
            this.energyPerTickMultiplier = (int) handler.getEnergyNeed();
            this.itemsPerOp = handler.getOperations();
            this.ticksNeed = handler.getTicksNeed();
            this.hasWaterGenerator = handler.hasWater();
            this.hasLavaGenerator = handler.hasLava();
            this.hasEjector = handler.hasEjector();
            this.hasEjectorFluid = handler.hasEjectorFluid();
            this.hasPuller = handler.hasPuller();
            this.pushSides = handler.getPushSides();
            this.pushFluidSides = handler.getPushFluidSides();
            this.pullSides = handler.getPullSides();

            this.needFluidUpdate = true;
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

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[]{UpgradesTypes.SPEED, UpgradesTypes.STORAGE, UpgradesTypes.EJECTOR, UpgradesTypes.PULLING, UpgradesTypes.FLUID_EJECTOR, UpgradesTypes.WATER_GENERATOR, UpgradesTypes.LAVA_GENERATOR};
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
        if (drained != null && drained.amount > 0) {
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
        needFluidUpdate = true;
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
