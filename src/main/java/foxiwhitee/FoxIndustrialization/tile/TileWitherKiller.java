package foxiwhitee.FoxIndustrialization.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.api.IUpgradableTile;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.*;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.InventoryUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.function.BiPredicate;

public class TileWitherKiller extends TileIC2Inv implements IUpgradableTile {
    private static ItemStack sigil, star;
    private static final BiPredicate<Integer, ItemStack> fillFilter = ((index, stack) -> {
        if (index >= 0 && index < 3) {
            return SlotFiltered.filters.get(FilterInitializer.FILTER_WITHER_SKULL).test(stack);
        } else if (index >= 3 && index < 7) {
            return SlotFiltered.filters.get(FilterInitializer.FILTER_SOUL_SAND).test(stack);
        } else {
            return false;
        }
    });

    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 7);
    private final FoxInternalInventory output = new FoxInternalInventory(this, 9);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3);
    private final int[] cachedAllSlots;
    private int ticksNeed, tick, bonus;
    private double energyNeed;
    private boolean doFill;

    private ForgeDirection[] pushSides = {}, pullSides = {};
    private final IInventory[] adjacentInventories = new IInventory[6];
    private int scanTimer = 0;
    private boolean hasEjector = false;
    private boolean hasPuller = false;

    public TileWitherKiller() {
        if (FICore.ifExtraUtilitesIsLoaded && sigil == null) {
            sigil = new ItemStack(GameRegistry.findItem("ExtraUtilites", "divisionSigil"));
        }
        if (star == null) {
            star = new ItemStack(Items.nether_star, 1);
        }

        this.maxEnergy = FIConfig.witherKillerStorage;
        this.energyNeed = FIConfig.witherKillerEnergyNeed;
        this.ticksNeed = FIConfig.witherKillerTicksNeed;

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
        if (doFill) {
            doFill = false;
            InventoryUtils.doFillInventory(inventory, fillFilter);
        }
        if (this.energy >= this.energyNeed) {
            ItemStack starCopy = star.copy();
            starCopy.stackSize += bonus;
            if (canCraft(starCopy)) {
                if (this.tick++ >= this.ticksNeed) {
                    this.tick = 0;
                    this.energy -= this.energyNeed;
                    consume();
                    InventoryUtils.insert(output, starCopy);
                    if (sigil != null && InventoryUtils.canInsert(output, sigil)) {
                        InventoryUtils.insert(output, sigil);
                    }
                }

                markForUpdate();
            } else {
                if (this.tick > 0) {
                    this.tick = 0;
                    markForUpdate();
                }
            }
        }
    }

    private void consume() {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null) {
                stack.stackSize--;
                if (stack.stackSize <= 0) {
                    inventory.setInventorySlotContents(i, null);
                }
            }
        }
    }

    private boolean canCraft(ItemStack starCopy) {
        boolean canInsert = InventoryUtils.canInsert(output, starCopy);
        boolean canConsume = true;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack == null) {
                canConsume = false;
                break;
            }
        }
        return canInsert && canConsume;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        output.writeToNBT(data, "output");
        upgrades.writeToNBT(data, "upgrades");
        data.setDouble("energyNeed", energyNeed);
        data.setInteger("ticksNeed", ticksNeed);
        data.setInteger("tick", tick);
        data.setInteger("bonus", bonus);
        data.setBoolean("hasEjector", hasEjector);
        data.setBoolean("hasPuller", hasPuller);
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
        ticksNeed = data.getInteger("ticksNeed");
        tick = data.getInteger("tick");
        bonus = data.getInteger("bonus");
        hasEjector = data.getBoolean("hasEjector");
        hasPuller = data.getBoolean("hasPuller");
        pullSides = UpgradeUtils.readDirectionsFromNbt(data, "pullSides");
        pushSides = UpgradeUtils.readDirectionsFromNbt(data, "pushSides");
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(ticksNeed);
        data.writeInt(tick);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        int oldTicksNeed = ticksNeed;
        int oldTick = tick;
        ticksNeed = data.readInt();
        tick = data.readInt();
        return old || oldTicksNeed != ticksNeed || oldTick != tick;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.WITHER_KILLER;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    @Override
    public UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.WITHER, UpgradesTypes.EJECTOR, UpgradesTypes.PULLING};
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.ADVANCED;
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
                .wither(FIConfig.witherKillerStorage, FIConfig.witherKillerEnergyNeed, FIConfig.witherKillerTicksNeed, 0)
                .puller()
                .ejector()
                .process();

            this.maxEnergy = handler.getStorage();
            this.energyNeed = handler.getEnergyNeed();
            this.ticksNeed = handler.getTicksNeed();
            this.bonus = handler.getBonus();
            this.hasEjector = handler.hasEjector();
            this.hasPuller = handler.hasPuller();
            this.pushSides = handler.getPushSides();
            this.pullSides = handler.getPullSides();

            this.energy = Math.min(this.energy, this.maxEnergy);

            markForUpdate();
        } else if (iInventory == inventory) {
            doFill = true;
        }
    }

    public int getTick() {
        return tick;
    }

    public int getTicksNeed() {
        return ticksNeed;
    }
}
