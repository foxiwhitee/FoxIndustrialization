package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerMolecularTransformer;
import foxiwhitee.FoxIndustrialization.network.packets.C2SClearTankInMTPacket;
import foxiwhitee.FoxIndustrialization.tile.TileMolecularTransformer;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraftforge.fluids.FluidTank;

public class GuiMolecularTransformer extends FIGui {
    private final TileMolecularTransformer tile;

    public GuiMolecularTransformer(ContainerMolecularTransformer container) {
        super(container, 282, 260);
        tile = (TileMolecularTransformer) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(73,  tank.getFluidAmount(), tank.getCapacity());
            drawFluid(tank, offsetX + 68, offsetY + 131 - (int) l, 18, (int) l);
            this.bindTexture(FICore.MODID, this.getBackground());
        }

        if (tile.getEnergyConsumed() > 0) {
            double l = ProductivityUtil.gauge(13, tile.getEnergyConsumed(), tile.getEnergyNeed());
            UtilGui.drawTexture(offsetX + 112, offsetY + 89, 282, 0, 4, (int) l, 4, (int) l);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(67, 58, 290, 0, 20, 75, 20, 75);

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(48, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(244, 130 - y, 286, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 244, 80, 8, 52, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
            this.bindTexture(FICore.MODID, this.getBackground());
        }

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            String text = LocalizationUtils.localize(tank.getFluid().getUnlocalizedName()) + "\n";
            text += tank.getFluidAmount() + " / " + tank.getCapacity() + " mB\n";
            text += LocalizationUtils.localize("tooltip.ufc.pressShiftToClear");
            drawIfInMouse(mouseX, mouseY, 68, 58, 20, 75, text);
            this.bindTexture(FICore.MODID, getBackground());
        }

        if (tile.getEnergyNeed() > 0) {
            String text = LocalizationUtils.localize("tooltip.molecularTransformer.energyNeed", EnergyUtility.formatNumber(tile.getEnergyNeed()));
            drawString(this.fontRendererObj, text, 145, 62, 0xbfbfbf);
            text = LocalizationUtils.localize("tooltip.molecularTransformer.energyConsumed", EnergyUtility.formatNumber(tile.getEnergyConsumed()));
            drawString(this.fontRendererObj, text, 145, 72, 0xbfbfbf);
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
            if (relX >= 67 && relX <= 86 && relY >= 58 && relY <= 132) {
                NetworkManager.instance.sendToServer(new C2SClearTankInMTPacket(tile.xCoord, tile.yCoord, tile.zCoord));
            }
        }
    }
}
