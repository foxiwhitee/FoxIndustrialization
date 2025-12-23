package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerTestNanoCompressor;
import foxiwhitee.FoxIndustrialization.tile.machines.TIleTestNanoCompressor;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.inventory.Container;

public class GuiTestNanoCOmpressor extends FoxBaseGui {
    ContainerTestNanoCompressor container;
    public GuiTestNanoCOmpressor(ContainerTestNanoCompressor container) {
        super(container, 256, 249);
        setModID(FICore.MODID);
        this.container = container;
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        TIleTestNanoCompressor tile = (TIleTestNanoCompressor) container.getTileEntity();
        if (tile.getTicks()[0] > 0) {
            double l = ProductivityUtil.gauge(120, tile.getTicks()[0], tile.getTicksNeed());
            UtilGui.drawTexture(offsetX + 66, offsetY + 115, 0, 250, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }
        if (tile.getTicks()[1] > 0) {
            double l = ProductivityUtil.gauge(120, tile.getTicks()[1], tile.getTicksNeed());
            UtilGui.drawTexture(offsetX + 66, offsetY + 125, 0, 250, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }
        if (tile.getTicks()[2] > 0) {
            double l = ProductivityUtil.gauge(120, tile.getTicks()[2], tile.getTicksNeed());
            UtilGui.drawTexture(offsetX + 66, offsetY + 135, 0, 250, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }

        this.drawCenteredString(this.fontRendererObj, String.format("%s/%s eu", tile.getEnergy(), tile.getMaxEnergy()), offsetX + 200, offsetY + 50, 0xFFFFFF);
    }

    @Override
    protected String getBackground() {
        return "gui/test.png";
    }

    @Override
    protected int[] getSizeTexture() {
        return new int[] {256, 256};
    }
}
