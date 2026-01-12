package foxiwhitee.FoxIndustrialization.client.gui.generator.fuel;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public abstract class GuiGenerator extends FIGui {
    private final TileGenerator tile;

    public GuiGenerator(ContainerGenerator container) {
        super(container, 262, 255);
        tile = (TileGenerator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (getLength() / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, getXStart(), getYStart(), getLength(), 7, getLength(), 7, 512, 512);
        this.bindTexture(FICore.MODID, this.getBackground());
    }

    protected abstract int getYStart();

    protected abstract int getXStart();

    protected abstract int getLength();

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(130, storedEnergy, maxStoredEnergy);
            int works = 0;
            for (int i : tile.getFuel()) {
                if (i > 0) {
                    works++;
                }
            }
            UtilGui.drawTexture(66, 107, 406 - (int) y, 0, (int) y, 20, (int) y, 20, 512.0D, 512.0D);
            String text = EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU\n" +
                LocalizationUtils.localize("tooltip.generator.generate", EnergyUtility.formatNumber(works * tile.getProduction()));
            drawIfInMouse(mouseX, mouseY, 65, 105, 132, 24, text);
        }
        this.bindTexture(FICore.MODID, this.getBackground());
    }
}
