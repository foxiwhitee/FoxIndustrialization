package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.InventoryUtils;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradeUtils;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import ic2.api.recipe.IPatternStorage;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import ic2.core.util.StackUtil;
import ic2.core.uu.UuIndex;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.List;

public class TileQuantumReplicator extends TileIC2Inv implements IFluidHandler, IUpgradableTile {
    private static Fluid fluid;
    private final FoxInternalInventory outputs = new FoxInternalInventory(this, 2);
    private final FoxInternalInventory inputs = new FoxInternalInventory(this, 2, 1);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3);
    private final FluidTank tank;
    private static final int energyNeed = 10_000;
    private TileEntityReplicator.Mode mode = TileEntityReplicator.Mode.STOPPED;
    private int[] patterns = new int[2];

    private int scanTimer;
    private final IInventory[] adjacentInventories = new IInventory[6];
    private ForgeDirection[] pushSides = {};
    private boolean hasEjector;

    public TileQuantumReplicator() {
        this.maxEnergy = FIConfig.quantumReplicatorStorage;
        this.tank = new FluidTank(FIConfig.quantumReplicatorTank);
        if (fluid == null) {
            fluid = FluidRegistry.getFluid("ic2uumatter");
        }
    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();
        if (worldObj.isRemote) {
            return;
        }
        if (scanTimer-- <= 0) {
            UpgradeUtils.updateAdjacentCache(this, adjacentInventories);
            scanTimer = 20;
        }
        if (hasEjector) {
            if (UpgradeUtils.pushIfCan(pushSides, adjacentInventories, outputs)) {
                markForUpdate();
            }
        }
        if (mode == TileEntityReplicator.Mode.SINGLE || mode == TileEntityReplicator.Mode.CONTINUOUS) {
            refreshInfo();
            boolean mustStop = false;
            boolean needUpdate = false;
            for (int i = 0; i < inputs.getSizeInventory(); i++) {
                ItemStack stack = inputs.getStackInSlot(i);
                if (stack != null) {
                    if (this.energy >= energyNeed && tank.getFluidAmount() >= patterns[i] && InventoryUtils.canInsert(outputs, stack, i)) {
                        this.energy -= energyNeed;
                        this.tank.drain(patterns[i], true);
                        InventoryUtils.insert(outputs, stack, i);
                        needUpdate = true;
                        if (mode == TileEntityReplicator.Mode.SINGLE) {
                            mustStop = true;
                        }
                    }
                }
            }
            if (mustStop) {
                mode = TileEntityReplicator.Mode.STOPPED;
            }
            if (needUpdate) {
                markForUpdate();
            }
        }
    }

    private void refreshInfo() {
        boolean mustStop = true;
        for (int i = 0; i < inputs.getSizeInventory(); i++) {
            ItemStack stack = inputs.getStackInSlot(i);
            if (stack == null) {
                this.patterns[i] = 0;
            } else {
                mustStop = false;
                this.patterns[i] = (int) (UuIndex.instance.getInBuckets(stack) * 1000);
            }
        }
        if (mustStop) {
            this.mode = TileEntityReplicator.Mode.STOPPED;
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        inputs.writeToNBT(data, "inputs");
        upgrades.writeToNBT(data, "upgrades");
        InventoryUtils.writeTankToNbt(data, "tank", tank);
        data.setBoolean("hasEjector", hasEjector);
        UpgradeUtils.writeDirectionsToNbt(data, "pushSides", pushSides);
        data.setInteger("mode", mode.ordinal());
        data.setIntArray("patterns", patterns);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        inputs.readFromNBT(data, "inputs");
        upgrades.readFromNBT(data, "upgrades");
        InventoryUtils.readTankFromNbt(data, "tank", tank);
        hasEjector = data.getBoolean("hasEjector");
        pushSides = UpgradeUtils.readDirectionsFromNbt(data, "pushSides");
        mode = TileEntityReplicator.Mode.values()[data.getInteger("mode")];
        patterns = data.getIntArray("patterns");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        InventoryUtils.writeTankToStream(data, tank);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean changedTank = InventoryUtils.readTankFromStream(data, tank);
        return old || changedTank;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return outputs;
    }

    public FoxInternalInventory getInputsInventory() {
        return inputs;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        for (int i = 0; i < inputs.getSizeInventory(); i++) {
            ItemStack stack = inputs.getStackInSlot(i);
            if (stack != null) {
                drops.add(stack);
            }
        }
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack stack = upgrades.getStackInSlot(i);
            if (stack != null) {
                drops.add(stack);
            }
        }
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[] {0, 1};
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            var handler = UpgradeUtils.newHandler(this, upgrades)
                .storage(FIConfig.quantumReplicatorStorage)
                .ejector()
                .process();

            this.maxEnergy = handler.getStorage();
            this.hasEjector = handler.hasEjector();
            this.pushSides = handler.getPushSides();

            this.energy = Math.min(this.maxEnergy, this.energy);

            markForUpdate();
        } else if (iInventory == inputs) {
            refreshInfo();
            ItemStack[] old = new ItemStack[] {inputs.getStackInSlot(0), inputs.getStackInSlot(1)};
            for (int j = 0; j < inputs.getSizeInventory(); j++) {
                ItemStack stack = inputs.getStackInSlot(j);
                if (stack == null && old[j] != null) {
                    patterns[j] = 0;
                    mode = TileEntityReplicator.Mode.STOPPED;
                } else if (stack != null && old[j] != null) {
                    if (!ItemStackUtil.stackEquals(stack, old[j])) {
                        patterns[j] = 0;
                        mode = TileEntityReplicator.Mode.STOPPED;
                    }
                }
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluid() == fluid) {
            int filled = tank.fill(resource, doFill);
            markForUpdate();
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
        return fluid == TileQuantumReplicator.fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.STORAGE, UpgradesTypes.EJECTOR};
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.QUANTUM;
    }

    public void setMode(TileEntityReplicator.Mode mode) {
        this.mode = mode;
    }
}
