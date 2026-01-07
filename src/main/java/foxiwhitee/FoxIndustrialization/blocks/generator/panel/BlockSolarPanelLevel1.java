package foxiwhitee.FoxIndustrialization.blocks.generator.panel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.panel.GuiCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileSolarPanelLevel1;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSolarPanelLevel1.class, container = ContainerCustomSolarPanel.class, gui = GuiCustomSolarPanel.class)
public class BlockSolarPanelLevel1 extends BlockCustomSolarPanel {
    public BlockSolarPanelLevel1(String name) {
        super(name, TileSolarPanelLevel1.class);
    }
}
