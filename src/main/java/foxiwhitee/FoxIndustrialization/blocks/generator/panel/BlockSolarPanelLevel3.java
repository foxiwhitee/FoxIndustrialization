package foxiwhitee.FoxIndustrialization.blocks.generator.panel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.panel.GuiCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileSolarPanelLevel3;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSolarPanelLevel3.class, container = ContainerCustomSolarPanel.class, gui = GuiCustomSolarPanel.class)
public class BlockSolarPanelLevel3 extends BlockCustomSolarPanel {
    public BlockSolarPanelLevel3(String name) {
        super(name, TileSolarPanelLevel3.class);
    }
}
