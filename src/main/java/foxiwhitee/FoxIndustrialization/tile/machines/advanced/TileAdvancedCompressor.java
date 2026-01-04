package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedCompressor extends TileAdvancedMachine {
    private static final InfoGui info = new InfoGui(0, 0, 107);

    public TileAdvancedCompressor() {
        super(MachineTier.ADVANCED, FIConfig.advancedCompressorStorage, FIConfig.advancedCompressorItemsPerOp, FIConfig.advancedCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.compressorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
