package foxiwhitee.FoxIndustrialization.client.gui.machine;

import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiNanoMachine extends GuiMachine {
    public GuiNanoMachine(ContainerNanoMachine container) {
        super(container);
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        double ticksNeed = tile.getTicksNeed();
        int tick1 = tile.getTicks()[0];
        int tick2 = tile.getTicks()[1];
        int tick3 = tile.getTicks()[2];

        if (tick1 > 0) {
            double l = ProductivityUtil.gauge(13, tick1, ticksNeed);
            UtilGui.drawTexture(93, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick2 > 0) {
            double l = ProductivityUtil.gauge(13, tick2, ticksNeed);
            UtilGui.drawTexture(129, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick3 > 0) {
            double l = ProductivityUtil.gauge(13, tick3, ticksNeed);
            UtilGui.drawTexture(165, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiNanoMachine.png";
    }
}
