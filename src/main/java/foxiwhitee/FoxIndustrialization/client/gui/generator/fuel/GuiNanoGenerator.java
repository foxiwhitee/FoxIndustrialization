package foxiwhitee.FoxIndustrialization.client.gui.generator.fuel;

import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerNanoGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiNanoGenerator extends GuiGenerator {
    private final TileNanoGenerator tile;

    public GuiNanoGenerator(ContainerNanoGenerator container) {
        super(container);
        tile = (TileNanoGenerator) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        int[] fuel = tile.getFuel();
        int[] fuelNeed = tile.getFuelNeed();

        if (fuel[0] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[0], fuelNeed[0]);
            UtilGui.drawTexture(93, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
        if (fuel[1] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[1], fuelNeed[1]);
            UtilGui.drawTexture(129, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
        if (fuel[2] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[2], fuelNeed[2]);
            UtilGui.drawTexture(165, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
    }

    @Override
    protected int getYStart() {
        return 217;
    }

    @Override
    protected int getXStart() {
        return 0;
    }

    @Override
    protected int getLength() {
        return 83;
    }

    @Override
    protected String getBackground() {
        return "gui/guiNanoGenerator.png";
    }
}
