package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumFurnace extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 112, 143);

    public TileQuantumFurnace() {
        super(MachineTier.QUANTUM, FIConfig.quantumFurnaceStorage, FIConfig.quantumFurnaceItemsPerOp, FIConfig.quantumFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.furnaceRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
