package foxiwhitee.FoxIndustrialization.client.gui.machine;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.machine.ContainerMachine;
import foxiwhitee.FoxIndustrialization.network.packets.C2SUpdateMachineInventoryModePacket;
import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxIndustrialization.tile.storage.TileEnergyStorage;
import foxiwhitee.FoxIndustrialization.utils.ButtonInventoryMode;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;

public abstract class GuiMachine extends FoxBaseGui {
    protected final TileBaseMachine tile;

    public GuiMachine(ContainerMachine container) {
        super(container, 262, 255);
        setModID(FICore.MODID);
        tile = (TileBaseMachine) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        TileBaseMachine.InfoGui info = tile.getInfoAboutGui();
        int x = 72 - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, info.getXStart(), info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

        this.bindTexture(FICore.MODID, this.getBackground());
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        ButtonInventoryMode inventoryMode = tile.getInventoryMode();
        UtilGui.drawTexture(27, 44, inventoryMode.getXStart(), inventoryMode.getYStart(), 18, 18, 18, 18);

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(48, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(224, 130 - y, 276, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 222, 80, 8, 52, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        NoTextureButton button = new NoTextureButton(0, this.guiLeft + 27, this.guiTop + 44, 18, 18,
            new String[]{"tooltip.button.name.none", "tooltip.button.name.merge", "tooltip.button.name.fill"},
            new String[]{"tooltip.button.description.none", "tooltip.button.description.merge", "tooltip.button.description.fill"});
        button.setCurrentText(tile.getInventoryMode().ordinal());
        this.buttonList.add(button);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof NoTextureButton && button.id == 0) {
            NetworkManager.instance.sendToServer(new C2SUpdateMachineInventoryModePacket(tile.xCoord, tile.yCoord, tile.zCoord));
        }
    }
}
