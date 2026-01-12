package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileAdvancedMatterGenerator extends TileMatterGenerator {
    private final static InfoGui info = new InfoGui("guiAdvancedMatter", 0, 305, 143);

    public TileAdvancedMatterGenerator() {
        super(FIConfig.advancedMatterGeneratorTank, FIConfig.advancedMatterGeneratorEnergyNeed, FIConfig.advancedMatterGeneratorGenerate);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.ADVANCED;
    }
}
