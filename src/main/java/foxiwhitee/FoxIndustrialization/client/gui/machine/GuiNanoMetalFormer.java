package foxiwhitee.FoxIndustrialization.client.gui.machine;

import foxiwhitee.FoxIndustrialization.container.machine.ContainerNanoMachine;
import foxiwhitee.FoxIndustrialization.network.packets.C2SUpdateMetalFormerModePacket;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMetalFormer;
import foxiwhitee.FoxIndustrialization.utils.ButtonMetalFormerMode;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.gui.GuiButton;

public class GuiNanoMetalFormer extends GuiNanoMachine {
    private final TileNanoMetalFormer tile;

    public GuiNanoMetalFormer(ContainerNanoMachine container) {
        super(container);
        tile = (TileNanoMetalFormer) container.getTileEntity();
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(offsetX, offsetY, mouseX, mouseY);

        ButtonMetalFormerMode mode = tile.getMode();
        UtilGui.drawTexture(25, 217, mode.getXStart(), mode.getYStart(), 18, 18, 18, 18);
    }

    @Override
    public void initGui() {
        super.initGui();
        NoTextureButton button = new NoTextureButton(1, this.guiLeft + 25, this.guiTop + 217, 18, 18,
            new String[]{"tooltip.metalFormerMode.rolling", "tooltip.metalFormerMode.cutting", "tooltip.metalFormerMode.extruding"}, new String[0]);
        button.setCurrentText(tile.getMode().ordinal());
        buttonList.add(button);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof NoTextureButton && button.id == 1) {
            NetworkManager.instance.sendToServer(new C2SUpdateMetalFormerModePacket(tile.xCoord,  tile.yCoord, tile.zCoord));
        }
    }
}
