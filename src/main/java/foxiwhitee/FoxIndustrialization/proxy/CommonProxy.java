package foxiwhitee.FoxIndustrialization.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.ModItems;
import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.integration.IntegrationLoader;
import foxiwhitee.FoxIndustrialization.network.packets.*;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.api.FoxLibApi;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        IntegrationLoader.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        FoxLibApi.instance.registries().registerPacket().register(C2SUpdateMachineInventoryModePacket.class);
        FoxLibApi.instance.registries().registerPacket().register(C2SUpdateMetalFormerModePacket.class);
        FoxLibApi.instance.registries().registerPacket().register(C2SClearTankInUFCPacket.class);
        FoxLibApi.instance.registries().registerPacket().register(C2SClearTankInMTPacket.class);
        FoxLibApi.instance.registries().registerPacket().register(C2SScannerButtonActionPacket.class);
        FoxLibApi.instance.registries().registerPacket().register(C2SSetModeInReplicatorPacket.class);
        IntegrationLoader.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.initRecipes();
        FilterInitializer.initFilters();
        IntegrationLoader.postInit(event);
    }
}
