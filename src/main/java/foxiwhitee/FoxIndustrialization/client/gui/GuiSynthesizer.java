package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.TileSynthesizer;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiSynthesizer extends FIGui {
    private final TileSynthesizer tile;

    public GuiSynthesizer(ContainerSynthesizer container) {
        super(container, 262, 265);
        tile = (TileSynthesizer) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, getBackground());
        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(76, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(69, 139 - y, 272, 0, 16, y, 16, y, 512.0D, 512.0D);
            String text = EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU\n" +
                LocalizationUtils.localize("tooltip.generator.generate", EnergyUtility.formatNumber(tile.getGenerating())) + "\n" +
                LocalizationUtils.localize("tooltip.solarPanel.output", EnergyUtility.formatNumber(tile.getOutput()));
            drawIfInMouse(mouseX, mouseY, 67, 61, 20, 80, text);
        }
    }
}
