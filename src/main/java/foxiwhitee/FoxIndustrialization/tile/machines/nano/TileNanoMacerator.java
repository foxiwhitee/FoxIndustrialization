package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoMacerator extends TileNanoMachine {
    private static final InfoGui info = new InfoGui(0, 72, 83);

    public TileNanoMacerator() {
        super(MachineTier.NANO, FIConfig.nanoMaceratorStorage, FIConfig.nanoMaceratorItemsPerOp, FIConfig.nanoMaceratorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return RecipeHelper.maceratorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
