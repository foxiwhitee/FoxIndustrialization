package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoMacerator extends TileNanoMachine {
    public TileNanoMacerator() {
        super(MachineTier.NANO, FIConfig.nanoMaceratorStorage, FIConfig.nanoMaceratorItemsPerOp, FIConfig.nanoMaceratorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.maceratorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_MACERATOR;
    }
}
