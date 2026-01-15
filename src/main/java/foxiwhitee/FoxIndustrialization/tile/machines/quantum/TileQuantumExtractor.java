package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumExtractor extends TileQuantumMachine {
    public TileQuantumExtractor() {
        super(MachineTier.QUANTUM, FIConfig.quantumExtractorStorage, FIConfig.quantumExtractorItemsPerOp, FIConfig.quantumExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.extractorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_EXTRACTOR;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumExtractorSupportsRF;
    }
}
