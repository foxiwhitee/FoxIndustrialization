package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel8 extends TileCustomSolarPanel {
    public TileSolarPanelLevel8() {
        super(FIConfig.solarPanel8GeneratingDay, FIConfig.solarPanel8GeneratingNight, FIConfig.solarPanel8Output, FIConfig.solarPanel8Storage);
    }
}
