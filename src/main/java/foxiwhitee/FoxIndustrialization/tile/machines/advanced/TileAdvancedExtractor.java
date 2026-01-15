package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedExtractor extends TileAdvancedMachine {
    public TileAdvancedExtractor() {
        super(MachineTier.ADVANCED, FIConfig.advancedExtractorStorage, FIConfig.advancedExtractorItemsPerOp, FIConfig.advancedExtractorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.extractorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_EXTRACTOR;
    }
}
