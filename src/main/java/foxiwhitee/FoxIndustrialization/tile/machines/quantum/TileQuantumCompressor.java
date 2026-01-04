package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumCompressor extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 96, 107);

    public TileQuantumCompressor() {
        super(MachineTier.QUANTUM, FIConfig.quantumCompressorStorage, FIConfig.quantumCompressorItemsPerOp, FIConfig.quantumCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.compressorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumCompressorSupportsRF;
    }
}
