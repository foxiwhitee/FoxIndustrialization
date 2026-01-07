package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel1 extends TileCustomSolarPanel {
    public TileSolarPanelLevel1() {
        super(FIConfig.solarPanel1GeneratingDay, FIConfig.solarPanel1GeneratingNight, FIConfig.solarPanel1Output, FIConfig.solarPanel1Storage);
    }
}
