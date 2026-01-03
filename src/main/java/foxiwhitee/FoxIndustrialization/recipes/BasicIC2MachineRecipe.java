package foxiwhitee.FoxIndustrialization.recipes;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import net.minecraft.item.ItemStack;

public class BasicIC2MachineRecipe implements IRecipeIC2 {
    private final Object input;
    private final ItemStack output;

    public BasicIC2MachineRecipe(Object input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public Object getInput() {
        return input;
    }

    @Override
    public boolean matches(ItemStack input) {
        if (this.input instanceof ItemStack) {
            return IFoxRecipe.simpleAreStacksEqual((ItemStack) this.input, input);
        } else if (this.input instanceof String) {
            return OreDictUtil.areStacksEqual(this.input, input);
        } else if (this.input instanceof StackOreDict ore) {
            return ore.check(input, false);
        }
        return false;
    }

    @Override
    public ItemStack getOut() {
        return output;
    }

}
