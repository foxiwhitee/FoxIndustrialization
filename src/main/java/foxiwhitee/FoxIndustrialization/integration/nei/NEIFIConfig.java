package foxiwhitee.FoxIndustrialization.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiRecipeTab;
import codechicken.nei.recipe.HandlerInfo;
import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import net.minecraft.item.ItemStack;

public class NEIFIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        if (ContentConfig.enableUniversalFluidComplex) {
            UniversalFluidComplexRecipeHandler universalFluidComplexRecipeHandler = new UniversalFluidComplexRecipeHandler();
            API.registerRecipeHandler(universalFluidComplexRecipeHandler);
            API.registerUsageHandler(universalFluidComplexRecipeHandler);
            API.addRecipeCatalyst(new ItemStack(ModBlocks.universalFluidComplex), "foxiwhitee.FoxIndustrialization.integration.nei.UniversalFluidComplexRecipeHandler");

            HandlerInfo handler = new HandlerInfo(
                "foxiwhitee.FoxIndustrialization.integration.nei.UniversalFluidComplexRecipeHandler",
                FICore.MODNAME,
                FICore.MODID,
                true,
                ""
            );
            handler.setItem("foxindustrialization:universalFluidComplex", "");
            handler.setHandlerDimensions(122 + 16 + 5, 192, 2);
            GuiRecipeTab.handlerMap.put(handler.getHandlerName(), handler);
        }
        if (ContentConfig.enableMolecularTransformer) {
            MolecularTransformerRecipeHandler molecularTransformerRecipeHandler = new MolecularTransformerRecipeHandler();
            API.registerRecipeHandler(molecularTransformerRecipeHandler);
            API.registerUsageHandler(molecularTransformerRecipeHandler);
            API.addRecipeCatalyst(new ItemStack(ModBlocks.molecularTransformer), "foxiwhitee.FoxIndustrialization.integration.nei.MolecularTransformerRecipeHandler");

            HandlerInfo handler = new HandlerInfo(
                "foxiwhitee.FoxIndustrialization.integration.nei.MolecularTransformerRecipeHandler",
                FICore.MODNAME,
                FICore.MODID,
                true,
                ""
            );
            handler.setItem("foxindustrialization:molecularTransformer", "");
            handler.setHandlerDimensions(94 + 16 + 5, 192, 2);
            GuiRecipeTab.handlerMap.put(handler.getHandlerName(), handler);
        }
    }

    @Override
    public String getName() {
        return FICore.MODNAME;
    }

    @Override
    public String getVersion() {
        return FICore.VERSION;
    }
}
