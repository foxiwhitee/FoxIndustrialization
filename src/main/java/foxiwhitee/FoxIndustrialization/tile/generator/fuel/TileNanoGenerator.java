package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileNanoGenerator extends TileGenerator {
    public TileNanoGenerator() {
        super(MachineTier.NANO, FIConfig.generatorNanoOutput, FIConfig.generatorNanoProduction, FIConfig.generatorNanoStorage);
    }
}
