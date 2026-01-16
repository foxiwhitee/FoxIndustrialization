package foxiwhitee.FoxIndustrialization.recipes.json;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.recipes.impl.MolecularTransformerRecipe;
import foxiwhitee.FoxLib.recipes.json.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.*;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.value.IAny;

import java.util.List;

@JsonRecipe(value = "molecularTransformer", hasOreDict = true, hasMineTweaker = true)
public class MolecularTransformerJSONRecipe implements IJsonRecipe {
    @NumberValue("energy")
    private double energy;

    @FluidValue(value = "fluid", canBeNull = true)
    private FluidStack inputFluid;

    @RecipeOutput
    @RecipeValue("output")
    private ItemStack output;

    @OreValue("inputs")
    private List<Object> inputs;

    public MolecularTransformerJSONRecipe() {

    }

    @Override
    public void register() {
        if ((this.inputs.isEmpty() || this.inputs.get(0) == null) || energy == 0 || output == null) {
            return;
        }
        ModRecipes.molecularTransformerRecipes.add(new MolecularTransformerRecipe(energy, inputFluid, output, inputs.get(0), inputs.size() == 2 ? inputs.get(1) : null));
    }

    @Override
    public void addCraftByMineTweaker(IItemStack stack, IAny... inputs) {
        double energy = inputs[0].asDouble();
        FluidStack fluid = inputs[1].as(ILiquidStack.class) == null ? null : (FluidStack) inputs[1].as(ILiquidStack.class).getInternal();
        Object input1, input2 = null;
        IIngredient ingredient = inputs[2].as(IIngredient.class);
        if (ingredient instanceof IItemStack) {
            input1 = ingredient.getInternal();
        } else {
            input1 = new StackOreDict(ingredient.getMark().replace("ore:", ""), 1);
        }
        ingredient = inputs[3].as(IIngredient.class);
        if (ingredient != null) {
            if (ingredient instanceof IItemStack) {
                input2 = ingredient.getInternal();
            } else {
                input2 = new StackOreDict(ingredient.getMark().replace("ore:", ""), 1);
            }
        }
        ModRecipes.molecularTransformerRecipes.add(new MolecularTransformerRecipe(energy, fluid, (ItemStack) stack.getInternal(), input1, input2));
    }

    @Override
    public void removeCraftByMineTweaker(IItemStack stack) {
        ItemStack output = (ItemStack) stack.getInternal();
        ModRecipes.molecularTransformerRecipes.removeIf(recipe -> ItemStackUtil.stackEquals(recipe.getOutput(), output));
    }
}
