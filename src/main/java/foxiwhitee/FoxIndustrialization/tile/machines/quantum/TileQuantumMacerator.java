package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumMacerator extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 120, 101);

    public TileQuantumMacerator() {
        super(MachineTier.QUANTUM, FIConfig.quantumMaceratorStorage, FIConfig.quantumMaceratorItemsPerOp, FIConfig.quantumMaceratorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.maceratorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumMaceratorSupportsRF;
    }
}
