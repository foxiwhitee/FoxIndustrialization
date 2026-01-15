package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumCompressor extends TileQuantumMachine {
    public TileQuantumCompressor() {
        super(MachineTier.QUANTUM, FIConfig.quantumCompressorStorage, FIConfig.quantumCompressorItemsPerOp, FIConfig.quantumCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.compressorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_COMPRESSOR;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumCompressorSupportsRF;
    }
}
