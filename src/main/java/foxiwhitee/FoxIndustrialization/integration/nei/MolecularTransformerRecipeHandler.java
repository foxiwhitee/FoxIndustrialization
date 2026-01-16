package foxiwhitee.FoxIndustrialization.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.recipes.IMolecularTransformerRecipe;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MolecularTransformerRecipeHandler extends TemplateRecipeHandler {
    private int tick;

    @Override
    public String getGuiTexture() {
        return FICore.MODID + ":textures/gui/guiNeiBlank.png";
    }

    @Override
    public String getRecipeName() {
        return "molecularTransformer";
    }

    public String getRecipeID() {
        return "molecularTransformer";
    }

    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(74, 19, 16, 4), this.getRecipeID()));
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(FICore.MODID + ":textures/gui/guiNeiMolecularTransformer.png");

        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 94);
        if (tick++ >= 17 * 20) {
            tick = 0;
        }
        GuiDraw.drawTexturedModalRect(75, 18, 166, 0, (int) ProductivityUtil.gauge(17, tick, 17 * 20), 4);
    }

    @Override
    public void drawForeground(int recipe) {
        super.drawForeground(recipe);
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(FICore.MODID + ":textures/gui/guiNeiMolecularTransformer.png");

        GuiDraw.drawTexturedModalRect(9, 9, 166, 4, 18, 56);
    }

    @Override
    public void drawExtras(int recipe) {
        CachedMolecularTransformerRecipe r = (CachedMolecularTransformerRecipe) arecipes.get(recipe);

        if (r.fluid != null) {
            drawFluid(10, 10, r.fluid, 16, 55);
        }

        String energyText = EnumChatFormatting.GRAY + EnergyUtility.formatNumber(r.energy) + " EU";
        GuiDraw.drawString(energyText, 64, 72, 0x404040, false);
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

        CachedMolecularTransformerRecipe r = (CachedMolecularTransformerRecipe) arecipes.get(recipe);

        int posX = 9 + recipeLeft;
        int posY = 9 + recipeTop;
        if (r.fluid != null) {
            if (relX >= posX && relX <= posX + 16 && relY >= posY && relY <= posY + 55) {
                addFluidTooltip(currentTip, r.fluid);
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

    public CachedMolecularTransformerRecipe getCachedRecipe(IMolecularTransformerRecipe recipe) {
        return new CachedMolecularTransformerRecipe(new Object[]{recipe.getFirstInput(), recipe.getSecondInput()}, recipe.getOutput(), recipe.getInputFluid(), recipe.getEnergyNeed());
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeID())) {
            for(IMolecularTransformerRecipe recipe : ModRecipes.molecularTransformerRecipes) {
                this.arecipes.add(this.getCachedRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IMolecularTransformerRecipe recipe : ModRecipes.molecularTransformerRecipes) {
            ItemStack stack = recipe.getOutput();
            if (stack.stackTagCompound != null &&
                NEIServerUtils.areStacksSameType(stack, result) ||
                stack.stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(stack, result)) {
                this.arecipes.add(this.getCachedRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for(IMolecularTransformerRecipe recipe : ModRecipes.molecularTransformerRecipes) {
            if (recipe != null) {
                CachedMolecularTransformerRecipe crecipe = this.getCachedRecipe(recipe);
                if (crecipe.contains(crecipe.getIngredients(), ingredient) || crecipe.contains(crecipe.getOtherStacks(), ingredient)) {
                    this.arecipes.add(crecipe);
                }
            }
        }
    }

    public class CachedMolecularTransformerRecipe extends CachedRecipe {
        public List<PositionedStack> inputs = new ArrayList<>();
        public PositionedStack output;
        public FluidStack fluid;
        public double energy;

        public CachedMolecularTransformerRecipe(Object[] inItems, ItemStack output, FluidStack fluid, double energy) {
            for (int i = 0; i < inItems.length; i++) {
                if (inItems[i] != null) {
                    if (inItems[i] instanceof ItemStack) {
                        inputs.add(new PositionedStack(inItems[i], 33 + (i * 19), 12));
                    } else if (inItems[i] instanceof StackOreDict ore) {
                        List<ItemStack> ores = OreDictionary.getOres(ore.getOre());
                        inputs.add(new PositionedStack(ores, 33 + (i * 19), 12));
                    } else if (inItems[i] instanceof String str) {
                        List<ItemStack> ores = OreDictionary.getOres(str);
                        inputs.add(new PositionedStack(ores, 33 + (i * 19), 12));
                    }
                }
            }
            this.output = new PositionedStack(output, 98, 12);

            this.fluid = fluid;
            this.energy = energy;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, inputs);
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }
    }
}
