package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileAdvancedMatterGenerator extends TileMatterGenerator {
    public TileAdvancedMatterGenerator() {
        super(FIConfig.advancedMatterGeneratorTank, FIConfig.advancedMatterGeneratorEnergyNeed, FIConfig.advancedMatterGeneratorGenerate);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_MATTER_GENERATOR;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.ADVANCED;
    }
}
