package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerAdvancedScanner;
import foxiwhitee.FoxIndustrialization.network.packets.C2SScannerButtonActionPacket;
import foxiwhitee.FoxIndustrialization.tile.TileAdvancedScanner;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import ic2.core.util.Util;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;

import static ic2.core.block.machine.tileentity.TileEntityScanner.State.*;

public class GuiAdvancedScanner extends FIGui {
    private final TileAdvancedScanner tile;
    private final String[] info = new String[9];

    public GuiAdvancedScanner(ContainerAdvancedScanner container) {
        super(container, 262, 255);
        tile = (TileAdvancedScanner) container.getTileEntity();
        this.info[0] = LocalizationUtils.localize("tooltip.advancedScanner.idle");
        this.info[1] = LocalizationUtils.localize("tooltip.advancedScanner.info1");
        this.info[2] = LocalizationUtils.localize("tooltip.advancedScanner.info2");
        this.info[3] = LocalizationUtils.localize("tooltip.advancedScanner.info3");
        this.info[4] = LocalizationUtils.localize("tooltip.advancedScanner.info4");
        this.info[5] = LocalizationUtils.localize("tooltip.advancedScanner.info5");
        this.info[6] = LocalizationUtils.localize("tooltip.advancedScanner.info6");
        this.info[7] = LocalizationUtils.localize("tooltip.advancedScanner.info7");
        this.info[8] = LocalizationUtils.localize("tooltip.advancedScanner.info8");
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (89 / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, 0, 345, 89, 7, 89, 7, 512, 512);
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(48, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(224, 130 - y, 276, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 222, 80, 8, 52, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
            this.bindTexture(FICore.MODID, this.getBackground());
        }

        double ticksNeed = tile.getTicksNeed();
        int tick = tile.getProgress();

        if (tick > 0) {
            double l = ProductivityUtil.gauge(13, tick, ticksNeed);
            UtilGui.drawTexture(129, 89, 272, 0, 4, (int) l, 4, (int) l);
        }

        final int infoCordX = 83;
        final int infoCordY = 140;

        switch (tile.getState()) {
            case IDLE:
                this.fontRendererObj.drawString(this.info[0], infoCordX, infoCordY, 15461152);
                break;
            case NO_STORAGE:
                this.fontRendererObj.drawString(this.info[2], infoCordX, infoCordY, 15461152);
                break;
            case SCANNING:
                this.fontRendererObj.drawString(this.info[1], infoCordX, infoCordY, 2157374);
                break;
            case NO_ENERGY:
                this.fontRendererObj.drawString(this.info[3], infoCordX, infoCordY, 14094352);
                break;
            case ALREADY_RECORDED:
                this.fontRendererObj.drawString(this.info[8], infoCordX, infoCordY, 14094352);
                break;
            case FAILED:
                this.fontRendererObj.drawString(this.info[6], infoCordX, infoCordY, 14094352);
                break;
            case COMPLETED:
                this.fontRendererObj.drawString(EnergyUtility.formatNumber(tile.getPatternUu()) + " UUM", infoCordX, infoCordY, 2157374);
                break;
            case TRANSFER_ERROR:
                this.fontRendererObj.drawString(this.info[7], infoCordX, infoCordY, 14094352);
                break;
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiAdvancedScanner.png";
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new NoTextureButton(0,  this.guiLeft + 97, this.guiTop + 112, 10, 10, "tooltip.advancedScanner.button.delete"));
        this.buttonList.add(new NoTextureButton(1, this.guiLeft + 155, this.guiTop + 112, 10, 10, "tooltip.advancedScanner.button.save"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof NoTextureButton) {
            if (tile.getState() == COMPLETED || tile.getState() == TRANSFER_ERROR || tile.getState() == FAILED) {
                if (button.id == 0) {
                    NetworkManager.instance.sendToServer(new C2SScannerButtonActionPacket(tile.xCoord, tile.yCoord, tile.zCoord, 0));
                }

                if (tile.getState() != FAILED) {
                    if (button.id == 1) {
                        NetworkManager.instance.sendToServer(new C2SScannerButtonActionPacket(tile.xCoord, tile.yCoord, tile.zCoord, 1));
                    }
                }
            }
        }
    }
}
