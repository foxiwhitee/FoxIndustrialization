package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileNanoMatterGenerator extends TileMatterGenerator {
    public TileNanoMatterGenerator() {
        super(FIConfig.nanoMatterGeneratorTank, FIConfig.nanoMatterGeneratorEnergyNeed, FIConfig.nanoMatterGeneratorGenerate);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_MATTER_GENERATOR;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.NANO;
    }
}
