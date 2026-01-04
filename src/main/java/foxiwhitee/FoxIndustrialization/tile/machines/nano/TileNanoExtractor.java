package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoExtractor extends TileNanoMachine {
    private static final InfoGui info = new InfoGui(0, 56, 83);

    public TileNanoExtractor() {
        super(MachineTier.NANO, FIConfig.nanoExtractorStorage, FIConfig.nanoExtractorItemsPerOp, FIConfig.nanoExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.extractorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
