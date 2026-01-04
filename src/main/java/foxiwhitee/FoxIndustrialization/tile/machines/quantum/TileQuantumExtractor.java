package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumExtractor extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 104, 101);

    public TileQuantumExtractor() {
        super(MachineTier.QUANTUM, FIConfig.quantumExtractorStorage, FIConfig.quantumExtractorItemsPerOp, FIConfig.quantumExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.extractorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumExtractorSupportsRF;
    }
}
