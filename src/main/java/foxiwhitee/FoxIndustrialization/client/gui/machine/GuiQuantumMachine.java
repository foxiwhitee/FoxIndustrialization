package foxiwhitee.FoxIndustrialization.client.gui.machine;

import foxiwhitee.FoxIndustrialization.container.machine.ContainerQuantumMachine;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiQuantumMachine extends GuiMachine {
    public GuiQuantumMachine(ContainerQuantumMachine container) {
        super(container);
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        double ticksNeed = tile.getTicksNeed();
        int tick1 = tile.getTicks()[0];
        int tick2 = tile.getTicks()[1];
        int tick3 = tile.getTicks()[2];
        int tick4 = tile.getTicks()[3];

        if (tick1 > 0) {
            double l = ProductivityUtil.gauge(13, tick1, ticksNeed);
            UtilGui.drawTexture(75, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick2 > 0) {
            double l = ProductivityUtil.gauge(13, tick2, ticksNeed);
            UtilGui.drawTexture(111, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick3 > 0) {
            double l = ProductivityUtil.gauge(13, tick3, ticksNeed);
            UtilGui.drawTexture(147, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick4 > 0) {
            double l = ProductivityUtil.gauge(13, tick4, ticksNeed);
            UtilGui.drawTexture(183, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
    }
}
