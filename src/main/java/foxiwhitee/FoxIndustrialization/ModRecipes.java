package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.BasicIC2MachineRecipe;
import foxiwhitee.FoxIndustrialization.recipes.UniversalFluidComplexRecipe;
import foxiwhitee.FoxLib.recipes.RecipesLocation;
import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    @RecipesLocation(modId = "foxindustrialization")
    public static final String[] recipes = {"recipes"};

    public static final List<BasicIC2MachineRecipe> furnaceRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> maceratorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> extractorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> compressorRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerExtrudingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerCuttingRecipes = new ArrayList<>();
    public static final List<BasicIC2MachineRecipe> metalformerRollingRecipes = new ArrayList<>();

    public static final List<UniversalFluidComplexRecipe> universalFluidComplexRecipes = new ArrayList<>();

    public static void initRecipes() {
        RecipeHelper.convertFurnaceRecipes();
        RecipeHelper.parseRecipe(maceratorRecipes, Recipes.macerator.getRecipes());
        RecipeHelper.parseRecipe(extractorRecipes, Recipes.extractor.getRecipes());
        RecipeHelper.parseRecipe(compressorRecipes, Recipes.compressor.getRecipes());
        RecipeHelper.parseRecipe(metalformerExtrudingRecipes, Recipes.metalformerExtruding.getRecipes());
        RecipeHelper.parseRecipe(metalformerCuttingRecipes, Recipes.metalformerCutting.getRecipes());
        RecipeHelper.parseRecipe(metalformerRollingRecipes, Recipes.metalformerRolling.getRecipes());
    }
}
