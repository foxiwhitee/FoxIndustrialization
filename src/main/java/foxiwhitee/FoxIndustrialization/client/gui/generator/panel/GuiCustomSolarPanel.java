package foxiwhitee.FoxIndustrialization.client.gui.generator.panel;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.panel.ContainerCustomSolarPanel;
import foxiwhitee.FoxIndustrialization.tile.generator.panel.TileCustomSolarPanel;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiCustomSolarPanel extends FIGui {
    private final TileCustomSolarPanel tile;

    public GuiCustomSolarPanel(ContainerCustomSolarPanel container) {
        super(container, 262, 255);
        tile = (TileCustomSolarPanel) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(offsetX, offsetY, 0,0, this.xSize, this.ySize, this.xSize, this.ySize, 512, 512);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (65 / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, 0, 233, 65, 7, 65, 7, 512, 512);

    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(73, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(123, 132 - y, 272, 0, 16, y, 16, y, 512.0D, 512.0D);
            String text = EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU\n" +
                LocalizationUtils.localize("tooltip.generator.generate", EnergyUtility.formatNumber(tile.getGenerating()));
            drawIfInMouse(mouseX, mouseY, 120, 56, 20, 77, text);
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiCustomSolarPanel.png";
    }
}
