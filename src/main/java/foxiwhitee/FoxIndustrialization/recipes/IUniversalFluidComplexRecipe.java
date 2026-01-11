package foxiwhitee.FoxIndustrialization.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface IUniversalFluidComplexRecipe {
    List<ItemStack> getOutputs();
    FluidStack getOutputFluid();

    List<ItemStack> getInputs();
    List<FluidStack> getInputsFluid();

    double getEnergyNeed();

    boolean matches(List<ItemStack> itemStacks, List<FluidStack> fluidStacks);
}
