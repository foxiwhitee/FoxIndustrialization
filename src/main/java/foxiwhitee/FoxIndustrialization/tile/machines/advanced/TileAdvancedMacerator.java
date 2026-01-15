package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedMacerator extends TileAdvancedMachine {
    public TileAdvancedMacerator() {
        super(MachineTier.ADVANCED, FIConfig.advancedMaceratorStorage, FIConfig.advancedMaceratorItemsPerOp, FIConfig.advancedMaceratorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.maceratorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_MACERATOR;
    }
}
