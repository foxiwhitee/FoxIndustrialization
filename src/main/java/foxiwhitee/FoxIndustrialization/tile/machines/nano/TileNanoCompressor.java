package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoCompressor extends TileNanoMachine {
    public TileNanoCompressor() {
        super(MachineTier.NANO, FIConfig.nanoCompressorStorage, FIConfig.nanoCompressorItemsPerOp, FIConfig.nanoCompressorEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.compressorRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_COMPRESSOR;
    }
}
