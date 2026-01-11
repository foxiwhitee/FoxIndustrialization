package foxiwhitee.FoxIndustrialization.integration.crafttweaker;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.recipes.UniversalFluidComplexRecipe;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenClass("mods.FoxIndustrialization.UFC")
public class UFCIntegration {

    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, ILiquidStack outputFluid,
                                 IItemStack[] inputs, ILiquidStack[] inputFluids, long energy) {

        if ((outputs == null || outputs.length == 0) && outputFluid == null) {
            MineTweakerAPI.getLogger().logError("Cannot add recipe: Output items and fluid cannot both be null!");
            return;
        }
        if ((inputs == null || inputs.length == 0) && (inputFluids == null || inputFluids.length == 0)) {
            MineTweakerAPI.getLogger().logError("Cannot add recipe: Input items and fluids cannot both be null!");
            return;
        }

        ItemStack[] mcOutputs = MineTweakerMC.getItemStacks(outputs);
        FluidStack mcOutputFluid = MineTweakerMC.getLiquidStack(outputFluid);
        ItemStack[] mcInputs = MineTweakerMC.getItemStacks(inputs);
        FluidStack[] mcInputFluids = MineTweakerMC.getLiquidStacks(inputFluids);

        MineTweakerAPI.apply(new AddRecipeAction(mcOutputs, mcOutputFluid, mcInputs, mcInputFluids, energy));
    }

    @ZenMethod
    public static void removeRecipe(@Optional IItemStack output, @Optional ILiquidStack outputFluid) {
        if (output == null && outputFluid == null) {
            MineTweakerAPI.getLogger().logError("Cannot remove recipe: both outputs and fluid are null!");
            return;
        }

        ItemStack mcOutputs = output != null ? MineTweakerMC.getItemStack(output) : null;
        FluidStack mcOutputFluid = (outputFluid != null) ? MineTweakerMC.getLiquidStack(outputFluid) : null;

        MineTweakerAPI.apply(new RemoveRecipeAction(mcOutputs, mcOutputFluid));
    }

    private static class AddRecipeAction implements IUndoableAction {
        private final UniversalFluidComplexRecipe recipe;

        public AddRecipeAction(ItemStack[] out, FluidStack outF, ItemStack[] in, FluidStack[] inF, long energy) {
            this.recipe = new UniversalFluidComplexRecipe(energy, outF, Arrays.asList(out), Arrays.asList(inF), Arrays.asList(in));
        }

        @Override
        public void apply() {
            ModRecipes.universalFluidComplexRecipes.add(recipe);
        }

        @Override
        public boolean canUndo() { return true; }

        @Override
        public void undo() {
            for (var recipe : ModRecipes.universalFluidComplexRecipes) {
                if (recipe.matches(this.recipe.getInputs(), this.recipe.getInputsFluid())) {
                    ModRecipes.universalFluidComplexRecipes.remove(recipe);
                    return;
                }
            }
        }

        @Override
        public String describe() { return "Adding Universal Fluid Complex recipe"; }

        @Override
        public String describeUndo() { return "Removing Universal Fluid Complex recipe"; }

        @Override
        public Object getOverrideKey() { return null; }
    }

    private static class RemoveRecipeAction implements IUndoableAction {
        private final ItemStack output;
        private final FluidStack outputFluid;
        private final List<Object> removedRecipes = new ArrayList<>();

        public RemoveRecipeAction(ItemStack output, FluidStack outputFluid) {
            this.output = output;
            this.outputFluid = outputFluid;
        }

        @Override
        public void apply() {
            for (var recipe : ModRecipes.universalFluidComplexRecipes) {
                if ((recipe.getOutputFluid() == null && outputFluid == null) || (recipe.getOutputFluid() != null && recipe.getOutputFluid().isFluidEqual(outputFluid))) {
                    boolean allNull = true;
                    for (ItemStack output : recipe.getOutputs()) {
                        if (output != null) {
                            allNull = false;
                            break;
                        }
                    }
                    if (output != null) {
                        allNull = false;
                    }
                    if (allNull) {
                        ModRecipes.universalFluidComplexRecipes.remove(recipe);
                        removedRecipes.add(recipe);
                    } else {
                        boolean equals = true;
                        for (ItemStack output : recipe.getOutputs()) {
                            if (output == null) {
                                continue;
                            }
                            if (!ItemStackUtil.stackEquals(output, this.output)) {
                                equals = false;
                                break;
                            }
                        }
                        if (equals) {
                            ModRecipes.universalFluidComplexRecipes.remove(recipe);
                            removedRecipes.add(recipe);
                        }
                    }
                }
            }
        }

        @Override
        public void undo() {
            for (Object r : removedRecipes) {
                ModRecipes.universalFluidComplexRecipes.add((UniversalFluidComplexRecipe) r);
            }
        }

        @Override
        public String describe() { return "Removing Universal Fluid Complex recipes by outputs"; }
        @Override
        public boolean canUndo() { return true; }
        @Override
        public String describeUndo() { return "Restoring removed Universal Fluid Complex recipes"; }
        @Override
        public Object getOverrideKey() { return null; }
    }
}
