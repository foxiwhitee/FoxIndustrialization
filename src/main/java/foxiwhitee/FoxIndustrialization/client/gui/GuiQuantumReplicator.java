package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.container.ContainerQuantumReplicator;
import foxiwhitee.FoxIndustrialization.network.packets.C2SSetModeInReplicatorPacket;
import foxiwhitee.FoxIndustrialization.tile.TileQuantumReplicator;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fluids.FluidTank;

public class GuiQuantumReplicator extends FIGui {
    private final TileQuantumReplicator tile;

    public GuiQuantumReplicator(ContainerQuantumReplicator container) {
        super(container, 262, 260);
        tile = (TileQuantumReplicator) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        int x = 72 - (107 / 2);
        UtilGui.drawTexture(offsetX + 55 + x, offsetY + 6, 0, 353, 107, 7, 107, 7, 512, 512);

        this.bindTexture(FICore.MODID, this.getBackground());
        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            double l = ProductivityUtil.gauge(73,  tank.getFluidAmount(), tank.getCapacity());
            drawFluid(tank, offsetX + 68, offsetY + 131 - (int) l, 18, (int) l);
        }
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
        UtilGui.drawTexture(67, 58, 280, 0, 20, 75, 20, 75);

        double storedEnergy = tile.getEnergy();
        if (storedEnergy >= 0.0D) {
            double maxStoredEnergy = tile.getMaxEnergy();
            double y = ProductivityUtil.gauge(48, storedEnergy, maxStoredEnergy);
            UtilGui.drawTexture(224, 130 - y, 276, 0, 4, y, 4, y, 512.0D, 512.0D);
            drawIfInMouse(mouseX, mouseY, 222, 80, 8, 52, EnergyUtility.formatNumber(storedEnergy) + " / " + EnergyUtility.formatNumber(maxStoredEnergy) + " EU");
            this.bindTexture(FICore.MODID, this.getBackground());
        }

        FluidTank tank = tile.getTank();
        if (tank.getFluidAmount() > 0) {
            String text = LocalizationUtils.localize(tank.getFluid().getUnlocalizedName()) + "\n";
            text += tank.getFluidAmount() + " / " + tank.getCapacity() + " mB";
            drawIfInMouse(mouseX, mouseY, 68, 58, 20, 75, text);
            this.bindTexture(FICore.MODID, getBackground());
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiQuantumReplicator.png";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new NoTextureButton(0, guiLeft + 108, guiTop + 144, 14, 14, "tooltip.replicator.button.stop"));
        buttonList.add(new NoTextureButton(1, guiLeft + 124, guiTop + 144, 14, 14, "tooltip.replicator.button.single"));
        buttonList.add(new NoTextureButton(2, guiLeft + 140, guiTop + 144, 14, 14, "tooltip.replicator.button.repeat"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof NoTextureButton) {
            if (button.id == 0) {
                NetworkManager.instance.sendToServer(new C2SSetModeInReplicatorPacket(tile.xCoord, tile.yCoord, tile.zCoord, TileEntityReplicator.Mode.STOPPED));
            } else if (button.id == 1) {
                NetworkManager.instance.sendToServer(new C2SSetModeInReplicatorPacket(tile.xCoord, tile.yCoord, tile.zCoord, TileEntityReplicator.Mode.SINGLE));
            } else if (button.id == 2) {
                NetworkManager.instance.sendToServer(new C2SSetModeInReplicatorPacket(tile.xCoord, tile.yCoord, tile.zCoord, TileEntityReplicator.Mode.CONTINUOUS));
            }
        }
    }
}
