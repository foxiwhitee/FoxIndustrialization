package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;

public class TileAdvancedKineticGenerator extends TileKineticGenerator {
    public TileAdvancedKineticGenerator() {
        super(FIConfig.kineticGeneratorAdvancedOutput, FIConfig.kineticGeneratorAdvancedStorage);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_KINETIC_GENERATOR;
    }
}
