package foxiwhitee.FoxIndustrialization.client.gui.generator.kinetic;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.kinetic.ContainerKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileKineticGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiKineticGenerator extends FIGui {
    private final TileKineticGenerator tile;

    public GuiKineticGenerator(ContainerKineticGenerator container) {
        super(container, 262, 255);
        this.tile = (TileKineticGenerator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(offsetX, offsetY, 0,0, this.xSize, this.ySize, this.xSize, this.ySize, 512, 512);

        this.bindTexture(FICore.MODID, "gui/names.png");
        TileKineticGenerator.InfoGui info = tile.getInfoAboutGui();
        int x = 75 - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + 51 + x, offsetY + 6, info.getXStart(), info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(73, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(122, 132 - y, 272, 0, 18, y, 18, y, 512.0D, 512.0D);
            String text = EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU\n" +
                LocalizationUtils.localize("tooltip.generator.generate", EnergyUtility.formatNumber(tile.getProduction()));
            drawIfInMouse(mouseX, mouseY, 119, 56, 22, 77, text);
        }
    }

    @Override
    protected String getBackground() {
        return "gui/" + tile.getInfoAboutGui().getTextureName() + ".png";
    }
}
