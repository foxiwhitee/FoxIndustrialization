package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileAdvancedCompressor extends TileAdvancedMachine {
    public TileAdvancedCompressor() {
        super(MachineTier.ADVANCED, FIConfig.advancedCompressorStorage, FIConfig.advancedCompressorItemsPerOp, FIConfig.advancedCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.compressorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_COMPRESSOR;
    }
}
