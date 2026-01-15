package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileQuantumFurnace extends TileQuantumMachine {
    public TileQuantumFurnace() {
        super(MachineTier.QUANTUM, FIConfig.quantumFurnaceStorage, FIConfig.quantumFurnaceItemsPerOp, FIConfig.quantumFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.furnaceRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_ELECTRIC_FURNACE;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumFurnaceSupportsRF;
    }
}
