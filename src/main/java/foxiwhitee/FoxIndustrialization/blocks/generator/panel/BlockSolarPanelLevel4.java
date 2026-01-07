package foxiwhitee.FoxIndustrialization.blocks.generator.panel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.panel.GuiCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileSolarPanelLevel4;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSolarPanelLevel4.class, container = ContainerCustomSolarPanel.class, gui = GuiCustomSolarPanel.class)
public class BlockSolarPanelLevel4 extends BlockCustomSolarPanel {
    public BlockSolarPanelLevel4(String name) {
        super(name, TileSolarPanelLevel4.class);
    }
}
