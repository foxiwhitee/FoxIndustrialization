package foxiwhitee.FoxIndustrialization.client.gui.storage;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiEnergyStorage extends FIGui {
    private final TileEnergyStorage tile;

    public GuiEnergyStorage(ContainerEnergyStorage container) {
        super(container, 262, 255);
        this.tile = (TileEnergyStorage) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(73, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(123, 132 - y, 272, 0, 16, y, 16, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 120, 56, 20, 77, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
        }
    }
}
