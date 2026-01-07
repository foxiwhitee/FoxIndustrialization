package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel5 extends TileCustomSolarPanel {
    public TileSolarPanelLevel5() {
        super(FIConfig.solarPanel5GeneratingDay, FIConfig.solarPanel5GeneratingNight, FIConfig.solarPanel5Output, FIConfig.solarPanel5Storage);
    }
}
