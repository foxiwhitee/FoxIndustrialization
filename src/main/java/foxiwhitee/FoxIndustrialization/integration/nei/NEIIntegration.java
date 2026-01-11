package foxiwhitee.FoxIndustrialization.integration.nei;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxIndustrialization.integration.IIntegration;
import foxiwhitee.FoxIndustrialization.integration.Integration;

@Integration(modid = "NotEnoughItems")
public class NEIIntegration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent fmlInitializationEvent) {
        if (isClient())
            clientInit();
    }

    @SideOnly(Side.CLIENT)
    public void clientInit() {
        new NEIFIConfig().loadConfig();
    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {

    }
}
