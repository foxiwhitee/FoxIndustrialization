package foxiwhitee.FoxIndustrialization.proxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.ModItems;
import foxiwhitee.FoxIndustrialization.helper.RecipeHelper;
import foxiwhitee.FoxIndustrialization.network.packets.C2SUpdateMachineInventoryModePacket;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.api.FoxLibApi;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

import java.util.HashMap;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }

    public void init(FMLInitializationEvent event) {
        FoxLibApi.instance.registries().registerPacket().register(C2SUpdateMachineInventoryModePacket.class);
    }

    public void postInit(FMLPostInitializationEvent event) {
        RecipeHelper.init();
        FilterInitializer.initFilters();
    }
}
