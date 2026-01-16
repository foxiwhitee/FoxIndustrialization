package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IMolecularTransformerRecipe;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradeUtils;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.InventoryUtils;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.List;

public class TileMolecularTransformer extends TileIC2Inv implements IFluidHandler, IUpgradableTile {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 2);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 1);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3);
    private final FluidTank tank;
    private double energyNeed, energyConsumed;
    private IMolecularTransformerRecipe recipe;

    private int scanTimer;
    private final IInventory[] adjacentInventories = new IInventory[6];
    private ForgeDirection[] pushSides = {}, pullSides = {};
    private boolean hasEjector, hasPuller, hasWaterGenerator, hasLavaGenerator, needFluidUpdate, active, started;

    public TileMolecularTransformer() {
        this.maxEnergy = FIConfig.molecularTransformerStorage;
        this.tank = new FluidTank(FIConfig.molecularTransformerTank);

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
            UpgradeUtils.updateAdjacentCache(this, adjacentInventories);
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

        if (!started && InventoryUtils.canInsert(output, recipe.getOutput())) {
            if (!active) {
                active = true;
                markForUpdate();
            }
            started = true;
            if (recipe.getInputFluid() != null) {
                this.tank.drain(recipe.getInputFluid().amount, true);
            }
            boolean firstConsumed = false, secondConsumed = false;
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack != null) {
                    if (!firstConsumed && consumeIf(firstConsumed, stack, recipe.getFirstInput())) {
                        firstConsumed = true;
                        if (stack.stackSize <= 0) {
                            inventory.setInventorySlotContents(i, null);
                        }
                        continue;
                    }
                    if (!secondConsumed && consumeIf(secondConsumed, stack, recipe.getSecondInput())) {
                        secondConsumed = true;
                        if (stack.stackSize <= 0) {
                            inventory.setInventorySlotContents(i, null);
                        }
                    }
                }
            }
        }

        double canConsume = Math.min(this.energy, this.energyNeed - this.energyConsumed);
        this.energy -= canConsume;
        this.energyConsumed += canConsume;
        if (this.energyConsumed >= this.energyNeed) {
            this.energyNeed = 0;
            this.energyConsumed = 0;
            this.started = false;
            InventoryUtils.insert(output, recipe.getOutput());
            getRecipe();
            if (recipe == null && active) {
                active = false;
            }
        }
        markForUpdate();
    }

    private boolean consumeIf(boolean consumed, ItemStack stack, Object o) {
        if (!consumed && o != null) {
            if (ItemStackUtil.matchesStackAndOther(stack, o)) {
                if (o instanceof ItemStack is) {
                    stack.stackSize -= is.stackSize;
                    return true;
                } else if (o instanceof StackOreDict ore) {
                    stack.stackSize -= ore.getCount();
                    return true;
                }
            }
        }
        return false;
    }

    private void getRecipe() {
        for (var recipe : ModRecipes.molecularTransformerRecipes) {
            if (recipe.matches(inventory.getStackInSlot(0), inventory.getStackInSlot(1), tank.getFluid())) {
                this.recipe = recipe;
                this.energyNeed = recipe.getEnergyNeed();
                return;
            }
        }
        this.recipe = null;
    }

    private void updateFluids() {
        if (hasWaterGenerator) {
            this.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, FIConfig.ufcFluidStorage), true);
        }
        if (hasLavaGenerator) {
            this.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.LAVA, FIConfig.ufcFluidStorage), true);
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        upgrades.writeToNBT(data, "upgrades");
        data.setDouble("energyNeed", energyNeed);
        data.setDouble("energyConsumed", energyConsumed);
        data.setBoolean("hasPuller", hasPuller);
        data.setBoolean("hasEjector", hasEjector);
        data.setBoolean("hasWaterGenerator", hasWaterGenerator);
        data.setBoolean("hasLavaGenerator", hasLavaGenerator);
        data.setBoolean("needFluidUpdate", needFluidUpdate);
        data.setBoolean("active", active);
        data.setBoolean("started", started);
        InventoryUtils.writeTankToNbt(data, "tank", tank);
        UpgradeUtils.writeDirectionsToNbt(data, "pullSides", pullSides);
        UpgradeUtils.writeDirectionsToNbt(data, "pushSides", pushSides);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        output.readFromNBT(data, "output");
        upgrades.readFromNBT(data, "upgrades");
        energyNeed = data.getDouble("energyNeed");
        energyConsumed = data.getDouble("energyConsumed");
        hasPuller = data.getBoolean("hasPuller");
        hasEjector = data.getBoolean("hasEjector");
        hasWaterGenerator = data.getBoolean("hasWaterGenerator");
        hasLavaGenerator = data.getBoolean("hasLavaGenerator");
        needFluidUpdate = data.getBoolean("needFluidUpdate");
        active = data.getBoolean("active");
        started = data.getBoolean("started");
        InventoryUtils.readTankFromNbt(data, "tank", tank);
        pullSides = UpgradeUtils.readDirectionsFromNbt(data, "pullSides");
        pushSides = UpgradeUtils.readDirectionsFromNbt(data, "pushSides");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeDouble(energyNeed);
        data.writeDouble(energyConsumed);
        data.writeBoolean(this.active);
        InventoryUtils.writeTankToStream(data, tank);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        double oldEnergyNeed = energyNeed;
        energyNeed = data.readDouble();
        double oldEnergyConsumed = energyConsumed;
        energyConsumed = data.readDouble();
        boolean oldActive = active;
        active = data.readBoolean();
        boolean tankChanged = InventoryUtils.readTankFromStream(data, tank);
        return old || tankChanged || oldEnergyNeed != energyNeed || oldEnergyConsumed != energyConsumed || oldActive != active;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
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
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[] {0, 1, 2};
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.MOLECULAR_TRANSFORMER;
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            var handler = UpgradeUtils.newHandler(this)
                .storage(FIConfig.molecularTransformerStorage)
                .ejector()
                .puller()
                .water()
                .lava()
                .process();

            this.maxEnergy = handler.getStorage();
            this.hasWaterGenerator = handler.hasWater();
            this.hasLavaGenerator = handler.hasLava();
            this.hasEjector = handler.hasEjector();
            this.hasPuller = handler.hasPuller();
            this.pushSides = handler.getPushSides();
            this.pullSides = handler.getPullSides();

            this.needFluidUpdate = true;
            this.energy = Math.min(this.maxEnergy, energy);

            markForUpdate();
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null) return 0;

        if (tank.getFluid() != null && tank.getFluid().isFluidEqual(resource)) {
            int filled = tank.fill(resource, doFill);
            if (filled > 0) {
                markForUpdate();
            }
            return filled;
        }

        if (tank.getFluid() == null) {
            int filled = tank.fill(resource, doFill);
            if (filled > 0) {
                markForUpdate();
            }
            return filled;
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (tank.getFluid() == null) return true;
        return tank.getFluid().getFluid() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }

    public void clearTank() {
        clearTank(tank);
        needFluidUpdate = true;
    }

    private void clearTank(FluidTank tank) {
        if (tank != null && tank.getFluid() != null) {
            tank.setFluid(null);
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

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.STORAGE, UpgradesTypes.EJECTOR, UpgradesTypes.PULLING, UpgradesTypes.LAVA_GENERATOR, UpgradesTypes.WATER_GENERATOR};
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.QUANTUM;
    }

    @Override
    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    public FluidTank getTank() {
        return tank;
    }

    public double getEnergyNeed() {
        return energyNeed;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }
}
