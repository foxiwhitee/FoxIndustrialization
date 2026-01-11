package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoCompressor extends TileNanoMachine {
    private static final InfoGui info = new InfoGui(0, 48, 89);

    public TileNanoCompressor() {
        super(MachineTier.NANO, FIConfig.nanoCompressorStorage, FIConfig.nanoCompressorItemsPerOp, FIConfig.nanoCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.compressorRecipes;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
