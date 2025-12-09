package foxiwhitee.FoxIndustrialization.recipes;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public interface IRecipeIC2 extends IFoxRecipe {
    double getEnergyPerTick();
    double getRecipeLength();
    Object getInput();
    boolean matches(ItemStack input);

    default List<Object> getInputs() {
        return Collections.singletonList(getInput());
    }

    default boolean matches(List<ItemStack> list) {
        return matches(list.get(0));
    }
}
