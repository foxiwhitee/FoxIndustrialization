package foxiwhitee.FoxIndustrialization.recipes.json;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.recipes.impl.UniversalFluidComplexRecipe;
import foxiwhitee.FoxLib.recipes.json.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

@JsonRecipe("universalFluidComplex")
public class UniversalFluidComplexJSONRecipe implements IJsonRecipe {
    @NumberValue("energy")
    private double energyNeed;

    @FluidValue(value = "outputFluid", canBeNull = true)
    private FluidStack outputFluid;

    @RecipeOutput
    @RecipeValue(value = "outputs", canBeNull = true)
    private List<ItemStack> outputs;

    @FluidValue(value = "inputsFluid", canBeNull = true)
    private List<FluidStack> inputFluids;

    @RecipeValue(value = "inputs", canBeNull = true)
    private List<ItemStack> inputs;

    public UniversalFluidComplexJSONRecipe() {}

    @Override
    public void register() {
        if (this.energyNeed == 0 ||
            ((this.inputFluids == null || this.inputFluids.isEmpty()) && (this.inputs == null || this.inputs.isEmpty())) ||
            (this.outputFluid == null && (this.outputs == null || this.outputs.isEmpty()))) {
            return;
        }
        ModRecipes.universalFluidComplexRecipes.add(new UniversalFluidComplexRecipe(energyNeed, outputFluid, outputs, inputFluids, inputs));
    }
}
