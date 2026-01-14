package foxiwhitee.FoxIndustrialization.utils;

import foxiwhitee.FoxIndustrialization.api.IHasMatterSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.api.IHasSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.api.ISynthesizerSunUpgrade;
import foxiwhitee.FoxIndustrialization.api.ISynthesizerUpgrade;
import foxiwhitee.FoxLib.api.energy.IDoubleEnergyContainerItem;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import ic2.api.item.IElectricItem;
import ic2.core.Ic2Items;
import ic2.core.item.ItemCrystalMemory;
import ic2.core.uu.UuGraph;
import ic2.core.uu.UuIndex;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.ArrayList;
import java.util.List;

public class FilterInitializer {
    private static final List<Class<?>> filterUltimateEnergyStorageClasses = new ArrayList<>();
    private static final List<Class<?>> filterQuantumEnergyStorageClasses = new ArrayList<>();
    private static final List<Class<?>> filterSingularEnergyStorageClasses = new ArrayList<>();

    private static final List<Class<?>> filterQuantumGeneratorClasses = new ArrayList<>();
    private static final List<Class<?>> filterInfinityGeneratorClasses = new ArrayList<>();

    static {
        addClassToFilterUltimateStorage(IElectricItem.class);
        addClassToFilterUltimateStorage(IDoubleEnergyContainerItem.class);
        addClassToFilterQuantumStorage(IElectricItem.class);
        addClassToFilterQuantumStorage(IDoubleEnergyContainerItem.class);
        addClassToFilterSingularStorage(IElectricItem.class);
        addClassToFilterSingularStorage(IDoubleEnergyContainerItem.class);
        addClassToFilterQuantumGenerator(IElectricItem.class);
        addClassToFilterQuantumGenerator(IDoubleEnergyContainerItem.class);
        addClassToFilterInfinityGenerator(IElectricItem.class);
        addClassToFilterInfinityGenerator(IDoubleEnergyContainerItem.class);
    }

    public static final String FILTER_BASIC_ENERGY_STORAGE = "basicEnergyStorage";
    public static final String FILTER_ADVANCED_ENERGY_STORAGE = "advancedEnergyStorage";
    public static final String FILTER_HYBRID_ENERGY_STORAGE = "hybridEnergyStorage";
    public static final String FILTER_NANO_ENERGY_STORAGE = "nanoEnergyStorage";
    public static final String FILTER_ULTIMATE_ENERGY_STORAGE = "ultimateEnergyStorage";
    public static final String FILTER_QUANTUM_ENERGY_STORAGE = "quantumEnergyStorage";
    public static final String FILTER_SINGULAR_ENERGY_STORAGE = "singularEnergyStorage";

    public static final String FILTER_ADVANCED_GENERATOR = "advancedGenerator";
    public static final String FILTER_NANO_GENERATOR = "nanoGenerator";
    public static final String FILTER_QUANTUM_GENERATOR = "quantumGenerator";
    public static final String FILTER_FLAMMABLE_MATERIAL = "flammableMaterial";

    public static final String FILTER_CUSTOM_SOLAR_PANEL = "solarPanel";
    public static final String FILTER_INFINITY_GENERATOR = "infinityGenerator";

    public static final String FILTER_SYNTHESIZER_SLOTS = "synthesizerSlots";
    public static final String FILTER_SYNTHESIZER_SUN_UPGRADE = "synthesizerSunUpgrade";
    public static final String FILTER_SYNTHESIZER_UPGRADE = "synthesizerUpgrade";

    public static final String FILTER_FLUID_GENERATOR = "fluidGenerator";

    public static final String FILTER_MATTER_GENERATOR = "matterGenerator";
    public static final String FILTER_SCRAP = "scrap";
    public static final String FILTER_MATTER_SYNTHESIZER = "matterSynthesizer";

    public static final String FILTER_SCANNER_CRYSTAL = "scannerCrystal";
    public static final String FILTER_SCANNER_SLOT = "scannerSlot";

    public static final String FILTER_SOUL_SAND = "soulSand";
    public static final String FILTER_WITHER_SKULL = "witherSkull";

    public static void initFilters() {
        SlotFiltered.filters.put(FILTER_BASIC_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_ADVANCED_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_HYBRID_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_NANO_ENERGY_STORAGE, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_ULTIMATE_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterUltimateEnergyStorageClasses.toArray(new Class[0])));
        SlotFiltered.filters.put(FILTER_QUANTUM_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterQuantumEnergyStorageClasses.toArray(new Class[0])));
        SlotFiltered.filters.put(FILTER_SINGULAR_ENERGY_STORAGE, stack -> itemInstanceof(stack, filterSingularEnergyStorageClasses.toArray(new Class[0])));
        SlotFiltered.filters.put(FILTER_ADVANCED_GENERATOR, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_NANO_GENERATOR, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_QUANTUM_GENERATOR, stack -> itemInstanceof(stack, filterQuantumGeneratorClasses.toArray(new Class[0])));

        SlotFiltered.filters.put(FILTER_FLAMMABLE_MATERIAL, stack -> TileEntityFurnace.getItemBurnTime(stack) > 0);

        SlotFiltered.filters.put(FILTER_CUSTOM_SOLAR_PANEL, stack -> itemInstanceof(stack, IElectricItem.class));
        SlotFiltered.filters.put(FILTER_INFINITY_GENERATOR, stack -> itemInstanceof(stack, filterInfinityGeneratorClasses.toArray(new Class[0])));

        SlotFiltered.filters.put(FILTER_SYNTHESIZER_SLOTS, stack -> itemInstanceof(stack, IHasSynthesizerIntegration.class));
        SlotFiltered.filters.put(FILTER_SYNTHESIZER_SUN_UPGRADE, stack -> itemInstanceof(stack, ISynthesizerSunUpgrade.class));
        SlotFiltered.filters.put(FILTER_SYNTHESIZER_UPGRADE, stack -> itemInstanceof(stack, ISynthesizerUpgrade.class));

        SlotFiltered.filters.put(FILTER_FLUID_GENERATOR, FluidContainerRegistry::isContainer);

        SlotFiltered.filters.put(FILTER_MATTER_GENERATOR, FluidContainerRegistry::isContainer);
        SlotFiltered.filters.put(FILTER_SCRAP, stack -> itemEquals(stack, Ic2Items.scrap.getItem()) || itemEquals(stack, Ic2Items.scrapBox.getItem()));
        SlotFiltered.filters.put(FILTER_MATTER_SYNTHESIZER, stack -> itemInstanceof(stack, IHasMatterSynthesizerIntegration.class) || Block.getBlockFromItem(stack.getItem()) == Block.getBlockFromItem(Ic2Items.massFabricator.getItem()));

        SlotFiltered.filters.put(FILTER_SCANNER_CRYSTAL, stack -> itemInstanceof(stack, ItemCrystalMemory.class));
        SlotFiltered.filters.put(FILTER_SCANNER_SLOT, stack -> {
            ItemStack copy = UuGraph.find(stack);
            return copy != null && UuIndex.instance.get(copy) < Double.POSITIVE_INFINITY;
        });

        SlotFiltered.filters.put(FILTER_SOUL_SAND, stack -> blockInstanceof(stack, BlockSoulSand.class));
        SlotFiltered.filters.put(FILTER_WITHER_SKULL, stack -> itemEquals(stack, Items.skull) && stack.getItemDamage() == 1);
    }

    public static void addClassToFilterUltimateStorage(Class<?> clazz) {
        filterUltimateEnergyStorageClasses.add(clazz);
    }

    public static void addClassToFilterQuantumStorage(Class<?> clazz) {
        filterQuantumEnergyStorageClasses.add(clazz);
    }

    public static void addClassToFilterSingularStorage(Class<?> clazz) {
        filterSingularEnergyStorageClasses.add(clazz);
    }

    public static void addClassToFilterQuantumGenerator(Class<?> clazz) {
        filterQuantumGeneratorClasses.add(clazz);
    }

    public static void addClassToFilterInfinityGenerator(Class<?> clazz) {
        filterInfinityGeneratorClasses.add(clazz);
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

    private static boolean itemEquals(ItemStack stack, Item item) {
        return stack.getItem() == item;
    }
}
