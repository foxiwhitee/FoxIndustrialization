package foxiwhitee.FoxIndustrialization.utils;

import foxiwhitee.FoxIndustrialization.api.energy.IDoubleEnergyContainerItem;
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

import java.util.ArrayList;
import java.util.List;

public class FilterInitializer {
    private static final List<Class<?>> filterUltimateEnergyStorageClasses = new ArrayList<>();
    private static final List<Class<?>> filterQuantumEnergyStorageClasses = new ArrayList<>();
    private static final List<Class<?>> filterSingularEnergyStorageClasses = new ArrayList<>();

    static {
        addClassToFilterUltimate(IElectricItem.class);
        addClassToFilterUltimate(IDoubleEnergyContainerItem.class);
        addClassToFilterQuantum(IElectricItem.class);
        addClassToFilterQuantum(IDoubleEnergyContainerItem.class);
        addClassToFilterSingular(IElectricItem.class);
        addClassToFilterSingular(IDoubleEnergyContainerItem.class);
    }

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
        SlotFiltered.filters.put(FILTER_ULTIMATE_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterUltimateEnergyStorageClasses.toArray(new Class[0])));
        SlotFiltered.filters.put(FILTER_QUANTUM_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterQuantumEnergyStorageClasses.toArray(new Class[0])));
        SlotFiltered.filters.put(FILTER_SINGULAR_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterSingularEnergyStorageClasses.toArray(new Class[0])));
    }

    public static void addClassToFilterUltimate(Class<?> clazz) {
        filterUltimateEnergyStorageClasses.add(clazz);
    }

    public static void addClassToFilterQuantum(Class<?> clazz) {
        filterQuantumEnergyStorageClasses.add(clazz);
    }

    public static void addClassToFilterSingular(Class<?> clazz) {
        filterSingularEnergyStorageClasses.add(clazz);
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
