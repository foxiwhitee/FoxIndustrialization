package foxiwhitee.FoxIndustrialization.client.gui.generator.infinity;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.infinity.ContainerInfinityGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.infinity.TileInfinityGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiInfinityGenerator extends FIGui {
    private final TileInfinityGenerator tile;

    public GuiInfinityGenerator(ContainerInfinityGenerator container) {
        super(container, 262, 255);
        tile = (TileInfinityGenerator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(offsetX, offsetY, 0,0, this.xSize, this.ySize, this.xSize, this.ySize, 512, 512);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (107 / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, 0, 241, 107, 7, 107, 7, 512, 512);

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
        return "gui/guiInfinityGenerator.png";
    }
}
