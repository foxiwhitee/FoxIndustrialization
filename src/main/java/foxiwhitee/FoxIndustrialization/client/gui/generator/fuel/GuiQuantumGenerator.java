package foxiwhitee.FoxIndustrialization.client.gui.generator.fuel;

import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerNanoGenerator;
import foxiwhitee.FoxIndustrialization.container.generator.fuel.ContainerQuantumGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileQuantumGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiQuantumGenerator extends GuiGenerator {
    private final TileQuantumGenerator tile;

    public GuiQuantumGenerator(ContainerQuantumGenerator container) {
        super(container);
        tile = (TileQuantumGenerator) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        int[] fuel = tile.getFuel();
        int[] fuelNeed = tile.getFuelNeed();

        if (fuel[0] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[0], fuelNeed[0]);
            UtilGui.drawTexture(75, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
        if (fuel[1] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[1], fuelNeed[1]);
            UtilGui.drawTexture(111, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
        if (fuel[2] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[2], fuelNeed[2]);
            UtilGui.drawTexture(147, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
        if (fuel[3] > 0) {
            double l = ProductivityUtil.gauge(12, fuel[3], fuelNeed[3]);
            UtilGui.drawTexture(183, 100 - (int) l, 272, 0, 4, (int) l + 1, 4, (int) l + 1);
        }
    }

    @Override
    protected int getYStart() {
        return 225;
    }

    @Override
    protected int getXStart() {
        return 0;
    }

    @Override
    protected int getLength() {
        return 101;
    }

    @Override
    protected String getBackground() {
        return "gui/guiQuantumGenerator.png";
    }
}
