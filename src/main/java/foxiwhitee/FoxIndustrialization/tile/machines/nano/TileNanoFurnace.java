package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMachine;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoFurnace extends TileNanoMachine {
    private static final InfoGui info = new InfoGui(0, 64, 125);

    public TileNanoFurnace() {
        super(MachineTier.NANO, FIConfig.nanoFurnaceStorage, FIConfig.nanoFurnaceItemsPerOp, FIConfig.nanoFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.furnaceRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
