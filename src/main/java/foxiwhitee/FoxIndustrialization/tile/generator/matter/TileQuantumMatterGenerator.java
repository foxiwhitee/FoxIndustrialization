package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileQuantumMatterGenerator extends TileMatterGenerator {
    private final static InfoGui info = new InfoGui("guiQuantumMatter", 0, 321, 143);

    public TileQuantumMatterGenerator() {
        super(FIConfig.quantumMatterGeneratorTank, FIConfig.quantumMatterGeneratorEnergyNeed, FIConfig.quantumMatterGeneratorGenerate);
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumMatterGeneratorSupportsRF && FICore.ifCoFHCoreIsLoaded;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.QUANTUM;
    }
}
