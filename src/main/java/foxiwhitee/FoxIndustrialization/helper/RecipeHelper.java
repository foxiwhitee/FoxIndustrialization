package foxiwhitee.FoxIndustrialization.helper;

import foxiwhitee.FoxIndustrialization.recipes.BasicIC2MachineRecipe;
import foxiwhitee.FoxIndustrialization.utils.StackOreDict;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeHelper {
    public static final List<BasicIC2MachineRecipe> maceratorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> extractorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> compressorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerExtrudingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerCuttingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerRollingRecipes = new ArrayList<>();

    public static void init() {
        parseRecipe(maceratorRecipes, Recipes.macerator.getRecipes(), 8, 600);
        parseRecipe(extractorRecipes, Recipes.extractor.getRecipes(), 8, 600);
        parseRecipe(compressorRecipes, Recipes.compressor.getRecipes(), 8, 600);
        parseRecipe(metalformerExtrudingRecipes, Recipes.metalformerExtruding.getRecipes(), 15, 500);
        parseRecipe(metalformerCuttingRecipes, Recipes.metalformerCutting.getRecipes(), 15, 500);
        parseRecipe(metalformerRollingRecipes, Recipes.metalformerRolling.getRecipes(), 15, 500);
    }

    private static void parseRecipe(List<BasicIC2MachineRecipe> recipes, Map<IRecipeInput, RecipeOutput> map, double energyPerTick, double length) {
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : map.entrySet()) {
            Object input;
            if (entry.getKey() instanceof RecipeInputOreDict r) {
                input = new StackOreDict(r.input, r.amount);
            } else {
                input = entry.getKey().getInputs().get(0);
            }
            recipes.add(new BasicIC2MachineRecipe(input, entry.getValue().items.get(0), energyPerTick, length));
        }
    }
}
