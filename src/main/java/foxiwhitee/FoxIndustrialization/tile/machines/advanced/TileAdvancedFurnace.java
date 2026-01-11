package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxIndustrialization.utils.UpgradesTypes;

import java.util.List;

public class TileAdvancedFurnace extends TileAdvancedMachine {
    private static final InfoGui info = new InfoGui(0, 16, 143);

    public TileAdvancedFurnace() {
        super(MachineTier.ADVANCED, FIConfig.advancedFurnaceStorage, FIConfig.advancedFurnaceItemsPerOp, FIConfig.advancedFurnaceEnergyPerTick);
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
