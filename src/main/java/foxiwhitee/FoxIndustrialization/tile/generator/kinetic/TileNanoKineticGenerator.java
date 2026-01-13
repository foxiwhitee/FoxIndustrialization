package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileNanoKineticGenerator extends TileKineticGenerator{
    private final static InfoGui info = new InfoGui("guiNanoKineticGenerator", 0, 265, 131);

    public TileNanoKineticGenerator() {
        super(FIConfig.kineticGeneratorNanoOutput, FIConfig.kineticGeneratorNanoStorage);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
