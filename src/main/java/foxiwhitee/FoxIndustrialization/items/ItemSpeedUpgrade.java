package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.api.IAdvancedUpgradeItem;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.text.DecimalFormat;
import java.util.List;

public class ItemSpeedUpgrade extends ItemWithMeta implements IAdvancedUpgradeItem {
    private static final DecimalFormat decimalformat = new DecimalFormat("0.##");

    public ItemSpeedUpgrade(String name) {
        super(name, "upgrades/", "Advanced", "Nano", "Quantum");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            int add = this.getItemsPerOpAdd(stack);
            if (add > 0) {
                list.add(StatCollector.translateToLocalFormatted("tooltip.upgrade.addPerOp", decimalformat.format(add)));
            }
            list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.time", decimalformat.format(this.getSpeedMultiplier(stack) * 100)));
            list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.power", decimalformat.format(this.getEnergyUseMultiplier(stack) * 100)));
        }
    }

    @Override
    public double getSpeedMultiplier(ItemStack stack) {
        double effect = switch (stack.getItemDamage()) {
            case 0 -> FIConfig.speedUpgradeAdvancedMultiplier;
            case 1 -> FIConfig.speedUpgradeNanoMultiplier;
            case 2 -> FIConfig.speedUpgradeQuantumMultiplier;
            default -> 1;
        };
        return Math.pow(effect, stack.stackSize);
    }

    @Override
    public double getStorageEnergyMultiplier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getItemsPerOpAdd(ItemStack stack) {
        int effect = switch (stack.getItemDamage()) {
            case 0 -> FIConfig.speedUpgradeAdvancedItemsPerOpAdd;
            case 1 -> FIConfig.speedUpgradeNanoItemsPerOpAdd;
            case 2 -> FIConfig.speedUpgradeQuantumItemsPerOpAdd;
            default -> 0;
        };
        return effect * stack.stackSize;
    }

    @Override
    public double getEnergyUseMultiplier(ItemStack stack) {
        double effect = switch (stack.getItemDamage()) {
            case 0 -> FIConfig.speedUpgradeAdvancedUseMultiplier;
            case 1 -> FIConfig.speedUpgradeNanoUseMultiplier;
            case 2 -> FIConfig.speedUpgradeQuantumUseMultiplier;
            default -> 1;
        };
        return Math.pow(effect, stack.stackSize);
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
