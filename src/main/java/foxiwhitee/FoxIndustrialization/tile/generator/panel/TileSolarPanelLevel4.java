package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel4 extends TileCustomSolarPanel {
    public TileSolarPanelLevel4() {
        super(FIConfig.solarPanel4GeneratingDay, FIConfig.solarPanel4GeneratingNight, FIConfig.solarPanel4Output, FIConfig.solarPanel4Storage);
    }
}
