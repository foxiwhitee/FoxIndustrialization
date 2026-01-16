package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IMolecularTransformerRecipe;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.recipes.IUniversalFluidComplexRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipesLocation;
import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    @RecipesLocation(modId = "foxindustrialization")
    public static final String[] recipes = {"recipes"};

    public static final List<IRecipeIC2> furnaceRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> maceratorRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> extractorRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> compressorRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> metalformerExtrudingRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> metalformerCuttingRecipes = new ArrayList<>();
    public static final List<IRecipeIC2> metalformerRollingRecipes = new ArrayList<>();

    public static final List<IUniversalFluidComplexRecipe> universalFluidComplexRecipes = new ArrayList<>();
    public static final List<IMolecularTransformerRecipe> molecularTransformerRecipes = new ArrayList<>();

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
