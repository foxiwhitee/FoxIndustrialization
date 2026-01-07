package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.TileSynthesizer;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiSynthesizer extends FoxBaseGui {
    private final TileSynthesizer tile;

    public GuiSynthesizer(ContainerSynthesizer container) {
        super(container, 262, 265);
        setModID(FICore.MODID);
        tile = (TileSynthesizer) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (107 / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, 0, 249, 107, 7, 107, 7, 512, 512);
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

    @Override
    protected String getBackground() {
        return "gui/guiSynthesizer.png";
    }
}
