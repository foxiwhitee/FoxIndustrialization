package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel3 extends TileCustomSolarPanel {
    public TileSolarPanelLevel3() {
        super(FIConfig.solarPanel3GeneratingDay, FIConfig.solarPanel3GeneratingNight, FIConfig.solarPanel3Output, FIConfig.solarPanel3Storage);
    }
}
