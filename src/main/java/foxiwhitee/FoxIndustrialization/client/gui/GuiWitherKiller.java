package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerWitherKiller;
import foxiwhitee.FoxIndustrialization.tile.TileWitherKiller;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiWitherKiller extends FIGui {
    private final TileWitherKiller tile;

    public GuiWitherKiller(ContainerWitherKiller container) {
        super(container, 262, 255);
        tile = (TileWitherKiller) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());

        if (tile.getTick() > 0) {
            double l = ProductivityUtil.gauge(38, tile.getTick(), tile.getTicksNeed());
            UtilGui.drawTexture(112, 81, 280, 0, (int) l, 5, (int) l, 5);
        }

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(48, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(224, 130 - y, 276, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 222, 80, 8, 52, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
            this.bindTexture(FICore.MODID, this.getBackground());
        }
    }
}
