package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileNanoGenerator extends TileGenerator {
    public TileNanoGenerator() {
        super(MachineTier.NANO, FIConfig.generatorNanoOutput, FIConfig.generatorNanoProduction, FIConfig.generatorNanoStorage);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_GENERATOR;
    }
}
