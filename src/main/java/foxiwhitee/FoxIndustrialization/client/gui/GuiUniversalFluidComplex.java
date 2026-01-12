package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerUniversalFluidComplex;
import foxiwhitee.FoxIndustrialization.network.packets.C2SClearTankInUFC;
import foxiwhitee.FoxIndustrialization.tile.TileUniversalFluidComplex;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraftforge.fluids.FluidTank;

public class GuiUniversalFluidComplex extends FIGui {
    private final TileUniversalFluidComplex tile;

    public GuiUniversalFluidComplex(ContainerUniversalFluidComplex container) {
        super(container, 278, 280);
        tile = (TileUniversalFluidComplex) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (137 / 2);
        UtilGui.drawTexture(offsetX + 63 + x, offsetY + 6, 0, 281, 137, 7, 137, 7, 512, 512);

        this.bindTexture(FICore.MODID, this.getBackground());

        FluidTank tank1 = tile.getInputTank1();
        FluidTank tank2 = tile.getInputTank2();
        FluidTank tank3 = tile.getInputTank3();
        FluidTank tankOut = tile.getOutputTank();

        if (tank1.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(55,  tank1.getFluidAmount(), tank1.getCapacity());
            drawFluid(tank1, offsetX + 56, offsetY + 141 - (int) l, 16, (int) l);
        }
        if (tank2.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(55,  tank2.getFluidAmount(), tank2.getCapacity());
            drawFluid(tank2, offsetX + 78, offsetY + 141 - (int) l, 16, (int) l);
        }
        if (tank3.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(55,  tank3.getFluidAmount(), tank3.getCapacity());
            drawFluid(tank3, offsetX + 100, offsetY + 141 - (int) l, 16, (int) l);
        }
        if (tankOut.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(55,  tankOut.getFluidAmount(), tankOut.getCapacity());
            drawFluid(tankOut, offsetX + 184, offsetY + 141 - (int) l, 16, (int) l);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(55, 85, 292, 0, 18, 57, 18, 57);
        UtilGui.drawTexture(77, 85, 292, 0, 18, 57, 18, 57);
        UtilGui.drawTexture(99, 85, 292, 0, 18, 57, 18, 57);
        UtilGui.drawTexture(183, 85, 292, 0, 18, 57, 18, 57);

        if (tile.getTick() > 0) {
            double l = ProductivityUtil.gauge(38, tile.getTick(), tile.getTicksNeed());
            UtilGui.drawTexture(120, 66, 310, 0, (int) l, 4, (int) l, 4);
        }

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(78, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(240, 155 - y, 288, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 238, 75, 8, 82, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
            this.bindTexture(FICore.MODID, this.getBackground());
        }

        FluidTank tank1 = tile.getInputTank1();
        FluidTank tank2 = tile.getInputTank2();
        FluidTank tank3 = tile.getInputTank3();
        FluidTank tankOut = tile.getOutputTank();
        String shiftText = LocalizationUtils.localize("tooltip.ufc.pressShiftToClear");
        String text;
        if (tank1.getFluidAmount() > 0) {
            text = LocalizationUtils.localize(tank1.getFluid().getUnlocalizedName()) + "\n";
            text += tank1.getFluidAmount() + " / " + tank1.getCapacity() + " mB\n";
            text += shiftText;
            drawIfInMouse(mouseX, mouseY, 55, 85, 18, 57, text);
        }
        if (tank2.getFluidAmount() > 0) {
            text = LocalizationUtils.localize(tank2.getFluid().getUnlocalizedName()) + "\n";
            text += tank2.getFluidAmount() + " / " + tank2.getCapacity() + " mB\n";
            text += shiftText;
            drawIfInMouse(mouseX, mouseY, 77, 85, 18, 57, text);
        }
        if (tank3.getFluidAmount() > 0) {
            text = LocalizationUtils.localize(tank3.getFluid().getUnlocalizedName()) + "\n";
            text += tank3.getFluidAmount() + " / " + tank3.getCapacity() + " mB\n";
            text += shiftText;
            drawIfInMouse(mouseX, mouseY, 99, 85, 18, 57, text);
        }
        if (tankOut.getFluidAmount() > 0) {
            text = LocalizationUtils.localize(tankOut.getFluid().getUnlocalizedName()) + "\n";
            text += tankOut.getFluidAmount() + " / " + tankOut.getCapacity() + " mB\n";
            text += shiftText;
            drawIfInMouse(mouseX, mouseY, 183, 85, 18, 57, text);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;
        int relX = mouseX - guiLeft;
        int relY = mouseY - guiTop;

        if (mouseButton == 0 && isShiftKeyDown()) {
            int mode = -1;
            if (relX >= 55 && relX <= 72 && relY >= 85 && relY <= 141) {
                mode = 1;
            }
            if (relX >= 77 && relX <= 94 && relY >= 85 && relY <= 141) {
                mode = 2;
            }
            if (relX >= 99 && relX <= 116 && relY >= 85 && relY <= 141) {
                mode = 3;
            }
            if (relX >= 183 && relX <= 200 && relY >= 85 && relY <= 141) {
                mode = 0;
            }

            NetworkManager.instance.sendToServer(new C2SClearTankInUFC(tile.xCoord, tile.yCoord, tile.zCoord, mode));
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiUniversalFluidComplex.png";
    }
}
