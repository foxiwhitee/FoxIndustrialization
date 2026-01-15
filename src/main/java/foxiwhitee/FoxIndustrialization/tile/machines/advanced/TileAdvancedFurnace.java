package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedFurnace extends TileAdvancedMachine {
    public TileAdvancedFurnace() {
        super(MachineTier.ADVANCED, FIConfig.advancedFurnaceStorage, FIConfig.advancedFurnaceItemsPerOp, FIConfig.advancedFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.furnaceRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_ELECTRIC_FURNACE;
    }
}
