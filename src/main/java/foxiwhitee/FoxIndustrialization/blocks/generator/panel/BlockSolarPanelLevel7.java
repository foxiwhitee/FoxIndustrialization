package foxiwhitee.FoxIndustrialization.blocks.generator.panel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.panel.GuiCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileSolarPanelLevel7;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSolarPanelLevel7.class, container = ContainerCustomSolarPanel.class, gui = GuiCustomSolarPanel.class)
public class BlockSolarPanelLevel7 extends BlockCustomSolarPanel {
    public BlockSolarPanelLevel7(String name) {
        super(name, TileSolarPanelLevel7.class);
    }
}
