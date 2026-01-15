package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileQuantumMatterGenerator extends TileMatterGenerator {
    public TileQuantumMatterGenerator() {
        super(FIConfig.quantumMatterGeneratorTank, FIConfig.quantumMatterGeneratorEnergyNeed, FIConfig.quantumMatterGeneratorGenerate);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.QUANTUM_MATTER_GENERATOR;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.QUANTUM;
    }
}
