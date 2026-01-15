package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileNanoKineticGenerator extends TileKineticGenerator {
    public TileNanoKineticGenerator() {
        super(FIConfig.kineticGeneratorNanoOutput, FIConfig.kineticGeneratorNanoStorage);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_KINETIC_GENERATOR;
    }
}
