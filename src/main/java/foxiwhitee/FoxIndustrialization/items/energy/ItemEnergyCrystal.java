package foxiwhitee.FoxIndustrialization.items.energy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.items.DefaultItem;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemEnergyCrystal extends DefaultItem implements IElectricItem {
    public ItemEnergyCrystal(String name) {
        super(name, 1);
        this.setTextureName(FICore.MODID + ":" + "crystals/" + name);
        this.setMaxDamage(27);
        this.setNoRepair();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.energyCrystal.charge", EnergyUtility.formatNumber(ElectricItem.manager.getCharge(stack)), EnergyUtility.formatNumber(getMaxCharge(stack))));
            list.add(LocalizationUtils.localize("tooltip.energyCrystal.output", EnergyUtility.formatNumber(getOutput())));
            list.add(LocalizationUtils.localize("tooltip.energyCrystal.modeIs", LocalizationUtils.localize("tooltip.energyCrystal.mode." + getMode(stack).name().toLowerCase())));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (world.isRemote || !(entity instanceof EntityPlayer player)) return;

        Mode mode = getMode(stack);
        if (mode == Mode.DISABLED) return;

        double energyToGive = ElectricItem.manager.getCharge(stack);
        if (energyToGive <= 0) return;

        if (mode == Mode.HOT_BAR || mode == Mode.HOT_BAR_AND_ARMOR || mode == Mode.ALL) {
            chargeRange(player, stack, 0, 9);
        }

        if (mode == Mode.ARMOR || mode == Mode.HOT_BAR_AND_ARMOR || mode == Mode.ALL) {
            chargeRange(player, stack, 100, 104);
        }

        if (mode == Mode.ALL) {
            chargeRange(player, stack, 9, 36);
        }
    }

    protected void chargeRange(EntityPlayer player, ItemStack charger, int start, int end) {
        for (int i = start; i < end; i++) {
            ItemStack target = (i >= 100) ? player.inventory.armorInventory[i - 100] : player.inventory.getStackInSlot(i);

            if (target != null && target != charger && target.getItem() instanceof IElectricItem && !(target.getItem() instanceof ItemEnergyCrystal)) {
                double transferred = ElectricItem.manager.charge(target, getOutput(), getTier(), false, false);
                if (transferred > 0) {
                    ElectricItem.manager.discharge(charger, transferred, getTier(), true, false, false);
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                Mode nextMode = getMode(stack).next();
                setMode(stack, nextMode);
                player.addChatMessage(new ChatComponentText(LocalizationUtils.localize("tooltip.energyCrystal.modeIs", LocalizationUtils.localize("tooltip.energyCrystal.mode." + nextMode.name().toLowerCase()))));
            }
            player.swingItem();
        }
        return stack;
    }

    public void setMode(ItemStack stack, Mode mode) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("mode", mode.ordinal());
    }

    public Mode getMode(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("mode")) {
            return Mode.values()[stack.getTagCompound().getInteger("mode")];
        }
        return Mode.DISABLED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return getMode(stack) != Mode.DISABLED;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        ItemStack itemStack = new ItemStack(this, 1);
        if (this.getChargedItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
            itemList.add(charged);
        }

        if (this.getEmptyItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, 0, Integer.MAX_VALUE, true, false);
            itemList.add(charged);
        }
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return getMaxStorage();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return getTier();
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return getOutput();
    }

    protected abstract double getMaxStorage();

    protected abstract double getOutput();

    protected abstract int getTier();

    public enum Mode {
        DISABLED, HOT_BAR, ARMOR, HOT_BAR_AND_ARMOR, ALL;

        public Mode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }
}
