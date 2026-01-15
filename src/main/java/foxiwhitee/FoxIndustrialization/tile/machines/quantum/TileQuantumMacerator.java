package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumMacerator extends TileQuantumMachine {
    public TileQuantumMacerator() {
        super(MachineTier.QUANTUM, FIConfig.quantumMaceratorStorage, FIConfig.quantumMaceratorItemsPerOp, FIConfig.quantumMaceratorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.maceratorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_MACERATOR;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumMaceratorSupportsRF;
    }
}
