package foxiwhitee.FoxIndustrialization.client.gui.machine;

import foxiwhitee.FoxIndustrialization.container.machine.ContainerAdvancedMachine;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiAdvancedMachine extends GuiMachine {
    public GuiAdvancedMachine(ContainerAdvancedMachine container) {
        super(container);
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        double ticksNeed = tile.getTicksNeed();
        int tick1 = tile.getTicks()[0];
        int tick2 = tile.getTicks()[1];

        if (tick1 > 0) {
            double l = ProductivityUtil.gauge(13, tick1, ticksNeed);
            UtilGui.drawTexture(111, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
        if (tick2 > 0) {
            double l = ProductivityUtil.gauge(13, tick2, ticksNeed);
            UtilGui.drawTexture(147, 89, 272, 0, 4, (int) l, 4, (int) l);
        }
    }
}
