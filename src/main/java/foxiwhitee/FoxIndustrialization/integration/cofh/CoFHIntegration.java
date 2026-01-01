package foxiwhitee.FoxIndustrialization.integration.cofh;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;

@Integration(modid = "CoFHCore")
public class CoFHIntegration implements IIntegration {

    @Override
    public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent fmlInitializationEvent) {
        FilterInitializer.addClassToFilterUltimate(IEnergyContainerItem.class);
        FilterInitializer.addClassToFilterQuantum(IEnergyContainerItem.class);
        FilterInitializer.addClassToFilterSingular(IEnergyContainerItem.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent fmlPostInitializationEvent) {

    }
}
