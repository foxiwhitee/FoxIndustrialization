package foxiwhitee.FoxIndustrialization.tile.generator.matter;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileNanoMatterGenerator extends TileMatterGenerator {
    private final static InfoGui info = new InfoGui("guiNanoMatter", 0, 313, 123);

    public TileNanoMatterGenerator() {
        super(FIConfig.nanoMatterGeneratorTank, FIConfig.nanoMatterGeneratorEnergyNeed, FIConfig.nanoMatterGeneratorGenerate);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.NANO;
    }
}
