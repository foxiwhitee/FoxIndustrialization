package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.text.DecimalFormat;
import java.util.List;

public class ItemStorageUpgrade extends ItemWithMeta implements IAdvancedUpgradeItem {
    private static final DecimalFormat decimalformat = new DecimalFormat("0.##");

    public ItemStorageUpgrade(String name) {
        super(name, "upgrades/", "Advanced", "Nano", "Quantum");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(StatCollector.translateToLocalFormatted("tooltip.upgrade.storageMult", decimalformat.format(this.getStorageEnergyMultiplier(stack) * (double)100)));
        }
    }

    @Override
    public double getSpeedMultiplier(ItemStack stack) {
        return 1;
    }

    @Override
    public double getStorageEnergyMultiplier(ItemStack stack) {
        double effect = switch (stack.getItemDamage()) {
            case 0 -> FIConfig.storageUpgradeAdvancedMultiplier;
            case 1 -> FIConfig.storageUpgradeNanoMultiplier;
            case 2 -> FIConfig.storageUpgradeQuantumMultiplier;
            default -> 1;
        };
        return Math.pow(effect, stack.stackSize);
    }

    @Override
    public int getItemsPerOpAdd(ItemStack stack) {
        return 0;
    }

    @Override
    public double getEnergyUseMultiplier(ItemStack stack) {
        return 1;
    }

    @Override
    public MachineTier getTier(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 1 -> MachineTier.NANO;
            case 2 -> MachineTier.QUANTUM;
            default -> MachineTier.ADVANCED;
        };
    }
}
