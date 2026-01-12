package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileSingularMatterGenerator extends TileMatterGenerator {
    private final static InfoGui info = new InfoGui("guiSingularMatter", 0, 329, 149);

    public TileSingularMatterGenerator() {
        super(FIConfig.singularMatterGeneratorTank, FIConfig.singularMatterGeneratorEnergyNeed, FIConfig.singularMatterGeneratorGenerate);
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.singularMatterGeneratorSupportsRF && FICore.ifCoFHCoreIsLoaded;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.SINGULAR;
    }
}
