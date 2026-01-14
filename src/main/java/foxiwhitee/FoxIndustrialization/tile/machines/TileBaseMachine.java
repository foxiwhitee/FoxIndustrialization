package foxiwhitee.FoxIndustrialization.tile.machines;

import foxiwhitee.FoxIndustrialization.api.IHasActiveState;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.tile.TileIC2Inv;
import foxiwhitee.FoxIndustrialization.utils.*;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public abstract class TileBaseMachine extends TileIC2Inv implements IHasActiveState, IUpgradableTile {
    private final FoxInternalInventory inventory;
    private final FoxInternalInventory output;
    private final FoxInternalInventory upgrades;
    protected final MachineTier tier;
    protected final IRecipeIC2[] currentRecipes;

    protected int[] ticks;
    private int itemsPerOp, ticksNeed;

    private double energyPerTick;
    private final double defaultMaxEnergy, defaultEnergyPerTick;

    private final int defaultItemsPerOp;

    private ForgeDirection[] pushSides = {}, pullSides = {};
    private final IInventory[] adjacentInventories = new IInventory[6];
    private int scanTimer = 0;
    private boolean hasEjector = false;
    private boolean hasPuller = false;
    private final int[] cachedAllSlots;

    private boolean doInventoryUpdateByMode = true;

    private ButtonInventoryMode inventoryMode = ButtonInventoryMode.NONE;

    private boolean active;

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

        if (doInventoryUpdateByMode) {
            switch (inventoryMode) {
                case MERGE:
                    InventoryUtils.doMergeInventory(inventory);
                    break;
                case FILL:
                    InventoryUtils.doFillInventory(inventory);
                    break;
            }
            doInventoryUpdateByMode = false;
        }

        if (canWork()) {
            for (int i = 0; i < ticks.length; i++) {
                if (currentRecipes[i] == null) {
                    getRecipe(inventory.getStackInSlot(i), i);
                }

                IRecipeIC2 recipe = currentRecipes[i];
                if (recipe == null) continue;

                if (energy < energyPerTick) continue;
                if (!InventoryUtils.canInsert(output, recipe.getOut(), i)) continue;

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

                    if (!InventoryUtils.canInsert(output, recipe.getOut(), i)) break;

                    in.stackSize -= need;
                    if (in.stackSize == 0) {
                        inventory.setInventorySlotContents(i, null);
                    }

                    InventoryUtils.insert(output, recipe.getOut().copy(), i);
                }

                getRecipe(inventory.getStackInSlot(i), i);

                markForUpdate();
            }
            if (someoneInWork()) {
                if (!active) {
                    active = true;
                    markForUpdate();
                }
            } else {
                if (active) {
                    active = false;
                    markForUpdate();
                }
            }
        } else {
            if (active) {
                active = false;
                markForUpdate();
            }
        }
    }

    private boolean someoneInWork() {
        for (IRecipeIC2 recipe : currentRecipes) {
            if (recipe != null) {
                return true;
            }
        }
        return false;
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
        UpgradeUtils.writeDirectionsToNbt(data, "pullSides", pullSides);
        UpgradeUtils.writeDirectionsToNbt(data, "pushSides", pushSides);
        data.setByte("invMode", (byte) inventoryMode.ordinal());
        data.setBoolean("hasEjector", hasEjector);
        data.setBoolean("hasPuller", hasPuller);
        data.setBoolean("active", active);
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
        pullSides = UpgradeUtils.readDirectionsFromNbt(data, "pullSides");
        pushSides = UpgradeUtils.readDirectionsFromNbt(data, "pushSides");
        inventoryMode = ButtonInventoryMode.values()[data.getByte("invMode")];
        hasEjector = data.getBoolean("hasEjector");
        hasPuller = data.getBoolean("hasPuller");
        active = data.getBoolean("active");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        for (int tick : ticks) {
            data.writeInt(tick);
        }
        data.writeInt(ticksNeed);
        data.writeByte(inventoryMode.ordinal());
        data.writeBoolean(active);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean oldActive = active;
        int[] oldTicks = Arrays.copyOf(ticks, ticks.length);
        int oldNeedTicks = ticksNeed;
        ButtonInventoryMode oldInvMode = inventoryMode;

        for (int i = 0; i < ticks.length; i++) {
            ticks[i] = data.readInt();
        }
        ticksNeed = data.readInt();
        inventoryMode = ButtonInventoryMode.values()[data.readByte()];
        active = data.readBoolean();
        return old || oldNeedTicks != ticksNeed || !Arrays.equals(oldTicks, ticks) || oldInvMode != inventoryMode || oldActive != active;
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
        return true;
    }

    @Override
    public void onChangeInventory(IInventory iInv, int slot, InvOperation op, ItemStack removed, ItemStack added) {
        if (iInv == upgrades) {
            var handler = UpgradeUtils.newHandler(this)
                .storage(defaultMaxEnergy)
                .speed(100, defaultEnergyPerTick, defaultItemsPerOp, 64)
                .ejector()
                .puller()
                .process();

            this.maxEnergy = handler.getStorage();
            this.energyPerTick = handler.getEnergyNeed();
            this.itemsPerOp = handler.getOperations();
            this.ticksNeed = handler.getTicksNeed();
            this.hasEjector = handler.hasEjector();
            this.hasPuller = handler.hasPuller();
            this.pullSides = handler.getPushSides();
            this.pullSides = handler.getPullSides();

            this.energy = Math.min(this.maxEnergy, energy);

            markForUpdate();
        } else if (iInv == inventory) {
            doInventoryUpdateByMode = true;
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

    public void changeInventoryMode() {
        int newID = inventoryMode.ordinal() + 1;
        if (newID >= ButtonInventoryMode.values().length) {
            newID -= ButtonInventoryMode.values().length;
        }
        inventoryMode = ButtonInventoryMode.values()[newID];
        markForUpdate();
    }

    public ButtonInventoryMode getInventoryMode() {
        return inventoryMode;
    }

    public MachineTier getTier() {
        return tier;
    }

    public double getTicksNeed() {
        return ticksNeed;
    }

    public int[] getTicks() {
        return ticks;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    protected abstract List<? extends IRecipeIC2> getRecipes();

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
