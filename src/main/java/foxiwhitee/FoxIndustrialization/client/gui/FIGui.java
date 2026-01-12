package foxiwhitee.FoxIndustrialization.client.gui;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public abstract class FIGui extends FoxBaseGui {
    public FIGui(Container container, int xSize, int ySize) {
        super(container, xSize, ySize);
        setModID(FICore.MODID);
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
}
