package foxiwhitee.FoxIndustrialization.blocks.generator.panel;

import foxiwhitee.FoxIndustrialization.client.gui.generator.panel.GuiCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileSolarPanelLevel8;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileSolarPanelLevel8.class, container = ContainerCustomSolarPanel.class, gui = GuiCustomSolarPanel.class)
public class BlockSolarPanelLevel8 extends BlockCustomSolarPanel {
    public BlockSolarPanelLevel8(String name) {
        super(name, TileSolarPanelLevel8.class);
    }
}
