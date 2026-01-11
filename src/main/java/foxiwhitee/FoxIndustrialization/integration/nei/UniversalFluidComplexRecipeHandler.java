package foxiwhitee.FoxIndustrialization.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.recipes.UniversalFluidComplexRecipe;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UniversalFluidComplexRecipeHandler extends TemplateRecipeHandler {
    private int tick;

    @Override
    public String getGuiTexture() {
        return FICore.MODID + ":textures/gui/guiNeiBlank.png";
    }

    @Override
    public String getRecipeName() {
        return "universalFluidComplex";
    }

    public String getRecipeID() {
        return "universalFluidComplex";
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 19, 18, 4), this.getRecipeID(), new Object[0]));
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(FICore.MODID + ":textures/gui/guiNeiUniversalFluidComplex.png");

        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 122);
        if (tick++ >= 19 * 20) {
            tick = 0;
        }
        GuiDraw.drawTexturedModalRect(74, 19, 166, 0, (int) ProductivityUtil.gauge(19, tick, 19 * 20), 4);
    }

    @Override
    public void drawForeground(int recipe) {
        super.drawForeground(recipe);
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(FICore.MODID + ":textures/gui/guiNeiUniversalFluidComplex.png");

        GuiDraw.drawTexturedModalRect(9, 35, 166, 4, 18, 56);
        GuiDraw.drawTexturedModalRect(31, 35, 166, 4, 18, 56);
        GuiDraw.drawTexturedModalRect(53, 35, 166, 4, 18, 56);
        GuiDraw.drawTexturedModalRect(117, 35, 166, 4, 18, 56);
    }

    @Override
    public void drawExtras(int recipe) {
        CachedUniversalFluidComplexRecipe r = (CachedUniversalFluidComplexRecipe) arecipes.get(recipe);

        for (int i = 0; i < r.inFluids.size(); i++) {
            drawFluid(10 + (i * 22), 36, r.inFluids.get(i), 16, 55);
        }

        if (r.outFluid != null) {
            drawFluid(118, 36, r.outFluid, 16, 55);
        }

        String energyText = EnumChatFormatting.GRAY + EnergyUtility.formatNumber(r.energy) + " EU";
        GuiDraw.drawString(energyText, 64, 100, 0x404040, false);
    }

    @Override
    public List<String> handleTooltip(GuiRecipe<?> gui, List<String> currentTip, int recipe) {
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        int xSize = 176;
        int ySize = Math.min(Math.max(gui.height - 68, 166), 370);

        int guiLeft = (gui.width - xSize) / 2;
        int guiTop = (gui.height - ySize) / 2 + 10;

        int recipeLeft = guiLeft + offset.x;
        int recipeTop = guiTop + offset.y;

        int relX = mouse.x;
        int relY = mouse.y;

        CachedUniversalFluidComplexRecipe r = (CachedUniversalFluidComplexRecipe) arecipes.get(recipe);

        for (int i = 0; i < r.inFluids.size(); i++) {
            int posX = 9 + (i * 22) + recipeLeft;
            int posY = 35 + recipeTop;

            if (relX >= posX && relX <= posX + 16 && relY >= posY && relY <= posY + 55) {
                addFluidTooltip(currentTip, r.inFluids.get(i));
            }
        }

        if (r.outFluid != null) {
            int posX = 118 + recipeLeft;
            int posY = 35 + recipeTop;
            if (relX >= posX && relX <= posX + 16 && relY >= posY && relY <= posY + 55) {
                addFluidTooltip(currentTip, r.outFluid);
            }
        }

        return super.handleTooltip(gui, currentTip, recipe);
    }

    private void addFluidTooltip(List<String> tip, FluidStack fluid) {
        tip.add(fluid.getLocalizedName());
        tip.add(EnumChatFormatting.GRAY.toString() + fluid.amount + " mB");
    }

    public void drawFluid(int x, int y, FluidStack fluid, int width, int height) {
        if (fluid == null || fluid.getFluid() == null) return;

        IIcon icon = fluid.getFluid().getStillIcon();
        if (icon == null) icon = fluid.getFluid().getIcon();
        if (icon == null) return;

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        int color = fluid.getFluid().getColor(fluid);
        GL11.glColor4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0F);

        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < height; j += 16) {
                int drawWidth = Math.min(width - i, 16);
                int drawHeight = Math.min(height - j, 16);

                drawFluidSection(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }
        GL11.glColor4f(1, 1, 1, 1);
    }

    private void drawFluidSection(int x, int y, IIcon icon, int width, int height) {
        double minU = icon.getMinU();
        double maxV = icon.getMaxV();
        double maxU = minU + (icon.getMaxU() - minU) * width / 16.0;
        double minV = maxV - (maxV - icon.getMinV()) * height / 16.0;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, minU, maxV);
        tessellator.addVertexWithUV(x + width, y + height, 0, maxU, maxV);
        tessellator.addVertexWithUV(x + width, y, 0, maxU, minV);
        tessellator.addVertexWithUV(x, y, 0, minU, minV);
        tessellator.draw();
    }

    public CachedUniversalFluidComplexRecipe getCachedRecipe(UniversalFluidComplexRecipe recipe) {
        return new CachedUniversalFluidComplexRecipe(recipe.getInputs().toArray(new ItemStack[0]), recipe.getInputsFluid().toArray(new FluidStack[0]), recipe.getOutputs().toArray(new ItemStack[0]), recipe.getOutputFluid(), recipe.getEnergyNeed());
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeID())) {
            for(UniversalFluidComplexRecipe recipe : ModRecipes.universalFluidComplexRecipes) {
                this.arecipes.add(this.getCachedRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (UniversalFluidComplexRecipe recipe : ModRecipes.universalFluidComplexRecipes) {
            for (ItemStack stack : recipe.getOutputs()) {
                if (stack.stackTagCompound != null &&
                    NEIServerUtils.areStacksSameType(stack, result) ||
                    stack.stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(stack, result)) {
                    this.arecipes.add(this.getCachedRecipe(recipe));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for(UniversalFluidComplexRecipe recipe : ModRecipes.universalFluidComplexRecipes) {
            if (recipe != null) {
                CachedUniversalFluidComplexRecipe crecipe = this.getCachedRecipe(recipe);
                if (crecipe.contains(crecipe.getIngredients(), ingredient) || crecipe.contains(crecipe.getOtherStacks(), ingredient)) {
                    this.arecipes.add(crecipe);
                }
            }
        }
    }

    public class CachedUniversalFluidComplexRecipe extends CachedRecipe {
        public List<PositionedStack> inputs = new ArrayList<>();
        public List<PositionedStack> outputs = new ArrayList<>();
        public List<FluidStack> inFluids = new ArrayList<>();
        public FluidStack outFluid;
        public double energy;

        public CachedUniversalFluidComplexRecipe(ItemStack[] inItems, FluidStack[] fluidsIn, ItemStack[] outItems, FluidStack fluidOut, double energy) {
            for (int i = 0; i < inItems.length; i++) {
                if (inItems[i] != null)
                    inputs.add(new PositionedStack(inItems[i], 13 + (i * 19), 13));
            }
            for (int i = 0; i < outItems.length; i++) {
                if (outItems[i] != null)
                    outputs.add(new PositionedStack(outItems[i], 99 + (i * 19), 13));
            }

            for (FluidStack fs : fluidsIn) if (fs != null) inFluids.add(fs);
            this.outFluid = fluidOut;
            this.energy = energy;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, inputs);
        }

        @Override
        public PositionedStack getResult() {
            if (outputs.isEmpty()) {
                return null;
            }
            return outputs.get(0);
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            List<PositionedStack> others = new ArrayList<PositionedStack>();
            if (outputs.size() > 1) {
                for (int i = 1; i < outputs.size(); i++) others.add(outputs.get(i));
            }
            return others;
        }
    }
}
