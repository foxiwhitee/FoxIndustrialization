package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel2 extends TileCustomSolarPanel {
    public TileSolarPanelLevel2() {
        super(FIConfig.solarPanel2GeneratingDay, FIConfig.solarPanel2GeneratingNight, FIConfig.solarPanel2Output, FIConfig.solarPanel2Storage);
    }
}
