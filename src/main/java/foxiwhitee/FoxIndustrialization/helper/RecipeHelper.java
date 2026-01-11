package foxiwhitee.FoxIndustrialization.helper;

import foxiwhitee.FoxIndustrialization.ModRecipes;
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
    public static void parseRecipe(List<BasicIC2MachineRecipe> recipes, Map<IRecipeInput, RecipeOutput> map) {
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

    public static void convertFurnaceRecipes() {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.smelting();

        for(Map.Entry<ItemStack, ItemStack> entry : furnaceRecipes.getSmeltingList().entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                ModRecipes.furnaceRecipes.add(new BasicIC2MachineRecipe(entry.getKey(), entry.getValue()));
            }
        }
    }
}
