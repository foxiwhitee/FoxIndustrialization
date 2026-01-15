package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileAdvancedGenerator extends TileGenerator {
    public TileAdvancedGenerator() {
        super(MachineTier.ADVANCED, FIConfig.generatorAdvancedOutput, FIConfig.generatorAdvancedProduction, FIConfig.generatorAdvancedStorage);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_GENERATOR;
    }
}
