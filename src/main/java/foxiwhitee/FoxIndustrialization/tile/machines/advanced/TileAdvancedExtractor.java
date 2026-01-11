package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedExtractor extends TileAdvancedMachine {
    private static final InfoGui info = new InfoGui(0, 8, 101);

    public TileAdvancedExtractor() {
        super(MachineTier.ADVANCED, FIConfig.advancedExtractorStorage, FIConfig.advancedExtractorItemsPerOp, FIConfig.advancedExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.extractorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
