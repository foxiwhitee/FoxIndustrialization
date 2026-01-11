package foxiwhitee.FoxIndustrialization.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.utils.RecipeFluidUtils;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.value.IAny;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniversalFluidComplexJSONRecipe implements IJsonRecipe<UniversalFluidComplexJSONRecipe.FluidAndItemStack, UniversalFluidComplexJSONRecipe.FluidAndItemStack> {
    private double energyNeed;
    private FluidStack outputFluid;
    private List<ItemStack> outputs;
    private List<FluidStack> inputFluids;
    private List<ItemStack> inputs;

    public UniversalFluidComplexJSONRecipe() {}

    @Override
    public FluidAndItemStack[] getOutputs() {
        return new FluidAndItemStack[] {new FluidAndItemStack(Arrays.asList(outputFluid), outputs)};
    }

    @Override
    public FluidAndItemStack[] getInputs() {
        return new FluidAndItemStack[] {new FluidAndItemStack(inputFluids, inputs)};
    }

    @Override
    public boolean matches(List<FluidAndItemStack> list) {
        return UniversalFluidComplexRecipe.checkInputsFluid(list.get(0).fluids, inputFluids) && UniversalFluidComplexRecipe.checkInputsItem(list.get(0).items, inputs);
    }

    @Override
    public boolean hasOreDict() {
        return false;
    }

    @Override
    public boolean hasMineTweakerIntegration() {
        return false;
    }

    @Override
    public String getType() {
        return "universalFluidComplex";
    }

    @Override
    public IJsonRecipe create(JsonObject jsonObject) {
        try {
            this.energyNeed = jsonObject.get("energy").getAsDouble();
        } catch (RuntimeException e) {
            this.energyNeed = 0;
        }
        try {
            this.outputFluid = RecipeFluidUtils.getOutputFluid(jsonObject);
        } catch (RuntimeException e) {
            this.outputFluid = null;
        }
        try {
            this.outputs = Arrays.asList(RecipeUtils.getOutputs(jsonObject));
        } catch (RuntimeException e) {
            this.outputs = null;
        }
        try {
            this.inputs = new ArrayList<>();
            for (Object o : RecipeUtils.getInputs(jsonObject, false)) {
                this.inputs.add((ItemStack) o);
            }
        } catch (RuntimeException e) {
            this.inputs = null;
        }
        try {
            this.inputFluids = Arrays.asList(RecipeFluidUtils.getInputsFluid(jsonObject));
        } catch (RuntimeException e) {
            this.inputFluids = null;
        }

        return this;
    }

    @Override
    public void register() {
        if (this.energyNeed == 0 ||
            ((this.inputFluids == null || this.inputFluids.isEmpty()) && (this.inputs == null || this.inputs.isEmpty())) ||
            (this.outputFluid == null && (this.outputs == null || this.outputs.isEmpty()))) {
            return;
        }
        ModRecipes.universalFluidComplexRecipes.add(new UniversalFluidComplexRecipe(energyNeed, outputFluid, outputs, inputFluids, inputs));
    }

    public static class FluidAndItemStack {
        public final List<FluidStack> fluids;
        public final List<ItemStack> items;

        public FluidAndItemStack(List<FluidStack> fluids, List<ItemStack> items) {
            this.fluids = fluids;
            this.items = items;
        }
    }

}
