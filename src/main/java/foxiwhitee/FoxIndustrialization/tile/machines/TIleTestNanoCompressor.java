package foxiwhitee.FoxIndustrialization.tile.machines;

import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;

import java.util.List;

public class TIleTestNanoCompressor extends TileBaseMachine {
    public TIleTestNanoCompressor() {
        super(MachineTier.NANO, 1_000_000, 1, 300);
    }

    @Override
    protected UpgradesTypes[] getAvailableTypes() {
        return new UpgradesTypes[] {UpgradesTypes.REDSTONE, UpgradesTypes.EJECTOR, UpgradesTypes.PULLING};
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.compressorRecipes;
    }
}
