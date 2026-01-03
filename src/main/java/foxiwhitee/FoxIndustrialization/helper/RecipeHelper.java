package foxiwhitee.FoxIndustrialization.helper;

import foxiwhitee.FoxIndustrialization.recipes.BasicIC2MachineRecipe;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeHelper {
    public static final List<BasicIC2MachineRecipe> furnaceRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> maceratorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> extractorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> compressorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerExtrudingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerCuttingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerRollingRecipes = new ArrayList<>();

    public static void init() {
        convertFurnaceRecipes();
        parseRecipe(maceratorRecipes, Recipes.macerator.getRecipes());
        parseRecipe(extractorRecipes, Recipes.extractor.getRecipes());
        parseRecipe(compressorRecipes, Recipes.compressor.getRecipes());
        parseRecipe(metalformerExtrudingRecipes, Recipes.metalformerExtruding.getRecipes());
        parseRecipe(metalformerCuttingRecipes, Recipes.metalformerCutting.getRecipes());
        parseRecipe(metalformerRollingRecipes, Recipes.metalformerRolling.getRecipes());
    }

    private static void parseRecipe(List<BasicIC2MachineRecipe> recipes, Map<IRecipeInput, RecipeOutput> map) {
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : map.entrySet()) {
            Object input;
            if (entry.getKey() instanceof RecipeInputOreDict r) {
                input = new StackOreDict(r.input, r.amount);
            } else {
                input = entry.getKey().getInputs().get(0);
            }
            recipes.add(new BasicIC2MachineRecipe(input, entry.getValue().items.get(0)));
        }
    }

    private static void convertFurnaceRecipes() {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.smelting();

        for(Map.Entry<ItemStack, ItemStack> entry : furnaceRecipes.getSmeltingList().entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                RecipeHelper.furnaceRecipes.add(new BasicIC2MachineRecipe(entry.getKey(), entry.getValue()));
            }
        }
    }
}
