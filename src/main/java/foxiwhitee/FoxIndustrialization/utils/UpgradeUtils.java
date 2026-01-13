package foxiwhitee.FoxIndustrialization.utils;

import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.items.ItemFluidGeneratorUpgrade;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import ic2.core.upgrade.IUpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.*;

public class UpgradeUtils {
    public static void updateAdjacentCache(TileEntity tile, IInventory[] adjacentInventories) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (tile.getWorldObj().blockExists(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ)) {
                TileEntity te = tile.getWorldObj().getTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
                adjacentInventories[dir.ordinal()] = (te instanceof IInventory) ? (IInventory) te : null;
            }
        }
    }

    public static void updateAdjacentCache(TileEntity tile, IInventory[] adjacentInventories, IFluidHandler[] adjacentFluidHandlers) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (tile.getWorldObj().blockExists(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ)) {
                TileEntity te = tile.getWorldObj().getTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
                adjacentInventories[dir.ordinal()] = (te instanceof IInventory) ? (IInventory) te : null;
                adjacentFluidHandlers[dir.ordinal()] = (te instanceof IFluidHandler) ? (IFluidHandler) te : null;
            }
        }
    }

    public static void pullIfCan(ForgeDirection[] pullSides, IInventory[] adjacentInventories, FoxInternalInventory inventory) {
        for (ForgeDirection side : pullSides) {
            IInventory neighbor = adjacentInventories[side.ordinal()];
            if (neighbor == null || ((TileEntity)neighbor).isInvalid()) continue;

            int sideFrom = side.getOpposite().ordinal();


            for (int slot = 0; slot < neighbor.getSizeInventory(); slot++) {
                ItemStack stack = neighbor.getStackInSlot(slot);
                if (neighbor instanceof ISidedInventory sided) {
                    int finalSlot = slot;
                    if (Arrays.stream(sided.getAccessibleSlotsFromSide(sideFrom)).noneMatch(x -> x == finalSlot)) {
                        continue;
                    } else if (!sided.canExtractItem(slot, stack, sideFrom)) {
                        continue;
                    }
                }

                if (stack != null) {
                    if (InventoryUtils.canInsert(inventory, stack)) {
                        InventoryUtils.insert(inventory, stack);

                        neighbor.setInventorySlotContents(slot, null);
                        neighbor.markDirty();
                    }
                }
            }
        }
    }

    public static boolean pushIfCan(ForgeDirection[] pushSides, IInventory[] adjacentInventories, FoxInternalInventory inventory) {
        boolean needUpdate = false;
        for (ForgeDirection side : pushSides) {
            IInventory neighbor = adjacentInventories[side.ordinal()];

            if (!((TileEntity)neighbor).isInvalid()) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (stack != null) {
                        needUpdate |= tryPushStack(stack, i, neighbor, side, inventory);
                    }
                }
            }
        }
        return needUpdate;
    }

    private static boolean tryPushStack(ItemStack stackToPush, int mySlot, IInventory neighbor, ForgeDirection side, FoxInternalInventory inventory) {
        boolean needUpdate = false;
        int sideTo = side.getOpposite().ordinal();

        for (int targetSlot = 0; targetSlot < neighbor.getSizeInventory(); targetSlot++) {
            if (neighbor instanceof ISidedInventory sided) {
                int finalSlot = targetSlot;
                if (Arrays.stream(sided.getAccessibleSlotsFromSide(sideTo)).noneMatch(x -> x == finalSlot)) {
                    continue;
                } else if (!neighbor.isItemValidForSlot(targetSlot, stackToPush) || !sided.canExtractItem(targetSlot, stackToPush, sideTo)) {
                    continue;
                }
            }

            ItemStack stackInTarget = neighbor.getStackInSlot(targetSlot);

            if (stackInTarget == null) {
                neighbor.setInventorySlotContents(targetSlot, stackToPush.copy());
                inventory.setInventorySlotContents(mySlot, null);
                neighbor.markDirty();
                needUpdate = true;
                break;
            } else if (ItemStackUtil.stackEquals(stackInTarget, stackToPush)) {
                int limit = Math.min(neighbor.getInventoryStackLimit(), stackInTarget.getMaxStackSize());
                int spaceLeft = limit - stackInTarget.stackSize;

                if (spaceLeft > 0) {
                    int amountToTransfer = Math.min(stackToPush.stackSize, spaceLeft);
                    stackInTarget.stackSize += amountToTransfer;
                    stackToPush.stackSize -= amountToTransfer;

                    if (stackToPush.stackSize <= 0) {
                        inventory.setInventorySlotContents(mySlot, null);
                    }

                    neighbor.markDirty();
                    needUpdate = true;

                    if (inventory.getStackInSlot(mySlot) == null) break;
                }
            }
        }
        return needUpdate;
    }

    public static boolean pushFluidIfCan(FluidTank tank, ForgeDirection[] pushFluidSides, IFluidHandler[] adjacentFluidHandlers) {
        if (tank.getFluidAmount() <= 0) return false;
        boolean needUpdate = false;

        for (ForgeDirection side : pushFluidSides) {
            IFluidHandler neighbor = adjacentFluidHandlers[side.ordinal()];

            if (neighbor instanceof IFluidHandler  && !((TileEntity)neighbor).isInvalid()) {
                needUpdate |= tryPushFluid(neighbor, side, tank);
            }
        }
        return needUpdate;
    }

    private static boolean tryPushFluid(IFluidHandler neighbor, ForgeDirection side, FluidTank tank) {
        ForgeDirection sideTo = side.getOpposite();
        FluidStack stackToPush = tank.getFluid().copy();
        boolean needUpdate = false;

        if (neighbor.canFill(sideTo, stackToPush.getFluid())) {
            int filled = neighbor.fill(sideTo, stackToPush, true);

            if (filled > 0) {
                tank.drain(filled, true);

                needUpdate = true;
                if (neighbor instanceof TileEntity tile) {
                    tile.markDirty();
                }
            }
        }
        return needUpdate;
    }

    public static ForgeDirection[] parseSides(List<Integer> raw) {
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

    public static void writeDirectionsToNbt(NBTTagCompound data, String key, ForgeDirection[] directions) {
        if (directions.length > 0) {
            int[] directionsNbt = new int[directions.length];
            for (int i = 0; i < directionsNbt.length; i++) {
                if (directions[i] == null) {
                    directionsNbt[i] = -1;
                } else {
                    directionsNbt[i] = directions[i].ordinal();
                }
            }
            data.setIntArray(key, directionsNbt);
        }
    }

    public static ForgeDirection[] readDirectionsFromNbt(NBTTagCompound data, String key) {
        ForgeDirection[] directions = new ForgeDirection[0];
        if (data.hasKey(key)) {
            int[] directionsNbt = data.getIntArray(key);
            directions = new ForgeDirection[directionsNbt.length];
            for (int i = 0; i < directionsNbt.length; i++) {
                if (directionsNbt[i] == -1) {
                    directions[i] = null;
                } else {
                    directions[i] = ForgeDirection.getOrientation(directionsNbt[i]);
                }
            }
        }
        return directions;
    }

    public static double safeMultiply(double a, double b) {
        if (a == 0 || b == 0) return 0;
        if (Double.isNaN(a) || Double.isNaN(b)) return Double.NaN;
        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            return (a > 0 == b > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
        }

        if (Math.abs(a) > Double.MAX_VALUE / Math.abs(b)) {
            return (a > 0 == b > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
        }

        return a * b;
    }

    public static Handler newHandler(IUpgradableTile tile, FoxInternalInventory inventory) {
        return new Handler(tile, inventory);
    }

    public static class Handler {
        private final FoxInternalInventory inventory;
        private final Set<UpgradesTypes> enabledTypes = EnumSet.noneOf(UpgradesTypes.class);
        private boolean canEject, canEjectFluid, canPull, canWater, canLava;
        private double defaultStorage, defaultEnergyNeed;
        private int defaultOperations, defaultTicksNeed, operationsLimit = Integer.MAX_VALUE;

        private Handler(IUpgradableTile tile, FoxInternalInventory inventory) {
            this.inventory = inventory;
            Collections.addAll(enabledTypes, tile.getAvailableTypes());
        }

        private boolean containsType(UpgradesTypes type) {
            return enabledTypes.contains(type);
        }

        public Handler storage(double defaultValue) {
            if (enabledTypes.contains(UpgradesTypes.STORAGE)) {
                this.defaultStorage = defaultValue;
            }
            return this;
        }

        public Handler speed(int defaultTicks, double defaultEnergy, int defaultOps, int opsLimit) {
            if (enabledTypes.contains(UpgradesTypes.SPEED)) {
                this.defaultTicksNeed = defaultTicks;
                this.defaultEnergyNeed = defaultEnergy;
                this.defaultOperations = defaultOps;
                this.operationsLimit = opsLimit;
            }
            return this;
        }

        public Handler ejector() {
            canEject = containsType(UpgradesTypes.EJECTOR);
            return this;
        }

        public Handler ejectorFluid() {
            canEjectFluid = containsType(UpgradesTypes.FLUID_EJECTOR);
            return this;
        }

        public Handler puller() {
            canPull = containsType(UpgradesTypes.PULLING);
            return this;
        }

        public Handler water() {
            canWater = containsType(UpgradesTypes.WATER_GENERATOR);
            return this;
        }

        public Handler lava() {
            canLava = containsType(UpgradesTypes.LAVA_GENERATOR);
            return this;
        }

        public HandlerResults process() {
            double storage = defaultStorage;
            double energyNeed = defaultEnergyNeed;
            int operations = defaultOperations;
            int ticksNeed = defaultTicksNeed;

            boolean hasWater = false, hasLava = false;

            List<Integer> pushRaw = new ArrayList<>();
            List<Integer> pushFRaw = new ArrayList<>();
            List<Integer> pullRaw = new ArrayList<>();

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack == null || stack.getItem() == null) continue;

                if (stack.getItem() instanceof IAdvancedUpgradeItem upgrade) {
                    if (enabledTypes.contains(UpgradesTypes.SPEED)) {
                        ticksNeed *= (int) upgrade.getSpeedMultiplier(stack);
                        operations += upgrade.getItemsPerOpAdd(stack);
                        energyNeed = safeMultiply(energyNeed, upgrade.getEnergyUseMultiplier(stack));
                    }
                    if (enabledTypes.contains(UpgradesTypes.STORAGE)) {
                        storage = safeMultiply(storage, upgrade.getStorageEnergyMultiplier(stack));
                    }
                } else if (stack.getItem() instanceof ItemFluidGeneratorUpgrade) {
                    switch (stack.getItemDamage()) {
                        case 0: {
                            hasWater = canWater;
                            break;
                        }
                        case 1: {
                            hasLava = canLava;
                            break;
                        }
                    }
                } else if (stack.getItem() instanceof IUpgradeItem) {
                    switch (stack.getItemDamage()) {
                        case 0: // Speed
                            if (enabledTypes.contains(UpgradesTypes.SPEED)) {
                                ticksNeed *= (int) Math.pow(0.7, stack.stackSize);
                                energyNeed *= Math.pow(1.6, stack.stackSize);
                            }
                            break;
                        case 2: // Energy Storage
                            if (enabledTypes.contains(UpgradesTypes.STORAGE)) {
                                storage += 10000 * stack.stackSize;
                            }
                            break;
                        case 3: // Ejector
                            if (enabledTypes.contains(UpgradesTypes.EJECTOR)) {
                                extractDir(stack, pushRaw);
                            }
                            break;
                        case 4: // Ejector Fluid
                            if (enabledTypes.contains(UpgradesTypes.FLUID_EJECTOR)) {
                                extractDir(stack, pushFRaw);
                            }
                            break;
                        case 6: // Pulling
                            if (enabledTypes.contains(UpgradesTypes.PULLING)) {
                                extractDir(stack, pullRaw);
                            }
                            break;
                    }
                }
            }

            ticksNeed = Math.max(1, ticksNeed);
            operations = Math.min(operations, operationsLimit);
            storage = Math.max(storage, energyNeed);

            return new HandlerResults(enabledTypes)
                .setStats(storage, energyNeed, operations, ticksNeed)
                .setStats(canEject, canEjectFluid, canPull, hasWater, hasLava)
                .setSides(parseSides(pushRaw), parseSides(pushFRaw), parseSides(pullRaw));
        }

        private void extractDir(ItemStack stack, List<Integer> list) {
            NBTTagCompound nbt = stack.getTagCompound();
            list.add((nbt != null && nbt.hasKey("dir")) ? (int) nbt.getByte("dir") : 0);
        }

        public static class HandlerResults {
            private final Set<UpgradesTypes> enabledTypes;
            private double storage, energyNeed;
            private int operations, ticksNeed;
            private boolean canEject, canEjectFluid, canPull, hasWater, hasLava;
            private ForgeDirection[] pushSides = new ForgeDirection[0];
            private ForgeDirection[] pushFluidSides = new ForgeDirection[0];
            private ForgeDirection[] pullSides = new ForgeDirection[0];

            protected HandlerResults(Set<UpgradesTypes> enabledTypes) {
                this.enabledTypes = enabledTypes;
            }

            protected HandlerResults setStats(double s, double e, int o, int t) {
                this.storage = s;
                this.energyNeed = e;
                this.operations = o;
                this.ticksNeed = t;
                return this;
            }

            protected HandlerResults setStats(boolean e, boolean f, boolean p, boolean w, boolean l) {
                this.canEject = e;
                this.canEjectFluid = f;
                this.canPull = p;
                this.hasWater = w;
                this.hasLava = l;
                return this;
            }

            protected HandlerResults setSides(ForgeDirection[] push, ForgeDirection[] pushFluid, ForgeDirection[] pull) {
                this.pushSides = push;
                this.pushFluidSides = pushFluid;
                this.pullSides = pull;
                return this;
            }

            public double getStorage() {
                return enabledTypes.contains(UpgradesTypes.STORAGE) ? storage : 0;
            }

            public double getEnergyNeed() {
                return enabledTypes.contains(UpgradesTypes.SPEED) ? energyNeed : 0;
            }

            public int getOperations() {
                return enabledTypes.contains(UpgradesTypes.SPEED) ? operations : 1;
            }

            public int getTicksNeed() {
                return enabledTypes.contains(UpgradesTypes.SPEED) ? ticksNeed : 20;
            }

            public boolean hasEjector() {
                return canEject && pushSides.length > 0;
            }

            public boolean hasEjectorFluid() {
                return canEjectFluid && pushFluidSides.length > 0;
            }

            public boolean hasPuller() {
                return canPull && pullSides.length > 0;
            }

            public boolean hasLava() {
                return hasLava;
            }

            public boolean hasWater() {
                return hasWater;
            }

            public ForgeDirection[] getPushSides() {
                return enabledTypes.contains(UpgradesTypes.EJECTOR) ? pushSides : new ForgeDirection[0];
            }

            public ForgeDirection[] getPushFluidSides() {
                return enabledTypes.contains(UpgradesTypes.FLUID_EJECTOR) ? pushFluidSides : new ForgeDirection[0];
            }

            public ForgeDirection[] getPullSides() {
                return enabledTypes.contains(UpgradesTypes.PULLING) ? pullSides : new ForgeDirection[0];
            }
        }
    }
}
