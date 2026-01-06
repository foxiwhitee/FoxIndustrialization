package foxiwhitee.FoxIndustrialization.tile.generator.fuel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

public class TileAdvancedGenerator extends TileGenerator {
    public TileAdvancedGenerator() {
        super(MachineTier.ADVANCED, FIConfig.generatorAdvancedOutput, FIConfig.generatorAdvancedProduction, FIConfig.generatorAdvancedStorage);
    }
}
