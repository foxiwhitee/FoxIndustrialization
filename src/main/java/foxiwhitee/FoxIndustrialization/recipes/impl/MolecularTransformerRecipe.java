package foxiwhitee.FoxIndustrialization.recipes.impl;

import foxiwhitee.FoxIndustrialization.recipes.IMolecularTransformerRecipe;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MolecularTransformerRecipe implements IMolecularTransformerRecipe {
    private final double energy;
    private final FluidStack inputFluid;
    private final ItemStack output;
    private final Object firstInput, secondInput;

    public MolecularTransformerRecipe(double energy, FluidStack inputFluid, ItemStack output, Object firstInput, Object secondInput) {
        this.energy = energy;
        this.inputFluid = inputFluid;
        this.output = output;
        this.firstInput = firstInput;
        this.secondInput = secondInput;
    }

    @Override
    public Object getFirstInput() {
        return firstInput;
    }

    @Override
    public Object getSecondInput() {
        return secondInput;
    }

    @Override
    public FluidStack getInputFluid() {
        return inputFluid;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public double getEnergyNeed() {
        return energy;
    }

    @Override
    public boolean matches(ItemStack firstInput, ItemStack secondInput, FluidStack fluid) {
        if (this.inputFluid != null) {
            if (fluid == null) return false;
            if (!this.inputFluid.isFluidEqual(fluid)) return false;
            if (fluid.amount < this.inputFluid.amount) return false;
        }

        boolean directMatch = ItemStackUtil.matchesStackAndOther(firstInput, this.firstInput);
        if (this.secondInput != null) {
            directMatch &= ItemStackUtil.matchesStackAndOther(secondInput, this.secondInput);
        }

        if (directMatch) return true;

        boolean crossMatch = ItemStackUtil.matchesStackAndOther(secondInput, this.firstInput);
        if (this.secondInput != null) {
            crossMatch &= ItemStackUtil.matchesStackAndOther(firstInput, this.secondInput);
        }

        return crossMatch;
    }
}
