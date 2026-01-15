package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileSingularMatterGenerator extends TileMatterGenerator {
    public TileSingularMatterGenerator() {
        super(FIConfig.singularMatterGeneratorTank, FIConfig.singularMatterGeneratorEnergyNeed, FIConfig.singularMatterGeneratorGenerate);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.SINGULAR_MATTER_GENERATOR;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.SINGULAR;
    }
}
