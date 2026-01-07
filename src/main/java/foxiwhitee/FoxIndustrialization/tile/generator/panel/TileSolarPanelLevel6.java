package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel6 extends TileCustomSolarPanel {
    public TileSolarPanelLevel6() {
        super(FIConfig.solarPanel6GeneratingDay, FIConfig.solarPanel6GeneratingNight, FIConfig.solarPanel6Output, FIConfig.solarPanel6Storage);
    }
}
