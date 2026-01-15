package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoExtractor extends TileNanoMachine {
    public TileNanoExtractor() {
        super(MachineTier.NANO, FIConfig.nanoExtractorStorage, FIConfig.nanoExtractorItemsPerOp, FIConfig.nanoExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.extractorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_EXTRACTOR;
    }
}
