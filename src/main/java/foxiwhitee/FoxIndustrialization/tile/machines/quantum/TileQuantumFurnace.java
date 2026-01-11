package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumFurnace extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 112, 143);

    public TileQuantumFurnace() {
        super(MachineTier.QUANTUM, FIConfig.quantumFurnaceStorage, FIConfig.quantumFurnaceItemsPerOp, FIConfig.quantumFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.furnaceRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumFurnaceSupportsRF;
    }
}
