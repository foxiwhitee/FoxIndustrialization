package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.tile.TileIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public abstract class FIGui extends FoxBaseGui {
    private final TileIC2 tile;

    public FIGui(FoxBaseContainer container, int xSize, int ySize) {
        super(container, xSize, ySize);
        setModID(FICore.MODID);
        tile = (TileIC2) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);

        this.bindTexture(FICore.MODID, "gui/names.png");
        GuiInfo info = tile.getGuiInfo();
        int x = (info.getFieldLength() / 2) - (info.getLength() / 2);
        UtilGui.drawTexture(offsetX + info.getFieldXStart() + x, offsetY + 6, 0, info.getYStart(), info.getLength(), 7, info.getLength(), 7, 512, 512);

        this.bindTexture(FICore.MODID, this.getBackground());
    }

    @Override
    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FICore.MODID, this.getBackground());
    }

    protected void drawFluid(FluidTank tank, int x, int y, int width, int height) {
        FluidStack fluid = tank.getFluid();
        if (fluid == null || fluid.amount <= 0) {
            return;
        }

        Fluid fluidType = fluid.getFluid();
        IIcon fluidStill = fluidType.getIcon(fluid);

        if (fluidStill == null) {
            return;
        }

        int fluidHeight = height;
        if (fluidHeight <= 0 && fluid.amount > 0) fluidHeight = 1;

        int fluidColor = fluidType.getColor();

        float red = (float) (fluidColor >> 16 & 255) / 255.0F;
        float green = (float) (fluidColor >> 8 & 255) / 255.0F;
        float blue = (float) (fluidColor & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        int drawY = y + height - fluidHeight;

        for (int currentDrawHeight = 0; currentDrawHeight < fluidHeight; currentDrawHeight += 16) {
            int actualDrawHeight = Math.min(fluidHeight - currentDrawHeight, 16);

            drawTexturedModelRectFromIcon(
                x,
                drawY + fluidHeight - currentDrawHeight - actualDrawHeight,
                fluidStill,
                width,
                actualDrawHeight
            );
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected final String getBackground() {
        return "gui/" + tile.getGuiInfo().getTextureName() + ".png";
    }
}
