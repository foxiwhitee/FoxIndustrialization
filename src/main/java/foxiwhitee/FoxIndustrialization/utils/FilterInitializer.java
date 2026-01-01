package foxiwhitee.FoxIndustrialization.utils;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockTNT;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FilterInitializer {
    public static final String FILTER_BASIC_ENERGY_STORAGE = "basicEnergyStorage";
    public static final String FILTER_ADVANCED_ENERGY_STORAGE = "advancedEnergyStorage";
    public static final String FILTER_HYBRID_ENERGY_STORAGE = "hybridEnergyStorage";
    public static final String FILTER_NANO_ENERGY_STORAGE = "nanoEnergyStorage";
    public static final String FILTER_ULTIMATE_ENERGY_STORAGE = "ultimateEnergyStorage";
    public static final String FILTER_QUANTUM_ENERGY_STORAGE = "quantumEnergyStorage";
    public static final String FILTER_SINGULAR_ENERGY_STORAGE = "singularEnergyStorage";

    public static void initFilters() {
        SlotFiltered.filters.put(FILTER_BASIC_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_ADVANCED_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_HYBRID_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_NANO_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_ULTIMATE_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_QUANTUM_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_SINGULAR_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
    }

    @SafeVarargs
    private static boolean blockInstanceof(ItemStack stack, Class<? extends Block>... classes) {
        boolean b = false;
        for (Class<? extends Block> clazz : classes) {
            if (clazz.isInstance(Block.getBlockFromItem(stack.getItem()))) {
                b = true;
                break;
            }
        }
        return b;
    }

    @SafeVarargs
    private static boolean itemInstanceof(ItemStack stack, Class<?>... classes) {
        boolean b = false;
        for (Class<?> clazz : classes) {
            if (clazz.isInstance(stack.getItem())) {
                b = true;
                break;
            }
        }
        return b;
    }

    private static boolean itemInstanceofProductivity(ItemStack stack, boolean has) {
        return stack.getItem() instanceof ItemProductivityCard && has;
    }

    private static boolean itemEquals(ItemStack stack, Item item) {
        return stack.getItem() == item;
    }
}
