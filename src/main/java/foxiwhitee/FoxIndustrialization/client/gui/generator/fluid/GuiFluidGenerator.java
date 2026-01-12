package foxiwhitee.FoxIndustrialization.client.gui.generator.fluid;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.fluid.ContainerFluidGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileFluidGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraftforge.fluids.FluidTank;

public class GuiFluidGenerator extends FIGui {
    private final TileFluidGenerator tile;

    public GuiFluidGenerator(ContainerFluidGenerator container) {
        super(container, 262, 255);
        tile = (TileFluidGenerator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        TileFluidGenerator.InfoGui info = tile.getInfoAboutGui();
        int x = 72 - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, info.getXStart(), info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

        this.bindTexture(FICore.MODID, getBackground());
        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(75,  tank.getFluidAmount(), tank.getCapacity());
            drawFluid(tank, offsetX + 122, offsetY + 133 - (int) l, 18, (int) l);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, getBackground());
        UtilGui.drawTexture(121, 57, 262, 0, 20, 77, 20, 77);

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            String text = LocalizationUtils.localize(tank.getFluid().getUnlocalizedName()) + "\n";
            text += tank.getFluidAmount() + " / " + tank.getCapacity() + " mB";
            drawIfInMouse(mouseX, mouseY, 121, 57, 20, 77, text);
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiFluidGenerator.png";
    }
}
