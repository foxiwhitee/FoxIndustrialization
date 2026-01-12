package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerMatterSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.TileMatterSynthesizer;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileMatterGenerator;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraftforge.fluids.FluidTank;

public class GuiMatterSynthesizer extends FIGui {
    private final TileMatterSynthesizer tile;

    public GuiMatterSynthesizer(ContainerMatterSynthesizer container) {
        super(container, 262, 282);
        tile = (TileMatterSynthesizer) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        TileMatterGenerator.InfoGui info = tile.getInfoAboutGui();
        int x = 75 - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + 51 + x, offsetY + 6, info.getXStart(), info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

        this.bindTexture(FICore.MODID, getBackground());
        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(75,  tank.getFluidAmount(), tank.getCapacity());
            drawFluid(tank, offsetX + 122, offsetY + 160 - (int) l, 18, (int) l);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, getBackground());
        UtilGui.drawTexture(121, 84, 262, 0, 20, 77, 20, 77);

        double energy = tile.getEnergy();
        if (energy > 0) {
            double l = ProductivityUtil.gauge(28,  energy, tile.getMaxEnergy());
            l = Math.min(l, 28);
            UtilGui.drawTexture(117, 165, 284, 0, (int) l, 7, (int) l, 7);
            String text = Math.min(((int) (100 * energy / tile.getMaxEnergy())), 100) + "%";
            drawIfInMouse(mouseX, mouseY, 117, 165, 28, 7, text);
            this.bindTexture(FICore.MODID, getBackground());
        }

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            String text = LocalizationUtils.localize(tank.getFluid().getUnlocalizedName()) + "\n";
            text += EnergyUtility.formatNumber(tank.getFluidAmount()) + " / " + EnergyUtility.formatNumber(tank.getCapacity()) + " mB\n";
            text += LocalizationUtils.localize("tooltip.matter.generate", EnergyUtility.formatNumber(tile.getAmount()));
            drawIfInMouse(mouseX, mouseY, 121, 84, 20, 77, text);
            this.bindTexture(FICore.MODID, getBackground());
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiMatterSynthesizer.png";
    }
}
