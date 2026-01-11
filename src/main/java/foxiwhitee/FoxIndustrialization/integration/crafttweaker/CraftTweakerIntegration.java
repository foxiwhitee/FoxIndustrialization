package foxiwhitee.FoxIndustrialization.integration.crafttweaker;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.integration.IIntegration;
import foxiwhitee.FoxIndustrialization.integration.Integration;
import minetweaker.MineTweakerAPI;

@Integration(modid = "MineTweaker3")
public class CraftTweakerIntegration implements IIntegration {

    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {
        MineTweakerAPI.registerClass(UFCIntegration.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {

    }
}
