package foxiwhitee.FoxIndustrialization.client.gui.storage;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.storage.ContainerEnergyStorage;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.inventory.Container;

public class GuiEnergyStorage extends FoxBaseGui {
    private final TileEnergyStorage tile;

    public GuiEnergyStorage(ContainerEnergyStorage container) {
        super(container, 262, 255);
        this.tile = (TileEnergyStorage) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(offsetX, offsetY, 0,0, this.xSize, this.ySize, this.xSize, this.ySize, 512, 512);

        this.bindTexture(FICore.MODID, "gui/names.png");
        TileEnergyStorage.InfoGui info = tile.getInfoAboutGui();
        int x = 72 - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, info.getXStart(), info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

        this.bindTexture(FICore.MODID, this.getBackground());
        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(73, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(this.guiLeft + 123, this.guiTop + 132 - y, 272, 0, 16, y, 16, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 120, 56, 21, 77, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
        }
    }

    @Override
    protected String getBackground() {
        return "gui/" + tile.getInfoAboutGui().getTextureName() + ".png";
    }
}
