package foxiwhitee.FoxIndustrialization.client.gui.generator.matter;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.client.gui.FIGui;
import foxiwhitee.FoxIndustrialization.container.generator.matter.ContainerMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileMatterGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraftforge.fluids.FluidTank;

public class GuiMatterGenerator extends FIGui {
    private final TileMatterGenerator tile;

    public GuiMatterGenerator(ContainerMatterGenerator container) {
        super(container, 262, 255);
        tile = (TileMatterGenerator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

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

        double energy = tile.getEnergy();
        if (energy > 0) {
            double l = ProductivityUtil.gauge(28,  energy, tile.getMaxEnergy());
            l = Math.min(l, 28);
            UtilGui.drawTexture(117, 138, 284, 0, (int) l, 7, (int) l, 7);
            String text = Math.min(((int) (100 * energy / tile.getMaxEnergy())), 100) + "%";
            drawIfInMouse(mouseX, mouseY, 117, 138, 28, 7, text);
            this.bindTexture(FICore.MODID, getBackground());
        }

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            String text = LocalizationUtils.localize(tank.getFluid().getUnlocalizedName()) + "\n";
            text += tank.getFluidAmount() + " / " + tank.getCapacity() + " mB";
            drawIfInMouse(mouseX, mouseY, 121, 57, 20, 77, text);
            this.bindTexture(FICore.MODID, getBackground());
        }
    }
}
