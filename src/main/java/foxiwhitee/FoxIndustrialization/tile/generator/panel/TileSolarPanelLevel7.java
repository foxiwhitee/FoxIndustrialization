package foxiwhitee.FoxIndustrialization.tile.generator.panel;

import foxiwhitee.FoxIndustrialization.config.FIConfig;

public class TileSolarPanelLevel7 extends TileCustomSolarPanel {
    public TileSolarPanelLevel7() {
        super(FIConfig.solarPanel7GeneratingDay, FIConfig.solarPanel7GeneratingNight, FIConfig.solarPanel7Output, FIConfig.solarPanel7Storage);
    }
}
