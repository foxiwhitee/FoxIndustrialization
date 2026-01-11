package foxiwhitee.FoxIndustrialization.tile.generator.kinetic;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileAdvancedKineticGenerator extends TileKineticGenerator {
    private final static InfoGui info = new InfoGui("guiAdvancedKineticGenerator", 0, 247, 149);

    public TileAdvancedKineticGenerator() {
        super(FIConfig.kineticGeneratorAdvancedOutput, FIConfig.kineticGeneratorAdvancedStorage);
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
