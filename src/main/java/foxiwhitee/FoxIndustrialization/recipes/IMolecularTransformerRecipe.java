package foxiwhitee.FoxIndustrialization.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IMolecularTransformerRecipe {
    Object getFirstInput();
    Object getSecondInput();

    FluidStack getInputFluid();

    ItemStack getOutput();

    double getEnergyNeed();

    boolean matches(ItemStack firstInput, ItemStack secondInput, FluidStack fluid);
}
