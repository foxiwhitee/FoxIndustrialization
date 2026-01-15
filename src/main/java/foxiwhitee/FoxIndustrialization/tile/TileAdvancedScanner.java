package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import ic2.api.recipe.IPatternStorage;
import ic2.core.block.machine.tileentity.TileEntityScanner;
import ic2.core.item.ItemCrystalMemory;
import ic2.core.uu.UuGraph;
import ic2.core.uu.UuIndex;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileAdvancedScanner extends TileIC2Inv {
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 1, 1);
    private final FoxInternalInventory disk = new FoxInternalInventory(this, 1, 1);
    private final static int energyToConsume = 512;
    private TileEntityScanner.State state = TileEntityScanner.State.IDLE;
    private IPatternStorage cachedStorage = null;
    private int tickCounterToUpdate = 0, progress;
    private ItemStack currentStack;
    private ItemStack pattern = null;
    private double patternUu;
    private boolean sleep;

    public TileAdvancedScanner() {

    }

    @Override
    @TileEvent(TileEventType.TICK)
    public void tick() {
        super.tick();

        if (tickCounterToUpdate++ >= 20) {
            tickCounterToUpdate = 0;
            this.cachedStorage = fetchPatternStorage();
        }

        if (this.progress < getTicksNeed()) {
            ItemStack slotStack = inventory.getStackInSlot(0);

            if (slotStack != null && (this.currentStack == null || ItemStackUtil.stackEquals(this.currentStack, slotStack))) {
                if (this.cachedStorage == null && disk.getStackInSlot(0) == null) {
                    this.state = TileEntityScanner.State.NO_STORAGE;
                    this.reset();
                } else if (this.energy < energyToConsume) {
                    this.state = TileEntityScanner.State.NO_ENERGY;
                    markForUpdate();
                } else {
                    if (sleep) {
                        return;
                    }
                    if (this.currentStack == null) {
                        this.currentStack = slotStack.copy();
                        this.pattern = UuGraph.find(this.currentStack);
                    }

                    if (this.pattern == null) {
                        this.state = TileEntityScanner.State.FAILED;
                        sleep = true;
                        markForUpdate();
                    } else if (this.isPatternRecorded(this.pattern)) {
                        this.state = TileEntityScanner.State.ALREADY_RECORDED;
                        this.reset();
                    } else {
                        this.state = TileEntityScanner.State.SCANNING;
                        this.energy -= energyToConsume;
                        this.progress++;

                        if (this.progress >= getTicksNeed()) {
                            finalizeScan();
                        }
                        markForUpdate();
                    }
                }
            } else {
                if (this.state != TileEntityScanner.State.COMPLETED) {
                    this.state = TileEntityScanner.State.IDLE;
                    this.reset();
                }
            }
        }
    }

    private void finalizeScan() {
        this.refreshInfo();
        if (this.patternUu != Double.POSITIVE_INFINITY) {
            this.state = TileEntityScanner.State.COMPLETED;
            ItemStack slotStack = inventory.getStackInSlot(0);
            slotStack.stackSize--;
            if (slotStack.stackSize == 0) {
                inventory.setInventorySlotContents(0, null);
            }
            this.markDirty();
        } else {
            this.state = TileEntityScanner.State.FAILED;
            sleep = true;
            this.reset();
        }
    }

    private void refreshInfo() {
        if (this.pattern != null) {
            this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
        }
    }

    private boolean isPatternRecorded(ItemStack stack) {
        if (disk.getStackInSlot(0) != null) {
            ItemStack crystalMemory = disk.getStackInSlot(0);
            if (crystalMemory.getItem() instanceof ItemCrystalMemory) {
                ItemStack recorded = ((ItemCrystalMemory)crystalMemory.getItem()).readItemStack(crystalMemory);
                if (ItemStackUtil.stackEquals(recorded, stack)) return true;
            }
        }

        IPatternStorage storage = getPatternStorage();
        if (storage != null) {
            for (ItemStack stored : storage.getPatterns()) {
                if (ItemStackUtil.stackEquals(stored, stack)) return true;
            }
        }

        return false;
    }

    private IPatternStorage getPatternStorage() {
        if (this.cachedStorage == null) {
            this.cachedStorage = fetchPatternStorage();
        }
        return this.cachedStorage;
    }

    public void reset() {
        this.progress = 0;
        this.currentStack = null;
        this.pattern = null;
        markForUpdate();
    }

    public void record() {
        if (!isDone()) {
            return;
        }
        if (this.pattern != null && this.patternUu != Double.POSITIVE_INFINITY) {
            if (!this.savetoDisk(this.pattern)) {
                IPatternStorage storage = getPatternStorage();
                if (storage == null || !storage.addPattern(this.pattern)) {
                    this.state = TileEntityScanner.State.TRANSFER_ERROR;
                    return;
                }
            }
            this.reset();
        } else {
            this.reset();
        }
    }

    private boolean isDone() { return this.progress >= getTicksNeed(); }

    private boolean savetoDisk(ItemStack stack) {
        if (disk.getStackInSlot(0) != null && stack != null) {
            ItemStack crystalMemory = disk.getStackInSlot(0);
            if (crystalMemory.getItem() instanceof ItemCrystalMemory) {
                ((ItemCrystalMemory)crystalMemory.getItem()).writecontentsTag(crystalMemory, stack);
                return true;
            }
        }
        return false;
    }

    private IPatternStorage fetchPatternStorage() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.blockExists(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)) {
                TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                if (te instanceof IPatternStorage) return (IPatternStorage) te;
            }
        }
        return null;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        disk.writeToNBT(data, "disk");
        if (this.currentStack != null) {
            NBTTagCompound contentTag = new NBTTagCompound();
            this.currentStack.writeToNBT(contentTag);
            data.setTag("currentStack", contentTag);
        }

        if (this.pattern != null) {
            NBTTagCompound contentTag = new NBTTagCompound();
            this.pattern.writeToNBT(contentTag);
            data.setTag("pattern", contentTag);
        }
        data.setInteger("state", state.ordinal());
        data.setInteger("progress", progress);
        data.setBoolean("sleep", sleep);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        disk.readFromNBT(data, "disk");
        NBTTagCompound contentTag = data.getCompoundTag("currentStack");
        this.currentStack = ItemStack.loadItemStackFromNBT(contentTag);

        contentTag = data.getCompoundTag("pattern");
        this.pattern = ItemStack.loadItemStackFromNBT(contentTag);

        state = TileEntityScanner.State.values()[data.getInteger("state")];
        progress = data.getInteger("progress");
        sleep = data.getBoolean("sleep");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(state.ordinal());
        data.writeInt(progress);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        TileEntityScanner.State oldState = state;
        int oldProgress = progress;
        state = TileEntityScanner.State.values()[data.readInt()];
        progress = data.readInt();
        return old || oldState != state || oldProgress != progress;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_SCANNER;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getDiskInventory() {
        return disk;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection var1) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == inventory) {
            sleep = false;
        }
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        if (disk.getStackInSlot(0) != null) {
            drops.add(disk.getStackInSlot(0));
        }
    }

    public int getTicksNeed() {
        return FIConfig.advancedScannerTicksNeed;
    }

    public int getProgress() {
        return progress;
    }

    public TileEntityScanner.State getState() {
        return state;
    }

    public double getPatternUu() {
        return patternUu;
    }

    public void setIdle() {
        this.state = TileEntityScanner.State.IDLE;
    }
}
