package foxiwhitee.FoxIndustrialization;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static foxiwhitee.FoxIndustrialization.FICore.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = DEPEND)
public class FICore {
    public static final String
        MODID = "FoxIndustrialization",
        MODNAME = "FoxIndustrialization",
        VERSION = "1.0.0",
        DEPEND = "required-after:IC2;";

    public static final CreativeTabs TAB = new CreativeTabs("FOX_INDUSTRIALIZATION_TAB") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.bedrock);
        }
    };
    @Mod.Instance(MODID)
    public static FICore instance;

    @SidedProxy(clientSide = "foxiwhitee.FoxIndustrialization.proxy.ClientProxy", serverSide = "foxiwhitee.FoxIndustrialization.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
